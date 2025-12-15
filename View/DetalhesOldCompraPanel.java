package View;

import Model.DAO.ProdutosComprasDAO;
import Exceptions.ContaExceptions;
import Model.VO.Cliente;
import Model.VO.Compra;
import Model.VO.Produto;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetalhesOldCompraPanel extends JPanel{
    JPanel panel;
    JPanel headPanel;
    JPanel listaProdPanel;
    JScrollPane listaProdScrollPanel;
    JPanel overallPanel;
    JPanel qtdPanel;
    JPanel formPanel;
    JPanel totalPanel;
    JPanel pagTrocoPanel;
    JLabel compraIdLabel;
    JLabel dataLabel;
    JLabel qtdProdLabel;
    JLabel formPagLabel;
    JLabel totalLabel;
    JLabel pagTrocoLabel;
    Font headFont = new Font("Arial", Font.BOLD, 24);
    Font footFont = new Font("Arial", Font.BOLD, 25);
    GridBagConstraints gbc = new GridBagConstraints();
    ArrayList<Produto> produtosCompra;
    ArrayList<Double> quantidadesCompra;

    public DetalhesOldCompraPanel(Compra compra) throws ContaExceptions.NomeInvalidoException, SQLException {
        panel = new JPanel();
        headPanel = new JPanel();
        listaProdPanel = new JPanel();
        listaProdScrollPanel = new JScrollPane(listaProdPanel);
        overallPanel = new JPanel();
        qtdPanel = new JPanel();
        formPanel = new JPanel();
        totalPanel = new JPanel();
        pagTrocoPanel = new JPanel();
        compraIdLabel = new JLabel(String.format("Compra #%06d", compra.getId()));
        dataLabel = new JLabel("Data: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(compra.getData()));
        qtdProdLabel = new JLabel("Quantidade de produtos: " + compra.getQtdProdutos());
        formPagLabel = new JLabel("Forma de Pagamento: " + compra.getFormaPagamento());
        totalLabel = new JLabel(String.format("Total: R$%.2f", compra.getTotal()));
        pagTrocoLabel = new JLabel(String.format("Valor Pago: R$%.2f  Troco: R$%.2f", compra.getValorPago(), compra.getTroco()));

//        headPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        listaProdPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        overallPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        qtdPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        formPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
//        totalPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        pagTrocoPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        compraIdLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        dataLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        qtdProdLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        formPagLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        totalLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        pagTrocoLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));

        panel.setLayout(new FlowLayout());
        headPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        headPanel.setPreferredSize(new Dimension(850, 50));
        listaProdPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));
        listaProdScrollPanel.setPreferredSize(new Dimension(880, 330));
        listaProdScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listaProdScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listaProdScrollPanel.getVerticalScrollBar().setUnitIncrement(15);
        listaProdScrollPanel.getVerticalScrollBar().setMinimumSize(new Dimension(20, 0));
        overallPanel.setLayout(new GridBagLayout());
        overallPanel.setPreferredSize(new Dimension(850, 80));
        qtdPanel.setLayout(new FlowLayout());
        qtdPanel.setPreferredSize(new Dimension(200, 40));
        formPanel.setLayout(new FlowLayout());
        formPanel.setPreferredSize(new Dimension(200, 40));
        totalPanel.setLayout(new FlowLayout());
        totalPanel.setPreferredSize(new Dimension(500, 40));
        pagTrocoPanel.setLayout(new FlowLayout());
        pagTrocoPanel.setPreferredSize(new Dimension(500, 40));
        compraIdLabel.setFont(headFont);
        dataLabel.setFont(headFont);
        qtdProdLabel.setFont(footFont);
        formPagLabel.setFont(footFont);
        totalLabel.setFont(footFont);
        pagTrocoLabel.setFont(footFont);

        headPanel.add(compraIdLabel);
        headPanel.add(dataLabel);
        qtdPanel.add(qtdProdLabel);
        formPanel.add(formPagLabel);
        totalPanel.add(totalLabel);
        pagTrocoPanel.add(pagTrocoLabel);
        produtosCompra = ProdutosComprasDAO.getProdutosCompraDAO(compra.getId());
        quantidadesCompra = ProdutosComprasDAO.getQtdsCompraDAO(compra.getId());
        for(int i=0; i<produtosCompra.size(); i++){
            listaProdPanel.add(new ProdutoOldCompraPanel(produtosCompra.get(i),
                                                         produtosCompra.get(i).getDescricao(),
                                                         produtosCompra.get(i).getMedida(),
                                                         produtosCompra.get(i).getPreco(),
                                                         quantidadesCompra.get(i),
                                                         produtosCompra.get(i).getDesconto()
                                                         ).getPanel());
        }
        listaProdPanel.setPreferredSize(new Dimension(880, listaProdPanel.getComponentCount()*117));

        //Panel qtd
        alterarGbc(0, 0);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
//        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets= new Insets(0, 0, 0, 20);
        overallPanel.add(qtdPanel, gbc);

        //Panel form
        alterarGbc(0, 1);
        gbc.insets= new Insets(0, 0, 5, 20);
        overallPanel.add(formPanel, gbc);

        //Panel total
        alterarGbc(1, 0);
        gbc.insets = new Insets(0, 20, 0, 0);
        overallPanel.add(totalPanel, gbc);

        //Panel pagTroco
        alterarGbc(1, 1);
        gbc.insets = new Insets(-5, 20, 0, 0);
        overallPanel.add(pagTrocoPanel, gbc);

        panel.add(headPanel);
        panel.add(listaProdScrollPanel);
        panel.add(overallPanel);

    }

    public DetalhesOldCompraPanel(Cliente cliente, Compra compra) throws ContaExceptions.NomeInvalidoException, SQLException {
        Font headFont = new Font("SansSerif", Font.BOLD, 14);
        Font footFont = new Font("SansSerif", Font.BOLD, 14);

        panel = new JPanel();
        headPanel = new JPanel();
        listaProdPanel = new JPanel();
        listaProdScrollPanel = new JScrollPane(listaProdPanel);
        overallPanel = new JPanel();
        qtdPanel = new JPanel();
        formPanel = new JPanel();
        totalPanel = new JPanel();
        pagTrocoPanel = new JPanel();
        compraIdLabel = new JLabel(String.format("Compra #%06d", compra.getId()));
        dataLabel = new JLabel("Data: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(compra.getData()));
        qtdProdLabel = new JLabel("Quantidade de produtos: " + compra.getQtdProdutos());
        formPagLabel = new JLabel("Forma de Pagamento: " + compra.getFormaPagamento());
        totalLabel = new JLabel(String.format("Total: R$%.2f", compra.getTotal()));
        pagTrocoLabel = new JLabel(String.format("Valor Pago: R$%.2f  Troco: R$%.2f", compra.getValorPago(), compra.getTroco()));

//        headPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        listaProdPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        overallPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        qtdPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        formPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
//        totalPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        pagTrocoPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        compraIdLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        dataLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        qtdProdLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        formPagLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        totalLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
//        pagTrocoLabel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));

        panel.setLayout(new FlowLayout());
        headPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        headPanel.setPreferredSize(new Dimension(550, 25));
        listaProdPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 5));
        listaProdScrollPanel.setPreferredSize(new Dimension(580, 200));
        listaProdScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listaProdScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listaProdScrollPanel.getVerticalScrollBar().setUnitIncrement(10);
        overallPanel.setLayout(new GridBagLayout());
        overallPanel.setPreferredSize(new Dimension(550, 60));
        overallPanel.setBackground(Color.lightGray);
        qtdPanel.setLayout(new FlowLayout());
        qtdPanel.setPreferredSize(new Dimension(160, 30));
        formPanel.setLayout(new FlowLayout());
        formPanel.setPreferredSize(new Dimension(160, 30));
        totalPanel.setLayout(new FlowLayout());
        totalPanel.setPreferredSize(new Dimension(330, 30));
        pagTrocoPanel.setLayout(new FlowLayout());
        pagTrocoPanel.setPreferredSize(new Dimension(330, 30));
        compraIdLabel.setFont(headFont);
        dataLabel.setFont(headFont);
        qtdProdLabel.setFont(footFont);
        formPagLabel.setFont(footFont);
        totalLabel.setFont(footFont);
        pagTrocoLabel.setFont(footFont);

        headPanel.add(compraIdLabel);
        headPanel.add(dataLabel);
        qtdPanel.add(qtdProdLabel);
        formPanel.add(formPagLabel);
        totalPanel.add(totalLabel);
        pagTrocoPanel.add(pagTrocoLabel);
        produtosCompra = ProdutosComprasDAO.getProdutosCompraDAO(compra.getId());
        quantidadesCompra = ProdutosComprasDAO.getQtdsCompraDAO(compra.getId());
        for(int i=0; i<produtosCompra.size(); i++){
            listaProdPanel.add(new ProdutoOldCompraPanel(
                    cliente,
                    produtosCompra.get(i),
                    produtosCompra.get(i).getDescricao(),
                    produtosCompra.get(i).getMedida(),
                    produtosCompra.get(i).getPreco(),
                    quantidadesCompra.get(i),
                    produtosCompra.get(i).getDesconto()
            ).getPanel());
        }
        listaProdPanel.setPreferredSize(new Dimension(575, listaProdPanel.getComponentCount()*65));

        //Panel qtd
        alterarGbc(0, 0);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
//        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets= new Insets(0, 0, 0, 10);
        overallPanel.add(qtdPanel, gbc);

        //Panel form
        alterarGbc(0, 1);
        gbc.insets= new Insets(0, 0, 2, 10);
        overallPanel.add(formPanel, gbc);

        //Panel total
        alterarGbc(1, 0);
        gbc.insets = new Insets(0, 10, 0, 0);
        overallPanel.add(totalPanel, gbc);

        //Panel pagTroco
        alterarGbc(1, 1);
        gbc.insets = new Insets(-2, 10, 0, 0);
        overallPanel.add(pagTrocoPanel, gbc);

        panel.add(headPanel);
        panel.add(listaProdScrollPanel);
        panel.add(overallPanel);

        panel.setBackground(Color.lightGray);
        for(Component c : panel.getComponents()){
            c.setBackground(Color.lightGray);
        }
        for(Component c : overallPanel.getComponents()){
            c.setBackground(Color.lightGray);
        }

    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    public JPanel getPanel() {
        return panel;
    }
}
