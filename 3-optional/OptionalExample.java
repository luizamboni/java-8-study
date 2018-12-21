import java.util.Optional;

class OptionalExample {
  public static void main(String[] args) {
    String name = "Luiz";

    // get access value without any careless
    System.out.println(
      Optional.ofNullable(name).get()
    );

    try {
      Optional.ofNullable(null).get();
    } catch (Exception e) {
      System.out.println(e);
    }

    // if care some careful
    Optional.ofNullable(null).ifPresent(val ->
      // never enter here
      System.out.println("value is present: " + val)
    );

    System.out.println(Optional.ofNullable(null).orElse(
      "DEFAULT value provided"
    ));

    // or to force a exception
    try {
      Optional.ofNullable(null).orElseThrow(Exception::new);
    } catch (Exception e) {
      System.out.println(e);
    }

    Optional.ofNullable(name).ifPresent(val -> 
      System.out.println("value is present: " + val)
    );
  }
}