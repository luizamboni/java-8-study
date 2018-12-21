import java.util.Optional;

class Usb {
  String getVersion() {
    return "3.0";
  }
}

class Computer {
  Usb usb = null;

  Computer(Boolean hasUsb){
    if(hasUsb)
      this.usb = new Usb();
  }

  public void setUsb(Usb usb) {
    this.usb = usb;
  }

  public Optional<Usb> getUsb() {
    return Optional.ofNullable(this.usb);
  }
}

class FlatMap {

  public static void main(String[] args) {

    // wraper value
    // flatMap returns a value unwraped of optional
    Optional<String> value = Optional.ofNullable(new Computer(true))
            .flatMap(Computer::getUsb)
            .map(Usb::getVersion);
    
    System.out.println(value);


    // flatMap returns a value unwraped of optional
    Optional<String> value2 = Optional.ofNullable(new Computer(false))
                                     .flatMap(Computer::getUsb)
                                     .map(Usb::getVersion);

    // return a empty Optional
    System.out.println(value2);
  }
}