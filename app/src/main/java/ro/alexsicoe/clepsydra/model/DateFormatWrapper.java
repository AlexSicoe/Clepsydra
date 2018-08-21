package ro.alexsicoe.clepsydra.model;


import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormatWrapper {
    private static final String template = "MM/dd/yyyy HH:mm";
    private static DateFormat format;

    public static DateFormat getFormat() {
        return format;
    }

    public static void setFormat(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        format = new SimpleDateFormat(template, locale);
    }
}
