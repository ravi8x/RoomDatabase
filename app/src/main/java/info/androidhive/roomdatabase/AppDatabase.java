package info.androidhive.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import info.androidhive.roomdatabase.model.Note;
import info.androidhive.roomdatabase.model.NoteDao;
import info.androidhive.roomdatabase.model.NoteTag;
import info.androidhive.roomdatabase.model.NoteTagDao;
import info.androidhive.roomdatabase.model.Tag;
import info.androidhive.roomdatabase.model.TagDao;

/**
 * Created by ravi on 05/02/18.
 */

@Database(entities = {Note.class, Tag.class, NoteTag.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    public abstract TagDao tagDao();

    public abstract NoteTagDao noteTagDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "notes_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}