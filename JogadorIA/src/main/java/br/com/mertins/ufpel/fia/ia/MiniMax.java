package br.com.mertins.ufpel.fia.ia;

import br.com.mertins.ufpel.fia.ia.util.Move;
import br.com.mertins.ufpel.fia.ia.monitor.Observator;
import br.com.mertins.ufpel.fia.ia.util.Heuristica;
import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;
import br.com.mertins.ufpel.fia.ia.util.Board;


/**
 *
 * @author mertins
 */
public class MiniMax {

    private final Observator observator;
    private final Heuristica heuristica;
    private final int depth;
    private Jogador jogador;

    public MiniMax(Observator observator, int depth) {
        this.observator = observator;
        this.depth = depth;
        this.heuristica = new Heuristica();
    }

    public Move run(Jogador jogador, TabuleiroState tabuleiroState) {
        this.jogador = jogador;
        Move node = minimax(new Move(Move.Infinite.NONE, this.jogador), tabuleiroState, this.depth, this.jogador == Jogador.Jogador1);
        this.observator.difference();
        return node;
    }

    private Move minimax(Move move, TabuleiroState tabuleiroState, int depth, boolean maximizingPlayer) {
        final Board tempBoard = new Board(tabuleiroState);
        if (depth == 0 || Board.gameOver(move, tabuleiroState) != Tabuleiro.Situacao.UNDEFINED) {
            Move temp = this.heuristica.process(tabuleiroState, move);
            return temp;
        } else if (maximizingPlayer) {
            Move bestValue = new Move(Move.Infinite.NEGATIVE, this.jogador);
            for (Move moveChild : tempBoard.findCandidates(this.jogador)) {
                Board child = new Board(tabuleiroState);
                child.move(moveChild.getPeca(), moveChild.getPosicaoAtual(), moveChild.getPosicaoNova());
                child.print();
                Move temp = minimax(moveChild, child.getTabuleiroState(), depth - 1, false);
                bestValue = bestValue.max(temp);
            }
            return bestValue;
        } else {
            Move bestValue = new Move(Move.Infinite.POSITIVE, Jogador.adversario(jogador));
            for (Move moveChild : tempBoard.findCandidates(Jogador.adversario(this.jogador))) {
                Board child = new Board(tabuleiroState);
                child.move(moveChild.getPeca(), moveChild.getPosicaoAtual(), moveChild.getPosicaoNova());
                child.print();
                Move temp = minimax(moveChild, child.getTabuleiroState(), depth - 1, true);
                bestValue = bestValue.min(temp);
            }
            return bestValue;
        }
    }

}
