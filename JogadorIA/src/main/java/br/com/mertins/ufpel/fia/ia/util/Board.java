package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mertins
 */
public class Board extends Tabuleiro {

    public Board() {
    }

    public Board(Peca[][] tabuleiro) {
        super(tabuleiro);
    }

    public Move[] findCandidates(Move move, Jogador jogador) {
        List<Move> vagos = new ArrayList();

        //encontrar as pecas do jogador
        //montar  cada movimento possivel da peca e devolver
//        Move[][] tabuleiro = this.buildBoardMoment(node).getState();
//        for (byte y = 0; y < tabuleiro.length; y++) {
//            for (byte x = 0; x < tabuleiro.length; x++) {
//                if (tabuleiro[y][x].getMarker() == Move.Marker.B) {
//                    node.addChild(tabuleiro[y][x]);
//                    tabuleiro[y][x].setMarker(node.getMarker().invert());
//                    tabuleiro[y][x].setPosX(x);
//                    tabuleiro[y][x].setPosY(y);
//                    vagos.add(tabuleiro[y][x]);
//                }
//            }
//        }
//movimentos deve informar qual o jogador, qual a peça , posicao inicial e posicao final
        return vagos.toArray(new Move[vagos.size()]);
    }

    public Situacao gameOver(Move move) {
        if (move.getPeca() == null) {
            return Situacao.UNDEFINED;
        }

        // efetuar jogada! Peca, posicao velha, posicao nova e jogador?
        if (this.pecasNoTabuleiro(Jogador.Jogador1).size() == 0) {
            return Situacao.WINJOG2;
        }
        if (this.pecasNoTabuleiro(Jogador.Jogador2).size() == 0) {
            return Situacao.WINJOG1;
        }
        //verificar se alguma peça chegou na toca adversaria

        return Situacao.UNDEFINED;
    }
}
