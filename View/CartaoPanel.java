package View;

import Model.VO.Cartao;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CartaoPanel extends JPanel {
    JPanel panel;
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
    JLabel numCartaoLabel;
    JLabel nomeTitularLabel;
    JLabel dtValidadeLabel;
    JLabel CVVLabel;
    GridBagConstraints gbc = new GridBagConstraints();
    Font defaultFont = new Font("Arial", Font.BOLD, 22);

    public CartaoPanel(Cartao cartao){
        panel = new JPanel();
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
        numCartaoLabel = new JLabel(cartao.getNumCartao());
        nomeTitularLabel = new JLabel(cartao.getNomeTitular());
        dtValidadeLabel = new JLabel(cartao.getDtValidade());
        CVVLabel = new JLabel("***");

//        numCartaoLabel.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        nomeTitularLabel.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        bandeiraLabel.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        dtValidadeLabel.setBorder(BorderFactory.createLineBorder(Color.red, 2));
//        CVVLabel.setBorder(BorderFactory.createLineBorder(Color.red, 2));

        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(250, 150));
        numCartaoLabel.setFont(defaultFont);
        numCartaoLabel.setPreferredSize(new Dimension(250, 30));
        nomeTitularLabel.setFont(defaultFont);
        nomeTitularLabel.setPreferredSize(new Dimension(250, 30));
        dtValidadeLabel.setFont(defaultFont);
        dtValidadeLabel.setPreferredSize(new Dimension(100, 30));
        CVVLabel.setFont(defaultFont);
        CVVLabel.setPreferredSize(new Dimension(100, 30));
        fundoLabel.setPreferredSize(new Dimension(100, 30));

        //Label nomeTitular
        alterarGbc(0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, -10, 0);
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nomeTitularLabel, gbc);

        //Label numCartao
        alterarGbc(0, 1);
        gbc.insets = new Insets(20, 10, 0, 0);
        panel.add(numCartaoLabel, gbc);

        //Labels dtValidade,CVV e bandeira
        alterarGbc(0, 2);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(-25, 10,-15, 0);
        panel.add(dtValidadeLabel, gbc);
        alterarGbc(1, 2);
        gbc.insets = new Insets(-25, 30, -15, 20);
        panel.add(CVVLabel, gbc);
        alterarGbc(2, 2);
        gbc.insets = new Insets(-25, 0, -15, 0);
        panel.add(bandeiraLabel, gbc);

        //Label fundo
        alterarGbc(0, 0);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridheight = 3;
        gbc.gridwidth = 3;
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
}
