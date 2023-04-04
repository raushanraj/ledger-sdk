package lib.dehaat.ledger.presentation.ledger.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lib.dehaat.ledger.R
import lib.dehaat.ledger.datasource.DummyDataSource
import lib.dehaat.ledger.framework.model.outstanding.OutstandingData
import lib.dehaat.ledger.initializer.LedgerSDK
import lib.dehaat.ledger.presentation.LedgerConstants
import lib.dehaat.ledger.presentation.common.uicomponent.SpaceMedium
import lib.dehaat.ledger.presentation.common.uicomponent.VerticalSpacer
import lib.dehaat.ledger.presentation.ledger.creditlimit.AvailableCreditLimitNudgeScreen
import lib.dehaat.ledger.presentation.model.revamp.SummaryViewData
import lib.dehaat.ledger.resources.Neutral80
import lib.dehaat.ledger.resources.Neutral90
import lib.dehaat.ledger.resources.SeaGreen110
import lib.dehaat.ledger.resources.textCaptionCP1
import lib.dehaat.ledger.resources.textHeadingH3
import lib.dehaat.ledger.resources.textParagraphT1Highlight
import lib.dehaat.ledger.util.getRoundedAmountInRupees
import lib.dehaat.ledger.util.toDoubleOrZero

@Preview(
    showBackground = true,
    name = "LedgerHeaderScreen Preview DBA"
)
@Composable
private fun LedgerHeaderScreenPreview() {
    LedgerHeaderScreen(
        summaryViewData = DummyDataSource.summaryViewData,
        onPayNowClick = {},
        onTotalOutstandingDetailsClick = {},
        onShowInvoiceListDetailsClick = {},
        onOtherPaymentModeClick = {}
    ) {}
}

@Preview(
    showBackground = true,
    name = "LedgerHeaderScreen Preview AIMS"
)
@Composable
private fun LedgerHeaderScreenAIMSPreview() {
    LedgerHeaderScreen(
        summaryViewData = DummyDataSource.summaryViewData,
        onPayNowClick = {},
        onTotalOutstandingDetailsClick = {},
        onShowInvoiceListDetailsClick = {},
        onOtherPaymentModeClick = {}
    ) {}
}

@Composable
fun LedgerHeaderScreen(
    summaryViewData: SummaryViewData?,
    onPayNowClick: () -> Unit,
    onTotalOutstandingDetailsClick: () -> Unit,
    onShowInvoiceListDetailsClick: () -> Unit,
    onOtherPaymentModeClick: () -> Unit,
    outstandingPaymentValid: () -> Unit
) = Column(
    modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()
) {
    val outstanding by LedgerSDK.outstandingDataFlow.collectAsState(
        OutstandingData(false)
    )
    if (outstanding.showDialog) {
        outstandingPaymentValid()
        OutStandingPaymentView(outstanding)
    }
    summaryViewData?.let {
        if (it.totalAvailableCreditLimit.toDoubleOrZero() < 0.0)
            it.minimumRepaymentAmount?.let {
                if (it.toDoubleOrZero() > 0.0) {
                    AvailableCreditLimitNudgeScreen(it.getRoundedAmountInRupees())
                    SpaceMedium()
                }
            }
    }

    summaryViewData?.let {
        if (it.creditLineStatus == LedgerConstants.ON_HOLD) {
            OverduePaymentView(
                it.creditLineSubStatus,
                it.agedOutstandingAmount,
                it.repaymentUnblockDays,
                it.repaymentUnblockAmount
            )
        }
    }

    val totalOutstandingAmount = summaryViewData?.totalOutstandingAmount?.toDoubleOrNull() ?: 0.0
    VerticalSpacer(height = 24.dp)
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = stringResource(id = R.string.total_outstanding),
        style = textParagraphT1Highlight(Neutral90)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = summaryViewData?.totalOutstandingAmount.getRoundedAmountInRupees(),
            style = textHeadingH3(
                textColor = if (totalOutstandingAmount < 0) SeaGreen110 else Neutral80
            )
        )

        ViewDetails(onTotalOutstandingDetailsClick)
    }

    if (totalOutstandingAmount < 0) {
        val outstandingAmount = totalOutstandingAmount * -1
        VerticalSpacer(height = 4.dp)
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(
                id = R.string.your_advance_amount,
                outstandingAmount.toString().getRoundedAmountInRupees()
            ),
            style = textCaptionCP1(Neutral80)
        )
    }

    VerticalSpacer(height = 12.dp)

    summaryViewData?.let {
        RepaymentScreen(
            summaryViewData = it,
            onPayNowClick = onPayNowClick,
            onOtherPaymentModeClick = onOtherPaymentModeClick,
            onShowInvoiceListDetailsClick = onShowInvoiceListDetailsClick
        )
    }
}
