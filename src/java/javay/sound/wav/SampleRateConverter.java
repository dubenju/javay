package javay.sound.wav;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SampleRateConverter {
    private static boolean      DEBUG = true;


    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        if (args.length == 1) {
            if (args[0].equals("-h")) {
                printUsageAndExit();
            } else {
                printUsageAndExit();
            }
        } else if (args.length != 3) {
            printUsageAndExit();
        }

        float   fTargetSampleRate = Float.parseFloat(args[0]);
        if (DEBUG) { out("target sample rate: " + fTargetSampleRate); }
        File    sourceFile = new File(args[1]);
        File    targetFile = new File(args[2]);

        /* We try to use the same audio file type for the target
           file as the source file. So we first have to find
           out about the source file's properties.
        */
        AudioFileFormat     sourceFileFormat = AudioSystem.getAudioFileFormat(sourceFile);
        AudioFileFormat.Type    targetFileType = sourceFileFormat.getType();

        /* Here, we are reading the source file.
         */
        AudioInputStream    sourceStream = null;
        sourceStream = AudioSystem.getAudioInputStream(sourceFile);
        if (sourceStream == null) {
            out("cannot open source audio file: " + sourceFile);
            System.exit(1);
        }
        AudioFormat sourceFormat = sourceStream.getFormat();
        if (DEBUG)  { out("source format: " + sourceFormat); }

        /* Currently, the only known and working sample rate
           converter for Java Sound requires that the encoding
           of the source stream is PCM (signed or unsigned).
           So as a measure of convenience, we check if this
           holds here.
        */
        AudioFormat.Encoding    encoding = sourceFormat.getEncoding();
//        if (! AudioCommon.isPcm(encoding)) {
//            out("encoding of source audio data is not PCM; conversion not possible");
//            System.exit(1);
//        }

        /* Since we now know that we are dealing with PCM, we know
           that the frame rate is the same as the sample rate.
        */
        float       fTargetFrameRate = fTargetSampleRate;

        /* Here, we are constructing the desired format of the
           audio data (as the result of the conversion should be).
           We take over all values besides the sample/frame rate.
        */

        AudioFormat targetFormat = new AudioFormat(
            sourceFormat.getEncoding(),
            fTargetSampleRate,
            sourceFormat.getSampleSizeInBits(),
            sourceFormat.getChannels(),
            sourceFormat.getFrameSize(),
            fTargetFrameRate,
            sourceFormat.isBigEndian());

        if (DEBUG)  { out("desired target format: " + targetFormat); }

        /* Now, the conversion takes place.
         */
        AudioInputStream    targetStream = AudioSystem.getAudioInputStream(targetFormat, sourceStream);
        if (DEBUG) { out("targetStream: " + targetStream); }

        /* And finally, we are trying to write the converted audio
           data to a new file.
        */
        int nWrittenBytes = 0;
        nWrittenBytes = AudioSystem.write(targetStream, targetFileType, targetFile);
        if (DEBUG) { out("Written bytes: " + nWrittenBytes); }
    }



    private static void printUsageAndExit() {
        out("SampleRateConverter: usage:");
        out("\tjava SampleRateConverter -h");
        out("\tjava SampleRateConverter <targetsamplerate> <sourcefile> <targetfile>");
        System.exit(1);
    }



    private static void out(String strMessage) {
        System.out.println(strMessage);
    }
}
