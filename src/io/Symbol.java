package io;

public class Symbol implements Comparable<Symbol> {
    private int length;
    private char sym;
    private double prob; //this should be able to take into account for the encoder. Not neccessarily the decoder.

    public Symbol(int length, char sym)
    {
        this.length = length;
        this.sym = sym;
    }
    public Symbol(int length, char sym, double prob)
    {
        this.length = length;
        this.sym = sym;
        this.prob = prob;
    }

    public char getSym() {
        return sym;
    }

    public double getProb() {
        return prob;
    }

    public int getLength() {
        return length;
    }

    @Override
    public int compareTo(Symbol o) {
        if(this.length < o.length) {
            return -1;
        }
        if(this.length > o.length) {
            return 1;
        }
        return 0;
    }
}
