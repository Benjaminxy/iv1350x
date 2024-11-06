package se.kth.iv1350.amazingpos.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.amazingpos.model.PurchasedItem;
import se.kth.iv1350.amazingpos.model.dto.CustomerDTO;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountDatabaseTest {

    private DiscountDatabase discountDatabase;

    @BeforeEach
    public void setUp() {
        discountDatabase = new DiscountDatabase();
    }

    @Test
    public void testCalculateTotalDiscountsWithMember() {
        PurchasedItem kiwi = new PurchasedItem("1", "Kiwi", 2, 5.0, 0.1);
        PurchasedItem tomato = new PurchasedItem("2", "Tomato", 3, 3.0, 0.1);
        List<PurchasedItem> items = Arrays.asList(kiwi, tomato);
        CustomerDTO memberCustomer = new CustomerDTO("123", true);

        double totalDiscount = discountDatabase.calculateTotalDiscounts(items, memberCustomer);

        // Calculate expected discounts
        double expectedKiwiDiscount = 2 * 5.0 * 1.1 * 0.07;  // 2 kiwis at 5.0 each with 10% VAT and 7% discount
        double expectedTomatoDiscount = 3 * 3.0 * 1.1 * 0.05;  // 3 tomatoes at 3.0 each with 10% VAT and 5% discount
        double totalAfterVAT = (2 * 5.0 * 1.1) + (3 * 3.0 * 1.1);
        double expectedCustomerDiscount = totalAfterVAT * 0.05;  // 5% discount for members
        double expectedTotalDiscount = expectedKiwiDiscount + expectedTomatoDiscount + expectedCustomerDiscount;

        assertEquals(expectedTotalDiscount, totalDiscount, 0.01);
    }

    @Test
    public void testCalculateTotalDiscountsWithNonMember() {
        PurchasedItem kiwi = new PurchasedItem("1", "Kiwi", 2, 5.0, 0.1);
        PurchasedItem tomato = new PurchasedItem("2", "Tomato", 3, 3.0, 0.1);
        List<PurchasedItem> items = Arrays.asList(kiwi, tomato);
        CustomerDTO nonMemberCustomer = new CustomerDTO("123", false);

        double totalDiscount = discountDatabase.calculateTotalDiscounts(items, nonMemberCustomer);

        // Calculate expected discounts
        double expectedKiwiDiscount = 2 * 5.0 * 1.1 * 0.07;  // 2 kiwis at 5.0 each with 10% VAT and 7% discount
        double expectedTomatoDiscount = 3 * 3.0 * 1.1 * 0.05;  // 3 tomatoes at 3.0 each with 10% VAT and 5% discount
        double expectedTotalDiscount = expectedKiwiDiscount + expectedTomatoDiscount;  // No customer discount

        assertEquals(expectedTotalDiscount, totalDiscount, 0.01);
    }

    @Test
    public void testCalculateTotalDiscountsWithNoItems() {
        List<PurchasedItem> items = Arrays.asList();
        CustomerDTO memberCustomer = new CustomerDTO("123", true);

        double totalDiscount = discountDatabase.calculateTotalDiscounts(items, memberCustomer);

        assertEquals(0, totalDiscount, 0.01);
    }
}
