package se.kth.iv1350.amazingpos.integration;

import se.kth.iv1350.amazingpos.model.Receipt;
import se.kth.iv1350.amazingpos.model.PaymentResult;
import se.kth.iv1350.amazingpos.model.TransactionDetails;

/**
 * Handles receipt printing.
 */
public class ReceiptService {
    private Printer printer;

    /**
     * Creates a new instance of ReceiptService.
     * 
     * @param printer The printer to use.
     */
    public ReceiptService(Printer printer) {
        this.printer = printer;
    }

    /**
     * Prints the receipt if the payment is successful.
     * 
     * @param details The transaction details.
     * @param result The payment result.
     */
    public void printReceipt(TransactionDetails details, PaymentResult result) {
        if (result.isSuccessful()) {
            printer.printReceipt(new Receipt(details));
        }
    }
}
