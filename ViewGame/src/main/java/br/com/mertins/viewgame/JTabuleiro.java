package br.com.mertins.viewgame;

import br.com.mertins.ufpel.fia.gameengine.elements.Jogador;
import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import br.com.mertins.ufpel.fia.gameengine.elements.TabuleiroState;
import br.com.mertins.viewgame.TabuleiroEvent.Botao;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

/**
 *
 * @author mertins
 */
public class JTabuleiro extends javax.swing.JPanel {

    /**
     * Creates new form JTabuleiro
     */
    public JTabuleiro() {
        initComponents();
        
    }
    private final int sizeBloco = 60;
    private final int size = 420;
    private final int ajustey = 44;
    private final int ajustex = 12;
    private TabuleiroState tabuleiroState;
    private TabuleiroEvent eventSource;

    public void setTabuleiroState(TabuleiroState tabuleiroState) {
        this.tabuleiroState = tabuleiroState;
        this.repaint();
    }

    public void addEventClick(TabuleiroEvent event) {
        this.eventSource = event;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, size, size);
        g2d.setColor(Color.blue);
        g2d.drawRect(0, 0, size, size);

        for (int i = 0; i < 7; i++) {
            g2d.drawLine(0, i * sizeBloco, size, i * sizeBloco);
        }
        for (int i = 0; i < 7; i++) {
            g2d.drawLine(i * sizeBloco, 0, i * sizeBloco, size);
        }
        g2d.setColor(Color.red);
        Font font = new Font("Serif", Font.PLAIN, 14);
        g2d.setFont(font);
        for (int j = 6; j > -1; j--) {
            int count = 1;
            for (int i = 7; i > -1; i--) {
                g2d.drawString(String.format("%s%s", (char) (j + 'A'), count++), j * sizeBloco + 2, i * sizeBloco - 2);
            }
        }

        g2d.setColor(Color.green);
        g2d.fillOval(184, 12, 50, 30); // Toca encima
        g2d.setColor(Color.yellow);
        g2d.fillOval(184, 376, 50, 30); // Toca embaixo
        g2d.setColor(Color.black);
        font = new Font("Serif", Font.PLAIN, 16);
        g2d.setFont(font);
        g2d.drawString("Jog2", 194, 30);
        g2d.drawString("Jog1", 194, 396);
        if (tabuleiroState != null && tabuleiroState.getTabuleiro() != null) {
            Peca[][] pecas = tabuleiroState.getTabuleiro();
            for (int y = 0; y < pecas.length; y++) {
                for (int x = 0; x < pecas.length; x++) {
                    if (pecas[y][x] != null) {
                        g2d.setColor(pecas[y][x].getJogador() == Jogador.Jogador1 ? Color.yellow : Color.green);
                        font = new Font("Serif", Font.PLAIN, 38);
                        g2d.setFont(font);
                        switch (pecas[y][x].getTipo()) {
                            case Toca:
                                break;
                            case Tiger:
                                g2d.drawString("T", x * sizeBloco + ajustex, y * sizeBloco + ajustey);
                                font = new Font("Serif", Font.PLAIN, 16);
                                g2d.setFont(font);
                                g2d.drawString(String.valueOf(pecas[y][x].getTipo().peso()),
                                        x * sizeBloco + ajustex + 28, y * sizeBloco + ajustey);
                                break;
                            case Dog:
                                g2d.drawString("C", x * sizeBloco + ajustex, y * sizeBloco + ajustey);
                                font = new Font("Serif", Font.PLAIN, 16);
                                g2d.setFont(font);
                                g2d.drawString(String.valueOf(pecas[y][x].getTipo().peso()),
                                        x * sizeBloco + ajustex + 28, y * sizeBloco + ajustey);
                                break;
                            case Rat:
                                g2d.drawString("R", x * sizeBloco + ajustex, y * sizeBloco + ajustey);
                                font = new Font("Serif", Font.PLAIN, 16);
                                g2d.setFont(font);
                                g2d.drawString(String.valueOf(pecas[y][x].getTipo().peso()),
                                        x * sizeBloco + ajustex + 28, y * sizeBloco + ajustey);
                                break;
                            case Elefant:
                                g2d.drawString("E", x * sizeBloco + ajustex, y * sizeBloco + ajustey);
                                font = new Font("Serif", Font.PLAIN, 16);
                                g2d.setFont(font);
                                g2d.drawString(String.valueOf(pecas[y][x].getTipo().peso()),
                                        x * sizeBloco + ajustex + 28, y * sizeBloco + ajustey);
                                break;
                        }
                    }
                }
            }
        }
        g2d.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 393, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (this.eventSource != null) {
            int posx = evt.getX() / sizeBloco;
            int posy = 7 - evt.getY() / sizeBloco;
            char letra = (char) ('A' + posx);
            String resultado = String.format("%s%d", letra, posy);
            Botao botao = evt.getButton() == MouseEvent.BUTTON1 ? Botao.BUTTON1
                    : evt.getButton() == MouseEvent.BUTTON2 ? Botao.BUTTON2
                            : evt.getButton() == MouseEvent.BUTTON3 ? Botao.BUTTON3 : Botao.BUTTON1;
            this.eventSource.eventClick(botao, resultado);
        }
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
