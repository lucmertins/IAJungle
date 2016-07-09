package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Peca;

/**
 *
 * @author mertins
 */
public class BoardState {

    private final Peca[][] tabuleiro;

    public BoardState(Peca[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public Peca[][] getTabuleiro() {
        return tabuleiro;
    }

}
