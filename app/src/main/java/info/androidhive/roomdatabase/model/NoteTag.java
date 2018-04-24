package info.androidhive.roomdatabase.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ravi on 09/03/18.
 */

@Entity(tableName = "note_tags", indices = {@Index(value = {"note_id", "tag_id"})},
        foreignKeys = {
                @ForeignKey(
                        entity = Note.class,
                        parentColumns = "id",
                        childColumns = "note_id"
                ),
                @ForeignKey(
                        entity = Tag.class,
                        parentColumns = "id",
                        childColumns = "tag_id"
                )
        })
public class NoteTag {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "note_id")
    int noteId;

    @ColumnInfo(name = "tag_id")
    int tagId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
