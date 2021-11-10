/**
 * BinaryTreeNode represents a node in a binary tree with a left and
 * right child.
 */
public class BinaryTreeNode<T>{
  protected T element;
  protected BinaryTreeNode<T> left, right;
  protected int height;

  /**
   * Creates a new tree node with the specified data.
   *
   * @param obj the element that will become a part of the new tree node
  */
  public BinaryTreeNode(T obj) {
    element = obj;
    left = null;
    right = null;
    height = 0;
  }

  /**
   * Returns the number of non-null children of this node.
   *
   * @return the integer number of non-null children of this node
   */
  public int numChildren(){
    int children = 0;

    if (left != null){
      children = 1 + left.numChildren();
    }
    if (right != null){
      children = children + 1 + right.numChildren();
    }

    return children;
  }

  /**
   * Return the element at this node.
   *
   * @return the element stored at this node
   */
  public T getElement(){
    return element;
  }

  public void setElement(T element){
    this.element = element;
  }

  /**
   * Return the right child of this node.
   *
   * @return the right child of this node
   */
  public BinaryTreeNode<T> getRight(){
    return right;
  }

  /**
   * Sets the right child of this node.
   *
   * @param node the right child of this node
   */
  public void setRight(BinaryTreeNode<T> node){
    right = node;
  }

  /**
   * Return the left child of this node.
   *
   * @return the left child of the node
   */
  public BinaryTreeNode<T> getLeft(){
    return left;
  }

  /**
   * Sets the left child of this node.
   *
   * @param node the left child of this node
   */
  public void setLeft(BinaryTreeNode<T> node){
    left = node;
  }

  public String toString(){
    return ""+element;
  }

  public String print(){
    String result = "";
    if (right != null){
      result += right.recPrint(true, "");
    }
    result += this+"\n";
    if (left != null){
      result += left.recPrint(false, "");
    }
    return result;
  }

  private String recPrint(boolean isRight, String indent) {
    String result = "";
    if (right != null){
      result += right.recPrint(true, indent + (isRight ? "        " : " |      "));
    }
    result += indent;
    if (isRight) {
      result += " /";
    } else {
      result += " \\";
    }
    result += "----- "+this+"\n";
    if (left != null) {
      result += left.recPrint(false, indent + (isRight ? " |      " : "        "));
    }
    return result;
  }
}
