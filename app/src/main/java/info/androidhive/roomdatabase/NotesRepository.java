package info.androidhive.roomdatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import info.androidhive.roomdatabase.model.Note;
import info.androidhive.roomdatabase.model.NoteDao;
import info.androidhive.roomdatabase.model.Tag;
import info.androidhive.roomdatabase.model.TagDao;

/**
 * Created by ravi on 05/02/18.
 */

public class NotesRepository {

    private NoteDao mNoteDao;
    private TagDao mTagDao;
    private LiveData<List<Note>> mAllNotes;

    NotesRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
        mTagDao = db.tagDao();
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

    public void deleteAllNotes() {
        new deleteAllNotesAsync(mNoteDao).execute();
    }

    public List<Tag> getAllTags() throws ExecutionException, InterruptedException {
        return new getAllTagsAsync(mTagDao).execute().get();
    }

    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the below error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

    private static class insertNotesAsync extends AsyncTask<Note, Void, Long> {

        private NoteDao mNoteDaoAsync;

        insertNotesAsync(NoteDao noteDao) {
            mNoteDaoAsync = noteDao;
        }

        @Override
        protected Long doInBackground(Note... notes) {
            long id = mNoteDaoAsync.insert(notes[0]);
            return id;
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

    private static class deleteAllNotesAsync extends AsyncTask<Note, Void, Void> {

        private NoteDao mNoteDaoAsync;

        deleteAllNotesAsync(NoteDao noteDao) {
            mNoteDaoAsync = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDaoAsync.deleteAll();
            return null;
        }
    }

    private static class getAllTagsAsync extends AsyncTask<Note, Void, List<Tag>> {

        private TagDao mTagDaoAsync;

        getAllTagsAsync(TagDao tagDao) {
            mTagDaoAsync = tagDao;
        }

        @Override
        protected List<Tag> doInBackground(Note... notes) {
            return mTagDaoAsync.getAll();
        }
    }
}
