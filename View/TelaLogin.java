package View;

import Model.RN.ClienteRN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TelaLogin extends JFrame implements ActionListener, KeyListener {
    JFrame frame;
    JLabel title;
    JLabel labelNome;
    JLabel labelSenha;
    JTextField textfNome;
    JPasswordField textfSenha;
    JButton submitButton;
    GridBagConstraints gbc = new GridBagConstraints();

    public TelaLogin(){
        frame = new JFrame("Login");
        title = new JLabel("Login");
        labelNome = new JLabel("Nome: ");
        labelSenha = new JLabel("Senha: ");
        textfNome = new JTextField();
        textfSenha = new JPasswordField();
        submitButton = new JButton("Confirmar");
        Font labelFont = new Font("Arial", Font.BOLD, 15);
        Font textfFont = new Font("Arial", Font.PLAIN, 15);

        //
        frame.setSize(550, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        title.setFont(new Font("Arial", Font.BOLD, 25));
        labelNome.setLayout(new FlowLayout(FlowLayout.RIGHT));
        labelNome.setFont(labelFont);
        textfNome.setPreferredSize(new Dimension(300, 17));
        textfNome.setFont(textfFont);
        labelSenha.setLayout(new FlowLayout(FlowLayout.RIGHT));
        labelSenha.setFont(labelFont);
        textfSenha.setPreferredSize(new Dimension(300, 17));
        textfSenha.setFont(textfFont);
        submitButton.setPreferredSize(new Dimension(120, 35));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.addActionListener(this);

        //Title
        alterarGbc(0, 0);
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 35, 0);
        frame.add(title, gbc);

        //Label Nome
        alterarGbc(0, 1);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        frame.add(labelNome, gbc);

        //TextField Nome
        alterarGbc(1, 1);
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 8, 0);
        gbc.fill = GridBagConstraints.VERTICAL;
        frame.add(textfNome, gbc);

        //Label Senha
        alterarGbc(0, 2);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        frame.add(labelSenha, gbc);

        //TextField Senha
        alterarGbc(1, 2);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 8, 0);
        frame.add(textfSenha, gbc);

        //SubmitButton
        alterarGbc(0, 3);
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 0, 0, 0);
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
        if(e.getSource()==submitButton){
            try {
                MenuPrincipal.setUsuario(ClienteRN.logarClienteRN(
                        textfNome.getText(),
                        new String(textfSenha.getPassword())
                ));
                System.out.println("Id: " + MenuPrincipal.getUsuario().getId());
                System.out.println("Nome: " + MenuPrincipal.getUsuario().getNome());
                System.out.println("Data de Nascimento: " + MenuPrincipal.getUsuario().getDataDeNascimento().toString());
                System.out.println("Sexo: " + MenuPrincipal.getUsuario().getSexo());
                System.out.println("Senha: " + MenuPrincipal.getUsuario().getSenha());
                InicioPanel.panel.remove(InicioPanel.loginButton);
                InicioPanel.panel.remove(InicioPanel.cadastroLabel);
                InicioPanel.panel.revalidate();
                InicioPanel.panel.repaint();
                System.out.println("Login bem sucedido!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
