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
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import java.util.List;

public class ViewNote extends AppCompatActivity {
    NoteViewModel noteViewModel;
    FloatingActionButton fab_add;
    RecyclerView rec_note;
    int REQUEST_ADD = 1;
    int REQUEST_EDIT = 2;
    String TITLE = "title";
    String DESC = "desc";
    String PRIORITY = "priority";
    String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        fab_add = findViewById(R.id.fab_add);
        rec_note = findViewById(R.id.rc_note);


        final NoteAdapter note_adapter = new NoteAdapter();

        rec_note.setLayoutManager(new LinearLayoutManager(this));
        rec_note.hasFixedSize();
        rec_note.setAdapter(note_adapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNote().observe(this, new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(@Nullable List<NoteModel> noteModels) {
                note_adapter.submitList(noteModels);
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
        }).attachToRecyclerView(rec_note);


        note_adapter.setOnItemClickListener(new NoteAdapter.NoteClickListener() {
            @Override
            public void NoteClick(NoteModel noteModel) {
                Intent intent = new Intent(ViewNote.this, AddEditNote.class);
                intent.putExtra(TITLE, noteModel.getTitle());
                intent.putExtra(DESC, noteModel.getDescription());
                intent.putExtra(PRIORITY, noteModel.getPriority());
                intent.putExtra(ID, noteModel.getId());
                startActivityForResult(intent, REQUEST_EDIT);
            }
        });


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewNote.this, AddEditNote.class);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("djfbhduyfgdfuyd",resultCode+"_"+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_ADD) {
            String title = getIntent().getStringExtra(TITLE);
            String desc = getIntent().getStringExtra(DESC);
            int priority = getIntent().getIntExtra(PRIORITY, 0);

            NoteModel note = new NoteModel(title, desc, priority);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Save", Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_EDIT) {
            String title = getIntent().getStringExtra(TITLE);
            String desc = getIntent().getStringExtra(DESC);
            int priority = getIntent().getIntExtra(PRIORITY, 0);
            int id = getIntent().getIntExtra(ID, -1);
            NoteModel noteModel = new NoteModel(title, desc, priority);
            if (id == -1) {
                Toast.makeText(this, "Note Not Update", Toast.LENGTH_SHORT).show();
                return;
            }
            noteModel.setId(id);
            noteViewModel.update(noteModel);
            Toast.makeText(this, "Note Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not Save", Toast.LENGTH_SHORT).show();
        }


    }
}
