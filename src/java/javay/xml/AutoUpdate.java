package javay.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"isAutoUpdate", "websites", "retry"})
public class AutoUpdate {
    private boolean isAutoUpdate;
    private List<Website> websites;
    private int retry;
    /**
     * @return the isAutoUpdate
     */
    public boolean getisAutoUpdate() {
        return isAutoUpdate;
    }
    /**
     * @param isAutoUpdate the isAutoUpdate to set
     */
    public void setisAutoUpdate(boolean isAutoUpdate) {
        this.isAutoUpdate = isAutoUpdate;
    }
    /**
     * @return the websites
     */
    @XmlElementWrapper(name="websites")
    @XmlElement(name="website")
    public List<Website> getWebsites() {
        return websites;
    }
    /**
     * @param websites the websites to set
     */
    public void setWebsites(List<Website> websites) {
        this.websites = websites;
    }
    /**
     * @return the retry
     */
    public int getRetry() {
        return retry;
    }
    /**
     * @param retry the retry to set
     */
    public void setRetry(int retry) {
        this.retry = retry;
    }
}
