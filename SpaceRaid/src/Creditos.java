
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Creditos extends JPanel {

    private final BufferedImage image;
    public JButton menu;

    /**
     * Es el JPanel que contiene los creditos del juego
     * @throws IOException debido a que uso una BufferedImage para pintar el fondo
     */
    
    public Creditos() throws IOException {
        image = ImageIO.read(getClass().getResourceAsStream("imagenes/creditos.png"));
        this.setLayout(null);
        menu = new JButton("Regresar");
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JButton button = (JButton) ae.getSource();
                JPanel buttonPanel = (JPanel) button.getParent();
                JPanel cardPanel = (JPanel) buttonPanel.getParent();
                CardLayout layout = (CardLayout) cardPanel.getLayout();
                layout.first(cardPanel);

            }
        });
        
        menu.setBounds(470, 700, 100, 30);
        this.add(menu);

    }
    /**
     * Imprime la imagen de fondo con los creditos
     * @param g java.awt.Graphics
     */

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }
    
     /**
     * Inicializamos el JPanel con un tamaño de 1024*780 ya que el JFrame no
     * tiene tamaño en si
     * @return Dimension que es el tamaño de la ventana
     */

    @Override
    public Dimension getPreferredSize() {
        // because no components are added to the JPanel, we will have a default sizxe of 0,0 so we instead force the JPanel to a size we want
        return new Dimension(1024, 780);
    }
}
