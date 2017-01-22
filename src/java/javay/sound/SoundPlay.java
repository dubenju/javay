package javay.sound;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import javay.swing.SoundPanel;


public class SoundPlay implements Runnable {
    private boolean stopflag = false;
    private AudioInputStream ais;
    private AudioFormat af;
    private SourceDataLine line;
    private SoundPanel panel;

    public SoundPlay(SoundPanel panel) {
        this.panel = panel;
    }
    private float linearToDb(double volume) {
        return (float) (20 * Math.log10(volume));
    }
    public void setVolume(int newVal) {
        if (line == null) {
            return ;
        }
        if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) { System.out.println(FloatControl.Type.MASTER_GAIN + " Supported"); }
        if (line.isControlSupported(FloatControl.Type.AUX_SEND)) { System.out.println(FloatControl.Type.AUX_SEND + " Supported"); }
        if (line.isControlSupported(FloatControl.Type.AUX_RETURN)) { System.out.println(FloatControl.Type.AUX_RETURN + " Supported"); }
        if (line.isControlSupported(FloatControl.Type.REVERB_SEND)) { System.out.println(FloatControl.Type.REVERB_SEND + " Supported"); }
        if (line.isControlSupported(FloatControl.Type.REVERB_RETURN)) { System.out.println(FloatControl.Type.REVERB_RETURN + " Supported"); }
        if (line.isControlSupported(FloatControl.Type.VOLUME)) { System.out.println(FloatControl.Type.VOLUME + " Supported"); }
        if (line.isControlSupported(FloatControl.Type.PAN)) { System.out.println(FloatControl.Type.PAN + " Supported"); }
        if (line.isControlSupported(FloatControl.Type.BALANCE)) { System.out.println(FloatControl.Type.BALANCE + " Supported"); }
        if (line.isControlSupported(FloatControl.Type.SAMPLE_RATE)) { System.out.println(FloatControl.Type.SAMPLE_RATE + " Supported"); }

        if (line.isControlSupported(FloatControl.Type.VOLUME)) {
            FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
            volumeControl.setValue((newVal * volumeControl.getMaximum() / 100.0f));
            // linearVolume = true;
        } else if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volumeControl = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
            double max = Math.pow(10.0, volumeControl.getMaximum()/20.0);
            double min = Math.pow(10.0, volumeControl.getMinimum()/20.0);
            float volume = newVal / 100.0f;
            double newValue = (max - min) * (volume * volume) + min;
            newValue = 20 * Math.log(newValue) / Math.log(10);
            float value = (float) newValue;
            volumeControl.setValue(value);

//            FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
//            System.out.println("MASTER_GAIN:" + newVal + "(" + volumeControl.getMinimum() + "," + volumeControl.getMaximum() + ")");
//            float range = volumeControl.getMaximum() - volumeControl.getMinimum();
//            range = range * newVal / 100.f;
//            float value = volumeControl.getMinimum() + range;

            System.out.println("MASTER_GAIN:" + newVal + "%(" + volumeControl.getMinimum() + "," + volumeControl.getMaximum() + "):" + value);
//            volumeControl.setValue(value);


            //linearVolume = false;
        }
        // FloatControl volctrl=(FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
        //volctrl.setValue(newVal);// newVal - the value of volume slider

    }
    @Override
    public void run() {
        try {
//            line.open();
            line.open(af);//或者line.open();format参数可有可无
        } catch (LineUnavailableException e1) {
            e1.printStackTrace();
        }
        line.start();

        int nBytesRead = 0;
        byte[] buffer = new byte[4096];
        while (this.stopflag == false) {
            try {
                nBytesRead = ais.read(buffer, 0, buffer.length);
            } catch (IOException e) {
                e.printStackTrace();
                break ;
            }
            if (nBytesRead <= 0) {
                break;
            }
            line.write(buffer, 0, nBytesRead);
        }
        line.drain();
        line.close();
        try {
            ais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.panel.btnReco.setEnabled(true);
        this.panel.btnPrev.setEnabled(false);
        this.panel.btnPlay.setEnabled(true);
        this.panel.btnPause.setEnabled(false);
        this.panel.btnStop.setEnabled(false);
        this.panel.btnNext.setEnabled(false);
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
     * @return ais
     */
    public AudioInputStream getAis() {
        return ais;
    }

    /**
     * @param ais セットする ais
     */
    public void setAis(AudioInputStream ais) {
        this.ais = ais;
    }

    /**
     * @return line
     */
    public SourceDataLine getLine() {
        return line;
    }

    /**
     * @param line セットする line
     */
    public void setLine(SourceDataLine line) {
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
