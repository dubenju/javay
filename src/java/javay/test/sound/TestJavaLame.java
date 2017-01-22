package javay.test.sound;

import java.nio.ByteBuffer;

import net.sourceforge.lame.lowlevel.LameDecoder;

public class TestJavaLame {

    public static void main(String[] args) {
	LameDecoder ld = new LameDecoder("./out/01-Get Your Head Straight_noId3.mp3");
	ByteBuffer sampleBuffer = new ByteBuffer();
	ld.decode(sampleBuffer);
	ld.close();
    }

}
