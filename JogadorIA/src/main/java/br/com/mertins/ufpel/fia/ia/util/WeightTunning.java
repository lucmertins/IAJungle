package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mertins
 */
public class WeightTunning {

    private int winTocaWeight;
    private int winAllPecasWeight;
    private int nearTocaManhattanWeight;
    private int nearTocaYWeight;
    private final Map<Peca.Tipo, Integer> pecasWeight = new HashMap<>();

    public WeightTunning() {
        pecasWeight.put(Peca.Tipo.Elefant, 1);
        pecasWeight.put(Peca.Tipo.Tiger, 1);
        pecasWeight.put(Peca.Tipo.Dog, 1);
        pecasWeight.put(Peca.Tipo.Rat, 1);
    }

    public void addWeightTunning(Peca.Tipo tipo, int weight) {
        pecasWeight.put(tipo, weight);
    }

    public int getWinTocaWeight() {
        return winTocaWeight;
    }

    public int getWinAllPecasWeight() {
        return winAllPecasWeight;
    }

    public int getNearTocaManhattanWeight() {
        return nearTocaManhattanWeight;
    }

    public int getNearTocaYWeight() {
        return nearTocaYWeight;
    }

    public int getPecaWeight(Peca.Tipo tipo) {
        return pecasWeight.get(tipo);
    }

    public void setWinTocaWeight(int winTocaWeight) {
        this.winTocaWeight = winTocaWeight;
    }

    public void setWinAllPecasWeight(int winAllPecasWeight) {
        this.winAllPecasWeight = winAllPecasWeight;
    }

    public void setNearTocaManhattanWeight(int nearTocaManhattanWeight) {
        this.nearTocaManhattanWeight = nearTocaManhattanWeight;
    }

    public void setNearTocaYWeight(int nearTocaYWeight) {
        this.nearTocaYWeight = nearTocaYWeight;
    }

}
