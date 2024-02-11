package com.vincent64.earthplugin.util;

import java.util.regex.Pattern;

public class MoneyUtil {
    private static final Pattern amountPattern =
            Pattern.compile("\\d{1,7}(\\.\\d{1,2})?");
    private static final double MAX_AMOUNT = 9_999_999.99d;
    private static final double MIN_AMOUNT = 0.01d;
    private static final Pattern quantityPattern =
            Pattern.compile("\\d{1,3}");
    private static final int MAX_QUANTITY = 256;
    private static final int MIN_QUANTITY = 1;

    public static boolean isValidAmountString(String amountString) {
        //Check if amount matches pattern
        if(!amountPattern.matcher(amountString).matches()) return false;
        //Convert amount to double
        double amount = Double.parseDouble(amountString);
        //Check if amount is within ranges
        return amount >= MIN_AMOUNT && amount <= MAX_AMOUNT;
    }

    public static boolean isValidQuantityString(String quantityString) {
        //Check if quantity matches pattern
        if(!quantityPattern.matcher(quantityString).matches()) return false;
        //Convert quantity to int
        int quantity = Integer.parseInt(quantityString);
        //Check if quantity is within ranges
        return quantity >= MIN_QUANTITY && quantity <= MAX_QUANTITY;
    }
}
