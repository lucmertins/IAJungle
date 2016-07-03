package br.com.mertins.ufpel.fia.gameengine.competitiva.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mertins
 */
public class Node {

    public enum Infinite {
        POSITIVE, NEGATIVE, NONE
    }

    public enum Marker {
        X, O, B;

        public Marker invert() {
            switch (this) {
                case X:
                    return O;
                case O:
                    return X;
                default:
                    return B;
            }
        }

        public char toChar() {
            switch (this) {
                case X:
                    return 'X';
                case O:
                    return '0';
                default:
                    return ' ';
            }
        }

        public char toByte() {
            switch (this) {
                case X:
                    return 1;
                case O:
                    return 2;
                default:
                    return 0;
            }
        }
    }
    private int value;
    private Marker marker;
    private final Infinite infinite;
    private final List<Node> children = new ArrayList<>();
    private Node parent = null;
    private byte posX;
    private byte posY;

    public Node(int value, Marker marker) {
        this.value = value;
        this.infinite = Infinite.NONE;
        this.marker = marker;
    }

    public Node(Infinite infinite, Marker marker) {
        this.infinite = infinite;
        this.value = 0;
        this.marker = marker;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        Node temp = this.parent;
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

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public List<Node> getChildren() {
        return children;
    }

    public byte getPosX() {
        return posX;
    }

    public void setPosX(byte posX) {
        this.posX = posX;
    }

    public byte getPosY() {
        return posY;
    }

    public void setPosY(byte posY) {
        this.posY = posY;
    }

    public Node max(Node other) {
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

    public Node min(Node other) {
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

    public void addChild(Node node) {
        node.parent = this;
        children.add(node);
    }

    public Node getParent() {
        return parent;
    }

}
