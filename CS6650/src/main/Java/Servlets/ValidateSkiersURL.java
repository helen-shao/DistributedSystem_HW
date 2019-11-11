package Servlets;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidateSkiersURL {


    protected static boolean hasURL(String urlPath) {
        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) return false;
        return true;
    }

    protected static boolean isGetVertical(String[] urlPath) {
        if (urlPath.length == 4) return true;
        return false;
    }
    protected static boolean isSkierURLValid(String[] urlPath) {
        // sample : [, 1, seasons, 2019, days, 1, skiers, 23]
        if (urlPath.length == 3) {
            // TODO: validate "/skiers/{skierID}/vertical"
//        return validateVertical(urlPath);
            return true;
        } else if (urlPath.length == 8) {
            return validateLiftRide(urlPath);
        }
        return false;
    }

    private static boolean validateVertical(String[] urlPath) {
        String skiers = urlPath[0];
        String skierID = urlPath[1];
        String vertical = urlPath[2];
        return true;
    }

    private static boolean validateLiftRide(String[] urlPath) {
        String seasonID = urlPath[3];
        String dayID = urlPath[5];
        String skierID = urlPath[7];
        if (!validateYear(seasonID)) return false;
        if (!validateDay(dayID)) return false;
        if (!validate32Int(skierID)) return false;
        return true;
    }

    private static Integer strToNum(String str) {
        Integer num = null;
        try {
            num = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            System.out.println("Wrong Format");
        } catch (NullPointerException npe) {
            System.out.println("Null Pointer");
        }
        return num;
    }

    private static boolean validate32Int(String str) {
        if (strToNum(str) == null) return false;
        return true;
    }
    private static boolean validateYear(String str) {
        Integer year = strToNum(str);
        if (year == null) return false;
        return true;
    }
    private static boolean validateDay(String str) {
        Integer day = strToNum(str);
        if (day == null) return false;
        if (day.intValue() < 1 || day.intValue() > 366) return false;
        return true;
    }
}
