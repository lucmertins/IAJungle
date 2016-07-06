package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro.Posicao;
import java.util.Random;

/**
 *
 * @author mertins
 */
public class Heuristica {

    public Move process(Board board, Move move) {
        Random gerador = new Random();
        int numero = gerador.nextInt(4);
        int value = move.getPeca() != null ? numero * move.getPeca().getTipo().peso() : numero;
        if ((move.getJogador() == Jogador.Jogador1 && move.getPosicaoNova() == Posicao.D7)
                || (move.getJogador() == Jogador.Jogador2 && move.getPosicaoNova() == Posicao.D1)) {
            value += 1000;  // peça chega na toca
        }
        Peca pecaAdv = board.peca(Jogador.adversario(move.getJogador()), move.getPosicaoNova());
        if (pecaAdv != null) {
            Peca.Tipo tipoJog = move.getPeca().getTipo();
            Peca.Tipo tipoAdv = pecaAdv.getTipo();
            if (tipoJog == Peca.Tipo.Rat && tipoAdv == Peca.Tipo.Elefant) {
                value = +800;
            } else if (tipoJog.peso() >= tipoAdv.peso()) {
                value = +500;
            } else {
                value -= 800;
            }
        }
        // movimento em direção a toca leva vantagem

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

//        value += move.getPeca().getTipo().peso();

        move.setValue(value);
        //System.out.printf("Heuristica peça[%s] novaposicao[%s] value[%d]\n", move.getPeca(), move.getPosicaoNova(), value);
        return move;
    }
}
