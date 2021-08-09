package br.com.alura.agenda.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

@Database(entities = {Aluno.class}, version = 2, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NAME_DATABASE = "agenda.db";
    private static AgendaDatabase instance;

    public static AgendaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AgendaDatabase.class,
                    NAME_DATABASE)
                    .allowMainThreadQueries()
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
                        }
                    })
                    .build();
        }
        return instance;
    }

    public abstract AlunoDAO getRoomAlunoDAO();
}
