package ercanduman.notetakingapp.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import ercanduman.notetakingapp.database.NoteDatabase;
import ercanduman.notetakingapp.database.NoteDatabaseAccessObject;
import ercanduman.notetakingapp.database.model.Note;

public class NoteRepository {
    private NoteDatabaseAccessObject dao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Context context) {
        NoteDatabase database = NoteDatabase.getInstance(context);
        dao = database.dao();
        allNotes = dao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    /**
     * All CRUD operation should be done in background thread.
     * Room database do not allow to do database operation on main thread,
     * so we have to create {@link android.os.AsyncTask classes for each crud operation.
     */
    public void insert(Note note) {
        new InsertNoteAsyncTask(dao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(dao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(dao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(dao).execute();
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDatabaseAccessObject noteDao;

        private InsertNoteAsyncTask(NoteDatabaseAccessObject dao) {
            this.noteDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDatabaseAccessObject noteDao;

        private UpdateNoteAsyncTask(NoteDatabaseAccessObject dao) {
            this.noteDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDatabaseAccessObject noteDao;

        private DeleteNoteAsyncTask(NoteDatabaseAccessObject dao) {
            this.noteDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDatabaseAccessObject noteDao;

        private DeleteAllNotesAsyncTask(NoteDatabaseAccessObject dao) {
            this.noteDao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
