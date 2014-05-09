package car.Hilfsklassen;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;

import uk.co.caprica.vlcj.binding.LibVlc;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Player extends JFrame {

	private static final long serialVersionUID = 1L;
	private final EmbeddedMediaPlayer mediaPlayer;
	private Canvas canvas;
	private MediaPlayerFactory mediaplayerfactory;
	private CanvasVideoSurface videosurface;
	private Container c;
	private JPanel jpn, jpe, jps, jpw;

	private Player(String url) {

		c = this.getContentPane();
		jpn = new JPanel();
		jpn.add(new JLabel("K L U K"));
		jpe = new JPanel();
		jpe.setBackground(new Color(240));
		jps = new JPanel();
		jpw = new JPanel();
		jpw.setBackground(new Color(240));
		
		canvas = new Canvas();

		mediaplayerfactory = new MediaPlayerFactory();
		videosurface = mediaplayerfactory.newVideoSurface(canvas);
		mediaPlayer = mediaplayerfactory.newEmbeddedMediaPlayer();
		mediaPlayer.setVideoSurface(videosurface);
		canvas.setSize(800, 600);

		c.add(jpn, BorderLayout.NORTH);
		c.add(jpe, BorderLayout.EAST);
		c.add(jps, BorderLayout.SOUTH);
		c.add(jpw, BorderLayout.WEST);
		c.add(canvas, BorderLayout.CENTER);

		this.setTitle("Live-Stream");
		this.setLocation(100, 100);
		this.setSize(1280, 1024);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		/**
		 * !! WICHTIG !!
		 */
		mediaPlayer.playMedia(url);
	}

	public static void main(final String[] args) {

		final String rtsp = "rtsp://192.168.15.85:8554/";
//		final String video = "D:\\Downloads\\video.mp4";

		/**
		 * Pfad der VLC-Libraries laden VLC-Library - Pfad beim Raspberry:
		 * "/usr/lib"
		 */
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"C:\\Program Files\\VideoLAN\\VLC");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		/**
		 * Starten des Players
		 */
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Player(rtsp);
			}
		});
	}

}