package br.com.mertins.viewgame;

/**
 *
 * @author mertins
 */
public interface TabuleiroEvent {

    public enum Botao {
        BUTTON1, BUTTON2, BUTTON3
    }

    void eventClick(Botao bota, String local);
}
