package View;

import Model.DAO.EnderecoDAO;
import Exceptions.ContaExceptions;
import Model.RN.EnderecoRN;
import Model.VO.Endereco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class EnderecosGUI extends JPanel implements ActionListener{
    static JPanel panel;
    static JComboBox<String> comboBox;
    static JPanel enderecoPanel;
    JButton cadastrarButton;
    JButton alterarButton;
    JButton excluirButton;
    ArrayList<Endereco> enderecos;
    static String[] enderecosStrings;
    static GridBagConstraints gbc = new GridBagConstraints();
    static int alterarStatus;

    public EnderecosGUI() throws ContaExceptions.UsuarioInexistenteException, SQLException {
        if(MenuPrincipal.getUsuario()==null){
            throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário vinculado");
        }

        enderecos = EnderecoDAO.getEnderecosClienteDAO(MenuPrincipal.getUsuario());
        enderecosStrings = new String[enderecos.size()];
        for(int i = 0; i< enderecosStrings.length; i++){
            enderecosStrings[i] = enderecos.get(i).toString();
        }

        alterarStatus = 0;

        panel = new JPanel();
        comboBox = new JComboBox<>(enderecosStrings);
        enderecoPanel = new JPanel();
        cadastrarButton = new JButton("Cadastrar Novo Endereço");
        alterarButton = new JButton("Alterar Endereço");
        excluirButton = new JButton("Excluir Endereço");

        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(250, 550));
        comboBox.setPreferredSize(new Dimension(420, 25));
        comboBox.addActionListener(this);
        enderecoPanel.setPreferredSize(new Dimension(400, 200));
        cadastrarButton.setPreferredSize(new Dimension(250, 30));
        cadastrarButton.addActionListener(this);
        cadastrarButton.setFocusable(false);
        alterarButton.setPreferredSize(new Dimension(250, 30));
        alterarButton.addActionListener(this);
        alterarButton.setFocusable(false);
        excluirButton.setPreferredSize(new Dimension(250, 30));
        excluirButton.addActionListener(this);
        excluirButton.setFocusable(false);
        if(comboBox.getItemCount()>0){
            comboBox.setSelectedIndex(0);
        }
        enderecoPanel.setOpaque(true);
        enderecoPanel.setBackground(Color.lightGray);

//        enderecoPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));

        //comboBox
        alterarGbc(0, 0);
        gbc.insets = new Insets(0, 0, 0, -5);
        panel.add(comboBox, gbc);

        //Panel Endereco
        alterarGbc(0, 1);
        gbc.insets = new Insets(60, 0, 40, 0);
        panel.add(enderecoPanel, gbc);

        //Buttons
        alterarGbc(0, 2);
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(cadastrarButton, gbc);
        gbc.insets = new Insets(0, -540, 0, 0);
        panel.add(alterarButton, gbc);
        gbc.insets = new Insets(0, 0, 0, -540);
        panel.add(excluirButton, gbc);

        if(CadastroEnderecoGUI.adressAdded){
            comboBox.setSelectedIndex(comboBox.getItemCount()-1);
            CadastroEnderecoGUI.adressAdded = false;
        }

    }

    public static void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==comboBox){
            for(Endereco end : enderecos){
                if(Objects.equals(end.toString(), comboBox.getSelectedItem())){
                    System.out.println("Trigerred");
                    alterarEnderecoPanel(new EnderecoPanel(end).getPanel());
                }
            }
        }
        if(e.getSource()==alterarButton){
            for(Endereco end : enderecos){
                if(Objects.equals(end.toString(), comboBox.getSelectedItem())){
                    System.out.println("Trigerred");
                    if (alterarStatus % 2 == 0) {
                        alterarEnderecoPanel(new AlterarEnderecoPanel(end).getPanel());
                        alterarButton.setText("Finalizar Alteração");
                        alterarStatus++;
                    } else {
                        try {
                            int old_enderecoCount = enderecos.size();
                            AlterarEnderecoPanel.alterarDados();
                            if(old_enderecoCount == enderecos.size()){
                                atualizarComboBox(comboBox.getSelectedIndex());
                            }else{
                                atualizarComboBox(comboBox.getSelectedIndex()-1);
                            }
                            alterarEnderecoPanel(new EnderecoPanel(AlterarEnderecoPanel.endereco).getPanel());
                            alterarButton.setText("Alterar Endereço");
                            alterarStatus++;
                        }catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }
        if(e.getSource()==excluirButton){
            for(Endereco end : enderecos){
                if(Objects.equals(end.toString(), comboBox.getSelectedItem()) && alterarStatus%2 == 0){
                    if (JOptionPane.showConfirmDialog(panel, "Deseja excluir esse endereço?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                        try {
                            EnderecoRN.deleteEnderecoRN(end, MenuPrincipal.getUsuario());
                            atualizarComboBox(comboBox.getSelectedIndex()-1);
                            JOptionPane.showMessageDialog(panel, "Endereco excluído om sucesso", "", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }
        if(e.getSource()==cadastrarButton){
            new CadastroEnderecoGUI();
        }
    }

    public void alterarEnderecoPanel(JPanel newPanel){
        panel.remove(enderecoPanel);
        enderecoPanel = newPanel;

        //Panel Endereco
        alterarGbc(0, 1);
        gbc.insets = new Insets(60, 0, 40, 0);
        panel.add(enderecoPanel, gbc);

        enderecoPanel.revalidate();
        enderecoPanel.repaint();
        enderecoPanel.setOpaque(true);
        enderecoPanel.setBackground(Color.lightGray);
        panel.revalidate();
        panel.repaint();
    }

    public void atualizarComboBox(int selectIndex) throws SQLException {
        try {
            enderecos = EnderecoDAO.getEnderecosClienteDAO(MenuPrincipal.getUsuario());
            enderecosStrings = new String[enderecos.size()];
            for (int i = 0; i < enderecosStrings.length; i++) {
                enderecosStrings[i] = enderecos.get(i).toString();
            }
            comboBox.removeAllItems();
            for(String s : enderecosStrings){
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
                enderecoPanel.removeAll();
            }

            panel.revalidate();
            panel.repaint();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
