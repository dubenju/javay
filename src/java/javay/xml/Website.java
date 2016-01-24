package javay.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "address"})
public class Website {
  private String name;
  private String address;
  private boolean isSelected;

  /**
   * getName.
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * setName.
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * getAddress.
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * setAddress.
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * isSelected.
   * @return the isSelected
   */
  @XmlAttribute(name = "isSelected")
  public boolean isSelected() {
    return isSelected;
  }

  /**
   * setSelected.
   * @param isSelected the isSelected to set
   */
  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }
}
