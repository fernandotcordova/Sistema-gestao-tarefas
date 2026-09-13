package sistemadegestaodetarefas;

import dao.CriarTabelas;
import dao.TarefaDAO;
import java.sql.SQLException;
import views.TelaPrincipal;

public class SistemaDeGestaoDeTarefas {

    public static void main(String[] args) throws SQLException {
        CriarTabelas criarTabelas = new CriarTabelas();
        TelaPrincipal telaPrincipal = new TelaPrincipal();
    }
    
}
