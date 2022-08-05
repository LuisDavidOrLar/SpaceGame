
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Background {

    public int nivel, xPos = 0, yPos = 0;
    private boolean nivel1 = true, nivel2, nivel3;
    private final BufferedImage image, image2;
    Rectangle pared1, pared2;

    /**
     * Es el constructor de Background que es el que maneja el movimiento infinito del fondo
     * @param nivel que es una variable que sirve para que al inicio del juego se genere el primer fondo 
     * @throws IOException ya que uso 2 BufferedImage para los fondos
     */
    public Background(int nivel) throws IOException {

        this.nivel = 0;
        image = ImageIO.read(getClass().getResourceAsStream("imagenes/nivel1.png"));
        image2 = ImageIO.read(getClass().getResourceAsStream("imagenes/nivel2.png"));
        asignarRectangulo();

    }

    /**
     * render me imprime de manera infinita el fondo del juego
     * @param g java.awt.Graphics
     * @param yOffset es una variable que me indica la posicion de la primera imagen para imprimir la segunda en loop
     * @param height  es la variable que me indica el tamaño de la primera imagen en el mapa
     */
    public void render(Graphics g, int yOffset, int height) {

        Graphics2D g2d = (Graphics2D) g.create();

        yPos = yOffset;

        while (yPos > 0) {
            yPos -= height;
            g2d.drawImage(image2, xPos, yPos, null);
        }

        yPos = yOffset;
        while (yPos < height) {
            if (nivel == 0) {
                g2d.drawImage(image, xPos, yPos, null);
            } else {
                g2d.drawImage(image2, xPos, yPos, null);
            }
            yPos += height;

        }

        
        if (yPos > 1500 && yPos < 1550) {
            nivel++;
        }

        asignarRectangulo();

    }

    /**
     * Se asigna de manera dinamica los bordes de las paredes dependiendo de que imagen sea
     */
    public void asignarRectangulo() {
        if (nivel == 0) {
            pared1 = new Rectangle(0, 0, 370, 1024);
            pared2 = new Rectangle(718, 0, 370, 1024);
        } else {
            pared1 = new Rectangle(0, 0, 84, 1024);
            pared2 = new Rectangle(1006, 0, 40, 1024);
        }

    }

}
