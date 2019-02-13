package Huffman;

public class InternalNode extends Node {
    private Node leftChild;
    private Node rightChild;

    private boolean result;
    public InternalNode() {
        leftChild = null;
        rightChild= null;
    }
    public InternalNode(Node leftChild,Node rightChild) {
        this.leftChild= leftChild;
        this.rightChild = rightChild;
    }

   public void setLeftChild(Node n) {

        leftChild = n;
   }
   public void setRightChild(Node n) {
        rightChild = n;
   }
    @Override
    int height() {
       int leftHeight = leftChild.height();
       int rightHeight = rightChild.height();
        return Math.max(leftHeight,rightHeight) +1;
    }

    @Override
    int count() {
        return leftChild.count() + rightChild.count();
    }

    @Override
    boolean isLeaf() {
        return false;
    }

    @Override
    boolean isFull() {
         return this.leftChild != null && this.rightChild != null
         && this.leftChild.isFull() && this.rightChild.isFull();
    }

    @Override
    boolean insertSymbol(int length, int symbol) {
        //make sure the parameters are valid
        assert length > 0;
        assert  symbol > 0;

        //we're going to try to go left.
        /*
        we're going to reduce the length by 1 for each recursion until length is 1.
         */
        if(leftChild != null) {
            if(!leftChild.isFull()) {
                return leftChild.insertSymbol(length -1, symbol);
            } else if(rightChild != null) {
                if(!rightChild.isFull()) {
                    return rightChild.insertSymbol(length -1, symbol);
                } else {
                    return false;
                }
            } else {
                if(length ==1) {
                    rightChild = new LeafNode(symbol);
                    return true;
                } else {
                    rightChild = new InternalNode();
                    return rightChild.insertSymbol(length -1, symbol);
                }
            }
        }  else {
            if(length ==1) {
                leftChild = new LeafNode(symbol);
                return true;
            } else {
                leftChild = new InternalNode();
                return leftChild.insertSymbol(length-1, symbol);
            }
        }
    }

    @Override
    Node left() {
        return leftChild;
    }

    @Override
    Node right() {
        return rightChild;
    }

    @Override
    int symbol() {
        throw new RuntimeException("Can't call it on internal node!");
    }

}
