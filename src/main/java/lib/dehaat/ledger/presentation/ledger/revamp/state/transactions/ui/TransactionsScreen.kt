package lib.dehaat.ledger.presentation.ledger.revamp.state.transactions.ui

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.dehaat.androidbase.helper.showToast
import lib.dehaat.ledger.R
import lib.dehaat.ledger.initializer.LedgerSDK
import lib.dehaat.ledger.initializer.themes.LedgerColors
import lib.dehaat.ledger.navigation.DetailPageNavigationCallback
import lib.dehaat.ledger.presentation.RevampLedgerViewModel
import lib.dehaat.ledger.presentation.common.UiEvent
import lib.dehaat.ledger.presentation.ledger.components.NoDataFound
import lib.dehaat.ledger.presentation.ledger.components.ShowProgress
import lib.dehaat.ledger.presentation.ledger.details.invoice.RevampInvoiceDetailViewModel
import lib.dehaat.ledger.presentation.ledger.details.payments.PaymentDetailViewModel
import lib.dehaat.ledger.presentation.ledger.revamp.state.creditnote.CreditNoteDetailsViewModel
import lib.dehaat.ledger.presentation.ledger.revamp.state.transactions.TransactionViewModel
import lib.dehaat.ledger.presentation.ledger.ui.component.TransactionCard
import lib.dehaat.ledger.presentation.ledger.ui.component.TransactionListHeader
import lib.dehaat.ledger.presentation.ledger.ui.component.TransactionType
import lib.dehaat.ledger.resources.Color3985BF
import lib.dehaat.ledger.resources.Color3BC6CA
import lib.dehaat.ledger.resources.mediumShape
import lib.dehaat.ledger.resources.textParagraphT2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsScreen(
    ledgerViewModel: RevampLedgerViewModel,
    ledgerColors: LedgerColors,
    onError: (Exception) -> Unit,
    detailPageNavigationCallback: DetailPageNavigationCallback,
    showFilterSheet: () -> Unit
) {
    val viewModel = hiltViewModel<TransactionViewModel>()
    val transactions = viewModel.transactionsList.collectAsLazyPagingItems()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                ledgerViewModel.downloadLedger()
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.external_storage_permission_required),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    )
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        stickyHeader {
            TransactionListHeader(
                ledgerViewModel = ledgerViewModel,
                onFilterClick = showFilterSheet,
                onLedgerDownloadClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        ledgerViewModel.downloadLedger()
                    } else {
                        launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                }
            ) { detailPageNavigationCallback.navigateToABSDetailPage(it) }
        }
        item {
            if (uiState.showWeeklyInterestDecreasingLabel) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color3985BF,
                                    Color3BC6CA
                                )
                            ),
                            shape = mediumShape()
                        )
                        .padding(vertical = 8.dp),
                    text = stringResource(R.string.ledger_interest_amount_reduced),
                    textAlign = TextAlign.Center,
                    style = textParagraphT2(Color.White)
                )
            }
        }
        items(transactions) { transaction ->
            transaction?.let {
                when (transaction.type) {
                    TransactionType.Invoice().invoiceType -> TransactionCard(
                        transactionType = TransactionType.Invoice(),
                        transaction = transaction,
                        modifier = Modifier.clickable {
                            transaction.erpId?.let { erpId ->
                                detailPageNavigationCallback.navigateToRevampInvoiceDetailPage(
                                    RevampInvoiceDetailViewModel.getBundle(
                                        ledgerId = transaction.ledgerId,
                                        source = transaction.source,
                                        erpId = erpId
                                    )
                                )
                            }
                        }
                    )

                    TransactionType.CreditNote().creditNoteType -> TransactionCard(
                        transactionType = TransactionType.CreditNote(),
                        transaction = transaction,
                        modifier = Modifier.clickable {
                            detailPageNavigationCallback.navigateToRevampCreditNoteDetailPage(
                                CreditNoteDetailsViewModel.getBundle(transaction.ledgerId)
                            )
                        }
                    )
                    TransactionType.Payment().paymentType -> TransactionCard(
                        transactionType = TransactionType.Payment(),
                        transaction = transaction,
                        modifier = Modifier.clickable {
                            detailPageNavigationCallback.navigateToRevampPaymentDetailPage(
                                PaymentDetailViewModel.getBundle(
                                    transaction.ledgerId,
                                    transaction.unrealizedPayment
                                )
                            )
                        }
                    )
                    TransactionType.Interest().interestType -> {
                        TransactionCard(
                            transactionType = TransactionType.Interest(),
                            transaction = transaction
                        )
                    }
                    TransactionType.FinancingFee().financingFeeType -> TransactionCard(
                        transactionType = TransactionType.FinancingFee(),
                        transaction = transaction
                    )
                    TransactionType.DebitNote().type -> TransactionCard(
                        transactionType = TransactionType.DebitNote(),
                        transaction = transaction
                    )
                    TransactionType.DebitEntry().type -> TransactionCard(
                        transactionType = TransactionType.DebitEntry(),
                        transaction = transaction
                    )
                }
            }
        }
        transactions.apply {
            when {
                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                    item { ShowProgress(ledgerColors) }
                }
                loadState.append is LoadState.NotLoading && loadState.append.endOfPaginationReached && itemCount == 0 -> {
                    item { NoDataFound {} }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.uiEvent.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).collect { event ->
            when (event) {
                UiEvent.RefreshList -> transactions.refresh()
                is UiEvent.ShowSnackbar -> {
                    onError(Exception(event.message))
                    if (LedgerSDK.isDebug) {
                        context.showToast(event.message)
                    } else {
                        context.showToast(R.string.tech_problem)
                    }
                }
                UiEvent.Success -> Unit
            }
        }
    }

    LaunchedEffect(Unit) {
        ledgerViewModel.selectedDaysToFilterEvent.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).collect { event ->
            viewModel.updateSelectedFilter(event)
        }
    }
}
