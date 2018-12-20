import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

class Lists {

  public static void main(String[] args) {

    // List<String> list1 = new ArrayList<>();
    List uncheckedList1 = new ArrayList();

    // List <String> list2 = new LinkedList();  
    // List <String> list3 = new Vector();
    // List <String> list4 = new Stack();

    // unchecked types
    uncheckedList1.add("luiz");
    uncheckedList1.add(2);


    uncheckedList1.forEach(item -> {
      System.out.println(item + " -> " + item.getClass());
    });

  }
}