package javay.awt.event;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javay.swing.CalcultorPanel;

public class CalcultorKeyAdapter extends KeyAdapter {
     private CalcultorPanel panel;
     public CalcultorKeyAdapter(CalcultorPanel panel) {
        this.panel = panel;
        System.out.println("init-------------------");
    }

    /**
     * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        System.out.println("KeyCode=" + k);
        switch(k){
        case KeyEvent.VK_0:
        case KeyEvent.VK_1:
        case KeyEvent.VK_2:
        case KeyEvent.VK_3:
        case KeyEvent.VK_4:
        case KeyEvent.VK_5:
        case KeyEvent.VK_6:
        case KeyEvent.VK_7:
        case KeyEvent.VK_8:
        case KeyEvent.VK_9: {
            String str = this.panel.textField.getText();
            str = str + "" + (k - '0');
            this.panel.textField.setText(str);
            break ;
        }
        case KeyEvent.VK_A:{
            break ;
        }
        case KeyEvent.VK_B:{
            break ;
        }
        case KeyEvent.VK_C:{
            break ;
        }
        case KeyEvent.VK_D:{
            break ;
        }
        case KeyEvent.VK_E:{
            break ;
        }
        case KeyEvent.VK_F:{
            break ;
        }
        case KeyEvent.VK_PERIOD:{
            break ;
        }
        case KeyEvent.VK_PLUS:{
            break ;
        }
        case KeyEvent.VK_SUBTRACT:{
            break ;
        }
        case KeyEvent.VK_MULTIPLY:{
            break ;
        }
        case KeyEvent.VK_DIVIDE:{
            break ;
        }
        case KeyEvent.VK_EQUALS:{
            break ;
        }
        case KeyEvent.VK_ENTER:{
            break ;
        }
        }
    }

}
/*
ボタン    キー
%    %
(    (
)    )
*    *
+    +
+/-    F9
-    -
.    . または、
/    /
0–9    0–9
1/x    R
=    Enter
A–F    A–F
And    &
Ave    Ctrl + A
Back    BackSpace
2 進    F8
Byte    F4
C    Esc
CE    Del
cos    O
Dat    Ins
10 進    F6
Deg    F2
dms    M
Dword    F2
Exp    X
F-E    V
Grad    F4
16 進    F5
Hyp    H
Int    ;
Inv    I
ln    N
log    L
Lsh    <
M+    Ctrl + P
MC    Ctrl + L
Mod    %
MR    Ctrl + R
MS    Ctrl + M
n!    !
Not    ~
8 進    F7
Or    縦棒 (|)
PI    P
Qword    F12
Rad    F3
s    Ctrl + D
sin    S
sqrt    @
Sta    Ctrl + S
Sum    Ctrl + T
tan    T
Word    F3
Xor    ^
x^2    @
x^3    #
x^y    Y
*/