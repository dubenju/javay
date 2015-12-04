package javay.game.othello;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OX extends Component implements MouseListener, MouseMotionListener, Runnable {
    private int[] board; // 盤面狀況,表達有邊框的10*10盤面
    private int turn, diskdiff; // 現在哪方可下, 與敵方的子數差異
    private OX parent; // 由哪一個盤面變化而來
    private double val = -1000000; // 优劣判断值
    private int hashval; // for hashtable
    private int[] legals; // 儲存此盤面可以下的著手

    public static final int EMPTY = 0x00; // 空
    public static final int BLACK = 0x01; // 黑子
    public static final int WHITE = 0x02; // 白子
    public static final int STONE = 0x03; // 上面兩個 or
    public static final int BOUND = 0x04; // 边界
    public static final int ADEMP = 0x08; // 是否鄰接子的空點

    private static final Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR); // 箭頭游標
    private static final Cursor hintCursor = new Cursor(Cursor.HAND_CURSOR); // 手形游標
    private static final Cursor thinkCursor = new Cursor(Cursor.WAIT_CURSOR); // 漏斗游標
    private static Dimension mySize = new Dimension(600,400); // 固定畫面的大小為寬600,高400

    private static final byte[] directions = {1, -1, 10, -10, 9, -9, 11, -11}; // 8个方向
    private static final int HASHSIZE = 63999979; // 小於64M的最大質數
    public  static final int HUMAN = 0, COMPUTER = 1;

    private static JFrame top; // 包含此元件的最上層Frame
    private static Thread thinking; // 計算中的Thread
    private  static int whoPlayBlack, whoPlayWhite;

    private static int newboard[] = { // 游戏界面的初始布局
        BOUND,BOUND,BOUND,BOUND,BOUND,BOUND,BOUND,BOUND,BOUND,BOUND,
        BOUND,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,BOUND,
        BOUND,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,BOUND,
        BOUND,EMPTY,EMPTY,ADEMP,ADEMP,ADEMP,ADEMP,EMPTY,EMPTY,BOUND,
        BOUND,EMPTY,EMPTY,ADEMP,WHITE,BLACK,ADEMP,EMPTY,EMPTY,BOUND,
        BOUND,EMPTY,EMPTY,ADEMP,BLACK,WHITE,ADEMP,EMPTY,EMPTY,BOUND,
        BOUND,EMPTY,EMPTY,ADEMP,ADEMP,ADEMP,ADEMP,EMPTY,EMPTY,BOUND,
        BOUND,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,BOUND,
        BOUND,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,BOUND,
        BOUND,BOUND,BOUND,BOUND,BOUND,BOUND,BOUND,BOUND,BOUND,BOUND};

    /**
     *
     * @param p JFrame
     */
    public OX(JFrame p) {
        addMouseListener(this);
        addMouseMotionListener(this);
        top = p;
        board = new int[100];
        System.arraycopy(newboard, 0, board, 0, 100);
        turn = BLACK;
        legals = new int[] {34, 43, 56, 65};
        System.out.println("OXbyJFrame");
    }

    /**
     * 复制
     * @param p
     */
    public OX(OX p) {
        board = new int[100];
        System.arraycopy(p.board, 0, board, 0, 100);
        turn = p.turn;
        diskdiff = p.diskdiff;
        val = -1000000;
        System.out.println("OXbyOX");
    }

    public void setBlackPlayer(int who) {
        if (whoPlayBlack == who) {
            return;
        }
        if (whoPlayBlack == 0 && thinking == null && (hasLegal(turn) || hasLegal(turn^STONE))) {
        	thinking = new Thread(this);
        	thinking.start();
        }
        whoPlayBlack = who;
    }

    public void setWhitePlayer(int who) {
        if (whoPlayWhite == who) {
            return;
        }
        if (whoPlayWhite == 0 && thinking == null && (hasLegal(turn) || hasLegal(turn^STONE))) {
        	thinking = new Thread(this);
        	thinking.start();
        }
        whoPlayWhite = who;
    }

    /**
     * 是否合法
     * @param pos
     * @return
     */
    boolean isLegal(int pos) {
        return isLegal(turn, pos);
    }

    // 檢查side這個顏色,能否下在pos
    boolean isLegal(int side, int pos) {
        int opp = side^STONE;
        for (int i = 0, scan; i < 8; i ++) {
            scan = pos + directions[i];
            if (board[scan] == opp) {
                for (scan += directions[i]; board[scan] == opp; scan += directions[i]);
                if ((board[scan] & side) != 0) { // 可夾住對方
                    return true;
                }
            }
        }
        return false;
    }

    // 檢查side是否有合法的著手可下
    boolean hasLegal(int side) {
        for (int i = 11; i < 89; i ++) {
            if ((board[i]==ADEMP) && isLegal(side, i)) {
                return true;
            }
        }
        return false;
    }

    // 下在pos,並改變盤面結構. 若pos為0, 表示此著手為pass
    boolean addMove(int pos) {
        int opp = turn^STONE;
        if (pos != 0) { // 0 表示pass
            int legal = diskdiff;
            for (int i = 0, scan; i < 8; i ++) {
                scan = pos+directions[i];
                if (board[scan] == opp) { // 此方向緊鄰著敵方的子
                    // 跳過連續的敵方子
                    for (scan += directions[i]; board[scan] == opp; scan+=directions[i]);
                    if (board[scan] == turn) { // 可夾住對方
                        // 將所有敵方子變成我方子
                        for (int c = pos+directions[i]; c!=scan ;board[c]=turn, c+=directions[i], diskdiff+=2);
                    }
                }
            }
            if (diskdiff==legal) { // 如果都沒有吃到
                return false;
            }
            diskdiff ++;
            board[pos] = turn;
            for (int i = 0; i < 8; i ++) { // 設定此點旁的空點為ADEMP
                if (board[pos+directions[i]] == EMPTY) {
                    board[pos+directions[i]] = ADEMP;
                }
            }
        }
        turn = opp; // 換對方下了
        diskdiff = -diskdiff;
        hashval = (hashval * 64 + (pos - 11)) % HASHSIZE;
        return true;
    }

    // Thread的進入點
    public void run() {
        setCursor(thinkCursor);
        for (;;) { // 當敵方需pass時,我方一直下
            if (turn == BLACK && whoPlayBlack == HUMAN) { // 先檢查是否改由人下
                break;
            }
            if (turn == WHITE && whoPlayWhite == HUMAN) { // 先檢查是否改由人下
                break;
            }
            addMove(best());
            repaint(); // ask winder manager to call paint() in another thread
            if (turn == BLACK && whoPlayBlack==HUMAN && hasLegal(turn)) { // 人可以下了
                break;
            }
            if (turn == WHITE && whoPlayWhite==HUMAN && hasLegal(turn)) { // 人可以下了
                break;
            }
            if (!hasLegal(turn) && !hasLegal(turn^STONE)) { // 對手和自己都不能下了
            	JOptionPane.showMessageDialog(top, "游戏结束了", "信息", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
            if (!hasLegal(turn)) {
                addMove(0);
            }
        }
        setCursor(normalCursor);
        thinking = null;
    }

    // The following 2 methods implement the MouseMotionListener interface
    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        if (thinking != null) return;
        int row = e.getY()/40;
        int col = e.getX()/40;
        if (row >= 8 || col >= 8) {
            setCursor(normalCursor);
            return; // 超過邊界
        }
        int pos = row*10 + col + 11;
        if (board[pos]==ADEMP && isLegal(turn, pos)) {
            setCursor(hintCursor);
        } else {
            setCursor(normalCursor);
        }
    }

    // The following 5 methods implement the MouseListener interface
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {
        int row = e.getY()/40;
        int col = e.getX()/40;
        if (row >= 8 || col >= 8) return; // 超過邊界
        if (thinking != null) return; // 電腦思考中
        int pos = row*10+col+11;
        if (board[pos] == ADEMP && addMove(pos)) { // 此位置可以下
            repaint();
            if (hasLegal(turn)) {
                if ((turn==WHITE && whoPlayWhite==COMPUTER) || (turn==BLACK && whoPlayBlack==COMPUTER)) { // let computer play
                    (thinking = new Thread(this)).start();
                }
            } else {
                if (!hasLegal(turn^STONE)) { // 雙方都不能下
                	JOptionPane.showMessageDialog(top, "游戏结束了", "信息", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                addMove(0); // 對方不能下,force pass
            }
        }
    }

    // 棋力強弱關鍵的求值函數
    private void eval() {
        val = diskdiff;
    }

    private void alphaBeta(int level) {
        if (legals == null) {
            findLegals();
        }
        for (int i=0; i<legals.length; i++) {
            OX tmp = new OX(this);
            tmp.addMove(legals[i]);
            if (level < 1) {
                tmp.eval();
            } else {
                tmp.alphaBeta(level - 1);
            }
            // alphaBeta cut
            if (val < -tmp.val) {
                val = -tmp.val;
                for (OX p = parent; p != null;) {
                    if (val >= -p.val) { // 對手不會選擇這條路的
                        return;
                    }
                    // 往上跳兩層
                    p = p.parent;
                    if (p != null) p = p.parent;
                }
            }
        }
    }

    private void findLegals() {
        int count = 0;
        int[] tmp = new int[60];
        for (int i = 11; i < 89; i ++) {
            if (board[i]==ADEMP && isLegal(turn, i)) {
                tmp[count++] = i;
            }
        }
        legals = new int[count];
        System.arraycopy(tmp, 0, legals, 0, count);
    }

    private int best() {
        int bestMove = 0;
        findLegals();
        val = -100000000;
        for (int i = 0; i < legals.length; i ++) {
            OX tmp = new OX(this);
            tmp.addMove(legals[i]);
            tmp.alphaBeta(3);
            if (-tmp.val > val) {
                bestMove = legals[i];
                val = -tmp.val;
            }
        }
        return bestMove;
    }

    /**
     * override paint() defined in Component
     */
    public void paint(Graphics g) {
        int black = 0, white = 0;
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setBackground(Color.GRAY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // GradientPaint gp = new GradientPaint(0, 0, new Color(0x006400), 0, 320, new Color(0x00FF00));
        RadialGradientPaint gp = new RadialGradientPaint(
        		new Point2D.Double(
        		160f, 160f), 160f,
        		new float[] {0.0f, 1.0f},
        		new Color[] {new Color(0x006400), new Color(0x00FF00)});
        Paint oldPaint = g2d.getPaint();
        g2d.setPaint(gp);

        // g2d.setColor(new Color(0, 255, 0));
        g2d.fillRect(0, 0, 320, 320);
        g2d.setPaint(oldPaint);

        g2d.setColor(Color.BLACK);
        for (int i = 0; i <= 8; i ++) {  // draw grids
        	g2d.drawLine(0, i * 40, 320, i * 40);
        	g2d.drawLine(i * 40, 0, i * 40, 320);
        }
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j ++) {
                int pos = i * 10 + j + 11;
                if ((board[pos] & BLACK) != 0) {
                	g2d.setColor(Color.BLACK);
                	g2d.fillOval(j * 40, i * 40, 40, 40);
                    black ++;
                } else if ((board[pos] & WHITE) != 0) {
                    // g.drawOval(j * 40, i * 40, 40, 40);
                	g2d.setColor(Color.WHITE);
                	g2d.fillOval(j * 40, i * 40, 40, 40);
                    white ++;
                }
            }
        }
        g2d.drawString("BLACK:" + black, 400, 100);
        g2d.drawString("WHITE:" + white, 400, 150);
        g2d.dispose();
    }

    /**
     * Start
     */
    public void newGame() {
        System.arraycopy(newboard, 0, board, 0, 100);
        turn = BLACK;
        hashval = diskdiff = 0;
        if (thinking != null) {
            try {
                thinking.join();
            } catch(Exception epp) {
            }
        }
        if (whoPlayBlack == COMPUTER) {
        	thinking = new Thread(this);
        	thinking.start();
        }
        repaint(); // Component
    }

    // override getPreferredSize defined in java.lang.Component,
    // so that the Component has proper size on screen
    public Dimension getPreferredSize() {
        return mySize;
    }

    // override hashCode() in java.lang.Object
    public int hashCode() {
        return hashval;
    }

    public boolean equals(Object o) {
    	boolean bRes = false;
        if (o instanceof OX) {
	        OX t = (OX) o;
	        for (int i = 11; i < 89; i ++) {
	            if (board[i] != t.board[i]) {
	                return false;
	            }
	        }
        }
        return bRes;
    }
}
