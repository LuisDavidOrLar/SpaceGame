
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Menu extends JPanel {

    JButton jugar, instrucciones, creditos, salir, relleno1, relleno2;
    private final BufferedImage image;
    BufferedImage logos = ImageIO.read(getClass().getResourceAsStream("imagenes/logo.png"));
    JLabel logo;
    private boolean activo;
    /**
     * Es el constructor del Jpanel Menu, uso boxLayout para que aparezcan
     * los botones uniformemente
     * @param iniciar es para añadirle al boton de Jugar la funcionalidad
     * de iniciar el Thread del gameLoop
     * @throws IOException porque uso una BufferedImage para pintar el fondo
     */
    
    public Menu(JButton iniciar) throws IOException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        image = ImageIO.read(getClass().getResourceAsStream("imagenes/stars.png"));
        relleno1 = new JButton("");
        relleno1.setAlignmentX(CENTER_ALIGNMENT);
        this.add(relleno1);
        
        this.add(Box.createRigidArea(new Dimension(5, 300)));

        jugar = iniciar;
        jugar.setAlignmentX(CENTER_ALIGNMENT);
        this.add(jugar);
        jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JButton button = (JButton) ae.getSource();
                JPanel buttonPanel = (JPanel) button.getParent();
                JPanel cardPanel = (JPanel) buttonPanel.getParent();
                CardLayout layout = (CardLayout) cardPanel.getLayout();
                layout.next(cardPanel);

            }
        });

        this.add(Box.createRigidArea(new Dimension(5, 50)));
        
        instrucciones = new JButton("Instrucciones");
        instrucciones.setAlignmentX(CENTER_ALIGNMENT);
        this.add(instrucciones);
        instrucciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JButton button = (JButton) ae.getSource();
                JPanel buttonPanel = (JPanel) button.getParent();
                JPanel cardPanel = (JPanel) buttonPanel.getParent();
                CardLayout layout = (CardLayout) cardPanel.getLayout();
                layout.show(cardPanel, "3");

            }
        });

        this.add(Box.createRigidArea(new Dimension(5, 50)));

        creditos = new JButton("Creditos");
        creditos.setAlignmentX(CENTER_ALIGNMENT);
        this.add(creditos);

        creditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JButton button = (JButton) ae.getSource();
                JPanel buttonPanel = (JPanel) button.getParent();
                JPanel cardPanel = (JPanel) buttonPanel.getParent();
                CardLayout layout = (CardLayout) cardPanel.getLayout();
                layout.show(cardPanel, "4");

            }
        });

        this.add(Box.createRigidArea(new Dimension(5, 50)));

        salir = new JButton("Salir");
        salir.setAlignmentX(CENTER_ALIGNMENT);
        this.add(salir);

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);

            }
        });

    }

    @Override
    public void paintComponent(Graphics g) {

        
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(logos, 280, 30,logos.getWidth(),logos.getHeight(), null);
    }

    /**
     * Inicializamos el JPanel con un tamaño de 1024*780 ya que el JFrame no
     * tiene tamaño en si
     * @return Dimension que es el tamaño de la ventana
     */
    @Override
    public Dimension getPreferredSize() {
        
        return new Dimension(1024, 780);
    }

}
