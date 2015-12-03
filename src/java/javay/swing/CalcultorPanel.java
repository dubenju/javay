/**
 * 
 */
package javay.swing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import javay.awt.event.CalcultorActionListener;

/**
 * @author dubenju
 *
 */
public class CalcultorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalcultorPanel() {
		JTextField textField;
		JPanel buttonPanel;
		Button numButtons[];
		Button btnDot, btnPosMinus, btnBackspace, btnDivide1, btnEqual, btnClear;
		Button btnAdd, btnSubtract, btnMultiply, btnDivide;
		Button btnX2, btnX3, btnXY, btnSin, btnCos, btnTan, btnSqrt, btnN, btnLeft, btnRight;
		CalcultorActionListener controler;

		//create the text field
		textField = new JTextField( 34 );
		
		//create the control
		controler = new CalcultorActionListener( textField );
		
		textField.setHorizontalAlignment( JTextField.RIGHT );
		textField.setEditable( false );
		
		//create button panel
		buttonPanel = new JPanel();
		
		//create buttons
		numButtons = new Button[10];
		for ( int i = 0; i < 10; i++ )
		{
			numButtons[i] = new Button( "" + i );
		}
		btnDot = new Button( "." );
		btnPosMinus = new Button( "+/-" );
		btnBackspace = new Button( "Backspace" );
		btnDivide1 = new Button( "1/x" );
		btnEqual = new Button( "=" );
		btnClear = new Button( "C" );
		btnAdd = new Button( "+" );
		btnSubtract = new Button( "-" );
		btnMultiply = new Button( "*" );
		btnDivide = new Button( "/" );
		
		//create special button
		btnLeft = new Button("(");
		btnRight = new Button(")");
		btnX2 = new Button("x^2");
		btnX3 = new Button("x^3");
		btnXY = new Button("x^y");
		btnSqrt = new Button("sqrt");
		btnSin = new Button("sin");
		btnCos = new Button("cos");
		btnTan = new Button("tan");
		btnN = new Button("n!");
		
		//add these buttons to buttonPanel
		buttonPanel.setLayout( new GridLayout(6, 5) );
		
		buttonPanel.add( btnLeft ).setForeground(Color.red);
		buttonPanel.add( btnRight ).setForeground(Color.red);
		buttonPanel.add( btnSin ).setForeground(Color.red);
		buttonPanel.add( btnCos ).setForeground(Color.red);
		buttonPanel.add( btnTan ).setForeground(Color.red);
		
		buttonPanel.add( btnX2 ).setForeground(Color.red);
		buttonPanel.add( btnX3 ).setForeground(Color.red);
		buttonPanel.add( btnXY ).setForeground(Color.red);
		buttonPanel.add( btnSqrt ).setForeground(Color.red);
		buttonPanel.add( btnN ).setForeground(Color.red);
		
		buttonPanel.add( numButtons[7] ).setForeground(Color.blue);
		buttonPanel.add( numButtons[8] ).setForeground(Color.blue);
		buttonPanel.add( numButtons[9] ).setForeground(Color.blue);
		buttonPanel.add( btnAdd ).setForeground(Color.red);
		buttonPanel.add( btnClear ).setForeground(Color.red);
		
		buttonPanel.add( numButtons[4] ).setForeground(Color.blue);
		buttonPanel.add( numButtons[5] ).setForeground(Color.blue);
		buttonPanel.add( numButtons[6] ).setForeground(Color.blue);
		buttonPanel.add( btnSubtract ).setForeground(Color.red);
		buttonPanel.add( btnBackspace ).setForeground(Color.red);
		
		buttonPanel.add( numButtons[1] ).setForeground(Color.blue);
		buttonPanel.add( numButtons[2] ).setForeground(Color.blue);
		buttonPanel.add( numButtons[3] ).setForeground(Color.blue);
		buttonPanel.add( btnMultiply ).setForeground(Color.red);
		buttonPanel.add( btnDivide1 ).setForeground(Color.red);
		
		buttonPanel.add( numButtons[0] ).setForeground(Color.blue);
		buttonPanel.add( btnDot ).setForeground(Color.blue);
		buttonPanel.add( btnPosMinus ).setForeground(Color.blue);
		buttonPanel.add( btnDivide ).setForeground(Color.red);
		buttonPanel.add( btnEqual ).setForeground(Color.red);
		
		//add let the control listen to the buttons
		for( int i = 0; i < 10; i++ )
		{
			numButtons[i].addActionListener( controler );
		}
		btnAdd.addActionListener( controler );
		btnBackspace.addActionListener( controler );
		btnClear.addActionListener( controler );
		btnDivide.addActionListener( controler );
		btnDivide1.addActionListener( controler );
		btnDot.addActionListener( controler );
		btnEqual.addActionListener( controler );
		btnMultiply.addActionListener( controler );
		btnPosMinus.addActionListener( controler );
		btnSubtract.addActionListener( controler );
		
		btnLeft.addActionListener( controler );
		btnRight.addActionListener( controler );
		btnSin.addActionListener( controler );
		btnCos.addActionListener( controler );
		btnTan.addActionListener( controler );
		btnX2.addActionListener( controler );
		btnX3.addActionListener( controler );
		btnXY.addActionListener( controler );
		btnN.addActionListener( controler );
		btnSqrt.addActionListener( controler );
		
		//add things to calculator panel
		add( textField, BorderLayout.NORTH );
		add( buttonPanel, BorderLayout.CENTER );
	}
}
