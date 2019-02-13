package Huffman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffEncode {

    public static void main(String[] args) throws IOException {
        String input_file_name = "data/uncompressed.txt";
        String output_file_name = "data/recompressed.txt";

        FileInputStream fis = new FileInputStream(input_file_name);

        int[] symbol_counts = new int[256];
        int num_symbols = 0;
        // Read in each symbol (i.e. byte) of input file and
        // update appropriate count value in symbol_count
        //  Should end up with total number of symbols
        //        // (i.e., length of file) as num_symbolss
        int next = fis.read();
        while (next != -1) {
            //keep track of num of symbols and their count.
            symbol_counts[next]++;
            num_symbols++;
            next = fis.read();
        }


        // Close input file
        fis.close();
        double entropy = 0;
        double achieved = 0;
        // Create array of symbol values
        int[] symbols = new int[256];
        for (int i = 0; i < 256; i++) {
            symbols[i] = i;
            Double prob = new Double((double) (symbol_counts[i]) / (double) num_symbols);
            if (prob > 0) {
                entropy += ((double) prob * (-1) * (Math.log((double) prob)) / Math.log(2));
            }

        }
        //Now to print out the theoretical entropy
        System.out.println("The theoreitcal entropy is " + entropy);
        // Create encoder using symbols and their associated counts from file.

        Encoder encoder = new Encoder(symbols, symbol_counts);

        // Open output stream.
        FileOutputStream fos = new FileOutputStream(output_file_name);
        OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

        //now for part 3. we will use the equations KMP gave and also the book.
        //1)
        // Write out code lengths for each symbol as 8 bit value to output file.
        for (int i = 0; i != 256; i++) {
            //already have it to write
            Double probs = new Double((double) symbol_counts[i] / (double) num_symbols);
            if (probs > 0) {
                achieved += ((double) probs * (double) encoder.getCode(i).length());
            }
            bit_sink.write(encoder.getCode(i).length(), 8);
        }
        //now for the encoder achieved entropy
        System.out.println("The encoder achieved the entropy of " + achieved);
        // Write out total number of symbols as 32 bit value.
        bit_sink.write(num_symbols, 32);
        // Reopen input file.
        fis = new FileInputStream(input_file_name);

        // Go through input file, read each symbol (i.e. byte),
        // look up code using encoder.getCode() and write code
        // out to output file.
        next = fis.read();
        while (next != -1) {
            encoder.encode(next, bit_sink);
            next = fis.read();
        }

        // Pad output to next word.
        bit_sink.padToWord();

        // Close files.
        fis.close();
        fos.close();
    }
}
