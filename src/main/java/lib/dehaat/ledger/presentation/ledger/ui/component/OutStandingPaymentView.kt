package lib.dehaat.ledger.presentation.ledger.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import lib.dehaat.ledger.resources.Error90
import lib.dehaat.ledger.R
import lib.dehaat.ledger.framework.model.outstanding.OutstandingData
import lib.dehaat.ledger.resources.Neutral10
import lib.dehaat.ledger.resources.text12Sp
import lib.dehaat.ledger.resources.textSemiBold14Sp
import lib.dehaat.ledger.util.formatAmount

@Composable
fun OutStandingPaymentView(outstandingData: OutstandingData) {
    Row(
        Modifier
            .fillMaxWidth()
            .aspectRatio(4.86f)
            .background(Error90)
    ) {
        OutStandingText(
            outstandingData,
            Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.lock_payment_view),
            modifier = Modifier.fillMaxHeight(),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
private fun OutStandingText(outstandingData: OutstandingData, modifier: Modifier) = Column(
    modifier.padding(horizontal = 16.dp, vertical = 12.dp)
) {
    Text(
        text = stringResource(R.string.some_features_locked),
        style = textSemiBold14Sp(Neutral10)
    )
    UnpaidOutStandingText(outstandingData)
}

@Composable
fun UnpaidOutStandingText(outstandingData: OutstandingData) {
    val amount = outstandingData.amount?.toString().orEmpty()
    val formattedAmount = remember(amount) { amount.formatAmount() }
    val outStandingText = stringResource(R.string.unpaid_outstanding, formattedAmount)
    Text(
        text = buildAnnotatedString {
            append(outStandingText)
            addStyle(
                style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold),
                start = outStandingText.length - amount.length - 2,
                end = outStandingText.length
            )
            append(" ")
            append(stringResource(R.string.more_than_90_days, outstandingData.numberOfDays ?: "90"))
        },
        modifier = Modifier.padding(top = 2.dp),
        style = text12Sp(Neutral10)
    )
}
