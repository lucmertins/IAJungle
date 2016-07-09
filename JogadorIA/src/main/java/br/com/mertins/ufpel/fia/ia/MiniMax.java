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
        Move raiz = new Move(this.jogador);
        Move move = minimax(raiz, tabuleiroState, this.depth, true);
        System.out.printf("\nvoltou do minimax %s   %s de %s para %s   valor [%d] \n", move.getJogador(), move.getPeca().getTipo().descricao(), move.getPosicaoAtual(), move.getPosicaoNova(), move.getValue());
        Move temp = move;
        while (temp.getParent() != raiz) {
            System.out.printf("\ncaminho avaliando mover %s de %s para %s   valor [%d] \n", temp.getPeca().getTipo().descricao(), temp.getPosicaoAtual(), temp.getPosicaoNova(), temp.getValue());
            temp = temp.getParent();
        }
        System.out.printf("\nmovimento escolhido %s de %s para %s   valor [%d] \n\n", temp.getPeca().getTipo().descricao(), temp.getPosicaoAtual(), temp.getPosicaoNova(), temp.getValue());

        Board board = new Board(tabuleiroState);
        System.out.printf("\n\n\n Jogada escolhida\n\n\n");
        board.print(jogador, temp, false);
        this.observator.difference();
        return temp;
    }

    private Move minimax(Move move, TabuleiroState tabuleiroState, int depth, boolean maximizingPlayer) {
        final Board tempBoard = new Board(tabuleiroState);
//        tempBoard.print(jogador);
        if (depth == 0 || Board.gameOver(move, tabuleiroState) != Tabuleiro.Situacao.UNDEFINED) {
            Move temp = this.heuristica.process(tabuleiroState, move);
            return temp;
        } else if (maximizingPlayer) {
            Move bestValue = null;
            for (Move moveChild : tempBoard.findCandidates(this.jogador)) {
                move.addChild(moveChild);
                Board boardChild = new Board(tabuleiroState);
                boardChild.move(moveChild.getPeca(), moveChild.getPosicaoAtual(), moveChild.getPosicaoNova());
                Move temp = minimax(moveChild, boardChild.getTabuleiroState(), depth - 1, false);
                bestValue = bestValue == null ? temp : bestValue.max(temp);
            }
            return bestValue;
        } else {
            Move bestValue = null;
            for (Move moveChild : tempBoard.findCandidates(Jogador.adversario(this.jogador))) {
                move.addChild(moveChild);
                Board boardChild = new Board(tabuleiroState);
                boardChild.move(moveChild.getPeca(), moveChild.getPosicaoAtual(), moveChild.getPosicaoNova());
                Move temp = minimax(moveChild, boardChild.getTabuleiroState(), depth - 1, true);
                bestValue = bestValue == null ? temp : bestValue.min(temp);
            }
            return bestValue;
        }
    }

}
