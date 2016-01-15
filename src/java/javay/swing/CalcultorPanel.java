/**
 *
 */
package javay.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import javay.awt.event.CalcultorActionListener;

/**
 * @author dubenju
 *
 */
public class CalcultorPanel extends JPanel {

	private int btnWidth = 52;
	private int btnHeight = 28;
    private JPanel mainPanel = new JPanel();
    public JTextField expr = new JTextField();
    public JVariableTextField textField = new JVariableTextField( 34 );

    JPanel option = new JPanel();
    JPanel optionLeft = new JPanel();
    public JTextField mmry = new JTextField(5);
    ButtonGroup group = new ButtonGroup();
    JRadioButton r16 = new JRadioButton(CalcultorConts.HEXADECIMAL);
    JRadioButton r10 = new JRadioButton(CalcultorConts.DECIMAL);
    JRadioButton r8 = new JRadioButton(CalcultorConts.OCTAL);
    JRadioButton r2 = new JRadioButton(CalcultorConts.BINARY);

	JPanel optionRight = new JPanel();
	ButtonGroup group2 = new ButtonGroup();
	public JRadioButton deg = new JRadioButton("度");
	public JRadioButton rad = new JRadioButton("弧度");
	public JRadioButton grad = new JRadioButton("百分度");

    JPanel exOption = new JPanel();
    JPanel exOptLeft = new JPanel();
    // [sin]、[cos]、[tan]、[PI]、[x^y]、[x^2]、[x^3]、[ln]、[log]、[Ave]、[Sum]、[s]、[dms]、[Lsh]
    // arcsin,arccos,arctan,2PI,x的y次方根,x的2次方根,x的3次方根,e的n次方,10的n次方,?ave,?sum,?s,smd,rsh
    public JCheckBox inv = new JCheckBox("反计算"); // inverse
    // [sin]、[cos]、[tan]
    // sinh,cosh,tanh
    public JCheckBox hyp = new JCheckBox("双曲");// Hyperbolic

    JPanel exOptRight = new JPanel();
    // 退格
    JButton btnBackspace = new JButton(CalcultorConts.BACKSPACE);
    // 清除
    JButton btnCe     = new JButton(CalcultorConts.CLEAR_ERROR);
    // 清除
    JButton btnClear     = new JButton(CalcultorConts.CLEAR);

    JPanel buttonPanel = new JPanel();

    JPanel btnPnl1 = new JPanel();
    JPanel btnPnl2 = new JPanel();
    JPanel btnPnl3 = new JPanel();
    JPanel btnPnl4 = new JPanel();

    JButton btnSta    = new JButton("Sta");
    JButton btnAve    = new JButton(CalcultorConts.AVE);
    JButton btnSum    = new JButton(CalcultorConts.SUM);
    JButton btnS    = new JButton(CalcultorConts.SSD);
    JButton btnDat    = new JButton("Dat");

    // 科学记数法 Scientific notation
    public JButton btnFE = new JButton(CalcultorConts.SCI);
    public JButton btnDMS = new JButton(CalcultorConts.DMS);
    // 正弦
    public JButton btnSin   = new JButton(CalcultorConts.SIN);
    // 余弦
    public JButton btnCos   = new JButton(CalcultorConts.COS);
    // 正切
    public JButton btnTan   = new JButton(CalcultorConts.TAN);

    // 左括号
    JButton btnLeft  = new JButton("(");
    public JButton btnExp = new JButton(CalcultorConts.EXP);
    // x的y次方
    JButton btnXY    = new JButton(CalcultorConts.XY);
    // 立方
    JButton btnX3    = new JButton(CalcultorConts.X3);
    // 平方
    JButton btnX2    = new JButton(CalcultorConts.X2);

    // 右括号
    JButton btnRight = new JButton(")");
    JButton btnLn = new JButton(CalcultorConts.LN);
    JButton btnLog = new JButton(CalcultorConts.LOG);
    // 阶乘
    JButton btnN     = new JButton(CalcultorConts.N);
    // 倒数
    JButton btnDivide1   = new JButton(CalcultorConts.DIVIDE1);

    JButton btnMC = new JButton(CalcultorConts.MC);
    JButton btnMR = new JButton(CalcultorConts.MR);
    JButton btnMS = new JButton(CalcultorConts.MS);
    JButton btnMP = new JButton(CalcultorConts.MP);
    JButton btnMN = new JButton(CalcultorConts.MM);

    // 0-9数字按钮
    public JButton[] numButtons = new JButton[10];
    // A-F数字按钮
    public JButton btnA = new JButton(CalcultorConts.TEN);
    public JButton btnB = new JButton(CalcultorConts.ELEVEN);
    public JButton btnC = new JButton(CalcultorConts.TWELVE);
    public JButton btnD = new JButton(CalcultorConts.THRITEEN);
    public JButton btnE = new JButton(CalcultorConts.FOURTEEN);
    public JButton btnF = new JButton(CalcultorConts.FIFTEEN);

    JButton btnAnd = new JButton(CalcultorConts.AND);
    JButton btnOr  = new JButton(CalcultorConts.OR);
    JButton btnNot = new JButton(CalcultorConts.NOT);
    JButton btnXor = new JButton(CalcultorConts.XOR);

    JButton btnLsh = new JButton(CalcultorConts.LSH);
    JButton btnInt = new JButton(CalcultorConts.INT);
    // 小数点
    JButton btnDot       = new JButton(CalcultorConts.DOT);
    JButton btnPosMinus  = new JButton(CalcultorConts.POS_MINUS);
    // 结果是
    JButton btnEqual     = new JButton(CalcultorConts.EQUAL);
    // 加
    JButton btnAdd       = new JButton(CalcultorConts.ADD);
    // 减
    JButton btnSubtract  = new JButton(CalcultorConts.SUBTRACT);
    // 乘
    JButton btnMultiply  = new JButton(CalcultorConts.MULTIPLY);
    // 除
    JButton btnDivide    = new JButton(CalcultorConts.DIVIDE);
    // 模
    JButton btnMod       = new JButton(CalcultorConts.MOD);

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CalcultorPanel() {
    	this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

        //create the text field
    	this.mainPanel.add(this.expr);
    	this.mainPanel.add(this.textField);
        this.expr.setHorizontalAlignment(JTextField.RIGHT);
        this.expr.setEditable(false);
        this.expr.setFont(new Font("Dialog", Font.PLAIN, 36));
        this.expr.setText("");
        this.expr.setToolTipText("表达式");

        this.textField.setHorizontalAlignment(JTextField.RIGHT);
        this.textField.setEditable( false );
        this.textField.setFont(new Font("Dialog", Font.PLAIN, 36));
        this.textField.getMaxWidth();
        LineBorder border = new LineBorder(Color.RED, 2, true);
        this.textField.setBorder(border);
        this.textField.setText("0");
        this.textField.setToolTipText("计算式");

        /* A */
        mainPanel.add(option);

        option.add(this.mmry);
        option.add(optionLeft);
        optionLeft.setBorder(BorderFactory.createLineBorder(Color.red));

        this.mmry.setHorizontalAlignment(JTextField.RIGHT);
        r10.setSelected(true);
		group.add(r16);
		group.add(r10);
		group.add(r8);
		group.add(r2);

		optionLeft.add(r16);
		optionLeft.add(r10);
		optionLeft.add(r8);
		optionLeft.add(r2);

		JPanel optionRight = new JPanel();
		option.add(optionRight);
		optionRight.setBorder(BorderFactory.createLineBorder(Color.green));
		deg.setSelected(true);
		group2.add(deg);
		group2.add(rad);
		group2.add(grad);

		optionRight.add(deg);
		optionRight.add(rad);
		optionRight.add(grad);

		/* B */
        mainPanel.add(exOption);
        exOption.add(exOptLeft);
        exOptLeft.setBorder(BorderFactory.createLineBorder(Color.green));
        exOptLeft.add(inv);
        exOptLeft.add(hyp);

        exOption.add(exOptRight);
        exOptRight.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // 退格
        // 清除
        // 清除
        exOptRight.add( btnBackspace ).setForeground(Color.red);
        exOptRight.add( btnCe ).setForeground(Color.red);
        exOptRight.add( btnClear ).setForeground(Color.red);

        /* C */
        mainPanel.add(buttonPanel);

        buttonPanel.setLayout(null);
        buttonPanel.setPreferredSize(new Dimension((btnWidth + 1) * 11, (btnHeight + 1) * 5));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.green));
        buttonPanel.add(btnPnl1);
        buttonPanel.add(btnPnl2);
        buttonPanel.add(btnPnl3);
        buttonPanel.add(btnPnl4);

        // 开平方
        btnPnl1.setLayout(null);
        btnPnl1.setBounds(0, 0, btnWidth + 1, (btnHeight + 1) * 5);
        btnPnl1.add( btnSta ).setForeground(Color.blue);
        btnPnl1.add( btnAve ).setForeground(Color.blue);
        btnPnl1.add( btnSum ).setForeground(Color.blue);
        btnPnl1.add( btnS ).setForeground(Color.blue);
        btnPnl1.add( btnDat ).setForeground(Color.blue);

        btnSta.setBounds(1, 1, btnWidth, btnHeight);
        btnAve.setBounds(1, btnSta.getY() + btnHeight, btnWidth, btnHeight);
        btnSum.setBounds(1, btnAve.getY() + btnHeight, btnWidth, btnHeight);
        btnS.setBounds(1, btnSum.getY() + btnHeight, btnWidth, btnHeight);
        btnDat.setBounds(1, btnS.getY() + btnHeight, btnWidth, btnHeight);

        //create special button
        // 平方
        // 立方
        // x的y次方
        // 阶乘
        // 倒数
        // 左括号
        // 右括号
        // 正弦
        // 余弦
        // 正切
        btnPnl2.setLayout(null);
        btnPnl2.setBounds(btnPnl1.getX() + btnPnl1.getWidth(), 0,(btnWidth + 1) * 3, (btnHeight + 1) * 5);
        btnPnl2.add( btnFE ).setForeground(Color.red);
        btnPnl2.add( btnLeft ).setForeground(Color.red);
        btnPnl2.add( btnRight ).setForeground(Color.red);
        btnPnl2.add( btnDMS ).setForeground(Color.red);
        btnPnl2.add( btnExp ).setForeground(Color.red);
        btnPnl2.add( btnLn ).setForeground(Color.red);
        btnPnl2.add( btnSin ).setForeground(Color.red);
        btnPnl2.add( btnXY ).setForeground(Color.red);
        btnPnl2.add( btnLog ).setForeground(Color.red);
        btnPnl2.add( btnCos ).setForeground(Color.red);
        btnPnl2.add( btnX3 ).setForeground(Color.red);
        btnPnl2.add( btnN ).setForeground(Color.red);
        btnPnl2.add( btnTan ).setForeground(Color.red);
        btnPnl2.add( btnX2 ).setForeground(Color.red);
        btnPnl2.add( btnDivide1 ).setForeground(Color.red);

        btnFE.setBounds(1, 1, btnWidth, btnHeight);
        btnDMS.setBounds(btnFE.getX(), btnFE.getY() + btnHeight, btnWidth, btnHeight);
        btnSin.setBounds(btnDMS.getX(), btnDMS.getY() + btnHeight, btnWidth, btnHeight);
        btnCos.setBounds(btnSin.getX(), btnSin.getY() + btnHeight, btnWidth, btnHeight);
        btnTan.setBounds(btnCos.getX(), btnCos.getY() + btnHeight, btnWidth, btnHeight);

        btnLeft.setBounds(btnFE.getX() + btnWidth + 1, 1, btnWidth, btnHeight);
        btnExp.setBounds(btnLeft.getX(), btnLeft.getY() + btnHeight, btnWidth, btnHeight);
        btnXY.setBounds(btnExp.getX(), btnExp.getY() + btnHeight, btnWidth, btnHeight);
        btnX3.setBounds(btnXY.getX(), btnXY.getY() + btnHeight, btnWidth, btnHeight);
        btnX2.setBounds(btnX3.getX(), btnX3.getY() + btnHeight, btnWidth, btnHeight);

        btnRight.setBounds(btnLeft.getX() + btnWidth + 1, 1, btnWidth, btnHeight);
        btnLn.setBounds(btnRight.getX(), btnRight.getY() + btnHeight, btnWidth, btnHeight);
        btnLog.setBounds(btnLn.getX(), btnLn.getY() + btnHeight, btnWidth, btnHeight);
        btnN.setBounds(btnLog.getX(), btnLog.getY() + btnHeight, btnWidth, btnHeight);
        btnDivide1.setBounds(btnN.getX(), btnN.getY() + btnHeight, btnWidth, btnHeight);

        btnPnl3.setLayout(null);
        btnPnl3.setBounds(btnPnl2.getX() + btnPnl2.getWidth(), 0, btnWidth + 1, (btnHeight + 1) * 5);
        btnPnl3.add( btnMC ).setForeground(Color.red);
        btnPnl3.add( btnMR ).setForeground(Color.red);
        btnPnl3.add( btnMS ).setForeground(Color.red);
        btnPnl3.add( btnMP ).setForeground(Color.red);
        btnPnl3.add( btnMN ).setForeground(Color.red);

        btnMC.setBounds(1, 1, btnWidth, btnHeight);
        btnMR.setBounds(1, btnMC.getY() + btnHeight, btnWidth, btnHeight);
        btnMS.setBounds(1, btnMR.getY() + btnHeight, btnWidth, btnHeight);
        btnMP.setBounds(1, btnMS.getY() + btnHeight, btnWidth, btnHeight);
        btnMN.setBounds(1, btnMP.getY() + btnHeight, btnWidth, btnHeight);

        // 小数点
        // 结果是
        // 加
        // 减
        // 乘
        // 除
        // 模

        // 0-9数字按钮
        //create buttons
        for ( int i = 0; i < 10; i++ ) {
            numButtons[i] = new JButton( "" + i );
        }

        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
        btnE.setEnabled(false);
        btnF.setEnabled(false);

        //add these buttons to buttonPanel
        btnPnl4.setLayout(null);
        btnPnl4.setBounds(btnPnl3.getX() + btnPnl3.getWidth(), 0, (btnWidth + 1) * 6, (btnHeight + 1) * 5);
        btnPnl4.add( numButtons[7] ).setForeground(Color.blue);
        btnPnl4.add( numButtons[8] ).setForeground(Color.blue);
        btnPnl4.add( numButtons[9] ).setForeground(Color.blue);
        btnPnl4.add( btnDivide ).setForeground(Color.red);
        btnPnl4.add( btnMod ).setForeground(Color.red);
        btnPnl4.add( btnAnd ).setForeground(Color.red);

        btnPnl4.add( numButtons[4] ).setForeground(Color.blue);
        btnPnl4.add( numButtons[5] ).setForeground(Color.blue);
        btnPnl4.add( numButtons[6] ).setForeground(Color.blue);
        btnPnl4.add( btnMultiply ).setForeground(Color.red);
        btnPnl4.add( btnOr ).setForeground(Color.red);
        btnPnl4.add( btnXor ).setForeground(Color.red);

        btnPnl4.add( numButtons[1] ).setForeground(Color.blue);
        btnPnl4.add( numButtons[2] ).setForeground(Color.blue);
        btnPnl4.add( numButtons[3] ).setForeground(Color.blue);
        btnPnl4.add( btnSubtract ).setForeground(Color.red);
        btnPnl4.add( btnLsh ).setForeground(Color.red);
        btnPnl4.add( btnNot ).setForeground(Color.red);

        btnPnl4.add( numButtons[0] ).setForeground(Color.blue);
        btnPnl4.add( btnPosMinus ).setForeground(Color.blue);
        btnPnl4.add( btnDot ).setForeground(Color.blue);
        btnPnl4.add( btnAdd ).setForeground(Color.red);
        btnPnl4.add( btnEqual ).setForeground(Color.red);
        btnPnl4.add( btnInt ).setForeground(Color.red);

        btnPnl4.add( btnA ).setForeground(Color.blue);
        btnPnl4.add( btnB ).setForeground(Color.blue);
        btnPnl4.add( btnC ).setForeground(Color.blue);
        btnPnl4.add( btnD ).setForeground(Color.blue);
        btnPnl4.add( btnE ).setForeground(Color.blue);
        btnPnl4.add( btnF ).setForeground(Color.blue);

        numButtons[7].setBounds(1, 1, btnWidth, btnHeight);
        numButtons[4].setBounds(numButtons[7].getX(), numButtons[7].getY() + btnHeight, btnWidth, btnHeight);
        numButtons[1].setBounds(numButtons[4].getX(), numButtons[4].getY() + btnHeight, btnWidth, btnHeight);
        numButtons[0].setBounds(numButtons[1].getX(), numButtons[1].getY() + btnHeight, btnWidth, btnHeight);
        btnA.setBounds(numButtons[0].getX(), numButtons[0].getY() + btnHeight, btnWidth, btnHeight);

        numButtons[8].setBounds(numButtons[7].getX() + btnWidth + 1, 1, btnWidth, btnHeight);
        numButtons[5].setBounds(numButtons[8].getX(), numButtons[8].getY() + btnHeight, btnWidth, btnHeight);
        numButtons[2].setBounds(numButtons[5].getX(), numButtons[5].getY() + btnHeight, btnWidth, btnHeight);
        btnPosMinus.setBounds(numButtons[2].getX(), numButtons[2].getY() + btnHeight, btnWidth, btnHeight);
        btnB.setBounds(btnPosMinus.getX(), btnPosMinus.getY() + btnHeight, btnWidth, btnHeight);

        numButtons[9].setBounds(numButtons[8].getX() + btnWidth + 1, 1, btnWidth, btnHeight);
        numButtons[6].setBounds(numButtons[9].getX(), numButtons[9].getY() + btnHeight, btnWidth, btnHeight);
        numButtons[3].setBounds(numButtons[6].getX(), numButtons[6].getY() + btnHeight, btnWidth, btnHeight);
        btnDot.setBounds(numButtons[3].getX(), numButtons[3].getY() + btnHeight, btnWidth, btnHeight);
        btnC.setBounds(btnDot.getX(), btnDot.getY() + btnHeight, btnWidth, btnHeight);

        btnDivide.setBounds(numButtons[9].getX() + btnWidth + 1, 1, btnWidth, btnHeight);
        btnMultiply.setBounds(btnDivide.getX(), btnDivide.getY() + btnHeight, btnWidth, btnHeight);
        btnSubtract.setBounds(btnMultiply.getX(), btnMultiply.getY() + btnHeight, btnWidth, btnHeight);
        btnAdd.setBounds(btnSubtract.getX(), btnSubtract.getY() + btnHeight, btnWidth, btnHeight);
        btnD.setBounds(btnAdd.getX(), btnAdd.getY() + btnHeight, btnWidth, btnHeight);

        btnMod.setBounds(btnDivide.getX() + btnWidth + 1, 1, btnWidth, btnHeight);
        btnOr.setBounds(btnMod.getX(), btnMod.getY() + btnHeight, btnWidth, btnHeight);
        btnLsh.setBounds(btnOr.getX(), btnOr.getY() + btnHeight, btnWidth, btnHeight);
        btnEqual.setBounds(btnLsh.getX(), btnLsh.getY() + btnHeight, btnWidth, btnHeight);
        btnE.setBounds(btnEqual.getX(), btnEqual.getY() + btnHeight, btnWidth, btnHeight);

        btnAnd.setBounds(btnMod.getX() + btnWidth + 1, 1, btnWidth, btnHeight);
        btnXor.setBounds(btnAnd.getX(), btnAnd.getY() + btnHeight, btnWidth, btnHeight);
        btnNot.setBounds(btnXor.getX(), btnXor.getY() + btnHeight, btnWidth, btnHeight);
        btnInt.setBounds(btnNot.getX(), btnNot.getY() + btnHeight, btnWidth, btnHeight);
        btnF.setBounds(btnInt.getX(), btnInt.getY() + btnHeight, btnWidth, btnHeight);

        //create the control
        CalcultorActionListener controler = new CalcultorActionListener( this );

        r16.addActionListener(controler);
        r10.addActionListener(controler);
        r8.addActionListener(controler);
        r2.addActionListener(controler);

        btnBackspace.addActionListener( controler );
        btnCe.addActionListener( controler );
        btnClear.addActionListener( controler );

        btnFE.addActionListener( controler );
        btnLeft.addActionListener( controler );
        btnRight.addActionListener( controler );
        btnSin.addActionListener( controler );
        btnCos.addActionListener( controler );
        btnTan.addActionListener( controler );
        btnX2.addActionListener( controler );
        btnX3.addActionListener( controler );
        btnXY.addActionListener( controler );
        btnN.addActionListener( controler );
        btnDivide1.addActionListener( controler );

        btnDMS.addActionListener( controler );
        btnExp.addActionListener( controler );
        btnLn.addActionListener( controler );
        btnLog.addActionListener( controler );
//        btnSqrt.addActionListener( controler );

        btnMC.addActionListener( controler );
        btnMR.addActionListener( controler );
        btnMS.addActionListener( controler );
        btnMP.addActionListener( controler );
        btnMN.addActionListener( controler );

        //add let the control listen to the buttons
        for( int i = 0; i < 10; i++ ) {
            numButtons[i].addActionListener( controler );
        }
        btnA.addActionListener( controler );
        btnB.addActionListener( controler );
        btnC.addActionListener( controler );
        btnD.addActionListener( controler );
        btnE.addActionListener( controler );
        btnF.addActionListener( controler );

        btnAdd.addActionListener( controler );
        btnSubtract.addActionListener( controler );
        btnMultiply.addActionListener( controler );
        btnDivide.addActionListener( controler );
        btnMod.addActionListener( controler );
        btnEqual.addActionListener( controler );

        btnDot.addActionListener( controler );
        btnPosMinus.addActionListener( controler );

        btnLsh.addActionListener( controler );
        btnInt.addActionListener( controler );

        btnAnd.addActionListener( controler );
        btnOr.addActionListener( controler );
        btnNot.addActionListener( controler );
        btnXor.addActionListener( controler );

        this.setLayout(new BorderLayout());
        //add things to calculator panel
        add( mainPanel, BorderLayout.CENTER );

        JLabel jlabel = new JLabel();
        jlabel.setFont(new Font("Dialog",   1,   15));
        jlabel.setForeground(Color.red);
        jlabel.setText(" ");
        JLabel jlabel2 = new JLabel();
        jlabel2.setFont(new Font("Dialog",   1,   15));
        jlabel2.setForeground(Color.red);
        jlabel2.setText(" ");
        add(jlabel, BorderLayout.EAST);
        add(jlabel2, BorderLayout.WEST);
    }
}
