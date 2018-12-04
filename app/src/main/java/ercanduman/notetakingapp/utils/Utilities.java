package ercanduman.notetakingapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static ercanduman.notetakingapp.Configuration.SIMPLE_DATE_FORMAT;

public class Utilities {

    public static String getSysdate() {
        return new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.getDefault()).format(new Date());
    }
}
