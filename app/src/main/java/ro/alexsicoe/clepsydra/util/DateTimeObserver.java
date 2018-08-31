package ro.alexsicoe.clepsydra.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeObserver implements View.OnClickListener {
    private final Calendar calendar = Calendar.getInstance();
    private final int cYear = calendar.get(Calendar.YEAR);
    private final int cMonth = calendar.get(Calendar.MONTH);
    private final int cDay = calendar.get(Calendar.DAY_OF_MONTH);
    private final int cHour = calendar.get(Calendar.HOUR_OF_DAY);
    private final int cMinute = calendar.get(Calendar.MINUTE);
    private Context mContext;

    public DateTimeObserver(Context context) {
        mContext = context;
    }

    @Override
    public void onClick(final View v) {
        new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(year, month, dayOfMonth, hourOfDay, minute);
                        Date date = calendar.getTime();
                        DateFormat df = new DateUtil(mContext).getDateTimeFormat();
                        ((EditText) v).setText(df.format(date));
                    }
                }, cHour, cMinute, false).show();
            }
        }, cYear, cMonth, cDay).show();
    }
}
