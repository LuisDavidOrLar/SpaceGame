
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.YELLOW;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Escenario extends JPanel {

    private Enemigos tempEnemigo,tempEnemigo2;
    private Nave sprites;
    private Gasolina tempGasolina;
    private Muro tempMuro;
    LinkedList<Enemigos> enemigo = new LinkedList<>();
    LinkedList<Gasolina> gasolina = new LinkedList<>();
    LinkedList<Muro> muro = new LinkedList<>();
    public int nivel = 1, yOffset = 0, yDelta = 0, vidamuro,auxtimer;
    private int randomNum,score,barraGasolina;
    public Random rand;
    public Boolean UP = false,activo,gasolinacheck;
    public Timer spawnEnemigo,timerGasolina,timerGasolinaplus;
    public Background mapas;
    Sonidos sonido;

    /**
     * Es el constructor del JPanel escenario, ya que el JPanel hace repaint
     * en el GameLoop ignoramos el Repaint propio para no causar problemas.
     * Inicializamos 2 timers, uno para el spawn de enemigos y otro para el
     * consumo de gasolina
     */
    public Escenario() {
        
        this.activo=false;
        vidamuro=1;
        gasolinacheck=false;
        sonido=new Sonidos();
        barraGasolina=250;
        rand= new Random();
        this.setIgnoreRepaint(true);
        score=0;
        try {
            mapas = new Background(nivel);

        } catch (IOException ex) {
            Logger.getLogger(Escenario.class.getName()).log(Level.SEVERE, null, ex);
        }

        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yOffset += yDelta;
                if (yOffset > getHeight()) {
                    yOffset = 0;
                }
                
            }
        });
        timer.start();

        spawnEnemigo = new Timer(5000, new TimerSpawn());
        timerGasolina = new Timer(250, new TimerFuel());
        timerGasolinaplus = new Timer(250, new TimerFuelPlus());
        timerGasolina.start();
        
    }

    /**
     * paintComponent se encarga de pintar todos los objetos en el mapa
     * Para imprimir los objetos de las LinkedList usamos un for con un auxiliar del mismo tipo del objeto
     * @param g java.awt.Graphics
     */
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        mapas.render(g, yOffset, this.getHeight());
        // this method gets called on Scene#repaint in our game loop and we then render each in our game
        
        
        g.setColor(RED);
        g.fillRect(670, 732, 250, 30);
        
        g.setColor(YELLOW);
        g.fillRect(670, 732, barraGasolina, 30);
        
        for (int i = 0; i < enemigo.size(); i++) {
            tempEnemigo = enemigo.get(i);
            tempEnemigo.render(g);
        }
        for (int i = 0; i < gasolina.size(); i++) {
            tempGasolina = gasolina.get(i);
            tempGasolina.render(g);
        }
        for (int i = 0; i < muro.size(); i++) {
            tempMuro = muro.get(i);
            tempMuro.render(g);
        }
        sprites.render(g2d);
        g.setColor(WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20)); 
        g.drawString("Score: "+String.valueOf(score), 500, 15);
        g.drawString("Vida: "+String.valueOf(sprites.vida), 100, 750);
    }

    @Override
    public Dimension getPreferredSize() {
        // because no components are added to the JPanel, we will have a default sizxe of 0,0 so we instead force the JPanel to a size we want
        return new Dimension(1024, 780);
    }

    /**
     * Añade una nave al juego
     * @param go es la nave que queremos añadir
     */
    public void add(Nave go) {
        this.sprites = go;
    }

    /**
     * Este metodo se llama en el GameLoop y es el que nos va a actualizar tanto
     * como el movimiento de los personajes y objetos como el sistema de Vidas
     */
    public void update() {
       
         if(sprites.muerte==true){
           sonido.background.stop();
           sonido.gameover.play();
        }  
        
       if(!sonido.background.isPlaying() && sprites.muerte==false ){
           
           sonido.background.play();
       } 
       
        
        if(activo==true){
            spawnEnemigo.start();
            activo=false;
        }
        if (!sprites.getBounds().intersects(mapas.pared1) && !sprites.getBounds().intersects(mapas.pared2)) {
            sprites.update();
        } else if (sprites.getBounds().intersects(mapas.pared1)) {
            sprites.x = 460;
            sprites.vida--;
        } else if (sprites.getBounds().intersects(mapas.pared2)) {
            sprites.x = 460;
            sprites.vida--;

        }
        
        
        if (UP) {
            yDelta = 10;
        } else {
            yDelta = 5;
        }

        for (int i = 0; i < enemigo.size(); i++) {
            tempEnemigo = enemigo.get(i);
            tempEnemigo.update(mapas);
            
        }
        
        for (int i = 0; i < gasolina.size(); i++) {
            tempGasolina = gasolina.get(i);
            
            tempGasolina.update();
            
            
            
             gasolinacheck = sprites.overlaps(tempGasolina.getBounds());
             if(gasolinacheck){
                 timerGasolina.stop();
                 timerGasolinaplus.start();
             }
                 
             if(tempGasolina.yPos>750){
              removeGas(tempGasolina);  
            }
              if(tempGasolina.getBounds().intersects(sprites.misilrec)){
          
                sprites.disparoHandler();
                score-=50;
                removeGas(tempGasolina);  
            }
      
        }
        
         for (int i = 0; i < muro.size(); i++) {
            tempMuro = muro.get(i);
            tempMuro.update();
            if(sprites.misilrec.intersects(tempMuro.getBounds())){
                
                sprites.disparoHandler();
                score+=100;
                if(tempMuro.vida<=1){
                   vidamuro++;
                   removeMuro(tempMuro);
                } else{
                    tempMuro.vida--;
                } 
            }
             if(tempMuro.getBounds().intersects(sprites.getBounds())){
                score-=100;
                sprites.vida--;
                sonido.explosionnave.play();
                removeMuro(tempMuro); 
        }
            
        }
       
        
        for (int i = 0; i < enemigo.size(); i++) {
            tempEnemigo = enemigo.get(i);
            if(i>0){
                tempEnemigo2= enemigo.get(i-1);
                if(tempEnemigo.getBounds().intersects(tempEnemigo2.getBounds())){
                    tempEnemigo.derecha = !tempEnemigo.derecha;
                    tempEnemigo.izquierda = !tempEnemigo.izquierda;
                    tempEnemigo2.derecha = !tempEnemigo.derecha;
                    tempEnemigo2.izquierda = !tempEnemigo.izquierda;
                }
            }
            if(tempEnemigo.yPos>750){
              removeEnemigos(tempEnemigo);  
            }
            
            if(tempEnemigo.getBounds().intersects(sprites.misilrec)){
                System.out.println("HOLA");
                sprites.disparoHandler();
                score+=50;
                removeEnemigos(tempEnemigo);  
            }
            
            if(tempEnemigo.getBounds().intersects(sprites.getBounds())){
                score-=50;
                sprites.vida--;
                sonido.explosionnave.play();
                removeEnemigos(tempEnemigo); 
        }
            
            
        }
        
        if(barraGasolina<=0){
            sprites.vida--;
            sonido.explosionnave.play();
            barraGasolina=250;
        }
        
    }

    /**
     * Este metodo añade a la LinkedList de enemigos, 1 enemigo a la vez
     * @param block es el enemigo que queremos añadir
     */
    public void addEnemigos(Enemigos block) {
        enemigo.add(block);
    }
    /**
     * Este metodo añade a la LinkedList de gasolina, 1 gasolina a la vez
     * @param block es el bidon de gasolina que queremos añadir
     */
    public void addGas(Gasolina block) {
        gasolina.add(block);
    }
    /**
     * Este metodo añade a la LinkedList de Muros, 1 muro a la vez
     * @param block es el muro que queremos añadir
     */
    public void addMuro(Muro block) {
        muro.add(block);
    }
    
    /**
     * Este metodo remueve de la LinkedList de enemigos, 1 enemigo a la vez
     * @param block es el enemigo que queremos eliminar
     */

    public void removeEnemigos(Enemigos block) {
        enemigo.remove(block);
    }
    
    /**
     * Este metodo remueve de la LinkedList de gasolina, 1 gasolina a la vez
     * @param block es la gasolina que queremos eliminar
     */

    
    public void removeGas(Gasolina block) {
        gasolina.remove(block);
    }
    
    /**
     * Este metodo remueve de la LinkedList de muros, 1 muro a la vez
     * @param block es el muro que queremos eliminar
     */

    
    public void removeMuro(Muro block) {
        muro.remove(block);
    }
    
    /**
     * TimerSpawn es nuestro generador de enemigos de manera aleatoria
     * Se ejecuta cada 2 a 4.5 segundos de manera aleatoria
     * Genera por probabilidad 1 enemigo, 1 gasolina o 1 muro, en ese mismo 
     * orden de probabilidad
     */

    class TimerSpawn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
             randomNum = rand.nextInt(4500 - 2000 + 1) + 2000;
            
            
            if( new Random().nextDouble() <= 0.4 ) {  //you might want to cache the Random instance
               addGas(new Gasolina(mapas.pared1.width,mapas.pared2.width));
               
            } else if(new Random().nextDouble() <= 0.08){
                addMuro(new Muro(vidamuro,mapas.pared1.width,mapas.pared2.width));
            }else{
                addEnemigos(new Enemigos(mapas.pared1.width,mapas.pared2.width));
                spawnEnemigo.setDelay(randomNum);
            }

        }
    }
    
    /**
     * TimerFuel es el timer que me disminuye la gasolina en 1 cada 250 milisegundos
     */

    class TimerFuel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
      
                barraGasolina--;

            
        }
        
    }
    
    class TimerFuelPlus implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            
            
           if (auxtimer>=8){
               auxtimer=0;
               timerGasolina.start();
               timerGasolinaplus.stop();
           }
                barraGasolina+=3;
                if(barraGasolina>=247){
                    barraGasolina=247;
                }
                auxtimer++;
            
            
        }
        
    }
    
    
}
