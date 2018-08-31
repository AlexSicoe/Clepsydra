package ro.alexsicoe.clepsydra.util;


import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class DateUtil {
    private static final String template = "MM/dd/yyyy HH:mm";
    private Locale locale;

    public DateUtil(Context context) {
        locale = context.getResources().getConfiguration().locale;
    }

    public DateFormat getDateTimeFormat() {
        return new SimpleDateFormat(template, locale);
    }

    public DateFormat getDateFormat(int defaultPattern) {
        return SimpleDateFormat.getDateInstance(defaultPattern, locale);
    }

    public DateFormat getDateTimeFormat(int datePattern, int timePattern) {
        return SimpleDateFormat.getDateTimeInstance(datePattern, timePattern, locale);
    }
}
