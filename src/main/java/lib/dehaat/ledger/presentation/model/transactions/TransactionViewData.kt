package lib.dehaat.ledger.presentation.model.transactions

data class TransactionViewData(
	val ledgerId: String,
	val type: String,
	val date: Long,
	val amount: String,
	val erpId: String?,
	val locusId: String?,
	val creditNoteReason: String?,
	val paymentMode: String?,
	val source: String,
	val unrealizedPayment: Boolean?,
	val interestEndDate: Long?,
	val interestStartDate: Long?,
	val partnerId: String,
	val sourceNo: String?,
	val fromDate: String?,
	val toDate: String?,
	val adjustmentAmount: Double?,
	val schemeName: String?,
	val paymentModeWithScheme: String?
)
