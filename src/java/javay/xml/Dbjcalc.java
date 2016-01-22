package javay.xml;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="dbjcalc")
@XmlType(propOrder={"calcultor", "autoUpdate"})
public class Dbjcalc {
    private Calcultor calcultor;
    private AutoUpdate autoUpdate;
    /**
     * @return the calcultor
     */
    public Calcultor getCalcultor() {
        return calcultor;
    }
    /**
     * @param calcultor the calcultor to set
     */
    public void setCalcultor(Calcultor calcultor) {
        this.calcultor = calcultor;
    }
    /**
     * @return the autpUpdate
     */
    public AutoUpdate getAutoUpdate() {
        return autoUpdate;
    }
    /**
     * @param autpUpdate the autpUpdate to set
     */
    public void setAutoUpdate(AutoUpdate autpUpdate) {
        this.autoUpdate = autpUpdate;
    }
}
