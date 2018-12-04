package ercanduman.notetakingapp.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import ercanduman.notetakingapp.database.model.Note;

@Dao
public interface DatabaseAccessObject {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM Note")
    void deleteAllNotes();

    @Query("SELECT * FROM Note ORDER BY date DESC")
    LiveData<List<Note>> getAllNotes();
}
