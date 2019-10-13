package net.pad.irisco;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by s.noori on 10/13/2019.
 */
@Entity(tableName = "note_table")
public class NoteModel {

    String title;
    String description;

    @PrimaryKey(autoGenerate = true)
    int id;

    int priority;

    public NoteModel(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public void setId(int id) {
        this.id = id;
    }
}
