package javay.sound.wav;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class Test01 {
   public static void main(String[] args) {
	   Test01Proc proc = new Test01Proc();
//	   proc.test01();
//	   proc.test02();
//	   proc.test03();
//	   proc.test04();
//	   proc.test05();
//	   proc.test07();
//	   proc.test08();
	   proc.test09();
   }
}
class Test01Proc {
	public void test01() {
	      try{
	         byte[] wave_data= new byte[44100 * 2];                      // @A-sta@
	         double l1      = 44100.0 / 440.0;    // A 440hz
	         for(int i = 0; i < wave_data.length; i ++) {
	            wave_data[i] = (byte) (110 * Math.sin((i / l1) * Math.PI * 2));
	         }                                                      //@A-end@

	         AudioFormat   frmt= new AudioFormat(44100, 8, 1, true, false);// @B-sta@
	         DataLine.Info info= new DataLine.Info(Clip.class,frmt);
	         Clip          clip= (Clip)AudioSystem.getLine(info);
	         clip.open(frmt,wave_data,0,wave_data.length);             // @B-end@
	         clip.start();

	         Thread.sleep(100);while(clip.isRunning()) {Thread.sleep(100);}
	      }
	      catch(Exception e){e.printStackTrace(System.err);}
	}
	public void test02() {
	      try{
	         byte[] wave_data = new byte[44100*3];// 
	         double l1      = 44100.0/440.0;    // A 440hz
	         double l2      = 44100.0/445.0;
	         for(int i=0;i<wave_data.length;i++){
	            wave_data[i]= (byte)(110*Math.sin((i/l1)*Math.PI*2));
	            wave_data[i]+= (byte)(55*Math.sin((i/l2)*Math.PI*2));
	         }                                                      //@A-end@

	         AudioFormat   frmt= new AudioFormat(44100,8,1,true,false);// @B-sta@
	         DataLine.Info info= new DataLine.Info(Clip.class,frmt);
	         Clip          clip= (Clip)AudioSystem.getLine(info);
	         clip.open(frmt,wave_data,0,wave_data.length);             // @B-end@
	         clip.start();

	         Thread.sleep(100);while(clip.isRunning()) {Thread.sleep(100);}
	      }
	      catch(Exception e){e.printStackTrace(System.err);}
	}
	public void test03() {
	      try{
	    	  byte[] pcm_data= new byte[44100*4]; 
	          double p1      = (440.0*Math.PI*2)/44100;
	          double pv      = (5.0*Math.PI*2)/44100;
	          double phase   = 0;
	          double v_phase = 0;
	          for(int i=0;i<pcm_data.length;i++){
	             phase   += p1;
	             v_phase += pv;
	             double p= phase+2.0*Math.sin(v_phase);
	             pcm_data[i]=  (byte)(110*Math.sin(p));
	             }

	         AudioFormat   frmt= new AudioFormat(44100,8,1,true,false);// @B-sta@
	         DataLine.Info info= new DataLine.Info(Clip.class,frmt);
	         Clip          clip= (Clip)AudioSystem.getLine(info);
	         clip.open(frmt,pcm_data,0,pcm_data.length);             // @B-end@
	         clip.start();

	         Thread.sleep(100);while(clip.isRunning()) {Thread.sleep(100);}
	      }
	      catch(Exception e){e.printStackTrace(System.err);}
	}
	public void test04() {
	      try{
	    	  byte[] pcm_data= new byte[44100*4]; 
	          double onkai[]={ -9,-7,-5,-4,-2,0,2,3 };// C,D,E,F,G,A,H,C
	          double px[]   = new double[8];
	          for(int i=0;i<8;++i){
	             px[i]= (440*Math.pow(2.0,onkai[i]/12.0)*Math.PI*2)/44100;
	             }
	          double phase   = 0;
	          for(int i=0;i<pcm_data.length;i++){
	             phase   += px[(i/10000)%8];
	             pcm_data[i]=  (byte)(110*Math.sin(phase));
	             }

	         AudioFormat   frmt= new AudioFormat(44100,8,1,true,false);// @B-sta@
	         DataLine.Info info= new DataLine.Info(Clip.class,frmt);
	         Clip          clip= (Clip)AudioSystem.getLine(info);
	         clip.open(frmt,pcm_data,0,pcm_data.length);             // @B-end@
	         clip.start();

	         Thread.sleep(100);while(clip.isRunning()) {Thread.sleep(100);}
	      }
	      catch(Exception e){e.printStackTrace(System.err);}
	}
	public void test05() {
	      try{
	          byte[] pcm_data= new byte[44100*2];
	          double f01 = 44100.0/220;
	          double f02 = 44100.0/(220*2);
	          double f03 = 44100.0/(220*3);
	          double f04 = 44100.0/(220*4);
	          double f05 = 44100.0/(220*5);
	          double f06 = 44100.0/(220*6);
	          for(int i=0;i<pcm_data.length;i++){
	             double b  = (byte)(50*Math.sin((i/f01)*Math.PI*2));
	             b        += (byte)(20*Math.sin((i/f02)*Math.PI*2));
	             b        += (byte)(10*Math.sin((i/f03)*Math.PI*2)); //<-第1フォルマント
	             b        += (byte)(0 *Math.sin((i/f04)*Math.PI*2));
	             b        += (byte)(20*Math.sin((i/f05)*Math.PI*2)); //<-第2フォルマント
	             b        += (byte)(20*Math.sin((i/f05)*Math.PI*2));
	             pcm_data[i] = (byte)b;
	             }

	         AudioFormat   frmt= new AudioFormat(44100,8,1,true,false);// @B-sta@
	         DataLine.Info info= new DataLine.Info(Clip.class,frmt);
	         Clip          clip= (Clip)AudioSystem.getLine(info);
	         clip.open(frmt,pcm_data,0,pcm_data.length);             // @B-end@
//	         FloatControl  control;
//	     //　 clip生成時
//	       control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
//	       // JSliderイベント発生時
//	       control.setValue((float)(Math.log10(slVol.getValue()/100.0) * 20.0));

	         clip.start();

	         Thread.sleep(100);while(clip.isRunning()) {Thread.sleep(100);}
	      }
	      catch(Exception e){e.printStackTrace(System.err);}
	}
	public void test07() {
	      try{
	          byte[] pcm_data= new byte[44100*2];
	          double L1      = 44100.0/440.0;
	          double L2      = 44100.0/455.0;
	          for(int i=0;i<pcm_data.length;i++){
	             pcm_data[i]=  (byte)(55*Math.sin((i/L1)*Math.PI*2));
	             pcm_data[i]+= (byte)(55*Math.sin((i/L2)*Math.PI*2));
	             }

	          AudioFormat      frmt= new AudioFormat(44100,8,1,true,false);
	          AudioInputStream ais = new AudioInputStream(
	                     new ByteArrayInputStream(pcm_data)
	                    ,frmt
	                    ,pcm_data.length);
	          AudioSystem.write(
	                     ais
	                    ,AudioFileFormat.Type.WAVE
	                    ,new File("test07.wav"));
	          }
	       catch(Exception e){e.printStackTrace(System.err);}
	}
	public void test08() {
	      try{
	          AudioInputStream ais 
	               = AudioSystem.getAudioInputStream(new File("test07.wav"));
	          AudioFormat      frmt = ais.getFormat();
	          DataLine.Info    info = new DataLine.Info(SourceDataLine.class,frmt);
	          SourceDataLine   line = (SourceDataLine) AudioSystem.getLine(info);
	          line.open(frmt);
	          line.start();
	          byte buf[]=new byte[1024];
	          int  len;
	          while( (len=ais.read(buf,0,buf.length))!=-1 ){
	             line.write(buf,0,len);
	             }
	          line.drain();
	          line.close();
	          }
	       catch(Exception e){e.printStackTrace(System.err);}
	}
	public void test09() {
	      try{
	          AudioInputStream ais 
	               = AudioSystem.getAudioInputStream(new File("test07.wav"));
	          AudioFormat      frmt = ais.getFormat();
	          byte[] buf= new byte[44100*4];
	          int  len;
	          int  dlen=0;
	          while( (len=ais.read(buf,dlen,buf.length-dlen))!=-1 ){ 
	             dlen+=len;
	             }

	          AudioFormat   frmt2= new AudioFormat(44100,8,1,true,false);
	          for(int idx=0;idx<dlen;++idx){
	             // ファイルから得たAudioFormatを使う場合この値変換は不要
	             buf[idx]= (byte)((((int)buf[idx])&0xFF)-128);
	             }
	          DataLine.Info info= new DataLine.Info(Clip.class,frmt2);
	          Clip          clip= (Clip)AudioSystem.getLine(info);
	          clip.open(frmt2,buf,0,dlen);
	          clip.start();

	          Thread.sleep(100);while(clip.isRunning()) {Thread.sleep(100);}
	          }
	       catch(Exception e){e.printStackTrace(System.err);}
	}
}