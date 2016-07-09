package br.com.mertins.ufpel.fia.gameengine.elements;

import java.io.Serializable;

/**
 *
 * @author mertins
 */
public class TabuleiroState implements Serializable {

    private final Peca[][] tabuleiro;

    public TabuleiroState(Peca[][] tab) {
        this.tabuleiro = new Peca[tab.length][tab.length];
        for (int j = 0; j < tabuleiro.length; j++) {
            for (int i = 0; i < tabuleiro.length; i++) {
                this.tabuleiro[j][i] = tab[j][i];
            }
        }
    }

    public Peca[][] getTabuleiro() {
        return tabuleiro;
    }
}
