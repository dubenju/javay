package javay.sound;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class SoundRecord implements Runnable {
    private boolean stopflag = false;
    private ByteArrayOutputStream baos = null;
    private AudioFormat af;
    private TargetDataLine  line;

    @Override
    public void run() {
        //打开具有指定格式的行，这样可使行获得所有所需的系统资源并变得可操作。
        try {
            line.open(af);
        } catch (LineUnavailableException e1) {
            e1.printStackTrace();
        }
        //允许某一数据行执行数据 I/O
        line.start();

        byte bts[] = new byte[4096];
        baos = new ByteArrayOutputStream();
        try {
            while(stopflag != true) {
                //当停止录音没按下时，该线程一直执行
                //从数据行的输入缓冲区读取音频数据。
                //要读取bts.length长度的字节,cnt 是实际读取的字节数
                int cnt = line.read(bts, 0, bts.length);
                // System.out.println("Read mic:" + cnt);
                if(cnt > 0) {
                    baos.write(bts, 0, cnt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                //关闭打开的字节数组流
                if(baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                line.drain();
                line.close();
            }
        }

    }
    /**
     * @return stopflag
     */
    public boolean isStopflag() {
        return stopflag;
    }
    /**
     * @param stopflag セットする stopflag
     */
    public void setStopflag(boolean stopflag) {
        this.stopflag = stopflag;
    }
    /**
     * @return baos
     */
    public ByteArrayOutputStream getBaos() {
        return baos;
    }
    /**
     * @param baos セットする baos
     */
    public void setBaos(ByteArrayOutputStream baos) {
        this.baos = baos;
    }
    /**
     * @return line
     */
    public TargetDataLine getLine() {
        return line;
    }
    /**
     * @param line セットする line
     */
    public void setLine(TargetDataLine line) {
        this.line = line;
    }
    /**
     * @return af
     */
    public AudioFormat getAf() {
        return af;
    }
    /**
     * @param af セットする af
     */
    public void setAf(AudioFormat af) {
        this.af = af;
    }

}
