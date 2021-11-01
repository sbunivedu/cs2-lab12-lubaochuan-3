import java.util.LinkedList;

/**
 * LinkedBinaryTree implements the BinaryTreeADT interface
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T>{
  protected BinaryTreeNode<T> root;

  /**
   * Creates an empty binary tree.
   */
  public LinkedBinaryTree(){
    root = null;
  }

  /**
   * Creates a binary tree with the specified element as its root.
   *
   * @param element the element that will become the root of the binary tree
   */
  public LinkedBinaryTree(T element){
    root = new BinaryTreeNode<T>(element);
  }

  /**
   * Creates a binary tree with the specified element as its root and the
   * given trees as its left child and right child
   *
   * @param element the element that will become the root of the binary tree
   * @param left the left subtree of this tree
   * @param right the right subtree of this tree
   */
  public LinkedBinaryTree(T element, LinkedBinaryTree<T> left,
    LinkedBinaryTree<T> right){
    root = new BinaryTreeNode<T>(element);
    root.setLeft(left.root);
    root.setRight(right.root);
  }

  /**
   * Returns a reference to the element at the root
   *
   * @return a reference to the specified target
   * @throws EmptyCollectionException if the tree is empty
   */
  public T getRootElement() throws EmptyCollectionException{
    if(root == null){
      throw new EmptyCollectionException("binary tree");
    }
    return root.getElement();
  }

  /**
   * Returns true if this binary tree is empty and false otherwise.
   *
   * @return true if this binary tree is empty, false otherwise
   */
  public boolean isEmpty(){
    return (root == null);
  }

  /**
   * Returns the integer size of this tree.
   *
   * @return the integer size of the tree
   */
  public int size(){
    int result = 0;
    if(root != null){
      result = 1 + root.numChildren();
    }
    return result;
  }

  /**
   * Returns the height of this tree.
   *
   * @return the height of the tree
   */
  public int getHeight(){
    return height(root);
  }

  /**
   * Returns the height of the specified node (recursive).
   *
   * @param node the node from which to calculate the height
   * @return the height of the tree
   */

  protected int height(BinaryTreeNode<T> node){
    if(node == null){
      return -1;
      //the height of a one node tree is zero,
      //therefore, the height of an empty tree must be -1.
    }else{
      return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }
  }

  /**
   * Returns true if this tree contains an element that matches the
   * specified target element and false otherwise.
   *
   * @param targetElement the element being sought in this tree
   * @return true if the element in is this tree, false otherwise
   */
  public boolean contains(T targetElement){
    if(find(targetElement) != null){
      return true;
    }else{
      return false;
    }
  }

  /**
   * Returns a reference to the specified target element if it is
   * found in this binary tree.  Throws a ElementNotFoundException if
   * the specified target element is not found in the binary tree.
   *
   * @param targetElement the element being sought in this tree
   * @return a reference to the specified target
   * @throws ElementNotFoundException if the element is not in the tree
   */
  public T find(T targetElement) throws ElementNotFoundException{
    BinaryTreeNode<T> current = findNode(targetElement, root);

    if (current == null){
      throw new ElementNotFoundException("LinkedBinaryTree");
    }
    return (current.getElement());
  }

  /**
   * Returns a reference to the specified target element if it is
   * found in this binary tree.
   *
   * @param targetElement the element being sought in this tree
   * @param next the element to begin searching from
   */
  private BinaryTreeNode<T> findNode(T targetElement,
                                     BinaryTreeNode<T> next){
    if (next == null){
      return null;
    }

    if (next.getElement().equals(targetElement)){
      return next;
    }

    BinaryTreeNode<T> temp = findNode(targetElement, next.getLeft());

    if (temp == null){
      temp = findNode(targetElement, next.getRight());
    }

    return temp;
  }

  /**
   * Returns a string representation of this binary tree showing
   * the nodes in an inorder fashion.
   *
   * @return a string representation of this binary tree
   */
  public String toString(){
    return root.print();
  }

  /**
   * Returns the result of inorder traversal
   *
   * @return a string representation of the inorder traversal over this binary tree
   */
  public String inOrder(){
    return inOrder(root);
  }

  /**
   * Helper method that returns the result of inorder traversal
   *
   * @param node the node from which to start the traversal
   * @return a string representation of the inorder traversal over this binary tree
   */
  private String inOrder(BinaryTreeNode<T> node){
    if(node == null){
      return "";
    }else{
      return inOrder(node.getLeft())+node.getElement()+inOrder(node.getRight());
    }
  }

  /**
   * Returns the result of preorder traversal
   *
   * @return a string representation of the preorder traversal over this binary tree
   */
  public String preOrder(){
    // To be completed as a Programming Project
    return null;
  }

  /**
   * Helper method that returns the result of preorder traversal
   *
   * @param node the node from which to start the traversal
   * @return a string representation of the preorder traversal over this binary tree
   */
  private String preOrder(BinaryTreeNode<T> node){
    // To be completed as a Programming Project
    return null;
  }

  /**
   * Returns the result of postorder traversal
   *
   * @return a string representation of the postorder traversal over this binary tree
   */
  public String postOrder(){
    // To be completed as a Programming Project
    return null;
  }

  /**
   * Helper method that returns the result of postorder traversal
   *
   * @param node the node from which to start the traversal
   * @return a string representation of the postorder traversal over this binary tree
   */
  private String postOrder(BinaryTreeNode<T> node){
    // To be completed as a Programming Project
    return null;
  }

  /**
   * Returns the result of levelorder traversal
   *
   * @return a string representation of the levelorder traversal over this binary tree
   */
  public String levelOrder(){
    LinkedList<BinaryTreeNode<T>> nodes = new LinkedList<BinaryTreeNode<T>>();
    String result = "";
    nodes.add(root);
    while(nodes.size() != 0){
      BinaryTreeNode<T> current = nodes.remove();
      if(current != null){
        result += current.getElement();
        nodes.add(current.getLeft());
        nodes.add(current.getRight());
      }
    }

    return result;
  }
}
