package Huffman;

public class SymbolWithCodeLength implements Comparable<SymbolWithCodeLength> {
   private int codeLength;
   private int value;

    public SymbolWithCodeLength(int value, int codeLength) {
    this.value = value;
    this.codeLength = codeLength;
   }

   public int getCodeLength() {
        return codeLength;
   }

   public int getValue() {
        return value;
   }
    @Override
    public int compareTo(SymbolWithCodeLength o) {
        if(this.codeLength < o.getCodeLength()) {
            return -1;
        } else if(this.codeLength > o.getCodeLength()) {
            return 1;
        } else {
            if(this.value < o.getValue()){
                return -1;
            }else if (this.value > o.getValue()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
