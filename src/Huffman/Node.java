package Huffman;
import java.util.Comparator;

abstract class Node implements Comparable<Node> {
   abstract int height();
   abstract int count();
   abstract boolean isLeaf();
   abstract boolean isFull();
   abstract boolean insertSymbol(int length, int symbol);
   abstract Node left();
   abstract Node right();
   abstract int symbol();


    @Override
    public int compareTo(Node o) {
        if(count() != o.count()) {
            return count() - o.count();
        } else {
            return height() - o.height();
        }
    }
}


