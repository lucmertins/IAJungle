package br.com.mertins.ufpel.fia.ia;

import br.com.mertins.ufpel.fia.ia.util.Move;
import br.com.mertins.ufpel.fia.ia.monitor.Observator;
import br.com.mertins.ufpel.fia.ia.util.Heuristica;
import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;
import br.com.mertins.ufpel.fia.ia.util.Board;
import br.com.mertins.ufpel.fia.ia.util.WeightTunning;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author mertins
 */
public class MiniMax {

    private final Observator observator;
    private final Heuristica heuristica;
    private final int depth;
    private Jogador jogador;
    private final Set<JogadasWinner> jogadasWinner = new HashSet();
    private final WeightTunning weightTunning;

    public MiniMax(Observator observator, int depth, WeightTunning weightTunning) {
        this.observator = observator;
        this.depth = depth;
        this.heuristica = new Heuristica();
        this.weightTunning = weightTunning;
    }

    public Move run(Jogador jogador, TabuleiroState tabuleiroState) {
        this.jogador = jogador;
        Move raiz = new Move(this.jogador, true);
        Move move = minimax(raiz, tabuleiroState, this.depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        Move temp = move;
        while (temp.getParent() != raiz) {  // encontrar o movimento a ser feito!
            temp = temp.getParent();
        }
//        StringBuilder print = new PrintTree().print(raiz);
//        System.out.println(print.toString());

        this.jogadasWinner.forEach(jogada -> {
            System.out.printf("Jogador [%s] vai ganhar o jogo após [%d] jogadas   \n", jogada.jogador, this.depth - jogada.depth);
        });
        this.observator.difference();
        return temp;
    }

    private Move minimax(Move move, TabuleiroState tabuleiroState, int depth, int alpha, int beta, boolean maximizingPlayer) {
        final Board tempBoard = new Board(tabuleiroState);
        Tabuleiro.Situacao situacao = tempBoard.gameOver(move);
        Move bestValue = null;
        if (depth == 0 || situacao != Tabuleiro.Situacao.UNDEFINED) {
            if (situacao != Tabuleiro.Situacao.UNDEFINED) {
                this.jogadasWinner.add(new JogadasWinner(depth, move.getJogador()));
//                if (jogador) {
//                    System.out.printf("Jogador [%s] situacao [%s] depth [%d]   \n", move.getJogador(), situacao.toString(), depth, move.getPeca().getTipo(), move.getPosicaoAtual(), move.getPosicaoNova());
//                }
            }
            move.setValue(this.heuristica.process(tabuleiroState, move, depth, this.weightTunning));
            bestValue = move;
        } else if (maximizingPlayer) {
            for (Move moveChild : tempBoard.findCandidates(this.jogador, maximizingPlayer)) {
                Board boardChild = new Board(tabuleiroState);
                boardChild.move(moveChild);
                Move temp = minimax(moveChild, boardChild.getTabuleiroState(), depth - 1, alpha, beta, !maximizingPlayer);
                // fazer poda?
                move.addChild(moveChild);
                bestValue = bestValue == null ? temp : bestValue.max(temp);
                if (bestValue != null && bestValue.compareValueMax(move.getValue()) == Move.ValueAssessment.LARGER) {
                    move.setValue(bestValue.getValue());
                }
            }
        } else {
            for (Move moveChild : tempBoard.findCandidates(Jogador.adversario(this.jogador), maximizingPlayer)) {
                Board boardChild = new Board(tabuleiroState);
                moveChild.setMovimento(boardChild.move(moveChild));
                Move temp = minimax(moveChild, boardChild.getTabuleiroState(), depth - 1, alpha, beta, !maximizingPlayer);
                // fazer poda?
                move.addChild(moveChild);
                bestValue = bestValue == null ? temp : bestValue.min(temp);
                if (bestValue != null && bestValue.compareValueMin(move.getValue()) == Move.ValueAssessment.SMALLER) {
                    move.setValue(bestValue.getValue());
                }
            }
        }
        return bestValue;
    }

    private class JogadasWinner {

        int depth;
        Jogador jogador;

        public JogadasWinner(int depth, Jogador jogador) {
            this.depth = depth;
            this.jogador = jogador;

        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + this.depth;
            hash = 79 * hash + Objects.hashCode(this.jogador);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final JogadasWinner other = (JogadasWinner) obj;
            if (this.depth != other.depth) {
                return false;
            }
            if (this.jogador != other.jogador) {
                return false;
            }
            return true;
        }

    }
}

//                if (temp != null && temp.getValue() > alpha) {
//                    alpha = temp.getValue();
//                }
//                if (alpha > beta) {  //if alpha >= beta then return alpha (cut off)
//                    move.addChild(moveChild);
//                    bestValue = bestValue == null ? temp : bestValue.max(temp);
//                }
//
//                if (temp != null && temp.getValue() < beta) {
//                    beta = temp.getValue();
//                }
//                if (alpha > beta) {  // if alpha >= beta then return beta (cut off)
//                    move.addChild(moveChild);
//                    bestValue = bestValue == null ? temp : bestValue.min(temp);
//                }
