package javay.xml;

import javax.xml.bind.annotation.XmlType;

import javay.math.BigNumRound;

@XmlType(propOrder={"currentVersion", "decimalLength", "roundMode"})
public class Calcultor {
    private String currentVersion;
    private int decimalLength;
    private BigNumRound roundMode;
    /**
     * @return the currentVersion
     */
    public String getCurrentVersion() {
        return currentVersion;
    }
    /**
     * @param currentVersion the currentVersion to set
     */
    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
    /**
     * @return the decimalLength
     */
    public int getDecimalLength() {
        return decimalLength;
    }
    /**
     * @param decimalLength the decimalLength to set
     */
    public void setDecimalLength(int decimalLength) {
        this.decimalLength = decimalLength;
    }
    /**
     * @return the roundMode
     */
    public BigNumRound getRoundMode() {
        return roundMode;
    }
    /**
     * @param roundMode the roundMode to set
     */
    public void setRoundMode(BigNumRound roundMode) {
        this.roundMode = roundMode;
    }
}
