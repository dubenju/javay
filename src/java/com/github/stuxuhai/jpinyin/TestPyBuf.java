package com.github.stuxuhai.jpinyin;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.SampleBuffer;

public class TestPyBuf {
    private Decoder decoder;
    private SampleBuffer buffer;
    public TestPyBuf(Decoder decoder, SampleBuffer buffer) {
        setDecoder(decoder);
        setBuffer(buffer);
    }
    /**
     * @return decoder
     */
    public Decoder getDecoder() {
        return decoder;
    }
    /**
     * @param decoder セットする decoder
     */
    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }
    /**
     * @return buffer
     */
    public SampleBuffer getBuffer() {
        return buffer;
    }
    /**
     * @param buffer セットする buffer
     */
    public void setBuffer(SampleBuffer buffer) {
        this.buffer = buffer;
    }
}
