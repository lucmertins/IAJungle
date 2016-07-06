package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import java.util.ArrayList;
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
        Board boardTemp = new Board(this.getTabuleiro());
        Movimento movimento = boardTemp.move(move.getPeca(), move.getPosicaoAtual(), move.getPosicaoNova());
        if (movimento == Movimento.WINALLPECAS || movimento == Movimento.WINTOCA) {
            return move.getJogador() == Jogador.Jogador1 ? Situacao.WINJOG1 : Situacao.WINJOG2;
        }
        return Situacao.UNDEFINED;
    }

    public int distanciaToca(Jogador jogador, Posicao ini) {
        return distanciaXToca(jogador, ini) + distanciaYToca(jogador, ini);
    }

    public int distanciaXToca(Jogador jogador, Posicao ini) {

        if (jogador == Jogador.Jogador1) {
            return Math.abs(Posicao.posX(ini) - Posicao.posX(Posicao.D7));
        }
        return Math.abs(Posicao.posX(ini) - Posicao.posX(Posicao.D1));
    }

    public int distanciaYToca(Jogador jogador, Posicao ini) {
        if (jogador == Jogador.Jogador1) {
            return Math.abs(Posicao.posY(ini) - Posicao.posY(Posicao.D7));
        }
        return Math.abs(Posicao.posY(ini) - Posicao.posY(Posicao.D1));
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
// remover os movimentos que conflitam com peças adversarias maiores
//        List<Posicao> listaFinal = new ArrayList<>();
//        lista.stream().filter((posicao) -> (this.movimentoValido(peca, posicaoAtual, posicao) != Movimento.INVALIDO)).forEach((posicao) -> {
//            listaFinal.add(posicao);
//        });

        Posicao[] posicoes = lista.toArray(new Posicao[lista.size()]);
        return posicoes;
    }

}
