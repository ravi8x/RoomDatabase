package info.androidhive.roomdatabase.adapter;

/**
 * Created by ravi on 05/02/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.roomdatabase.R;
import info.androidhive.roomdatabase.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<Note> notes;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.note)
        public TextView note;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public NotesAdapter(List<Note> moviesList) {
        this.notes = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note movie = notes.get(position);
        holder.note.setText(movie.getNote());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
