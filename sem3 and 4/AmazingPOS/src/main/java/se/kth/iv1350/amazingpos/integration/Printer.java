package se.kth.iv1350.amazingpos.integration;

import se.kth.iv1350.amazingpos.model.Receipt;

/**
 * Handles printing receipts.
 */
public class Printer {
    /**
     * Prints the receipt.
     * 
     * @param receipt The receipt to print.
     */
    public void printReceipt(Receipt receipt) {
        System.out.println(receipt.createReceiptString());
    }
}
