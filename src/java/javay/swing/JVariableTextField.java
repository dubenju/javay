package javay.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JVariableTextField extends JTextField implements MouseListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(JVariableTextField.class);
    
    private int maxWidth = 1;
    private String numberSystem = CalcultorConts.DECIMAL;
    private String mem = null;
    private int display = 0; // normal,1:ScientificNotation

    private JPopupMenu pop = null; // 弹出菜单
    private JMenuItem copy = null, paste = null, cut = null; // 三个功能菜单
    /**
     * 
     */
    public JVariableTextField() {
        maxWidth = this.getColumns() * this.getFont().getSize();
        setOpaque(false);
        this.mem = null;
        init();
    }

    /**
     * @param text
     * @param columns
     */
    public JVariableTextField(String text, int columns) {
        super(text, columns);
        maxWidth = this.getColumns() * this.getFont().getSize();
        setOpaque(false);
        this.mem = null;
        init();
    }

    /**
     * @param text
     */
    public JVariableTextField(String text) {
        super(text);
        maxWidth = this.getColumns() * this.getFont().getSize();
        setOpaque(false);
        this.mem = null;
        init();
    }

    /**
     * @param doc
     * @param text
     * @param columns
     */
    public JVariableTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        maxWidth = this.getColumns() * this.getFont().getSize();
        setOpaque(false);
        this.mem = null;
        init();
    }

    /**
     * @param columns
     */
    public JVariableTextField(int columns) {
        super(columns);
        maxWidth = this.getColumns() * this.getFont().getSize();
        setOpaque(false);
        this.mem = null;
        init();
    }

    private void init() {
        this.addMouseListener(this);
        pop = new JPopupMenu();  
        pop.add(copy = new JMenuItem("复制"));  
        pop.add(paste = new JMenuItem("粘贴"));  
        pop.add(cut = new JMenuItem("剪切"));  
        copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));  
        paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));  
        cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));  
        copy.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                action(e);  
            }  
        });  
        paste.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                action(e);  
            }  
        });  
        cut.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                action(e);  
            }  
        });  
        this.add(pop);
    }
     /** 
     * 菜单动作 
     *  
     * @param e 
     */  
    public void action(ActionEvent e) {  
        String str = e.getActionCommand();  
        if (str.equals(copy.getText())) { // 复制  
            this.copy();  
        } else if (str.equals(paste.getText())) { // 粘贴  
            this.paste();  
        } else if (str.equals(cut.getText())) { // 剪切  
            this.cut();  
        }  
    } 

    /**
     * @see javax.swing.text.JTextComponent#setText(java.lang.String)
     */
    @Override
    public void setText(String t) {
        Font font = this.getFont();
        int fontsize = font.getSize();
        int length = t.length();
        if (length > 0) {
            fontsize = maxWidth / length;
            if (fontsize == 0 ) {
                fontsize = 1;
            }
            if (fontsize > 36) {
                fontsize = 36;
            }
            Font newFont = new Font(font.getName(), font.getStyle(), fontsize);
            this.setFont(newFont);
        }
        super.setText(t);
    }

    /**
     * @see javax.swing.JTextField#setColumns(int)
     */
    @Override
    public void setColumns(int columns) {
        super.setColumns(columns);
        Font font = this.getFont();
        maxWidth = columns * font.getSize();
    }

    /**
     * @see javax.swing.JTextField#setFont(java.awt.Font)
     */
    @Override
    public void setFont(Font f) {
        super.setFont(f);
    }

    /**
     * @return the maxWidth
     */
    public int getMaxWidth() {
        maxWidth = this.getColumns() * this.getFont().getSize();
        return maxWidth;
    }

    /**
     * @param maxWidth the maxWidth to set
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /*＊
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Color color = g2.getColor();
        g2.setColor(Color.BLUE);
        g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);
        Font font = this.getFont();
        int fontSize = font.getSize();
        fontSize = (int) (fontSize * 0.66);
        int width = this.getWidth();
        for(int i = width, cnt = 0; i >= 0; i -= fontSize, cnt ++) {
            if (cnt % 10 == 0) {
                g2.drawLine(i, getHeight() - 15, i, getHeight() - 2);
            } else if (cnt % 5 == 0) {
                g2.drawLine(i, getHeight() - 10, i, getHeight() - 2);
            } else {
                g2.drawLine(i, getHeight() - 5, i, getHeight() - 2);
            }
        }
        g2.setColor(color);

        if (this.mem != null && this.mem.length() > 0) {
            Color color2 = g2.getColor();
            g2.setColor(Color.GRAY);
            g2.drawString(this.mem, 30, 60);
            g2.drawString("M", 1, 60);
            g2.setColor(color2);
        }
        if (this.display == 1) {
            Color color2 = g2.getColor();
            g2.setColor(Color.GRAY);
            g2.drawString("E", 1, 30);
            g2.setColor(color2);
        }
        super.paintComponent(g);
    }

    /**
     * @return the numberSystem
     */
    public String getNumberSystem() {
        return numberSystem;
    }

    /**
     * @param numberSystem the numberSystem to set
     */
    public void setNumberSystem(String numberSystem) {
        this.numberSystem = numberSystem;
    }

    public String getMemory() {
        return this.mem;
    }
    public void setMemory(String m) {
        this.mem = m;
    }

    /**
     * @return the display
     */
    public int getDisplay() {
        return display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(int display) {
        this.display = display;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {  
            copy.setEnabled(isCanCopy());  
            paste.setEnabled(isClipboardString());  
            cut.setEnabled(isCanCopy());  
            pop.show(this, e.getX(), e.getY());  
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
      
    /** 
     * 剪切板中是否有文本数据可供粘贴 
     *  
     * @return true为有文本数据 
     */  
    public boolean isClipboardString() {  
        boolean b = false;  
        Clipboard clipboard = this.getToolkit().getSystemClipboard();  
        Transferable content = clipboard.getContents(this);  
        try {  
            if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {  
                b = true;  
            }
        } catch (Exception e) {  
        }
        return b;  
    }  
  
    /** 
     * 文本组件中是否具备复制的条件 
     *  
     * @return true为具备 
     */  
    public boolean isCanCopy() {  
        boolean b = false;  
        int start = this.getSelectionStart();  
        int end = this.getSelectionEnd();  
        if (start != end) {
            b = true;
        }
        return b;  
    }  
}
