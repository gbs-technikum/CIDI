package car.Hilfsklassen;

import java.awt.Canvas;
import uk.co.caprica.vlcj.binding.LibVlc;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import javax.swing.SwingUtilities;

public class Player {

	private final EmbeddedMediaPlayer mediaPlayer;
	private Canvas cStreamFenster;
	private MediaPlayerFactory mediaplayerfactory;
	private CanvasVideoSurface videosurface;

	public Player(String url, Canvas cInput) {

		cStreamFenster = cInput;

		mediaplayerfactory = new MediaPlayerFactory();
		videosurface = mediaplayerfactory.newVideoSurface(cStreamFenster);
		mediaPlayer = mediaplayerfactory.newEmbeddedMediaPlayer();
		mediaPlayer.setVideoSurface(videosurface);
		cStreamFenster.setSize(400, 300);
		/**
		 * !! WICHTIG !!
		 */
		mediaPlayer.playMedia(url);
	}

	public static void startPlayer(Canvas cInput){
		/**
		 * Pfad der VLC-Libraries in Windows
		*/
//		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
//				"C:\\Program Files\\VideoLAN\\VLC");
		
		/**
		 * Pfad der VLC-Libraries auf RPi
		 */	
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"/usr/lib");
		
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		final Canvas cTest = cInput;
		
		final String rtsp = "rtsp://localhost:8554/";
//		final String rtsp = "D:\\Downloads\\video.mp4";

		/**
		 * Starten des Players
		 */
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Player(rtsp, cTest);
			}
		});
	}
}
