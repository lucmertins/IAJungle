package br.com.mertins.ufpel.fia.gameengine.competitiva;

import br.com.mertins.ufpel.fia.gameengine.competitiva.util.Node;
import br.com.mertins.ufpel.fia.gameengine.competitiva.util.Observator;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author mertins
 */
public class MiniMax {

    private final Observator observator;
//    protected final BoardTicTacToe board;
    private final int depth;

    public MiniMax(Observator observator,int depth) {
        this.observator = observator;
        this.depth=depth;
//        this.board = new BoardTicTacToe();
    }

//    public Node run() {
//        Node node=minimax(board.getNodeBegin(), this.depth, true);
//        this.observator.difference();
//        return node;
//    }
//
//    public void print(Node node) {
//        this.board.print(node);
//    }

    public String time(Duration duration) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        String format = fmt.format(duration.addTo(LocalDateTime.of(0, 1, 1, 0, 0)));
        return format;
    }

//    private Node minimax(Node node, int depth, boolean maximizingPlayer) {
//        if (depth == 0||this.board.gameOver(node)!=BoardTicTacToe.Resultado.EMJOGO) {
//            Node temp=this.board.heuristic(node);
//            this.observator.gameOver(node);
//            return temp;
//        } else if (maximizingPlayer) {
//            Node bestValue = new Node(Node.Infinite.NEGATIVE, Node.Marker.X);
//            for (Node nodeChild : this.board.findCandidates(node)) {
//                Node temp = minimax(nodeChild, depth - 1, false);
//                bestValue = bestValue.max(temp);
//            }
//            return bestValue;
//        } else {
//            Node bestValue = new Node(Node.Infinite.POSITIVE,Node.Marker.O);
//            for (Node nodeChild : this.board.findCandidates(node)) {
//                Node temp = minimax(nodeChild, depth - 1, true);
//                bestValue = bestValue.min(temp);
//            }
//            return bestValue;
//        }
//    }
}
