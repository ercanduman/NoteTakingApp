package ercanduman.notetakingapp.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import ercanduman.notetakingapp.Configuration;
import ercanduman.notetakingapp.MainApplication;
import ercanduman.notetakingapp.R;
import ercanduman.notetakingapp.database.NoteDao;
import ercanduman.notetakingapp.database.NoteDatabase;
import ercanduman.notetakingapp.database.model.Note;

import static ercanduman.notetakingapp.Configuration.isDebugMode;

/**
 * Read data from CSV files and import to database
 */
public class DatabaseUtils extends AsyncTask<Void, Void, Void> {
    private static final String TAG = Configuration.TAG;
    private NoteDao noteDao;

    public DatabaseUtils(NoteDatabase db) {
        this.noteDao = db.dao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (isDebugMode) Log.d(TAG, "DatabaseUtils.doInBackground: running...");
        importNotes();
        if (isDebugMode) Log.d(TAG, "DatabaseUtils.doInBackground: finished.");
        return null;
    }

    private void importNotes() {
        Log.d(TAG, "importNotes: START");
        int lineNumber = 0;
        InputStream inputStream = MainApplication.getContext().getResources().openRawResource(R.raw.app_data_notes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (isDebugMode) {
                    Log.d(TAG, "\nimportNotes: line number: " + lineNumber);
                    Log.d(TAG, "importNotes: line data: " + line);
                }
                String[] lineStrings = line.split(";");
                Note note = new Note(lineStrings[0], lineStrings[1], lineStrings[2]);
                noteDao.insert(note);
            }
        } catch (IOException e) {
            Log.d(TAG, "importNotes: Error occurred at line number: " + lineNumber);
            e.printStackTrace();
        }
        if (isDebugMode) Log.d(TAG, "importNotes: " + lineNumber + " rows imported.");
        Log.d(TAG, "importNotes: END");
    }
}