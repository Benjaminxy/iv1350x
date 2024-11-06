package se.kth.iv1350.amazingpos.view;
import se.kth.iv1350.amazingpos.controller.Controller;
import se.kth.iv1350.amazingpos.util.TotalRevenueFileOutput;
import se.kth.iv1350.amazingpos.model.PaymentResult;
import se.kth.iv1350.amazingpos.model.dto.CustomerDTO;
import se.kth.iv1350.amazingpos.model.exception.OutOfStockException;
import se.kth.iv1350.amazingpos.model.exception.ProductNotFoundException;
import se.kth.iv1350.amazingpos.model.exception.DiscountApplicationException;
import se.kth.iv1350.amazingpos.model.exception.PaymentFailedException;
import se.kth.iv1350.amazingpos.model.exception.DatabaseFailureException;

import java.io.IOException;

/**
 * Handles Customer  interactions and displays information to the customer.
 */
public class View {
    private final Controller controller;
    private ErrorDisplayHandler errorMsgHandler = new ErrorDisplayHandler();

    /**
     * Creates a new instance of View.
     * 
     * @param controller The controller that handles the operations.
     * @throws IOException If there is an error initializing observers.
     */
    public View(Controller controller) throws IOException {
        this.controller = controller;
        controller.addSaleObserver(new TotalRevenueView());
        controller.addSaleObserver(new TotalRevenueFileOutput());
    }

    /**
     * Executes the main interface for user interaction.
     */
    public void executionInterface() {
        try {
            startSale();
            addItemToSale("0005", 1);
            addItemToSale("0003", 1);
            addItemToSale("0005", 1);
            addItemToSale("0003", 2);
            addItemToSale("999", 1); // example the database failure exception
            completeSaleAndProcessPayment(100.0, "cash", "123457");
        } catch (Exception e) {
            errorMsgHandler.displayErrorMessage("An error happend during the transaction.  try again.");
        }
    }

    /**
     * Starts a new sale.
     */
    private void startSale() {
        controller.startNewSale();
    }

    /**
     * Adds an item to the current sale.
     * 
     * @param itemId this is ID of the item to add.
     * @param times thus is number of times to add the item.
     */
    private void addItemToSale(String itemId, int times) {
        for (int i = 0; i < times; i++) {
            try {
                boolean itemAdded = controller.addItemToSale(itemId, 1);
                if (itemAdded) {
                    System.out.println("Item " + itemId + " added successfully.");
                } else {
                    System.out.println("Failed to add item " + itemId + ".");
                }
            } catch (ProductNotFoundException e) {
                errorMsgHandler.displayErrorMessage("Item not found: " + itemId);
            } catch (OutOfStockException e) {
                errorMsgHandler.displayErrorMessage("Not enough stock for item: " + itemId);
            } catch (DatabaseFailureException e) {
                errorMsgHandler.displayErrorMessage("Database error , wait a second...: " + e.getMessage());
            }
        }
    }

    /**
     * Completes the sale and processes the payment.
     * 
     * @param amountPaid The amount paid by the customer.
     * @param paymentMethod The method of payment.
     * @param customerId The ID of the customer.
     */
    private void completeSaleAndProcessPayment(double amountPaid, String paymentMethod, String customerId) {
        try {
            double totalAfterDiscounts = controller.applyDiscounts(customerId);
            CustomerDTO customer = controller.getCustomer(customerId);
            if (customer != null && customer.isMember()) {
                System.out.println("Customer is a member.");
            } else {
                System.out.println("Customer is not a member.");
            }
            System.out.println(String.format("Total after discounts: $%.2f", totalAfterDiscounts));

            PaymentResult result = controller.endSale(amountPaid, paymentMethod, customerId);
            if (result.isSuccessful()) {
                System.out.println(String.format("Payment successful. Change : $%.2f", result.getChange()));
            } else {
                errorMsgHandler.displayErrorMessage("Payment failed . Payment was too low..");
            }
        } catch (PaymentFailedException e) {
            errorMsgHandler.displayErrorMessage("Payment failed. Please try again.");
        } catch (DiscountApplicationException e) {
            errorMsgHandler.displayErrorMessage("Failed to complete the sale. Please try again.");
        }
    }
}
