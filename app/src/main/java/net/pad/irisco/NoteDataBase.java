package net.pad.irisco;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;



/**
 * Created by s.noori on 10/13/2019.
 */
@Database(entities = {NoteModel.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDataBase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback RoomCallBack = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsync(instance).execute();
        }
    };

    private static class populateDbAsync extends AsyncTask<Void, Void, Void> {
        NoteDao noteDao;
        private populateDbAsync(NoteDataBase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new NoteModel("first note", "this is firs note", 1));
            noteDao.insert(new NoteModel("first note", "this is firs note", 2));
            return null;
        }
    }
}
