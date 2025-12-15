package View;

import Exceptions.EntradaInvalidaException;
import Model.VO.Produto;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class ProdutoCarrinhoGUI extends JPanel implements ActionListener {
    static int idIncremental = 0;
    int id;
    JPanel panel;
    Produto produto;
    JPanel prodInfoPanel;
    JLabel qtdLabel;
    JLabel totalLabel;
    JLabel totalDescLabel;
    JButton alterarButton;
    JButton excluirButton;
    ImageIcon alterarIcon = new ImageIcon("src//Imagens//lapis_icon-removebg-preview.png");
    ImageIcon excluirIcon =  new ImageIcon("src/Imagens/lixo_icon-removebg-preview.png");
    GridBagConstraints gbc = new GridBagConstraints();
    int ajustaFonte = (int) (((MenuPrincipal.currentFrame.getWidth()-900)/50.0 + (MenuPrincipal.currentFrame.getHeight()-550)/50.0));
    Font nomeLabelFont = new Font("Arial", Font.PLAIN, 17);
    static ArrayList<Produto> produtosCarrinho = MenuPrincipal.getCarrinho().getCarrinhoProd();
    static ArrayList<Double> quantidadesCarrinho = MenuPrincipal.getCarrinho().getCarrinhoQtds();

    public ProdutoCarrinhoGUI(Produto produto, double qtd){
        this.id = idIncremental++;
        panel = new JPanel();
        prodInfoPanel = new ProdutoPanel(produto).getPanel();
        this.produto = produto;
        qtdLabel = new JLabel("Quantidade: " + qtd + " " + produto.getMedida());
        totalLabel = new JLabel(String.format("Total: R$%.2f", qtd * produto.getPreco() * (1.0 - produto.getDesconto()/100.0)));
        totalDescLabel = new JLabel(String.format("Custo economizado: R$%.2f", qtd * produto.getPreco() * produto.getDesconto()/100.0));
        alterarButton = new JButton(alterarIcon);
        excluirButton = new JButton(excluirIcon);

        panel.setPreferredSize(new Dimension(856, 120));
        qtdLabel.setFont(nomeLabelFont);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalDescLabel.setFont(new Font("Arial", Font.ITALIC, 15));

        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        prodInfoPanel.setPreferredSize(new Dimension(282, 100));
        prodInfoPanel.setBorder(null);
        alterarButton.setSize(new Dimension(20, 20));
        alterarButton.setBackground(Color.white);
        alterarButton.addActionListener(this);
        alterarButton.setFocusable(false);
        excluirButton.setSize(new Dimension(20, 20));
        excluirButton.setBackground(Color.white);
        excluirButton.addActionListener(this);
        excluirButton.setFocusable(false);

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

        //Button alterar
        alterarGbc(5, 2);
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, -50, -90);
        panel.add(alterarButton, gbc);

        //Button excluir
        alterarGbc(6, 2);
        gbc.insets = new Insets(0, 0, -50, -210);
        panel.add(excluirButton, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==alterarButton){
            try {
                double qtd = 0.0;
                qtd = Double.parseDouble(JOptionPane.showInputDialog(MenuPrincipal.currentFrame, "Insira a quantidade desejada:  (" + produto.getMedida() + ")", "Comprar produto", JOptionPane.QUESTION_MESSAGE));
                if (qtd <= 0 || Objects.equals(produto.getMedida(), "un") && qtd!=Math.round(qtd)) {
                    throw new EntradaInvalidaException("Quantidade Inválida");
                }
                qtdLabel.setText("Quantidade: " + qtd + " " + produto.getMedida());
                totalLabel.setText(String.format("Total: R$%.2f", qtd * produto.getPreco() * (1.0 - produto.getDesconto()/100.0)));
                totalDescLabel.setText(String.format("Custo economizado: R$%.2f", qtd * produto.getPreco() * produto.getDesconto()/100.0));
                quantidadesCarrinho.remove(id);
                quantidadesCarrinho.add(id, qtd);
                CarrinhoGUI.atualizarOverallPanel();
                panel.revalidate();
                panel.repaint();
            }catch (EntradaInvalidaException exc) {
                JOptionPane.showMessageDialog(MenuPrincipal.currentFrame, exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }catch (Exception exc){
                JOptionPane.showMessageDialog(MenuPrincipal.currentFrame, "Entrada Inválida", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==excluirButton){
            produtosCarrinho.remove(id);
            quantidadesCarrinho.remove(id);

            idIncremental = 0;
            CarrinhoGUI.atualizarOverallPanel();
            CarrinhoGUI.atualizarLista();
        }
    }
}
