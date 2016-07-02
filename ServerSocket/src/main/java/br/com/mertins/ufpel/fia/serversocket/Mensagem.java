package br.com.mertins.ufpel.fia.serversocket;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mertins
 */
public class Mensagem implements Serializable {

    public enum TipoMsg {
        CANDIDATOJOGO, AGUARDANDOADVERSARIO,JOGOESTABELECIDO,CONEXAOENCERRADA,JOGADA,JOGADAINVALIDA
    }
    private int id;
    private Date data;
    private TipoMsg tipo;
    private Jogador jogador;
    private Tabuleiro.Posicao posicaoAtual;
    private Tabuleiro.Posicao posicaoNova;
    private Peca.Tipo tipoPeca;
    private Peca[][] tabuleiro;

    public Mensagem() {
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoMsg getTipo() {
        return tipo;
    }

    public void setTipo(TipoMsg tipo) {
        this.tipo = tipo;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
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

    public Peca[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Peca[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public Peca.Tipo getTipoPeca() {
        return tipoPeca;
    }

    public void setTipoPeca(Peca.Tipo tipoPeca) {
        this.tipoPeca = tipoPeca;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mensagem other = (Mensagem) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        return hash;
    }

}
