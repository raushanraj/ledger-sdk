package lib.dehaat.ledger.framework.mapper

import com.dehaat.androidbase.helper.orFalse
import lib.dehaat.ledger.entities.abs.ABSTransactionEntity
import lib.dehaat.ledger.entities.creditlines.CreditLineEntity
import lib.dehaat.ledger.entities.creditsummary.CreditEntity
import lib.dehaat.ledger.entities.creditsummary.CreditSummaryEntity
import lib.dehaat.ledger.entities.creditsummary.InfoEntity
import lib.dehaat.ledger.entities.creditsummary.OverdueEntity
import lib.dehaat.ledger.entities.detail.creditnote.CreditNoteDetailEntity
import lib.dehaat.ledger.entities.detail.creditnote.ProductEntity
import lib.dehaat.ledger.entities.detail.creditnote.ProductsInfoEntity
import lib.dehaat.ledger.entities.detail.creditnote.SummaryEntity
import lib.dehaat.ledger.entities.detail.invoice.InvoiceDetailDataEntity
import lib.dehaat.ledger.entities.detail.invoice.LoanEntity
import lib.dehaat.ledger.entities.detail.invoice.OverdueInfoEntity
import lib.dehaat.ledger.entities.detail.invoice.invoicedownload.InvoiceDownloadDataEntity
import lib.dehaat.ledger.entities.detail.payment.PaymentDetailEntity
import lib.dehaat.ledger.entities.revamp.creditnote.CreditNoteDetailsEntity
import lib.dehaat.ledger.entities.revamp.creditsummary.CreditSummaryEntityV2
import lib.dehaat.ledger.entities.revamp.invoice.CreditNoteEntity
import lib.dehaat.ledger.entities.revamp.invoice.InvoiceDataEntity
import lib.dehaat.ledger.entities.revamp.invoice.ProductEntityV2
import lib.dehaat.ledger.entities.revamp.invoice.ProductsInfoEntityV2
import lib.dehaat.ledger.entities.revamp.invoice.SummaryEntityV2
import lib.dehaat.ledger.entities.revamp.invoicelist.InvoiceListEntity
import lib.dehaat.ledger.entities.revamp.transaction.TransactionEntityV2
import lib.dehaat.ledger.entities.transactions.TransactionEntity
import lib.dehaat.ledger.entities.transactionsummary.ABSEntity
import lib.dehaat.ledger.entities.transactionsummary.TransactionSummaryEntity
import lib.dehaat.ledger.framework.model.abs.ABSTransaction
import lib.dehaat.ledger.framework.model.creditlines.CreditLine
import lib.dehaat.ledger.framework.model.creditlines.CreditLineData
import lib.dehaat.ledger.framework.model.creditsummary.Credit
import lib.dehaat.ledger.framework.model.creditsummary.CreditSummaryData
import lib.dehaat.ledger.framework.model.creditsummary.Info
import lib.dehaat.ledger.framework.model.creditsummary.Overdue
import lib.dehaat.ledger.framework.model.detail.creditnote.CreditNoteDetailData
import lib.dehaat.ledger.framework.model.detail.creditnote.Product
import lib.dehaat.ledger.framework.model.detail.creditnote.ProductsInfo
import lib.dehaat.ledger.framework.model.detail.creditnote.Summary
import lib.dehaat.ledger.framework.model.detail.invoice.InvoiceDetailData
import lib.dehaat.ledger.framework.model.detail.invoice.Loan
import lib.dehaat.ledger.framework.model.detail.invoice.OverdueInfo
import lib.dehaat.ledger.framework.model.detail.invoice.invoicedownload.DownloadInvoiceData
import lib.dehaat.ledger.framework.model.detail.payment.PaymentDetailData
import lib.dehaat.ledger.framework.model.revamp.creditnote.CreditNoteDetailsData
import lib.dehaat.ledger.framework.model.revamp.creditsummary.CreditV2
import lib.dehaat.ledger.framework.model.revamp.download.ResponseLedgerDownload
import lib.dehaat.ledger.framework.model.revamp.invoicedetails.InvoiceDataV2
import lib.dehaat.ledger.framework.model.revamp.invoicelist.InterestInvoice
import lib.dehaat.ledger.framework.model.revamp.transactions.TransactionData
import lib.dehaat.ledger.framework.model.transactions.Transaction
import lib.dehaat.ledger.framework.model.transactions.TransactionsData
import lib.dehaat.ledger.framework.model.transactionsummary.ABSData
import lib.dehaat.ledger.framework.model.transactionsummary.TransactionDetailData
import lib.dehaat.ledger.presentation.ledger.ui.component.orZero
import javax.inject.Inject

typealias NetworkPaymentDetailSummary = lib.dehaat.ledger.framework.model.detail.payment.Summary
typealias EntityPaymentDetailSummary = lib.dehaat.ledger.entities.detail.payment.SummaryEntity
typealias NetworkInvoiceDetailSummary = lib.dehaat.ledger.framework.model.detail.invoice.Summary
typealias EntityInvoiceDetailSummary = lib.dehaat.ledger.entities.detail.invoice.SummaryEntity
typealias NetworkInvoiceDetailProductsInfo = lib.dehaat.ledger.framework.model.detail.invoice.ProductsInfo
typealias EntityInvoiceDetailProductsInfo = lib.dehaat.ledger.entities.detail.invoice.ProductsInfoEntity
typealias NetworkInvoiceDetailProduct = lib.dehaat.ledger.framework.model.detail.invoice.Product
typealias EntityInvoiceDetailProduct = lib.dehaat.ledger.entities.detail.invoice.ProductEntity
typealias ProductsInfoV2 = lib.dehaat.ledger.framework.model.revamp.invoicedetails.ProductsInfo

class LedgerFrameworkMapper @Inject constructor() {

    fun toCreditSummaryDataEntity(creditSummaryData: CreditSummaryData) = with(creditSummaryData) {
        CreditSummaryEntity(
            credit = toCreditSummaryCreditEntity(credit),
            overdue = toCreditSummaryOverDueEntity(overdue),
            info = toCreditSummaryInfoEntity(info),
        )
    }

    fun toCreditSummaryEntity(credit: CreditV2) = with(credit) {
        CreditSummaryEntityV2(
            bufferLimit = bufferLimit,
            creditNoteAmountTillDate = creditNoteAmountTillDate,
            externalFinancierSupported = externalFinancierSupported,
            interestTillDate = interestTillDate,
            minInterestAmountDue = minInterestAmountDue,
            minInterestOutstandingDate = minInterestOutstandingDate,
            minOutstandingAmountDue = minOutstandingAmountDue,
            paymentAmountTillDate = paymentAmountTillDate,
            permanentCreditLimit = permanentCreditLimit,
            purchaseAmountTillDate = purchaseAmountTillDate,
            totalAvailableCreditLimit = totalAvailableCreditLimit,
            totalCreditLimit = totalCreditLimit,
            totalOutstandingAmount = totalOutstandingAmount,
            totalPurchaseAmount = totalPurchaseAmount,
            undeliveredInvoiceAmount = undeliveredInvoiceAmount,
            totalInterestOutstanding = totalInterestOutstanding.orEmpty(),
            totalInterestPaid = totalInterestPaid.orEmpty(),
            minimumRepaymentAmount = minimumRepaymentAmount,
            repaymentDate = repaymentDate,
            overdueAmount = overdueAmount,
            overdueCreditLimit = overdueCreditLimit,
            creditLineStatus = creditLineStatus,
            creditLineSubStatus = creditLineSubStatus,
            agedOutstandingAmount = agedOutstandingAmount,
            repaymentUnblockAmount = repaymentUnblockAmount,
            repaymentUnblockDays = repaymentUnblockDays
        )
    }

    fun toTransactionSummaryDataEntity(
        transactionDetailData: TransactionDetailData
    ) = with(transactionDetailData) {
        TransactionSummaryEntity(
            purchaseAmount = purchaseAmount,
            paymentAmount = paymentAmount,
            interestAmount = interestAmount,
            totalInvoiceAmount = totalInvoiceAmount,
            creditNoteAmount = creditNoteAmount,
            debitNodeAmount = debitNodeAmount,
            totalInterestRefundAmount = totalInterestRefundAmount,
            financingFeeAmount = financingFeeAmount,
            interestPaid = interestPaid,
            interestOutstanding = interestOutstanding,
            debitEntryAmount = debitEntryAmount,
            netPaymentAmount = netPaymentAmount,
            abs = toABSEntity(abs)
        )
    }

    private fun toABSEntity(abs: ABSData?) =
        abs?.run {
            ABSEntity(
                amount.orZero(),
                lastMoveScheme,
                showBanner.orFalse(),
                lastMovedSchemeAmount
            )
        }

    fun toCreditLineDataEntity(data: CreditLineData) = data.creditLines.map {
        toCreditLineEntity(it)
    }

    fun toTransactionsDataEntity(data: TransactionsData) = data.transactions.map {
        toTransactionEntity(it)
    }

    fun toTransactionsEntity(data: TransactionData) = data.transactions.map {
        TransactionEntityV2(
            amount = it.amount,
            creditNoteReason = it.creditNoteReason,
            date = it.date,
            erpId = it.erpId,
            interestEndDate = it.interestEndDate,
            interestStartDate = it.interestStartDate,
            ledgerId = it.ledgerId,
            locusId = it.locusId,
            partnerId = it.partnerId,
            paymentMode = it.paymentMode,
            source = it.source,
            sourceNo = it.sourceNo,
            type = it.type,
            unrealizedPayment = it.unrealizedPayment,
            isInterestSubVented = it.isInterestSubVented,
            fromDate = it.fromDate,
            toDate = it.toDate,
            adjustmentAmount = it.adjustmentAmount,
            schemeName = it.schemeName
        )
    }

    fun toCreditNoteDetailDataEntity(data: CreditNoteDetailData) = with(data) {
        CreditNoteDetailEntity(
            summary = getCreditNoteDetailSummaryEntity(summary),
            productsInfo = getCreditNoteDetailProductInfoEntity(productsInfo),
        )
    }

    fun toCreditNoteDetailsEntity(data: CreditNoteDetailsData) = with(data) {
        CreditNoteDetailsEntity(
            productsInfo = getProductInfoEntityV2(productsInfo),
            summary = with(summary) {
                lib.dehaat.ledger.entities.revamp.creditnote.SummaryEntityV2(
                    amount = amount,
                    invoiceDate = invoiceDate,
                    invoiceNumber = invoiceNumber,
                    reason = reason,
                    timestamp = timestamp
                )
            }
        )
    }

    fun toPaymentDetailDataEntity(data: PaymentDetailData) = with(data) {
        PaymentDetailEntity(
            summary = getPaymentDetailSummaryEntity(summary),
        )
    }

    fun toInvoiceDetailDataEntity(data: InvoiceDetailData) = with(data) {
        InvoiceDetailDataEntity(
            summary = getInvoiceDetailSummaryEntity(summary),
            loans = loans?.map { getInvoiceDetailLoanEntity(it) },
            overdueInfo = getInvoiceDetailOverdueInfoEntity(overdueInfo),
            productsInfo = getInvoiceDetailProductInfoEntity(productsInfo),
        )
    }

    fun toInvoiceDetailEntity(data: InvoiceDataV2) = with(data) {
        InvoiceDataEntity(
            creditNotes = creditNotes.map {
                CreditNoteEntity(
                    it.creditNoteAmount,
                    it.creditNoteDate,
                    it.creditNoteType,
                    it.ledgerId
                )
            },
            productsInfo = getProductInfoEntityV2(productsInfo),
            summary = with(summary) {
                SummaryEntityV2(
                    interestBeingCharged = interestBeingCharged,
                    interestDays = interestDays,
                    interestStartDate = interestStartDate,
                    invoiceAmount = invoiceAmount,
                    invoiceDate = invoiceDate,
                    invoiceId = invoiceId,
                    processingFee = processingFee,
                    totalOutstandingAmount = totalOutstandingAmount,
                    totalInterestCharged = totalInterestCharged,
                    totalInterestPaid = totalInterestPaid,
                    totalInterestOutstanding = totalInterestOutstanding,
                    penaltyAmount = penaltyAmount,
                    invoiceAge = invoiceAge,
                    isInterestSubVented = isInterestSubVented
                )
            }
        )
    }

    private fun getProductInfoEntityV2(
        data: ProductsInfoV2
    ) = with(data) {
        ProductsInfoEntityV2(
            count = count,
            discount = discount,
            gst = gst,
            productList = getProductListV2(productList),
            itemTotal = itemTotal,
            subTotal = subTotal
        )
    }

    private fun getProductListV2(
        productList: List<lib.dehaat.ledger.framework.model.revamp.invoicedetails.Product>
    ) = productList.map {
        ProductEntityV2(
            fname = it.fname,
            name = it.name,
            priceTotal = it.priceTotal,
            priceTotalDiscexcl = it.priceTotalDiscexcl,
            quantity = it.quantity
        )
    }

    fun toInvoiceDownloadDataEntity(data: DownloadInvoiceData) = with(data) {
        InvoiceDownloadDataEntity(
            source = source,
            pdf = pdf,
            fileName = fileName,
            docType = docType
        )
    }

    private fun getPaymentDetailSummaryEntity(data: NetworkPaymentDetailSummary) = with(data) {
        EntityPaymentDetailSummary(
            referenceId = referenceId,
            timestamp = timestamp,
            totalAmount = totalAmount,
            mode = mode,
            principalComponent = principalComponent,
            interestComponent = interestComponent,
            overdueInterestComponent = overdueInterestComponent,
            penaltyComponent = penaltyComponent,
            advanceComponent = advanceComponent,
            paidTo = paidTo,
            belongsToGapl = belongsToGapl,
            schemeName = schemeName
        )
    }

    private fun getInvoiceDetailProductInfoEntity(data: NetworkInvoiceDetailProductsInfo) =
        with(data) {
            EntityInvoiceDetailProductsInfo(
                count = count,
                discount = discount,
                gst = gst,
                itemTotal = itemTotal,
                subTotal = subTotal,
                productList = productList.map {
                    getInvoiceDetailProductEntity(it)
                }
            )
        }

    private fun getInvoiceDetailProductEntity(it: NetworkInvoiceDetailProduct) =
        with(it) {
            EntityInvoiceDetailProduct(
                fname = fname,
                name = name,
                priceTotal = priceTotal,
                priceTotalDiscexcl = priceTotalDiscexcl,
                quantity = quantity
            )
        }

    private fun getInvoiceDetailLoanEntity(data: Loan) = with(data) {
        LoanEntity(
            loanAccountNo = loanAccountNo,
            status = status,
            amount = amount,
            invoiceContributionInLoan = invoiceContributionInLoan,
            totalOutstandingAmount = totalOutstandingAmount,
            principalOutstandingAmount = principalOutstandingAmount,
            interestOutstandingAmount = interestOutstandingAmount,
            penaltyOutstandingAmount = penaltyOutstandingAmount,
            overdueInterestOutstandingAmount = overdueInterestOutstandingAmount,
            disbursalDate = disbursalDate,
            interestFreeEndDate = interestFreeEndDate,
            financier = financier,
            belongsToGapl = belongsToGapl
        )
    }

    private fun getInvoiceDetailSummaryEntity(data: NetworkInvoiceDetailSummary) = with(data) {
        EntityInvoiceDetailSummary(
            amount = amount,
            number = number,
            timestamp = timestamp
        )
    }

    private fun getInvoiceDetailOverdueInfoEntity(data: OverdueInfo?) = with(data) {
        OverdueInfoEntity(
            overdueDate = this?.overdueDate
        )
    }

    private fun getCreditNoteDetailProductInfoEntity(data: ProductsInfo) = with(data) {
        ProductsInfoEntity(
            count = count,
            gst = gst,
            itemTotal = itemTotal,
            subTotal = subTotal,
            productList = productList?.map {
                getCreditNoteDetailProductEntity(it)
            },
            discount = discount
        )
    }

    private fun getCreditNoteDetailProductEntity(it: Product) =
        with(it) {
            ProductEntity(
                fname = fname,
                name = name,
                priceTotal = priceTotal,
                priceTotalDiscexcl = priceTotalDiscexcl,
                quantity = quantity
            )
        }

    private fun getCreditNoteDetailSummaryEntity(data: Summary) = with(data) {
        SummaryEntity(
            amount = amount,
            invoiceNumber = invoiceNumber,
            timestamp = timestamp,
            reason = reason,
            invoiceDate = invoiceDate,
            schemeName = schemeName
        )
    }

    private fun toTransactionEntity(data: Transaction) = with(data) {
        TransactionEntity(
            ledgerId = ledgerId,
            type = type,
            date = date,
            amount = amount,
            erpId = erpId,
            locusId = locusId,
            creditNoteReason = creditNoteReason,
            paymentMode = paymentMode,
            source = source,
            unrealizedPayment = unrealizedPayment,
            interestStartDate = interestStartDate,
            interestEndDate = interestEndDate,
            partnerId = partnerId,
            sourceNo = sourceNo,
            fromDate = fromDate,
            toDate = toDate,
            adjustmentAmount = adjustmentAmount,
            schemeName = schemeName
        )
    }

    private fun toCreditLineEntity(data: CreditLine) = with(data) {
        CreditLineEntity(
            belongsToGapl = belongsToGapl,
            lenderViewName = lenderViewName,
            creditLimit = creditLimit,
            availableCreditLimit = availableCreditLimit,
            totalOutstandingAmount = totalOutstandingAmount,
            principalOutstandingAmount = principalOutstandingAmount,
            interestOutstandingAmount = interestOutstandingAmount,
            overdueInterestOutstandingAmount = overdueInterestOutstandingAmount,
            penaltyOutstandingAmount = penaltyOutstandingAmount,
            advanceAmount = totalAdvanceAmount
        )
    }

    private fun toCreditSummaryInfoEntity(data: Info) = with(data) {
        InfoEntity(
            totalPurchaseAmount = totalPurchaseAmount,
            totalPaymentAmount = totalPaymentAmount,
            undeliveredInvoiceAmount = undeliveredInvoiceAmount
        )
    }

    private fun toCreditSummaryOverDueEntity(data: Overdue) = with(data) {
        OverdueEntity(
            totalOverdueLimit = totalOverdueLimit,
            totalOverdueAmount = totalOverdueAmount,
            minPaymentAmount = minPaymentAmount,
            minPaymentDueDate = minPaymentDueDate
        )
    }

    private fun toCreditSummaryCreditEntity(data: Credit) = with(data) {
        CreditEntity(
            externalFinancierSupported = externalFinancierSupported,
            totalCreditLimit = totalCreditLimit,
            totalAvailableCreditLimit = totalAvailableCreditLimit,
            totalOutstandingAmount = totalOutstandingAmount,
            principalOutstandingAmount = principalOutstandingAmount,
            interestOutstandingAmount = interestOutstandingAmount,
            overdueInterestOutstandingAmount = overdueInterestOutstandingAmount,
            penaltyOutstandingAmount = penaltyOutstandingAmount
        )
    }

    fun toInterestApproachedInvoiceListEntity(
        data: List<InterestInvoice>?
    ) = data?.map {
        InvoiceListEntity(
            amount = it.amount,
            date = it.date,
            interestStartDate = it.interestStartDate,
            interestFreePeriodEndDate = it.interestFreePeriodEndDate,
            ledgerId = it.ledgerId,
            locusId = it.locusId,
            outstandingAmount = it.outstandingAmount,
            partnerId = it.partnerId,
            source = it.source,
            type = it.type,
            interestDays = it.interestDays
        )
    }

    fun toABSTransactionEntityList(transactions: List<ABSTransaction>) = transactions.map {
        toABSTransactionEntity(it)
    }

    private fun toABSTransactionEntity(transaction: ABSTransaction) = with(transaction) {
        ABSTransactionEntity(amount, orderingDate, schemeName)
    }

    fun toLedgerDownloadUrl(response: ResponseLedgerDownload) = response.data.s3Url
}
