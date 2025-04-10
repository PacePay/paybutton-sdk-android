package io.paysky.util;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Locale;


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

//    public static int formatPaymentAmountToServer(String payAmount) {
//        if (SessionManager.getInstance().getEmpData().getDecimalPlace() == DecimalPlaces.THREE_DECIMAL.getDecimalPlace()) {
//            double amount = Double.parseDouble(payAmount.replaceAll(",", ""));
//            DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.US);
//            df.setMaximumFractionDigits(SessionManager.getInstance().getEmpData().getDecimalPlace());
//            df.setMinimumFractionDigits(SessionManager.getInstance().getEmpData().getDecimalPlace());
//            String amountStr = df.format(amount);
//            amountStr = amountStr.replaceAll("\\D+", "");
//            return Integer.parseInt(amountStr);
//        } else {
//            payAmount = payAmount.replaceAll("\\D+", "");
//            payAmount = payAmount.replaceAll(",", "");
//            return Integer.parseInt(payAmount);
//        }
//    }

    public static BigInteger formatPaymentAmountToServerBigInteger(String payAmount) {
        payAmount = payAmount.replaceAll("\\D+", "");
        payAmount = payAmount.replaceAll(",", "");
        return new BigInteger(payAmount);
    }

    public static String removeCommaInString(String title) {
        return title.replace(',', ' ').replaceAll("[\\s+.]", "");
    }

}
