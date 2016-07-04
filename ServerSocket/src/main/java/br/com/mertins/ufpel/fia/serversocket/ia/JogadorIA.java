package br.com.mertins.ufpel.fia.serversocket.ia;

import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.ia.util.Board;

/**
 *
 * @author mertins
 */
public class JogadorIA {
    private final Board board;

    public JogadorIA() {
        this.board=new Board();
    }
    
    
    public void setTabuleiro(Tabuleiro tabuleiro){
        board.setTabuleiro(tabuleiro.getTabuleiro());
        
    }
}
