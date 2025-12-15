package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class DinheiroCompraPanel extends JPanel implements DocumentListener {
    JPanel panel;
    JTextField dinheiroTextF;
    JLabel dinheiroLabel;
    JLabel trocoLabel;
    JLabel totalLabel;
    Font defaultFont = new Font("Arial", Font.BOLD, 15);
    GridBagConstraints gbc = new GridBagConstraints();
    static float dinheiro;
    static float troco;

    public DinheiroCompraPanel(float total){
        dinheiro = 0;
        troco = 0;
        panel = new JPanel();
        dinheiroTextF = new JTextField();
        dinheiroLabel = new JLabel("Troco para:   R$ ");
        trocoLabel = new JLabel();
        totalLabel = new JLabel(String.format("-    Total:   R$ %.2f", total));
//        total = totalCompra;

        panel.setPreferredSize(new Dimension(450, 200));
        panel.setLayout(new GridBagLayout());
        dinheiroLabel.setFont(defaultFont);
        dinheiroLabel.setPreferredSize(new Dimension(200, 20));
        dinheiroLabel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        dinheiroTextF.setFont(new Font("Arial", Font.PLAIN, 15));
        dinheiroTextF.setPreferredSize(new Dimension(200, 20));
        totalLabel.setFont(defaultFont);
        totalLabel.setPreferredSize(new Dimension(400, 20));
        trocoLabel.setFont(defaultFont);
        trocoLabel.setPreferredSize(new Dimension(400, 20));
        try{
            dinheiro = Float.parseFloat(dinheiroTextF.getText());
            troco = dinheiro - total;
        }catch(Exception _){

        }
        trocoLabel.setText(String.format("Troco:   R$ %.2f",troco));
        dinheiroTextF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                dinheiro = 0;
                troco = 0;
                try{
                    dinheiro = Float.parseFloat(dinheiroTextF.getText());
                    troco = dinheiro - total;
                }catch(Exception exc){
                    JOptionPane.showMessageDialog(null, "Entrada Inválida", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                if(troco<0){
                    troco = 0;
                }
                trocoLabel.setText(String.format("Troco:   R$ %.2f",troco));
                panel.revalidate();
                panel.repaint();
                System.out.println("insert");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                dinheiro = 0;
                troco = 0;
                try{
                    dinheiro = Float.parseFloat(dinheiroTextF.getText());
                    troco = dinheiro - total;
                }catch(Exception _){

                }
                if(troco<0){
                    troco = 0;
                }
                trocoLabel.setText(String.format("Troco:   R$ %.2f",troco));
                panel.revalidate();
                panel.repaint();
                System.out.println("remove");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        //Label Dinheiro
        alterarGbc(0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(dinheiroLabel, gbc);

        //TextField Dinheiro
        alterarGbc(1, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(dinheiroTextF, gbc);
        alterarGbc(1, 1);
        panel.add(new JLabel("                                              "), gbc);

        //Label total
        alterarGbc(0, 1);
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, -44);
        panel.add(totalLabel, gbc);

        //hr
        alterarGbc(0, 2);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(new JSeparator(), gbc);

        //Label troco
        alterarGbc(0, 3);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, -29);
        panel.add(trocoLabel, gbc);

    }

    public JPanel getPanel() {
        return panel;
    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
//        layeredPane.remove(trocoLabel);
//        layeredPane.add(trocoLabel, gbc);
//        layeredPane.revalidate();
//        layeredPane.repaint();
//        System.out.println("insert");
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
//        layeredPane.remove(trocoLabel);
//        layeredPane.add(trocoLabel, gbc);
//        layeredPane.revalidate();
//        layeredPane.repaint();
//        System.out.println("remove");
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
