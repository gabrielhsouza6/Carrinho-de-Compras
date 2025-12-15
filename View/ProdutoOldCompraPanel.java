package View;

import Model.VO.Cliente;
import Model.VO.Produto;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ProdutoOldCompraPanel extends JPanel {
    static int idIncremental = 0;
    int id;
    JPanel panel;
    Produto produto;
    JPanel prodInfoPanel;
    JLabel qtdLabel;
    JLabel totalLabel;
    JLabel totalDescLabel;
    GridBagConstraints gbc = new GridBagConstraints();
    int ajustaFonte = (int) (((MenuPrincipal.currentFrame.getWidth()-900)/50.0 + (MenuPrincipal.currentFrame.getHeight()-550)/50.0));
    Font nomeLabelFont = new Font("Arial", Font.PLAIN, 17);

    public ProdutoOldCompraPanel(Produto produto, String descricao, String medida, double preco, double qtd, double dsct){
        this.id = idIncremental++;
        panel = new JPanel();
        prodInfoPanel = new ProdutoPanel(produto, descricao, medida, preco, dsct).getPanel();
        this.produto = produto;
        qtdLabel = new JLabel("Quantidade: " + qtd + " " + medida);
        totalLabel = new JLabel(String.format("Total: R$%.2f", qtd * preco * (1.0 - dsct/100.0)));
        totalDescLabel = new JLabel(String.format("Custo economizado: R$%.2f", qtd * preco * dsct/100.0));

        panel.setPreferredSize(new Dimension(856, 110));
        qtdLabel.setFont(nomeLabelFont);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalDescLabel.setFont(new Font("Arial", Font.ITALIC, 15));

        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        prodInfoPanel.setPreferredSize(new Dimension(282, 100));
        prodInfoPanel.setBorder(null);

//        prodInfoPanel.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
//        qtdLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
//        totalLabel.setBorder(BorderFactory.createLineBorder(Color.red, 3));
//        totalDescLabel.setBorder(BorderFactory.createLineBorder(Color.green, 3));

        //Panel prodInfo
        alterarGbc(0, 0);
        gbc.gridheight = 4;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(prodInfoPanel, gbc);

        //Label Qtd
        alterarGbc(4, 0);
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 60, 3, 0);
        panel.add(qtdLabel, gbc);

        //Label total
        alterarGbc(4, 2);
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 60, -60, 0);
        panel.add(totalLabel, gbc);

        //Label descTotal
        if(produto.getDesconto()>0) {
            alterarGbc(4, 3);
            gbc.insets = new Insets(0, 60, -60, 0);
            panel.add(totalDescLabel, gbc);
        }

    }

    public ProdutoOldCompraPanel(Cliente cliente, Produto produto, String descricao, String medida, double preco, double qtd, double dsct){
        this.id = idIncremental++;
        Font nomeLabelFont = new Font("Arial", Font.PLAIN, 12);
        panel = new JPanel();
        JPanel overallPanel = new JPanel();
        prodInfoPanel = new ProdutoPanel(cliente, produto, descricao, medida, preco, dsct).getPanel();
        this.produto = produto;
        qtdLabel = new JLabel("Quantidade: " + qtd + " " + medida);
        totalLabel = new JLabel(String.format("Total: R$%.2f", qtd * preco * (1.0 - dsct/100.0)));
        totalDescLabel = new JLabel(String.format("Custo economizado: R$%.2f", qtd * preco * dsct/100.0));

        panel.setPreferredSize(new Dimension(570, 60));
        qtdLabel.setFont(nomeLabelFont);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 12));
        totalDescLabel.setFont(new Font("Arial", Font.ITALIC, 11));

        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        prodInfoPanel.setPreferredSize(new Dimension(400, 55));
//        prodInfoPanel.setLayout(new BorderLayout(0, 2));
//        prodInfoPanel.add(new ProdutoPanel(cliente, produto, descricao, medida, preco, dsct).getPanel());
        overallPanel.setPreferredSize(new Dimension(150, 55));
        overallPanel.setLayout(new FlowLayout());
        totalLabel.setPreferredSize(new Dimension(135, 22));
        qtdLabel.setPreferredSize(new Dimension(135, 22));

//        prodInfoPanel.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
//        qtdLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
//        totalLabel.setBorder(BorderFactory.createLineBorder(Color.red, 3));
//        totalDescLabel.setBorder(BorderFactory.createLineBorder(Color.green, 3));

        overallPanel.add(qtdLabel);
        overallPanel.add(totalLabel);
        panel.add(prodInfoPanel);
        panel.add(overallPanel);

    }

    public JPanel getPanel() {
        return panel;
    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }
}
