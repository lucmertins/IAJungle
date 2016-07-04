package br.com.mertins.ufpel.fia.jogadoria.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mertins
 */
public class Move {

    public enum Infinite {
        POSITIVE, NEGATIVE, NONE
    }

    private int value;
    private final Infinite infinite;
    private final List<Move> children = new ArrayList<>();
    private Move parent = null;
    
    public Move(int value) {
        this.value = value;
        this.infinite = Infinite.NONE;
    }

    public Move(Infinite infinite) {
        this.infinite = infinite;
        this.value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        Move temp = this.parent;
        while (temp != null) {
            if (temp.value < this.value) {
                temp.value = this.value;
                temp = temp.parent;
            } else {
                temp = null;
            }
        }
    }

    public Infinite getInfinite() {
        return infinite;
    }

    public List<Move> getChildren() {
        return children;
    }

   
   public Move max(Move other) {
        if (other == null) {
            return this;
        }
        if (this.infinite == Infinite.NONE && other.infinite == Infinite.NONE) {
            return this.value >= other.value ? this : other;
        } else if (this.infinite == Infinite.NEGATIVE && other.infinite != Infinite.NEGATIVE) {
            return other;
        } else if (this.infinite != Infinite.NEGATIVE && other.infinite == Infinite.NEGATIVE) {
            return this;
        } else {
            return this;
        }
    }

    public Move min(Move other) {
        if (other == null) {
            return this;
        }
        if (this.infinite == Infinite.NONE && other.infinite == Infinite.NONE) {
            return this.value < other.value ? this : other;
        } else if (this.infinite == Infinite.POSITIVE && other.infinite != Infinite.POSITIVE) {
            return other;
        } else if (this.infinite != Infinite.POSITIVE && other.infinite == Infinite.POSITIVE) {
            return this;
        } else {
            return this;
        }
    }

    public void addChild(Move node) {
        node.parent = this;
        children.add(node);
    }

    public Move getParent() {
        return parent;
    }

}
