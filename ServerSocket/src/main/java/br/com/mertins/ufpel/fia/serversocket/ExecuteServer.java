package br.com.mertins.ufpel.fia.serversocket;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mertins
 */
public class ExecuteServer {

    private final Queue<Conexao> clientesConectados = new LinkedList<>();

    private void run() {
        new Thread() {
            @Override
            public void run() {
                try {

                    while (true) {
                        Conexao conexao = new Conexao();
//                        Logger.getLogger(ExecuteServer.class.getName()).log(Level.INFO, "Aguardando cliente");
                        conexao.aguardarCliente();
                        synchronized (clientesConectados) {
                            clientesConectados.add(conexao);
                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(ExecuteServer.class.getName()).log(Level.SEVERE, "Falha aguardando cliente", ex);
                }
            }

        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
//                        Logger.getLogger(ExecuteServer.class.getName()).log(Level.INFO, "Acordou");
                        synchronized (clientesConectados) {
                            if (!clientesConectados.isEmpty()) {
//                                Logger.getLogger(ExecuteServer.class.getName()).log(Level.INFO, "Lista não esta vazia");
                                Conexao temp = null;
                                for (Conexao conexao : clientesConectados) {
                                    if (conexao.getStatus() == Conexao.Status.AGUARDANDOJOGADOR) {
                                        if (temp == null) {
                                            temp = conexao;

                                        } else {
                                            //ligar jogadores
                                            duplas(temp, conexao);
                                            temp = null;
                                        }
                                    }
                                }
                                if (temp != null) {
                                    Mensagem msg = new Mensagem();
                                    msg.setTipo(Mensagem.TipoMsg.AGUARDANDOADVERSARIO);
                                    try {
                                        temp.enviar(msg);
                                    } catch (Exception ex) {
                                        Logger.getLogger(ExecuteServer.class.getName()).log(Level.SEVERE, "Jogador em espera saiu fora", ex);
                                        temp.close();
                                        clientesConectados.remove(temp);
                                    }
                                }
                            }
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExecuteServer.class.getName()).log(Level.SEVERE, "Falha esperando 1 segundo para avaliar fila", ex);
                }
            }

        }.start();
    }

    private void duplas(Conexao jog1, Conexao jog2) {
        new Thread() {
            @Override
            public void run() {
                jog1.setJogadorCliente(Jogador.Jogador1);
                jog2.setJogadorCliente(Jogador.Jogador2);
                Tabuleiro tabuleiro=new Tabuleiro();
                jog1.setTabuleiro(tabuleiro);
                jog2.setTabuleiro(tabuleiro);
                Mensagem msg = new Mensagem();
                msg.setTipo(Mensagem.TipoMsg.JOGOESTABELECIDO);
                msg.setJogador(Jogador.Jogador1);
                msg.setTabuleiro(tabuleiro.getTabuleiro());
                boolean ativo = enviaMsg(jog1, msg);
                if (ativo == true) {
                    msg.setJogador(Jogador.Jogador2);
                }
                ativo = enviaMsg(jog2, msg);
            }
        }.start();
    }

    private boolean enviaMsg(Conexao conexao, Mensagem msg) {
        try {
            Logger.getLogger(ExecuteServer.class.getName()).log(Level.INFO, String.format("Enviando msg [%s]", conexao.getJogadorCliente()));
            conexao.enviar(msg);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ExecuteServer.class.getName()).log(Level.SEVERE, "Jogador não esta mais conectado", ex);
            return false;
        }
    }

    public static void main(String[] args) {
        new ExecuteServer().run();

    }
}
