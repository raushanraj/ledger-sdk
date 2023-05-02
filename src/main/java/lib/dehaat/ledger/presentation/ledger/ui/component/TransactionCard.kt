package lib.dehaat.ledger.presentation.ledger.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lib.dehaat.ledger.R
import lib.dehaat.ledger.datasource.DummyDataSource
import lib.dehaat.ledger.initializer.toDateMonthYear
import lib.dehaat.ledger.presentation.common.uicomponent.VerticalSpacer
import lib.dehaat.ledger.presentation.model.revamp.transactions.TransactionViewDataV2
import lib.dehaat.ledger.resources.LedgerTheme
import lib.dehaat.ledger.resources.Neutral10
import lib.dehaat.ledger.resources.Neutral60
import lib.dehaat.ledger.resources.Neutral70
import lib.dehaat.ledger.resources.Neutral80
import lib.dehaat.ledger.resources.Pumpkin120
import lib.dehaat.ledger.resources.SeaGreen110
import lib.dehaat.ledger.resources.Warning10
import lib.dehaat.ledger.resources.textCaptionCP1
import lib.dehaat.ledger.resources.textParagraphT1Highlight
import lib.dehaat.ledger.util.getAmountInRupees

@Preview(
    name = "TransactionCard Invoice Preview",
    showBackground = true
)
@Composable
private fun TransactionCardInvoicePreview() = LedgerTheme {
    TransactionCard(
        transactionType = TransactionType.Invoice(),
        transaction = DummyDataSource.invoiceTransaction
    )
}

@Preview(
    name = "TransactionCard CreditNote Preview",
    showBackground = true
)
@Composable
private fun TransactionCardCreditNotePreview() = LedgerTheme {
    TransactionCard(
        transactionType = TransactionType.CreditNote(),
        transaction = DummyDataSource.invoiceTransaction
    )
}

@Preview(
    name = "TransactionCard Payment Preview",
    showBackground = true
)
@Composable
private fun TransactionCardPaymentPreview() = LedgerTheme {
    TransactionCard(
        transactionType = TransactionType.Payment(),
        transaction = DummyDataSource.invoiceTransaction
    )
}

@Preview(
    name = "TransactionCard Interest Preview",
    showBackground = true
)
@Composable
private fun TransactionCardInterestPreview() = LedgerTheme {
    TransactionCard(
        transactionType = TransactionType.Interest(),
        transaction = DummyDataSource.invoiceTransaction
    )
}

@Preview(
    name = "TransactionCard Financing Fee Preview",
    showBackground = true
)
@Composable
private fun TransactionCardFinancingFeePreview() = LedgerTheme {
    TransactionCard(
        transactionType = TransactionType.FinancingFee(),
        transaction = DummyDataSource.invoiceTransaction
    )
}

@Composable
fun TransactionCard(
    transactionType: TransactionType,
    transaction: TransactionViewDataV2,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
        .background(Color.White)
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
) {
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()

    ) {

        Image(
            modifier = Modifier
                .height(32.dp)
                .width(32.dp),
            painter = painterResource(id = transactionType.getIcon()),
            contentDescription = stringResource(id = R.string.accessibility_icon)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val name = when (transactionType) {
                    is TransactionType.CreditNote, is TransactionType.DebitNote, is TransactionType.DebitEntry -> {
                        stringResource(
                            id = transactionType.name,
                            transaction.creditNoteReason.orEmpty()
                        )
                    }

                    is TransactionType.Payment -> {
                        getPaymentTransactionLabel(transactionType, transaction)
                    }

                    else -> {
                        stringResource(id = transactionType.name)
                    }
                }
                Text(
                    text = name,
                    modifier = Modifier.weight(1f),
                    style = textParagraphT1Highlight(
                        if (transactionType is TransactionType.Payment) {
                            SeaGreen110
                        } else {
                            Neutral80
                        }
                    )
                )
                Text(
                    text = transactionType.getAmount(transaction.amount.getAmountInRupees()),
                    style = textParagraphT1Highlight(transactionType.amountColor())
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val date = when (transactionType) {
                    is TransactionType.Interest -> {
                        when {
                            transaction.fromDate == null && transaction.toDate != null -> {
                                stringResource(
                                    id = R.string.weekly_interest_till_date_,
                                    transaction.toDate
                                )
                            }
                            transaction.fromDate == null && transaction.toDate == null -> {
                                stringResource(id = R.string.weekly_interest_till_date)
                            }
                            transaction.fromDate == transaction.toDate && transaction.toDate != null -> {
                                transaction.toDate
                            }
                            transaction.fromDate != null && transaction.toDate != null -> {
                                stringResource(
                                    id = R.string.ledger_to,
                                    transaction.fromDate,
                                    transaction.toDate
                                )
                            }
                            else -> ""
                        }
                    }
                    else -> transaction.date.toDateMonthYear()
                }
                Text(
                    text = date,
                    style = textCaptionCP1(Neutral60)
                )

                transaction.interestStartDate?.let {
                    Text(
                        modifier = Modifier
                            .background(color = Warning10, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        text = stringResource(
                            id = R.string.interest_start_dates,
                            it.toDateMonthYear()
                        ),
                        style = textCaptionCP1(Neutral80)
                    )
                }
                transaction.unrealizedPayment?.let {
                    if (it)
                        Text(
                            modifier = Modifier
                                .background(color = Neutral10, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            text = stringResource(R.string.under_process),
                            style = textCaptionCP1(Neutral70)
                        )
                }
            }
        }
    }
    VerticalSpacer(height = 16.dp)
    Divider()
}

@Composable
private fun getPaymentTransactionLabel(
    transactionType: TransactionType,
    transaction: TransactionViewDataV2
) = stringResource(
    id = transactionType.name,
    transaction.schemeName?.let {
        stringResource(
            id = R.string.colon_value,
            it
        )
    }.orEmpty()
)

sealed class TransactionType(@StringRes val name: Int, val type: String) {
    data class Invoice(
        val invoiceName: Int = R.string.invoice,
        val invoiceType: String = "INVOICE"
    ) : TransactionType(
        name = invoiceName, type = invoiceType
    )

    data class CreditNote(
        val creditNoteName: Int = R.string.credit_note_ledger,
        val creditNoteType: String = "CREDIT_NOTE"
    ) : TransactionType(
        name = creditNoteName, type = creditNoteType
    )

    data class Payment(
        val paymentName: Int = R.string.ledger_payment,
        val paymentType: String = "PAYMENT"
    ) : TransactionType(
        name = paymentName, type = paymentType
    )

    data class Interest(
        val interestName: Int = R.string.interest_amount_ledger,
        val interestType: String = "INTEREST"
    ) : TransactionType(
        name = interestName, type = interestType
    )

    data class FinancingFee(
        val financingFeeName: Int = R.string.financing_fee,
        val financingFeeType: String = "FINANCING_FEE"
    ) : TransactionType(
        name = financingFeeName, type = financingFeeType
    )

    data class DebitNote(
        val financingFeeName: Int = R.string.debit_note,
        val financingFeeType: String = "DEBIT_NOTE"
    ) : TransactionType(
        name = financingFeeName, type = financingFeeType
    )

    data class DebitEntry(
        val financingFeeName: Int = R.string.debit_entry,
        val financingFeeType: String = "DEBIT_ENTRY"
    ) : TransactionType(
        name = financingFeeName, type = financingFeeType
    )
}

@DrawableRes
fun TransactionType.getIcon() = when (this) {
    is TransactionType.Invoice -> R.drawable.ic_ledger_revamp_invoice
    is TransactionType.CreditNote -> R.drawable.ic_ledger_revamp_credit_note
    is TransactionType.Payment -> R.drawable.ic_ledger_revamp_payment
    is TransactionType.Interest -> R.drawable.ic_ledger_revamp_interest
    is TransactionType.FinancingFee -> R.drawable.ic_ledger_revamp_invoice
    is TransactionType.DebitNote -> R.drawable.ledger_debit_note
    is TransactionType.DebitEntry -> R.drawable.ledger_debit_note
}

fun TransactionType.amountColor() = when (this) {
    is TransactionType.Invoice -> Pumpkin120
    is TransactionType.Interest -> Pumpkin120
    is TransactionType.CreditNote -> SeaGreen110
    is TransactionType.Payment -> SeaGreen110
    is TransactionType.FinancingFee -> Pumpkin120
    is TransactionType.DebitNote -> Pumpkin120
    is TransactionType.DebitEntry -> Pumpkin120
}

private fun TransactionType.getAmount(amount: String) = when (this) {
    is TransactionType.Invoice -> "+ $amount"
    is TransactionType.Interest -> "+ $amount"
    is TransactionType.CreditNote -> "- $amount"
    is TransactionType.Payment -> "- $amount"
    is TransactionType.FinancingFee -> "+ $amount"
    is TransactionType.DebitNote -> "+ $amount"
    is TransactionType.DebitEntry -> "+ $amount"
}
