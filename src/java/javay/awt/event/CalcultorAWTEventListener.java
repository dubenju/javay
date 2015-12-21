package javay.awt.event;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import javay.swing.CalcultorPanel;

public class CalcultorAWTEventListener implements AWTEventListener {

	private CalcultorPanel panel;
	public CalcultorAWTEventListener(CalcultorPanel panel) {
        this.panel = panel;
	}
	@Override
	public void eventDispatched(AWTEvent event) {
        if (event.getClass() == KeyEvent.class) {  
            KeyEvent kE = ((KeyEvent) event);
            if (kE.getID() == KeyEvent.KEY_PRESSED) {
	            int k = kE.getKeyCode();
	
	            System.out.println("KeyCode=" + k);
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
		            case KeyEvent.VK_9: {
		                String str = this.panel.textField.getText();
		                if (str.equals("0") || str.equals("0.0")) {
		                    str = "";
		                }
		            	str = str + "" + (k - '0');
		            	this.panel.textField.setText(str);
		            	break ;
		            }
		            case KeyEvent.VK_A:
		            case KeyEvent.VK_B:
		            case KeyEvent.VK_C:
		            case KeyEvent.VK_D:
		            case KeyEvent.VK_E:
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
	}

}

