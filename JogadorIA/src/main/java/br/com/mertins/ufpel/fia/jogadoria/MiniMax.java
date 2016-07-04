package br.com.mertins.ufpel.fia.jogadoria;

import br.com.mertins.ufpel.fia.jogadoria.util.Move;
import br.com.mertins.ufpel.fia.jogadoria.monitor.Observator;
import br.com.mertins.ufpel.fia.jogadoria.util.Heuristica;
import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.jogadoria.util.Board;

/**
 *
 * @author mertins
 */
public class MiniMax {

    private final Observator observator;
    private Board board;
    private Heuristica heuristica;
    private final int depth;

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
        return this.run(jogador);
    }

    public Move run(Jogador jogador) {
        Move node = minimax(new Move(Move.Infinite.NONE), this.depth, jogador == Jogador.Jogador1);
        this.observator.difference();
        return node;
    }

    private Move minimax(Move node, int depth, boolean maximizingPlayer) {
        if (depth == 0 || this.board.gameOver(node) != Tabuleiro.Situacao.UNDEFINED) {
            Move temp = this.heuristica.process(node);
            return temp;
        } else if (maximizingPlayer) {
            Move bestValue = new Move(Move.Infinite.NEGATIVE);
            for (Move moveChild : this.board.findCandidates(node)) {
                Move temp = minimax(moveChild, depth - 1, false);
                bestValue = bestValue.max(temp);
            }
            return bestValue;
        } else {
            Move bestValue = new Move(Move.Infinite.POSITIVE);
            for (Move moveChild : this.board.findCandidates(node)) {
                Move temp = minimax(moveChild, depth - 1, true);
                bestValue = bestValue.min(temp);
            }
            return bestValue;
        }
    }
}
