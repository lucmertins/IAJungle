package br.com.mertins.ufpel.fia.gameengine.elements;

/**
 *
 * @author mertins
 */
public class Tabuleiro {

    public enum Situacao {
        WINJOG1, WINJOG2, DRAW, UNDEFINED
    }

    public enum Posicao {
        A1, B1, C1, D1, E1, F1, G1,
        A2, B2, C2, D2, E2, F2, G2,
        A3, B3, C3, D3, E3, F3, G3,
        A4, B4, C4, D4, E4, F4, G4,
        A5, B5, C5, D5, E5, F5, G5,
        A6, B6, C6, D6, E6, F6, G6,
        A7, B7, C7, D7, E7, F7, G7;

        public static Posicao posicao(int x, int y) {
            return Posicao.values()[x + y * 7];
        }

        public static int posX(Posicao posicao) {
            return posicao.toString().charAt(0) - 'A';
        }

        public static int posY(Posicao posicao) {
            return posicao.toString().charAt(1) - '1';
        }

    }
    private final int tamanho = 7;
    private Peca[][] tabuleiro;
    private Situacao situacao;

    public Tabuleiro() {
    }

    public Tabuleiro(Peca[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public void init() {
        tabuleiro = new Peca[tamanho][tamanho];
        Peca peca = new Peca(Jogador.Jogador1, Peca.Tipo.Toca);
        tabuleiro[0][3] = peca;
        peca = new Peca(Jogador.Jogador1, Peca.Tipo.Rat);
        tabuleiro[0][1] = peca;
        peca = new Peca(Jogador.Jogador1, Peca.Tipo.Elefant);
        tabuleiro[0][5] = peca;
        peca = new Peca(Jogador.Jogador1, Peca.Tipo.Tiger);
        tabuleiro[1][2] = peca;
        peca = new Peca(Jogador.Jogador1, Peca.Tipo.Dog);
        tabuleiro[1][4] = peca;

        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Toca);
        tabuleiro[6][3] = peca;
        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Rat);
        tabuleiro[6][5] = peca;
        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Elefant);
        tabuleiro[6][1] = peca;
        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Tiger);
        tabuleiro[5][4] = peca;
        peca = new Peca(Jogador.Jogador2, Peca.Tipo.Dog);
        tabuleiro[5][2] = peca;
        this.situacao = Situacao.UNDEFINED;
    }

    public boolean move(Peca peca, Posicao posIni, Posicao posFim) {
        if (movimentoValido(peca, posIni, posFim)) {
            System.out.printf("[%s] [%s] [%s] [%s]\n", peca.getJogador(), peca.getTipo(), posIni, posFim);
            tabuleiro[Posicao.posY(posIni)][Posicao.posX(posIni)] = null;
            tabuleiro[Posicao.posY(posFim)][Posicao.posX(posFim)] = peca;
            return true;
        }
        return false;
    }

    public Peca[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Peca[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    private boolean movimentoValido(Peca peca, Posicao posIni, Posicao PosFim) {
        if (this.situacao == Situacao.UNDEFINED && peca.getTipo().movel()) {        // jogo em aberto e peça é movivel?
            Peca newPos = tabuleiro[Posicao.posY(PosFim)][Posicao.posX(PosFim)];
            if (newPos == null) {                                                   // local futuro esta vazio?
                return true;
            } else if (newPos.getJogador() != peca.getJogador()) {                  // não é sobreposição de peças do mesmo jogador?
                if (newPos.getTipo() == Peca.Tipo.Toca) {                           // ganhou o jogo?
                    this.situacao = peca.getJogador() == Jogador.Jogador1 ? Situacao.WINJOG1 : Situacao.WINJOG2;
                    return true;
                } else if (peca.getTipo() == Peca.Tipo.Rat && newPos.getTipo() == Peca.Tipo.Elefant // rato comendo elefante?
                        || ((peca.getTipo().peso() >= newPos.getTipo().peso() // ou peça de peso >= e não é elefante comendo rato?
                        && !(peca.getTipo() == Peca.Tipo.Elefant && newPos.getTipo() == Peca.Tipo.Rat)))) {
                    return true;
                }
            }
        }
        return false;
    }

}
