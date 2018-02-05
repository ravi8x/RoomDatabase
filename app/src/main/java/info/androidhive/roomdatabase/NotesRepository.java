package info.androidhive.roomdatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import info.androidhive.roomdatabase.model.Note;
import info.androidhive.roomdatabase.model.NoteDao;

/**
 * Created by ravi on 05/02/18.
 */

public class NotesRepository {

    private NoteDao mNoteAdo;
    private LiveData<List<Note>> mAllNotes;

    NotesRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mNoteAdo = db.noteDao();
        mAllNotes = mNoteAdo.getAllNotes();
    }

    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    public void insertNote(Note note) {
        new insertNotesAsync(mNoteAdo).execute(note);
    }

    private static class insertNotesAsync extends AsyncTask<Note, Void, Void> {

        private NoteDao mNoteDaoAsync;

        insertNotesAsync(NoteDao noteDao) {
            mNoteDaoAsync = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDaoAsync.insert(notes[0]);
            return null;
        }
    }
}
