package lib.dehaat.ledger.framework.model.transactions

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transaction(
    @Json(name = "ledger_id")
    val ledgerId: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "date")
    val date: Long,
    @Json(name = "amount")
    val amount: String,
    @Json(name = "erp_id")
    val erpId: String?,
    @Json(name = "locus_id")
    val locusId: String?,
    @Json(name = "credit_note_reason")
    val creditNoteReason: String?,
    @Json(name = "payment_mode")
    val paymentMode: String?,
    @Json(name = "source")
    val source: String,
    @Json(name = "unrealized_payment")
    val unrealizedPayment: Boolean?,
    @Json(name = "interest_end_date")
    val interestEndDate: Long?,
    @Json(name = "interest_start_date")
    val interestStartDate: Long?,
    @Json(name = "partner_id")
    val partnerId: String,
    @Json(name = "source_no")
    val sourceNo: String?,
    @Json(name = "from_date")
    val fromDate: Long?,
    @Json(name = "to_date")
    val toDate: Long?,
    @Json(name = "adjustment_amount")
    val adjustmentAmount: Double?,
    @Json(name = "scheme_name")
    val schemeName: String?
)
