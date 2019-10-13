package net.pad.irisco;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by s.noori on 10/13/2019.
 */

public class NoteViewModel extends AndroidViewModel {
    NoteRepository noteRepository;
    LiveData<List<NoteModel>> allNote;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNote = noteRepository.getAllNote();
    }

    public void insert(NoteModel noteModel) {
        noteRepository.insert(noteModel);
    }

    public void update(NoteModel noteModel) {
        noteRepository.update(noteModel);
    }

    public void delete(NoteModel noteModel) {
        noteRepository.delete(noteModel);
    }

    public void deleteAll() {
        noteRepository.deleteAll();
    }

    public LiveData<List<NoteModel>> getAllNote() {
        return allNote;

    }


}
