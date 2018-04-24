package info.androidhive.roomdatabase;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.androidhive.roomdatabase.adapter.NotesAdapter;
import info.androidhive.roomdatabase.model.Note;
import info.androidhive.roomdatabase.model.Tag;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private AppDatabase db;
    private NotesRepository notesRepository;
    private RecyclerView recyclerView;
    private NotesAdapter mAdapter;
    private List<Note> noteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inserting note
                final Note note = new Note("Call mother from FAB!");

                List<Tag> tags = new ArrayList<>();
                tags.add(new Tag("Android"));
                tags.add(new Tag("Material Design"));

                notesRepository.insertNote(note);

                List<Tag> tagsFromDb = null;
                try {
                    tagsFromDb = notesRepository.getAllTags();
                    for (Tag tag : tagsFromDb) {
                        Log.e(TAG, "Tag: " + tag.getId() + ", " + tag.getName());
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        mAdapter = new NotesAdapter(noteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        db = AppDatabase.getDatabase(getApplicationContext());
        notesRepository = new NotesRepository(getApplication());

        LiveData<List<Note>> notes = notesRepository.getAllNotes();

        Observer<List<Note>> notesObserver = new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                Log.e(TAG, "onChanged: " + notes.size());
                noteList.clear();
                noteList.addAll(notes);
                mAdapter.notifyDataSetChanged();
            }
        };

        notes.observe(this, notesObserver);

        // updating note
        Note mm = new Note("Don't call brother!");
        mm.setId(1);
        notesRepository.updateNote(mm);

        // notesRepository.deleteAllNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
