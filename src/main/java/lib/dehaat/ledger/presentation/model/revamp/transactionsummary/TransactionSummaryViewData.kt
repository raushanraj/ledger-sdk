package lib.dehaat.ledger.presentation.model.revamp.transactionsummary

data class TransactionSummaryViewData(
    val purchaseAmount: String,
    val paymentAmount: String,
    val interestAmount: String,
    val abs: ABSViewData?
)

data class ABSViewData(
    val amount: Double,
    val lastMoveScheme: String?,
    val showBanner: Boolean,
    val lastMovedSchemeAmount: String?
)
