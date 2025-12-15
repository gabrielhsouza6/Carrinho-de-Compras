package View;

import Model.DAO.CartaoDAO;
import Exceptions.ContaExceptions;
import Model.DAO.CartoesClientesDAO;
import Model.RN.CartaoRN;
import Model.RN.CartoesClienteRN;
import Model.VO.Cartao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class CartoesGUI extends JPanel implements ActionListener {
    JPanel panel;
    static JComboBox comboBox;
    JPanel cartaoPanel;
    JButton cadastrarButton;
    JButton alterarButton;
    JButton excluirButton;
    ArrayList<Cartao> cartoes;
    String[] cartoesStrings;
    GridBagConstraints gbc = new GridBagConstraints();
    static int alterarStatus;
    static String tipo = "";
    JPanel buttonsPanel;
    ButtonGroup tipoButtons;
    JRadioButton creditoButton;
    JRadioButton debitoButton;

    public CartoesGUI() throws ContaExceptions.UsuarioInexistenteException, SQLException {
        if(MenuPrincipal.getUsuario()==null){
            throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário vinculado");
        }
        cartoes = CartoesClientesDAO.getCartoesCliente(MenuPrincipal.getUsuario());
        alterarStatus = 0;

        cartoesStrings = new String[cartoes.size()];
        for(int i = 0; i< cartoesStrings.length; i++){
            cartoesStrings[i] = cartoes.get(i).toString();
        }

        panel = new JPanel();
        comboBox = new JComboBox(cartoesStrings);
        cartaoPanel = new JPanel();
        cadastrarButton = new JButton("Cadastrar Novo Cartão");
        alterarButton = new JButton("Alterar Cartão");
        excluirButton = new JButton("Excluir Cartão");
        creditoButton = new JRadioButton("Crédito");
        debitoButton = new JRadioButton("Débito");
        tipoButtons = new ButtonGroup();
        buttonsPanel = new JPanel();

        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(250, 550));
        comboBox.setPreferredSize(new Dimension(350, 22));
        comboBox.addActionListener(this);
        cartaoPanel.setPreferredSize(new Dimension(250, 150));
        cadastrarButton.setPreferredSize(new Dimension(250, 30));
        cadastrarButton.addActionListener(this);
        alterarButton.setPreferredSize(new Dimension(250, 30));
        alterarButton.addActionListener(this);
        excluirButton.setPreferredSize(new Dimension(250, 30));
        excluirButton.addActionListener(this);
        creditoButton.setFont(new Font("Arial", Font.PLAIN, 15));
        creditoButton.addActionListener(this);
        debitoButton.setFont(new Font("Arial", Font.PLAIN, 15));
        debitoButton.addActionListener(this);
        tipoButtons.add(creditoButton);
        tipoButtons.add(debitoButton);
        buttonsPanel.setPreferredSize(new Dimension(250, 30));
        buttonsPanel.add(creditoButton);
        buttonsPanel.add(debitoButton);
        buttonsPanel.setLayout(new FlowLayout());

        if(comboBox.getItemCount()>0){
            comboBox.setSelectedIndex(0);
        }

        //comboBox
        alterarGbc(0, 0);
        gbc.insets = new Insets(0, 0, 0, -5);
        panel.add(comboBox, gbc);

        //Panel cartao
        alterarGbc(0, 1);
        gbc.insets = new Insets(70, 0, 40, 0);
        panel.add(cartaoPanel, gbc);


        //Buttons
        alterarGbc(0, 2);
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(cadastrarButton, gbc);
        gbc.insets = new Insets(0, -540, 0, 0);
        panel.add(alterarButton, gbc);
        gbc.insets = new Insets(0, 0, 0, -540);
        panel.add(excluirButton, gbc);

        if(CadastroCartaoGUI.cardAdded){
            comboBox.setSelectedIndex(comboBox.getItemCount()-1);
            CadastroCartaoGUI.cardAdded = false;
        }

    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==creditoButton){
            tipo = "Crédito";
        }
        if(e.getSource()==debitoButton){
            tipo = "Débito";
        }
        if(e.getSource()==comboBox){
            for(Cartao c : cartoes){
                if(Objects.equals(comboBox.getSelectedItem(), c.toString())){
                    System.out.println("Trigerred");
                    alterarCartaoPanel(new CartaoPanel(c).getPanel());
                }
            }
        }
        if(e.getSource()==alterarButton){
            for(Cartao c : cartoes){
                if(Objects.equals(comboBox.getSelectedItem(), c.toString())) {
                    System.out.println("Trigerred");
                    if (alterarStatus % 2 == 0) {
                        alterarCartaoPanel(new AlterarCartaoPanel(c).getPanel());
                        addButtonsAlterar();
                        alterarButton.setText("Finalizar Alteração");
                        alterarStatus++;
                    } else {
                        try {
                            int old_cartaoCount = cartoes.size();
                            AlterarCartaoPanel.alterarDados();
                            if (old_cartaoCount == cartoes.size()) {
                                atualizarComboBox(comboBox.getSelectedIndex());
                            } else {
                                atualizarComboBox(comboBox.getSelectedIndex() - 1);
                            }
                            removerButtonsAlterar();
                            alterarCartaoPanel(new CartaoPanel(AlterarCartaoPanel.cartao).getPanel());
                            alterarButton.setText("Alterar Cartão");
                            alterarStatus++;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Dados Inconsistentes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }
        if(e.getSource()==excluirButton){
            for(Cartao c : cartoes){
                if(Objects.equals(c.toString(), comboBox.getSelectedItem()) && alterarStatus%2 == 0){
                    if (JOptionPane.showConfirmDialog(panel, "Deseja excluir esse cartão?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                        try {
                            CartaoRN.deleteCartaoRN(MenuPrincipal.getUsuario(), c);
                            atualizarComboBox(comboBox.getSelectedIndex()-1);
                            JOptionPane.showMessageDialog(panel, "Cartão excluído om sucesso", "", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException | ContaExceptions.UsuarioInexistenteException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
        if(e.getSource()==cadastrarButton){
            new CadastroCartaoGUI();
        }
    }

    public void alterarCartaoPanel(JPanel newPanel){
        panel.remove(cartaoPanel);
        cartaoPanel = newPanel;

        //Panel cartao
        alterarGbc(0, 1);
        gbc.insets = new Insets(70, 0, 40, 0);
        panel.add(cartaoPanel, gbc);

        cartaoPanel.revalidate();
        cartaoPanel.repaint();
        panel.revalidate();
        panel.repaint();
    }

    public void atualizarComboBox(int selectIndex){
        try {
            cartoes = CartoesClientesDAO.getCartoesCliente(MenuPrincipal.getUsuario());
            cartoesStrings = new String[cartoes.size()];
            for (int i = 0; i < cartoesStrings.length; i++) {
                cartoesStrings[i] = cartoes.get(i).toString();
            }
            comboBox.removeAllItems();
            for(String s : cartoesStrings){
                comboBox.addItem(s);
            }

            panel.remove(comboBox);

            //comboBox
            alterarGbc(0, 0);
            gbc.insets = new Insets(0, 0, 0, -5);
            panel.add(comboBox, gbc);

            if(comboBox.getItemCount()>0){
                comboBox.setSelectedIndex(selectIndex);
            }else{
                cartaoPanel.removeAll();
            }

            panel.revalidate();
            panel.repaint();
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addButtonsAlterar(){
        panel.remove(alterarButton);
        panel.remove(excluirButton);
        panel.remove(cadastrarButton);

        //Buttons Tipo
        alterarGbc(0, 2);
        gbc.insets = new Insets(-40, 0, 0, 0);
        panel.add(buttonsPanel, gbc);

        for(Cartao c : cartoes){
            if(Objects.equals(comboBox.getSelectedItem(), c.toString())){
                if(Objects.equals(c.getTipo(), "Crédito")){
                    creditoButton.setSelected(true);
                    debitoButton.setSelected(false);
                    tipo = "Crédito";
                }else if(Objects.equals(c.getTipo(), "Débito")){
                    creditoButton.setSelected(false);
                    debitoButton.setSelected(true);
                    tipo = "Débito";
                }
            }
        }

        //Buttons
        alterarGbc(0, 3);
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(cadastrarButton, gbc);
        gbc.insets = new Insets(0, -540, 0, 0);
        panel.add(alterarButton, gbc);
        gbc.insets = new Insets(0, 0, 0, -540);
        panel.add(excluirButton, gbc);

        panel.revalidate();
        panel.repaint();
    }

    public void removerButtonsAlterar(){
        panel.remove(alterarButton);
        panel.remove(excluirButton);
        panel.remove(cadastrarButton);
        panel.remove(buttonsPanel);

        //Buttons
        alterarGbc(0, 2);
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(cadastrarButton, gbc);
        gbc.insets = new Insets(0, -540, 0, 0);
        panel.add(alterarButton, gbc);
        gbc.insets = new Insets(0, 0, 0, -540);
        panel.add(excluirButton, gbc);

        panel.revalidate();
        panel.repaint();
    }
}
