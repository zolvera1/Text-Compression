package Huffman;

public class LeafNode extends Node {
    private int symbol;
    private int count;

    public LeafNode(int symbol) {
        this.symbol = symbol;
        count = 0;
    }

    public LeafNode(int symbol, int count){
        this.count = count;
        this.symbol = symbol;
    }

    @Override
    int height() {
        return 0;
    }

    @Override
    int count() {
        return count;
    }

    @Override
    boolean isLeaf() {
        return true;
    }

    @Override
    boolean isFull() {
        return true;
    }

    @Override
    boolean insertSymbol(int length, int symbol) {
        throw new RuntimeException("Can't call this function as this is a leaf node.");
    }

    @Override
    Node left() {
        throw new RuntimeException("Leaf nodes do not have children!");
    }

    @Override
    Node right() {
        throw new RuntimeException("Leaf nodes do not have children!");
    }

    @Override
    int symbol() {
        return symbol;
    }

    public int getSymbol() {
        return symbol;
    }
}
