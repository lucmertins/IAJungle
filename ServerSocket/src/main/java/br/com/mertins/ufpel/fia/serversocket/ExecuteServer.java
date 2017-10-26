package br.com.mertins.ufpel.fia.serversocket;

import br.com.mertins.ufpel.fia.network.Conexao;
import br.com.mertins.ufpel.fia.network.Mensagem;
import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                        conexao.aguardarCliente();   //aguardar cliente
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
                        Thread.sleep(1000);      // a cada segundo verifica se não tem duplas para jogar 
                        synchronized (clientesConectados) {
                            if (!clientesConectados.isEmpty()) {
                                Conexao temp = null;
                                for (Conexao conexao : clientesConectados) {
                                    if (conexao.getStatus() == Conexao.Status.AGUARDANDOJOGADOR) {
                                        if (temp == null) {
                                            temp = conexao;
                                        } else {
                                            duplas(temp, conexao);    //junta dois jogadores que estavam aguardando
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
                jog1.setJogador(Jogador.Jogador1);
                jog2.setJogador(Jogador.Jogador2);
                Tabuleiro tabuleiro = new Tabuleiro();
                tabuleiro.init();
                jog1.setTabuleiro(tabuleiro);
                jog2.setTabuleiro(tabuleiro);
                Mensagem msg = new Mensagem();
                msg.setTipo(Mensagem.TipoMsg.JOGOESTABELECIDO);
                msg.setJogador(Jogador.Jogador1);
                msg.setTabuleiroState(tabuleiro.getTabuleiroState());
                enviaMsg(jog1, msg);
                msg.setJogador(Jogador.Jogador2);
                enviaMsg(jog2, msg);
                
                jog1.setAdversario(jog2);
                jog2.setAdversario(jog1);
                jog1.vezDoJogo();
                FileWriter arquivo = arquivo(jog1, jog2);
                boolean jogando = true;
                Conexao conexao = jog1;
                while (jogando) {
                    Mensagem receber;
                    try {
                        receber = conexao.receber();     // Enviar sempre o tabuleiro e a movimentação efetuada
                        Peca peca = tabuleiro.peca(receber.getJogador(), receber.getPosicaoAtual());
                        msg = new Mensagem();
                        msg.setTabuleiroState(tabuleiro.getTabuleiroState());
                        if (peca == null) {
                            msg.setJogador(receber.getJogador());
                            msg.setTipo(Mensagem.TipoMsg.JOGADAINVALIDA);
                        } else {
                            Tabuleiro.Movimento move = tabuleiro.move(peca, receber.getPosicaoAtual(), receber.getPosicaoNova());
                            switch (move) {
                                case INVALIDO:
                                    msg.setJogador(receber.getJogador());
                                    msg.setTipo(Mensagem.TipoMsg.JOGADAINVALIDA);
                                    break;
                                case VALIDO:
                                    msg.setJogador(receber.getJogador() == Jogador.Jogador1 ? Jogador.Jogador2 : Jogador.Jogador1);
                                    msg.setTipo(Mensagem.TipoMsg.JOGADA);
                                    msg.setPosicaoAtual(receber.getPosicaoAtual());
                                    msg.setPosicaoNova(receber.getPosicaoNova());
                                    msg.setTipoPeca(receber.getTipoPeca());
                                    arquivo.write(String.format("[MOV][%s][%s][%s][%s]\n", receber.getJogador(), peca.getTipo().descricao(), receber.getPosicaoAtual(), receber.getPosicaoNova()));
                                    arquivo.flush();
                                    if (jog1.isVezdajogada()) {
                                        jog2.vezDoJogo();
                                        conexao = jog2;
                                    } else {
                                        jog1.vezDoJogo();
                                        conexao = jog1;
                                    }
                                    break;
                                case WINALLPECAS:
                                    msg.setJogador(receber.getJogador());
                                    msg.setTipo(Mensagem.TipoMsg.COMEUTODASPECAS);
                                    msg.setPosicaoNova(receber.getPosicaoNova());
                                    msg.setTipoPeca(receber.getTipoPeca());
                                    arquivo.write(String.format("[ALL][%s][%s][%s][%s]\n", receber.getJogador(), peca.getTipo().descricao(), receber.getPosicaoAtual(), receber.getPosicaoNova()));
                                    arquivo.flush();
                                    jogando = false;
                                    break;
                                case WINTOCA:
                                    msg.setJogador(receber.getJogador());
                                    msg.setTipo(Mensagem.TipoMsg.CHEGOUTOCA);
                                    msg.setPosicaoNova(receber.getPosicaoNova());
                                    msg.setTipoPeca(receber.getTipoPeca());
                                    arquivo.write(String.format("[TOC][%s][%s][%s][%s]\n", receber.getJogador(), peca.getTipo().descricao(), receber.getPosicaoAtual(), receber.getPosicaoNova()));
                                    arquivo.flush();
                                    jogando = false;
                                    break;
                            }
                        }
                        jog1.enviar(msg);
                        jog2.enviar(msg);
                    } catch (Exception ex) {
                        msg = new Mensagem();
                        msg.setTipo(Mensagem.TipoMsg.CONEXAOENCERRADA);
                        jogando = false;
                        try {
                            jog1.enviar(msg);
                        } catch (Exception ex2) {
                        }
                        try {
                            jog2.enviar(msg);
                        } catch (Exception ex2) {
                        }
                        
                        Logger.getLogger(ExecuteServer.class.getName()).log(Level.SEVERE, "Falha em receber conexao", ex);
                    } finally {
                        if (!jogando) {
                            clientesConectados.remove(jog1);
                            clientesConectados.remove(jog2);
                            try {
                                arquivo.close();
                            } catch (Exception ex2) {
                            }
                        }
                    }
                    
                }
                
            }
        }.start();
    }
    
    private boolean enviaMsg(Conexao conexao, Mensagem msg) {
        try {
            Logger.getLogger(ExecuteServer.class.getName()).log(Level.INFO, String.format("Enviando msg [%s]", conexao.getJogador()));
            conexao.enviar(msg);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ExecuteServer.class.getName()).log(Level.SEVERE, "Jogador não esta mais conectado", ex);
            return false;
        }
    }
    
    private FileWriter arquivo(Conexao jog1, Conexao jog2) {
        String property = System.getProperty("user.home");
        File folder = new File(String.format("%s%sIAJungleWork", property, File.separator));
        if (!folder.exists()) {
            folder.mkdir();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String nome = String.format("%s%s%s%d%d.log", folder.getAbsolutePath(), File.separator, sdf.format(new Date()), jog1.getPort(), jog2.getPort());
        try {
            return new FileWriter(nome);
        } catch (IOException ex) {
            Logger.getLogger(ExecuteServer.class.getName()).log(Level.SEVERE, "Não criou o arquivo", ex);
            return null;
        }
    }
    
    public static void main(String[] args) {
        new ExecuteServer().run();
        
    }
}
