
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class Gasolina {
    
    
    BufferedImage gasolina;
    public int xPos,yPos,aux;
    int colision,colision2;
    Random rand;
            
    /**
     * Es el constructor de los bidones de gasolina del juego
     * llamamos a asignarX() aqui para generar el bidon de manera aleatoria
     * @param x es la pared izquierda, lo uso pára detectar colision y cambiar de sentido el movimiento
     * @param y es la pared derecha, lo uso pára detectar colision y cambiar de sentido el movimiento
     */
    
    public Gasolina(int x, int y){
        rand= new Random();
        colision=x;
        colision2=y;
        asignarX();
        yPos= -10;
        try {
            gasolina = ImageIO.read(getClass().getResourceAsStream("imagenes/fuel.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(MainLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    /**
     * Esta es la funcion para pintar los bidones de gasolina 
     * @param g java.awt.Graphics
     */
    
    public void render(Graphics g){
        
        g.drawImage(gasolina, xPos, yPos,gasolina.getWidth(),gasolina.getHeight(), null);
        
    }
    
    /**
     * Actualizo el movimiento del bidon de gasolina
     */
    public void update(){
        yPos+=2;
        
    }
    
     /**
     * Crea un rectangulo alrededor del objeto para poder manejar las colisiones
     * @return el rectangulo correspondiente
     */
     public Rectangle getBounds() {
        return new Rectangle(xPos-25, yPos-25, gasolina.getWidth()*2, gasolina.getHeight()*2);
    }
     
     /**
     * Asigna la posicion de generacion de manera aleatoria
     */
     public void asignarX(){
        do{
            
           aux = rand.nextInt(800 - 100 + 1) + 100;
            
            
        }while(aux<=colision+5 && aux>=colision2-10);
        
        xPos= aux;
        
    }
    
    
}
