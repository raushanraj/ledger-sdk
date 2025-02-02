package lib.dehaat.ledger.presentation.ledger.revamp.state.credits

import lib.dehaat.ledger.presentation.ledger.revamp.state.UIState
import lib.dehaat.ledger.presentation.ledger.revamp.state.outstandingcalculation.OutstandingCalculationUiState
import lib.dehaat.ledger.presentation.model.revamp.SummaryViewData
import lib.dehaat.ledger.presentation.model.revamp.transactionsummary.TransactionSummaryViewData
import lib.dehaat.ledger.presentation.model.transactions.DaysToFilter

data class LedgerViewModelState(
    val summaryViewData: SummaryViewData? = null,
    val showFilterSheet: Boolean = false,
    val selectedFilter: DaysToFilter = DaysToFilter.All,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
) {
    fun toUIState() = LedgerUIState(
        summaryViewData = summaryViewData,
        showFilterSheet = showFilterSheet,
        appliedFilter = selectedFilter,
        state = when {
            isSuccess -> UIState.SUCCESS
            isError -> UIState.ERROR(errorMessage)
            isLoading -> UIState.LOADING
            else -> UIState.SUCCESS
        }
    )
}

data class LedgerUIState(
    val summaryViewData: SummaryViewData?,
    val showFilterSheet: Boolean,
    val appliedFilter: DaysToFilter,
    val state: UIState
)

data class TransactionViewModelState(
    val summary: TransactionSummaryViewData? = null,
    val outstandingCalculationUiState: OutstandingCalculationUiState? = null,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
) {
    fun toUIState() = TransactionUIState(
        summaryViewData = summary,
        outstandingCalculationUiState = outstandingCalculationUiState,
        state = when {
            isSuccess -> UIState.SUCCESS
            isError -> UIState.ERROR(errorMessage)
            isLoading -> UIState.LOADING
            else -> UIState.SUCCESS
        }
    )
}

data class TransactionUIState(
    val summaryViewData: TransactionSummaryViewData?,
    val outstandingCalculationUiState: OutstandingCalculationUiState?,
    val state: UIState
)
