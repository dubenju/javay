/**
 *
 */
package javay.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

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

        FlowLayout fly = new FlowLayout();
        fly.setHgap(5);
        fly.setVgap(5);
        buttonPanel.setLayout(fly);
//        buttonPanel.setLayout(null);
        buttonPanel.add(btnPnl1);
        buttonPanel.add(btnPnl2);
        buttonPanel.add(btnPnl3);
        buttonPanel.add(btnPnl4);

        // 开平方
        GridLayout gly1 = new GridLayout(5, 1);
        gly1.setHgap(3);
        gly1.setVgap(3);
//        btnPnl1.setLayout( gly1 );
        btnPnl1.setLayout(null);
        btnPnl1.setBounds(10, 100, 500, 25);
        btnPnl1.setBorder(BorderFactory.createLineBorder(Color.green));
//        btnSta.setBounds(10, 100, 500, 25);
        btnSta.setPreferredSize(new Dimension(30,30));
//        btnSta.setBorder(BorderFactory.createRaisedBevelBorder());
        btnPnl1.add( btnSta ).setForeground(Color.blue);
        btnPnl1.add( btnAve ).setForeground(Color.blue);
        btnPnl1.add( btnSum ).setForeground(Color.blue);
        btnPnl1.add( btnS ).setForeground(Color.blue);
        btnPnl1.add( btnDat ).setForeground(Color.blue);

        //create special button
//        // 平方
//        // 立方
//        // x的y次方
//        // 阶乘
//        // 倒数
//        // 左括号
//        // 右括号
//        // 正弦
//        // 余弦
//        // 正切

        GridLayout gly2 = new GridLayout(5, 3);
        gly2.setVgap(3);
        gly2.setHgap(3);
        btnPnl2.setLayout( gly2 );
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

//        Button bntMC = new Button("MC");
//        Button bntMR = new Button("MR");
//        Button bntMS = new Button("MS");
//        Button bntMP = new Button("M+");
//        Button bntMN = new Button("M-");

        GridLayout gly3 = new GridLayout(5, 1);
        gly3.setHgap(3);
        gly3.setVgap(3);
        btnPnl3.setLayout( gly3 );
        btnPnl3.add( btnMC ).setForeground(Color.red);
        btnPnl3.add( btnMR ).setForeground(Color.red);
        btnPnl3.add( btnMS ).setForeground(Color.red);
        btnPnl3.add( btnMP ).setForeground(Color.red);
        btnPnl3.add( btnMN ).setForeground(Color.red);

//        Button btnAnd = new Button("And");
//        Button btnOr  = new Button("Or");
//        Button btnNot = new Button("Not");
//        Button btnXor = new Button("Xor");

//        Button btnLsh = new Button("Lsh");
//        Button btnInt = new Button("Int");
        // 小数点
//        Button btnDot       = new Button(CalcultorConts.DOT);
//        Button btnPosMinus  = new Button(CalcultorConts.POS_MINUS);
        // 结果是
//        Button btnEqual     = new Button(CalcultorConts.EQUAL);
//        // 加
//        Button btnAdd       = new Button(CalcultorConts.ADD);
//        // 减
//        Button btnSubtract  = new Button(CalcultorConts.SUBTRACT);
//        // 乘
//        Button btnMultiply  = new Button(CalcultorConts.MULTIPLY);
//        // 除
//        Button btnDivide    = new Button(CalcultorConts.DIVIDE);
//        // 模
//        Button btnMod       = new Button(CalcultorConts.MOD);

//         0-9数字按钮
        //create buttons
//        Button[] numButtons = new Button[10];
        for ( int i = 0; i < 10; i++ ) {
            numButtons[i] = new JButton( "" + i );
        }

//        Button btnA = new Button("A");
//        Button btnB = new Button("B");
//        Button btnC = new Button("C");
//        Button btnD = new Button("D");
//        Button btnE = new Button("E");
//        Button btnF = new Button("F");
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
        btnE.setEnabled(false);
        btnF.setEnabled(false);

        //add these buttons to buttonPanel
        GridLayout gly4 = new GridLayout(5, 6);
        gly4.setHgap(3);
        gly4.setVgap(3);
        btnPnl4.setLayout( gly4 );

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
        // add( mainPanel, BorderLayout.NORTH );
        add( mainPanel, BorderLayout.CENTER );

        JLabel jlabel = new JLabel();
        jlabel.setFont(new Font("Dialog",   1,   15));
        jlabel.setForeground(Color.red);
        jlabel.setText(" ");
//        jlabel.setBorder(BorderFactory.createLineBorder(Color.red));
        JLabel jlabel2 = new JLabel();
        jlabel2.setFont(new Font("Dialog",   1,   15));
        jlabel2.setForeground(Color.red);
        jlabel2.setText(" ");
//        jlabel2.setBorder(BorderFactory.createLineBorder(Color.red));
        add(jlabel, BorderLayout.EAST);
        add(jlabel2, BorderLayout.WEST);

//        KeyAdapter keyAdapter = new CalcultorKeyAdapter();
//        this.addKeyListener(keyAdapter);
    }
}
