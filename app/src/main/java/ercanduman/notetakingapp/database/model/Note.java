package ercanduman.notetakingapp.database.model;

import java.sql.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String title;
    private String description;
    private Date date;

    public Note(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Note() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
