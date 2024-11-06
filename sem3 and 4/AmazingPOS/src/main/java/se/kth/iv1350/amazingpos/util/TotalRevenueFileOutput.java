package se.kth.iv1350.amazingpos.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Logs the total revenue to a file.
 */
public class TotalRevenueFileOutput implements CurrentSaleObserver {
    
    private PrintWriter logFile;
    private double totalRevenue = 0;

    /**
     * Creates a new instance of TotalRevenueFileOutput.
     * 
     * @throws IOException If there is an error opening the log file.
     */
    public TotalRevenueFileOutput() throws IOException {
        logFile = new PrintWriter(new FileWriter("totalRevenue.txt", true));
    }

    /**
     * Adds the sale amount to the total revenue and logs it.
     * 
     * @param totalAmount The amount of the sale.
     */
    @Override
    public void addSaleAmount(double totalAmount) {
        totalRevenue += totalAmount;
        logSaleToFile();
    }

    /**
     * Logs the total revenue to a file.
     */
    private void logSaleToFile() {
        logFile.println("Total Revenue is : " + totalRevenue + " $");
        logFile.flush();
    }
}
