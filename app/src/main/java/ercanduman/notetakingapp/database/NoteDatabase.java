package ercanduman.notetakingapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import ercanduman.notetakingapp.database.model.Note;

import static ercanduman.notetakingapp.Configuration.DATABASE_NAME;
import static ercanduman.notetakingapp.Configuration.DATABASE_VERSION;
import static ercanduman.notetakingapp.utils.Utilities.getSysdate;

@Database(entities = Note.class, version = DATABASE_VERSION)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDatabaseAccessObject dao();

    // Synchronized means only thread can access to instance at a time
    // also prevents create two instance at a time
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                        NoteDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration() // TODO: remove this in production and provide migration queries
                        .addCallback(roomCallback)
                        .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDatabaseAccessObject noteDao;

        private PopulateDBAsyncTask(NoteDatabase db) {
            this.noteDao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // TODO: 04.12.2018: if you'd like to import data from another source like csv files, then you can do it here.
            noteDao.insert(new Note("Note title 1", "Description 1", getSysdate()));
            noteDao.insert(new Note("Note title 2", "Description 2", getSysdate()));
            noteDao.insert(new Note("Note title 3", "Description 3", getSysdate()));
            noteDao.insert(new Note("Note title 4", "Description 4", getSysdate()));
            return null;
        }
    }

}
