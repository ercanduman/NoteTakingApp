package ercanduman.notetakingapp;

/**
 * All app constants will be stored here
 */
public class Configuration {

    // Database vars
    public static final String DATABASE_NAME = "note_database";
    public static final int DATABASE_VERSION = 1;
    public static final String SIMPLE_DATE_FORMAT = "dd.MM.yyyy HH:mm";

    // Logging vars
    public static final boolean isDebugMode = true; // TODO: 04.12.2018 false for production
    public static final String TAG = "EDUMAN";

    // Extras
    public static final String EXTRA_NOTE = "ercanduman.notetakingapp.EXTRA_NOTE";
    public static final int REQUEST_CODE_ADD_NOTE = 9991;

}
