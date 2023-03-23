package lib.dehaat.ledger.presentation.model.detail.payment

data class PaymentDetailSummaryViewData(
    val referenceId: String,
    val timestamp: Long,
    val totalAmount: String,
    val mode: String,
    val principalComponent: String?,
    val interestComponent: String?,
    val overdueInterestComponent: String?,
    val penaltyComponent: String?,
    val advanceComponent: String?,
    val paidTo: String?,
    val belongsToGapl: Boolean?,
    val schemeName: String?
)