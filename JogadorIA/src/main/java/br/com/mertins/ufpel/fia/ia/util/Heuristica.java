package br.com.mertins.ufpel.fia.ia.util;

import java.util.Random;

/**
 *
 * @author mertins
 */
public class Heuristica {
    public Move process(Move move) {
        Random gerador = new Random();
        int numero = gerador.nextInt(10);
        move.setValue(move.getPeca()!=null?numero*move.getPeca().getTipo().peso():numero);
        
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
