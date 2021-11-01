# An AVL Tree (Balanced Binary Search Tree)

## Purpose
The purpose of this lab is to implement an AVL tree.

## Evaluation
Students will be awarded up to 10 points for successfully completing this lab as
outlined below.

## AVL Tree
You will make our Binary Search Tree (from last lab) into an AVL tree as shown
in the following UML class diagram.
![Class Diagram](images/class_diagram.png)

### Step 1: Modify the BinaryTreeNode class
To implement an AVL tree you will need to modify our implementation of the
`BinaryTreeNode`. Recall that in an AVL tree, each node has a balance factor which will be used to decide if a rotation needs to be accomplished. Although each node will not store a balance factor in our tree, we will store a height value which will be used to calculate the balance factor.

As shown in the UML, modify your `BinaryTreeNode` class so that it stores an integer called `height`. Initialize it to `0` and use the `protected` visibility modifier so that it can be directly accessed in the `LinkedBinarySearchTree` class:
```java
protected int height = 0;
```

### Step 2: Modify the LinkedBinarySearchTree class
Most of the work we will need to do will take place in the `LinkedBinarySearchTree` class. Begin by creating some methods that deal with the height of the AVL tree.

1. Currently the `LinkedBinarySearchTree` inherits the `public int getHeight()` method from the `LinkedBinaryTree` class. This method returns the height of the tree by calling the `private int height(BinaryTreeNode<T> node)` helper method with `root` as the parameter. This `height` method recursively computes the height of any tree from the heights of its subtrees. Now that each `BinaryTreeNode` stores a height value, the height of any tree is the "height" of the root node of the tree. Modify the `height` method in the `LinkedBinaryTree` class to simply return the height value stored in each `BinaryTreeNode`. Declare this `height` method as `protected` so that it is accessible in the `LinkedBinarySearchTree` class through inheritance.
```java
  // in LinkedBinaryTree class
  protected int height(BinaryTreeNode<T> node){
    if(node == null){
      return -1;
      //the height of a one node tree is zero,
      //therefore, the height of an empty tree must be -1.
    }else{
      return node.height;
    }
  }
```

2. Implement the four rotation methods in the `LinkedBinarySearchTree` as follows:
```java
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
```

### Step 3: Create a recursive AVL insert method
Create a new `addElementAVL` method the `LinkedBinarySearchTree` so that it can behave like an AVL tree. Note that this new method needs to return a `BinaryTreeNode<T>` object. This method takes an element of type `T` and an reference to a `BinaryTreeNode<T>` object and inserts the "element" in the tree that starts from the node. The result tree should always be a AVL (balanced binary search tree). After each insert the tree many need to be rebalanced through rotation, which may cause its root to change. Therefore, this insert method needs to return the new root to its caller. Please take some time to study the comments in order to understand the code.
```java
  // helper method
  private int balanceFactor(BinaryTreeNode<T> node){
    return height(node.right) - height(node.left);
  }

  private BinaryTreeNode<T> addElementAVL(T element, BinaryTreeNode<T> node){
    Comparable<T> comparableElement = (Comparable<T>)element;

    if(node == null){
      // empty spot? add/insert a new node here.
      node = new BinaryTreeNode<T>(element);
    }else if(comparableElement.compareTo(node.getElement()) < 0){
      // insert in left subtree.
      node.left = addElementAVL(element, node.left);
      // AFTER inserting, it is possible the tree is out of balance, so check
      // balance factors
      if(balanceFactor(node) == -2){
        // if true, left subtree too tall
        // decide what sort of rotation will fix the problem
        if(balanceFactor(node.left) < 0 ){
          // left subtree of left child is too tall
          // fix with a single right rotation
          node = singleRightRotation(node);
        }else{
          // right subtree of left child is too tall
          // so double rotation is necessary
          node = doubleLeftRightRotation(node);
        }
      }
    }else{
      // insert in right subtree
      // shown below is simply the mirror image of what we did above
      node.right = addElementAVL(element, node.right);
      if(balanceFactor(node) == 2 ){
        // if true, right subtree too tall
        // decide what sort of rotation will fix the problem
        if(balanceFactor(node.right) > 0 ){
          node = singleLeftRotation(node);
        }else{
          node = doubleRightLeftRotation(node);
        }
      }
    }

    // We are done, but now we need to reset the height of this node after the
    // insertion
    node.height = Math.max(height(node.left), height(node.right)) + 1;
    return node;
  }
```

### Step 4: Testing
Modify the `addElement` method in the `LinkedBinarySearchTree` class to use the new `addElementAVL` method as follows:
```java
  public void addElement(T element){
    if (!(element instanceof Comparable)){
      throw new NonComparableElementException("LinkedBinarySearchTree");
    }

    //if (isEmpty()){
    //  root = new BinaryTreeNode<T>(element);
    //}else{
    //  addElement(element, root);
    //}

    root = addElementAVL(element, root);
  }
```

Run the `LinkedBinarySearchTreeTester` class with the following test case:
```
  LinkedBinarySearchTree<String> tree = new LinkedBinarySearchTree<String>();
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("A");
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("B");
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("C");
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("D");
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("E");
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("F");
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("G");
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("H");
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("I");
  System.out.println("The height is: "+tree.getHeight());
  tree.addElement("J");
  System.out.println("The height is: "+tree.getHeight());

  //Display the contents of the tree
  System.out.println(tree);
  //How tall is the tree now?
  System.out.println(tree.getHeight());
```
Your result should look as follows:
```
javac *.java
java LinkedBinarySearchTreeTester

The height is: -1
The height is: 0
The height is: 1
The height is: 1
The height is: 2
The height is: 2
The height is: 2
The height is: 2
The height is: 3
The height is: 3
The height is: 3
The height is: 3
J
                 /----- J
         /----- I
 /----- H
 |       |       /----- G
 |       \----- F
 |               \----- E
D
 |       /----- C
 \----- B
         \----- A

3
removing "B"
                 /----- J
         /----- I
 /----- H
 |       |       /----- G
 |       \----- F
 |               \----- E
D
 \----- C
         \----- A

removing "D"
                 /----- J
         /----- I
 /----- H
 |       |       /----- G
 |       \----- F
E
 \----- C
         \----- A

removing "H"
         /----- J
 /----- I
 |       |       /----- G
 |       \----- F
E
 \----- C
         \----- A

```
It seems that the tree does behave like an AVL balanced binary search tree, which rebalances itself as it grows. The height of the tree grows more slowly stopping at 3.

You can add more test cases to test your AVL tree more thoroughly.
