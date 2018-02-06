package info.androidhive.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by ravi on 05/02/18.
 */

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "note")
    String note;

    @TypeConverters(TagTypeConverter.class)
    List<Tag> tags;

    public Note(@NonNull String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
