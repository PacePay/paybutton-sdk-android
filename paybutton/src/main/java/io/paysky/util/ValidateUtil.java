package io.paysky.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class ValidateUtil extends NoProguard {

    public static boolean hasContinuousCharacter(String number) {
        return Pattern.compile("(012|123|234|345|456|567|678|789|210|321|432|654|765|876|987)+").matcher(number).lookingAt();
    }

    public static boolean hasDistinctDigits(int number) {

        try {
            int numMask = 0;
            int numDigits = (int) Math.ceil(Math.log10(number + 1));
            for (int digitIdx = 0; digitIdx < numDigits; digitIdx++) {
                int curDigit = (int) (number / Math.pow(10, digitIdx)) % 10;
                int digitMask = (int) Math.pow(2, curDigit);
                if ((numMask & digitMask) > 0) return false;
                numMask = numMask | digitMask;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isTextEmpty(String text){
        return text.isEmpty();
    }


    public static boolean validPassword(String password){
        return password.length()>=4;
    }

    public static boolean validMail(String email) {
        return email.matches("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    public static boolean isAfterToday(int year, int month, int day) {
        Calendar today = Calendar.getInstance();
        Calendar myDate = Calendar.getInstance();

        myDate.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        dateFormat.format(today.getTime());
        dateFormat.format(myDate.getTime());
        if (myDate.before(today)){
            return false;
        }
        return true;
    }

}
