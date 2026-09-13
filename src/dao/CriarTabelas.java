package dao;

import java.sql.*;

public class CriarTabelas {

    public CriarTabelas() throws SQLException {
        criarTabelaTarefa();
    }

    
    public static void criarTabelaTarefa() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS tarefa (
                id SERIAL PRIMARY KEY,
                descricao TEXT NOT NULL,
                nome VARCHAR(255) NOT NULL,
                dataVencimento INT,
                concluida BOOLEAN DEFAULT FALSE
            );
        """;

        try (Connection conexao = Conexao.getConnection(); 
             Statement stmt = conexao.createStatement()
            ) 
        {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir na tabela: " + e.getMessage());
        }
    }

}
