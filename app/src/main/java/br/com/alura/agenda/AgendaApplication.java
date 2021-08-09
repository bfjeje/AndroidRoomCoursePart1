package br.com.alura.agenda;

import android.app.Application;

import androidx.room.Room;

import br.com.alura.agenda.database.AgendaDatabase;
import br.com.alura.agenda.database.dao.RoomAlunoDAO;
import br.com.alura.agenda.model.Aluno;

@SuppressWarnings("WeakerAccess")
public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
    }

    public static String AGENDADB = "agenda.db";

    private void criaAlunosDeTeste() {
        AgendaDatabase database =
                Room.databaseBuilder(this,
                        AgendaDatabase.class,
                        AGENDADB)
                        .build();
        RoomAlunoDAO dao = database.getRoomAlunoDAO();
        dao.salva(new Aluno("Alex", "1122223333", "alex@alura.com.br"));
        dao.salva(new Aluno("Fran", "1122223333", "fran@gmail.com"));
    }
}
