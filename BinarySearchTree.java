package oop.ex4.data_structures;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * this class defines a binary search tree of nodes with its basic methods: add, delete, contains, size.
 * @author Ronen Mor.
 */
public class BinarySearchTree implements Iterable<Integer> {
    protected final static int NOT_FOUND_RETURN = -1;
    protected Node root;
    protected int size;

    //Constructors
    /**
     * The default constructor
     */
    public BinarySearchTree(){
        root=null;
        size=0;
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     * @param data the values to add
     */
    public BinarySearchTree(int[] data){
        for (int value: data){
            add(value);
        }
    }

    /**
     * A copy constructor that creates a deep copy of the given AvlTree.
     *
     * @param avlTree The AVL tree to be copied
     */
    public BinarySearchTree(BinarySearchTree avlTree){
        Iterator<Integer> treeIterator = avlTree.iterator();
        while (treeIterator.hasNext()){
            add(treeIterator.next());
        }
    }

    //METHODS

    /**
     * Add a new node with the given key to the tree.
     * @param newValue - the value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added, false
     * otherwise
     */
    public boolean add(int newValue) {
        //an edge case- tree is empty- add the new value as the root
        if (root==null){
            Node nodeToAdd = new Node(newValue);
            root = nodeToAdd;
            size=size+1;
            return true;
        }
        else{ //calls the recursive assistance method
            if (addHelper(newValue, root)){
                size=size+1;
                return true;
            }
            else return false;
        }
    }

    /**
     * Check whether the tree contains the given input value.
     * @param searchVal - value to search for
     * @return if val is found in the tree, return the depth of the node (0 for the root) with the given
     * value if it was found in the tree, -1 otherwise.
     */
    public int contains (int searchVal){
        if (root==null) return NOT_FOUND_RETURN;
        else return containsHelper(searchVal, root, 0);
    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete - the value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public boolean delete (int toDelete){
        if (root==null) return false; //an edge case-there's no root->value to delete not found->return false
        //edge case- root needs to be deleted- handled manually
        if (root.getValue()==toDelete) {
            //root has only one child -> set the root as this child.
            if (root.getRightSon()==null) root=root.getLeftSon();
            else if (root.getLeftSon()==null) root=root.getRightSon();
            //else- root has both children- call 'twoSonsDeletion'
            else twoSonsDeletion(root);
            size=size-1;
            return true;
        }
        else { //calls the recursive assistance method
            if (deleteHelper(toDelete, root)){
                size=size-1;
                return true;
            }
            else return false;
        }
    }

    /**
     *
     * @return the number of nodes in the tree.
     */
    public int size(){
        return size;
    }

    /**
     * returns an iterator for the BinarySearch tree. The returned iterator iterates over the tree nodes in an
     * ascending order.
     * @return an iterator for the BinarySearch tree
     */
    public Iterator<Integer> iterator(){
        ArrayList<Integer> targetArray = new ArrayList<Integer>();
        return createIterator(root,targetArray).iterator();
    }

    //ADDITIONAL METHODS

    /**
     * a recursive assistance method of the method add which adds a new value to the tree
     * @param newValue the value to add
     * @param currentNode a starting node for a binary search
     * @return true if the value was added, false otherwise (value already exists in the tree)
     */
    private boolean addHelper(int newValue, Node currentNode) {
        //if newValue equals to current node's value- it already exists in the tree- return false.
        if (currentNode.getValue() == newValue){
            return false;
        }
        // if current node's value is greater than newValue, add the value to it's left if it has no
        // leftSon, and call the recursive function with it's left son otherwise.
        else if (currentNode.getValue() > newValue){
            if (currentNode.getLeftSon()==null) {
                Node nodeToAdd = new Node(newValue);
                currentNode.setLeftSon(nodeToAdd);
                updateDepth(currentNode);
                return true;
            }
            else{
                if (addHelper(newValue, currentNode.getLeftSon())){
                    updateDepth(currentNode);
                    return true;
                }
                else return false;

            }
        }
        // else -current node's value is smaller than newValue, add the value to it's right if it has no
        // rightSon, and call the recursive function with it's right son otherwise.
        else {
            if (currentNode.getRightSon()==null) {
                Node nodeToAdd = new Node(newValue);
                currentNode.setRightSon(nodeToAdd);
                updateDepth(currentNode);
                return true;
            }
            else{
                if (addHelper(newValue, currentNode.getRightSon())){
                    updateDepth(currentNode);
                    return true;
                }
                else return false;
            }
        }
    }


    /**
     * a recursive assistance method of the method 'contains' which searches for a value in the tree
     * @param searchVal the value which needs to be searched
     * @param currentNode the node which needs to be equaled to the given value
     * @param iterationNum counts the number of the recursive calls
     * @return the depth of the value in the tree if found, -1 otherwise
     */
    private int containsHelper (int searchVal, Node currentNode, int iterationNum){
        if (currentNode.getValue()==searchVal){
            return iterationNum;
        }
        // if current node's value is greater than the value to find, call the recursive function with it's
        // left son if exists, and return -1 otherwise (value not found)
        else if (currentNode.getValue() > searchVal) {
            if (currentNode.getLeftSon() == null) return NOT_FOUND_RETURN;
            else return containsHelper(searchVal, currentNode.getLeftSon(), iterationNum + 1);
        }
        // if current node's value is smaller than the value to find, call the recursive function with it's
        // right son if exists, and return -1 otherwise (value not found)
        else{
            if (currentNode.getRightSon() == null) return NOT_FOUND_RETURN;
            else return containsHelper(searchVal, currentNode.getRightSon(), iterationNum + 1);
        }
    }

    /**
     * a recursive assistance method of the method 'delete' which deletes a value in the tree
     * @param toDelete the value which needs to be deleted
     * @param currentNode the node which needs to be equaled to the given value
     * @return true if the value was found and deleted, false otherwise
     */
    private boolean deleteHelper (int toDelete, Node currentNode){
        //base case- reached lowest in the tree and didn't find the value to delete- returns false.
        if (currentNode==null) return false;
        if (currentNode.getValue() > toDelete){ //if value to delete smaller than current value- go left
            if (currentNode.getLeftSon()!=null && currentNode.getLeftSon().getValue()==toDelete){
                Node nodeToDelete = currentNode.getLeftSon();// the desired value to delete found in the tree
                //if the node to delete has only left/right son- link it's parent to it's right/left son,
                // update depth, return true
                if (nodeToDelete.getRightSon()==null) {
                    currentNode.setLeftSon(nodeToDelete.getLeftSon());
                    updateDepth(currentNode);
                    return true;
                }
                else if (nodeToDelete.getLeftSon()==null) {
                    currentNode.setLeftSon(nodeToDelete.getRightSon());
                    updateDepth(currentNode);
                    return true;
                }
                else{ //else- the node to delete has both sons, call 'twoSonsDeletion'
                    twoSonsDeletion(nodeToDelete);
                    return true;
                }
            }
            // value to delete wasn't found, call the recursive function with the left son
            else if (deleteHelper(toDelete,currentNode.getLeftSon())){
                updateDepth(currentNode);
                return true;
            }
            else return false;
        }
        else if (currentNode.getValue() < toDelete){ //if value to delete smaller than current value- go right
            if (currentNode.getRightSon()!=null && currentNode.getRightSon().getValue()==toDelete){
                Node nodeToDelete = currentNode.getRightSon(); //the desired value to delete found in the tree
                //if the node to delete has only left/right son- link it's parent to it's right/left son,
                // update depth, return true
                if (nodeToDelete.getRightSon()==null) {
                    currentNode.setRightSon(nodeToDelete.getLeftSon());
                    updateDepth(currentNode);
                    return true;
                }
                else if (nodeToDelete.getLeftSon()==null) {
                    currentNode.setRightSon(nodeToDelete.getRightSon());
                    updateDepth(currentNode);
                    return true;
                }
                else{ //else- the node to delete has both sons, call 'twoSonsDeletion'
                    twoSonsDeletion(nodeToDelete);
                    return true;
                }
            }
            // value to delete wasn't found, call the recursive function with the right son
            else if(deleteHelper(toDelete,currentNode.getRightSon())){
                updateDepth(currentNode);
                return true;
            }
            else return false;
        }
        else return false; //TODO <- making problems?
    }

    /** an assistance method for 'deleteHelper'- deleted a node which has left and right subtrees
     * @param nodeToDelete - the node which needs to be removed
     */
    private void twoSonsDeletion(Node nodeToDelete){
        //next rows find the successive element of the node needs to be deleted by going left
        // once, then keep going right all the way down- finds the maximal value of the left subtree.
        Node successive = nodeToDelete.getLeftSon();
        while (successive.getRightSon() != null){
            successive = successive.getRightSon();
        }
        //keeps the successive value, deletes it from the tree, sets the value of the original node needed
        // to be deleted to the successive value.
        int tempVal = successive.getValue();
        delete(successive.getValue());
        nodeToDelete.setValue(tempVal);
    }

    /**
     * a recursive assistance method for the method 'iterator'- inserts the tree's values ascending into an
     * array, and returns it.
     * @param currentNode the current node which needs to be inserted
     * @param targetArray the target array of all tree's values
     * @return an array with all tree's values in ascending order
     */
    private ArrayList<Integer> createIterator(Node currentNode, ArrayList<Integer> targetArray){
        //inserts all values of the tree into an array by a recursive call- add the left subtree, add root,
        // add the right subtree
        if (currentNode!=null){
            createIterator(currentNode.getLeftSon(), targetArray);
            targetArray.add(currentNode.getValue());
            createIterator(currentNode.getRightSon(),targetArray);
        }
        return targetArray;
    }


    /**
     * updates the depth of the given node
     * @param nodeToUpdate the node which it's depth should be updated
     */
    protected void updateDepth(Node nodeToUpdate){
        if (nodeToUpdate.getLeftSon()==null){
            if (nodeToUpdate.getRightSon()==null) nodeToUpdate.setDepth(0); // if the node has no sons-its
                // a leaf- set depth to 0
            //if the node has only right son- set it's depth to it's son's depth+1
            else nodeToUpdate.setDepth(nodeToUpdate.getRightSon().getDepth()+1);
        }
        //if the node has only right son- set it's depth to it's son's depth+1
        else if (nodeToUpdate.getRightSon()==null){
            nodeToUpdate.setDepth(nodeToUpdate.getLeftSon().getDepth()+1);
        }
        //if the node has 2 sons- set it's depth as the maximal depth of it's sons + 1
        else{
            int leftDepth = nodeToUpdate.getLeftSon().getDepth();
            int rightDepth = nodeToUpdate.getRightSon().getDepth();
            nodeToUpdate.setDepth(Math.max(leftDepth, rightDepth)+1);
        }
    }


}
