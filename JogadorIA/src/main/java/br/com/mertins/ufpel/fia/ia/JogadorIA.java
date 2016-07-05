package br.com.mertins.ufpel.fia.ia;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import br.com.mertins.ufpel.fia.network.Conexao;
import br.com.mertins.ufpel.fia.ia.monitor.Observator;
import br.com.mertins.ufpel.fia.ia.util.Board;
import br.com.mertins.ufpel.fia.ia.util.Move;
import br.com.mertins.ufpel.fia.network.Mensagem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mertins
 */
public class JogadorIA {
    
    private MiniMax minimax;
    
    public JogadorIA() {
        Observator observator = new Observator(Observator.ALGORITHMS.MINIMAX);
        minimax = new MiniMax(observator, 6);
    }
    
    public void run() {
        Conexao conexao = new Conexao();
        try {
            conexao.conectarServidor("localhost");
            boolean conectado = true;
            while (conectado) {
                Mensagem receber = conexao.receber();
                System.out.printf(String.format("%s %s\n", receber.getTipo().toString(), receber.getJogador() == null ? "" : receber.getJogador()));
                switch (receber.getTipo()) {
                    case JOGOESTABELECIDO:
                        conexao.setJogador(receber.getJogador());
                        this.jogar(conexao, receber);
                        break;
                    case JOGADAINVALIDA:
                    case JOGADA:
                        if (receber.getJogador() == conexao.getJogador()) {
                            if (receber.getPosicaoNova() != null) {  // se não for a primeira jogada
                                System.out.printf("%s moveu %s para %s\n",
                                        conexao.getJogador() == Jogador.Jogador1 ? Jogador.Jogador2 : Jogador.Jogador1,
                                        receber.getTipoPeca().descricao(), receber.getPosicaoNova());
                            }
                            jogar(conexao, receber);
                        }
                        break;
                    case CHEGOUTOCA:
                    case COMEUTODASPECAS:
                        if (receber.getJogador() == conexao.getJogador()) {
                            System.out.printf("IA ganhou!\n");
                        } else {
                            System.out.printf("IA perdeu!\n");
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
        Board board = new Board(msgRecebida.getTabuleiro());
        conexao.setTabuleiro(board);
        minimax.setBoard(board);
        Move move = minimax.run(conexao.getJogador(), board);
        Mensagem msg = new Mensagem();
        msg.setJogador(conexao.getJogador());
        msg.setPosicaoAtual(Tabuleiro.Posicao.B1);
        msg.setPosicaoNova(Tabuleiro.Posicao.B2);
        msg.setTipoPeca(Peca.Tipo.Elefant);
        msg.setTipo(Mensagem.TipoMsg.JOGADA);
        conexao.enviar(msg);
    }
    
    public static void main(String[] args) {
        JogadorIA jogador = new JogadorIA();
        jogador.run();
    }
}
