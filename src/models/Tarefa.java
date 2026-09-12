package models;

public class Tarefa {
    
    private String descricao;
    private String nome;
    private int dataDeVencimento; 
    private boolean concluida;

    public Tarefa(String descricao, String nome, int dataDeVencimento) {
        this.descricao = descricao;
        this.nome = nome;
        this.dataDeVencimento = dataDeVencimento;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDataDeVencimento() {
        return dataDeVencimento;
    }

    public void setDataDeVencimento(int dataDeVencimento) {
        this.dataDeVencimento = dataDeVencimento;
    }
    
    
    

    @Override
    public String toString() {
        return "======TAREFA=======\n" +
               "Nome: " + nome + "\n" +
               "Descricao: " + descricao + "\n" +
               "Data de vencimento: " + dataDeVencimento + "\n" +
               "Status: " + (concluida ? "Concluida" : "Pendente") + "\n" +
               "==================";
    }
   
    
    
}
