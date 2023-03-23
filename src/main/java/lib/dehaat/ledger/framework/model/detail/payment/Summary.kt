package lib.dehaat.ledger.framework.model.detail.payment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Summary(
    @Json(name = "reference_id")
    val referenceId: String,
    @Json(name = "timestamp")
    val timestamp: Long,
    @Json(name = "total_amount")
    val totalAmount: String,
    @Json(name = "mode")
    val mode: String,
    @Json(name = "principal_component")
    val principalComponent: String?,
    @Json(name = "interest_component")
    val interestComponent: String?,
    @Json(name = "overdue_interest_component")
    val overdueInterestComponent: String?,
    @Json(name = "penalty_component")
    val penaltyComponent: String?,
    @Json(name = "advance_component")
    val advanceComponent: String?,
    @Json(name = "paid_to")
    val paidTo: String?,
    @Json(name = "belongs_to_gapl")
    val belongsToGapl: Boolean?,
    @Json(name = "scheme_name")
    val schemeName: String?
)
