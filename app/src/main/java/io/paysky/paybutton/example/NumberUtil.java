package io.paysky.paybutton.example;

import java.math.BigInteger;

import io.paysky.util.NoProguard;

public class NumberUtil extends NoProguard {

    public static String maskNumber(String number, String mask) {
        int index = 0;
        StringBuilder masked = new StringBuilder();
        try {
            for (int i = 0; i < mask.length(); i++) {
                char c = mask.charAt(i);
                if (c == '#') {
                    masked.append(number.charAt(index));
                    index++;
                } else if (c == 'x') {
                    masked.append(c);
                    index++;
                } else {
                    masked.append(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return masked.toString();
    }

    public static String generateRandomNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        while (index < 10) {
            stringBuilder.append(1 + (int) (Math.random() * 49));
            index++;
        }
        return stringBuilder.toString();
    }

    public static int formatPaymentAmountToServer(String payAmount) {

            payAmount = payAmount.replaceAll("\\D+", "");
            payAmount = payAmount.replaceAll(",", "");
            return Integer.parseInt(payAmount);

    }

    public static BigInteger formatPaymentAmountToServerBigInteger(String payAmount) {
        payAmount = payAmount.replaceAll("\\D+", "");
        payAmount = payAmount.replaceAll(",", "");
        return new BigInteger(payAmount);
    }

    public static String removeCommaInString(String title) {
        return title.replace(',', ' ').replaceAll("[\\s+.]", "");
    }

}

