package View;

import Model.DAO.CartaoDAO;
import Model.DAO.CartoesClientesDAO;
import Model.VO.Cartao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class CartaoCompraPanel extends JPanel implements ActionListener {
    JPanel panel;
    JPanel cartaoComboBoxPanel;
    JLabel cartaoLabel;
    static JComboBox<String> cartaoComboBox;
    JPanel cartaoPanel;
    ArrayList<Cartao> cartoes = CartoesClientesDAO.getCartoesCliente(MenuPrincipal.getUsuario());
    String[] cartoesStrings;


    public CartaoCompraPanel() throws SQLException {
        panel = new JPanel();
        cartaoComboBoxPanel = new JPanel();
        cartaoLabel = new JLabel("Cartão: ");
        cartaoPanel = new JPanel();

        cartoesStrings = new String[cartoes.size()];
        for(int i = 0; i< cartoesStrings.length; i++){
            cartoesStrings[i] = cartoes.get(i).toString();
        }

        cartaoComboBox = new JComboBox<>(cartoesStrings);

        panel.setPreferredSize(new Dimension(450, 200));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        cartaoComboBoxPanel.setPreferredSize(new Dimension(450, 40));
        cartaoComboBoxPanel.setLayout(new FlowLayout());
        cartaoComboBox.setPreferredSize(new Dimension(300, 17));
        cartaoComboBox.addActionListener(this);
        cartaoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        cartaoPanel.setPreferredSize(new Dimension(250, 150));
        if(!cartoes.isEmpty()){
            cartaoComboBox.setSelectedIndex(0);
        }

        cartaoComboBoxPanel.add(cartaoLabel);
        cartaoComboBoxPanel.add(cartaoComboBox);

        panel.add(cartaoComboBoxPanel);
        panel.add(cartaoPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cartaoComboBox){
            for(Cartao c : cartoes){
                if(Objects.equals(c.toString(), cartaoComboBox.getSelectedItem())){
                    alterarCartaoPanel(new CartaoPanel(c).getPanel());
                }
            }
        }
    }

    public void alterarCartaoPanel(JPanel newPanel){
        panel.remove(cartaoPanel);
        cartaoPanel = newPanel;
        panel.add(cartaoPanel);
        cartaoPanel.revalidate();
        cartaoPanel.repaint();
        panel.revalidate();
        panel.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }
}
