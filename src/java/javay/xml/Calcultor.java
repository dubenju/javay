package javay.xml;

import javay.math.BigNumRound;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"currentVersion", "decimalLength", "roundMode"})
public class Calcultor {
  private String currentVersion;
  private int decimalLength;
  private BigNumRound roundMode;

  /**
   * getCurrentVersion.
   * @return the currentVersion
   */
  public String getCurrentVersion() {
    return currentVersion;
  }

  /**
   * setCurrentVersion.
   * @param currentVersion the currentVersion to set
   */
  public void setCurrentVersion(String currentVersion) {
    this.currentVersion = currentVersion;
  }

  /**
   * getDecimalLength.
   * @return the decimalLength
   */
  public int getDecimalLength() {
    return decimalLength;
  }

  /**
   * setDecimalLength.
   * @param decimalLength the decimalLength to set
   */
  public void setDecimalLength(int decimalLength) {
    this.decimalLength = decimalLength;
  }

  /**
   * getRoundMode.
   * @return the roundMode
   */
  public BigNumRound getRoundMode() {
    return roundMode;
  }

  /**
   * setRoundMode.
   * @param roundMode the roundMode to set
   */
  public void setRoundMode(BigNumRound roundMode) {
    this.roundMode = roundMode;
  }
}
