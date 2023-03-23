package lib.dehaat.ledger.presentation.ledger.transactions.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dehaat.androidbase.helper.isNotNull
import lib.dehaat.ledger.R
import lib.dehaat.ledger.datasource.DummyDataSource
import lib.dehaat.ledger.initializer.themes.AIMSColors
import lib.dehaat.ledger.initializer.themes.DBAColors
import lib.dehaat.ledger.initializer.themes.LedgerColors
import lib.dehaat.ledger.initializer.toDateMonthYear
import lib.dehaat.ledger.presentation.ledger.transactions.constants.TransactionType
import lib.dehaat.ledger.presentation.model.transactions.TransactionViewData
import lib.dehaat.ledger.resources.Pumpkin120
import lib.dehaat.ledger.resources.SeaGreen110
import lib.dehaat.ledger.resources.textBold14Sp
import lib.dehaat.ledger.resources.textMedium14Sp
import lib.dehaat.ledger.util.getAmountInRupees

@Preview(
    name = "TransactionInvoiceItem Preview AIMS",
    showBackground = true
)
@Composable
fun TransactionInvoiceItemPreviewAIMS() {
    TransactionInvoiceItem(
        ledgerColors = AIMSColors(),
        data = DummyDataSource.transactionViewData,
        onClick = {}
    )
}

@Preview(
    name = "TransactionInvoiceItem Preview DBA",
    showBackground = true
)
@Composable
fun TransactionInvoiceItemPreviewDBA() {
    TransactionInvoiceItem(
        ledgerColors = DBAColors(),
        data = DummyDataSource.transactionViewData,
        onClick = {}
    )
}

@Composable
fun TransactionInvoiceItem(
    ledgerColors: LedgerColors,
    data: TransactionViewData,
    onClick: (data: TransactionViewData) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick(data) }
            .fillMaxWidth()
            .wrapContentHeight()
            .background(shape = RoundedCornerShape(9.dp), color = Color.White)
            .padding(top = 16.dp, start = 16.dp, end = 8.dp, bottom = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val icon = provideTransactionIcon(data.type)
        Image(painter = painterResource(id = icon), contentDescription = "transaction icon")

        var transactionType = provideTransactionLabel(data.type)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Row {
                val tagLabel = provideTransactionTag(data = data)
                if (tagLabel.isNotNull()) {
                    transactionType = "$transactionType ($tagLabel)"
                }
                Text(
                    text = transactionType,
                    style = textMedium14Sp(
                        textColor = if (data.type == TransactionType.PAYMENT) {
                            SeaGreen110
                        } else {
                            ledgerColors.CtaDarkColor
                        }
                    )
                )
            }

            Text(
                modifier = Modifier.padding(top = 8.dp),
                style = textMedium14Sp(textColor = ledgerColors.TransactionDateColor),
                text = data.date.toDateMonthYear(),
            )
        }

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = data.amount.getAmountInRupees(),
            style = textBold14Sp(textColor = getAmountColor(data.type, ledgerColors = ledgerColors))
        )
        Image(
            painter = painterResource(id = R.drawable.ic_transaction_arrow_right),
            contentDescription = "Proceed"
        )
    }
}

fun getAmountColor(type: String, ledgerColors: LedgerColors) = when (type) {
    TransactionType.INVOICE -> ledgerColors.TransactionAmountColor
    TransactionType.DEBIT_NOTE -> Pumpkin120
    TransactionType.DEBIT_ENTRY -> Pumpkin120
    else -> ledgerColors.DownloadInvoiceColor
}

@Composable
private fun provideTransactionIcon(type: String) = when (type) {
    TransactionType.INVOICE -> R.drawable.ic_ledger_revamp_invoice
    TransactionType.PAYMENT -> R.drawable.ic_ledger_revamp_payment
    TransactionType.CREDIT_NOTE -> R.drawable.ic_ledger_revamp_credit_note
    TransactionType.DEBIT_NOTE -> R.drawable.ledger_debit_note
    TransactionType.DEBIT_ENTRY -> R.drawable.ledger_debit_note
    TransactionType.INTEREST -> R.drawable.ic_ledger_revamp_interest
    TransactionType.FINANCING_FEE -> R.drawable.ic_ledger_revamp_invoice
    else -> R.drawable.ic_ledger_revamp_invoice
}

@Composable
private fun provideTransactionLabel(type: String) = when (type) {
    TransactionType.INVOICE -> stringResource(id = R.string.invoice)
    TransactionType.PAYMENT -> stringResource(id = R.string.ledger_payment, "")
    TransactionType.CREDIT_NOTE -> stringResource(id = R.string.ledger_credit_note)
    TransactionType.DEBIT_NOTE -> stringResource(id = R.string.debit_note)
    TransactionType.DEBIT_ENTRY -> stringResource(id = R.string.debit_entry)
    TransactionType.INTEREST -> stringResource(id = R.string.interest_amount_ledger)
    TransactionType.FINANCING_FEE -> stringResource(id = R.string.financing_fee)
    else -> ""
}

private fun provideTransactionTag(data: TransactionViewData) = when (data.type) {
    TransactionType.PAYMENT -> data.paymentModeWithScheme
    TransactionType.CREDIT_NOTE -> data.creditNoteReason
    else -> null
}