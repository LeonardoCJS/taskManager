package org.example;

import org.example.dominio.Prioridade;
import org.example.dominio.Status;
import org.example.dominio.Tarefa;
import org.example.excecoes.TarefaDuplicadaException;
import org.example.servico.GerenciadorDeTarefas;
import org.example.servico.OperacoesDeTarefa;
import org.example.util.DateUtil;

import javax.xml.crypto.Data;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    //atributo pertence a classe
    private static final Scanner scanner = new Scanner(System.in);

    //atributo nao estatico pertence ao objeto
    private final OperacoesDeTarefa gerenciadorTarefas = new GerenciadorDeTarefas();


    public static void main(String[] args) {
        Main aplicacao = new Main();
        aplicacao.rodar();
    }

    private void rodar() {
        boolean sair = false;
        while (!sair) {

            mostrarMenu();
            String escolha = scanner.nextLine().trim();
            switch (escolha) {
                case "1" -> adicionarTarefa();
                case "2" -> listarTarefas();
                case "3" -> atualizarTarefa();
                case "4" -> removerTarefa();
                case "0" -> sair = true;
                default -> System.out.println("Opção Inválida");
            }
        }
        System.out.println("Fim do programa");

    }

    private void mostrarMenu() {
        System.out.println("====== Gerenciador de tarefas ======");
        System.out.println("1. Adicionar tarefa");
        System.out.println("2. listar todas as tarefas");
        System.out.println("3. Atualizar a tarefa");
        System.out.println("4. Remover tarefa");
        //faltar 5 agrupar tarefas por data
        System.out.println("0. Sair");
        System.out.println("Selecione uma opção:");
    }

    private void adicionarTarefa() {

        try {
            System.out.println("Digite o titulo:");
            String titulo = scanner.nextLine();
            System.out.println("Digite o descricao:");
            String descricao = scanner.nextLine();
            System.out.println("Digite a data da entrega (dd/MM/yyyy): ");
            String dataEntregaInputUsuario = scanner.nextLine();
            LocalDate dataEntrega;
            try {
                dataEntrega = DateUtil.converterData(dataEntregaInputUsuario);
            } catch (DateTimeParseException e) {
                System.out.println("Formato da data está invalido, utilize dd/MM/yyyy");
                return;
            }
            System.out.println("Digite o valor a prioridade (BAIXO,MEDIO,ALTO):");
            String prioridadeValor = scanner.nextLine();
            Prioridade prioridade;
            try {
                prioridade = Prioridade.valueOf(prioridadeValor.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridade invalida, use: BAIXO,MEDIO,ALTO");
                return;
            }

            Tarefa tarefa = new Tarefa(UUID.randomUUID(),titulo,descricao,dataEntrega,prioridade, Status.PENDENTE);
            gerenciadorTarefas.adicionarTarefa(tarefa);

        } catch (TarefaDuplicadaException e) {
            System.out.println("Erro ao adicionar tarefa:"+e.getMessage());
        }

    }

    private void listarTarefas() {
        List <Tarefa> tarefas = gerenciadorTarefas.listarTarefa();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa foi encontrada.");
            return;
        }
        System.out.println("===== Lista de Tarefas =====");
        for (Tarefa tarefa : tarefas) {
            System.out.println("ID: " + tarefa.getId());
            System.out.println("Título: " + tarefa.getTitulo());
            System.out.println("Descrição: " + tarefa.getDescricao());
            System.out.println("Data de entrega: " + tarefa.getDataDeEntrega());
            System.out.println("Prioridade: " + tarefa.getPrioridade());
            System.out.println("Status: " + tarefa.getStatus());
            System.out.println("----------------------------");
        }

    }

    private  void atualizarTarefa() {
        System.out.println("Digite a data da tarefa que deseja atualizar: ");
        String dataTarefa = scanner.nextLine();
        try{
            LocalDate data = DateUtil.converterData(dataTarefa);
            List<Tarefa> tarefaExistente = gerenciadorTarefas.buscarTarefaPorData(data);
            if (tarefaExistente.isEmpty()) {
                System.out.println("Nenhuma tarefa foi encontrada.");
                return;
            }
            System.out.println("Tarefa encontrada: ");
            for (int i = 0; i < tarefaExistente.size() ; i++) {
                Tarefa t = tarefaExistente.get(i);
                System.out.println((i + 1) + " - " + t.getTitulo() + " | " + t.getDescricao() + " | " + t.getStatus());
                
            }
            System.out.println("Digite o número da tarefa que deseja atualizar:");
            String escolha = scanner.nextLine();

            int indice;
            try {
                indice = Integer.parseInt(escolha) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                return;
            }

            if (indice < 0 || indice >= tarefaExistente.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            Tarefa tarefaEncontradas = tarefaExistente.get(indice);

            System.out.println("Digite o novo titulo ou presione enter para manter: ");
            String novoTitulo = scanner.nextLine();
            if (!novoTitulo.isBlank()) tarefaEncontradas.setTitulo(novoTitulo);
            System.out.println("Digite a nova descricao ou presione enter para manter: ");
            String novaDescricao = scanner.nextLine();
            if (!novaDescricao.isBlank()) tarefaEncontradas.setDescricao(novaDescricao);
            System.out.println("Digite a nova Data de entrega (dd/MM/yyyy) ou presione enter para manter: ");
            String novaDataEntrega = scanner.nextLine();
            if (novaDataEntrega.isBlank()) {
                try{
                    LocalDate novaEntrega = DateUtil.converterData(novaDataEntrega);
                    tarefaEncontradas.setDataDeEntrega(novaEntrega);
                }catch (DateTimeParseException e){
                    System.out.println("Formato da data invalida. Alteração ignorada.");
                }
            }
            System.out.println("Digite nova prioridade (BAIXO, MEDIO, ALTO) ou presione enter para manter: ");
            String novaPrioridadeScr = scanner.nextLine();
            if (novaPrioridadeScr.isBlank()) {
                try{
                    Prioridade novaPrioridade = Prioridade.valueOf(novaPrioridadeScr.toUpperCase());
                    tarefaEncontradas.setPrioridade(novaPrioridade);

                }catch (IllegalArgumentException e){
                    System.out.println("Prioridade invalida. Alteração ignorada.");
                }
            }

            System.out.println("Digite o novo status (PENDENTE, INICIADO, COMPLETO, CANCELADO) ou Enter para manter:");
            String novoStatusStr = scanner.nextLine();
            if (!novoStatusStr.isBlank()) {
                try {
                    Status novoStatus = Status.valueOf(novoStatusStr.toUpperCase());
                    tarefaEncontradas.setStatus(novoStatus);
                } catch (IllegalArgumentException e) {
                    System.out.println("Status inválido. Alteração ignorada.");
                }
            }
            gerenciadorTarefas.atualizarTarefa(tarefaEncontradas.getId(), tarefaEncontradas);
            System.out.println("Tarefa atualizada com sucesso.");
        } catch (DateTimeParseException e){
            System.out.println("Data invalida, utilize o formato dd/MM/yyyy");
        }
    }
    private void removerTarefa(){
        System.out.println("Digite a data da tarefa que deseja atualizar: ");
        String dataTarefa = scanner.nextLine();
        try{
            LocalDate data = DateUtil.converterData(dataTarefa);
            List<Tarefa> tarefaExistente = gerenciadorTarefas.buscarTarefaPorData(data);
            if (tarefaExistente.isEmpty()) {
                System.out.println("Nenhuma tarefa foi encontrada.");
                return;
            }
            System.out.println("Tarefa encontrada: ");
            for (int i = 0; i < tarefaExistente.size() ; i++) {
                Tarefa t = tarefaExistente.get(i);
                System.out.println((i + 1) + " - " + t.getTitulo() + " | " + t.getDescricao() + " | " + t.getStatus());

            }
            System.out.println("Digite o número da tarefa que deseja remover:");
            String escolha = scanner.nextLine();

            int indice;
            try {
                indice = Integer.parseInt(escolha) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                return;
            }

            if (indice < 0 || indice >= tarefaExistente.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            Tarefa tarefaEncontradas = tarefaExistente.get(indice);
            gerenciadorTarefas.removerTarefa(tarefaEncontradas.getId());
            System.out.println("Tarefa removida com sucesso.");

        }catch (DateTimeParseException e){
            System.out.println("Data invalida, utilize o formato dd/MM/yyyy");
        }


    }
}

