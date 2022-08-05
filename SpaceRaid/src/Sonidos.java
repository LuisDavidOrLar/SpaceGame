
import java.net.URL;
import javafx.scene.media.AudioClip;

/**
 * Es la clase que maneja los sonidos del juego, usa AudioClip de javafx ya que tiene opciones simplificadas para reproducir audios
 * @author Luis Ortega 
 */
public class Sonidos {

      public  AudioClip inicio = new AudioClip(getClass().getResource("/sonidos/intro.wav").toExternalForm());
      public  AudioClip DISPARO = new AudioClip(getClass().getResource("/sonidos/disparo.wav").toExternalForm());
      public  AudioClip explosionnave = new AudioClip(getClass().getResource("/sonidos/navexplosion.wav").toExternalForm());
      public  AudioClip explosionenemigo = new AudioClip(getClass().getResource("/sonidos/explosion.wav").toExternalForm());
      public  AudioClip background = new AudioClip(getClass().getResource("/sonidos/background.wav").toExternalForm());
      public  AudioClip puente = new AudioClip(getClass().getResource("/sonidos/puenteexplosion.wav").toExternalForm());
      public  AudioClip gameover = new AudioClip(getClass().getResource("/sonidos/gameover.wav").toExternalForm());
}
