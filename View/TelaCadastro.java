package View;

import Model.RN.ClienteRN;
import Model.VO.Cliente;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class TelaCadastro extends JFrame implements ActionListener, DocumentListener {
    JFrame frame;
    JLabel titleLabel;
    JLabel descLabel;
    JLabel labelNome;
    JLabel labelDtNasc;
    JLabel labelSexo;
    JLabel labelCpf;
    JLabel labelSenha;
    JTextField textfNome;
    JTextField textfCpf;
    JPasswordField textfSenha;
    JRadioButton rbuttonM;
    JRadioButton rbuttonF;
    ButtonGroup sexButtons;
    JButton submitButton;
    String sexo = null;
    int idadeMinima = 10;
    GridBagConstraints gbc = new GridBagConstraints();
    JDateChooser jDateChooser;

    TelaCadastro(){
        //Inicialização
        frame = new JFrame("Cadastro");
        frame.setSize(650, 360);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        titleLabel = new JLabel("Cadastro");
        descLabel = new JLabel("Insira suas informações e pressione Confirmar");
        labelNome = new JLabel("Nome: ");
        labelDtNasc = new JLabel("Data de Nascimento: ");
        labelSexo = new JLabel("Sexo: ");
        labelCpf = new JLabel("CPF: ");
        labelSenha = new JLabel("Senha: ");
        textfNome = new JTextField();
        textfCpf = new JTextField();
        textfSenha = new JPasswordField();
        rbuttonM = new JRadioButton("Masculino");
        rbuttonF = new JRadioButton("Feminino");
        sexButtons = new ButtonGroup();
        submitButton = new JButton("Confirmar");
        jDateChooser = new JDateChooser();

        //Alterações
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        descLabel.setFont(new Font("Arial", Font.BOLD, 17));
        labelNome.setLayout(new FlowLayout(FlowLayout.RIGHT));
        labelNome.setFont(new Font("Arial", Font.BOLD, 15));
        textfNome.setPreferredSize(new Dimension(300, 17));
        textfNome.setFont(new Font("Arial", Font.PLAIN, 15));
        labelDtNasc.setLayout(new FlowLayout(FlowLayout.RIGHT));
        labelDtNasc.setFont(new Font("Arial", Font.BOLD, 15));
        labelSexo.setLayout(new FlowLayout(FlowLayout.RIGHT));
        labelSexo.setFont(new Font("Arial", Font.BOLD, 15));
        labelCpf.setLayout(new FlowLayout(FlowLayout.RIGHT));
        labelCpf.setFont(new Font("Arial", Font.BOLD, 15));
        textfCpf.setPreferredSize(new Dimension(300, 17));
        textfCpf.setFont(new Font("Arial", Font.PLAIN, 15));
        rbuttonF.setFont(new Font("Arial", Font.PLAIN, 15));
        rbuttonF.addActionListener(this);
        rbuttonM.setFont(new Font("Arial", Font.PLAIN, 15));
        rbuttonM.addActionListener(this);
        submitButton.setPreferredSize(new Dimension(120, 35));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.addActionListener(this);
        jDateChooser.setFont(new Font("Arial", Font.PLAIN, 15));
        jDateChooser.setDateFormatString("dd/MM/yyyy");
        jDateChooser.setPreferredSize(new Dimension(120, 14));
        labelSenha.setLayout(new FlowLayout(FlowLayout.RIGHT));
        labelSenha.setFont(new Font("Arial", Font.BOLD, 15));
        textfSenha.setPreferredSize(new Dimension(300, 17));
        textfSenha.setFont(new Font("Arial", Font.PLAIN, 15));

        sexButtons.add(rbuttonM);
        sexButtons.add(rbuttonF);

        textfCpf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(textfCpf.getText().length()==3 || textfCpf.getText().length()==7){
                    SwingUtilities.invokeLater(() -> {
                        textfCpf.setText(textfCpf.getText()+".");
                    });
                }else if(textfCpf.getText().length()==11){
                    SwingUtilities.invokeLater(() -> {
                        textfCpf.setText(textfCpf.getText()+"-");
                    });
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        //Titulo
        gbc.gridwidth = 3;
        alterarGbc(0, 0);
        gbc.insets = new Insets(0, 0, 5, 0);
        frame.add(titleLabel, gbc);

        //Descricao
        alterarGbc(0, 1);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 5, 30, 0);
        frame.add(descLabel, gbc);

        //Label Nome
        alterarGbc(0, 3);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, -40, 15, -20);
        frame.add(labelNome, gbc);

        //TextField Nome
        alterarGbc(1, 3);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, -40, 10, 0);
        frame.add(textfNome, gbc);

        //Label CPF
        alterarGbc(0, 4);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, -40, 15, -30);
        frame.add(labelCpf, gbc);

        //TextField CPF
        alterarGbc(1, 4);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, -45, 10, 0);
        frame.add(textfCpf, gbc);

        //Label Data de Nascimento
        alterarGbc(0, 5);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, -50, 15, 0);
        frame.add(labelDtNasc, gbc);

        //TextField Data de Nascimento
        alterarGbc(1, 5);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 15, -20);
        frame.add(jDateChooser, gbc);

        //Label Sexo
        alterarGbc(2, 5);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, -60, 15, 0);
        frame.add(labelSexo, gbc);

        //Buttons
        alterarGbc(2, 5);
        gbc.insets = new Insets(0, 0, 15, -75); //Left button
        frame.add(rbuttonM, gbc);
        alterarGbc(2, 5);
        gbc.insets = new Insets(0, 0, 15, -250);  //Right Button
        frame.add(rbuttonF, gbc);

        //Label Senha
        alterarGbc(0, 6);
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(-5, -40, 15, -20);
        frame.add(labelSenha, gbc);

        //TextField Senha
        alterarGbc(1, 6);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(-5, -35, 10, 0);
        frame.add(textfSenha, gbc);

        //Submit Button
        alterarGbc(0, 7);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
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
        if(e.getSource()==rbuttonM){
            sexo = "Masculino";
        }
        else if(e.getSource()==rbuttonF){
            sexo = "Feminino";
        }
        if(e.getSource() == submitButton){
            try {
                ClienteRN.addClienteRN(new Cliente(
                        0,
                        textfNome.getText(),
                        textfCpf.getText(),
                        new Date(jDateChooser.getCalendar().getTimeInMillis()),
                        sexo,
                        new String(textfSenha.getPassword()
                )));
                MenuPrincipal.setUsuario(ClienteRN.logarClienteRN(textfNome.getText(), new String(textfSenha.getPassword())));
                InicioPanel.panel.remove(InicioPanel.loginButton);
                InicioPanel.panel.remove(InicioPanel.cadastroLabel);
                InicioPanel.panel.revalidate();
                InicioPanel.panel.repaint();
                frame.dispose();
            } catch (SQLException ex) {
                throw new RuntimeException();
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(frame, "Idade Inválida", "Erro ao login", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro ao login", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
