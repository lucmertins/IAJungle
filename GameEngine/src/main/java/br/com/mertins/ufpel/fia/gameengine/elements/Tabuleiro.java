package br.com.mertins.ufpel.fia.gameengine.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author mertins
 */
public class Tabuleiro {

    public enum Situacao {
        WINJOG1, WINJOG2, DRAW, UNDEFINED
    }

    public enum Posicao {

        A7, B7, C7, D7, E7, F7, G7,
        A6, B6, C6, D6, E6, F6, G6,
        A5, B5, C5, D5, E5, F5, G5,
        A4, B4, C4, D4, E4, F4, G4,
        A3, B3, C3, D3, E3, F3, G3,
        A2, B2, C2, D2, E2, F2, G2,
        A1, B1, C1, D1, E1, F1, G1;

        public static Posicao posicao(int x, int y) {
            return Posicao.values()[x + y * 7];
        }

        public static int posX(Posicao posicao) {
            return posicao.toString().charAt(0) - 'A';
        }

        public static int posY(Posicao posicao) {
            return '7' - posicao.toString().charAt(1) ;
        }

    }
    private final int tamanho = 7;
    private Peca[][] tabuleiro;
    private Situacao situacao;

    public Tabuleiro() {
    }

    public Tabuleiro(Peca[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public void init() {
        tabuleiro = new Peca[tamanho][tamanho];
        Peca peca = new Peca(Jogador.Jogador1, Peca.Tipo.Toca);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.D1)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.D1)] = peca;
        peca = new Peca(Jogador.Jogador1, Peca.Tipo.Rat);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.F1)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.F1)] = peca;
        peca = new Peca(Jogador.Jogador1, Peca.Tipo.Elefant);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.B1)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.B1)] = peca;
        peca = new Peca(Jogador.Jogador1, Peca.Tipo.Tiger);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.E2)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.E2)] = peca;
        peca = new Peca(Jogador.Jogador1, Peca.Tipo.Dog);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.C2)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.C2)] = peca;

        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Toca);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.D7)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.D7)] = peca;
        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Rat);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.B7)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.B1)] = peca;
        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Elefant);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.F7)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.F1)] = peca;
        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Tiger);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.C6)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.C6)] = peca;
        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Dog);
        tabuleiro[Tabuleiro.Posicao.posY(Tabuleiro.Posicao.E6)][Tabuleiro.Posicao.posX(Tabuleiro.Posicao.E6)] = peca;
        this.situacao = Situacao.UNDEFINED;
    }

    public boolean move(Peca peca, Posicao posIni, Posicao posFim) {
        if (movimentoValido(peca, posIni, posFim)) {
            System.out.printf("[%s] [%s] [%s] [%s]\n", peca.getJogador(), peca.getTipo(), posIni, posFim);
            tabuleiro[Posicao.posY(posIni)][Posicao.posX(posIni)] = null;
            tabuleiro[Posicao.posY(posFim)][Posicao.posX(posFim)] = peca;
            return true;
        }
        return false;
    }

    public Posicao posicao(Jogador jogador, Peca peca) {
        for (int y = 0; y < tabuleiro.length; y++) {
            for (int x = 0; x < tabuleiro.length; x++) {
                if (tabuleiro[y][x] != null && tabuleiro[y][x].getJogador() == jogador && tabuleiro[y][x].getTipo() == peca.getTipo()) {
                    return Tabuleiro.Posicao.posicao(x, y);
                }
            }
        }
        return null;
    }

    public List<Peca> pecasNoTabuleiro(Jogador jogador) {
        List<Peca> lista = new ArrayList();
        try {
            for (Peca[] tab : tabuleiro) {
                for (int x = 0; x < tabuleiro.length; x++) {
                    if (tab[x] != null && tab[x].getJogador() == jogador && tab[x].getTipo() != Peca.Tipo.Toca) {
                        lista.add(tab[x]);
                    }
                }
            }
        } catch (Exception ex) {
        }
        Collections.sort(lista);
        return lista;
    }

    public Peca[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Peca[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    private boolean movimentoValido(Peca peca, Posicao posIni, Posicao PosFim) {
        if (this.situacao == Situacao.UNDEFINED && peca.getTipo().movel()) {        // jogo em aberto e peça é movivel?
            Peca newPos = tabuleiro[Posicao.posY(PosFim)][Posicao.posX(PosFim)];
            if (newPos == null) {                                                   // local futuro esta vazio?
                return true;
            } else if (newPos.getJogador() != peca.getJogador()) {                  // não é sobreposição de peças do mesmo jogador?
                if (newPos.getTipo() == Peca.Tipo.Toca) {                           // ganhou o jogo?
                    this.situacao = peca.getJogador() == Jogador.Jogador1 ? Situacao.WINJOG1 : Situacao.WINJOG2;
                    return true;
                } else if (peca.getTipo() == Peca.Tipo.Rat && newPos.getTipo() == Peca.Tipo.Elefant // rato comendo elefante?
                        || ((peca.getTipo().peso() >= newPos.getTipo().peso() // ou peça de peso >= e não é elefante comendo rato?
                        && !(peca.getTipo() == Peca.Tipo.Elefant && newPos.getTipo() == Peca.Tipo.Rat)))) {
                    return true;
                }
            }
        }
        return false;
    }

}
