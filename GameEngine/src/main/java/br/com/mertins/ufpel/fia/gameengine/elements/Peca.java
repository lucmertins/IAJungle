package br.com.mertins.ufpel.fia.gameengine.elements;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author mertins
 */
public class Peca implements Serializable, Comparable<Object> {

    public enum Tipo {
        Toca, Elefant, Tiger, Dog, Rat;

        public int peso() {
            switch (this) {
                case Toca:
                    return 100;
                case Elefant:
                    return 10;
                case Tiger:
                    return 8;
                case Dog:
                    return 6;
                case Rat:
                default:
                    return 4;
            }
        }

        public boolean movel() {
            return this != Toca;
        }

        public String descricao() {
            switch (this) {
                case Toca:
                    return "Toca";
                case Elefant:
                    return "Elefante";
                case Tiger:
                    return "Tigre";
                case Dog:
                    return "Cachorro";
                case Rat:
                    return "Rato";
                default:
                    return "Sem descrição";
            }
        }

        public String sigla() {
            switch (this) {
                case Toca:
                    return "T";
                case Elefant:
                    return "E";
                case Tiger:
                    return "T";
                case Dog:
                    return "C";
                case Rat:
                    return "R";
                default:
                    return "";
            }
        }
    }
    private final Jogador jogador;
    private final Tipo tipo;

    Peca(Jogador jogador, Tipo tipo) {
        this.jogador = jogador;
        this.tipo = tipo;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return tipo.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.jogador);
        hash = 89 * hash + Objects.hashCode(this.tipo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Peca other = (Peca) obj;
        if (this.jogador != other.jogador) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Peca) {
            Peca outra = (Peca) o;
            return this.tipo.peso() > outra.tipo.peso() ? 1 : this.tipo.peso() < outra.tipo.peso() ? -1 : 0;
        }
        return 0;
    }
}
