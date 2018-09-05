package oop.ex4.data_structures;

/**
 * this class defines a node of a binary search tree
 * @author Ronen Mor.
 */
public class Node {
    private int value;
    private Node right=null, left=null;
    private int depth;

    //CONSTRUCTORS

    /**
     * default constructor of a node
     */
    public Node(){
        depth = 0;
    }

    /**
     * creates a new node with the given value
     * @param val - value to set for the new node
     */
    public Node(int val){
        value=val;
        depth = 0;
    }

    /**
     *
     * @return the value of the node
     */
    public int getValue(){
        return value;
    }

    /**
     *
     * @return a link to the right node of the current node
     */
    public Node getRightSon(){
        return right;
    }

    /**
     *
     * @return a link to the left node of the current node
     */
    public Node getLeftSon(){
        return left;
    }

    /**
     * this method creates a right link to the given Node
     * @param rightSon- the new node to be linked
     */
    public void setRightSon(Node rightSon){
            right = rightSon;
    }
    /**
     * this method creates a left link to the given Node
     * @param leftSon- the new node to be linked
     */
    public void setLeftSon(Node leftSon){
        left = leftSon;
    }

    /**
     * this method sets the value of the node
     * @param newVal - the value to be set
     */
    public void setValue(int newVal){
        value = newVal;
    }

    /**
     * this method changes the depth of the node
     * @param newVal - the depth to be set
     */
    public void setDepth(int newVal){
        depth = newVal;
    }

    /**
     *
     * @return the depth of the node
     */
    public int getDepth(){
        return depth;
    }

}
