package net.pad.irisco;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class ViewNote extends AppCompatActivity {
    NoteViewModel noteViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        RecyclerView recyclerView = findViewById(R.id.rc_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewNote.this, AddEditNote.class);
                startActivityForResult(intent, 1);
            }
        });


        final NoteAdapter note_adapter = new NoteAdapter();
        recyclerView.setAdapter(note_adapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNote().observe(this, new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(@Nullable List<NoteModel> notes) {
                note_adapter.submitList(notes);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(note_adapter.getNote(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);


        note_adapter.setOnItemClickListener(new NoteAdapter.NoteClickListener() {
            @Override
            public void NoteClick(NoteModel noteModel) {
                Intent intent = new Intent(ViewNote.this, AddEditNote.class);
                intent.putExtra("Title", noteModel.getTitle());
                intent.putExtra("Desc", noteModel.getDescription());
                intent.putExtra("Priority", noteModel.priority);
                intent.putExtra("Id", noteModel.getId());
                startActivityForResult(intent, 2);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewNote.this, AddEditNote.class);
                startActivityForResult(intent, 1);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                noteViewModel.deleteAll();
                Toast.makeText(this, "all notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("Title");
            String desc = data.getStringExtra("Desc");
            int priority = data.getIntExtra("Priority", 0);


            NoteModel note = new NoteModel(title, desc, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Save", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("Title");
            String desc = data.getStringExtra("Desc");
            int priority = data.getIntExtra("Priority", 0);
            int id = data.getIntExtra("Id", -1);
            NoteModel note = new NoteModel(title, desc, priority);
            if (id == -1) {
                Toast.makeText(this, "Note Not Update", Toast.LENGTH_SHORT).show();
                return;
            }
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note Update", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Note Not Save", Toast.LENGTH_SHORT).show();
        }
    }
}
