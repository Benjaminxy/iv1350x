package se.kth.iv1350.amazingpos.model;

import se.kth.iv1350.amazingpos.integration.ExternaInventorySystem;
import se.kth.iv1350.amazingpos.integration.DiscountDatabase;
import se.kth.iv1350.amazingpos.integration.ExternalAccountingSystem;
import se.kth.iv1350.amazingpos.model.dto.CustomerDTO;
import se.kth.iv1350.amazingpos.model.dto.ItemDTO;
import se.kth.iv1350.amazingpos.model.exception.DatabaseFailureException;
import se.kth.iv1350.amazingpos.model.exception.OutOfStockException;
import se.kth.iv1350.amazingpos.model.exception.ProductNotFoundException;
import se.kth.iv1350.amazingpos.model.exception.DiscountApplicationException;
import se.kth.iv1350.amazingpos.util.CurrentSaleObserver;
import se.kth.iv1350.amazingpos.util.CurrentSaleNotifier;
import se.kth.iv1350.amazingpos.model.exception.PaymentFailedException;
import se.kth.iv1350.amazingpos.util.LogHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the current sale.
 */
public class CurrentSale implements CurrentSaleNotifier {
    private List<PurchasedItem> currentSaleItems;
    private ExternaInventorySystem inventory;
    private double totalAmount = 0;
    private List<CurrentSaleObserver> observers = new ArrayList<>();
    private LogHandler logHandler;

    /**
     * Creates a new instance of CurrentSale.
     * 
     * @param inventory The inventory system to use.
     */
    public CurrentSale(ExternaInventorySystem inventory) {
        this.inventory = inventory;
        this.currentSaleItems = new ArrayList<>();
        try {
            this.logHandler = new LogHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an item to the sale.
     * 
     * @param itemId The ID of the item.
     * @param quantity The quantity of the item.
     * @return true if the item was added successfully.
     * @throws ProductNotFoundException If the item is not found.
     * @throws OutOfStockException If the item is out of stock.
     * @throws DatabaseFailureException If there is a database failure.
     */
    public boolean addItem(String itemId, int quantity) throws ProductNotFoundException, OutOfStockException, DatabaseFailureException {
        try {
            if (itemId.equals("999")) {
                throw new DatabaseFailureException("Database connection error occurred for item ID: " + itemId);
            }

            ItemDTO item = inventory.getItem(itemId);
            if (item == null) {
                throw new ProductNotFoundException(itemId);
            }
            int availableStock = inventory.getAvailableStock(itemId);
            if (availableStock < quantity) {
                throw new OutOfStockException(itemId, availableStock, quantity);
            }
            if (!inventory.updateInventory(itemId, quantity)) {
                throw new OutOfStockException(itemId, availableStock, quantity);
            }
            for (PurchasedItem existingItem : currentSaleItems) {
                if (existingItem.getItemId().equals(itemId)) {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    updateTotalAmount();
                    return true;
                }
            }
            PurchasedItem newItem = new PurchasedItem(itemId, item.getDescription(), quantity, item.getPrice(), item.getVatRate());
            currentSaleItems.add(newItem);
            updateTotalAmount();
            return true;
        } catch (ProductNotFoundException | OutOfStockException | DatabaseFailureException e) {
            logHandler.logException(e);
            throw e;
        }
    }
    /**
     * Adds an item to the sale.
     *.
     */
      private void updateTotalAmount() {
        totalAmount = 0;
        for (PurchasedItem item : currentSaleItems) {
            double totalProductPrice = item.getPrice() * item.getQuantity() * (1 + item.getVatRate());
            totalAmount += totalProductPrice;
        }
    }

    /**
     * Updates the total amount after applying discounts.
     * 
     * @param newTotal The new total amount.
     */
    public void updatePriceAfterDiscounts(double newTotal) {
        this.totalAmount = newTotal;
    }

    /**
     * Gets the total amount of the sale.
     * 
     * @return The total amount.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Clears the current sale.
     */
    public void clearSale() {
        currentSaleItems.clear();
        totalAmount = 0;
    }

    /**
     * Gets the items in the current sale.
     * 
     * @return The list of items.
     */
    public List<PurchasedItem> getCurrentSaleItems() {
        return currentSaleItems;
    }

    /**
     * Calculates the total price after VAT.
     * 
     * @return The total price after VAT.
     */
    public double calculatePriceAfterVAT() {
        double totalPriceAfterVAT = 0;
        for (PurchasedItem item : currentSaleItems) {
            double itemTotalPrice = item.getPrice() * item.getQuantity() * (1 + item.getVatRate());
            totalPriceAfterVAT += itemTotalPrice;
        }
        return totalPriceAfterVAT;
    }

    /**
     * Applies discounts to the sale.
     * 
     * @param customer The customer information.
     * @param discounts The discount database.
     * @param accounting The accounting system.
     * @return The total amount after discounts.
     * @throws DiscountApplicationException If there is an error applying discounts.
     */
    public double applyDiscounts(CustomerDTO customer, DiscountDatabase discounts, ExternalAccountingSystem accounting) throws DiscountApplicationException {
        try {
            double totalBeforeDiscount = getTotalAmount();
            double totalDiscount = discounts.calculateTotalDiscounts(getCurrentSaleItems(), customer);
            double totalAfterDiscount = totalBeforeDiscount - totalDiscount;
            updatePriceAfterDiscounts(totalAfterDiscount);
            accounting.registerSale(getCurrentSaleItems(), totalAfterDiscount);
            return totalAfterDiscount;
        } catch (Exception e) {
            logHandler.logException(e);
            throw new DiscountApplicationException("Failed to apply discounts and complete sale", e);
        }
    }

    /**
     * Ends the sale and processes the payment.
     * 
     * @param amountPaid The amount paid by the customer.
     * @param paymentMethod The payment method used.
     * @param customer The customer information.
     * @param paymentService The payment service.
     * @return The payment result.
     * @throws PaymentFailedException If the payment fails.
     */
    public PaymentResult endSale(double amountPaid, String paymentMethod, CustomerDTO customer, Payment paymentService) throws PaymentFailedException {
        double totalDue = getTotalAmount();
        PaymentResult result = paymentService.processPayment(amountPaid, totalDue, paymentMethod);
        if (result.isSuccessful()) {
            notifyObservers(totalDue);
        }
        return result;
    }

    /**
     * Creates the transaction details.
     * 
     * @param amountPaid The amount paid.
     * @param paymentMethod The payment method.
     * @return The transaction details.
     */
    public TransactionDetails createTransactionDetails(double amountPaid, String paymentMethod) {
        double totalPriceAfterVAT = calculatePriceAfterVAT();
        double totalDiscount = totalPriceAfterVAT - getTotalAmount();
        double totalDiscountPercentage = (totalDiscount / totalPriceAfterVAT) * 100;

        return new TransactionDetails(
                getCurrentSaleItems(),
                getTotalAmount(),
                amountPaid,
                totalDiscount,
                paymentMethod,
                totalPriceAfterVAT,
                totalDiscountPercentage
        );
    }

    /**
     * Adds an observer to be notified when a sale is completed.
     * 
     * @param observer The observer to add.
     */
    @Override
    public void addObserver(CurrentSaleObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all observers about a completed sale.
     * 
     * @param totalAmount The total amount of the completed sale.
     */
    @Override
    public void notifyObservers(double totalAmount) {
        for (CurrentSaleObserver observer : observers) {
            observer.addSaleAmount(totalAmount);
        }
    }
}
