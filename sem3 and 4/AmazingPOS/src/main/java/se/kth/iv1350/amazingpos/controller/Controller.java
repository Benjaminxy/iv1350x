package se.kth.iv1350.amazingpos.controller;

import java.io.IOException;

import se.kth.iv1350.amazingpos.integration.*;
import se.kth.iv1350.amazingpos.model.*;
import se.kth.iv1350.amazingpos.model.dto.*;
import se.kth.iv1350.amazingpos.model.exception.*;
import se.kth.iv1350.amazingpos.util.CurrentSaleObserver;
import se.kth.iv1350.amazingpos.util.LogHandler;

/**
 * This class handles  control of the POS system.
 */
public class Controller {
    private ReceiptService receiptService;
    private CurrentSale currentSale;
    private ExternaInventorySystem inventory;
    private CustomerManagementSystem customerManagement;
    private DiscountDatabase discounts;
    private ExternalAccountingSystem accounting;
    private Printer printer;
    private Payment paymentService;
    private LogHandler logHandler;

    /**
     * Creates a new instance of the Controller class.
     */
    public Controller() {
        this.inventory = new ExternaInventorySystem();
        this.customerManagement = new CustomerManagementSystem();
        this.discounts = new DiscountDatabase();
        this.accounting = new ExternalAccountingSystem(inventory);
        this.printer = new Printer();
        this.currentSale = new CurrentSale(inventory);
        this.receiptService = new ReceiptService(printer);
        this.paymentService = new Payment();
        try {
            this.logHandler = new LogHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers a new item in the inventory.
     * 
     * @param itemDTO        object representing the item.
     * @param initialQuantity  The initial quantity of product.
     */
    public void registerItem(ItemDTO itemDTO, int initialQuantity) {
        inventory.registerItem(itemDTO, initialQuantity);
    }

    /**
     * Registers a new customer in the pos system.
     * 
     * @param customerDTO Dto representing the customer.
     */
    public void registerCustomer(CustomerDTO customerDTO) {
        customerManagement.registerCustomer(customerDTO);
    }

    /**
     * Starts a new sale.
     */
    public void startNewSale() {
        currentSale.clearSale();
    }

    /**
     * Adds an item to the current sale.
     * 
     * @param itemId   this is ID of the item to add.
     * @param quantity this is the quantity of the item to add.
     * @return true if the item was added successfully, false otherwise.
     * @throws ProductNotFoundException  if the product is not found.
     * @throws OutOfStockException       if the product is out of stock.
     * @throws DatabaseFailureException  if there is a database failure.
     */
    public boolean addItemToSale(String itemId, int quantity) throws ProductNotFoundException, OutOfStockException, DatabaseFailureException {
        return currentSale.addItem(itemId, quantity);
    }

    /**
     * Applies discounts to the current sale based on the customer's ID.
     * 
     * @param customerId this is ID of the customer.
     * @return this is the total amount after discounts is applied.
     * @throws DiscountApplicationException if there is an problem to apply discounts.
     */
    public double applyDiscounts(String customerId) throws DiscountApplicationException {
        CustomerDTO customer = customerManagement.getCustomer(customerId);
        return currentSale.applyDiscounts(customer, discounts, accounting);
    }

    /**
     * Ends the current sale and processes the payment.
     * 
     * @param amountPaid    The amount paid by the customer.
     * @param paymentMethod The payment method used.
     * @param customerId    The ID of the customer.
     * @return The result of the payment.
     * @throws PaymentFailedException if the payment fails.
     */
    public PaymentResult endSale(double amountPaid, String paymentMethod, String customerId) throws PaymentFailedException {
        CustomerDTO customer = customerManagement.getCustomer(customerId);
        PaymentResult result = currentSale.endSale(amountPaid, paymentMethod, customer, paymentService);
        receiptService.printReceipt(currentSale.createTransactionDetails(amountPaid, paymentMethod), result);
        return result;
    }

    /**
     * Retrieves a customer by their ID.
     * 
     * @param customerId The ID of the customer.
     * @return The data transfer object representing the customer.
     */
    public CustomerDTO getCustomer(String customerId) {
        return customerManagement.getCustomer(customerId);
    }

    /**
     *  notified when a sale is completed.
     * 
     * @param observer The observer to add.
     */
    public void addSaleObserver(CurrentSaleObserver observer) {
        currentSale.addObserver(observer);
    }

    
}
