package br.com.mertins.ufpel.fia.ia;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;
import br.com.mertins.ufpel.fia.ia.listener.MovimentoEvent;
import br.com.mertins.ufpel.fia.ia.listener.MovimentoListener;
import br.com.mertins.ufpel.fia.network.Conexao;
import br.com.mertins.ufpel.fia.ia.monitor.Observator;
import br.com.mertins.ufpel.fia.ia.util.Board;
import br.com.mertins.ufpel.fia.ia.util.Move;
import br.com.mertins.ufpel.fia.ia.util.WeightTunning;
import br.com.mertins.ufpel.fia.network.Mensagem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mertins
 */
public class JogadorIA {

    private final MiniMax minimax;
    private MovimentoListener listener;

    public JogadorIA(WeightTunning weightTunning) {
        Observator observator = new Observator(Observator.ALGORITHMS.MINIMAX);
        minimax = new MiniMax(observator, 5, weightTunning);
    }

    public JogadorIA(WeightTunning weightTunning, MovimentoListener listener) {
        this(weightTunning);
        this.listener = listener;
    }

    public void run() {
        Conexao conexao = new Conexao();
        try {
            conexao.conectarServidor("localhost");
            boolean conectado = true;
            while (conectado) {
                Mensagem receber = conexao.receber();
                switch (receber.getTipo()) {
                    case JOGOESTABELECIDO:
                        conexao.setJogador(receber.getJogador());
                        Board board = new Board(receber.getTabuleiroState());
                        conexao.setTabuleiro(board);
                        if (listener != null) {
                            listener.action(new EvtMov(receber, receber.getJogador() == conexao.getJogador() ? "IA" : "ADVERSÁRIO", MovimentoEvent.Status.BEGIN));
                        }
                        if (conexao.getJogador() == Jogador.Jogador1) {
                            jogar(conexao, receber);
                        }
                        break;
                    case JOGADAINVALIDA:
                    case JOGADA:
                        if (listener != null) {
                            listener.action(new EvtMov(receber, receber.getJogador() == conexao.getJogador() ? "ADVERSÁRIO" : "IA", MovimentoEvent.Status.MOV));
                        }
                        if (receber.getJogador() == conexao.getJogador()) {
                            jogar(conexao, receber);
                        }
                        break;
                    case CHEGOUTOCA:
                    case COMEUTODASPECAS:
                        if (receber.getJogador() == conexao.getJogador()) {
                            System.out.printf("%s IA ganhou!\n", receber.getJogador());
                        } else {
                            System.out.printf("%s IA perdeu!\n", receber.getJogador());
                        }
                        if (listener != null) {
                            listener.action(new EvtMov(receber, receber.getJogador() == conexao.getJogador() ? "IA" : "ADVERSÁRIO", MovimentoEvent.Status.END));
                        }
                    case CONEXAOENCERRADA:
                        conectado = false;
                        conexao.close();
                        conexao = null;
                        break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(JogadorIA.class.getName()).log(Level.SEVERE, "Falha", ex);
        }
    }

    private void jogar(Conexao conexao, Mensagem msgRecebida) throws IOException {
        Board board = new Board(msgRecebida.getTabuleiroState());
        conexao.setTabuleiro(board);
        Move move = minimax.run(conexao.getJogador(), msgRecebida.getTabuleiroState());
        Mensagem msg = new Mensagem();
        msg.setJogador(conexao.getJogador());
        msg.setPosicaoAtual(move.getPosicaoAtual());
        msg.setPosicaoNova(move.getPosicaoNova());
        msg.setTipoPeca(move.getPeca().getTipo());
        msg.setTipo(Mensagem.TipoMsg.JOGADA);
        conexao.enviar(msg);
    }

    private class EvtMov implements MovimentoEvent {

        private final Mensagem msg;
        private final String tipo;
        private final Status status;

        public EvtMov(Mensagem msg, String tipo, Status status) {
            this.msg = msg;
            this.tipo = tipo;
            this.status = status;
        }

        @Override
        public Jogador getJogador() {
            return msg.getJogador();
        }

        @Override
        public Tabuleiro.Posicao getPosicaoAtual() {
            return msg.getPosicaoAtual();
        }

        @Override
        public Tabuleiro.Posicao getPosicaoNova() {
            return msg.getPosicaoNova();
        }

        @Override
        public Peca.Tipo getTipoPeca() {
            return msg.getTipoPeca();
        }

        @Override
        public TabuleiroState getTabuleiroState() {
            return msg.getTabuleiroState();
        }

        @Override
        public String getTipo() {
            return tipo;
        }

        @Override
        public Status getStatus() {
            return this.status;
        }
    }

    public static void main(String[] args) {
        WeightTunning weightTunning = new WeightTunning();
        weightTunning.setWinTocaWeight(1000);
        weightTunning.setWinAllPecasWeight(1000);
        weightTunning.setNearTocaManhattanWeight(5);
        weightTunning.setNearTocaYWeight(3);

        weightTunning.addWeightTunning(Peca.Tipo.Elefant, 1);
        weightTunning.addWeightTunning(Peca.Tipo.Tiger, 1);
        weightTunning.addWeightTunning(Peca.Tipo.Dog, 1);
        weightTunning.addWeightTunning(Peca.Tipo.Rat, 1);

        JogadorIA jogador = new JogadorIA(weightTunning, (MovimentoEvent evt) -> {
            if (evt.getStatus() != MovimentoEvent.Status.BEGIN) {
                System.out.printf("%s %s moveu %s de %s para %s\n",
                        evt.getTipo(),
                        evt.getJogador() == Jogador.Jogador1 ? Jogador.Jogador2 : Jogador.Jogador1,
                        evt.getTipoPeca().descricao(), evt.getPosicaoAtual(), evt.getPosicaoNova());
            } else {
                System.out.printf("Jogo iniciando com jogador %s - %s\n", evt.getJogador(), evt.getTipo());
            }
        });
        jogador.run();
    }
}
