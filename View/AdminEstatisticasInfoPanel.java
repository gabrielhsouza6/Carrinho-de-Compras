package View;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

public class AdminEstatisticasInfoPanel extends JPanel {
    JPanel panel;
    JPanel graphPanel;
    JPanel labelsPanel;
    JPanel titlePanel;
    JLabel titleLabel;
    JPanel graphBarPanel;
    ArrayList<Integer> intDataList;
    ArrayList<String> stringDataList;

    public AdminEstatisticasInfoPanel(String title, ArrayList<Integer> intDataList, ArrayList<String> stringDataList){
        this.intDataList = intDataList;
        this.stringDataList = stringDataList;
        panel = new JPanel();
        graphPanel= new JPanel();
        labelsPanel = new JPanel();
        titlePanel = new JPanel();
        titleLabel = new JLabel(title);
        graphBarPanel = new JPanel();

        int maxStringLength = 0;
        for(String s : stringDataList){
            if(s.length()>maxStringLength){
                maxStringLength = s.length();
            }
        }

        panel.setPreferredSize(new Dimension(900, 335));
        graphPanel.setPreferredSize(new Dimension(550, 320));
        labelsPanel.setPreferredSize(new Dimension(300, 270));
        graphBarPanel.setPreferredSize(new Dimension(550, 240));
        graphBarPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setPreferredSize(new Dimension(550, 40));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));

        addDataToPanel();
        titlePanel.add(titleLabel);

        graphPanel.add(graphBarPanel);

        graphPanel.add(titlePanel);

        panel.add(graphPanel);
        panel.add(labelsPanel);

        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    public JPanel getPanel() {
        return panel;
    }

    public void addDataToPanel(){
        int total = 0;
        for(Integer intData : intDataList){
            total += intData;
        }

        for(int i=0; i<intDataList.size(); i++){
            graphBarPanel.add(new EstatisticasProgressBar(stringDataList.get(i), Math.round((intDataList.get(i)/(float)total)*100)).getPanel());
            labelsPanel.add(new EstatisticasInfoLabelPanel(stringDataList.get(i), intDataList.get(i)).getPanel());
        }
    }

}
