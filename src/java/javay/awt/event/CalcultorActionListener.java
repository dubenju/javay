/**
 *
 */
package javay.awt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import javay.math.BigNum;
import javay.swing.CalcultorConts;

/**
 * @author dubenju
 *
 */
public class CalcultorActionListener implements ActionListener {

	JTextField textField;
	//integer1 ,integer2
	String op1, op2, operator;
	String errMsg = "Error";
	//the state for now ,begin state = 0
	/*
	 * 0: 初期状态
	 * 1: 错误状态
	 * 2: 数值输入中
	 * 3: 得出结果
	 * 4: 操作符号输入完了
	 * 5: 第二个数值输入中
	 * other:
	 */
	int state = 0;

	/**
	 *
	 */
	public CalcultorActionListener(JTextField tf) {
		textField = tf;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch( state ) {
		case 0:
			inputState0(s);
			break;
		case 1:
			inputState1(s);
			break;
		case 2:
			inputState2(s);
			break;
		case 3:
			inputState3(s);
			break;
		case 4:
			inputState4(s);
			break;
		case 5:
			inputState5(s);
			break;
		default:
			System.out.println( "Unknow state error!state=" + state);
			System.exit(1);
		}
	}

	private boolean isDigit( String s ) {
		boolean b;
		b = s.equals("0")||s.equals("1")||s.equals("2")||s.equals("3")||s.equals("4")
			||s.equals("5")||s.equals("6")||s.equals("7")||s.equals("8")||s.equals("9");
		return b;
	}
	private int fN( float fop1 ) {
		int ruslt=0;
		if ( fop1 == 0 || fop1 == 1 ) {
			ruslt = 1;
		} else {
			ruslt = (int) (fop1 * fN(fop1-1) );
		}
		return ruslt;
	}
	private boolean isOperator(String s) {
		return s.equals(CalcultorConts.ADD) || s.equals(CalcultorConts.SUBTRACT) ||
				s.equals(CalcultorConts.MULTIPLY) || s.equals(CalcultorConts.DIVIDE) || s.equals(CalcultorConts.MOD)
				||s.equals(CalcultorConts.X2)||s.equals(CalcultorConts.X3)||s.equals(CalcultorConts.XY)
				||s.equals("sqrt")||s.equals("sin")||s.equals("cos")
				||s.equals("tan")||s.equals("n!")||s.equals("(")||s.equals(")");
	}

	//state 0 start
	private void inputState0( String s ) {
		if ( isDigit(s) || s.equals(CalcultorConts.POS_MINUS) || s.equals(CalcultorConts.DOT) ) {
			state = 2; // 数值输入中
			textField.setText("0");
			inputState2(s);
		}
		if ( isOperator(s) ) {
			op1 = "0";
			operator = s;
			state = 4; // 操作符号输入完了
		}
		if ( s.equals(CalcultorConts.DIVIDE1) ) {
			textField.setText(errMsg);
			state = 1; // 错误状态
		}
	}

	//state 1 error
	private void inputState1( String s ) {
		if ( isDigit(s)||s.equals(CalcultorConts.POS_MINUS)||s.equals(CalcultorConts.DOT) ) {
			textField.setText("0");
			state = 0; // 初期状态
			inputState0(s);
		} else {
			state = 0; // 初期状态
			textField.setText("0");
		}
	}

	//state 2 op1 reading,op1 is being input
	private void inputState2( String s ) {
		if ( isDigit(s) ) {
			String text = textField.getText();
			if ( text.equals("0") ) {
				text = s;
			} else {
				text = text + s;
			}
			textField.setText(text);
		}

		if ( s.equals(CalcultorConts.DOT) ) {
			String text = textField.getText();
			if ( !text.contains(".") ) {
				text = text + s;
				textField.setText(text);
			}
		}
		if ( s.equals(CalcultorConts.POS_MINUS) ) {
			String text = textField.getText();
			if ( text.charAt(0) == '-' ) {
				text = text.substring(1);
			} else {
				text = "-" + text;
			}
			textField.setText(text);
		}
		if ( isOperator(s) || s.equals(CalcultorConts.DIVIDE1) ) {
			state = 3; // 得出结果
			op1 = textField.getText();
			inputState3(s);
		}
		if ( s.equals(CalcultorConts.EQUAL) ) {
			state = 3; // 得出结果
			op1 = textField.getText();
		}
		if ( s.equals("C") ) {
			textField.setText("0");
		}
		if ( s.equals("Backspace") ) {
			String text = textField.getText();
			if ( text.length() == 1 ) {
				textField.setText("0");
			} else {
				textField.setText( text.substring(0, text.length()-1) );
			}
		}
	}

	//state 3 op1 read only, only op1 was input ,op2 = operator = null
	private void inputState3( String s ) {
		if ( isDigit(s) || s.equals(CalcultorConts.DOT) ) {
			op1 = "";
			textField.setText("0");
			state = 2; // 数值输入中
			inputState2(s);
		}
		if ( s.equals(CalcultorConts.POS_MINUS) ) {
			state = 2; // 数值输入中
			op1 = "";
			inputState2(s);
		}

		if ( isOperator(s) ) {
			operator = s;
			state = 4; // 操作符号输入完了
			if (operator.equals(CalcultorConts.X2)|| operator.equals(CalcultorConts.X3)||operator.equals("sin")
					||operator.equals("cos")||operator.equals("tan")||operator.equals("sqrt")
					||operator.equals("n!")) {
				inputState6(s);
			}
		}
		if ( s.equals(CalcultorConts.DIVIDE1) ) {
			//Float fOp1 = new Float(op1);
			BigNum nOp1 = new BigNum(op1);
			//if ( fOp1.floatValue() == 0.0 ) {
			if (nOp1.isZero()) {
				state = 1; // 错误状态
				//error
				textField.setText(errMsg);
			} else {
				// float f = 1.0f/fOp1.floatValue();
				BigNum value = new BigNum("1").divide(nOp1, 0, 0);
				// op1 = String.valueOf(f);
				op1 = value.toString();
				textField.setText(op1);
			}
		}

		if ( s.equals("C") || s.equals("Backspace") ) {
			state = 0; // 初期状态
			textField.setText("0");
		}
	}

	//state 2, op1 and operator are read, op2 = null
	private void inputState4( String s ) {
		if ( isDigit(s)||s.equals(CalcultorConts.POS_MINUS) || s.equals(CalcultorConts.DOT) ) {
			textField.setText("0");
			state = 5; // 第二个数值输入中
			inputState5(s);
		}

		if ( isOperator(s)) {
			operator = s;
		}
		if ( s.equals(CalcultorConts.DIVIDE1) ) {
			state = 3;
			operator = "";
			inputState3(s);
		}
		if ( s.equals("C") || s.equals("Backspace") ) {
			state = 0; // 初期状态
			textField.setText("0");
		}
	}

	//state5,op2 reading,in reading of op2
	private void inputState5( String s ) {
		if ( isDigit(s) ) {
			String text = textField.getText();
			if ( text.equals("0") ) {
				text = s;
			} else {
				text = text + s;
			}
			textField.setText(text);
		}
		if ( s.equals(CalcultorConts.DOT) ) {
			String text = textField.getText();
			if ( !text.contains(".") ) {
				text = text + s;
				textField.setText(text);
			}
		}
		if ( s.equals(CalcultorConts.POS_MINUS) ) {
			String text = textField.getText();
			if (text.charAt(0) == '-' ) {
				text = text.substring(1);
			} else {
				text = '-' + text;
			}
			textField.setText(text);
		}

		if ( isOperator(s) ) {
			op2 = textField.getText();
			BigNum nOp1 = new BigNum(op1);
			BigNum nOp2 = new BigNum(op2);
			if ( operator.equals(CalcultorConts.ADD) ) {
				nOp1 = nOp1.add(nOp2);
			} else if ( operator.equals(CalcultorConts.SUBTRACT) ) {
				nOp1 = nOp1.subtract(nOp2);
			} else if ( operator.equals(CalcultorConts.MULTIPLY) ) {
				nOp1 = nOp1.multiply(nOp2);
			} else if ( operator.equals(CalcultorConts.DIVIDE) ) {
				if (nOp2.isZero() == false) {
					nOp1 = nOp1.divide(nOp2, 0, 0);
				} else {
					state = 1; // 错误状态
					textField.setText(errMsg);
					return;
				}
			} else if ( operator.equals(CalcultorConts.MOD) ) {
				if (nOp2.isZero() == false) {
					nOp1 = nOp1.mod(nOp2);
				} else {
					state = 1; // 错误状态
					textField.setText(errMsg);
					return;
				}
			} else {
				System.out.println("Unknown operator error!operator=" + operator);
				state = 1; // 错误状态
				textField.setText(errMsg);
				return;
			}

			//here we got good calculating result
			op1 = nOp1.toString();
			textField.setText(op1);
			operator = s;
			state = 4;
		}
		if ( s.equals(CalcultorConts.DIVIDE1) ) {
			op1 = textField.getText();
			state = 3;
			inputState3(s);
		}
		if ( s.equals(CalcultorConts.EQUAL) ) {
			op2 = textField.getText();
			System.out.println("op1=" + op1 + " " + operator + " " + "op2=" + op2 + "=");
			BigNum nOp1 = new BigNum(op1);
			BigNum nOp2 = new BigNum(op2);
			if ( operator.equals(CalcultorConts.ADD) ) {
				nOp1 = nOp1.add(nOp2);
			} else if ( operator.equals(CalcultorConts.SUBTRACT) ) {
				nOp1 = nOp1.subtract(nOp2);
			} else if ( operator.equals(CalcultorConts.MULTIPLY) ) {
				nOp1 = nOp1.multiply(nOp2);
			} else if ( operator.equals(CalcultorConts.DIVIDE) ) {
				if (nOp2.isZero() == false) {
					nOp1 = nOp1.divide(nOp2, 0, 0);
				} else {
					state = 1; // 错误状态
					textField.setText(errMsg);
					return;
				}
			} else if ( operator.equals(CalcultorConts.MOD) ) {
				if (nOp2.isZero() == false) {
					nOp1 = nOp1.mod(nOp2);
				} else {
					state = 1; // 错误状态
					textField.setText(errMsg);
					return;
				}
			} else if ( operator.equals(CalcultorConts.XY) ) {
				nOp1 = nOp1.pow(nOp2);
			} else {
				System.out.println("Unknown operator error!operator=" + operator);
				state = 1; // 错误状态
				textField.setText(errMsg);
				return;
			}

			//here we got good calculating result
			op1 = nOp1.toString();
			textField.setText(op1);
			state = 3;
		}
		if ( s.equals("C") ) {
			state = 0;
			textField.setText("0");
		}
		if ( s.equals("Backspace") ) {
			String text = textField.getText();
			if ( text.length() == 1 ) {
				textField.setText("0");
			} else {
				textField.setText( text.substring(0, text.length()-1) );
			}
		}
	}

	//state6, new calculation
	private void inputState6( String s ) {
		Float f1;
		f1 = new Float(op1);
		float fop1;
		fop1 = f1.floatValue();
		if ( operator.equals("sqrt") ) {
			if ( fop1 < 0 ) {
				textField.setText(errMsg);
				return;
			} else {
				fop1 = (float) Math.sqrt( fop1 );
			}
		} else if ( operator.equals(CalcultorConts.X2) ) {
			fop1 = fop1 * fop1;
		} else if ( operator.equals(CalcultorConts.X3) ) {
			fop1 = fop1 * fop1 * fop1;
		} else if ( operator.equals("sin") ) {
			fop1 = (float) Math.sin( fop1 );
		} else if ( operator.equals("cos") ) {
			fop1 = (float) Math.cos( fop1 );
		} else if ( operator.equals("tan") ) {
			fop1 = (float) Math.tan( fop1 );
		} else if( operator.equals("n!") ) {
			fop1 = this.fN(fop1);
		}
		op1 = String.valueOf(fop1);
		textField.setText(op1);
	}
}
