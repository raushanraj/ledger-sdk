package lib.dehaat.ledger.entities.detail.creditnote

data class SummaryEntity(
    val amount: String,
    val invoiceNumber: String?,
    val invoiceDate: Long?,
    val timestamp: Long,
    val reason: String,
    val schemeName: String?,
)
