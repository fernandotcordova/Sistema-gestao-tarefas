package dao;

import java.sql.*;

public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/gerenciadorTarefas";
    private static final String USUARIO = "seuUsuarioAqui";
    private static final String SENHA = "suaSenhaAqui";

    // Método de conexão
    public static Connection getConnection() throws SQLException, SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
    

}
