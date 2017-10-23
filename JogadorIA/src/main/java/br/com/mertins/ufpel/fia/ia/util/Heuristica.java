package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;
import java.util.List;

/**
 *
 * @author mertins
 */
public class Heuristica {

    public int process(TabuleiroState state, Move move, WeightTunning conf) {
        Board board = new Board(state);
        int value = 0;

        // **** muito valor por chegar na toca
        if (move.getMovimento() == Tabuleiro.Movimento.WINTOCA) {
            value = conf.getWinTocaWeight();
            // **** muito valor por comer todas as peças
        } else if (move.getMovimento() == Tabuleiro.Movimento.WINALLPECAS) {
            value = conf.getWinAllPecasWeight();
        } else {
            List<Peca> pecasJogador = board.pecasNoTabuleiro(move.getJogador());
            // **** valoriza as peças
            value += conf.getPecaWeight(move.getPeca().getTipo());
            for (Peca peca : pecasJogador) {
                value += conf.getPecaWeight(peca.getTipo());
            }
            // **** mais valor se tiver mais peças proximas a toca (distancia Manhattan menor)
            for (Peca peca : pecasJogador) {
                value += (Board.WORSTDISTANCE - board.distanciaToca(move.getJogador(), board.posicao(peca))) * conf.getNearTocaManhattanWeight();
            }
            // **** mais valor se tiver mais peças proxima no eixo y a toca (Y menor).
            for (Peca peca : pecasJogador) {
                value += (Board.WORSTDISTANCE - board.distanciaYToca(move.getJogador(), board.posicao(peca))) * conf.getNearTocaYWeight();
            }

            // Avaliação da situação do adversário
            Jogador adversario = Jogador.adversario(move.getJogador());
            List<Peca> pecasAdversario = board.pecasNoTabuleiro(adversario);
            value -= conf.getPecaWeight(move.getPeca().getTipo());
            for (Peca peca : pecasAdversario) {
                value -= conf.getPecaWeight(peca.getTipo());
            }
            // **** ,menos valor se adversario tiver mais peças proximas a toca (distancia Manhattan menor)
            for (Peca peca : pecasAdversario) {
                value -= (Board.WORSTDISTANCE - board.distanciaToca(adversario, board.posicao(peca))) * conf.getNearTocaManhattanWeight();
            }
            // **** menos valor se adversário tiver mais peças proxima no eixo y a toca (Y menor).
            for (Peca peca : pecasAdversario) {
                value -= (Board.WORSTDISTANCE - board.distanciaYToca(adversario, board.posicao(peca))) * conf.getNearTocaYWeight();
            }

        }
//        board.print(move.getJogador(), move, false);
//        System.out.printf("Jogador [%s] Heuristica peça[%s] novaposicao[%s] value[%d]\n", move.getJogador(), move.getPeca(), move.getPosicaoNova(), value);
        return value;
    }
}
