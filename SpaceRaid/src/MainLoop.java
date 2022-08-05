
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class MainLoop {

    private Escenario scene;
    private Menu menu;
    private Instrucciones instruccion;
    private Creditos creditos;
    private Nave player;
    private Thread gameLoop;
    private boolean isRunning,activo;
    JButton iniciar;
    CardLayout card;
    JPanel cards;
    Sonidos sonido; 
    
    public MainLoop() {
        sonido= new Sonidos();
        sonido.inicio.play();
        createAndShowUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainLoop::new);
    }

    /**
     * En esta Funcion creamos nuestro frame y activamos nuestro
     * motor de juego junto con el CardLayout y algunos botones
     */
    private void createAndShowUI() {
        JFrame frame = new JFrame("Space Raid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jbutton salir
        try {
            player = new Nave(ImageIO.read(getClass().getResourceAsStream("imagenes/nave.png")), ImageIO.read(getClass().getResourceAsStream("imagenes/bala.png")));
        } catch (IOException ex) {
            Logger.getLogger(MainLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
        card = new CardLayout();
        cards = new JPanel(card);
       activo=true;
        this.scene = new Escenario();
         iniciar= new JButton("Jugar");
         iniciar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
             isRunning = true;
             activo=false;
             sonido.inicio.stop();
             scene.activo=true;
             gameLoop.start(); 
            }           
        });
         
        try {
            this.instruccion = new Instrucciones();
            this.creditos = new Creditos();
            this.menu = new Menu(iniciar);
        } catch (IOException ex) {
            Logger.getLogger(MainLoop.class.getName()).log(Level.SEVERE, null, ex);
        }

        cards.add(menu, "1");
        cards.add(scene, "2");
        cards.add(instruccion, "3");
        cards.add(creditos, "4");
        this.scene.add(player);

        this.addKeyBindings();
        this.setupGameLoop();

        frame.add(cards);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

       
        
         
        
       
    }

    /**
     * Este metodo es el que realmente crea el GameLoop
     * Siempre estará en un Thread aparte para no bloquear
     * la interfaz gráfica
     */
    private void setupGameLoop() {
        
        gameLoop = new Thread(() -> {
           
            while (isRunning) {
                
                this.scene.update();
                
                this.scene.repaint();
                
                if(activo==true && !sonido.inicio.isPlaying()){
                    sonido.inicio.play();
                }
         
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                }
            }
        });
    }
    
    /**
     * Esta funcion añade los KeyBindings y los añadimos a nuestro JPanel scene
     * para mover nuestra nave y acelerar
     */

    private void addKeyBindings() {
       
        this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "A pressed");
        this.scene.getActionMap().put("A pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.LEFT = true;
            }
        });
        this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "A released");
        this.scene.getActionMap().put("A released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.LEFT = false;
            }
        });
        this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "D pressed");
        this.scene.getActionMap().put("D pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.RIGHT = true;
            }
        });
        this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "D released");
        this.scene.getActionMap().put("D released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.RIGHT = false;
            }
        });

        this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "Up pressed");
        this.scene.getActionMap().put("Up pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.UP = true;
            }
        });

        this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "Up released");
        this.scene.getActionMap().put("Up released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.UP = false;
            }
        });

        this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "Space pressed");
        this.scene.getActionMap().put("Space pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.SPACE = true;
            }
        });

        this.scene.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "Space released");
        this.scene.getActionMap().put("Space released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.SPACE = false;
            }
        });

    }

}
