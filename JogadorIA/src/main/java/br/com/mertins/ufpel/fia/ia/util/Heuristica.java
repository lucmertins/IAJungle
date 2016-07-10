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

    public Move process(TabuleiroState state, Move move) {
        Board board = new Board(state);
        Tabuleiro.Movimento movimento = board.move(move.getPeca(), move.getPosicaoAtual(), move.getPosicaoNova());
//        board.print(move.getJogador());
        int value = 0;
        if (movimento == Tabuleiro.Movimento.WINALLPECAS || movimento == Tabuleiro.Movimento.WINTOCA) {
            value = 1000000;
        }
        Jogador adversario = Jogador.adversario(move.getJogador());
        board.getTabuleiroState();
        List<Peca> pecasJogador = board.pecasNoTabuleiro(move.getJogador());
        List<Peca> pecasAdversario = board.pecasNoTabuleiro(adversario);
        int numPecas = pecasJogador.size() - pecasAdversario.size();
        value += numPecas > 0 ? numPecas * 200 : 100;
        for (Peca peca : pecasJogador) {
            value += 200 - board.distanciaToca(move.getJogador(), board.posicao(peca));
        }
        move.setValue(value);
//        board.print(move.getJogador(), move, false);
//        System.out.printf("Heuristica peça[%s] novaposicao[%s] value[%d]\n", move.getPeca(), move.getPosicaoNova(), value);
        return move;
    }
}
