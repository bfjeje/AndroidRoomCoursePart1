package br.com.alura.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class AgendaMigrations {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN sobrenome TEXT");
        }
    };
    private static final Migration MIGRATIOIN_2_3 = new Migration(2, 3) {
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
    };
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN momentoDeCadastro INTEGER");
        }
    };
    public static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATIOIN_2_3, MIGRATION_3_4};
}
