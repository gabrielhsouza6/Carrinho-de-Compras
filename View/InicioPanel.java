package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.Map;

public class InicioPanel extends JPanel implements ActionListener, MouseListener {
    static JPanel panel;
    static JButton loginButton;
    static JLabel cadastroLabel;
    ImageIcon fundo;
    JLabel fundoLabel;
    GridBagConstraints gbc = new GridBagConstraints();

    public InicioPanel() {
        panel = new JPanel();
        loginButton = new JButton("Login");
        cadastroLabel = new JLabel("Não possuo cadastro");
        fundoLabel = new JLabel();

        panel.setLayout(new GridBagLayout());
        fundo = new ImageIcon("src//Imagens//carrinho fundo paint.png");
        fundoLabel.setIcon(fundo);
        loginButton.setFocusable(false);
        loginButton.setSize(200, 110);
        loginButton.setFont(new Font("Roboto", Font.ITALIC, 25));
        loginButton.addMouseListener(this);
        cadastroLabel.setFont(new Font(null, Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        if (MenuPrincipal.getUsuario() == null) {
            panel.add(loginButton, gbc);
            gbc.insets = new Insets(65, 0, 0, 0);
            panel.add(cadastroLabel, gbc);
        }
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(fundoLabel, gbc);

        loginButton.addActionListener(this);
        cadastroLabel.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loginButton){
            new TelaLogin();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==cadastroLabel){
            new TelaCadastro();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        cadastroLabel.setForeground(new Color(0, 0, 13));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        cadastroLabel.setForeground(Color.BLUE);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==cadastroLabel){
            cadastroLabel.setForeground(Color.BLUE);
            cadastroLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            Font newFont = new Font(null, Font.BOLD, 16);
            Map atributos = newFont.getAttributes();
            atributos.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            cadastroLabel.setFont(newFont.deriveFont(atributos));
        }
        if(e.getSource()==loginButton){
            cadastroLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        cadastroLabel.setForeground(Color.BLACK);
        cadastroLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        cadastroLabel.setFont(new Font(null, Font.BOLD, 16));
        cadastroLabel.setText("Não possuo cadastro");
    }

    public JPanel getPanel() {
        return panel;
    }
}
