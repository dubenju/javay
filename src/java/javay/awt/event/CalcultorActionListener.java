/**
 *
 */
package javay.awt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.math.MathBn;
import javay.swing.CalcultorConts;
import javay.swing.CalcultorPanel;

/**
 * 第一种：自身类实现ActionListener接口，作为事件监听器。
 * 第一种的监听处理部分，如果有多个，那就需要一个个去判断，从理论上说是影响程序速度的。
 *
 * 第二种：通过匿名类处理。
 * 第二种和第三种比较常用，如果程序的监听事件比较少，可以用第二种，匿名类很合适。
 *
 * 第三种：通过内部类处理。
 * 第三种符合面向对象编程（可以设置内部类只提供自身类使用，而且方便使用自身类的资源），尤其是适合多个监听事件的处理，当然也适合第二种方法情况。
 *
 * 第四种：通过外部类处理。
 * 第四种是外部类，如果多个监听事件相同，就可以选用此种方法。
 * ActionListener接口，作为事件监听器。
 * @author dubenju
 *
 */
public class CalcultorActionListener implements ActionListener {

	//private JTextField textField;
	private CalcultorPanel panel;
	//integer1 ,integer2
	private String op1, op2, operator;
	private String errMsg = "Error";
	private String mem;
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
//	public CalcultorActionListener(JTextField tf) {
//		textField = tf;
//	}
	public CalcultorActionListener(CalcultorPanel panel) {
        this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		System.out.println("state=" + state + ",s=" + s);
		switch( state ) {
		case 0:
			// 0: 初期状态
			inputState0(s);
			break;
		case 1:
			// 1: 错误状态
			inputState1(s);
			break;
		case 2:
			// 2: 数值输入中
			inputState2(s);
			break;
		case 3:
			// 3: 得出结果
			inputState3(s);
			break;
		case 4:
			// 4: 操作符号输入完了
			inputState4(s);
			break;
		case 5:
			// 5: 第二个数值输入中
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

	private boolean isOperator(String s) {
		return isOperator1(s) || isOperator2(s) ||
				s.equals("sqrt")  ||s.equals("(")||s.equals(")");
	}

	private boolean isOperator1(String s) {
		return s.equals(CalcultorConts.DIVIDE1)  || s.equals(CalcultorConts.N) ||
				s.equals(CalcultorConts.LOG) || s.equals(CalcultorConts.LN) ||
				s.equals(CalcultorConts.X2) || s.equals(CalcultorConts.X3) ||
				s.equals(CalcultorConts.EXP) || s.equals(CalcultorConts.SIN) ||
				s.equals(CalcultorConts.COS) || s.equals(CalcultorConts.TAN) ||
				s.equals(CalcultorConts.DMS);
	}

	private boolean isOperator2(String s) {
		return s.equals(CalcultorConts.ADD) || s.equals(CalcultorConts.SUBTRACT) ||
				s.equals(CalcultorConts.MULTIPLY) || s.equals(CalcultorConts.DIVIDE) ||
				s.equals(CalcultorConts.MOD) || s.equals(CalcultorConts.XY);
	}
	/**
	 * 0: 初期状态
	 * state 0 start
	 * @param s
	 */
	private void inputState0( String s ) {
		if ( isDigit(s) || s.equals(CalcultorConts.POS_MINUS) || s.equals(CalcultorConts.DOT) ) {
			state = 2; // 数值输入中
			this.panel.textField.setText("0");
			inputState2(s);
		}
		if ( isOperator1(s)) {
			state = 1; // 错误状态
			this.panel.textField.setText(errMsg);
		} else if ( isOperator(s) ) {
			state = 4; // 操作符号输入完了
			op1 = "0";
			operator = s;
		}

	}

	/**
	 * 1: 错误状态
	 * state 1 error
	 * @param s
	 */
	private void inputState1( String s ) {
		if ( isDigit(s) ||s.equals(CalcultorConts.POS_MINUS) || s.equals(CalcultorConts.DOT) ) {
			state = 0; // 初期状态
			this.panel.textField.setText("0");
			inputState0(s);
		} else {
			//state = 0; // 初期状态
			//textField.setText("0");
		}
		// TODO:Clear
		if ( s.equals(CalcultorConts.CLEAR) ) {
			// 清除,不管在任何情况下都是回到初始状态的0
			state = 0; // 初期状态
			this.panel.textField.setText("0");
		}
		if ( s.equals(CalcultorConts.CLEAR_ERROR) ) {
			// 纠错,在输入数字之后，我按住ce键即可恢复到0，但是对前面的运算和输入是没有关联。
			state = 0; // 初期状态
			this.panel.textField.setText("0");
		}
	}

	/**
	 * 2: 数值输入中
	 * state 2 op1 reading,op1 is being input
	 * @param s
	 */
	private void inputState2( String s ) {
		if ( isDigit(s) ) {
			String text = this.panel.textField.getText();
			if ( text.equals("0") ) {
				text = s;
			} else {
				text = text + s;
			}
			this.panel.textField.setText(text);
		}

		if ( s.equals(CalcultorConts.DOT) ) {
			String text = this.panel.textField.getText();
			if ( !text.contains(".") ) {
				text = text + s;
				this.panel.textField.setText(text);
			}
		}
		if ( s.equals(CalcultorConts.POS_MINUS) ) {
			String text = this.panel.textField.getText();
			if ( text.charAt(0) == '-' ) {
				text = text.substring(1);
			} else {
				text = "-" + text;
			}
			this.panel.textField.setText(text);
		}

		if ( isOperator(s)) {
			state = 3; // 得出结果
			op1 = this.panel.textField.getText();
			inputState3(s);
		}
		if ( s.equals(CalcultorConts.EQUAL) ) {
			state = 3; // 得出结果
			op1 = this.panel.textField.getText();
		}

		//TODO:stateSB
		if ( s.equals(CalcultorConts.CLEAR) ) {
			// 清除,不管在任何情况下都是回到初始状态的0
			this.panel.textField.setText("0");
		}
		if ( s.equals(CalcultorConts.CLEAR_ERROR) ) {
			// 纠错,在输入数字之后，我按住ce键即可恢复到0，但是对前面的运算和输入是没有关联。
			this.panel.textField.setText("0");
		}
		if ( s.equals(CalcultorConts.BACKSPACE) ) {
			String text = this.panel.textField.getText();
			if ( text.length() == 1 ) {
				this.panel.textField.setText("0");
			} else {
				this.panel.textField.setText( text.substring(0, text.length()-1) );
			}
		}
	}

	/**
	 * 3: 得出结果
	 * state 3 op1 read only, only op1 was input ,op2 = operator = null
	 * @param s
	 */
	private void inputState3( String s ) {
		if ( isDigit(s) || s.equals(CalcultorConts.DOT) ) {
			state = 2; // 数值输入中
			this.panel.textField.setText("0");
			op1 = "";
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
			if ( this.isOperator1(operator) ||operator.equals("sqrt")) {
				inputState6(s);
			}
		}
		// TODO:??
		if ( s.equals(CalcultorConts.DIVIDE1) ) {
			BigNum nOp1 = new BigNum(op1);
			if (nOp1.isZero()) {
				// error
				state = 1; // 错误状态
				this.panel.textField.setText(errMsg);
			} else {
				BigNum value = new BigNum("1").divide(nOp1, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
				op1 = value.toString();
				this.panel.textField.setText(op1);
			}
		}

		if ( s.equals(CalcultorConts.CLEAR) || s.equals(CalcultorConts.CLEAR_ERROR) || s.equals(CalcultorConts.BACKSPACE) ) {
			// TODO:CE
			state = 0; // 初期状态
			this.panel.textField.setText("0");
		}
	}

	/**
	 * 4: 操作符号输入完了
	 * state 4, op1 and operator are read, op2 = null
	 * @param s
	 */
	private void inputState4( String s ) {
		if ( isDigit(s)||s.equals(CalcultorConts.POS_MINUS) || s.equals(CalcultorConts.DOT) ) {
			this.panel.textField.setText("0");
			state = 5; // 第二个数值输入中
			inputState5(s);
		}

		if ( isOperator(s)) {
			operator = s;
		}
		if ( this.isOperator1(s) ) {
			state = 3;
			operator = "";
			inputState3(s);
		}
		if ( s.equals(CalcultorConts.CLEAR) || s.equals(CalcultorConts.CLEAR_ERROR) || s.equals(CalcultorConts.BACKSPACE) ) {
			// TODO:CE
			state = 0; // 初期状态
			this.panel.textField.setText("0");
		}
	}

	/**
	 * 5: 第二个数值输入中
	 * state5,op2 reading,in reading of op2
	 * @param s
	 */
	private void inputState5( String s ) {
		if ( isDigit(s) ) {
			String text = this.panel.textField.getText();
			if ( text.equals("0") ) {
				text = s;
			} else {
				text = text + s;
			}
			this.panel.textField.setText(text);
		}
		if ( s.equals(CalcultorConts.DOT) ) {
			String text = this.panel.textField.getText();
			if ( !text.contains(".") ) {
				text = text + s;
				this.panel.textField.setText(text);
			}
		}
		if ( s.equals(CalcultorConts.POS_MINUS) ) {
			String text = this.panel.textField.getText();
			if (text.charAt(0) == '-' ) {
				text = text.substring(1);
			} else {
				text = '-' + text;
			}
			this.panel.textField.setText(text);
		}

		if ( this.isOperator1(s) ) {
			op1 = this.panel.textField.getText();
			state = 3;
			inputState3(s);
		} else if ( isOperator(s) ) {
			op2 = this.panel.textField.getText();
			BigNum nOp1 = new BigNum(op1);
			BigNum nOp2 = new BigNum(op2);
			if ( operator.equals(CalcultorConts.ADD) ) {
				// +
				nOp1 = nOp1.add(nOp2);
			} else if ( operator.equals(CalcultorConts.SUBTRACT) ) {
				// -
				nOp1 = nOp1.subtract(nOp2);
			} else if ( operator.equals(CalcultorConts.MULTIPLY) ) {
				// *
				nOp1 = nOp1.multiply(nOp2);
			} else if ( operator.equals(CalcultorConts.DIVIDE) ) {
				// /
				if (nOp2.isZero() == false) {
					nOp1 = nOp1.divide(nOp2, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
				} else {
					state = 1; // 错误状态
					this.panel.textField.setText(errMsg);
					return;
				}
			} else if ( operator.equals(CalcultorConts.MOD) ) {
				// %
				if (nOp2.isZero() == false) {
					nOp1 = nOp1.mod(nOp2);
				} else {
					state = 1; // 错误状态
					this.panel.textField.setText(errMsg);
					return;
				}
			} else {
				System.out.println("@inputState5:Unknown operator error!operator=" + operator);
				state = 1; // 错误状态
				this.panel.textField.setText(errMsg);
				return;
			}

			//here we got good calculating result
			op1 = nOp1.toString();
			this.panel.textField.setText(op1);
			operator = s;
			state = 4;
		}

		if ( s.equals(CalcultorConts.EQUAL) ) {
			op2 = this.panel.textField.getText();
			System.out.println("op1=" + op1 + " " + operator + " " + "op2=" + op2 + "=");
			BigNum nOp1 = new BigNum(op1);
			BigNum nOp2 = new BigNum(op2);
			if ( operator.equals(CalcultorConts.ADD) ) {
				// +
				nOp1 = nOp1.add(nOp2);
			} else if ( operator.equals(CalcultorConts.SUBTRACT) ) {
				// -
				nOp1 = nOp1.subtract(nOp2);
			} else if ( operator.equals(CalcultorConts.MULTIPLY) ) {
				// *
				nOp1 = nOp1.multiply(nOp2);
			} else if ( operator.equals(CalcultorConts.DIVIDE) ) {
				// /
				if (nOp2.isZero() == false) {
					nOp1 = nOp1.divide(nOp2, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
				} else {
					state = 1; // 错误状态
					this.panel.textField.setText(errMsg);
					return;
				}
			} else if ( operator.equals(CalcultorConts.MOD) ) {
				// %
				if (nOp2.isZero() == false) {
					nOp1 = nOp1.mod(nOp2);
				} else {
					state = 1; // 错误状态
					this.panel.textField.setText(errMsg);
					return;
				}
			} else if ( operator.equals(CalcultorConts.XY) ) {
				// X^Y
				nOp1 = nOp1.pow(nOp2);
			} else {
				System.out.println("@inputState5@Unknown operator error!operator=" + operator);
				state = 1; // 错误状态
				this.panel.textField.setText(errMsg);
				return;
			}

			//here we got good calculating result
			op1 = nOp1.toString();
			this.panel.textField.setText(op1);
			state = 3;
		}
		if ( s.equals(CalcultorConts.CLEAR) ) {
			state = 0;
			this.panel.textField.setText("0");
		}
		if ( s.equals(CalcultorConts.CLEAR_ERROR) ) {
			state = 0;
			this.panel.textField.setText("0");
		}
		if ( s.equals(CalcultorConts.BACKSPACE) ) {
			String text = this.panel.textField.getText();
			if ( text.length() == 1 ) {
				this.panel.textField.setText("0");
			} else {
				this.panel.textField.setText( text.substring(0, text.length()-1) );
			}
		}
	}

	/**
	 * state6, new calculation
	 * @param s
	 */
	private void inputState6( String s ) {
		BigNum bop1 = new BigNum(op1);
		if ( operator.equals("sqrt") ) {
//			if ( fop1 < 0 ) {
//				textField.setText(errMsg);
//				return;
//			} else {
//				fop1 = (float) Math.sqrt( fop1 );
//			}
		} else if ( operator.equals(CalcultorConts.X2) ) {
			bop1 = bop1.pow(2);
		} else if ( operator.equals(CalcultorConts.X3) ) {
			bop1 = bop1.pow(3);
		} else if ( operator.equals(CalcultorConts.SIN) ) {
			BigNum r = MathBn.toRadians(bop1);
			bop1 = MathBn.sin( r );
		} else if ( operator.equals(CalcultorConts.COS) ) {
			BigNum r = MathBn.toRadians(bop1);
			bop1 = MathBn.cos( r );
		} else if ( operator.equals(CalcultorConts.TAN) ) {
			BigNum r = MathBn.toRadians(bop1);
			bop1 = MathBn.tan( r );
		} else if( operator.equals(CalcultorConts.N) ) {
			bop1 = bop1.factorial();
		} else if( operator.equals(CalcultorConts.LN) ) {
			bop1 = bop1.ln(2, BigNumRound.HALF_EVENT);
		} else if( operator.equals(CalcultorConts.EXP) ) {
			bop1 = BigNum.E.pow(bop1);
		} else if( operator.equals(CalcultorConts.DMS) ) {
			if (this.panel.inv.isSelected()) {
				bop1 = MathBn.smd(bop1);
				this.panel.inv.setSelected(false);
			} else {
				bop1 = MathBn.dms(bop1);
			}
		}
		op1 = String.valueOf(bop1);
		this.panel.textField.setText(op1);
	}
}
