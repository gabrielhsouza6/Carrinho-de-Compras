package View;

import javax.swing.*;
import java.awt.*;

public class EstatisticasInfoLabelPanel extends JPanel {
    JPanel panel;
    JLabel label;

    public EstatisticasInfoLabelPanel(String stringData, Integer intData){
        panel = new JPanel();
        label = new JLabel(String.format("%s: %d", stringData, intData));

        label.setFont(new Font("SansSerif", Font.BOLD, 19));
        panel.setPreferredSize(new Dimension(290, 50));
        panel.add(label);
    }

    public JPanel getPanel() {
        return panel;
    }
}
