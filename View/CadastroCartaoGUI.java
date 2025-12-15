package View;

import Model.DAO.CartaoDAO;
import Exceptions.ContaExceptions;
import Model.RN.CartaoRN;
import Model.VO.Cartao;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroCartaoGUI extends JFrame implements ActionListener {
    static JFrame frame;
    JLabel titleLabel;
    JPanel numCartaoPanel;
    JPanel nomeTitularPanel;
    JPanel dtValidadePanel;
    JPanel CVVPanel;
    JPanel tipoPanel;
    JLabel labelnumCartao;
    JLabel labelnomeTitular;
    JLabel labelDtValidade;
    JLabel labelCVV;
    JLabel labelTipo;
    JTextField textfNumCartao;
    JTextField textfNomeTitular;
    JTextField textfDtValidade;
    JPasswordField textfCVV;
    JRadioButton creditoButton;
    JRadioButton debitoButton;
    ButtonGroup tiposButtons;
    JButton submitButton;
    GridBagConstraints gbc = new GridBagConstraints();
    Font textfFont = new Font("Arial", Font.PLAIN, 15);
    Font labelFont = new Font("Arial", Font.BOLD, 15);
    static String tipo = "";
    static boolean cardAdded = false;


    public CadastroCartaoGUI(){
        //Inicialização
        frame = new JFrame("Cadastro");
        frame.setSize(650, 360);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        titleLabel = new JLabel("Cadastro");
        labelnumCartao = new JLabel("Numero do Cartão: ");
        labelnomeTitular = new JLabel("Nome do Titular: ");
        labelDtValidade = new JLabel("Data de Validade: ");
        labelCVV = new JLabel("CVV: ");
        labelTipo = new JLabel("Tipo: ");
        textfNumCartao = new JTextField();
        textfNomeTitular = new JTextField();
        textfDtValidade = new JTextField();
        textfCVV = new JPasswordField();
        creditoButton = new JRadioButton("Crédito");
        debitoButton = new JRadioButton("Débito");
        tiposButtons = new ButtonGroup();
        submitButton = new JButton("Confirmar");
        numCartaoPanel = new JPanel();
        nomeTitularPanel = new JPanel();
        dtValidadePanel = new JPanel();
        CVVPanel = new JPanel();
        tipoPanel = new JPanel();

//        titleLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        //Alterações
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setPreferredSize(new Dimension(150, 30));
        labelnumCartao.setFont(labelFont);
        textfNumCartao.setPreferredSize(new Dimension(300, 17));
        textfNumCartao.setFont(textfFont);
        labelnomeTitular.setFont(labelFont);
        labelDtValidade.setFont(labelFont);
        labelCVV.setFont(labelFont);
        textfCVV.setFont(textfFont);
        textfCVV.setPreferredSize(new Dimension(300, 17));
        textfNomeTitular.setPreferredSize(new Dimension(300, 17));
        textfNomeTitular.setFont(textfFont);
        submitButton.setPreferredSize(new Dimension(120, 35));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.addActionListener(this);
        labelTipo.setFont(labelFont);
        textfDtValidade.setPreferredSize(new Dimension(300, 17));
        textfDtValidade.setFont(textfFont);
        creditoButton.addActionListener(this);
        creditoButton.setFont(textfFont);
        debitoButton.addActionListener(this);
        debitoButton.setFont(textfFont);
        numCartaoPanel.add(labelnumCartao);
        numCartaoPanel.add(textfNumCartao);
        numCartaoPanel.setPreferredSize(new Dimension(450, 30));
        numCartaoPanel.setLayout(new FlowLayout());
        nomeTitularPanel.add(labelnomeTitular);
        nomeTitularPanel.add(textfNomeTitular);
        nomeTitularPanel.setPreferredSize(new Dimension(450, 30));
        nomeTitularPanel.setLayout(new FlowLayout());
        dtValidadePanel.add(labelDtValidade);
        dtValidadePanel.add(textfDtValidade);
        dtValidadePanel.setPreferredSize(new Dimension(450, 30));
        dtValidadePanel.setLayout(new FlowLayout());
        CVVPanel.add(labelCVV);
        CVVPanel.add(textfCVV);
        CVVPanel.setPreferredSize(new Dimension(450, 30));
        CVVPanel.setLayout(new FlowLayout());
        tipoPanel.add(labelTipo);
        tipoPanel.add(creditoButton);
        tipoPanel.add(debitoButton);
        tipoPanel.setPreferredSize(new Dimension(450, 30));
        tipoPanel.setLayout(new FlowLayout());
        tiposButtons.add(debitoButton);
        tiposButtons.add(creditoButton);

        textfNumCartao.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater( () -> {
                    int inputLength = textfNumCartao.getText().length();
                    if(inputLength == 4 || inputLength == 9 || inputLength == 14){
                        textfNumCartao.setText(textfNumCartao.getText()+" ");
                    }
                        }
                );
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        //Label title
        alterarGbc(0, 0);
        gbc.insets = new Insets(-10, 0, 20, -50);
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        frame.add(titleLabel, gbc);

        //Panel NumCartao
        alterarGbc(0, 1);
        gbc.insets = new Insets(0, -85, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(numCartaoPanel, gbc);

        //Panel Nome Titular
        alterarGbc(0, 2);
        gbc.insets = new Insets(0, -76, 0, 0);
        frame.add(nomeTitularPanel, gbc);

        //Panel dtValidade
        alterarGbc(0 ,3);
        gbc.insets = new Insets(0, -80, 0, 0);
        frame.add(dtValidadePanel, gbc);

        //Panel CVV
        alterarGbc(0, 4);
        gbc.insets = new Insets(0, 5, 0, 0);
        frame.add(CVVPanel, gbc);

        //Panel Tipo
        alterarGbc(0, 5);
        gbc.insets = new Insets(0, -30, 0, 0);
        frame.add(tipoPanel, gbc);

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
        if(e.getSource()==creditoButton){
            tipo = "Crédito";
            System.out.println(tipo);
        }
        if(e.getSource()==debitoButton){
            tipo = "Débito";
            System.out.println(tipo);
        }
        if(e.getSource()==submitButton){
            try{
                CartaoRN.addCartaoRN(
                        MenuPrincipal.getUsuario(),
                        new Cartao(
                                0,
                                   textfNumCartao.getText(),
                                   textfNomeTitular.getText(),
                                   textfDtValidade.getText(),
                                   new String(textfCVV.getPassword()),
                                   tipo
                ));
                JOptionPane.showMessageDialog(frame, "Cartao adicionado com sucesso", "", JOptionPane.INFORMATION_MESSAGE);
                cardAdded = true;
                MenuPrincipal.alterarTela(new CartoesGUI().getPanel());
                frame.dispose();
            } catch (SQLException | ContaExceptions.UsuarioInexistenteException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
