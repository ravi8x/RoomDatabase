package info.androidhive.roomdatabase.ui.notes;

/**
 * Created by ravi on 05/02/18.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.roomdatabase.R;
import info.androidhive.roomdatabase.db.dao.NoteEntity;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private static final String TAG = NotesAdapter.class.getSimpleName();

    private Context context;
    private List<NoteEntity> noteList;
    private NotesAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.note)
        TextView note;

        @BindView(R.id.dot)
        TextView dot;

        @BindView(R.id.timestamp)
        TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(noteList.get(getLayoutPosition()), getLayoutPosition());
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongClick(noteList.get(getLayoutPosition()), getLayoutPosition());
                    return true;
                }
            });
        }
    }


    public NotesAdapter(Context context, NotesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (noteList != null) {
            NoteEntity note = noteList.get(position);
            holder.note.setText(note.getNote());
            // Displaying dot from HTML character code
            holder.dot.setText(Html.fromHtml("&#8226;"));

            // Changing dot color to random color
            holder.dot.setTextColor(getRandomMaterialColor("400"));

            // Formatting and displaying timestamp
            holder.timestamp.setText(formatDate(note.getTimestamp()));
        }
    }

    @Override
    public int getItemCount() {
        if (noteList != null)
            return noteList.size();
        else
            return noteList.size();
    }

    public void setNoteList(final List<NoteEntity> notes) {
        if (noteList == null) {
            noteList = notes;
            notifyItemRangeInserted(0, noteList.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return noteList.size();
                }

                @Override
                public int getNewListSize() {
                    return notes.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    NoteEntity oldNote = noteList.get(oldItemPosition);
                    NoteEntity newNote = notes.get(newItemPosition);

                    return oldNote.getId() == newNote.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    NoteEntity oldNote = noteList.get(oldItemPosition);
                    NoteEntity newNote = notes.get(newItemPosition);

                    return oldNote.getId() == newNote.getId() && oldNote.getNote().equals(newNote.getNote());
                }
            });
            noteList = notes;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    /**
     * Chooses random color defined in res/array.xml
     */
    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(Date timestamp) {
        try {
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(timestamp);
        } catch (Exception e) {
            // TODO - handle exception
            e.printStackTrace();
        }

        return "";
    }

    public interface NotesAdapterListener {
        void onClick(NoteEntity note, int position);

        void onLongClick(NoteEntity note, int position);
    }
}