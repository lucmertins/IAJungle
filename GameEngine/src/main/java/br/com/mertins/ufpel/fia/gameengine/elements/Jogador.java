package br.com.mertins.ufpel.fia.gameengine.elements;

/**
 *
 * @author mertins
 */
public enum Jogador {
    Jogador1, Jogador2;

    public static Jogador adversario(Jogador jogador) {
        return jogador == Jogador1 ? Jogador2 : Jogador1;
    }
}
