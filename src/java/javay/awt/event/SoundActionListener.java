package javay.awt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import javay.sound.SoundPlay;
import javay.sound.SoundRecord;
import javay.swing.SoundConts;
import javay.swing.SoundPanel;
import javay.util.log.expt.HandleExpt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundActionListener implements ActionListener {
    private static final Logger log = LoggerFactory.getLogger(SoundActionListener.class);
    private SoundPanel panel;
    private SoundPlay player;
    private SoundRecord record;

    public SoundActionListener(SoundPanel panel) {
        this.panel = panel;
        this.player = new SoundPlay(this.panel);
        this.record = new SoundRecord();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
              String scmd = ev.getActionCommand();
              log.debug("scmd=" + scmd);
              if (scmd.equals(SoundConts.Record)) {
                  this.record.setStopflag(false);
                  this.panel.btnReco.setEnabled(false);
                  this.panel.btnPrev.setEnabled(false);
                  this.panel.btnPlay.setEnabled(false);
                  this.panel.btnPause.setEnabled(false);
                  this.panel.btnStop.setEnabled(true);
                  this.panel.btnNext.setEnabled(false);
                  this.panel.btnSave.setEnabled(false);

                  float sampleRate = 8000f;
//                  sampleRate = 44100f;
//                  sampleRate = 48000f;
//                  sampleRate = 96000f;
//                  sampleRate = 192000f;
                  int sampleSizeInBits = 8;
                  // sampleSizeInBits = 16;
                  // sampleSizeInBits = 24; // ng
                  int channels = 1;
                  // channels = 2;
                  boolean signed = true;
                  if (sampleSizeInBits == 16) {
                      signed = true;
                  }
                  boolean bigEndian = false;
                  AudioFormat       af = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
                  DataLine.Info   info = new DataLine.Info(TargetDataLine.class, af);
                  TargetDataLine  line = (TargetDataLine)(AudioSystem.getLine(info));

                  this.record.setAf(af);
                  this.record.setLine(line);

                  Thread t1 = new Thread(this.record);
                  t1.start();
              }
              if (scmd.equals(SoundConts.Prev)) {

              }
              if (scmd.equals(SoundConts.Play)) {
                  this.player.setStopflag(false);
                  this.panel.btnReco.setEnabled(false);
                  this.panel.btnPrev.setEnabled(true);
                  this.panel.btnPlay.setEnabled(false);
                  this.panel.btnPause.setEnabled(true);
                  this.panel.btnStop.setEnabled(true);
                  this.panel.btnNext.setEnabled(true);

                  AudioInputStream ais   = AudioSystem.getAudioInputStream(new File("./classes/javay/sound/shangxinlei.wav"));
                  AudioFormat      af   = ais.getFormat();
                  if (this.record.getBaos() != null) {
                      //将baos中的数据转换为字节数据
                      byte audioData[] = this.record.getBaos().toByteArray();
                      log.debug("播放录音:" + audioData.length);
                      //转换为输入流
                      af = this.record.getAf();
                      log.debug("播放录音:" + af);
                      ais = new AudioInputStream(new ByteArrayInputStream(audioData), af, audioData.length/af.getFrameSize());
                  }

                  DataLine.Info    info = new DataLine.Info(SourceDataLine.class, af);
                  SourceDataLine   line = (SourceDataLine) AudioSystem.getLine(info);
                  Clip clip = AudioSystem.getClip();

                  this.player.setAis(ais);
                  this.player.setAf(af);
                  this.player.setLine(line);


                  Thread t2 = new Thread(this.player);
                  t2.start();


              }
              if (scmd.equals(SoundConts.Pause)) {

              }
              if (scmd.equals(SoundConts.Stop)) {
                  this.player.setStopflag(true);
                  this.record.setStopflag(true);

                  this.panel.btnReco.setEnabled(true);
                  this.panel.btnPrev.setEnabled(false);
                  this.panel.btnPlay.setEnabled(true);
                  this.panel.btnPause.setEnabled(false);
                  this.panel.btnStop.setEnabled(false);
                  this.panel.btnNext.setEnabled(false);
                  if (this.record.getBaos() != null) {
                      this.panel.btnSave.setEnabled(true);
                  } else {
                      this.panel.btnSave.setEnabled(false);
                  }

              }
              if (scmd.equals(SoundConts.Next)) {

              }
              if (scmd.equals(SoundConts.Save)) {
                  //将baos中的数据转换为字节数据
                  byte audioData[] = this.record.getBaos().toByteArray();
                  //转换为输入流
                  AudioFormat af = this.record.getAf();
                  ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
                  AudioInputStream ais = new AudioInputStream(bais, af, audioData.length/af.getFrameSize());

                  //定义最终保存的文件名
                  File file = null;
                  //写入文件
                  try {
                      //以当前的时间命名录音的名字
                      //将录音的文件存放到F盘下语音文件夹下
                      File filePath = new File("/sndfile");
                      if(!filePath.exists()) {
                          //如果文件不存在，则创建该目录
                          filePath.mkdir();
                      }
                      long time = System.currentTimeMillis();
                      file = new File(filePath+"/"+time+".wav");
                      AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
//                      //将录音产生的wav文件转换为容量较小的mp3格式
//                      //定义产生后文件名
//                      String tarFileName = time+".mp3";
//                      Runtime run = null;
//
//                      try {
//                          run = Runtime.getRuntime();
//                          long start=System.currentTimeMillis();
//                          //调用解码器来将wav文件转换为mp3文件
//                          // Process p=run.exec(filePath+"/"+"lame -b 16 "+filePath+"/"+file.getName()+" "+filePath+"/"+tarFileName); //16为码率，可自行修改
//                          //释放进程
////                          p.getOutputStream().close();
////                          p.getInputStream().close();
////                          p.getErrorStream().close();
////                          p.waitFor();
//                          long end=System.currentTimeMillis();
//                          System.out.println("convert need costs:"+(end-start)+"ms");
//                          //删除无用的wav文件
//                          if(file.exists()) {
//                              file.delete();
//                          }
//                      } catch (Exception e) {
//                          e.printStackTrace();
//                      }finally{
//                          //最后都要执行的语句
//                          //run调用lame解码器最后释放内存
//                          run.freeMemory();
//                      }

                  } catch (Exception e) {
                      e.printStackTrace();
                  }finally{
                      //关闭流
                      try {
                          if(bais != null) {
                              bais.close();
                          }
                          if(ais != null) {
                              ais.close();
                          }
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }

                  this.panel.btnSave.setEnabled(false);
                  this.record.setBaos(null);
              }
        } catch (Exception ex) {
          HandleExpt.handle(ex);
          //ex.printStackTrace();
        }
    }

    public void setVolume(int value) {
        player.setVolume(value);
    }
}
