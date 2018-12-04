package ercanduman.notetakingapp;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ercanduman.notetakingapp.database.model.Note;
import ercanduman.notetakingapp.repository.NoteRepository;

/**
 * {@link AndroidViewModel is subclass of {@link androidx.lifecycle.ViewModel}}
 */
public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    /**
     * Application context should be passed to ViewModel. If activity context passed and configuration changed then activity will be destroyed,
     * which means MemoryLeak occurs because there is no activity to reference.
     *
     * @param application
     */
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    /**
     * @Info: All the activities has to reference to ViewModel, not the repository. {@link NoteRepository}
     * So we must create methods for ViewModel that match the repository methods
     */
    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
