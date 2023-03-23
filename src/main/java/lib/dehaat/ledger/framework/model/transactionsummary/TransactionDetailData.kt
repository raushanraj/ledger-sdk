package lib.dehaat.ledger.framework.model.transactionsummary

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionDetailData(
    @Json(name = "purchase_amount")
    val purchaseAmount: String,
    @Json(name = "payment_amount")
    val paymentAmount: String,
    @Json(name = "interest_amount")
    val interestAmount: String?,
    @Json(name = "abs")
    val abs: ABSData?,
    @Json(name = "total_invoice_amount")
    val totalInvoiceAmount: String?,
    @Json(name = "credit_note_amount")
    val creditNoteAmount: String?,
    @Json(name = "debit_note_amount")
    val debitNodeAmount: String?,
    @Json(name = "total_interest_refund_amount")
    val totalInterestRefundAmount: String?,
    @Json(name = "finance_fee_amount")
    val financingFeeAmount: String?,
    @Json(name = "interest_paid")
    val interestPaid: String?,
    @Json(name = "interest_outstanding")
    val interestOutstanding: String?,
    @Json(name = "debit_entry_amount")
    val debitEntryAmount: String?,
    @Json(name = "net_payment_amount")
    val netPaymentAmount: String
)

@JsonClass(generateAdapter = true)
data class ABSData(
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "last_move_scheme")
    val lastMoveScheme: String?,
    @Json(name = "show_banner")
    val showBanner: Boolean?,
    @Json(name = "last_moved_scheme_amount")
    val lastMovedSchemeAmount: String?
)
