package io.paysky.upg.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PaySky-3 on 8/9/2017.
 */

public class DateTimeUtil extends NoProguard {
    public static String getDateTimeLocalTrxn ="";
    public static String getDateTimeLocalTrxn() {

        int arg1 = Calendar.getInstance().get(Calendar.YEAR);
        String arg1s = "" + arg1;
        int random = (int) (Math.random() * 50 + 1);

        arg1s = arg1s.substring(2, 4);
        int arg2 = Calendar.getInstance().get(Calendar.MONTH);
        int arg3 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int arg4 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int arg5 = Calendar.getInstance().get(Calendar.MINUTE);
        int arg6 = Calendar.getInstance().get(Calendar.SECOND);
        int arg7 = Calendar.getInstance().get(Calendar.MILLISECOND);

        arg2 = arg2 + 1;


        return "" + new StringBuilder().append(arg1s).append("")
                .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3))
                .append((arg4 < 10 ? "0" + arg4 : arg4)).append((arg5 < 10 ? "0" + arg5 : arg5))
                .append((arg6 < 10 ? "0" + arg6 : arg6)).append("").append(arg7) + random;
    }

    public static String getDateTimeExpirePayLinkPlusOneDay() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        int arg1 = cal.get(Calendar.YEAR);
        int arg2 = cal.get(Calendar.MONTH) + 1;
        int arg3 = cal.get(Calendar.DAY_OF_MONTH);
        int arg4 = cal.get(Calendar.HOUR_OF_DAY);
        int arg5 = cal.get(Calendar.MINUTE);

        return "" + new StringBuilder().append(arg1).append("")
                .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3))
                .append((arg4 < 10 ? "0" + arg4 : arg4)).append((arg5 < 10 ? "0" + arg5 : arg5));
    }

    public static String getDateTimeNow() {
        if (BuildUtil.isMoamalatApp()) {
            int arg1 = Calendar.getInstance(new Locale("en")).get(Calendar.YEAR);
            int arg2 = Calendar.getInstance(new Locale("en")).get(Calendar.MONTH);
            int arg3 = Calendar.getInstance(new Locale("en")).get(Calendar.DAY_OF_MONTH);
            arg2 = arg2 + 1;

            String nowData;
            nowData = "" + new StringBuilder().append(arg1).append("")
                    .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3));
            return nowData;
        } else {
            int arg1 = Calendar.getInstance().get(Calendar.YEAR);
            int arg2 = Calendar.getInstance().get(Calendar.MONTH);
            int arg3 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            arg2 = arg2 + 1;

            String nowData;
            nowData = "" + new StringBuilder().append(arg1).append("")
                    .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3));
            return nowData;
        }

    }

    public static String getDateTimeFromMonthVpos() {
        if (BuildUtil.isMoamalatApp()) {
            int arg1 = Calendar.getInstance(new Locale("en")).get(Calendar.YEAR);
            int arg2 = Calendar.getInstance(new Locale("en")).get(Calendar.MONTH) + 1;
            int arg3 = Calendar.getInstance(new Locale("en")).get(Calendar.DAY_OF_MONTH);
            arg2 = arg2 + 1;
            String timeFrom;
            if (BuildUtil.isVPOSGroupApp()) {
                Date date = new Date();
                Calendar cal = Calendar.getInstance(new Locale("en"));
                cal.setTime(date);
                cal.add(Calendar.DATE, -3);
                arg1 = cal.get(Calendar.YEAR);
                arg2 = cal.get(Calendar.MONTH) + 1;
                arg3 = cal.get(Calendar.DAY_OF_MONTH);
            }
            timeFrom = "" + new StringBuilder().append(arg1).append("")
                    .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3));
            return timeFrom;
        } else {
            int arg1 = Calendar.getInstance().get(Calendar.YEAR);
            int arg2 = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int arg3 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            arg2 = arg2 + 1;
            String timeFrom;
            if (BuildUtil.isVPOSGroupApp()) {
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, -3);
                arg1 = cal.get(Calendar.YEAR);
                arg2 = cal.get(Calendar.MONTH) + 1;
                arg3 = cal.get(Calendar.DAY_OF_MONTH);
            }
            timeFrom = "" + new StringBuilder().append(arg1).append("")
                    .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3));
            return timeFrom;
        }

    }


    public static String getDateTimeMerchantServices() {
        int arg1 = Calendar.getInstance().get(Calendar.YEAR);
        int arg2 = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int arg3 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        arg2 = arg2 + 1;
        String timeFrom;
        if (BuildUtil.isVPOSGroupApp()) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            arg1 = cal.get(Calendar.YEAR);
            arg2 = cal.get(Calendar.MONTH) + 1;
            arg3 = cal.get(Calendar.DAY_OF_MONTH);
        }
        timeFrom = "" + new StringBuilder().append(arg1).append("")
                .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3));
        return timeFrom;
    }

    public static String getDateTimeFromMonthVposMinusOneDay() {
        int arg1 = Calendar.getInstance().get(Calendar.YEAR);
        int arg2 = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int arg3 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        arg2 = arg2 + 1;
        String timeFrom;
        if (BuildUtil.isVPOSGroupApp()) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            arg1 = cal.get(Calendar.YEAR);
            arg2 = cal.get(Calendar.MONTH) + 1;
            arg3 = cal.get(Calendar.DAY_OF_MONTH);
        }
        timeFrom = "" + new StringBuilder().append(arg1).append("")
                .append((arg2 < 10 ? "0" + arg2 : arg2)).append("").append((arg3 < 10 ? "0" + arg3 : arg3));
        return timeFrom;
    }


    public static String getDateFromString(String DateFrom) {
        if (BuildUtil.isMoamalatApp()) {
            String oneWayTripDate = "";
            SimpleDateFormat input = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            SimpleDateFormat output;
            output = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            try {
                if (DateFrom != null && !DateFrom.isEmpty()) {
                    oneWayTripDate = output.format(input.parse(DateFrom));
                }
            } catch (ParseException ignored) {
                ignored.printStackTrace();
            }
            return oneWayTripDate;
        } else {
            String oneWayTripDate = "";
            SimpleDateFormat input = new SimpleDateFormat("yyyyMMdd",Locale.ENGLISH);
            SimpleDateFormat output;
            if (SessionManager.getInstance().getLang().equals("en")) {
                output = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
            } else {
                output = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
            }
            try {
                if (DateFrom != null && !DateFrom.isEmpty()) {
                    oneWayTripDate = output.format(input.parse(DateFrom));
                }
            } catch (ParseException ignored) {
                ignored.printStackTrace();
            }
            return oneWayTripDate;
        }

    }


    public static String getDateWithHoursFromString(String inputDate) {
        String oneWayTripDate = "";
        SimpleDateFormat input = new SimpleDateFormat("yyyyMMddHHmm", Locale.ENGLISH);
        SimpleDateFormat output;
        if (SessionManager.getInstance().getLang().equals("en")) {
            output = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        } else
            output = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

        try {
            oneWayTripDate = output.format(input.parse(inputDate));

        } catch (ParseException ee) {

        }
        return oneWayTripDate;
    }


    public static Calendar toCalendar(String DateFrom) {
        SimpleDateFormat input = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

        if (DateFrom.isEmpty()) {
            return Calendar.getInstance();
        }

        Calendar cal = null;
        if (BuildUtil.isMoamalatApp()) {
            cal = Calendar.getInstance();
        } else {
            cal = Calendar.getInstance();

        }
        try {
            cal.setTime(input.parse(DateFrom));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }


}
