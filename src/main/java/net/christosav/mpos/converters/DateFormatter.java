package net.christosav.mpos.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String format(Date date) {
        return sdf.format(date);
    }
}
