package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
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
//        }else if(board.peca){   //
//            board.
        }
        
        
        
//        Resultado gameOver = this.gameOver(node);
//        switch(gameOver){
//            case VITORIA:
//                node.setValue(1);
//                break;
//            case DERROTA:
//                node.setValue(-1);
//                break;
//            default:
//                node.setValue(0);
//        }
        return move;
    }
}
