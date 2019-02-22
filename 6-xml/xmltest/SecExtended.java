import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wsse:Security2")
public class SecExtended extends Sec {

  @XmlElement(name = "Extra")
  private String extra;

  public void setExtra(String val) {
    this.extra = val;
  }

  public String getextra() {
    return this.extra;
  }
}