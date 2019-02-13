package Huffman;
import io.*;

import java.io.IOException;
import java.util.*;

public class CodeTree {
    private Node root;

    //we can use a map for the frequency tables and to keep track of the symbols. Maybe use a hashmap??
    public CodeTree(List<SymbolWithCodeLength> symbols) {
        root = new InternalNode();
        int i = 0;
        while(i < symbols.size()) {
            root.insertSymbol(symbols.get(i).getCodeLength(), symbols.get(i).getValue());
            i++;
        }
        assert root.isFull();
    }
    public int decode(InputStreamBitSource bit_source) throws IOException, InsufficientBitsLeftException {

            // Start at the root
            Node n = root;

            while (!n.isLeaf()) {
                // Get next bit and walk either left or right,
                // updating n to be as appropriate
                if(bit_source.next(1) == 0x1) {
                    n = n.right();
                } else {
                    n = n.left();
                }
            }

            // Return symbol at leaf
            return n.symbol();
    }
}
