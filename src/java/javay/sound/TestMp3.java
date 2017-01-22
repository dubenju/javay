package javay.sound;

import java.io.FileInputStream;
import java.io.InputStream;

import javay.util.UBytes;

/**
 * TAG_V2(ID3V2)、
 * Frame、
 * TAG_V1(ID3V1)
 *
 * MP3文件由帧（frame）构成的，每个帧包括帧头（FrameHeader）和帧数据（FrameData）
 * 帧头记录了mp3的位率，采样率，版本等信息，每个帧之间相互独立。
 *
 * 位率bitrate决定
 * CBR位率相等(Constant BitRate)
 * VBR位率不等(Variable BitRate)
 * Xing
 */
public class TestMp3 {

    public static void main(String[] args) throws Exception {
        Id3v23Header id3header = null;
        Id3v23Frame id3frame = null;
        Mp3FrameHeader mp3header = null;
        Mp3FrameSideInfo mp3side = null;

        int bytesum = 1;
        int byteread = 0;
        String in = "./classes/javay/sound/test.mp3";
//        in = "./out/01-Get Your Head Straight_noId3.mp3";
         in = "./out/01/f_00018.mp3";
        // in = "c:/prj/mp3/zxmzf.mp3";
        in = "c:/prj/mp3/001.mp3";


        test(in);



        InputStream inStream = new FileInputStream(in);

        // ID3Header
        byte[] buffer = new byte[10];
        /*if ( (byteread = inStream.read(buffer)) == -1) {
            System.out.println("read error ID3Header");
            inStream.close();
            System.out.println("read=" + bytesum);
            return ;
        }
        bytesum  += byteread;
        id3header = new Id3v23Header(buffer);
        System.out.println("ID3Header=" + id3header);
        long id3Size = id3header.getId3Size();
        while(id3Size > 0) {
            buffer = new byte[10];
            if ( (byteread = inStream.read(buffer)) == -1) {
                System.out.println("read error ID3Frame");
                inStream.close();
                System.out.println("read=" + bytesum);
                return ;
            }
            bytesum  += byteread;
            id3Size -= byteread;

            if (UBytes.isZero(buffer)) {
                // Padding
                buffer = new byte[(int) id3Size];
                if ( (byteread = inStream.read(buffer)) == -1) {
                    System.out.println("read error Padding");
                    inStream.close();
                    System.out.println("read=" + bytesum);
                    return ;
                }
                bytesum  += byteread;
                id3Size -= byteread;
                continue;
            }

            id3frame = new Id3v23Frame(buffer);
            System.out.println("ID3Frame=" + id3frame);
            buffer = new byte[(int)id3frame.getDataSize()];
            if ( (byteread = inStream.read(buffer)) == -1) {
                System.out.println("read error ID3Frame");
                inStream.close();
                System.out.println("read=" + bytesum);
                return ;
            }
            bytesum  += byteread;
            id3Size -= byteread;
            if ("APIC".equals(id3frame.getFrameId())) {
                System.out.println("ID3Frame=" + id3frame);
            } else {
                id3frame.setData(buffer);
                System.out.println("ID3Frame=" + id3frame);
            }
            System.out.println("id3Size=" + id3Size);
        }*/

        while (bytesum > 0) {
            // frame
            // MP3
            buffer = new byte[4];
            if ( (byteread = inStream.read(buffer)) == -1) {
                System.out.println("read error Mp3FrameHeader(4bytes)");
                inStream.close();
                System.out.println("read=" + bytesum);
                return ;
            }
            bytesum  += byteread;
            mp3header = new Mp3FrameHeader(buffer);
            System.out.println("MP3Header=" + mp3header);

            int frameSize = mp3header.getSize();
            frameSize -= byteread;
            /*
             * Protection bit
             * 0 - Protected by CRC (16bit crc follows header)
             * 1 - Not protected
             */
            int crc = mp3header.getProtection();
            if (crc == 0) {
                buffer = new byte[2];
                if ( (byteread = inStream.read(buffer)) == -1) {
                    System.out.println("read error crc(2bytes)");
                    inStream.close();
                    System.out.println("read=" + bytesum);
                    return ;
                }
                bytesum  += byteread;
                frameSize -= byteread;
                System.out.println(UBytes.toHexString(buffer));
                /*
                 * 3）CRC校验
                 * 如果帧头的校验位为0，则帧头后就有一个16位的CRC值，这个值是big-endian的值，把这个值和该帧通过计算得出的CRC值进行比较就可以得知该帧是否有效。
                 */
            }

            // side information
            int channel = mp3header.getChannelMode();
            int version = mp3header.getVersion();
            buffer = null;
            if (3 != channel) {
                // Stereo
                if (version == 3) {
                    // MPEG 1
                    buffer = new byte[32];
                }
                if (version == 0 || version == 2) {
                    // MPEG 2/2.5 (LSF)
                    buffer = new byte[17];
                }
            } else {
                // Mono
                if (version == 3) {
                    // MPEG 1
                    buffer = new byte[17];
                }
                if (version == 0 || version == 2) {
                    // MPEG 2/2.5 (LSF)
                    buffer = new byte[9];
                }
            }
            if ( (byteread = inStream.read(buffer)) == -1) {
                System.out.println("read error Mp3FrameSideInfo");
                inStream.close();
                System.out.println("read=" + bytesum);
                return ;
            }
            bytesum  += byteread;
            frameSize -= byteread;
            System.out.println(UBytes.toHexString(buffer));
            /*
             * 4）帧数据
             * 在帧头后边是Side Info(姑且称之为通道信息)。对标准的立体声MP3文件来说其长度为32字节。通道信息后面是Scale factor(增益因子)信息。当解码器在读到上述信息后，就可以进行解码了。
             * 对于mp3来说现在有两种编码方式，一种是CBR，也就是固定位率，固定位率的帧的大小在整个文件中都是是固定的（公式如上所述），只要知道文件总长度，和从第一帧帧头读出的信息，就都可以通过计算得出这个mp3文件的信息，比如总的帧数，总的播放时间等等，要定位到某一帧或某个时间点也很方便，这种编码方式不需要文件头，第一帧开始就是音频数据。另一种是VBR，就是可变位率，VBR是XING公司推出的算法，所以在MP3的FRAME里会有“Xing"这个关键字（也有用"Info"来标识的，现在很多流行的小软件也可以进行VBR压缩，它们是否遵守这个约定，那就不得而知了），它存放在MP3文件中的第一个有效帧的数据区里，它标识了这个MP3文件是VBR的。同时第一个帧里存放了MP3文件的帧的总个数，这就很容易获得了播放总时间，同时还有100个字节存放了播放总时间的100个时间分段的帧索引，假设4分钟的MP3歌曲，240S，分成100段，每两个相邻INDEX的时间差就是2.4S，所以通过这个INDEX，只要前后处理少数的FRAME，就能快速找出我们需要快进的帧头。其实这第一帧就相当于文件头了。不过现在有些编码器在编码CBR文件时也像VBR那样将信息记入第一帧，比如著名的lame，它使用"Info"来做CBR的标记。
             */
            mp3side = new Mp3FrameSideInfo(buffer, mp3header);
            System.out.println("MP3Side=" + mp3side);

            if (frameSize > 0) {
                buffer = new byte[frameSize];
                if ( (byteread = inStream.read(buffer)) == -1) {
                    System.out.println("read error ID3Header");
                    inStream.close();
                    System.out.println("read=" + bytesum);
                    return ;
                }
                bytesum  += byteread;
            }
            // Frame
        }


        inStream.close();
        System.out.println("read=" + bytesum);



    }

    public static void test(String in) throws Exception {
        org.jaudiotagger.audio.mp3.MP3File mp3 = new org.jaudiotagger.audio.mp3.MP3File(in);
        System.out.println(mp3.displayStructureAsPlainText());
    }
}
