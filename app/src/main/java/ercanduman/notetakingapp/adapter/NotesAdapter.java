package ercanduman.notetakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ercanduman.notetakingapp.R;
import ercanduman.notetakingapp.database.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener clickListener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new NoteHolder(view);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAtPosition(int position) {
        return notes.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, final int position) {
        final Note currentNote = notes.get(position);
        holder.title.setText(currentNote.getTitle());
        holder.description.setText(currentNote.getDescription());
        holder.date.setText(currentNote.getDate());
        holder.id.setText(String.valueOf(currentNote.getId()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
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
                        clickListener.onItemClick(notes.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
}