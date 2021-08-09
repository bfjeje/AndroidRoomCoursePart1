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

@Database(entities = {Aluno.class}, version = 3, exportSchema = false)
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
                    }, new Migration(2, 3) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            // create new table with informations
                            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_new` " +
                                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                    "`nome` TEXT, " +
                                    "`telefone` TEXT, " +
                                    "`email` TEXT)");
                            //copy data from old table to new
                            database.execSQL("INSERT INTO Aluno_new (id, nome, telefone, email)" +
                                    "SELECT id, nome, telefone, email FROM Aluno");
                            //delete old table
                            database.execSQL("DROP TABLE Aluno");
                            //Rename new table to old table name
                            database.execSQL("ALTER TABLE Aluno_new RENAME TO Aluno");
                        }
                    })
                    .build();
        }
        return instance;
    }

    public abstract AlunoDAO getRoomAlunoDAO();
}
