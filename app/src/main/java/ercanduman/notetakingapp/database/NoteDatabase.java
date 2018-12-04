package ercanduman.notetakingapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import ercanduman.notetakingapp.database.model.Note;
import ercanduman.notetakingapp.utils.DatabaseUtils;

import static ercanduman.notetakingapp.Configuration.DATABASE_NAME;
import static ercanduman.notetakingapp.Configuration.DATABASE_VERSION;

@Database(entities = Note.class, version = DATABASE_VERSION)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDao dao();

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
            new DatabaseUtils(instance).execute();
        }
    };
}
