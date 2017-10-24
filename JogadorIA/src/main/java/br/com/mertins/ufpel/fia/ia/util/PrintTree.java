package br.com.mertins.ufpel.fia.ia.util;

/**
 *
 * @author mertins
 */
public class PrintTree {

    public StringBuilder print(Move move) {
        StringBuilder sb = new StringBuilder();
        print(move, "", true, sb);
        return sb;
    }

    private void print(Move move, String prefix, boolean isTail, StringBuilder sb) {
        String jogador=move.getJogador().toString();
        String movimento = move.getPosicaoAtual() == null ? "ROOT" : String.format("%s->%s", move.getPosicaoAtual(), move.getPosicaoNova());
        String text = move.getValue() == null ? "null" : move.getValue().toString();

        sb.append(String.format("%s%s(%s-%s) [%s] \n", prefix, (isTail ? "└── " : "├── "), jogador,movimento, text));

        for (int i = 0; i < move.getChildren().size() - 1; i++) {
            print( move.getChildren().get(i),prefix + (isTail ? "             " : "│            "), false, sb);
        }
        if (move.getChildren().size() > 0) {
            print(move.getChildren().get(move.getChildren().size() - 1),prefix + (isTail ? "             " : "│            "), true, sb);
        }
    }
}
