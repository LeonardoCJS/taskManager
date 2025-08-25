package org.example.servico;

import org.example.dominio.Status;
import org.example.dominio.Tarefa;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OperacoesDeTarefa {


    void adicionarTarefa (Tarefa tarefa);

    void removerTarefa (UUID id);

    void atualizarTarefa (UUID id, Tarefa tarefa);



    List<Tarefa> listarTarefa();

    List<Tarefa> buscarTarefaPorData(LocalDate date);

    List<Tarefa> buscarTarefaPorStatus(Status status);

    Map<LocalDate,List<Tarefa>> agruparTarefasPorData();


}
