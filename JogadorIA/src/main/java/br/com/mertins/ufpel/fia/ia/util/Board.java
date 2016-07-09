package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mertins
 */
public class Board extends Tabuleiro {

    public Board() {
    }

    public Board(TabuleiroState tabuleiroState) {
        super(tabuleiroState);
    }

    public Move[] findCandidates(Jogador jogador) {
        List<Move> possiveis = new ArrayList();
        List<Peca> pecasNoTabuleiro = this.pecasNoTabuleiro(jogador);
        for (Peca peca : pecasNoTabuleiro) {
            Posicao posicaoAtual = this.posicao(peca);
            Posicao[] posicoesPossiveis = posicoesPossiveis(peca);
            for (Posicao posicaoNova : posicoesPossiveis) {
                if (this.movimentoValido(peca, posicaoAtual, posicaoNova) != Movimento.INVALIDO) {
                    Move candMove = new Move(jogador);
                    candMove.setPeca(peca);
                    candMove.setPosicaoAtual(posicaoAtual);
                    candMove.setPosicaoNova(posicaoNova);
                    possiveis.add(candMove);
                }
            }
        }
        return possiveis.toArray(new Move[possiveis.size()]);
    }

    public static Situacao gameOver(Move move, TabuleiroState tabuleiroState) {
        if (move.getPeca() == null) {
            return Situacao.UNDEFINED;
        }
        Board boardTemp = new Board(tabuleiroState);
        if (tabuleiroState.getTabuleiro()[0][3].getJogador() == Jogador.Jogador1) {
            return Situacao.WINJOG1;
        }
        if (tabuleiroState.getTabuleiro()[6][3].getJogador() == Jogador.Jogador2) {
            return Situacao.WINJOG2;
        }
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
        List<Posicao> listaFinal = new ArrayList<>();
        for (Posicao posicao : lista) {
            if (this.movimentoValido(peca, posicaoAtual, posicao) != Movimento.INVALIDO) {
                listaFinal.add(posicao);
            }
        }
        Posicao[] posicoes = listaFinal.toArray(new Posicao[listaFinal.size()]);
        return posicoes;
    }

    public void print(Jogador jogador) {
        Peca[][] tabuleiro = this.getTabuleiroState().getTabuleiro();
        System.out.printf("\n\n****Jogador da vez [%s] ****\n\n", jogador);
        for (int j = 0; j < tabuleiro.length; j++) {
            System.out.printf("\n");
            for (int i = 0; i < tabuleiro.length; i++) {
                Peca peca = tabuleiro[j][i];
                if (peca == null) {
                    System.out.printf("\t_ __\t");
                } else {
                    System.out.printf("\t%s %s\t", peca.getTipo().sigla(), peca.getJogador().sigla());
                }
            }
        }
        System.out.println("\n\n");
    }

    public void print(Jogador jogador, Move move, boolean adversario) {
        System.out.printf("\n%s %s avaliando mover %s de %s para %s   valor [%d] \n", adversario ? "Adversario" : "IA", jogador, move.getPeca().getTipo().descricao(), move.getPosicaoAtual(), move.getPosicaoNova(), move.getValue());
    }
}
