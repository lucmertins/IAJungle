package br.com.mertins.ufpel.fia.ia.util;

import br.com.mertins.ufpel.fia.gameengine.elements.Peca;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mertins
 */
public class WeightTunning {

    private int winTocaWeight = 1000;
    private int winAllPecasWeight = 1000;
    private int nearTocaManhattanWeight = 5;
    private int nearTocaYWeight = 3;
    private Map<Peca.Tipo, Integer> pecasWeight = new HashMap<>();

    public WeightTunning() {
        pecasWeight.put(Peca.Tipo.Elefant, 1);
        pecasWeight.put(Peca.Tipo.Tiger, 1);
        pecasWeight.put(Peca.Tipo.Dog, 1);
        pecasWeight.put(Peca.Tipo.Rat, 1);
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

}
