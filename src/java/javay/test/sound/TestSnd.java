package javay.test.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class TestSnd {

    public static void main(String[] args) throws Exception {
	float sampleRate = 44100f;
	sampleRate = 48000f;
	sampleRate = 96000f;
	sampleRate = 192000f;
	int sampleSizeInBits = 16;
	// sampleSizeInBits = 24; // ng
	int channels = 2;
	boolean signed = true;
	if (sampleSizeInBits == 16) {
	    signed = true;
	}
	boolean bigEndian = false;
	AudioFormat af = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);

	DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
	TargetDataLine tdLine = (TargetDataLine)(AudioSystem.getLine(info));

	tdLine.open(af);
	tdLine.drain();
	tdLine.close();

    }

}
