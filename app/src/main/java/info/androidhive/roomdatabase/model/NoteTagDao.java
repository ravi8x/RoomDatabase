package info.androidhive.roomdatabase.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

/**
 * Created by ravi on 09/03/18.
 */
@Dao
public interface NoteTagDao {
    @Insert
    long insert(NoteTag noteTag);
}
