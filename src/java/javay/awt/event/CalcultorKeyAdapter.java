package javay.awt.event;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class CalcultorKeyAdapter extends KeyAdapter {
	 private JPanel panel;
	 public CalcultorKeyAdapter(JPanel panel) {
        this.panel = panel;
        System.out.println("init-------------------");
    }

	/**
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		System.out.println("KeyCode=" + k);
		switch(k){
        case KeyEvent.VK_0: {
        	//this.panel.
        	break ;
        }
        case KeyEvent.VK_1:{
        	break ;
        }
        case KeyEvent.VK_2:{
        	break ;
        }
        case KeyEvent.VK_3:{
        	break ;
        }
        case KeyEvent.VK_4:{
        	break ;
        }
        case KeyEvent.VK_5:{
        	break ;
        }
        case KeyEvent.VK_6:{
        	break ;
        }
        case KeyEvent.VK_7:{
        	break ;
        }
        case KeyEvent.VK_8:{
        	break ;
        }
        case KeyEvent.VK_9:{
        	break ;
        }
        case KeyEvent.VK_A:{
        	break ;
        }
        case KeyEvent.VK_B:{
        	break ;
        }
        case KeyEvent.VK_C:{
        	break ;
        }
        case KeyEvent.VK_D:{
        	break ;
        }
        case KeyEvent.VK_E:{
        	break ;
        }
        case KeyEvent.VK_F:{
        	break ;
        }
        case KeyEvent.VK_PERIOD:{
        	break ;
        }
        case KeyEvent.VK_PLUS:{
        	break ;
        }
        case KeyEvent.VK_SUBTRACT:{
        	break ;
        }
        case KeyEvent.VK_MULTIPLY:{
        	break ;
        }
        case KeyEvent.VK_DIVIDE:{
        	break ;
        }
        case KeyEvent.VK_EQUALS:{
        	break ;
        }
        case KeyEvent.VK_ENTER:{
        	break ;
        }
		}
	}

}
