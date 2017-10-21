package br.com.mertins.ufpel.fia.ia;

import br.com.mertins.ufpel.fia.ia.util.Move;
import br.com.mertins.ufpel.fia.ia.monitor.Observator;
import br.com.mertins.ufpel.fia.ia.util.Heuristica;
import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;
import br.com.mertins.ufpel.fia.ia.util.Board;
import br.com.mertins.ufpel.fia.ia.util.WeightTunning;

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
        Move raiz = new Move(this.jogador, true);
        Move move = minimax(raiz, tabuleiroState, this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
//        TreeNode tree = new TreeNode(raiz);
//        tree.print();
        Move temp = move;
        while (temp.getParent() != raiz) {  // encontrar o movimento a ser feito!
            temp = temp.getParent();
        }
        Board board = new Board(tabuleiroState);
        System.out.printf("\n\n\n Jogada escolhida\n\n\n");
        board.print(jogador, temp, false);
        this.observator.difference();
        return temp;
    }

    private Move minimax(Move move, TabuleiroState tabuleiroState, int depth, int alpha, int beta, boolean maximizingPlayer) {
        final Board tempBoard = new Board(tabuleiroState);
        Tabuleiro.Situacao situacao = Board.gameOver(move, tabuleiroState);
        Move bestValue = null;
//        boolean primeiro = true;
        if (depth == 0 || situacao != Tabuleiro.Situacao.UNDEFINED) {
            if (situacao != Tabuleiro.Situacao.UNDEFINED) {
                System.out.printf("Jogador [%s] situacao [%s] depth [%d]   \n", move.getJogador(), situacao.toString(), depth, move.getPeca().getTipo(), move.getPosicaoAtual(), move.getPosicaoNova());
            }
            Move temp = this.heuristica.process(tabuleiroState, move, new WeightTunning());
            return temp;
        } else if (maximizingPlayer) {
            for (Move moveChild : tempBoard.findCandidates(this.jogador, maximizingPlayer)) {
                move.addChild(moveChild);
                Board boardChild = new Board(tabuleiroState);
                boardChild.move(moveChild.getPeca(), moveChild.getPosicaoAtual(), moveChild.getPosicaoNova());
//                boardChild.print(jogador);
//                System.out.println("************* " + (depth - 1));
                Move temp = minimax(moveChild, boardChild.getTabuleiroState(), depth - 1, alpha, beta, !maximizingPlayer);
//                System.out.println(temp.getValue() + "************* " + (depth - 1));
                bestValue = bestValue == null ? temp : bestValue.max(temp);
                if (bestValue != null) {
                    move.setValue(bestValue.getValue());
                }
            }
        } else {
            for (Move moveChild : tempBoard.findCandidates(Jogador.adversario(this.jogador), maximizingPlayer)) {
                move.addChild(moveChild);
                Board boardChild = new Board(tabuleiroState);
                boardChild.move(moveChild.getPeca(), moveChild.getPosicaoAtual(), moveChild.getPosicaoNova());
//                boardChild.print(Jogador.adversario(this.jogador));
//                System.out.println(temp.getValue());
                Move temp = minimax(moveChild, boardChild.getTabuleiroState(), depth - 1, alpha, beta, !maximizingPlayer);
//                System.out.println("*************  " + (depth - 1));
                bestValue = bestValue == null ? temp : bestValue.min(temp);
                if (bestValue != null) {
                    move.setValue(bestValue.getValue());
                }
            }
        }
//        if (depth == 4) {
//            System.out.println("");
//        }
        return bestValue;

    }
}

//                if (primeiro) {
//                    bestValue = temp;
//                    primeiro = false;
//                }
//                if (temp != null && temp.getValue() > alpha) {
//                    alpha = temp.getValue();
//                }
//                if (alpha > beta) {  //if alpha >= beta then return alpha (cut off)
//                    move.addChild(moveChild);
//                    bestValue = bestValue == null ? temp : bestValue.max(temp);
//                }
//    if (primeiro) {
//                    bestValue = temp;
//                    primeiro = false;
//                }
//                if (temp != null && temp.getValue() < beta) {
//                    beta = temp.getValue();
//                }
//                if (alpha > beta) {  // if alpha >= beta then return beta (cut off)
//                    move.addChild(moveChild);
//                    bestValue = bestValue == null ? temp : bestValue.min(temp);
//                }
