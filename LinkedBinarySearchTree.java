/**
 * LinkedBinarySearchTree implements the BinarySearchTreeADT interface
 * with links.
 */
public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T>
  implements BinarySearchTreeADT<T>{
  /**
   * Creates an empty binary search tree.
   */
  public LinkedBinarySearchTree(){
      super();
  }

  /**
   * Creates a binary search with the specified element as its root.
   *
   * @param element the element that will be the root of the new binary
   *        search tree
   */
  public LinkedBinarySearchTree(T element){
    super(element);

    if (!(element instanceof Comparable)){
      throw new NonComparableElementException("LinkedBinarySearchTree");
    }
  }

  /**
   * Adds the specified object to the binary search tree in the
   * appropriate position according to its natural order.  Note that
   * equal elements are added to the right.
   *
   * @param element the element to be added to the binary search tree
   */
  public void addElement(T element){
    if (!(element instanceof Comparable)){
      throw new NonComparableElementException("LinkedBinarySearchTree");
    }

    //if (isEmpty()){
    //  root = new BinaryTreeNode<T>(element);
    //}else{
    root = addElementAVL(element, root);
    //}
  }

  /**
   * Adds the specified object to the binary search tree in the
   * appropriate position according to its natural order.  Note that
   * equal elements are added to the right.
   *
   * @param element the element to be added to the binary search tree
   */
  private void addElement(T element, BinaryTreeNode<T> node){
    Comparable<T> comparableElement = (Comparable<T>)element;

    if (comparableElement.compareTo(node.getElement()) < 0){
      // go left
      if (node.left == null){
        node.left = new BinaryTreeNode<T>(element);
      }else{
        addElement(element, node.left);
      }
    }else{
      // go right
      if (node.right == null){
        node.right = new BinaryTreeNode<T>(element);
      }else{
        addElement(element, node.right);
      }
    }
  }

  private int balanceFactor(BinaryTreeNode<T> node){
    return height(node.right) - height(node.left);
  }

  private BinaryTreeNode<T> addElementAVL(T element, BinaryTreeNode<T> node){
    if(node == null){
	    return new BinaryTreeNode<T>(element);
    }else if(((Comparable)element).compareTo(node.getElement()) < 0){
	    node.left = addElementAVL(element, node.left);
    }else{
	    node.right = addElementAVL(element, node.right);
	  }

	  node = balance(node);

	  return node;
  }

private BinaryTreeNode<T> balance(BinaryTreeNode<T> node){
  // node is the parent of subtrees that are changed as the result of add or remove
  int bf = balanceFactor(node);
  if(bf>1 || bf<-1){
    if(bf==-2 && balanceFactor(node.left)==-1){
      //single right rotation
      //System.out.println("left rotation");
      node = singleRightRotation(node);
    }else if(bf==-2 && balanceFactor(node.left)==1){
      //left right double rotation
      //System.out.println("left right rotation");
      node = doubleLeftRightRotation(node);
    }else if(bf==2 && balanceFactor(node.right)==1){
      //single left rotation
      //System.out.println("left rotation");
      node = singleLeftRotation(node);
    }else if(bf==2 && balanceFactor(node.right)==-1){
      //right left double rotation
      //System.out.println("right left rotation");
      node = doubleRightLeftRotation(node);
    }
  } 
  // update height
  node.height = Math.max(height(node.right), height(node.left))+1;
  return node;
}
  /**
   * Overrides the implementation in LinkedBinaryTree class.
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

    if(((Comparable)targetElement).compareTo(next.getElement())<=0){
      // go left
      return findNode(targetElement, next.getLeft());
    }else{
      // go right
      return findNode(targetElement, next.getRight());
    }
  }

  /**
   * Removes the first element that matches the specified target
   * element from the binary search tree.
   * Throws a ElementNotFoundException if the specified target
   * element is not found in the binary search tree.
   *
   * @param targetElement the element being sought in the binary search tree
   * @throws ElementNotFoundException if the target element is not found
   */
  public void removeElement(T targetElement)
    throws ElementNotFoundException{
    root = removeElement(targetElement, root);
  }

  /**
   * Removes the first element that matches the specified target
   * element from the binary search tree starting from "node".
   * Throws a ElementNotFoundException if the specified target
   * element is not found in the binary search tree.
   *
   * @param targetElement the element being sought in the binary search tree
   * @param node the node from which to search
   * @return the reference to a replacement node for "node"
   * @throws ElementNotFoundException if the target element is not found
   */
  private BinaryTreeNode<T> removeElement(T targetElement, BinaryTreeNode<T> node)
    throws ElementNotFoundException{
    if (node == null){
      throw new ElementNotFoundException("LinkedBinarySearchTree");
    }else{
      if (((Comparable<T>)targetElement).equals(node.element)){
        node = remove(node);
      }else if (((Comparable)targetElement).compareTo(node.element) < 0){
        node.left = removeElement(targetElement, node.left);
      }else{
        node.right = removeElement(targetElement, node.right);
      }
    }
    return node;
  }

  /**
   * Remove the node.
   * if the node has no children, return null.
   * if the node has online one child, return that child.
   * if the node has two children, return the inorder successor of
   * the node to be removed.
   *
   * @param node the node to remove
   * @return the reference to a replacement node for "node"
   */
  private BinaryTreeNode<T> remove(BinaryTreeNode<T> node){
    if(node.left == null){       // CASE 1: Right Child Only
      return node.right;           // attach right subtree
    }else if(node.right == null){  // CASE 2: Left Child Only
      return node.left;             // attach left subtree
    }else{                              // CASE 3: both L & R children
      BinaryTreeNode<T> parent = node;   // remove the min in this subtree
      BinaryTreeNode<T> current = node.right;
      if(current.left == null){     // special case: if the current right
        current.left = parent.left; // has no left subtree.
      }else{
        while (current.left != null){ // traverse to find the replacement node
          parent = current;
          current = current.left;
        }
        parent.left = current.right;
        current.left = node.left;
        current.right = node.right;
      }
      return current;
    }
  }

  /**
   * Removes elements that match the specified target element from
   * the binary search tree. Throws a ElementNotFoundException if
   * the sepcified target element is not found in this tree.
   *
   * @param targetElement the element being sought in the binary search tree
   * @throws ElementNotFoundException if the target element is not found
   */
  public void removeAllOccurrences(T targetElement)
    throws ElementNotFoundException{
    removeElement(targetElement);
    try{
      while (contains((T)targetElement))
      removeElement(targetElement);
    }catch (Exception ElementNotFoundException){}
  }

  /**
   * Removes the node with the least value from the binary search
   * tree and returns a reference to its element.  Throws an
   * EmptyCollectionException if this tree is empty.
   *
   * @return a reference to the node with the least value
   * @throws EmptyCollectionException if the tree is empty
   */
  public T removeMin() throws EmptyCollectionException{
    T result = null;

    if (isEmpty()){
      throw new EmptyCollectionException("LinkedBinarySearchTree");
    }else{
      if (root.left == null){
        result = root.element;
        root = root.right;
      }else{
        BinaryTreeNode<T> parent = root;
        BinaryTreeNode<T> current = root.left;
        while (current.left != null){
          parent = current;
          current = current.left;
        }
        result =  current.element;
        parent.left = current.right;
      }
    }
    return result;
  }

  /**
   * Removes the node with the highest value from the binary
   * search tree and returns a reference to its element.  Throws an
   * EmptyCollectionException if this tree is empty.
   *
   * @return a reference to the node with the highest value
   * @throws EmptyCollectionException if the tree is empty
   */
  public T removeMax() throws EmptyCollectionException{
    // To be completed as a Programming Project
    return null;
  }

  /**
   * Returns the element with the least value in the binary search
   * tree. It does not remove the node from the binary search tree.
   * Throws an EmptyCollectionException if this tree is empty.
   *
   * @return the element with the least value
   * @throws EmptyCollectionException if the tree is empty
   */
  public T findMin() throws EmptyCollectionException{
    // To be completed as a Programming Project
    return null;
  }

  /**
   * Returns the element with the highest value in the binary
   * search tree.  It does not remove the node from the binary
   * search tree.  Throws an EmptyCollectionException if this
   * tree is empty.
   *
   * @return the element with the highest value
   * @throws EmptyCollectionException if the tree is empty
   */
  public T findMax() throws EmptyCollectionException{
    // To be completed as a Programming Project
    return null;
  }

  private BinaryTreeNode<T> singleRightRotation(BinaryTreeNode<T> oldRoot){
    //TASK: Rotate the left child up and to the right to
    //      become the new root of this subtree
    BinaryTreeNode<T> newRoot = oldRoot.left;
    oldRoot.left = newRoot.right;
    newRoot.right = oldRoot;
    oldRoot.height = Math.max(height(oldRoot.left), height(oldRoot.right)) + 1;
    newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;
    return newRoot;
  }

  private BinaryTreeNode<T> singleLeftRotation(BinaryTreeNode<T> oldRoot){
    //TASK: Rotate the right child up and to the left to
    //      become the new root of this subtree
    BinaryTreeNode<T> newRoot = oldRoot.right;
    oldRoot.right = newRoot.left;
    newRoot.left = oldRoot;
    oldRoot.height = Math.max(height(oldRoot.left), height(oldRoot.right)) + 1;
    newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;
    return newRoot;
  }

  private BinaryTreeNode<T> doubleLeftRightRotation(BinaryTreeNode<T> oldRoot){
    //TASK: Rotate the left subtree to the left, then up
    //      and to the right to become the new root of this subtree
    oldRoot.left = singleLeftRotation(oldRoot.left);
    BinaryTreeNode<T> newRoot = singleRightRotation(oldRoot);
    return newRoot;
  }

  private BinaryTreeNode<T> doubleRightLeftRotation(BinaryTreeNode<T> oldRoot){
    //TASK: Rotate the right subtree to the right, then up and to
    //      the left to become the new root of this subtree
    oldRoot.right = singleRightRotation(oldRoot.right);
    BinaryTreeNode<T> newRoot = singleLeftRotation(oldRoot);
    return newRoot;
  }
}
