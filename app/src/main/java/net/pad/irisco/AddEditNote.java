package net.pad.irisco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNote extends AppCompatActivity {
    EditText edittext_title, edittext_description;
    NumberPicker priority_picker;

    String TITLE = "title";
    String DESC = "desc";
    String PRIORITY = "priority";
    String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        priority_picker = findViewById(R.id.priority_picker);
        edittext_title = findViewById(R.id.edittext_title);
        edittext_description = findViewById(R.id.edittext_description);
        priority_picker.setMaxValue(10);
        priority_picker.setMinValue(0);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        if (getIntent().hasExtra(ID)) {
            getSupportActionBar().setTitle("Edit Note");
        } else {
            getSupportActionBar().setTitle("Add Note");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
//
//    private void saveNote() {
//        String title = ed_title.getText().toString();
//        String desc = ed_desc.getText().toString();
//        int priority = numberPicker.getValue();
//
//        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
//            Toast.makeText(this, "Please Insert Title And Description", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Intent intent = new Intent();
//        intent.putExtra("Title", title);
//        intent.putExtra("Desc", desc);
//        intent.putExtra("Priority", priority);
//
//        int id = getIntent().getIntExtra("Id", -1);
//        if (id != -1) {
//            intent.putExtra("Id", id);
//        }
//        setResult(RESULT_OK, intent);
//        finish();
//    }




    private void saveNote() {
        String title = edittext_title.getText().toString();
        String desc = edittext_description.getText().toString();
        int priority = priority_picker.getValue();


        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Note Not Save", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(TITLE, title);
        intent.putExtra(DESC, desc);
        intent.putExtra(PRIORITY, priority);
        int id = getIntent().getIntExtra(ID, -1);
        if (id != -1) {
            intent.putExtra(ID, id);
        }
        Log.d("djfbhduyfgdfuyd0",id+"_");

       setResult( RESULT_OK,intent);
        finish();

    }
}
