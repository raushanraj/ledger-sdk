package lib.dehaat.ledger.presentation.ledger.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lib.dehaat.ledger.R
import lib.dehaat.ledger.datasource.DummyDataSource
import lib.dehaat.ledger.initializer.LedgerSDK
import lib.dehaat.ledger.initializer.themes.AIMSColors
import lib.dehaat.ledger.initializer.themes.LedgerColors
import lib.dehaat.ledger.presentation.ledger.ui.component.creditSummary.PayNowButton
import lib.dehaat.ledger.presentation.ledger.ui.component.creditSummary.PaymentOptionsButton
import lib.dehaat.ledger.presentation.model.creditsummary.CreditSummaryViewData
import lib.dehaat.ledger.resources.Error90

@Preview(
    name = "Credit Summary AIMS",
    showBackground = true
)
@Composable
private fun CreditSummaryAIMS() {
    DummyDataSource.initAIMS(LocalContext.current)
    CreditSummaryView(
        creditSummaryData = DummyDataSource.creditSummaryViewData,
        ledgerColors = AIMSColors(),
        isLmsActivated = { false },
        onClickTotalOutstandingInfo = { },
        onPayNowClick = { },
        onPaymentOptionsClick = { }
    )
}

@Preview(
    name = "Credit Summary DBA",
    showBackground = true
)
@Composable
private fun CreditSummaryDBA() {
    DummyDataSource.initDBA(LocalContext.current)
    CreditSummaryView(
        creditSummaryData = DummyDataSource.creditSummaryViewData,
        ledgerColors = AIMSColors(),
        isLmsActivated = { false },
        onClickTotalOutstandingInfo = { },
        onPayNowClick = { },
        onPaymentOptionsClick = { }
    )
}

@Composable
fun CreditSummaryView(
    creditSummaryData: CreditSummaryViewData?,
    ledgerColors: LedgerColors,
    isLmsActivated: () -> Boolean?,
    onClickTotalOutstandingInfo: () -> Unit,
    onPayNowClick: () -> Unit,
    onPaymentOptionsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ledgerColors.CreditSummaryPrimaryColor,
                        Color.White
                    )
                )
            )
            .fillMaxWidth()
    ) {
        val outstanding by LedgerSDK.outstandingDataFlow.collectAsState()
        if (outstanding.showDialog) {
            OutStandingPaymentView(outstanding)
        } else if (creditSummaryData?.isOrderingBlocked == true) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Error90)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = stringResource(R.string.ordering_is_blocked),
                color = Color.White
            )
        }

        HeaderTotalOutstanding(
            creditSummaryData = creditSummaryData,
            ledgerColors = ledgerColors,
            isLmsActivated = isLmsActivated,
            onClickTotalOutstandingInfo = onClickTotalOutstandingInfo
        )

        if (LedgerSDK.isDBA) {
            Divider(modifier = Modifier, thickness = 1.dp)

            PayNowButton(
                ledgerColors = ledgerColors,
                onPayNowClick = onPayNowClick
            )
        }

        Divider(thickness = 2.dp, color = ledgerColors.CreditViewHeaderDividerBColor)

        if (LedgerSDK.isDBA) {
            PaymentOptionsButton(ledgerColors, onPaymentOptionsClick)
        }
    }
}
