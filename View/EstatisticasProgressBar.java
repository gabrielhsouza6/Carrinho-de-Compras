package View;

import javax.swing.*;
import java.awt.*;

public class EstatisticasProgressBar extends JPanel {
    JPanel panel;
    JPanel progressBarPanel;
    JProgressBar progressBar;
    JLabel label;

    public EstatisticasProgressBar(String stringData, int intData){
        panel = new JPanel();
        progressBarPanel = new JPanel();
        label = new JLabel(stringData);
        progressBar = new JProgressBar();

        panel.setPreferredSize(new Dimension(stringData.length()*10, 230));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        progressBarPanel.setPreferredSize(new Dimension(stringData.length()*10-10, 185));
        progressBar.setPreferredSize(new Dimension(20, 180));
        progressBar.setValue(intData);
        progressBar.setOrientation(SwingConstants.VERTICAL);
        label.setFont(new Font("SansSerif", Font.BOLD, 15));
//        label.setPreferredSize(new Dimension(stringData.length()*10, 30));

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setPreferredSize(new Dimension(540, 20));

        progressBarPanel.add(progressBar);
        panel.add(progressBarPanel);
        panel.add(separator);
        panel.add(label);

//        progressBarPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
//        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));

    }

    public JPanel getPanel() {
        return panel;
    }
}
