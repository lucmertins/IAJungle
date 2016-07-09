package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro.Posicao;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;
import java.util.Random;

/**
 *
 * @author mertins
 */
public class Heuristica {

    public Move process(TabuleiroState state, Move move) {
        Board board = new Board(state);
        Random gerador = new Random();
        int numero = gerador.nextInt(4) + 1;
        int value = move.getPeca() != null ? numero * move.getPeca().getTipo().peso() : numero;
        if ((move.getJogador() == Jogador.Jogador1 && move.getPosicaoNova() == Posicao.D7)
                || (move.getJogador() == Jogador.Jogador2 && move.getPosicaoNova() == Posicao.D1)) {
            value += 1000000;  // peça chega na toca
        }
        if (board.peca(move.getJogador(), move.getPosicaoNova()) == null) {

            Peca pecaAdv = board.peca(Jogador.adversario(move.getJogador()), move.getPosicaoNova());
            if (pecaAdv != null) {
                Peca.Tipo tipoJog = move.getPeca().getTipo();
                Peca.Tipo tipoAdv = pecaAdv.getTipo();
                if (tipoJog == Peca.Tipo.Rat && tipoAdv == Peca.Tipo.Elefant) {
                    value = +800;
                } else if (tipoJog.peso() >= tipoAdv.peso()) {
                    value = +500;
                } else {
                    value -= 50000;
                }
            }
            // movimento em direçãoa toca leva vantagem

            if (board.distanciaToca(move.getJogador(), move.getPosicaoAtual()) > board.distanciaToca(move.getJogador(), move.getPosicaoNova())) {
                value += 250;
            }
            if (board.distanciaYToca(move.getJogador(), move.getPosicaoAtual()) > board.distanciaYToca(move.getJogador(), move.getPosicaoNova())) {
                value += 150;
            }
            if (board.distanciaXToca(move.getJogador(), move.getPosicaoAtual()) > board.distanciaXToca(move.getJogador(), move.getPosicaoNova())) {
                value += 100;
            }

            value += 100 - 9 * board.distanciaXToca(move.getJogador(), move.getPosicaoNova());
            value += 100 - 3 * board.distanciaYToca(move.getJogador(), move.getPosicaoNova());
        } else {
            value = -500000;
        }
//        value += move.getPeca().getTipo().peso();
        move.setValue(value);
        //System.out.printf("Heuristica peça[%s] novaposicao[%s] value[%d]\n", move.getPeca(), move.getPosicaoNova(), value);
        return move;
    }
}
