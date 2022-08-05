
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Enemigos {

    public int xPos, yPos,colision,colision2,auxenemigo,aux;
    
    private final BufferedImage enemigo1, enemigo2;
    public boolean izquierda, derecha;
    Random rand;

    /**
     * Es el constructor de los 2 tipos de enemigos que tenemos en el juego aparte del muro para subir de nivel
     * llamamos a asignarX() aqui para generar el enemigo de manera aleatoria
     * @param colision1 es la pared izquierda, lo uso pára detectar colision y cambiar de sentido el movimiento
     * @param colision2 es la pared derecha, lo uso pára detectar colision y cambiar de sentido el movimiento
     */
    public Enemigos(int colision1, int colision2) {
        izquierda = true;
        rand= new Random();
        this.colision=colision1;
        this.colision2=colision2;
        auxenemigo =(int) (Math.random() * (2 - 1 + 1) + 1);
        derecha = false;
        asignarX();
        yPos= -10;
        try {
            enemigo1 = ImageIO.read(getClass().getResourceAsStream("imagenes/Enemigo1.png"));
            enemigo2 = ImageIO.read(getClass().getResourceAsStream("imagenes/Enemigo2.png"));
        } catch (IOException ex) {
            Logger.getLogger(MainLoop.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * Esta es la funcion para pintar los enemigos, depende de auxenemigo 
     * para generar un tipo de enemigo aleatorio
     * @param g java.awt.Graphics
     */

    public void render(Graphics g) {
        if(auxenemigo==1){
       
            g.drawImage(enemigo1, xPos, yPos,enemigo1.getHeight(),enemigo1.getWidth(), null);
        }
        if(auxenemigo==2){
         
            g.drawImage(enemigo2, xPos, yPos,enemigo1.getHeight(),enemigo1.getWidth(), null);
        }
        
    }
    /**
     * Aqui actualizo el movimiento del enemigo, depende de auxenemigo para saber 
     * a que velocidad se mueve dependiendo del tipo de enemigo
     * @param bg es un objeto del tipo Background para saber cuando colisiona y tiene que cambiar de sentido
     */

    public void update(Background bg) {
        if(auxenemigo==1){
             yPos+=2;
        if (izquierda) {
            xPos -= 3;
        } else if(derecha){
            xPos += 3;
        }
        }
        
          if(auxenemigo==2){
             yPos+=2;
        if (izquierda) {
            xPos -= 6;
        } else if(derecha){
            xPos += 6;
        }
        }
       

        if (this.getBounds().intersects(bg.pared1)) {
            izquierda = false;
            derecha = true;
        } else if (xPos>=bg.pared2.x-135) {
            derecha = false;
            izquierda = true;
        }

    }
    /**
     * Asigna la posicion de generacion de manera aleatoria
     */
    public void asignarX(){
        do{         
        aux = rand.nextInt(1000 - 100 + 1) + 100;
        }while(aux<=colision+5 && aux>=colision2-5);
        
        xPos= aux;
        
    }

    /**
     * Crea un rectangulo alrededor del objeto para poder manejar las colisiones
     * @return el rectangulo correspondiente
     */
    public Rectangle getBounds() {
        return new Rectangle(xPos-7, yPos, enemigo1.getHeight()+10, enemigo1.getWidth()+10);
    }

}
