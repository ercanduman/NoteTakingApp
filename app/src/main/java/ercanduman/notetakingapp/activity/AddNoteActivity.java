package ercanduman.notetakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ercanduman.notetakingapp.Configuration;
import ercanduman.notetakingapp.R;
import ercanduman.notetakingapp.database.model.Note;

import static ercanduman.notetakingapp.Configuration.EXTRA_NOTE;
import static ercanduman.notetakingapp.Configuration.isDebugMode;
import static ercanduman.notetakingapp.utils.Utilities.getSysdate;

public class AddNoteActivity extends AppCompatActivity {
    private static final String TAG = Configuration.TAG;
    private EditText title, description;
    private static Note passedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up (Back) button on the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        String sysdate = getSysdate();
        TextView date = findViewById(R.id.add_note_date);
        title = findViewById(R.id.add_note_title);
        description = findViewById(R.id.add_note_description);
        date.setText(sysdate);

        passedNote = new Note();
        passedNote.setDate(sysdate);

        Intent intent = getIntent();
        if (intent.getExtras() != null && intent.getExtras().containsKey(EXTRA_NOTE)) {
            setTitle(getString(R.string.title_activity_edit_note));
            passedNote = intent.getParcelableExtra(EXTRA_NOTE);
            date.setText(passedNote.getDate());
            title.setText(passedNote.getTitle());
            description.setText(passedNote.getDescription());
            if (isDebugMode)
                Log.d(TAG, "AddNoteActivity.onCreate: Note info: " + passedNote.toString());
        } else {
            setTitle(getString(R.string.title_activity_add_note));
            if (isDebugMode)
                Log.d(TAG, "AddNoteActivity.onCreate: No note passed. New note will be created");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        // Reset errors
        title.setError(null);
        description.setError(null);

        String titleText = title.getText().toString().trim();
        String descText = description.getText().toString().trim();
        if (!titleText.equals("") && !descText.equals("")) {

            if (passedNote == null) {
                if (isDebugMode) Log.d(TAG, "saveNote: passed note is null");
                return;
            }

            passedNote.setTitle(titleText);
            passedNote.setDescription(descText);
            Intent data = new Intent();
            data.putExtra(EXTRA_NOTE, passedNote);
            setResult(RESULT_OK, data);
            finish();

        } else {
            Toast.makeText(this, getString(R.string.dialog_empty), Toast.LENGTH_SHORT).show();
            if (titleText.isEmpty()) title.setError(getString(R.string.dialog_error_empty));
            if (descText.isEmpty()) description.setError(getString(R.string.dialog_error_empty));
        }
    }
}
