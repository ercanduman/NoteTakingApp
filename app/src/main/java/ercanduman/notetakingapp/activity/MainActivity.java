package ercanduman.notetakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ercanduman.notetakingapp.Configuration;
import ercanduman.notetakingapp.R;
import ercanduman.notetakingapp.adapter.NotesAdapter;
import ercanduman.notetakingapp.database.model.Note;
import ercanduman.notetakingapp.viewmodel.NoteViewModel;

import static ercanduman.notetakingapp.Configuration.EXTRA_NOTE;
import static ercanduman.notetakingapp.Configuration.REQUEST_CODE_ADD_NOTE;
import static ercanduman.notetakingapp.Configuration.isDebugMode;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = Configuration.TAG;
    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true); // adds perfomance to recyclerview
        final NotesAdapter adapter = new NotesAdapter();
        recyclerView.setAdapter(adapter);

        // If you pass this keyword, then the ViewModel will be destroyed after activity destroys.
        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // onChanged method triggered every time LiveData changes. so do not need to call notifyDataSetChanged methods.
                adapter.setNotes(notes);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) { //You can add multiple swipe direction such as ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int itemPosition = viewHolder.getAdapterPosition();
                viewModel.delete(adapter.getNoteAtPosition(itemPosition));
                // Since MainActivity observes LiveData, any changes in the Note table or in ViewModel reflects in RecyclerView immediately
                // Which means we don't need to reload table data to recyclerView or notifyDataSetChanged callbacks

                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
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
        if (id == R.id.action_delete_all_notes) {
            viewModel.deleteAllNotes();
            Toast.makeText(this, "All Notes deleted.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD_NOTE) {
            if (data != null) {
                Note note = data.getParcelableExtra(EXTRA_NOTE);
                if (note != null) {
                    // Since MainActivity observes LiveData, any changes in the Note table reflected in RecyclerView immediately
                    // Which means we don't need to reload table data to recyclerView or notifyDataSetChanged callbacks
                    viewModel.insert(note);
                    if (isDebugMode)
                        Log.d(TAG, "MainActivity.onActivityResult: Note info: " + note.toString());
                    Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (isDebugMode) Log.d(TAG, "MainActivity.onActivityResult: data is null!");
                Toast.makeText(this, "Cannot save, an error occurred...", Toast.LENGTH_SHORT).show();
            }
        } else Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
    }
}
