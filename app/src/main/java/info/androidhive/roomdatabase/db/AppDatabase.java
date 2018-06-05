package info.androidhive.roomdatabase.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import info.androidhive.roomdatabase.db.entity.NoteEntity;
import info.androidhive.roomdatabase.db.dao.NoteDao;
import info.androidhive.roomdatabase.db.converter.DateConverter;

/**
 * Created by ravi on 05/02/18.
 */

@Database(entities = {NoteEntity.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

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