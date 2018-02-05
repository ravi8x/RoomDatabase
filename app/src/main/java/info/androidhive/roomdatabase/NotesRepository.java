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

    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    NotesRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    public void insertNote(Note note) {
        new insertNotesAsync(mNoteDao).execute(note);
    }

    public void updateNote(Note note) {
        new updateNotesAsync(mNoteDao).execute(note);
    }

    public void deleteNote(Note note) {
        new deleteNotesAsync(mNoteDao).execute(note);
    }

    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the below error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

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

    private static class updateNotesAsync extends AsyncTask<Note, Void, Void> {

        private NoteDao mNoteDaoAsync;

        updateNotesAsync(NoteDao noteDao) {
            mNoteDaoAsync = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDaoAsync.update(notes[0]);
            return null;
        }
    }

    private static class deleteNotesAsync extends AsyncTask<Note, Void, Void> {

        private NoteDao mNoteDaoAsync;

        deleteNotesAsync(NoteDao noteDao) {
            mNoteDaoAsync = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDaoAsync.delete(notes[0]);
            return null;
        }
    }
}
