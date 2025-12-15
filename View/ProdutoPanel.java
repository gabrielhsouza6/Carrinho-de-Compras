package View;

import Model.VO.Carrinho;
import Model.VO.Cliente;
import Model.VO.Produto;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Objects;

public class ProdutoPanel extends JPanel{
    JPanel panel;
    Produto produto;
    JLabel nomeLabel1;
    JLabel nomeLabel2;
    JLabel precoLabel;
    ImageIcon image;
    JLabel imagemLabel;
    JLabel descontoLabel;
    GridBagConstraints gbc = new GridBagConstraints();
    int ajustaFonte = (int) (((MenuPrincipal.currentFrame.getWidth()-900)/50.0 + (MenuPrincipal.currentFrame.getHeight()-550)/50.0));
    Font nomeLabelFont = new Font("Arial", Font.PLAIN, 17 + ajustaFonte);
    Carrinho carrinho = MenuPrincipal.getCarrinho();

    public ProdutoPanel(Produto produto){
        panel = new JPanel();
        this.produto = produto;
        nomeLabel1 = new JLabel(produto.getDescricao());
        nomeLabel2 = new JLabel();
        precoLabel = new JLabel("R$ " + produto.getPreco().toString() + " / " + produto.getMedida());
        image = new ImageIcon(produto.getFoto());
        imagemLabel = new JLabel(image);
        descontoLabel = new JLabel("Desconto: " + produto.getDesconto().toString() + "%");

        imagemLabel.setPreferredSize(new Dimension(150, 120));
        panel.setPreferredSize(new Dimension(282, 120));
//        nomeLabel1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        nomeLabel1.setFont(nomeLabelFont);
        nomeLabel1.setLayout(new FlowLayout(FlowLayout.LEFT));
//        nomeLabel2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        nomeLabel2.setFont(nomeLabelFont);
        nomeLabel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        precoLabel.setFont(new Font("Arial", Font.BOLD, (int) (24 + 1.15 * ajustaFonte)));
//        precoLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        descontoLabel.setFont(new Font("Arial", Font.ITALIC, 15 + ajustaFonte));
//        descontoLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        int frameWitdh = MenuPrincipal.currentFrame.getWidth();
        int frameHeight = MenuPrincipal.currentFrame.getHeight();
        int previousIndex = 0;

        panel.setPreferredSize(new Dimension((int) (frameWitdh / (900/282.0)), (int) (frameHeight / (550/120.0))));
        for(int i=0; i<nomeLabel1.getText().length(); i++){
            if(nomeLabel1.getText().charAt(i)==' ') {
                if(i>=22){
                    nomeLabel2.setText(nomeLabel1.getText().substring(previousIndex+1));
                    nomeLabel1.setText(nomeLabel1.getText().substring(0, previousIndex));
                    break;
                }else if(i>=12){
                    nomeLabel2.setText(nomeLabel1.getText().substring(i+1));
                    nomeLabel1.setText(nomeLabel1.getText().substring(0, i));
                    break;
                }
                previousIndex = i;
            }
        }

        //Label Nome1
        alterarGbc(1, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10, 0, 3*ajustaFonte, -15);
        panel.add(nomeLabel1, gbc);

        //Label Nome2
        alterarGbc(1, 1);
        gbc.insets = new Insets(0, 0, 3*ajustaFonte, -15);
        panel.add(nomeLabel2, gbc);

        //Label Preco
        alterarGbc(1, 2);
        gbc.insets = new Insets(15, 0, 0, -15);
        panel.add(precoLabel, gbc);

        //Label Desconto
        if(produto.getDesconto()>0) {
            alterarGbc(1, 3);
            gbc.insets = new Insets(20, 0, 15, -15);
            panel.add(descontoLabel, gbc);
        }

        //Label Foto
        alterarGbc(0, 0);
        gbc.gridheight = 4;
        gbc.insets = new Insets(0, -65, 0, 10);
        panel.add(imagemLabel, gbc);
    }

    public ProdutoPanel(Produto produto, String descricao, String medida, double preco, double dsct){
        panel = new JPanel();
        this.produto = produto;
        nomeLabel1 = new JLabel(descricao);
        nomeLabel2 = new JLabel();
        precoLabel = new JLabel(String.format("R$ %.2f / %s", preco, medida));
        image = new ImageIcon(produto.getFoto());
        imagemLabel = new JLabel(image);
        descontoLabel = new JLabel("Desconto: " + dsct + "%");

        imagemLabel.setPreferredSize(new Dimension(150, 130));
        panel.setPreferredSize(new Dimension(282, 120));
//        nomeLabel1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        nomeLabel1.setFont(nomeLabelFont);
        nomeLabel1.setLayout(new FlowLayout(FlowLayout.LEFT));
//        nomeLabel2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        nomeLabel2.setFont(nomeLabelFont);
        nomeLabel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        precoLabel.setFont(new Font("Arial", Font.BOLD, (int) (24 + 1.15 * ajustaFonte)));
//        precoLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        descontoLabel.setFont(new Font("Arial", Font.ITALIC, 15 + ajustaFonte));
//        descontoLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        int frameWitdh = MenuPrincipal.currentFrame.getWidth();
        int frameHeight = MenuPrincipal.currentFrame.getHeight();
        int previousIndex = 0;

        panel.setPreferredSize(new Dimension((int) (frameWitdh / (900/282.0)), (int) (frameHeight / (550/120.0))));
        for(int i=0; i<nomeLabel1.getText().length(); i++){
            if(nomeLabel1.getText().charAt(i)==' ') {
                if(i>=25){
                    nomeLabel2.setText(nomeLabel1.getText().substring(previousIndex+1));
                    nomeLabel1.setText(nomeLabel1.getText().substring(0, previousIndex));
                    break;
                }else if(i>=15){
                    nomeLabel2.setText(nomeLabel1.getText().substring(i+1));
                    nomeLabel1.setText(nomeLabel1.getText().substring(0, i));
                    break;
                }
                previousIndex = i;
            }
        }

        //Label Nome1
        alterarGbc(1, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10, 0, 3*ajustaFonte, -15);
        panel.add(nomeLabel1, gbc);

        //Label Nome2
        alterarGbc(1, 1);
        gbc.insets = new Insets(0, 0, 3*ajustaFonte, -15);
        panel.add(nomeLabel2, gbc);

        //Label Preco
        alterarGbc(1, 2);
        gbc.insets = new Insets(15, 0, 0, -15);
        panel.add(precoLabel, gbc);

        //Label Desconto
        if(produto.getDesconto()>0) {
            alterarGbc(1, 3);
            gbc.insets = new Insets(20, 0, 15, -15);
            panel.add(descontoLabel, gbc);
        }

        //Label Foto
        alterarGbc(0, 0);
        gbc.gridheight = 4;
        gbc.insets = new Insets(0, -65, 0, 10);
        panel.add(imagemLabel, gbc);
    }

    public ProdutoPanel(Cliente cliente, Produto produto, String descricao, String medida, double preco, double dsct){
        Font nomeLabelFont = new Font("SansSerif", Font.BOLD, 18);
        panel = new JPanel();
        this.produto = produto;
        JPanel nomePanel = new JPanel();
        nomeLabel1 = new JLabel(descricao);
        nomeLabel2 = new JLabel();
        JPanel overallPanel = new JPanel();
        precoLabel = new JLabel(String.format("R$ %.2f / %s", preco, medida));
        descontoLabel = new JLabel("Desconto: " + dsct + "%");
//        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        panel.setPreferredSize(new Dimension(400, 55));
        nomePanel.setPreferredSize(new Dimension(250, 50));
        nomePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 0));
        overallPanel.setPreferredSize(new Dimension(140, 50));
        overallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
//        nomeLabel1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        nomeLabel1.setFont(nomeLabelFont);
        nomeLabel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
//        nomeLabel1.setPreferredSize(new Dimension(240, 22));
//        nomeLabel2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        nomeLabel2.setFont(nomeLabelFont);
//        nomeLabel2.setPreferredSize(new Dimension(240, 22));
        nomeLabel2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
        precoLabel.setFont(new Font("Arial", Font.BOLD, 21));
//        precoLabel.setPreferredSize(new Dimension(150, 20));
//        precoLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        descontoLabel.setFont(new Font("Arial", Font.ITALIC, 13));
//        descontoLabel.setPreferredSize(new Dimension(150, 20));
//        descontoLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
//        panel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        overallPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));

        int previousIndex = 0;

        for(int i=0; i<nomeLabel1.getText().length(); i++){
            if(nomeLabel1.getText().charAt(i)==' ') {
                if(i>=25){
                    nomeLabel2.setText(nomeLabel1.getText().substring(previousIndex+1));
                    nomeLabel1.setText(nomeLabel1.getText().substring(0, previousIndex));
                    break;
                }else if(i>=15){
                    nomeLabel2.setText(nomeLabel1.getText().substring(i+1));
                    nomeLabel1.setText(nomeLabel1.getText().substring(0, i));
                    break;
                }
                previousIndex = i;
            }
        }

        if(Objects.equals(nomeLabel2.getText(), "")){
            nomeLabel2.setText("   ");
        }

        if(dsct==0){
            descontoLabel.setText("  ");
        }

        nomePanel.add(nomeLabel1);
        if(!nomeLabel2.getText().trim().isEmpty()) {
            nomePanel.add(nomeLabel2);
        }else{
            nomePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        }
        overallPanel.add(precoLabel);
        if(dsct>0) {
            overallPanel.add(descontoLabel);
        }else{
            overallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        }

        panel.add(nomePanel);
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
