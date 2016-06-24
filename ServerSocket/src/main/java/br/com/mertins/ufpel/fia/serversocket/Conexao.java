package br.com.mertins.ufpel.fia.serversocket;

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
    private Socket socket;
    private static ServerSocket serverSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Status status = Status.AGUARDANDOJOGADOR;
    private Tabuleiro tabuleiro;

    private Jogador jogadorCliente;

    public Conexao() {
    }

    private void preparaClient() throws IOException {
        if (serverSocket == null) {
            serverSocket = new ServerSocket(8888);
        }
    }

    public void aguardarCliente() throws IOException {
        this.preparaClient();
        socket = serverSocket.accept();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void conectarServidor(String local) throws UnknownHostException, IOException {
        socket = new Socket(local, 8888);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void enviar(Mensagem x) throws IOException {
        out.writeObject(x);
    }

    public Mensagem receber() throws IOException, ClassNotFoundException {
        return (Mensagem) in.readObject();
    }

    public Status getStatus() {
        return status;
    }

    public Jogador getJogadorCliente() {
        return jogadorCliente;
    }

    public void setJogadorCliente(Jogador jogadorCliente) {
        this.jogadorCliente = jogadorCliente;
        if (jogadorCliente != null) {
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
}
