package javay.test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 波形文件数据的表示
 * 只表示声道1的信息
 */
public class TestWaveDraw extends Application {
    // 常量
    private final String fileName = "music/dog01.wav"; // 要表示的声音文件
    private final double sec = 0.15; // 要表示的时长(s)

    // 获取声音信息用的变量
    private AudioFormat format = null;
    private int[] values = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 字色的设置
        System.setProperty("prism.lcdtext" , "false" );

        // 场景图
        HBox root = new HBox();

        // 图
        init(); // 读取声音文件的信息到values
        root.getChildren().add(createLineChart()); // 折线

        // 现场
        Scene scene = new Scene(root, 900, 300);

        // 窗口
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 读取声音文件的信息到values
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public void init() throws Exception {
        // 获取AudioInputStream
        File file = new File(fileName);
        AudioInputStream is = AudioSystem.getAudioInputStream(file);

        // 获取Format信息
        format = is.getFormat();
        System.out.println(format.toString());
        System.out.println("采样率:" + format.getSampleRate());
        System.out.println("帧大小:" + format.getFrameSize());
        System.out.println("样本大小:" + format.getSampleSizeInBits());

        // 计算获取的样本数
        // 指定时间的样本数
        int mount = (int) (format.getSampleRate() * sec);

        // 读取声音数据
        values  = new int[mount];
        for(int i = 0; i < mount; i ++) {
            // 1帧的大小
            int size = format.getFrameSize();
            byte[] data = new byte[size];
            int readedSize = is.read(data);

            // 读取失败时
            if( readedSize == -1 ){
                break;
            }

            // SampleSizeInBits
            switch( format.getSampleSizeInBits() ) {
                case 8:
                    values[i] = (int) data[0];
                    break;
                case 16:
                    values[i] = (int) ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getShort();
                    break;
                default:
                    break;
            }
        }

        // 关闭AudioInputStream
        is.close();
    }

    /**
     * 用折线图表示
     * @return Node
     */
    @SuppressWarnings("unchecked")
    public Node createLineChart() {
        // 折线图
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);
        chart.setMinWidth(900);

        // 生成数据
        Series<Number, Number> series1 = new Series<Number, Number>();
        series1.setName("声道1");
        for(int i = 0; i < values.length; i ++) {
            series1.getData().add(new XYChart.Data<Number, Number>(i, values[i]));
        }

        // 登录数据
        chart.getData().addAll(series1);
        // 设置标题
        String title = String.format("『%s』的波形数据（采样率：%.1fHz）", fileName, format.getSampleRate());
        chart.setTitle(title);

        // 调整外观
        chart.setCreateSymbols(false); // 去掉符号
        series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 1px;"); // 细线

        return chart;
    }
}
