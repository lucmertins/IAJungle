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

    public enum ValueAssessment {
        SMALLER, LARGER, EQUAL, INVALID
    }
    private Integer value;
    private final List<Move> children = new ArrayList<>();
    private Move parent = null;
    private final Jogador jogador;
    private Peca peca;
    private Tabuleiro.Posicao posicaoAtual;
    private Tabuleiro.Posicao posicaoNova;
    boolean ia;
    private Tabuleiro.Movimento movimento;

    public Move(Jogador jogador, boolean ia) {
        this.jogador = jogador;

//        this.value = ia?Integer.MIN_VALUE:Integer.MAX_VALUE;
    }

    public Integer getValue() {
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
        int val1 = this.value == null ? Integer.MIN_VALUE : this.value;
        int val2 = other.value == null ? Integer.MIN_VALUE : other.value;
        return val1 >= val2 ? this : other;
    }

    public Move min(Move other) {
        if (other == null) {
            return this;
        }
        int val1 = this.value == null ? Integer.MAX_VALUE : this.value;
        int val2 = other.value == null ? Integer.MAX_VALUE : other.value;
        return val1 < val2 ? this : other;
    }

    public ValueAssessment compareValueMax(Integer value) {
        if (this.value == null && value == null) {
            return ValueAssessment.INVALID;
        } else if (this.value == null) {
            return ValueAssessment.SMALLER;
        } else if (value == null) {
            return ValueAssessment.LARGER;
        } else if (this.value == value) {
            return ValueAssessment.EQUAL;
        } else {
            return this.value > value ? ValueAssessment.LARGER : ValueAssessment.SMALLER;
        }
    }

    public ValueAssessment compareValueMin(Integer value) {
        if (this.value == null && value == null) {
            return ValueAssessment.INVALID;
        } else if (this.value == null) {
            return ValueAssessment.LARGER;
        } else if (value == null) {
            return ValueAssessment.SMALLER;
        } else if (this.value == value) {
            return ValueAssessment.EQUAL;
        } else {
            return this.value < value ? ValueAssessment.SMALLER : ValueAssessment.LARGER;
        }

    }

    public void addChild(Move node) {
        node.parent = this;
        children.add(node);
    }

    public Move getParent() {
        return parent;
    }

    public Tabuleiro.Movimento getMovimento() {
        return movimento;
    }

    public void setMovimento(Tabuleiro.Movimento movimento) {
        this.movimento = movimento;
    }

}
