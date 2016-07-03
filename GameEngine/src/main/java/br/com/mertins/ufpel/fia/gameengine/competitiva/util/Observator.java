package br.com.mertins.ufpel.fia.gameengine.competitiva.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mertins
 */
public class Observator {

    public enum ALGORITHMS {
        MINIMAX
    }
    private boolean run = true;
    private final ALGORITHMS algorithm;
    private final List<Event> eventos = new ArrayList();
    private int vitorias = 0;
    private int empates = 0;
    private int derrotas = 0;
    private int valorHeadPositivo = 0;
    private int valorHeadNegativo = 0;
    private int valorHeadZero = 0;

    public Observator(ALGORITHMS algorithm) {
        this.algorithm = algorithm;
        this.eventos.add(new Event("Begin "));
        Thread th = new Thread(() -> {
            while (run) {
                try {
                    Thread.sleep(10000);
                    Runtime.getRuntime().gc();
                    informe();
                } catch (InterruptedException ex) {
                    run = false;
                    Logger.getLogger(Observator.class.getName()).log(Level.SEVERE, "falha no sleep observator", ex);
                }

            }
        });
        th.setDaemon(true);
        th.start();

    }

    public ALGORITHMS getAlgorithm() {
        return algorithm;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getEmpates() {
        return empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getValorHeadPositivo() {
        return valorHeadPositivo;
    }

    public int getValorHeadNegativo() {
        return valorHeadNegativo;
    }

    public int getValorHeadZero() {
        return valorHeadZero;
    }

    public List<Event> getEvents() {
        return eventos;
    }

    public void okSolution() {
        this.eventos.add(new Event("Sucess"));
        this.run = false;
    }

    public void gameOver(Node node) {
        String id;
        if (node.getValue() > 0) {
            id = "Vitória";
            vitorias++;
        } else if (node.getValue() < 0) {
            id = "Derrota";
            derrotas++;
        } else {
            id = "Empate";
            empates++;
        }

        Node temp = node;
        while (temp.getParent() != null) {
            temp = temp.getParent();
        }
        switch (temp.getValue()) {
            case 1:
                valorHeadPositivo++;
                break;
            case -1:
                valorHeadNegativo++;
                break;
            case 0:
                valorHeadZero++;
                break;
        }
        this.eventos.add(new Event(id, node));
    }

    public Duration difference() {
        this.run = false;
        if (this.eventos.size() > 0) {
            Event begin = this.eventos.get(0);
            Event end = this.eventos.get(this.eventos.size() - 1);
            Duration duracao = Duration.between(begin.getInstant(), end.getInstant());
            return duracao;
        }
        return null;
    }

    private String informe() {
        Event begin = this.eventos.get(0);
        Duration duration = Duration.between(begin.getInstant(), Instant.now());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String format = fmt.format(duration.addTo(LocalDateTime.of(0, 1, 1, 0, 0)));
       System.out.printf("Execução [%s] MemFree [%dM]  vitorias [%d] empates [%d] derrotas [%d]\n", format, Runtime.getRuntime().freeMemory() / 1048576, vitorias, empates, derrotas);
        return format;
    }
}
