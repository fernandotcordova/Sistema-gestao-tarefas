package dao;

import models.Tarefa;

public class TarefaDAO {
    
    private Tarefa[] tarefasCadastradas;
    private int quantidadeTarefas;
    private static final int CAPACIDADE_INICIAL = 10;

    public TarefaDAO() {
        this.tarefasCadastradas = new Tarefa[CAPACIDADE_INICIAL];
        this.quantidadeTarefas = 0;
        
        //pre cadastro
    }
    
    private void modificarVetor(){
        if(quantidadeTarefas == tarefasCadastradas.length){
            Tarefa[] novoVetor = new Tarefa[tarefasCadastradas.length *2];
            
            System.arraycopy(tarefasCadastradas, 0, novoVetor, 0, tarefasCadastradas.length);
            
            this.tarefasCadastradas = novoVetor;
        }
    }
    
    public void adicionarTarefa(Tarefa novaTarefa){
        
        modificarVetor();
        tarefasCadastradas[quantidadeTarefas] = novaTarefa;
        quantidadeTarefas++;
    }
    
    public String listarTarefas(){
        
        String resultado = "";
        
        if(quantidadeTarefas == 0){
            resultado += "Nenhuma tarefa cadastrada.\n";
        } else{
            resultado += "---Lista de tarefas cadastradas---\n";
            for(int i = 0; i < quantidadeTarefas; i++){
                if(tarefasCadastradas[i] != null){
                    resultado += (i+1) + "." + tarefasCadastradas[i].toString() + "\n";
                }
                
            }
        }
        return resultado;
    }
    
    public boolean marcarComoConcluida(String nome){
        
        String nomeTratado = nome.trim();
        
        for(int i = 0; i< quantidadeTarefas; i++){
            if(tarefasCadastradas[i] != null && tarefasCadastradas[i].getNome().equalsIgnoreCase(nomeTratado)){
                tarefasCadastradas[i].setConcluida(true);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean atualizarTarefa(String nome, String novoNome, String novaDescricao, String novaDataVencimento){
        
        String nomeTratado = nome.trim();
        
        
        for(int i = 0; i < quantidadeTarefas; i++){
            if(tarefasCadastradas[i] != null && tarefasCadastradas[i].getNome().equalsIgnoreCase(nomeTratado)){
                if(novoNome != null && !novoNome.trim().isEmpty()){
                    tarefasCadastradas[i].setNome(novoNome.trim());
                }
                
                if(novaDescricao != null && !novaDescricao.trim().isEmpty()){
                    tarefasCadastradas[i].setDescricao(novaDescricao.trim());
                }
                
                if(novaDataVencimento != null && !novaDataVencimento.trim().isEmpty()){
                    try{
                        int dataConvertida = Integer.parseInt(novaDataVencimento.trim());
                        tarefasCadastradas[i].setDataDeVencimento(dataConvertida);
                    } catch(NumberFormatException e){
                        System.err.println("Aviso: Data de vencimento" + novaDataVencimento + "incorreta.");
                    }
                }
                
                return true;
            }
        }
        
        return false;
            }
    
    public String removerTarefa(String nomeParaRemover){
        
        String nomeParaRemoverTratado = nomeParaRemover.trim();
        int indiceParaRemover = -1;
        
        for(int i = 0; i < quantidadeTarefas; i++){
            if(tarefasCadastradas[i] != null && tarefasCadastradas[i].getNome().equalsIgnoreCase(nomeParaRemoverTratado)){
                indiceParaRemover = i;
                break;
            }
        }
        
        if(indiceParaRemover != -1){
            for(int i = indiceParaRemover; i < quantidadeTarefas - 1; i++){
                tarefasCadastradas[i] = tarefasCadastradas[i + 1]; 
            }
            
            tarefasCadastradas[quantidadeTarefas - 1] = null;
            
            quantidadeTarefas--;
            
            return "Tarefa: " + nomeParaRemover + "com sucesso";
        } else{
            return "Erro: Tarefa" + nomeParaRemover + "nao foi encontrado";
        }
    }
    
    public String buscarTarefa(String nome){
        
        String nomeTratado = nome.trim();
        
        for(int i = 0; i < quantidadeTarefas; i++){
            if(tarefasCadastradas[i] != null && tarefasCadastradas[i].getNome().equalsIgnoreCase(nomeTratado)){
                return tarefasCadastradas[i].toString();
            }
        }
        
        return "Nao foi possivel achar essa tarefa com o nome: " + nome;
    }
    
    public String listarTarefasConcluidas(){
        String resultado = "";
        
        int contadorTarefasConcluidas = 0;
        
        resultado += "---Lista de tarefas concluidas---\n";
        
        for(int i = 0; i < quantidadeTarefas; i++){
            if(tarefasCadastradas[i] != null && tarefasCadastradas[i].isConcluida()){
                resultado += (contadorTarefasConcluidas + 1) + "." + tarefasCadastradas[i].toString() + "\n";
                contadorTarefasConcluidas++;
            }
        }
        
        if(contadorTarefasConcluidas == 0){
            resultado += "Nenhuma tarefa concluida cadastrada\n";
        }
        
        return resultado;
    }
    
    public String listarTarefasPendentes(){
        
        String resultado = "";
        
        int contadorTarefasPendentes = 0;
        
        resultado += "Lista de tarefas concluidas---\n";
        
        for(int i = 0; i < quantidadeTarefas; i++){
            if(tarefasCadastradas[i] != null && !tarefasCadastradas[i].isConcluida()){
                resultado += (contadorTarefasPendentes + 1) + "." + tarefasCadastradas[i].toString() + "\n";
                contadorTarefasPendentes++;
            }
        }
        
        if(contadorTarefasPendentes == 0){
            resultado += "Nenhuma tarefa pendente cadastrada\n";
        }
        
        return resultado;
    }
    
    
        }


