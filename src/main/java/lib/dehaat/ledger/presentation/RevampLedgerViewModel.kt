package lib.dehaat.ledger.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.cleanarch.base.entity.result.api.APIResultEntity
import com.dehaat.androidbase.helper.callInViewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import lib.dehaat.ledger.domain.usecases.GetCreditSummaryUseCase
import lib.dehaat.ledger.domain.usecases.GetTransactionSummaryUseCase
import lib.dehaat.ledger.domain.usecases.LedgerDownloadUseCase
import lib.dehaat.ledger.entities.revamp.creditsummary.CreditSummaryEntityV2
import lib.dehaat.ledger.entities.transactionsummary.TransactionSummaryEntity
import lib.dehaat.ledger.presentation.common.BaseViewModel
import lib.dehaat.ledger.presentation.ledger.revamp.state.credits.LedgerViewModelState
import lib.dehaat.ledger.presentation.ledger.revamp.state.credits.TransactionViewModelState
import lib.dehaat.ledger.presentation.ledger.revamp.state.credits.availablecreditlimit.AvailableCreditLimitViewState
import lib.dehaat.ledger.presentation.ledger.revamp.state.credits.outstandingcreditlimit.OutstandingCreditLimitViewState
import lib.dehaat.ledger.presentation.mapper.ViewDataMapper
import lib.dehaat.ledger.presentation.model.transactions.DaysToFilter
import lib.dehaat.ledger.presentation.model.transactions.toStartAndEndDates
import lib.dehaat.ledger.util.processAPIResponseWithFailureSnackBar

@HiltViewModel
class RevampLedgerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCreditSummaryUseCase: GetCreditSummaryUseCase,
    private val getTransactionSummaryUseCase: GetTransactionSummaryUseCase,
    private val ledgerDownloadUseCase: LedgerDownloadUseCase,
    val ledgerAnalytics: LibLedgerAnalytics,
    private val mapper: ViewDataMapper
) : BaseViewModel() {

    val partnerId by lazy { savedStateHandle.get<String>(LedgerConstants.KEY_PARTNER_ID) ?: "" }
    val dcName by lazy { savedStateHandle.get<String>(LedgerConstants.KEY_DC_NAME) ?: "" }

    private val _selectedDaysToFilterEvent = MutableSharedFlow<DaysToFilter>()
    val selectedDaysToFilterEvent: SharedFlow<DaysToFilter> get() = _selectedDaysToFilterEvent

    private val viewModelState = MutableStateFlow(LedgerViewModelState())
    private val transactionsViewModelState = MutableStateFlow(TransactionViewModelState())

    val uiState = viewModelState
        .map { it.toUIState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUIState()
        )

    val transactionUIState = transactionsViewModelState
        .map { it.toUIState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            transactionsViewModelState.value.toUIState()
        )

    var availableCreditLimitViewState: AvailableCreditLimitViewState? = null
        private set

    var outstandingCreditLimitViewState: OutstandingCreditLimitViewState? = null
        private set

    init {
        getCreditSummaryFromServer()
        getTransactionSummaryFromServer()
    }

    fun showFilterBottomSheet() = viewModelState.update {
        it.copy(showFilterSheet = true)
    }

    fun hideFilterBottomSheet() = viewModelState.update {
        it.copy(showFilterSheet = false)
    }

    fun getTransactionSummaryFromServer(daysToFilter: DaysToFilter? = null) = callInViewModelScope {
        callingAPI()
        val dates = daysToFilter?.toStartAndEndDates()
        val response = getTransactionSummaryUseCase.getTransactionSummaryV2(partnerId, dates?.first, dates?.second)
        calledAPI()
        processTransactionSummaryResponse(response)
    }

    private fun processTransactionSummaryResponse(
        result: APIResultEntity<TransactionSummaryEntity?>
    ) = result.processAPIResponseWithFailureSnackBar(::sendFailureEvent) { entity ->
        val transactionSummaryViewData = mapper.toTransactionSummaryViewData(entity)
        val outstandingCalculationUiState = mapper.toOutstandingCalculationViewData(entity)
        transactionsViewModelState.update { ledgerDetailViewModelState ->
            ledgerDetailViewModelState.copy(
                isLoading = false,
                summary = transactionSummaryViewData,
                outstandingCalculationUiState = outstandingCalculationUiState
            )
        }
    }

    private fun getCreditSummaryFromServer() {
        callInViewModelScope {
            callingAPI()
            val response = getCreditSummaryUseCase.getCreditSummaryV2(partnerId)
            calledAPI()
            processCreditSummaryResponse(response)
        }
    }

    private fun processCreditSummaryResponse(
        result: APIResultEntity<CreditSummaryEntityV2?>
    ) = result.processAPIResponseWithFailureSnackBar(::sendFailureEvent) { creditSummaryEntity ->
        val creditSummaryViewData = mapper.toCreditSummaryViewData(creditSummaryEntity)
        availableCreditLimitViewState =
            mapper.toAvailableCreditLimitViewState(creditSummaryEntity)
        outstandingCreditLimitViewState =
            mapper.toOutstandingCreditLimitViewState(creditSummaryEntity)
        viewModelState.update { state ->
            state.copy(
                summaryViewData = creditSummaryViewData,
                isSuccess = true
            )
        }
    }

    private fun sendFailureEvent(message: String) {
        viewModelState.update {
            it.copy(
                isError = true,
                errorMessage = message
            )
        }
    }

    private fun calledAPI() = updateProgressDialog(false)

    private fun callingAPI() = updateProgressDialog(true)

    fun updateProgressDialog(show: Boolean) = viewModelState.update {
        it.copy(isLoading = show)
    }

    fun updateFilter(daysToFilter: DaysToFilter) = callInViewModelScope {
        _selectedDaysToFilterEvent.emit(daysToFilter)
        viewModelState.update {
            it.copy(selectedFilter = daysToFilter)
        }
    }

    fun getSelectedDates(daysToFilter: DaysToFilter) = when (daysToFilter) {
        DaysToFilter.SevenDays -> calculateTime(7)
        DaysToFilter.OneMonth -> calculateTime(30)
        DaysToFilter.ThreeMonth -> calculateTime(90)
        else -> null
    }

    private fun calculateTime(dayCount: Int): Pair<Long, Long> {
        val daysSec = dayCount * 24 * 60 * 60
        val currentDaySec = System.currentTimeMillis() / 1000
        val pastDaySec = currentDaySec.minus(daysSec)
        return Pair(pastDaySec, currentDaySec)
    }

    fun downloadLedger() = callInViewModelScope {
        updateProgressDialog(true)
        ledgerDownloadUseCase(partnerId)
        updateProgressDialog(false)
    }

    fun outstandingPaymentValid() = viewModelState.update {
        val summaryViewData = it.summaryViewData?.copy(
            isOrderingBlocked = false,
            creditLineStatus = null
        )
        it.copy(summaryViewData = summaryViewData)
    }
}
