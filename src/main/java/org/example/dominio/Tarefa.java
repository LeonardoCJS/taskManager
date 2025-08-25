package org.example.dominio;

import java.time.LocalDate;
import java.util.UUID;

public class Tarefa {

    private UUID id;
    private String titulo;
    private String descricao;

    private LocalDate dataDeEntrega;

    private Prioridade prioridade;

    private Status status;

    public Tarefa(UUID id, String titulo, String descricao, LocalDate dataDeEntrega, Prioridade prioridade, Status status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataDeEntrega = dataDeEntrega;
        this.prioridade = prioridade;
        this.status = status;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getDataDeEntrega() {
        return dataDeEntrega;
    }

    public void setDataDeEntrega(LocalDate dataDeEntrega) {
        this.dataDeEntrega = dataDeEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
