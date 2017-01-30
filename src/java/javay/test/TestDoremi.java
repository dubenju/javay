package javay.test;

import java.io.IOException;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 音声波形を作成し出力するプログラム
 */
public class TestDoremi extends Application {
    // 定数宣言
    // ドレミ音階の周波数
    // http://www.yk.rim.or.jp/~kamide/music/notes.html
    private final double[]  FREQUANCIES = { 261.6255653005986d ,    // ド
                                            293.6647679174076d ,    // レ
                                            329.6275569128699d ,    // ミ
                                            349.2282314330039d ,    // ファ
                                            391.9954359817492d ,    // ソ
                                            440.0d ,                // ラ
                                            493.8833012561241d ,    // シ
                                            523.2511306011972d };   // ド
    private final String[]  DOREMI      = { "ド"  , "レ" , "ミ" ,   // 音階の文字表現
                                            "ファ", "ソ" , "ラ" ,
                                            "シ"  , "ド" };
    private final long      DURATION    = 100;                      // 一度に音を鳴らす時間[ms]

    // 変数宣言
    private int             note        = 0;                        // 現在出力中の音階を表すインデックス
    byte[]                  buffer      = null;                     // 現在出力中の音声波形データ
    private double          amplitude   = 10;                       // 現在出力中の音声波形の最大振幅(音量に相当)
    VBox                    root        = null;                     // シーングラフのルートノード

    /**
     * プログラム起動用関数
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // フォント色がおかしくなることへの対処
        System.setProperty( "prism.lcdtext" , "false" );

        // シーングラフの作成
                    root    = new VBox();
        Button      button  = new Button( "音階変更" );
        root.getChildren().add( button );

        // シーンの作成
        Scene       scene   = new Scene( root , 300 , 250 );

        // ウィンドウ表示
        primaryStage.setScene( scene );
        primaryStage.show();

        // ボタンのイベントハンドラを設定
        // ボタン押下時に音階を変更する
        button.addEventHandler(ActionEvent.ACTION, e ->  note = ( note + 1 ) % FREQUANCIES.length );

        // 音声再生
        soundStart();

    }

    /**
     * 内部で音声再生スレッドを起動
     *
     * 音階変更時に音が途切れるのは、チャート作成用のタスクが最優先で実行されるため
     *
     * それ以外で音が途切れるのは、CPUの処理が追いついていない場合。
     * この場合、DURATIONの値を上げるとよい
     * @throws Exception
     */
    public void soundStart() throws Exception {
        // 波形データを作成するスレッドを起動するタスクを定義
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                // システムで利用しているエンディアン（バイト・オーダ）を取得
                ByteOrder   bo          = ByteOrder.nativeOrder();
                boolean     isBigEndian = ( bo == ByteOrder.BIG_ENDIAN );

                // 出力音声のフォーマットを指定
                AudioFormat         format      = new AudioFormat( AudioFormat.Encoding.PCM_SIGNED ,    // オーディオのデータフォーマット
                                                                   44100.0f ,                           // サンプリングレート
                                                                   Byte.SIZE ,                          // 1サンプルを表すビット数
                                                                   1,                                   // モノラル(1)・ステレオ指定(2)
                                                                   1,                                   // フレームのバイト数 = 第3引数 * 第4引数 / 8
                                                                   44100.0f ,                           // 1秒あたりのフレーム数 = 第2引数
                                                                   isBigEndian );                       // ビッグエンディアンかどうか

                // 音声波形データ格納用のバッファを作成
                int             bufferSize  = (int)( format.getSampleRate() * format.getFrameSize() * DURATION / 1000.0 );
                                buffer      = new byte[ bufferSize  ];

                // 音声出力ラインを作成
                DataLine.Info   info        = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine  line        = (SourceDataLine) AudioSystem.getLine(info);

                // 音声出力ラインを開く
                line.open(format, buffer.length);
                line.start();

                // 音声再生用ループ
                try{
                    // 前回鳴らした音階
                    int beforeNote  = -1;

                    // 音声再生用ループ
                    while( true ) {
                        // 音声データをバッファに書込
                        int readBytes   = 0;
                        if( -1 == ( readBytes = writeBuffer( buffer , format ) ) ){ break; }

                        // チャートの更新
                        if( note != beforeNote ) {
                            // チャートの作成
                            Platform.runLater( () -> addChart() );

                            // 更新
                            beforeNote = note;
                        }

                        // スリープ
                        sleep( DURATION );

                        // 音声データ再生
                        line.write(buffer, 0, readBytes);

                    }
                }finally{
                    // 再生終了を待つ
                    line.drain();

                    // 音声再生終了
                    line.close();
                }

                // タスクを終わらせる
                return true;
            };
        };

        // タスクを実行
        Thread t = new Thread( task );
        t.setDaemon( true );
        t.start();

    }


    /**
     * バッファに波形を書き込む
     * @param buffer
     * @return
     * @throws IOException
     */
    private int writeBuffer( byte[] buffer , AudioFormat format ) throws IOException {
        // 新しい音階の波形データをバッファに書き込み
        for( int i=0 ; i<buffer.length ; i++ ) {
            // サンプル1つ分の値を書き込み
            // 波形はサイン波
            radian      = radian + 2.0 * Math.PI * FREQUANCIES[note] / format.getSampleRate();
            radian      = Math.toRadians( Math.toDegrees( radian ) );
            double tmp  = amplitude * Math.sin( radian );
            buffer[i]   = (byte)tmp;
        }

        // 値を返す
        return buffer.length;
    }
    private double          radian      = 0.0d;                     // 前回のラジアン


    /**
     *
     * 前回関数の呼び出しから、duration[ms]経過するまでsleepする関数
     * @param duration
     * @throws InterruptedException
     */
    private void sleep( long duration ) throws InterruptedException {
        // 現在の時刻を取得
        long now = System.currentTimeMillis();

        // 最初の呼び出しの際には、duratioin[ms]の間スリープするように設定
        if( beforeTime == 0 ){ beforeTime = now - duration; }

        // もし、前回呼び出しからduration[ms]経つまでスリープする
        if( now - beforeTime < duration ) {
            Thread.sleep( now - beforeTime );
        }

        // 時間を更新
        beforeTime = now;
    }
    private long beforeTime = 0;                                    // 前回の関数起動時間

    /**
     * 折れ線グラフで波形表示
     * @return
     */
    @SuppressWarnings("unchecked")
    public void addChart() {
        // 折れ線グラフ
        NumberAxis                  xAxis   = new NumberAxis();
        NumberAxis                  yAxis   = new NumberAxis();
        LineChart<Number, Number>   chart   = new LineChart<Number, Number>( xAxis , yAxis );
        chart.setMinWidth( 300 );
        chart.setMinHeight( 200 );

        // データがない場合は終了
        if( buffer == null ){ return; }

        // データを作成
        Series< Number , Number > series1    = new Series<Number, Number>();
        series1.setName( DOREMI[note]  );
        for( int i=0 ; i<buffer.length ; i++ ) {
            series1.getData().add( new XYChart.Data<Number, Number>( i , (int)buffer[i] ) );
        }

        // データを登録
        chart.getData().addAll( series1 );

        // 見た目を調整
        chart.setCreateSymbols(false);                                                      // シンボルを消去
        series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 0.75px;");  // 線を細く

        // チャートがあれば削除
        if( root.getChildren().size() > 1 ){ root.getChildren().remove( 1 ); }

        // チャートの追加
        root.getChildren().add( chart );
    }
}
