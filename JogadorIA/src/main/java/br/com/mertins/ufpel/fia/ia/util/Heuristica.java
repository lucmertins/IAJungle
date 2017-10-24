package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;
import java.util.List;

/**
 *
 * @author mertins
 */
public class Heuristica {

    public int process(TabuleiroState state, Move move, int depth, WeightTunning conf) {
        Board board = new Board(state);
        int value = 0;

        switch (move.getMovimento()) {
            case WINTOCA:
                value = conf.getWinTocaWeight() * (depth + 1);
                break;
            case WINALLPECAS:
                value = conf.getWinAllPecasWeight() * (depth + 1);
                break;
            default:
                // **** avaliação situação jogador
                value = avalia(board, move, value, conf, true);
                // **** avaliação situação adversário
                value += avalia(board, move, value, conf, false);
                
        }
        return value;
    }

    private int avalia(Board board, Move move, int value, WeightTunning conf, boolean sum) {
        int minmax = sum ? 1 : -1;
        Jogador jogador = sum ? move.getJogador() : Jogador.adversario(move.getJogador());
        List<Peca> pecas = board.pecasNoTabuleiro(jogador);
        // **** valoriza as peças
        value += conf.getPecaWeight(move.getPeca().getTipo()) * minmax;

        // **** mais valor se tiver mais peças proximas a toca (distancia Manhattan menor)
        value = pecas.stream().map((peca) -> conf.getPecaWeight(peca.getTipo()) * minmax).reduce(value, Integer::sum);

        value = pecas.stream().map((peca) -> (Board.WORSTDISTANCE - board.distanciaToca(jogador, board.posicao(peca))) * conf.getNearTocaManhattanWeight() * minmax).reduce(value, Integer::sum);

        // **** mais valor se tiver mais peças proxima no eixo y a toca (Y menor).
        value = pecas.stream().map((peca) -> (Board.WORSTDISTANCE - board.distanciaYToca(jogador, board.posicao(peca))) * conf.getNearTocaYWeight() * minmax).reduce(value, Integer::sum);
        return value;

    }
}
