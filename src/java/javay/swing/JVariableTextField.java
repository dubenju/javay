package javay.swing;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class JVariableTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int maxWidth = 1;

	/**
	 * 
	 */
	public JVariableTextField() {
		maxWidth = this.getColumns() * this.getFont().getSize();
	}

	/**
	 * @param text
	 * @param columns
	 */
	public JVariableTextField(String text, int columns) {
		super(text, columns);
		maxWidth = this.getColumns() * this.getFont().getSize();
	}

	/**
	 * @param text
	 */
	public JVariableTextField(String text) {
		super(text);
		maxWidth = this.getColumns() * this.getFont().getSize();
	}

	/**
	 * @param doc
	 * @param text
	 * @param columns
	 */
	public JVariableTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
		maxWidth = this.getColumns() * this.getFont().getSize();
	}

	/**
	 * @param columns
	 */
	public JVariableTextField(int columns) {
		super(columns);
		maxWidth = this.getColumns() * this.getFont().getSize();
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
		long width = length * fontsize;
		System.out.println("t.length=" + t.length() + ",columns=" + columns + ",fontsize=" + fontsize + ",maxWidth=" + maxWidth);
		//if (width > maxWidth) {
			fontsize = maxWidth / length;
			if (fontsize == 0 ) {
				fontsize = 1;
			}
			if (fontsize > 36) {
				fontsize = 36;
			}
			Font newFont = new Font(font.getName(), font.getStyle(), fontsize);
			this.setFont(newFont);
		//}
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

}
