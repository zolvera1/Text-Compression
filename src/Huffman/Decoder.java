package Huffman;
import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Decoder {

    public static void main(String[] args) throws IOException, InsufficientBitsLeftException {

            String input = "data/recompressed.txt";
            String output = "data/reuncompressed.txt";
            FileInputStream fis = new FileInputStream(input);
            InputStreamBitSource _bitsource =  new InputStreamBitSource(fis);
            System.out.println("Decoding file " + input);

            List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();
            int numSymbols;
            for(int i = 0; i < 256; i++) {
                //decode next symbol and write out to file
                int length = _bitsource.next(8);
               symbols_with_length.add(new SymbolWithCodeLength(i,length));
            }
            //sort!
            symbols_with_length.sort(null);
            //time to use the tree.
            CodeTree tree = new CodeTree(symbols_with_length);
            numSymbols = _bitsource.next(32);
            int[] symCounts = new int[256];


            try{
                FileOutputStream fos = new FileOutputStream(output);
            for(int i = 0; i != numSymbols; i++) {
                //tp help with entropy
                int sym = tree.decode(_bitsource);
                symCounts[sym]++;
                fos.write(sym);
            }
            //calculating the entropy for part 3
                for(int i= 0; i != 256; i++) {
                    double prob = ((double)symCounts[i]/(double)numSymbols);
                    if(symCounts[i] >= 0) {
                        System.out.println("Probability of symbols at " + i + " is " + prob);
                    }
                }
            fos.flush();
            fos.close();
            fis.close();
            System.out.println("Finished!");
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(InsufficientBitsLeftException e) {
            e.printStackTrace();
        }
    }
}
