import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class UserNameToken {

  @XmlElement(name = "Username", required = true)
  protected String username;

  @XmlElement(name = "Password",  required = true)
  protected String password;

  public void setUsername(String val) {
    this.username = val;
  }


  public void setPassword(String val) {
    this.password = val;
  }


  // getters
  public String getpassword(){
    return password;
  }


  public String getusername(){
    return username;
  }
}