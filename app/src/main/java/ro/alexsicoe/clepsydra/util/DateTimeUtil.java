package ro.alexsicoe.clepsydra.util;


import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Deprecated
public final class DateTimeUtil {
    private static final String template = "MM/dd/yyyy HH:mm";
    private static DateFormat format;

    private DateTimeUtil() {
    }

    public static DateFormat getFormat() {
        return format;
    }

    public static void setFormat(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        format = new SimpleDateFormat(template, locale);
    }
}
