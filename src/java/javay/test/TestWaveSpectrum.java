package javay.test;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 音声(wav)データの波形を見るプログラム
 * ただし、Wav(PCM・リトルエディアン)形式で保存された
 * ファイルのチャンネル１のみ出力
 */
public class TestWaveSpectrum extends Application {
    // 常量
    private final String    fileName    = "music/dog01.wav";        // 要表示的声音文件
    private final double    sec         = 0.15;                     // 要表示的时长(s)

    // 获取声音信息用的变量
    private AudioFormat     format                  = null;
    private double[]        valuesActual            = null;
    private double[]        valuesImaginal          = null;
    private double[]        spectrumActual          = null;
    private double[]        spectrumImaginal        = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 字色的设置
        System.setProperty( "prism.lcdtext" , "false" );

        // 场景图
        HBox        root    = new HBox();
        VBox        box1    = new VBox();
        VBox        box2    = new VBox();
        root.getChildren().addAll( box1 , box2 );

        // 音声データを読込
        System.out.println( "loading wav data..." );
        initialize();

        // 元音声波形をチャート表示
        box1.getChildren().add( createLineChart( "音声波形" , valuesActual ) );            // 折れ線グラフの追加

        // 離散フーリエ変換後の波形をチャート表示
        System.out.println( "caliculating DFT..." );
        spectrumActual       = new double[ valuesActual.length ];
        spectrumImaginal     = new double[ valuesActual.length ];
        DFT( valuesActual , spectrumActual , spectrumImaginal , false );

        // 離散フーリエ変換後のスペクトルをチャート表示
        box2.getChildren().add( createLineChart( "スペクトル(実数部)" , spectrumActual ) );            // 折れ線グラフの追加
        box2.getChildren().add( createLineChart( "スペクトル(虚数部)" , spectrumImaginal ) );          // 折れ線グラフの追加

        // 逆離散フーリエ変換
        System.out.println( "caliculating IDFT..." );
        for( int i=0 ; i<valuesActual.length ; i++ ) {
            valuesActual[i]     = spectrumActual[i];
            valuesImaginal[i]   = spectrumImaginal[i];
        }
        IDFT( valuesActual , valuesImaginal , spectrumActual , spectrumImaginal );

        // 逆フーリエ変換後のスペクトルをチャート表示
        box1.getChildren().add( createLineChart( "逆フーリエ変換後の音声波形" , spectrumActual ) );          // 折れ線グラフの追加

        // 现场
        Scene       scene   = new Scene( root , 800 , 400 );

        // 窗口
        primaryStage.setScene( scene );
        primaryStage.show();

    }

    /**
     * 音声ファイルを読み込み、メタ情報とサンプリング・データを取得
     * @throws Exception
     */
    protected void initialize() throws Exception {
        // 获取AudioInputStream
        File                file    = new File( fileName );
        AudioInputStream    is      = AudioSystem.getAudioInputStream( file );

        // 获取Format信息
        format = is.getFormat();
        System.out.println( format.toString() );

        // 计算获取的样本数
        // 指定时间的样本数
        int mount   = (int) ( format.getSampleRate() * sec );

        // 读取声音数据
        valuesActual    = new double[ mount ];
        valuesImaginal  = new double[ mount ];
        for(int i = 0; i < mount; i ++) {
            // 1帧的大小
            int     size        = format.getFrameSize();
            byte[]  data        = new byte[ size ];
            int     readedSize  = is.read(data);

            // 读取失败时
            if( readedSize == -1 ){ break; }

            // SampleSizeInBits
            switch( format.getSampleSizeInBits() ) {
                case 8:
                    valuesActual[i]   = (int) data[0];
                    break;
                case 16:
                    valuesActual[i]   = (int) ByteBuffer.wrap( data ).order( ByteOrder.LITTLE_ENDIAN ).getShort();
                    break;
                default:
                    break;
            }
        }

        // 关闭AudioInputStream
        is.close();
    }

    /**
     * 離散フーリエ変換
     * @param in フーリエ変換を行う実数配列
     * @param outActual 計算結果の実数部配列
     * @param outImaginal 計算結果の虚数部配列
     * @param winFlg 窓関数の使用フラグ
     */
    protected void DFT( double[] in , double[] outActual , double[] outImaginal , boolean winFlg ) {
        // 配列初期化
        int  length             = in.length;

        // 離散フーリエ変換
        for( int k=0 ; k<length ; k++ ) {
            // 初期化
            outActual[k]    = 0.0d;
            outImaginal[k]  = 0.0d;

            // 計算
            for( int n=0 ; n<length ; n++ ) {
                // 入力値に窓関数を適用
                double normal   = ( !winFlg )? in[n]  : hanWindow( in[n] , n , 0 , length );

                // k次高周波成分を計算
                outActual[k]    +=        normal * Math.cos( 2.0 * Math.PI * (double)n * (double)k / (double)length );
                outImaginal[k]  += -1.0 * normal * Math.sin( 2.0 * Math.PI * (double)n * (double)k / (double)length );
            }

            // 残りの計算
            //outActual[k]    /= length;
            //outImaginal[k]  /= length;
        }
    }

    /**
     * 逆離散フーリエ変換
     * @param inActual 逆離散フーリエ変換を行う値の実数部配列
     * @param inImaginal 逆離散フーリエ変換を行う値の虚数部配列
     * @param outActual 計算結果の実数部配列
     * @param outImaginal 計算結果の虚数部配列
     */
    protected void IDFT( double[] inActual , double[] inImaginal , double[] outActual , double[] outImaginal ) {
        // 配列初期化
        int  length             = inActual.length;

        // 離散フーリエ変換
        for( int k=0 ; k<length ; k++ ) {
            // 初期化
            outActual[k]    = 0.0d;
            outImaginal[k]  = 0.0d;

            // 計算
            for( int n=0 ; n<length ; n++ ) {
                // k次高周波成分を計算
                outActual[k]    +=  inActual[n]   * Math.cos( 2.0 * Math.PI * (double)n * (double)k / (double)length )
                                -   inImaginal[n] * Math.sin( 2.0 * Math.PI * (double)n * (double)k / (double)length );
                outImaginal[k]  +=  inActual[n]   * Math.sin( 2.0 * Math.PI * (double)n * (double)k / (double)length )
                                +   inImaginal[n] * Math.cos( 2.0 * Math.PI * (double)n * (double)k / (double)length );
            }

            // 残りの計算
            outActual[k]    /= length;
            outImaginal[k]  /= length;
        }
    }

    /**
     * 窓関数（ハン窓）
     * @param in 変換する値
     * @param i 配列中のインデックス
     * @param minIndex 配列の最小インデックス
     * @param maxIndex 配列の最大インデックス
     * @return
     */
    protected double hanWindow( double in , double i , double minIndex , double maxIndex ) {
        // 入力値の正規化
        double normal   = i / ( maxIndex - minIndex );

        // ハン窓関数の値を取得
        double  han     =  0.5 - 0.5 * Math.cos( 2.0 * Math.PI * normal );

        return in * han;
    }

    /**
     * 用折线图表示
     * @param title グラフのタイトル文字
     * @param values グラフに出力するデータ配列
     * @return 作成したグラフノード
     */
    @SuppressWarnings("unchecked")
    protected Node createLineChart( String title , double[] values ) {
        // 折线图
        NumberAxis                  xAxis   = new NumberAxis();
        NumberAxis                  yAxis   = new NumberAxis();
        LineChart<Number, Number>   chart   = new LineChart<Number, Number>( xAxis , yAxis );
        chart.setMinWidth( 400 );
        chart.setMinHeight( 200 );

        // 生成数据
        Series< Number , Number > series1    = new Series<Number, Number>();
        series1.setName( title  );
        for( int i=0 ; i<values.length ; i++ ) {
            series1.getData().add( new XYChart.Data<Number, Number>( i , values[i] ) );
        }

        // 登录数据
        chart.getData().addAll( series1 );

        // 调整外观
        chart.setCreateSymbols(false);                                                          // 去掉符号
        series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 0.75px;");   // 细线

        return chart;
    }
}
