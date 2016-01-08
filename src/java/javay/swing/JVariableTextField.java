package javay.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class JVariableTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int maxWidth = 1;
	private String numberSystem = CalcultorConts.DECIMAL;

	/**
	 * 
	 */
	public JVariableTextField() {
		maxWidth = this.getColumns() * this.getFont().getSize();
		setOpaque(false);
	}

	/**
	 * @param text
	 * @param columns
	 */
	public JVariableTextField(String text, int columns) {
		super(text, columns);
		maxWidth = this.getColumns() * this.getFont().getSize();
		setOpaque(false);
	}

	/**
	 * @param text
	 */
	public JVariableTextField(String text) {
		super(text);
		maxWidth = this.getColumns() * this.getFont().getSize();
		setOpaque(false);
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
	}

	/**
	 * @param columns
	 */
	public JVariableTextField(int columns) {
		super(columns);
		maxWidth = this.getColumns() * this.getFont().getSize();
		setOpaque(false);
	}

	/**
	 * @see javax.swing.text.JTextComponent#setText(java.lang.String)
	 */
	@Override
	public void setText(String t) {
		int columns = this.getColumns();
		Font font = this.getFont();
		int fontsize = font.getSize();
		int length = t.length();
//		long width = length * fontsize;
//		System.out.println("t.length=" + t.length() + ",columns=" + columns + ",fontsize=" + fontsize + ",maxWidth=" + maxWidth);
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
		fontSize = (int) (fontSize *0.66);
		int ng = font.getNumGlyphs();
//		System.out.println("ng=" + ng);
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
		//g.fillRect(0, 0, getWidth(), getHeight());  
		g2.setColor(color);
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

}
