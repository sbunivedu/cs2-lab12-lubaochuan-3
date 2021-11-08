public class LinkedBinarySearchTreeTester{

  public static void main(String[] args){

    //Build the tree and display the height as it changes
    LinkedBinarySearchTree<String> tree = new LinkedBinarySearchTree<String>();
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("A");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("B");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("C");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("D");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("E");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("F");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("G");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("H");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("I");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());
    tree.addElement("J");
    System.out.println(tree);
    System.out.println("The height is: "+tree.getHeight());

    tree.addElement("J"); // try adding duplicate node
    System.out.println("The height is: "+tree.getHeight());
    System.out.println(tree.find("J"));
    //Display the contents of the tree
    System.out.println(tree);
    //How tall is the tree now?
    System.out.println(tree.getHeight());

    System.out.println("removing \"B\"");
    tree.removeElement("B");
    System.out.println(tree);
    System.out.println("removing \"D\"");
    tree.removeElement("D");
    System.out.println(tree);
    System.out.println("removing \"H\"");
    tree.removeElement("H");
    System.out.println(tree);
  }
}
