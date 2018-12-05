package ercanduman.notetakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import ercanduman.notetakingapp.R;
import ercanduman.notetakingapp.database.model.Note;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.NoteHolder> {
    private OnItemClickListener clickListener;

    public NotesAdapter() {
        super(diffUtilCallback);
    }

    private static DiffUtil.ItemCallback<Note> diffUtilCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getDate().equals(newItem.getDate())
                        && oldItem.getTitle().equals(newItem.getTitle())
                        && oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new NoteHolder(view);
    }

    public Note getNoteAtPosition(int position) {
        return getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, final int position) {
        final Note currentNote = getItem(position);
        holder.title.setText(currentNote.getTitle());
        holder.description.setText(currentNote.getDescription());
        holder.date.setText(currentNote.getDate());
        holder.id.setText(String.valueOf(currentNote.getId()));
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView title, description, date, id;

        NoteHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_title);
            description = itemView.findViewById(R.id.list_item_description);
            date = itemView.findViewById(R.id.list_item_date);
            id = itemView.findViewById(R.id.list_item_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && clickListener != null)
                        clickListener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
}