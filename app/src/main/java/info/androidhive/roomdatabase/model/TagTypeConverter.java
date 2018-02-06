package info.androidhive.roomdatabase.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by ravi on 05/02/18.
 */

@Entity
@TypeConverters(value = {TagTypeConverter.class})
public class TagTypeConverter {

    @TypeConverter
    public static List<Tag> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Tag>>() {
        }.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Tag> someObjects) {
        return new Gson().toJson(someObjects);
    }
}
