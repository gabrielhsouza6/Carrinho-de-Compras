package View;

import Exceptions.ContaExceptions;
import Model.DAO.CartaoDAO;
import Model.RN.CartaoRN;
import Model.VO.Cartao;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;

public class AlterarCartaoPanel extends JPanel{
    static Cartao cartao;
    static JPanel panel;
    JPanel inferiorPanel;
    JLabel fundoLabel;
    ImageIcon fundoIcon = new ImageIcon("src/Imagens/fundoCartao.png");
    JLabel bandeiraLabel;
    ImageIcon bandeiraIcon = null;
    ImageIcon MasterCardIcon = new ImageIcon("");
    ImageIcon EloIcon = new ImageIcon("src/Imagens/eloIcon.png");
    ImageIcon VisaIcon = new ImageIcon();
    ImageIcon AmericanExpressIcon = new ImageIcon();
    ImageIcon DiscoverIcon =  new ImageIcon();
    ImageIcon HiperCardIcon = new ImageIcon();
    static JTextField numCartaoTextField;
    static JTextField nomeTitularTextField;
    static JTextField dtValidadeTextField;
    static JPasswordField CVVTextField;
    GridBagConstraints gbc = new GridBagConstraints();
    Font textFieldFont = new Font("Arial", Font.PLAIN, 21);

    public AlterarCartaoPanel(Cartao cartao){
        this.cartao = cartao;
        panel = new JPanel();
        inferiorPanel = new JPanel();
        fundoLabel = new JLabel(fundoIcon);
        String numCartao = cartao.getNumCartao();

        if(numCartao.charAt(0) == '4'){
            bandeiraIcon = VisaIcon;
        }else if(Integer.parseInt(numCartao.substring(0, 2))<=55 && Integer.parseInt(numCartao.substring(0, 2))>=51
                || Integer.parseInt(numCartao.substring(0, 4))<=2720 && Integer.parseInt(numCartao.substring(0, 4))>=2221){
            bandeiraIcon = MasterCardIcon;
        }else if(numCartao.substring(0, 2).equals("37") || numCartao.substring(0, 2).equals("34")){
            bandeiraIcon = AmericanExpressIcon;
        }else if(numCartao.substring(0, 4).equals("6011") || numCartao.substring(0, 2).equals("65")
                || Integer.parseInt(numCartao.substring(0, 3))<=649 && Integer.parseInt(numCartao.substring(0, 3))>=644){
            bandeiraIcon = DiscoverIcon;
        }else if(numCartao.substring(0, 4).equals("6062")){
            bandeiraIcon = HiperCardIcon;
        }else{
            bandeiraIcon = EloIcon;
        }

        bandeiraLabel = new JLabel(bandeiraIcon);
        numCartaoTextField = new JTextField(cartao.getNumCartao());
        nomeTitularTextField = new JTextField(cartao.getNomeTitular());
        dtValidadeTextField = new JTextField(cartao.getDtValidade());
        CVVTextField = new JPasswordField(cartao.getCVV());

//        numCartaoTextField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        nomeTitularTextField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        bandeiraLabel.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        dtValidadeTextField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        CVVTextField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        inferiorPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(250, 150));
        inferiorPanel.setPreferredSize(new Dimension(250, 30));
        inferiorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        numCartaoTextField.setFont(textFieldFont);
        numCartaoTextField.setPreferredSize(new Dimension(250, 30));
        nomeTitularTextField.setFont(textFieldFont);
        nomeTitularTextField.setPreferredSize(new Dimension(210, 30));
        dtValidadeTextField.setFont(textFieldFont);
        dtValidadeTextField.setPreferredSize(new Dimension(60, 30));
        CVVTextField.setFont(textFieldFont);
        CVVTextField.setPreferredSize(new Dimension(50, 30));
        bandeiraLabel.setPreferredSize(new Dimension(80, 30));
        fundoLabel.setPreferredSize(new Dimension(250, 150));

        inferiorPanel.add(dtValidadeTextField);
        inferiorPanel.add(CVVTextField);
        inferiorPanel.add(bandeiraLabel);
        inferiorPanel.setOpaque(true);
        inferiorPanel.setBackground(Color.gray);

        numCartaoTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater( () -> {
                            int inputLength = numCartaoTextField.getText().length();
                            if(inputLength == 4 || inputLength == 9 || inputLength == 14){
                                numCartaoTextField.setText(numCartaoTextField.getText()+" ");
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

        //TextField nomeTitular
        alterarGbc(0, 0);
//        gbc.fill = GridBagConstraints.NONE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, -10, 20);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
//        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nomeTitularTextField, gbc);

        //TextField numCartao
        alterarGbc(0, 1);
        gbc.insets = new Insets(20, 10, 0, 20);
        panel.add(numCartaoTextField, gbc);

        //TextFields dtValidade,CVV e bandeira
        alterarGbc(0, 2);
//        gbc.gridwidth = 1;
//        gbc.insets = new Insets(-25, 10,-15, 0);
//        layeredPane.add(dtValidadeTextField, gbc);
//        alterarGbc(1, 2);
        gbc.insets = new Insets(-25, -10, -15, 0);
//        layeredPane.add(CVVTextField, gbc);
//        alterarGbc(2, 2);
//        gbc.insets = new Insets(-25, 0, -15, 0);
//        layeredPane.add(bandeiraLabel, gbc);
        panel.add(inferiorPanel, gbc);

        //Label fundo
        alterarGbc(0, 0);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridheight = 3;
//        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(fundoLabel, gbc);

    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void alterarDados() throws SQLException, ContaExceptions.UsuarioInexistenteException {
        CartaoRN.updateCartaoRN(MenuPrincipal.getUsuario(), new Cartao(
                cartao.getId(),
                numCartaoTextField.getText(),
                nomeTitularTextField.getText(),
                dtValidadeTextField.getText(),
                new String(CVVTextField.getPassword()),
                CartoesGUI.tipo
        ));
        cartao = CartaoRN.searchCartaoRN(cartao.getId());
    }
}
