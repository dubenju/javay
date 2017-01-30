package javay.test;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class TestFlvPlay extends Application {
        @Override
        public void start(Stage primaryStage) {
            try {
                // シーングラフを作成
                StackPane   Pane        = new StackPane();

                // 動画ファイルのパスを取得
                File        f           = new File( "movie\\oow2010-2.flv" );

                // 動画再生クラスをインスタンス化
                Media       Video       = new Media( f.toURI().toString() );
                MediaPlayer Play        = new MediaPlayer(Video);
                MediaView   mediaView   = new MediaView(Play);

                // シーングラフに追加
                Pane.getChildren().add(mediaView);

                // シーンを追加
                Scene   scene   = new Scene(Pane, 750, 500);

                // ステージ設定
                primaryStage.setTitle("VideoPlay");
                primaryStage.setScene(scene);
                primaryStage.show();
                Play.play();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }