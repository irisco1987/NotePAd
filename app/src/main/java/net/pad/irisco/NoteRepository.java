package net.pad.irisco;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import net.pad.irisco.NoteDao;
import net.pad.irisco.NoteDataBase;
import net.pad.irisco.NoteModel;

import java.util.List;

/**
 * Created by s.noori on 10/12/2019.
 */

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<NoteModel>> allNote;

    public NoteRepository(Application application) {
        NoteDataBase noteDataBase = NoteDataBase.getInstance(application);
        noteDao = noteDataBase.noteDao();
        allNote = noteDao.getAllNote();
    }

    public void insert(NoteModel note) {
        new insertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(NoteModel note) {
        new updateAsyncTask(noteDao).execute(note);
    }

    public void delete(NoteModel note) {
        new deleteAsyncTask(noteDao).execute(note);
    }

    public void deleteAll() {
        new deleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<NoteModel>> getAllNote() {
        return allNote;
    }


    private static class insertNoteAsyncTask extends AsyncTask<NoteModel, Void, Void> {
        NoteDao noteDao;

        private insertNoteAsyncTask(NoteDao note_Dao) {
            noteDao = note_Dao;
        }

        @Override
        protected Void doInBackground(NoteModel... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<NoteModel, Void, Void> {
        NoteDao noteDao;


        private updateAsyncTask(NoteDao note_Dao) {
            noteDao = note_Dao;
        }

        @Override
        protected Void doInBackground(NoteModel... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<NoteModel, Void, Void> {
        NoteDao noteDao;

        private deleteAsyncTask(NoteDao note_Dao) {
            noteDao = note_Dao;
        }

        @Override
        protected Void doInBackground(NoteModel... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


    private static class deleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        NoteDao noteDao;

        private deleteAllNoteAsyncTask(NoteDao note_Dao) {
            noteDao = note_Dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }


}
