package net.pad.irisco;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by s.noori on 10/13/2019.
 */

public class NoteRepository {

    NoteDao noteDao;
    LiveData<List<NoteModel>> allNote;

    public NoteRepository(Application application) {

        NoteDataBase noteDataBase = NoteDataBase.getInstance(application);
        noteDao = noteDataBase.noteDao();
        allNote = noteDao.getAllNote();
    }


    public void insert(NoteModel note) {
        new insertAsyncTask(noteDao).execute(note);
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

    private static class insertAsyncTask extends AsyncTask<NoteModel, Void, Void> {
        NoteDao noteDao;

        private insertAsyncTask(NoteDao note_dao) {
            noteDao = note_dao;
        }

        @Override
        protected Void doInBackground(NoteModel... noteModels) {
            noteDao.insert(noteModels[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<NoteModel, Void, Void> {
        NoteDao noteDao;

        private updateAsyncTask(NoteDao note_dao) {
            noteDao = note_dao;
        }

        @Override
        protected Void doInBackground(NoteModel... noteModels) {
            noteDao.update(noteModels[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<NoteModel, Void, Void> {
        NoteDao noteDao;

        private deleteAsyncTask(NoteDao note_dao) {
            noteDao = note_dao;
        }

        @Override
        protected Void doInBackground(NoteModel... noteModels) {
            noteDao.delete(noteModels[0]);
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
