package javay.awt.event;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CalcultorKeyAdapter extends KeyAdapter {

	/* (非 Javadoc)
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		switch(k){
        case KeyEvent.VK_0:
        case KeyEvent.VK_1:
        case KeyEvent.VK_2:
        case KeyEvent.VK_3:
        case KeyEvent.VK_4:
        case KeyEvent.VK_5:
        case KeyEvent.VK_6:
        case KeyEvent.VK_7:
        case KeyEvent.VK_8:
        case KeyEvent.VK_9:
        case KeyEvent.VK_A:
        case KeyEvent.VK_B:
        case KeyEvent.VK_C:
        case KeyEvent.VK_D:
        case KeyEvent.VK_E:
        case KeyEvent.VK_F:
        case KeyEvent.VK_PLUS:
        case KeyEvent.VK_SUBTRACT:
        case KeyEvent.VK_MULTIPLY:
        case KeyEvent.VK_DIVIDE:
        case KeyEvent.VK_EQUALS:
        case KeyEvent.VK_ENTER:
		}
	}

}
