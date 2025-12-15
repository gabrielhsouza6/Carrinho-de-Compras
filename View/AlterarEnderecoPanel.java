package View;

import Model.DAO.EnderecoDAO;
import Model.RN.EnderecoRN;
import Model.VO.Endereco;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;

public class AlterarEnderecoPanel extends JPanel{
    static Endereco endereco;
    JPanel panel;
    JPanel ruaPanel;
    JLabel ruaLabel;
    static JTextField ruaTextF;
    JPanel numeroPanel;
    JLabel numeroLabel;
    static JTextField numeroTextfF;
    JPanel bairroPanel;
    JLabel bairroLabel;
    static JTextField bairroTextF;
    JPanel cidadePanel;
    JLabel cidadeLabel;
    static JTextField cidadeTextF;
    JPanel estadoPanel;
    JLabel estadoLabel;
    static JComboBox estadoComboBox;
    GridBagConstraints gbc = new GridBagConstraints();
    Font defaultFont = new Font("Arial", Font.BOLD, 22);
    Font textFieldFont = new Font("Arial", Font.PLAIN, 21);
    static String[] estados = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};

    public AlterarEnderecoPanel(Endereco endereco){
        this.endereco = endereco;
        panel = new JPanel();
        ruaPanel = new JPanel();
        ruaLabel = new JLabel("Rua: ");
        ruaTextF = new JTextField(endereco.getRua());
        bairroPanel = new JPanel();
        bairroLabel = new JLabel("Bairro: ");
        bairroTextF = new JTextField(endereco.getBairro());
        numeroPanel = new JPanel();
        numeroLabel = new JLabel("Numero: ");
        numeroTextfF = new JTextField(endereco.getNumero());
        cidadePanel = new JPanel();
        cidadeLabel = new JLabel("Cidade: ");
        cidadeTextF = new JTextField(endereco.getCidade());
        estadoPanel = new JPanel();
        estadoLabel = new JLabel("Estado: ");
        estadoComboBox = new JComboBox(estados);
        for(int i=0; i<estados.length; i++){
            if(Objects.equals(estados[i], endereco.getEstado())){
                estadoComboBox.setSelectedIndex(i);
            }
        }

//        ruaLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        bairroLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        numeroLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        cidadeLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        estadoLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));


        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(400, 200));
        ruaPanel.setPreferredSize(new Dimension(350, 30));
        ruaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ruaPanel.setOpaque(true);
        ruaPanel.setBackground(Color.lightGray);
        bairroPanel.setPreferredSize(new Dimension(350, 30));
        bairroPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bairroPanel.setOpaque(true);
        bairroPanel.setBackground(Color.lightGray);
        numeroPanel.setPreferredSize(new Dimension(350, 30));
        numeroPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        numeroPanel.setOpaque(true);
        numeroPanel.setBackground(Color.lightGray);
        cidadePanel.setPreferredSize(new Dimension(350, 30));
        cidadePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        cidadePanel.setOpaque(true);
        cidadePanel.setBackground(Color.lightGray);
        estadoPanel.setPreferredSize(new Dimension(350, 30));
        estadoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        estadoPanel.setOpaque(true);
        estadoPanel.setBackground(Color.lightGray);
        ruaLabel.setFont(defaultFont);
//        ruaLabel.setPreferredSize(new Dimension(100, 30));
        ruaTextF.setFont(textFieldFont);
        ruaTextF.setPreferredSize(new Dimension(200, 25));
        bairroLabel.setFont(defaultFont);
//        bairroLabel.setPreferredSize(new Dimension(100, 30));
        bairroTextF.setFont(textFieldFont);
        bairroTextF.setPreferredSize(new Dimension(200, 25));
        numeroLabel.setFont(defaultFont);
//        numeroLabel.setPreferredSize(new Dimension(100, 30));
        numeroTextfF.setFont(textFieldFont);
        numeroTextfF.setPreferredSize(new Dimension(200, 25));
        cidadeLabel.setFont(defaultFont);
//        cidadeLabel.setPreferredSize(new Dimension(100, 30));
        cidadeTextF.setFont(textFieldFont);
        cidadeTextF.setPreferredSize(new Dimension(200, 25));
        estadoLabel.setFont(defaultFont);
//        estadoLabel.setPreferredSize(new Dimension(100, 30));
        estadoComboBox.setFont(textFieldFont);
        estadoComboBox.setPreferredSize(new Dimension(200, 25));

        ruaPanel.add(ruaLabel);
        ruaPanel.add(ruaTextF);
        bairroPanel.add(bairroLabel);
        bairroPanel.add(bairroTextF);
        numeroPanel.add(numeroLabel);
        numeroPanel.add(numeroTextfF);
        cidadePanel.add(cidadeLabel);
        cidadePanel.add(cidadeTextF);
        estadoPanel.add(estadoLabel);
        estadoPanel.add(estadoComboBox);

        //Panel Rua
        alterarGbc(0, 0);
        panel.add(ruaPanel, gbc);

        //Panel Numero
        alterarGbc(0, 1);
        panel.add(numeroPanel, gbc);

        //Panel Bairro
        alterarGbc(0, 2);
        panel.add(bairroPanel, gbc);

        //Panel Cidade
        alterarGbc(0, 3);
        panel.add(cidadePanel, gbc);

        //Panel Estado
        alterarGbc(0, 4);
        panel.add(estadoPanel, gbc);
    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void alterarDados() throws SQLException {
        EnderecoRN.updateEnderecoRN(new Endereco(
                endereco.getId(),
                ruaTextF.getText(),
                bairroTextF.getText(),
                numeroTextfF.getText(),
                cidadeTextF.getText(),
                estados[estadoComboBox.getSelectedIndex()]
        ), MenuPrincipal.getUsuario());

        endereco = new Endereco(
                endereco.getId(),
                ruaTextF.getText(),
                bairroTextF.getText(),
                numeroTextfF.getText(),
                cidadeTextF.getText(),
                estados[estadoComboBox.getSelectedIndex()]
        );
    }
}
