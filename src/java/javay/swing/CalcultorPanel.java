/**
 *
 */
package javay.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
    private JPanel topDisplay = new JPanel();
    public DefaultListModel<String> modelHistory = new DefaultListModel<String>();
    public DefaultListModel<String> modelStatistics = new DefaultListModel<String>();
    public JList<String> listHistory = new JList<String>(this.modelHistory);
    public JList<String> listStatistics = new JList<String>(this.modelStatistics);
	JScrollPane spHistory = new JScrollPane();
	JScrollPane spStatistics = new JScrollPane();
    public JTextField expr = new JTextField();
    public JVariableTextField textField = new JVariableTextField(25);

    JPanel option = new JPanel();
    JPanel optionLeft = new JPanel();
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
    JButton btnClear     = new JButton("C");

    JPanel buttonPanel = new JPanel();

    JPanel btnPnl1 = new JPanel();
    JPanel btnPnl2 = new JPanel();
    JPanel btnPnl3 = new JPanel();
    JPanel btnPnl4 = new JPanel();

    JButton btnSta    = new JButton("add");
    public JButton btnAve    = new JButton(CalcultorConts.AVE);
    public JButton btnSum    = new JButton(CalcultorConts.SUM);
    public JButton btnS    = new JButton(CalcultorConts.SSD);
    public JButton btnDat    = new JButton("Dat");

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
    	this.mainPanel.add(this.topDisplay);

    	this.topDisplay.setLayout(null);
    	this.topDisplay.setPreferredSize(new Dimension((btnWidth + 1) * 11, 210));
//    	this.topDisplay.setBorder(BorderFactory.createLineBorder(Color.red));
    	this.topDisplay.add(this.expr);
    	this.topDisplay.add(this.textField);

    	DefaultListCellRenderer rendererStatistics = (DefaultListCellRenderer) this.listStatistics.getCellRenderer();
    	rendererStatistics.setHorizontalAlignment(SwingConstants.RIGHT);
    	DefaultListCellRenderer rendererHistory = (DefaultListCellRenderer) this.listHistory.getCellRenderer();
    	rendererHistory.setHorizontalAlignment(SwingConstants.RIGHT);

    	this.spStatistics.getViewport().setView(this.listStatistics);
    	this.spStatistics.setBounds(1, 1, 295, btnHeight * 3);
    	this.listStatistics.setToolTipText("统计");
    	this.topDisplay.add(this.spStatistics);

    	this.spHistory.getViewport().setView(this.listHistory);
        this.spHistory.setBounds(this.spStatistics.getX() + this.spStatistics.getWidth() + 1, 1, 295, btnHeight * 3);
        this.listHistory.setToolTipText("履历");
        this.topDisplay.add(this.spHistory);

    	this.expr.setBounds(1, this.spHistory.getY() + this.spHistory.getHeight(), (btnWidth + 1) * 11, btnHeight);
        this.expr.setHorizontalAlignment(JTextField.RIGHT);
        this.expr.setEditable(false);
        this.expr.setFont(new Font("Dialog", Font.PLAIN, 36));
        this.expr.setText("");
        this.expr.setToolTipText("表达式");

        this.textField.setBounds(1, this.expr.getY() + this.expr.getHeight(), 590, 90);
        this.textField.setHorizontalAlignment(JTextField.RIGHT);
        this.textField.setEditable( false );
        this.textField.setFont(new Font("Dialog", Font.PLAIN, 36));
        this.textField.getMaxWidth();
        LineBorder border = new LineBorder(Color.RED, 1, true);
        this.textField.setBorder(border);
        this.textField.setText("0");
        this.textField.setToolTipText("计算结果");

        /* A */
        mainPanel.add(option);

//        option.add(this.mmry);
        option.add(optionLeft);
        optionLeft.setBorder(BorderFactory.createLineBorder(Color.red));

//        this.mmry.setHorizontalAlignment(JTextField.RIGHT);
        r10.setSelected(true);
		group.add(r16);
		group.add(r10);
		group.add(r8);
		group.add(r2);
		
		this.r16.setToolTipText("计算结果");
		this.r10.setToolTipText("计算结果");
		this.r8.setToolTipText("计算结果");
		this.r2.setToolTipText("计算结果");

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

		this.deg.setToolTipText("计算结果");
		this.rad.setToolTipText("计算结果");
		this.grad.setToolTipText("计算结果");
		
		optionRight.add(deg);
		optionRight.add(rad);
		optionRight.add(grad);

		/* B */
        mainPanel.add(exOption);
        exOption.add(exOptLeft);
        exOptLeft.setLayout(null);
        exOptLeft.setPreferredSize(new Dimension((btnWidth + 1) * 4, (btnHeight + 1)));
        exOptLeft.add(inv);
        exOptLeft.add(hyp);

        inv.setBounds(1, 1, btnWidth * 2, btnHeight);
        hyp.setBounds(inv.getX() + inv.getWidth(), 1, btnWidth * 2, btnHeight);

        this.inv.setToolTipText("计算结果");
        this.hyp.setToolTipText("计算结果");

        exOption.add(exOptRight);
        exOptRight.setLayout(null);
        exOptRight.setPreferredSize(new Dimension((btnWidth + 1) * 4, (btnHeight + 1)));

        // 退格、清除、清除
        exOptRight.add( btnBackspace ).setForeground(Color.red);
        exOptRight.add( btnCe ).setForeground(Color.red);
        exOptRight.add( btnClear ).setForeground(Color.red);

        btnBackspace.setBounds(1, 1, btnWidth * 2, btnHeight);
        btnCe.setBounds(btnBackspace.getX() + btnBackspace.getWidth(), 1, btnWidth, btnHeight);
        btnClear.setBounds(btnCe.getX() + btnWidth, 1, btnWidth, btnHeight);
        btnClear.setActionCommand(CalcultorConts.CLEAR);

        this.btnBackspace.setToolTipText("Backspace：退格，删除当前输入数字中的最后一位。");
        this.btnCe.setToolTipText("CE：清除，清除显示的数字。");
        this.btnClear.setToolTipText("C：归零，清除当前的计算。");

        /* C */
        mainPanel.add(buttonPanel);

        buttonPanel.setLayout(null);
        buttonPanel.setPreferredSize(new Dimension((btnWidth + 1) * 11, (btnHeight + 1) * 5));
//        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.green));
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

        btnSta.setActionCommand(CalcultorConts.APP);
        btnAve.setEnabled(false);
        btnSum.setEnabled(false);
        btnS.setEnabled(false);
        btnDat.setEnabled(false);

        this.btnSta.setToolTipText("add:向统计列表里追加统计数据。");
        this.btnAve.setToolTipText("Ave：计算统计框中各数的平均值。若要计算平均方值，请使用Inv+Ave。");
        this.btnSum.setToolTipText("Sum：计算统计框中各数的和。若要计算平方和，请使用Inv+Sum。");
        this.btnS.setToolTipText("S：计算n-1个样本参数的标准偏差。若要计算n个样本参数为的标准偏差，请使用Inv+s。");
        this.btnDat.setToolTipText("保留。");

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

        this.btnFE.setToolTipText("F-E：打开或关闭科学计数法。大于10^32的数总是以指数形式表示。F-E只能用于十进制数字系统。");
        this.btnDMS.setToolTipText("Dms：将显示数字转换为度-分-秒格式（假设显示数字是用度数表示的）。若要将显示数字转换为用度数表示的格式（假设显示数字是用度-分-秒格式表示的），请使用Inv+dms。dms只能用于十进制数字系统。");
        this.btnSin.setToolTipText("Sin：计算显示数字的正弦。若要计算反正弦，请使用Inv+sin。若要计算双曲正弦，请使用Hyp+sin。若要计算反双曲正弦，请使用Inv+Hyp+sin。sin只能用于十进制数字系统。");
        this.btnCos.setToolTipText("Cos：cos计算显示数字的余弦。若要计算反余弦，请使用Inv+cos。若要计算双曲余弦，请使用Hyp+cos。若要计算反双曲余弦，请使用Inv+Hyp+cos。cos只能用于十进制数字系统。");
        this.btnTan.setToolTipText("Tan：计算显示数字的正切。若要计算反正切，请使用Inv+tan。若要计算双曲正切，请使用Hyp+tan。若要计算反双曲正切，请使用Inv+Hyp+tan。tan只能用于十进制数字系统。");

        this.btnLeft.setToolTipText("计算结果");
        this.btnExp.setToolTipText("Exp：允许输入用科学计数法表示的数字。指数限制为四位数。指数中只能使用十进制数（键0-9）。Exp只能用于十进制数字系统。");
        this.btnXY.setToolTipText("x^y：计算x的y次方。此按钮为二进制运算符。例如，若要计算2的4次方，请单击2x^y4=，结果为16。若要计算x的y次方根，请使用Inv+x^y。");
        this.btnX3.setToolTipText("x^3：计算显示数字的立方。若要计算立方根，请使用Inv+x^3。");
        this.btnX2.setToolTipText("x^2：计算显示数字的平方。若要计算平方根，请使用Inv+x^2。");
        
        this.btnRight.setToolTipText("计算结果");
        this.btnLn.setToolTipText("ln：计算自然对数（以e为底）。若要计算e的x次方（其中x是当前数字），请使用Inv+ln。");
        this.btnLog.setToolTipText("log：计算常用对数（以10为底）。若要计算10的x次方，请使用Inv+log。");
        this.btnN.setToolTipText("n!：计算显示数字的阶乘。");
        this.btnDivide1.setToolTipText("1/x：计算显示数字的倒数。");
        
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

        this.btnMC.setToolTipText("MC：清除存储器中的数值。");
        this.btnMR.setToolTipText("MR：将存于存储器中的数显示在计算器的显示框上。");
        this.btnMS.setToolTipText("MS：将显示框的数值存于存储器中。如果存储器中有数值将会显示M标志。");
        this.btnMP.setToolTipText("M+：将显示框的数与存储器中的数相加并进行存储。");
        this.btnMN.setToolTipText("M-：将显示框的数与存储器中的数相减并进行存储。");
        
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
            this.numButtons[i].setToolTipText("数字" + i);
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

        this.btnA.setToolTipText("ABCDEF：在数值中输入选中字母（只有在十六进制模式为开启状态时该按钮才可用）。");
        this.btnB.setToolTipText("ABCDEF：在数值中输入选中字母（只有在十六进制模式为开启状态时该按钮才可用）。");
        this.btnC.setToolTipText("ABCDEF：在数值中输入选中字母（只有在十六进制模式为开启状态时该按钮才可用）。");
        this.btnD.setToolTipText("ABCDEF：在数值中输入选中字母（只有在十六进制模式为开启状态时该按钮才可用）。");
        this.btnE.setToolTipText("ABCDEF：在数值中输入选中字母（只有在十六进制模式为开启状态时该按钮才可用）。");
        this.btnF.setToolTipText("ABCDEF：在数值中输入选中字母（只有在十六进制模式为开启状态时该按钮才可用）。");

        this.btnPosMinus.setToolTipText("+/-：改变当前显示数的符号。");
        this.btnDot.setToolTipText("计算结果");
        this.btnDivide.setToolTipText("计算结果");
        this.btnMultiply.setToolTipText("计算结果");
        this.btnSubtract.setToolTipText("计算结果");
        this.btnAdd.setToolTipText("计算结果");
        this.btnMod.setToolTipText("Mod：显示x/y的模数或余数。");
        this.btnEqual.setToolTipText("计算结果");
        this.btnAnd.setToolTipText("And：计算按位AND。逻辑运算符在执行任何按位运算时将截断数字的小数部分。");
        this.btnOr.setToolTipText("Or：计算按位OR。逻辑运算符在执行任何按位运算时将截断数字的小数部分。");
        this.btnNot.setToolTipText("Not：计算按位取反。逻辑运算符在执行任何按位运算时将截断数字的小数部分。");
        this.btnXor.setToolTipText("Xor：计算按位异OR。逻辑运算符在执行任何按位运算时将截断数字的小数部分。");
        this.btnLsh.setToolTipText("Lsh：左移。若要右移，请使用Inv+Lsh。在单击该按钮后，必须指定（以二进制形式）要将显示区中的数字左移或右移多少位，然后单击=。逻辑运算符在执行任何按位运算时将截断数字的小数部分。");
        this.btnInt.setToolTipText("Int：显示十进制数值的整数部分。若要显示十进制数值的小数部分，请使用Inv+Int。");
        //create the control
        CalcultorActionListener controler = new CalcultorActionListener( this );

        r16.addActionListener(controler);
        r10.addActionListener(controler);
        r8.addActionListener(controler);
        r2.addActionListener(controler);

        btnBackspace.addActionListener( controler );
        btnCe.addActionListener( controler );
        btnClear.addActionListener( controler );

        btnSta.addActionListener( controler );
        btnAve.addActionListener( controler );
        btnSum.addActionListener( controler );
        btnS.addActionListener( controler );
        btnDat.addActionListener( controler );

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
