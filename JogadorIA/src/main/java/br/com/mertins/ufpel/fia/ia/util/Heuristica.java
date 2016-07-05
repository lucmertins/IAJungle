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
    public Move process(Board board,Move move) {
        Random gerador = new Random();
        int numero = gerador.nextInt(10);
        move.setValue(move.getPeca()!=null?numero*move.getPeca().getTipo().peso():numero);
        int value=0;
        if ((move.getJogador()==Jogador.Jogador1&&move.getPosicaoNova()==Posicao.D7)  
                ||(move.getJogador()==Jogador.Jogador2&&move.getPosicaoNova()==Posicao.D1)){
            value+=100000;  // peça chega na toca
        }
        Peca pecaAdv = board.peca(Jogador.adversario(move.getJogador()),move.getPosicaoNova());
        if (pecaAdv!=null){
            Peca.Tipo tipoJog = move.getPeca().getTipo();
            Peca.Tipo tipoAdv = pecaAdv.getTipo();
            if (tipoJog==Peca.Tipo.Rat && tipoAdv==Peca.Tipo.Elefant){
                value =+30000;
            }else if (tipoJog.peso()>=tipoAdv.peso()){
                value=+20000;
            }else{
                value-=20000;
            }
        }
        // movimento em direção a toca leva vantagem
        value-=board.distanciaToca(move.getJogador(),move.getPosicaoNova());
        move.setValue(value);
        return move;
    }
}
