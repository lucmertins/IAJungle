package br.com.mertins.ufpel.fia.network;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Tabuleiro;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author mertins
 */
public class Conexao {

    /**
     *
     * @author mertins
     */
    public enum Status {

        AGUARDANDOJOGADOR, JOGANDO
    }

    public enum Papel {
        SERVIDOR, CLIENTE
    }
    private Socket socket;
    private static ServerSocket serverSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Status status = Status.AGUARDANDOJOGADOR;
    private Tabuleiro tabuleiro;
    private Conexao adversario;
    private Jogador jogador;
    private boolean vezdajogada = false;
    private final int CLIENTE_JAVA = 1;
    private final int CLIENTE_C = 2;
    private int tipoCliente;
    private Papel papel;

    public Conexao(Socket socket, ObjectOutputStream out, ObjectInputStream in, Tabuleiro tabuleiro, Conexao adversario, Jogador jogador) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.tabuleiro = tabuleiro;
        this.adversario = adversario;
        this.jogador = jogador;
    }

    public Conexao() {
    }

    private void preparaClient() throws IOException {
        if (serverSocket == null) {
            serverSocket = new ServerSocket(8888);
        }
    }

    public void aguardarCliente() throws IOException {
        this.papel = Papel.SERVIDOR;
        this.preparaClient();
        socket = serverSocket.accept();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        tipoCliente = socket.getInputStream().read();
    }

    public void conectarServidor(String local) throws UnknownHostException, IOException {
        this.papel = Papel.CLIENTE;
        this.tipoCliente = CLIENTE_JAVA;
        socket = new Socket(local, 8888);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        socket.getOutputStream().write(CLIENTE_JAVA);
    }

    public void enviar(Mensagem msg) throws IOException {
        out.reset();
        if (this.papel == Papel.CLIENTE || this.tipoCliente == CLIENTE_JAVA) {
            out.writeObject(msg);
        } else {
            // gravar para o C!!
        }
    }

    public Mensagem receber() throws IOException, ClassNotFoundException {
        if (this.papel == Papel.CLIENTE || this.tipoCliente == CLIENTE_JAVA) {
            return (Mensagem) in.readObject();
        } else {
            return null;   //leitura C como?
        }
    }

    public Status getStatus() {
        return status;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
        if (jogador != null) {
            this.status = Status.JOGANDO;
        } else {
            this.status = Status.AGUARDANDOJOGADOR;
        }
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public Conexao getAdversario() {
        return adversario;
    }

    public void setAdversario(Conexao adversario) {
        this.adversario = adversario;
    }

    public void vezDoJogo() {
        this.vezdajogada = true;
        if (this.adversario != null) {
            this.adversario.vezdajogada = false;
        }
    }

    public boolean isVezdajogada() {
        return vezdajogada;
    }

    public void close() {
        try {
            in.close();
        } catch (Exception ex) {
        }
        try {
            out.close();
        } catch (Exception ex) {
        }
        try {
            socket.close();
        } catch (Exception ex) {
        }
    }

    public void endServer() {
        this.close();
        try {
            serverSocket.close();
        } catch (Exception ex) {
        }
    }

    public String getIp() {
        if (this.socket != null && !this.socket.isClosed()) {
            return this.socket.getInetAddress().getHostAddress();
        }
        return null;
    }

    public int getPort() {
        if (this.socket != null && !this.socket.isClosed()) {
            return this.socket.getPort();
        }
        return 0;
    }
}
