package lib.dehaat.ledger.entities.transactionsummary

data class TransactionSummaryEntity(
	val purchaseAmount: String,
	val paymentAmount: String,
	val interestAmount: String?,
	val totalInvoiceAmount: String?,
	val creditNoteAmount: String?,
	val debitNodeAmount: String?,
	val totalInterestRefundAmount: String?,
	val financingFeeAmount: String?,
	val interestPaid: String?,
	val interestOutstanding: String?,
	val debitEntryAmount: String?,
	val netPaymentAmount: String,
	val abs: ABSEntity?
)

data class ABSEntity(
	val amount: Double,
	val lastMoveScheme: String?,
	val showBanner: Boolean,
	val lastMovedSchemeAmount: String?
)
