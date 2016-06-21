/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mertins.ufpel.fia.serversocket;

import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mertins
 */
public class Mensagem implements Serializable {

    private int id;
    private Date data;
    private Peca.Jogador jogador;
    private Tabuleiro.Posicao posicaoAtual;
    private Tabuleiro.Posicao posicaoNova;

    public Mensagem() {
    }

    public Mensagem(int id, Date data) {
        this.id = id;
        this.data = data;
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
