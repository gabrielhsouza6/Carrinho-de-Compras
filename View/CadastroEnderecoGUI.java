package View;

import Model.DAO.EnderecoDAO;
import Exceptions.ContaExceptions;
import Model.RN.EnderecoRN;
import Model.VO.Endereco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

public class CadastroEnderecoGUI extends JFrame implements ActionListener {
    static JFrame frame;
    JLabel titleLabel;
    JPanel ruaPanel;
    JPanel bairroPanel;
    JPanel numeroPanel;
    JPanel cidadePanel;
    JPanel estadoPanel;
    JLabel labelRua;
    JLabel labelBairro;
    JLabel labelNumero;
    JLabel labelCidade;
    JLabel labelEstado;
    JTextField textfRua;
    JTextField textfBairro;
    JTextField textfNumero;
    JTextField textfCidade;
    JComboBox<String> comboBoxEstado;
    String[] estados = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    JButton submitButton;
    GridBagConstraints gbc = new GridBagConstraints();
    Font textfFont = new Font("Arial", Font.PLAIN, 15);
    Font labelFont = new Font("Arial", Font.BOLD, 15);
    String estado = "AC";
    static boolean adressAdded = false;


    public CadastroEnderecoGUI(){
        //Inicialização
        frame = new JFrame("Cadastro");
        frame.setSize(650, 360);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        titleLabel = new JLabel("Cadastro");
        labelRua = new JLabel("Rua: ");
        labelBairro = new JLabel("Bairro: ");
        labelNumero = new JLabel("Numero: ");
        labelCidade = new JLabel("Cidade: ");
        labelEstado = new JLabel("Estado: ");
        textfRua = new JTextField();
        textfBairro = new JTextField();
        textfNumero = new JTextField();
        textfCidade = new JTextField();
        comboBoxEstado = new JComboBox<>(estados);
        submitButton = new JButton("Confirmar");
        ruaPanel = new JPanel();
        bairroPanel = new JPanel();
        numeroPanel = new JPanel();
        cidadePanel = new JPanel();
        estadoPanel = new JPanel();

//        titleLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        //Alterações
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setPreferredSize(new Dimension(150, 30));
        labelRua.setFont(labelFont);
        textfRua.setPreferredSize(new Dimension(300, 17));
        textfRua.setFont(textfFont);
        labelBairro.setFont(labelFont);
        labelNumero.setFont(labelFont);
        labelCidade.setFont(labelFont);
        textfCidade.setFont(textfFont);
        textfCidade.setPreferredSize(new Dimension(300, 17));
        textfBairro.setPreferredSize(new Dimension(300, 17));
        textfBairro.setFont(textfFont);
        submitButton.setPreferredSize(new Dimension(120, 35));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.addActionListener(this);
        labelEstado.setFont(labelFont);
        textfNumero.setPreferredSize(new Dimension(300, 17));
        textfNumero.setFont(textfFont);
        comboBoxEstado.setFont(textfFont);
        comboBoxEstado.setPreferredSize(new Dimension(200, 25));
        comboBoxEstado.addActionListener(this);
        ruaPanel.add(labelRua);
        ruaPanel.add(textfRua);
        ruaPanel.setPreferredSize(new Dimension(600, 30));
        ruaPanel.setLayout(new FlowLayout());
        bairroPanel.add(labelBairro);
        bairroPanel.add(textfBairro);
        bairroPanel.setPreferredSize(new Dimension(600, 30));
        bairroPanel.setLayout(new FlowLayout());
        numeroPanel.add(labelNumero);
        numeroPanel.add(textfNumero);
        numeroPanel.setPreferredSize(new Dimension(600, 30));
        numeroPanel.setLayout(new FlowLayout());
        cidadePanel.add(labelCidade);
        cidadePanel.add(textfCidade);
        cidadePanel.setPreferredSize(new Dimension(600, 30));
        cidadePanel.setLayout(new FlowLayout());
        estadoPanel.add(labelEstado);
        estadoPanel.add(comboBoxEstado);
        estadoPanel.setPreferredSize(new Dimension(600, 30));
        estadoPanel.setLayout(new FlowLayout());

        //Label title
        alterarGbc(0, 0);
        gbc.insets = new Insets(-10, 0, 20, -50);
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        frame.add(titleLabel, gbc);

        //Panel rua
        alterarGbc(0, 1);
        gbc.insets = new Insets(0, -5, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(ruaPanel, gbc);

        //Panel Bairro
        alterarGbc(0, 2);
        gbc.insets = new Insets(0, -30, 0, 0);
        frame.add(bairroPanel, gbc);

        //Panel Numero
        alterarGbc(0 ,3);
        gbc.insets = new Insets(0, -30, 0, 0);
        frame.add(numeroPanel, gbc);

        //Panel Cidade
        alterarGbc(0, 4);
        gbc.insets = new Insets(0, -20, 0, 0);
        frame.add(cidadePanel, gbc);

        //Panel Estado
        alterarGbc(0, 5);
        gbc.insets = new Insets(0, -30, 0, 0);
        frame.add(estadoPanel, gbc);

        //Button
        alterarGbc(0, 6);
        gbc.insets = new Insets(25, 0, 0, 0);
        gbc.fill = GridBagConstraints.VERTICAL;
        frame.add(submitButton, gbc);



        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==comboBoxEstado){
            System.out.println("COMBOBOX TRIGERRED");
            estado = Objects.requireNonNull(comboBoxEstado.getSelectedItem()).toString();
        }
        if(e.getSource()==submitButton){
            try{
                EnderecoRN.addEnderecoRN(
                        MenuPrincipal.getUsuario(),
                        new Endereco(
                                0,
                                textfRua.getText(),
                                textfBairro.getText(),
                                textfNumero.getText(),
                                textfCidade.getText(),
                                estado
                ));
                JOptionPane.showMessageDialog(frame, "Endereço adicionado com sucesso", "", JOptionPane.INFORMATION_MESSAGE);
                adressAdded = true;
                MenuPrincipal.alterarTela(new EnderecosGUI().getPanel());
                frame.dispose();
            } catch (SQLException | ContaExceptions.UsuarioInexistenteException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
            }
        }
    }
}
