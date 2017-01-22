package javay.awt.event;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;

import javay.swing.CalcultorPanel;
import javay.swing.SoundPanel;


public class SoundAwtEventListener implements AWTEventListener {
    private SoundPanel panel;
    public SoundAwtEventListener(SoundPanel panel) {
	this.panel = panel;
    }
    @Override
    public void eventDispatched(AWTEvent event) {
	// TODO 自動生成されたメソッド・スタブ

    }

}
