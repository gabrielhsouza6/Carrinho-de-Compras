package View;

import Model.VO.Endereco;

import javax.swing.*;
import java.awt.*;

public class EnderecoPanel extends JPanel {
    JPanel panel;
    JLabel ruaLabel;
    JLabel numeroLabel;
    JLabel bairroLabel;
    JLabel cidadeLabel;
    JLabel estadoLabel;
    GridBagConstraints gbc = new GridBagConstraints();
    Font defaultFont = new Font("Arial", Font.BOLD, 22);

    public EnderecoPanel(Endereco endereco){
        panel = new JPanel();
        ruaLabel = new JLabel("Rua: " + endereco.getRua());
        bairroLabel = new JLabel("Bairro: " + endereco.getBairro());
        numeroLabel = new JLabel("Numero: " + endereco.getNumero());
        cidadeLabel = new JLabel("Cidade: " + endereco.getCidade());
        estadoLabel = new JLabel("Estado: " + endereco.getEstado());

//        ruaLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        bairroLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        numeroLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        cidadeLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        estadoLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));


        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(400, 200));
        ruaLabel.setFont(defaultFont);
        ruaLabel.setPreferredSize(new Dimension(350, 30));
        bairroLabel.setFont(defaultFont);
        bairroLabel.setPreferredSize(new Dimension(350, 30));
        numeroLabel.setFont(defaultFont);
        numeroLabel.setPreferredSize(new Dimension(350, 30));
        cidadeLabel.setFont(defaultFont);
        cidadeLabel.setPreferredSize(new Dimension(350, 30));
        estadoLabel.setFont(defaultFont);
        estadoLabel.setPreferredSize(new Dimension(350, 30));

        //Label Rua
        alterarGbc(0, 0);
        panel.add(ruaLabel, gbc);

        //Label Numero
        alterarGbc(0, 1);
        panel.add(numeroLabel, gbc);

        //Label Bairro
        alterarGbc(0, 2);
        panel.add(bairroLabel, gbc);

        //Label Cidade
        alterarGbc(0, 3);
        panel.add(cidadeLabel, gbc);

        //Label Estado
        alterarGbc(0, 4);
        panel.add(estadoLabel, gbc);
    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    public JPanel getPanel() {
        return panel;
    }
}
