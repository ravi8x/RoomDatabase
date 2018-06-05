package info.androidhive.roomdatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import info.androidhive.roomdatabase.ui.notes.NoteListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new NoteListFragment(), NoteListFragment.TAG)
                    .commitNow();
        }
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        NoteListFragment fragment = (NoteListFragment) getSupportFragmentManager().findFragmentByTag(NoteListFragment.TAG);
        if (fragment != null) {
            fragment.showNoteDialog(false, null, -1);
        }
    }
}
