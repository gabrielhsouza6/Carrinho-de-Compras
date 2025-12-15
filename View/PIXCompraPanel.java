package View;

import javax.swing.*;
import java.awt.*;

public class PIXCompraPanel extends JPanel {
    JPanel panel;
    JLabel pixLogoLabel;
    JLabel qrCodeLabel;
    ImageIcon pixLogo = new ImageIcon("src/Imagens/Pix icon.png");
    ImageIcon qrCode = new ImageIcon("src/Imagens/pix code icon.png");

    public PIXCompraPanel(){
        panel = new JPanel();
        pixLogoLabel = new JLabel(pixLogo);
        qrCodeLabel = new JLabel(qrCode);

        panel.setPreferredSize(new Dimension(450, 200));
        panel.setLayout(new FlowLayout());
        pixLogoLabel.setPreferredSize(new Dimension(500, 50));
        qrCodeLabel.setPreferredSize(new Dimension(500, 140));

        panel.add(pixLogoLabel);
        panel.add(qrCodeLabel);
    }

    public JPanel getPanel() {
        return panel;
    }
}
