package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author mertins
 */
public class Board extends Tabuleiro {

    public Board() {
    }

    public Board(Peca[][] tabuleiro) {
        super(tabuleiro);
    }

    public Move[] findCandidates(Move move) {
        List<Move> vagos = new ArrayList();
        this.pecasNoTabuleiro(move.getJogador()).stream().forEach((peca) -> {
            Posicao posicaoAtual = this.posicao(peca);
            Posicao[] posicoesPossiveis = posicoesPossiveis(peca);
            for (Posicao posicaoNova : posicoesPossiveis) {
                Move candMove = new Move(Move.Infinite.NONE, move.getJogador());
                candMove.setPeca(peca);
                candMove.setPosicaoAtual(posicaoAtual);
                candMove.setPosicaoNova(posicaoNova);
                vagos.add(candMove);
            }
        });
        return vagos.toArray(new Move[vagos.size()]);
    }

    public Situacao gameOver(Move move) {
        if (move.getPeca() == null) {
            return Situacao.UNDEFINED;
        }

        // efetuar jogada! Peca, posicao velha, posicao nova e jogador?
        if (this.pecasNoTabuleiro(Jogador.Jogador1).isEmpty()) {
            return Situacao.WINJOG2;
        }
        if (this.pecasNoTabuleiro(Jogador.Jogador2).isEmpty()) {
            return Situacao.WINJOG1;
        }
        //verificar se alguma peça chegou na toca adversaria

        return Situacao.UNDEFINED;
    }

    private Posicao[] posicoesPossiveis(Peca peca) {
        List<Posicao> lista = new ArrayList<>();
        Posicao posicaoAtual = this.posicao(peca);
        int posXAtual = Posicao.posX(posicaoAtual);
        int posYAtual = Posicao.posY(posicaoAtual);
        switch (posXAtual) {
            case 0:
                lista.add(Posicao.posicao(1, posYAtual));
                break;
            case 6:
                lista.add(Posicao.posicao(5, posYAtual));
                break;
            default:
                lista.add(Posicao.posicao(posXAtual - 1, posYAtual));
                lista.add(Posicao.posicao(posXAtual + 1, posYAtual));
                break;
        }
        switch (posYAtual) {
            case 0:
                lista.add(Posicao.posicao(posXAtual, 1));
                break;
            case 6:
                lista.add(Posicao.posicao(posXAtual, 5));
                break;
            default:
                lista.add(Posicao.posicao(posXAtual, posYAtual - 1));
                lista.add(Posicao.posicao(posXAtual, posYAtual + 1));
                break;
        }
        Posicao[] posicoes = lista.toArray(new Posicao[lista.size()]);
        return posicoes;
    }

}
