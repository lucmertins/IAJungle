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

    public Move process(TabuleiroState state, Move move) {
        Board board = new Board(state);
        board.move(move.getPeca(), move.getPosicaoAtual(), move.getPosicaoNova());
//        board.print(move.getJogador());
        Jogador adversario = Jogador.adversario(move.getJogador());
        board.getTabuleiroState();
        List<Peca> pecasJogador = board.pecasNoTabuleiro(move.getJogador());
        List<Peca> pecasAdversario = board.pecasNoTabuleiro(adversario);
        int value = pecasJogador.size() * 200 - pecasAdversario.size() * 200;
        for (Peca peca : pecasJogador) {
            value += 200 - board.distanciaToca(move.getJogador(), board.posicao(peca));
        }
        move.setValue(value);
//        board.print(move.getJogador(), move, false);
//        System.out.printf("Heuristica peça[%s] novaposicao[%s] value[%d]\n", move.getPeca(), move.getPosicaoNova(), value);
        return move;
    }
}
