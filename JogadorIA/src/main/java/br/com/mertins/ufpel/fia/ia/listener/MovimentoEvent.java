package br.com.mertins.ufpel.fia.ia.listener;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;

/**
 *
 * @author mertins
 */
public interface MovimentoEvent {
     Jogador getJogador();
     Tabuleiro.Posicao getPosicaoAtual();
     Tabuleiro.Posicao getPosicaoNova();
     Peca.Tipo getTipoPeca();
     TabuleiroState getTabuleiroState();
}
