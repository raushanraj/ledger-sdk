package lib.dehaat.ledger.presentation.model.revamp.transactions

data class TransactionViewDataV2(
	val amount: String,
	val creditNoteReason: String?,
	val date: Long,
	val erpId: String?,
	val interestEndDate: Long?,
	val interestStartDate: Long?,
	val ledgerId: String,
	val locusId: Int?,
	val partnerId: String,
	val paymentMode: String?,
	val source: String,
	val sourceNo: String?,
	val type: String,
	val unrealizedPayment: Boolean?,
	val fromDate: String?,
	val toDate: String?,
	val adjustmentAmount: Double?,
	val schemeName: String?,
)
