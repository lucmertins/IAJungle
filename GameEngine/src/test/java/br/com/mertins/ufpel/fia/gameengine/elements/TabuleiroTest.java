package br.com.mertins.ufpel.fia.gameengine.elements;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mertins
 */
public class TabuleiroTest {

    public TabuleiroTest() {
    }

    @Test
    public void testMove() {
        assertEquals(Tabuleiro.Posicao.A1, Tabuleiro.Posicao.posicao(0, 6));
        assertEquals(Tabuleiro.Posicao.A2, Tabuleiro.Posicao.posicao(0, 5));
        assertEquals(Tabuleiro.Posicao.B1, Tabuleiro.Posicao.posicao(1, 6));
        assertEquals(Tabuleiro.Posicao.D4, Tabuleiro.Posicao.posicao(3,3));
        assertEquals(Tabuleiro.Posicao.G7, Tabuleiro.Posicao.posicao(6, 0));
    }

    @Test
    public void testPosX() {
        assertEquals(0, Tabuleiro.Posicao.posX(Tabuleiro.Posicao.A1));
        assertEquals(0, Tabuleiro.Posicao.posX(Tabuleiro.Posicao.A2));
        assertEquals(1, Tabuleiro.Posicao.posX(Tabuleiro.Posicao.B1));
        assertEquals(3, Tabuleiro.Posicao.posX(Tabuleiro.Posicao.D4));
        assertEquals(6, Tabuleiro.Posicao.posX(Tabuleiro.Posicao.G7));

    }
    
    @Test
    public void testPosY() {
        assertEquals(6, Tabuleiro.Posicao.posY(Tabuleiro.Posicao.A1));
        assertEquals(5, Tabuleiro.Posicao.posY(Tabuleiro.Posicao.A2));
        assertEquals(6, Tabuleiro.Posicao.posY(Tabuleiro.Posicao.B1));
        assertEquals(3, Tabuleiro.Posicao.posY(Tabuleiro.Posicao.D4));
        assertEquals(0, Tabuleiro.Posicao.posY(Tabuleiro.Posicao.G7));

    }

}
