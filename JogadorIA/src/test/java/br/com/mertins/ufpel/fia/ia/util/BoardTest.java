package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mertins
 */
public class BoardTest {

    public BoardTest() {
    }

    @Test
    public void testDistanciaXToca() {
        Board instance = new Board();
        assertEquals(3, instance.distanciaXToca(Jogador.Jogador1, Tabuleiro.Posicao.A1));
        assertEquals(0, instance.distanciaXToca(Jogador.Jogador1, Tabuleiro.Posicao.D4));
        assertEquals(3, instance.distanciaXToca(Jogador.Jogador1, Tabuleiro.Posicao.G1));
        assertEquals(2, instance.distanciaXToca(Jogador.Jogador1, Tabuleiro.Posicao.F1));
        assertEquals(1, instance.distanciaXToca(Jogador.Jogador1, Tabuleiro.Posicao.C7));
        assertEquals(3, instance.distanciaXToca(Jogador.Jogador2, Tabuleiro.Posicao.A7));
        assertEquals(0, instance.distanciaXToca(Jogador.Jogador2, Tabuleiro.Posicao.D4));
        assertEquals(3, instance.distanciaXToca(Jogador.Jogador2, Tabuleiro.Posicao.G7));
        assertEquals(1, instance.distanciaXToca(Jogador.Jogador2, Tabuleiro.Posicao.E1));
    }

    @Test
    public void testDistanciaYToca() {
        Board instance = new Board();
        assertEquals(6, instance.distanciaYToca(Jogador.Jogador1, Tabuleiro.Posicao.A1));
        assertEquals(3, instance.distanciaYToca(Jogador.Jogador1, Tabuleiro.Posicao.D4));
        assertEquals(6, instance.distanciaYToca(Jogador.Jogador1, Tabuleiro.Posicao.G1));
        assertEquals(6, instance.distanciaYToca(Jogador.Jogador1, Tabuleiro.Posicao.F1));
        assertEquals(0, instance.distanciaYToca(Jogador.Jogador1, Tabuleiro.Posicao.C7));
        assertEquals(6, instance.distanciaYToca(Jogador.Jogador2, Tabuleiro.Posicao.A7));
        assertEquals(3, instance.distanciaYToca(Jogador.Jogador2, Tabuleiro.Posicao.D4));
        assertEquals(6, instance.distanciaYToca(Jogador.Jogador2, Tabuleiro.Posicao.G7));
        assertEquals(0, instance.distanciaYToca(Jogador.Jogador2, Tabuleiro.Posicao.E1));
    }
}
