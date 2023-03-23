package lib.dehaat.ledger.datasource

import android.content.Context
import lib.dehaat.ledger.R
import lib.dehaat.ledger.initializer.LedgerParentApp
import lib.dehaat.ledger.initializer.LedgerSDK
import lib.dehaat.ledger.initializer.callbacks.LedgerCallBack
import lib.dehaat.ledger.presentation.ledger.revamp.state.credits.outstandingcreditlimit.OutstandingCreditLimitViewState
import lib.dehaat.ledger.presentation.model.creditlines.CreditLineViewData
import lib.dehaat.ledger.presentation.model.creditsummary.CreditSummaryViewData
import lib.dehaat.ledger.presentation.model.creditsummary.CreditViewData
import lib.dehaat.ledger.presentation.model.creditsummary.InfoViewData
import lib.dehaat.ledger.presentation.model.creditsummary.OverdueViewData
import lib.dehaat.ledger.presentation.model.detail.payment.PaymentDetailSummaryViewData
import lib.dehaat.ledger.presentation.model.invoicelist.InvoiceListViewData
import lib.dehaat.ledger.presentation.model.revamp.SummaryViewData
import lib.dehaat.ledger.presentation.model.revamp.transactions.TransactionViewDataV2
import lib.dehaat.ledger.presentation.model.transactions.TransactionViewData

object DummyDataSource {

    private val creditViewData by lazy {
        CreditViewData(
            externalFinancierSupported = true,
            totalCreditLimit = "000001",
            totalAvailableCreditLimit = "-000002",
            totalOutstandingAmount = "000003",
            principalOutstandingAmount = "000004",
            interestOutstandingAmount = "000005",
            overdueInterestOutstandingAmount = "000006",
            penaltyOutstandingAmount = "000007"
        )
    }

    private val infoViewData by lazy {
        InfoViewData(
            totalPurchaseAmount = "000008",
            totalPaymentAmount = "000009",
            undeliveredInvoiceAmount = "000010"
        )
    }
    private val overdueViewData by lazy {
        OverdueViewData(
            totalOverdueLimit = "1000",
            totalOverdueAmount = "₹ 450",
            minPaymentAmount = "1000",
            minPaymentDueDate = 78
        )
    }
    val summaryViewData = SummaryViewData(
        bufferLimit = "100000",
        creditNoteAmountTillDate = "",
        externalFinancierSupported = false,
        interestTillDate = "40000",
        minInterestAmountDue = "",
        minInterestOutstandingDate = 7,
        minOutstandingAmountDue = "100",
        paymentAmountTillDate = "",
        permanentCreditLimit = "",
        purchaseAmountTillDate = "",
        totalAvailableCreditLimit = "",
        totalCreditLimit = "",
        totalOutstandingAmount = "-320000",
        totalPurchaseAmount = "",
        undeliveredInvoiceAmount = "",
        totalInterestOutstanding = "",
        totalInterestPaid = "",
        minimumRepaymentAmount = "",
        isOrderingBlocked = true,
        hideMinimumRepaymentSection = true,
        repaymentDate = "",
        showToolTipInformation = true,
        creditLineStatus = "",
        creditLineSubStatus = "",
        agedOutstandingAmount = "0.0",
        repaymentUnblockDays = 0,
        repaymentUnblockAmount = "0.0",
        isCreditLineOnHold = true
    )

    val creditSummaryViewData by lazy {
        CreditSummaryViewData(
            creditViewData,
            overdueViewData,
            infoViewData,
            isOrderingBlocked = true,
            isCreditLimitExhausted = true,
            isOverdueLimitExhausted = true
        )
    }
    val creditLineViewData by lazy {
        CreditLineViewData(
            belongsToGapl = true,
            lenderViewName = "lenderViewName",
            creditLimit = "000011",
            availableCreditLimit = "000012",
            totalOutstandingAmount = "000013",
            principalOutstandingAmount = "000014",
            interestOutstandingAmount = "000015",
            overdueInterestOutstandingAmount = "000016",
            penaltyOutstandingAmount = "000017",
            advanceAmount = "000018"
        )
    }

    val invoice = InvoiceListViewData(
        amount = "20000",
        date = 6237462923,
        interestStartDate = 623847623,
        interestFreePeriodEndDate = 6234786239,
        ledgerId = "3647",
        locusId = 3444,
        outstandingAmount = "26348",
        partnerId = "26384",
        source = "SAP",
        type = "INVOICE",
        interestDays = 45
    )

    val transactionViewData by lazy {
        TransactionViewData(
            ledgerId = "ledgerId",
            type = "type",
            date = 67384543,
            amount = "000019",
            erpId = "erpId",
            locusId = "locusId",
            creditNoteReason = "creditNoteReason",
            paymentMode = "paymentMode",
            source = "SAP",
            unrealizedPayment = false,
            interestEndDate = 0L,
            interestStartDate = 0L,
            partnerId = "",
            sourceNo = null,
            fromDate = null,
            toDate = null,
            adjustmentAmount = null,
            schemeName = null,
            paymentModeWithScheme = "paymentMode"
        )
    }

    val invoiceTransaction = TransactionViewDataV2(
        amount = "100",
        creditNoteReason = null,
        date = 1658214762,
        erpId = "2022$$0090000169",
        interestEndDate = 1658214762,
        interestStartDate = 1658214762,
        ledgerId = "95",
        locusId = 4,
        partnerId = "0010000654",
        paymentMode = "CASH",
        source = "SAP",
        sourceNo = "",
        type = "INVOICE",
        unrealizedPayment = false,
        fromDate = "45/Dec/2022",
        toDate = "46/Dec/2022",
        adjustmentAmount = 400.00,
        schemeName = null
    )

    val paymentDetailSummaryViewData = PaymentDetailSummaryViewData(
        referenceId = "refId",
        timestamp = 78386423894,
        totalAmount = "1200",
        mode = "CASH",
        principalComponent = null,
        interestComponent = null,
        overdueInterestComponent = null,
        penaltyComponent = null,
        advanceComponent = null,
        paidTo = null,
        belongsToGapl = null,
        schemeName = null
    )

    val outstandingCreditLimitViewState = OutstandingCreditLimitViewState(
        totalOutstandingAmount = "1000",
        totalPurchaseAmount = "2000",
        interestTillDate = "3000",
        paymentAmountTillDate = "5000",
        purchaseAmountTillDate = "4000",
        creditNoteAmountTillDate = "6000"
    )

    private val dbaApp by lazy {
        LedgerParentApp.DBA(
            ledgerCallBack = LedgerCallBack(
                onClickPayNow = {},
                onRevampPayNowClick = {},
                onDownloadInvoiceSuccess = {},
                onPaymentOptionsClick = {},
                downloadInvoiceIntent = { _, _ -> null },
                exceptionHandler = {},
                firebaseScreenLogger = { _, _ -> }
            )
        )
    }

    private val aimsApp by lazy {
        LedgerParentApp.AIMS(
            downloadInvoiceClick = {},
            downloadInvoiceIntent = { _, _ -> null },
            exceptionHandler = {},
            firebaseScreenLogger = { _, _ -> }
        )
    }

    fun initDBA(context: Context) = LedgerSDK.init(
        context,
        dbaApp,
        "bucket",
        R.drawable.ic_info_icon,
        true
    )

    fun initAIMS(context: Context) = LedgerSDK.init(
        context,
        aimsApp,
        "bucket",
        R.drawable.ic_info_icon,
        true
    )
}
