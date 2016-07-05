package br.com.mertins.ufpel.fia.ia;

import br.com.mertins.ufpel.fia.ia.util.Move;
import br.com.mertins.ufpel.fia.ia.monitor.Observator;
import br.com.mertins.ufpel.fia.ia.util.Heuristica;
import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.ia.util.Board;

/**
 *
 * @author mertins
 */
public class MiniMax {

    private final Observator observator;
    private Board board;
    private final Heuristica heuristica;
    private final int depth;
    private Jogador jogador;

    public MiniMax(Observator observator, int depth) {
        this.observator = observator;
        this.depth = depth;
        this.board = new Board();
        this.heuristica = new Heuristica();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Move run(Jogador jogador, Board board) {
        this.setBoard(board);
        this.jogador = jogador;
        Move node = minimax(new Move(Move.Infinite.NONE, this.jogador), this.depth, this.jogador == Jogador.Jogador1);
        this.observator.difference();
        return node;
    }

    private Move minimax(Move move, int depth, boolean maximizingPlayer) {
        if (depth == 0 || this.board.gameOver(move) != Tabuleiro.Situacao.UNDEFINED) {
            Move temp = this.heuristica.process(this.board,move);
            return temp;
        } else if (maximizingPlayer) {
            Move bestValue = new Move(Move.Infinite.NEGATIVE, this.jogador);
            for (Move moveChild : this.board.findCandidates(move)) {
                Move temp = minimax(moveChild, depth - 1, false);
                bestValue = bestValue.max(temp);
            }
            return bestValue;
        } else {
            Move bestValue = new Move(Move.Infinite.POSITIVE, Jogador.adversario(jogador));
            for (Move moveChild : this.board.findCandidates(move)) {
                Move temp = minimax(moveChild, depth - 1, true);
                bestValue = bestValue.min(temp);
            }
            return bestValue;
        }
    }
}
