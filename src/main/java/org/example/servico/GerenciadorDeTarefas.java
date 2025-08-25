package org.example.servico;

import org.example.dominio.Status;
import org.example.dominio.Tarefa;
import org.example.excecoes.TarefaDuplicadaException;
import org.example.excecoes.TarefaNaoEncontradaException;

import java.time.LocalDate;
import java.util.*;

public  class GerenciadorDeTarefas implements OperacoesDeTarefa {


    // nosso banco de dados temporario, guardar todas as tarefas na memoria
    private final Map<UUID, Tarefa>  tarefas = new LinkedHashMap<>();


    @Override
    public void adicionarTarefa(Tarefa tarefa) {
        //tarefa nao pode ser nula
        if(tarefa == null){
            // lancando excecao
            throw new IllegalArgumentException("tarefa nao pode ser nula");
        }
        //tarefa nao pode existir com mesmo id, id duplicado
        if(tarefas.containsKey(tarefa.getId())){
            throw new TarefaDuplicadaException("tarefa duplicada");
        }
        tarefas.put(tarefa.getId(), tarefa);

    }

    @Override
    public void removerTarefa(UUID id) {
        if(id == null) {
            throw new IllegalArgumentException("id nao pode ser nula");
        }
        Tarefa tarefaRemovida = tarefas.remove(id);
        //tarefaRemovida != null entao tarefa foi removida com sucesso
        if(tarefaRemovida == null){
            //tarefa nao existe
            throw new TarefaNaoEncontradaException("Tarefa nao encontrada Id: " +id);
        }

    }

    @Override
    public void atualizarTarefa(UUID id, Tarefa tarefa) {
        if(id == null || tarefa == null) {
            throw new IllegalArgumentException("id e tarefa nao pode ser nulos");
        }

        Tarefa tarefaAntiga = tarefas.get(id);
        if(tarefaAntiga == null) {
            throw new TarefaNaoEncontradaException("Tarefa do id nao foi encontrada:" + id);
        }
        tarefas.put(id, tarefa);

    }

    @Override
    public List<Tarefa> listarTarefa() {
        return List.copyOf(tarefas.values());
    }

    @Override
    public List<Tarefa> buscarTarefaPorData(LocalDate date) {
        if(date == null) {
            throw  new IllegalArgumentException("A data nao pode ser nula");

        }
        List<Tarefa> resultado = new ArrayList<>();

        for(Tarefa tarefa : tarefas.values()) {
            if(tarefa.getDataDeEntrega().equals(date)) {
                resultado.add(tarefa);
            }
        }
        return resultado;
    }

    @Override
    public List<Tarefa> buscarTarefaPorStatus(Status status) {
        return List.of();
    }

    @Override
    public Map<LocalDate, List<Tarefa>> agruparTarefasPorData() {
        return Map.of();
    }
}
