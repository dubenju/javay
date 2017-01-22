package javay.sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;


public class PlayerMusic {
    public PlayerMusic(String filename) {
        this.filename = filename;
    }

    public void play() {
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
            long l1 = System.nanoTime();
            player = new Player(buffer);

            long l2 = System.nanoTime();

            player.play();
            long l3 = System.nanoTime();
            System.out.println("\tnew:" + (l2 - l1) / 1000000 + "ms,play:" + (l3 - l2) / 1000000 + "ms.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        PlayerMusic mp3 = new PlayerMusic("./in/01-Get Your Head Straight.mp3");
        mp3.play();

    }

    private String filename;
    private Player player;
}