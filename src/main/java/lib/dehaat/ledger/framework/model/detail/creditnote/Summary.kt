package lib.dehaat.ledger.framework.model.detail.creditnote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Summary(
    @Json(name = "amount")
    val amount: String,
    @Json(name = "origin_invoice_no")
    val invoiceNumber: String?,
    @Json(name = "invoice_date")
    val invoiceDate: Long?,
    @Json(name = "timestamp")
    val timestamp: Long,
    @Json(name = "reason")
    val reason: String,
    @Json(name = "scheme_name")
    val schemeName: String?
)
