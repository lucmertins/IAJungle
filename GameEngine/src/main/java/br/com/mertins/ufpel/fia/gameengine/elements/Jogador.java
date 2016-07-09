package br.com.mertins.ufpel.fia.gameengine.elements;

/**
 *
 * @author mertins
 */
public enum Jogador {
    Jogador1, Jogador2;

    public String sigla() {
        switch (this) {
            case Jogador1:
                return "J1";
            case Jogador2:
                return "J2";
            default:
                return "";
        }
    }

    public static Jogador adversario(Jogador jogador) {
        return jogador == Jogador1 ? Jogador2 : Jogador1;
    }
}
