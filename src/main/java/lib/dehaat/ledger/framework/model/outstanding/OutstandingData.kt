package lib.dehaat.ledger.framework.model.outstanding

import java.math.BigDecimal

data class OutstandingData(
	val showDialog: Boolean,
	val amount: BigDecimal? = null,
	val numberOfDays: String? = null
)
