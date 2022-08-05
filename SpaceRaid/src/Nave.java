
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

 public class Nave {

        public int x = 460, y = 600, speed = 5, vida,yMisil,aux=0,xMisil;
        public final BufferedImage image,misil;
        Rectangle misilrec;
        Timer timer;
        Sonidos sonido;
        public boolean LEFT, RIGHT,SPACE,misilandando,muerte;

        /**
         * Constructor de nuestra nave
         * @param image es la imagen de la nave
         * @param misil es la imagen del misil
         */
        public Nave(BufferedImage image, BufferedImage misil) {
            sonido=new Sonidos();
            this.muerte= false;
            this.timer = new Timer(5, new TimerPer());
            this.image = image;
            this.misil=misil;
            this.vida=3;
            this.misilandando=false;
            this.misilrec= new Rectangle();
        }
        
        /**
        * render dibuja la nave y el misil solo si se encuentra activo 
        * @param g2d java.awt.Graphics2D
        */
        public void render(Graphics2D g2d) {
            
            g2d.drawImage(this.image, this.x, this.y, null);
            if(misilandando){
           
            g2d.drawImage(this.misil, this.xMisil, this.yMisil,misil.getHeight()*2,misil.getWidth()*2, null);
            }
        }
        
        
        /**
         * Actualiza la posicion de la nave y dispara si se activa su respectiva
         * flag, que se activan presionando el respectivo boton.
         * Si se acaban las vidas finaliza el juego
         */
        public void update() {
            if (LEFT) {
                this.x -= this.speed;
            }
            if (RIGHT) {
                this.x += this.speed;
            }
            
            if (SPACE) {
                if(misilandando==false){
                yMisil=y-15;
                xMisil=x+27;
                misilrec.height= misil.getHeight()+10;
                misilrec.width=misil.getWidth()+10;
                misilrec.x=xMisil;
                misilrec.y=yMisil;
                timer.start();
                sonido.DISPARO.play();
                }
                
            }
                      
            if(this.getBounds().x<=0){
                x=1;
            }
            if(this.getBounds().x>=890){
                x=889;
            }
            if(vida==0){
                muerte=true;
                
                JOptionPane.showMessageDialog(null, "Has Perdido","PERDISTE", JOptionPane.ERROR_MESSAGE);
               
                System.exit(0);
            }
      
            
        }
        
        /**
        * Crea un rectangulo alrededor del objeto para poder manejar las colisiones
        * @return el rectangulo correspondiente
        */
        public Rectangle getBounds() {
        return new Rectangle(x, y, image.getHeight(), image.getWidth());
    }
        
        /**
         * es el Handler del disparo
         */
        public void disparoHandler(){
                timer.stop();
                misilandando=false;
                xMisil=x;
                yMisil=780;
                misilrec.x= x;
                misilrec.y=780;
                aux=0;
                sonido.explosionenemigo.play();
            
            
        }
        /**
         * chequea si el rectangulo de la nave solapa el rectangulo de la gasolina
         * @param r es el rectangulo de la gasolina
         * @return true o false dependiendo si solapa o no
         */
        public boolean overlaps (Rectangle r) {
         return x < r.x + r.width && x + this.getBounds().width > r.x && y < r.y + r.height && y + this.getBounds().height > r.y;
}
        
        /**
         * TimerPer es la animacion de disparar de misil, llega hasta el final si no choca un enemigo o se detiene si colisiona un enemigo, gasolina o muro
         */
        class TimerPer implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            misilandando=true;
            yMisil-=12;
            misilrec.y=yMisil;
            
            aux++;
            if(aux>50){
                aux=0;
                misilandando=false;
                xMisil=x+27;
                yMisil=780;
                timer.stop();
            }
            
        }
    }
         
    }

