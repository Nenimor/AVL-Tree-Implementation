package oop.ex4.data_structures;

/**
 * this class defines an AVL tree- a self balancing binary search tree of nodes. This class extends the
 * class 'BinarySearchTree', inheriting it's basic methods, and making some relevant changes to them.
 * @author Ronen Mor.
 */
public class AvlTree extends BinarySearchTree {
    protected final static int UNBALANCED_FACTOR = 2;
    protected final static int UNBALANCED_SON_FACTOR=1;
    //static methods
    /**
     * Calculates the minimum number of nodes in an AVL tree of height h.
     * @param h - the height of the tree (a non-negative number) in question.
     * @return the minimum number of nodes in an AVL tree of the given height.
     */
    public static int findMinNodes(int h){
        return (int)(Math.round(((Math.sqrt(5)+2)/
                Math.sqrt(5))*Math.pow((1+
                Math.sqrt(5))/2,h)-1));
    }

    /**
     * Calculates the maximum number of nodes in an AVL tree of height h.
     * @param h - the height of the tree (a non-negative number) in question.
     * @return the maximum number of nodes in an AVL tree of the given height.
     */
    public static int findMaxNodes(int h){
        return (int)(Math.pow(2,h+1)-1);
    }

    //CONSTRUCTORS

    /**
     * The default constructor
     */
    public AvlTree(){
        super();
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     * @param data the values to add
     */
    public AvlTree(int[] data){
        super(data);

    }

    /**
     * A copy constructor that creates a deep copy of the given AvlTree.
     *
     * @param avlTree The AVL tree to be copied
     */
    public AvlTree(BinarySearchTree avlTree){
        super(avlTree);
    }

    //METHODS

    /**
     * Add a new node with the given key to the tree.
     * @param newValue - the value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added, false
     * otherwise
     */
    public boolean add(int newValue) {
        if (super.add(newValue)) {
            Node balanceResult = balanceTree(newValue,root);
            if (balanceResult!=null) root=balanceResult;
            return true;
        }
        else return false;
    }
    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete - the value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public boolean delete (int toDelete){
        if (super.delete(toDelete)){
            Node balanceResult = balanceTree(toDelete, root);
            if (balanceResult!=null) root=balanceResult;
            return true;
        }
        else return false;
    }


    /**
     * calculates the balance factor of a given node by (depth of right subtree - depth of left subtree)
     * @param nodeToUpdate - the node which it's balance factor should be calculates
     * @return the balance factor
     */
    private int calculateBalanceFactor (Node nodeToUpdate){
        int rightDepth,leftDepth;
        if (nodeToUpdate.getRightSon()==null) rightDepth = 0;
        else rightDepth = nodeToUpdate.getRightSon().getDepth()+1;
        if (nodeToUpdate.getLeftSon()==null) leftDepth = 0;
        else leftDepth = nodeToUpdate.getLeftSon().getDepth()+1;
        int balanceFactor = rightDepth-leftDepth;
        return balanceFactor;
    }

    /**
     * a recursive method which balances the tree
     * @param value-value which has been added to the tree / value which has been deleted
     * @param currentNode the node which it's balance factor should be checked
     * @return null if rotation wasn't done, and the new node to connect if a rotation was done
     */
    private Node balanceTree(int value, Node currentNode){
        if (currentNode==null) return null;
        //next rows goes all the way down the tree to the new value which has been added / the position of
        // the value which has been deleted.
        if (currentNode.getValue()>value){
            Node nodeToConnect = balanceTree(value, currentNode.getLeftSon());
            if (nodeToConnect != null) currentNode.setLeftSon(nodeToConnect);
        }
        else if (currentNode.getValue()<value) {
            Node nodeToConnect = balanceTree(value, currentNode.getRightSon());
            if (nodeToConnect != null) currentNode.setRightSon(nodeToConnect);
        }

        int balanceFactor = calculateBalanceFactor(currentNode);

        //next rows performs the correct rotations according to the balance factor being calculated
        if (balanceFactor == -UNBALANCED_FACTOR){
            int leftBalanceFactor = calculateBalanceFactor(currentNode.getLeftSon());
            //RR Rotation
            if (leftBalanceFactor == -UNBALANCED_SON_FACTOR){
                return RightRotation(currentNode);
            }
            //LR Rotation
            else{
                currentNode.setLeftSon(LeftRotation(currentNode.getLeftSon()));
                return RightRotation(currentNode);
            }
        }
        else if(balanceFactor == UNBALANCED_FACTOR){
            int rightBalanceFactor = calculateBalanceFactor(currentNode.getRightSon());
            //LL Rotation
            if (rightBalanceFactor == UNBALANCED_SON_FACTOR){
                return LeftRotation(currentNode);
            }
            //RL Rotation
            else{
                currentNode.setRightSon(RightRotation(currentNode.getRightSon()));
                return LeftRotation(currentNode);
            }
        }
        return null;
    }

    //ROTATIONS:

    /**
     * creates a left rotation of the given node
     * @param nodeToRotate the node which needs to be rotated
     * @return the new rotated node which needs to be attached to the tree
     */
    private Node LeftRotation(Node nodeToRotate){
        Node tempNode = nodeToRotate.getRightSon();
        nodeToRotate.setRightSon(tempNode.getLeftSon());
        tempNode.setLeftSon(nodeToRotate);
        updateDepth(tempNode.getLeftSon());
        updateDepth(tempNode);
        return tempNode; //returns the new node which needs to be connected
    }


    /**
     * creates a right rotation of the given node
     * @param nodeToRotate the node which needs to be rotated
     * @return the new rotated node which needs to be attached to the tree
     */
    private Node RightRotation(Node nodeToRotate){
        Node tempNode = nodeToRotate.getLeftSon();
        nodeToRotate.setLeftSon(tempNode.getRightSon());
        tempNode.setRightSon(nodeToRotate);
        updateDepth(tempNode.getRightSon());
        updateDepth(tempNode);
        return tempNode; //returns the new node which needs to be connected
    }
}
