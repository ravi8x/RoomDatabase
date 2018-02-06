package info.androidhive.roomdatabase.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by ravi on 05/02/18.
 */

@Dao
public interface TagDao {
    @Query("SELECT * FROM tags ORDER BY id DESC")
    List<Tag> getAll();
}
