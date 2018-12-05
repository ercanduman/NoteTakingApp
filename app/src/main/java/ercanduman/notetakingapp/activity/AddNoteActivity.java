package ercanduman.notetakingapp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ercanduman.notetakingapp.R;

public class AddNoteActivity extends AppCompatActivity {
    private TextView date;
    private EditText title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        date = findViewById(R.id.add_note_date);
        title = findViewById(R.id.add_note_title);
        description = findViewById(R.id.add_note_description);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            // TODO: 05.12.2018 save note here
            Toast.makeText(this, "Will be saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.dialog_empty), Toast.LENGTH_SHORT).show();
            if (titleText.isEmpty()) title.setError("This field cannot be empty!");
            if (descText.isEmpty()) description.setError("This field cannot be empty!");
        }
    }
}
