package br.com.mertins.ufpel.fia.gameengine.competitiva;

import br.com.mertins.ufpel.fia.gameengine.competitiva.util.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mertins
 */
public class SingleJungle {

    public enum Resultado {
        VITORIA, DERROTA, EMPATE, EMJOGO
    }

    private final Node nodeBegin = new Node(Node.Infinite.NONE, Node.Marker.O);

    public SingleJungle() {
        this.clear();
    }

    public Node getNodeBegin() {
        return nodeBegin;
    }

//    public Node[] findCandidates(Node node) {
//        List<Node> vagos = new ArrayList();
//        Node[][] tabuleiro = this.buildBoardMoment(node).getState();
//        for (byte y = 0; y < tabuleiro.length; y++) {
//            for (byte x = 0; x < tabuleiro.length; x++) {
//                if (tabuleiro[y][x].getMarker() == Node.Marker.B) {
//                    node.addChild(tabuleiro[y][x]);
//                    tabuleiro[y][x].setMarker(node.getMarker().invert());
//                    tabuleiro[y][x].setPosX(x);
//                    tabuleiro[y][x].setPosY(y);
//                    vagos.add(tabuleiro[y][x]);
//                }
//            }
//        }
//        return vagos.toArray(new Node[vagos.size()]);
//    }

//    public void print(Node node) {
//        BoardTicTacToeState state=this.buildBoardMoment(node);
//        Node[][] tabuleiro = state.getState();
//        System.out.printf("\nHeight %d      valor heuristica %d \n",state.getHeight(),node.getValue());
//        for (int y = 0; y < tabuleiro.length; y++) {
//            for (int x = 0; x < tabuleiro.length; x++) {
//                System.out.printf("%s\t", tabuleiro[y][x].getMarker().toString());
//            }
//            System.out.println();
//        }
//    }

//    public Node heuristic(Node node) {
//        Resultado gameOver = this.gameOver(node);
//        switch(gameOver){
//            case VITORIA:
//                node.setValue(1);
//                break;
//            case DERROTA:
//                node.setValue(-1);
//                break;
//            default:
//                node.setValue(0);
//        }
//        return node;
//    }

//    public Resultado gameOver(Node node) {
//        Node[][] tabuleiro = this.buildBoardMoment(node).getState();
//        boolean espacoLivre = false;
//        for (int y = 0; y < tabuleiro.length; y++) {
//            for (int x = 0; x < tabuleiro.length; x++) {
//                if ((tabuleiro[y][0].getMarker() == tabuleiro[y][1].getMarker() && tabuleiro[y][1].getMarker() == tabuleiro[y][2].getMarker() && tabuleiro[y][2].getMarker() == Node.Marker.X)
//                        || (tabuleiro[0][x].getMarker() == tabuleiro[1][x].getMarker() && tabuleiro[1][x].getMarker() == tabuleiro[2][x].getMarker() && tabuleiro[2][x].getMarker() == Node.Marker.X)
//                        || (tabuleiro[0][0].getMarker() == tabuleiro[1][1].getMarker() && tabuleiro[1][1].getMarker() == tabuleiro[2][2].getMarker() && tabuleiro[2][2].getMarker() == Node.Marker.X)
//                        || (tabuleiro[2][0].getMarker() == tabuleiro[1][1].getMarker() && tabuleiro[1][1].getMarker() == tabuleiro[0][2].getMarker() && tabuleiro[0][2].getMarker() == Node.Marker.X)) {
//                    return Resultado.VITORIA;
//                }
//                if ((tabuleiro[y][0].getMarker() == tabuleiro[y][1].getMarker() && tabuleiro[y][1].getMarker() == tabuleiro[y][2].getMarker() && tabuleiro[y][2].getMarker() == Node.Marker.O)
//                        || (tabuleiro[0][x].getMarker() == tabuleiro[1][x].getMarker() && tabuleiro[1][x].getMarker() == tabuleiro[2][x].getMarker() && tabuleiro[2][x].getMarker() == Node.Marker.O)
//                        || (tabuleiro[0][0].getMarker() == tabuleiro[1][1].getMarker() && tabuleiro[1][1].getMarker() == tabuleiro[2][2].getMarker() && tabuleiro[2][2].getMarker() == Node.Marker.O)
//                        || (tabuleiro[2][0].getMarker() == tabuleiro[1][1].getMarker() && tabuleiro[1][1].getMarker() == tabuleiro[0][2].getMarker() && tabuleiro[0][2].getMarker() == Node.Marker.O)) {
//                    return Resultado.DERROTA;
//                }
//                if (tabuleiro[y][x].getMarker() == Node.Marker.B) {
//                    espacoLivre = true;
//                }
//            }
//        }
//        return espacoLivre ? Resultado.EMJOGO : Resultado.EMPATE;
//    }

    private Node[][] clear() {
        Node[][] tabuleiro = new Node[3][3];
        for (Node[] tabuleiro1 : tabuleiro) {
            for (int x = 0; x < tabuleiro.length; x++) {
                tabuleiro1[x] = new Node(Node.Infinite.NONE, Node.Marker.B);
            }
        }
        return tabuleiro;
    }

//    private BoardTicTacToeState buildBoardMoment(final Node node) {
//        int height = 0;
//        Node[][] tabuleiro = this.clear();
//        Node nodeTemp = node;
//        while (nodeTemp.getParent() != null) {
//            tabuleiro[nodeTemp.getPosY()][nodeTemp.getPosX()].setMarker(nodeTemp.getMarker());
//            nodeTemp = nodeTemp.getParent();
//            height++;
//        }
//        return new BoardTicTacToeState(tabuleiro, height);
//    }
}
