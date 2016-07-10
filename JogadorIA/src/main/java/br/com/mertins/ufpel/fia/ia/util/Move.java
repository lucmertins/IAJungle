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
public class Move {

    private int value;
    private final List<Move> children = new ArrayList<>();
    private Move parent = null;
    private final Jogador jogador;
    private Peca peca;
    private Tabuleiro.Posicao posicaoAtual;
    private Tabuleiro.Posicao posicaoNova;
    boolean ia;

    public Move(Jogador jogador, boolean ia) {
        this.jogador = jogador;
        
//        this.value = ia?Integer.MIN_VALUE:Integer.MAX_VALUE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
//        Move temp = this.parent;
//        while (temp != null) {
//            if (ia && temp.value < this.value) {
//                temp.value = this.value;
//            } else if (!ia && temp.value > this.value) {
//                temp.value = this.value;
//            }
//            temp = temp.parent;
//        }
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Tabuleiro.Posicao getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Tabuleiro.Posicao posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Tabuleiro.Posicao getPosicaoNova() {
        return posicaoNova;
    }

    public void setPosicaoNova(Tabuleiro.Posicao posicaoNova) {
        this.posicaoNova = posicaoNova;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public List<Move> getChildren() {
        return children;
    }

    public Move max(Move other) {
        if (other == null) {
            return this;
        }
        return this.value >= other.value ? this : other;
    }

    public Move min(Move other) {
        if (other == null) {
            return this;
        }
        return this.value < other.value ? this : other;
    }

    public void addChild(Move node) {
        node.parent = this;
        children.add(node);
    }

    public Move getParent() {
        return parent;
    }

}
