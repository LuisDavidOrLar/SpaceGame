
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class Muro {
    public int vida,xPos,yPos,aux, colision,colision2;;
    public final BufferedImage muro;
    Random rand;
    
    public Muro(int vida,int colision1, int colision2){
    rand= new Random();
    this.colision=colision1;
    this.colision2=colision2;    
    this.vida=vida;  
    xPos=82;
    yPos=-10;
       
    try {
            muro = ImageIO.read(getClass().getResourceAsStream("imagenes/base.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(MainLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void render(Graphics g){

        g.drawImage(muro, xPos, yPos,muro.getWidth(),muro.getHeight(), null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 40)); 
        g.drawString(String.valueOf(vida), (muro.getWidth()/2)+67, yPos+155);
        
    }
    
    public Rectangle getBounds() {
        return new Rectangle(xPos, yPos, muro.getWidth(), muro.getHeight());
    }
    
    public void update(){
        yPos+=2;
        
    }
    
}
