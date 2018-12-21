import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Funtionals {

  public static void main(String[] args) {
   
    // given
    String name = "Luiz Carlos Zamboni";

    // when not matcj
    Optional.ofNullable(name).filter(val ->
      val.contains("Gê")
    ).ifPresent(val ->
      System.out.println("end chain for " + val)
    );

    // when match
    Optional.ofNullable(name).filter(val ->
      val.contains("Luiz")
    ).ifPresent(val ->
      System.out.println("end chain for " + val)
    );


    Optional.ofNullable(name).map(val ->
      val.substring(0, 4)
    ).filter(val -> 
      val.equals("Luiz")
    ).map(val -> 
       Arrays.asList( val.split("(?!^)"))
    ).ifPresent(values -> {
      values.forEach(val ->  System.out.println("letter: " + val));
    });
  }
}