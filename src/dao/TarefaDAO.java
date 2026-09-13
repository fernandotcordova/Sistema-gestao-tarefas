package dao;

import java.util.List;
import models.Tarefa;
import java.sql.*;
import java.util.ArrayList;

public class TarefaDAO {

    private Tarefa[] tarefasCadastradas;
    private int quantidadeTarefas;
    private static final int CAPACIDADE_INICIAL = 10;

    public TarefaDAO() {
        this.tarefasCadastradas = new Tarefa[CAPACIDADE_INICIAL];
        this.quantidadeTarefas = 0;

    }

    public void adicionarTarefa(Tarefa novaTarefa) {

        String sql = """
                     INSERT INTO tarefa (descricao, nome, dataVencimento, concluida) 
                     VALUES (?, ?, ?, ?);
                     """;

        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, novaTarefa.getDescricao());
            stmt.setString(2, novaTarefa.getNome());
            stmt.setInt(3, novaTarefa.getDataDeVencimento());
            stmt.setBoolean(4, novaTarefa.isConcluida());

            stmt.executeUpdate();

            System.out.println("Tarefa adicionada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }

    public List<Tarefa> listarTarefas() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT nome, descricao, dataVencimento, concluida FROM tarefa";

        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int dataVencimento = rs.getInt("dataVencimento");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                boolean concluida = rs.getBoolean("concluida");

                tarefas.add(new Tarefa(descricao, nome, dataVencimento, concluida));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return tarefas;
    }

    public boolean marcarComoConcluida(Tarefa tarefa) {

        String sql = "UPDATE tarefas SET concluida = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, tarefa.isConcluida());
            stmt.setInt(2, tarefa.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao marcar tarefa como concluída");
            return false;
        }
    }

    public boolean atualizarTarefa(Tarefa tarefa) {

        if(tarefa == null || tarefa.getId() <= 0){
            System.err.println("Tarefa inválida ou sem id");
            return false;
        }
        
        String sql = "UPDATE tarefa SET nome = ?, descricao = ?, dataVencimento = ?, concluida = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarefa.getNome());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setInt(3, tarefa.getDataDeVencimento());
            stmt.setBoolean(4, tarefa.isConcluida());
            stmt.setInt(5, tarefa.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tarefa: " + e.getMessage());
            return false;
        }

    }

    public boolean removerTarefa(Tarefa tarefa) {

        if (tarefa == null || tarefa.getId() <= 0) {
            System.err.println("ID da tarefa inválido: " + (tarefa!= null ? tarefa.getId() : "null"));
            return false;
        }

        String sql = "DELETE FROM tarefa WHERE id = ?";

        try (Connection conn = Conexao.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tarefa.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover tarefa: " + e.getMessage());
            return false;
        }
        
        
    }

    public Tarefa buscarTarefa(String nome) {

        if(nome == null || nome.trim().isEmpty()){
            System.out.println("Nome nulo ou vazio");
            return null;
        }
        
        String sql = "SELECT * FROM tarefa WHERE LOWER(nome) = LOWER(?)";

        try (Connection conn = Conexao.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome.trim());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Tarefa tarefa = new Tarefa(
                        rs.getString("descricao"), 
                        rs.getString("nome"), 
                        rs.getInt("datavencimento"), 
                        rs.getBoolean("concluida"),
                        rs.getInt("id")
                );
                
                System.out.println(tarefa);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar tarefa: " + e.getMessage());
        }

        return null;

    }

    public List<Tarefa> listarTarefasConcluidas() throws SQLException {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM tarefas WHERE 'concluida' = ?";

        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, true);
            while (rs.next()) {
                int dataVencimento = rs.getInt("dataVencimento");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                boolean concluida = rs.getBoolean("concluida");

                tarefas.add(new Tarefa(descricao, nome, dataVencimento, concluida));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return tarefas;

    }

    public List<Tarefa> listarTarefasPendentes() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM tarefas WHERE 'concluida' = ?";

        try (Connection conn = Conexao.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, false);
            while (rs.next()) {
                int dataVencimento = rs.getInt("dataVencimento");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                boolean concluida = rs.getBoolean("concluida");

                tarefas.add(new Tarefa(descricao, nome, dataVencimento, concluida));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return tarefas;
    }

}
