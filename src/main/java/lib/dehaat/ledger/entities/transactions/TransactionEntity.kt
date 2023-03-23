package lib.dehaat.ledger.entities.transactions

data class TransactionEntity(
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
    val fromDate: Long?,
    val toDate: Long?,
    val adjustmentAmount: Double?,
    val schemeName: String?,
)
