
class Arrays {

  public static void main(String[] args) {


    String[] names = { "luiz", "gerusa" };

    System.out.println("arrays have a fixed size: " + names.length);

    System.out.println("these are the elements:");

    for(String name : names) {
      System.out.println(name);
    }

    names[0] = "Zamboni";
    System.out.println("\n\nbut elements can be changeds:");

    for(String name : names) {
      System.out.println(name);
    }
  }
}