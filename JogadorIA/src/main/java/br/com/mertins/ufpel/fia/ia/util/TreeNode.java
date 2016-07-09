package br.com.mertins.ufpel.fia.ia.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mertins
 */
public class TreeNode {

    final String name;
    final List<TreeNode> children;

    public TreeNode(Move raiz) {
        this("raiz");
        children.add(addMove(raiz));
    }

    private TreeNode(String name) {
        this(name, new ArrayList<>());
    }

    private TreeNode(String name, List<TreeNode> children) {
        this.name = name;
        this.children = children;
    }

    private void add(TreeNode node) {
        this.children.add(node);
    }

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + name);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1).print(prefix + (isTail ? "    " : "│   "), true);
        }
    }

    private TreeNode addMove(Move raiz) {
        Move temp = raiz;
        TreeNode node = new TreeNode("raiz");
        for (Move child : temp.getChildren()) {
            TreeNode t = new TreeNode(String.format("%s %s->%s [%d]", child.getPeca().getTipo(), child.getPosicaoAtual(), child.getPosicaoNova(), child.getValue()));
            node.add(t);
            addChilds(child, t);
        }
        return node;
    }

    private void addChilds(Move mchild, TreeNode tchild) {
        Move temp = mchild;
        for (Move child : temp.getChildren()) {
            TreeNode t = new TreeNode(String.format("%s %s->%s [%d]", child.getPeca().getTipo(), child.getPosicaoAtual(), child.getPosicaoNova(), child.getValue()));
            tchild.add(t);
            addChilds(child, t);
        }
    }

    public static void main(String[] args) {
        TreeNode tf = new TreeNode("f");
        TreeNode asdf = new TreeNode("asdf");
        TreeNode e = new TreeNode("e");
        TreeNode d = new TreeNode("d");
        TreeNode b = new TreeNode("b");
        TreeNode a = new TreeNode("a");
        TreeNode c = new TreeNode("c");
        TreeNode f = new TreeNode("f");
        TreeNode z = new TreeNode("z");

        z.add(c);
        z.add(d);
        z.add(e);
        z.add(f);

        c.add(a);
        c.add(b);

        e.add(asdf);

        z.print();

    }
}
