package Huffman;

import java.io.IOException;
import java.util.*;

import io.OutputStreamBitSink;

public class Encoder {
    private Map<Integer, String> _code_map;
    private Map<Integer, String> cmap;

    public Encoder(int[] symbols, int[] symbol_counts) {
        assert symbols.length == symbol_counts.length;

        // Given symbols and their associated counts, create initial
        // Huffman tree. Use that tree to get code lengths associated
        // with each symbol. Create canonical tree using code lengths.
        // Use canonical tree to form codes as strings of 0 and 1
        // characters that are inserted into _code_map.

        // Start with an empty list of nodes

        List<Node> node_list = new ArrayList<Node>();

        // Create a leaf node for each symbol, encapsulating the
        // frequency count information into each leaf.
        for (int i = 0; i < 256; i++) {
            node_list.add(new LeafNode(symbols[i], symbol_counts[i]));
        }
        // Sort the leaf nodes
        node_list.sort(null);

        // While you still have more than one node in your list...
        while (node_list.size() > 1) {
            // Remove the two nodes associated with the smallest counts
            Node low1 = node_list.remove(0);
            Node low2 = node_list.remove(0);
            // Create a new internal node with those two nodes as children.
            InternalNode internal = new InternalNode(low1, low2);
            // Add the new internal node back into the list
            node_list.add(internal);
            // Resort
            Collections.sort(node_list);
        }
        assert node_list.size() == 1;

        // Create a temporary empty mapping between symbol values and their code strings
        cmap = new HashMap<Integer, String>();

        // Start at root and walk down to each leaf, forming code string along the
        // way (0 means left, 1 means right). Insert mapping between symbol value and
        // code string into cmap when each leaf is reached.

        traverseFromRoot(node_list.get(0), "", true);

        // Create empty list of SymbolWithCodeLength objects
        List<SymbolWithCodeLength> sym_with_length = new ArrayList<SymbolWithCodeLength>();
        for (int i = 0; i < symbols.length; i++) {
            sym_with_length.add(new SymbolWithCodeLength(i, cmap.get(i).length()));
        }
        // For each symbol value, find code string in cmap and create new SymbolWithCodeLength
        // object as appropriate (i.e., using the length of the code string you found in cmap).

        // Sort sym_with_lenght
       Collections.sort(sym_with_length);

        // Now construct the canonical tree as you did in HuffmanDecodeTree constructor

        InternalNode canonical_root = new InternalNode();
        int iterator = 0;
        while(iterator < sym_with_length.size()) {
            canonical_root.insertSymbol(sym_with_length.get(iterator).getCodeLength(), sym_with_length.get(iterator).getValue());
            iterator++;
        }

        // If all went well, tree should be full.
        assert canonical_root.isFull();

        // Create code map that encoder will use for encoding

        _code_map = new HashMap<Integer, String>();

        // Walk down canonical tree forming code strings as you did before and
        // insert into map.

        traverseFromRoot(canonical_root, "", false);

    }

    public String getCode(int symbol) {
        return _code_map.get(symbol);
    }

    public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
        bit_sink.write(_code_map.get(symbol));
    }

    private void traverseFromRoot(Node root, String code, boolean bool) {
        if (!root.isLeaf()) {
            if (root.left() != null) {
                String new_code0 = code + "0";
                traverseFromRoot(root.left(), new_code0, bool);
            }
            if (root.right() != null) {
                String new_code = code + "1";
                traverseFromRoot(root.right(), new_code, bool);
            }
        } else {
            //bool
            if(bool) {
                cmap.put(root.symbol(), code);
            } else {
                _code_map.put(root.symbol(), code);
            }

        }
    }
}

