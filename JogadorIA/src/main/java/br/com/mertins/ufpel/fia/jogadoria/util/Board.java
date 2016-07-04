package br.com.mertins.ufpel.fia.jogadoria.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mertins
 */
public class Board extends Tabuleiro{

    public Move[] findCandidates(Move node) {
        List<Move> vagos = new ArrayList();
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
        return vagos.toArray(new Move[vagos.size()]);
    }

    public Situacao gameOver(Move move) {
        return Situacao.UNDEFINED;
    }
}
