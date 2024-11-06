package se.kth.iv1350.amazingpos.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Handles the display of error messages.
 */
public class ErrorDisplayHandler {

    /**
     * Displays an error message with a timestamp.
     * 
     * @param message The error message to display.
     */
    public void displayErrorMessage(String message) {
        StringBuilder createErrorMessage = new StringBuilder();
        createErrorMessage.append(makeTime());
        createErrorMessage.append(", ERROR: ");
        createErrorMessage.append(message);
        System.out.println(createErrorMessage);
    }

    /**
     * Creates a timestamp.
     * 
     * @return The current time formatted as a string.
     */
    private String makeTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return now.format(formatter);
    }
}
