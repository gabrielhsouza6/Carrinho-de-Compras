package View;

import Exceptions.ContaExceptions;
import Model.VO.Cliente;
import Model.VO.Compra;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class OldCompraPanel extends JPanel implements MouseListener {
    JPanel panel;
    JPanel numPanel;
    JPanel compraIdPanel;
    JPanel dataPanel;
    JPanel overallPanel;
    JLabel numLabel;
    JLabel compraIdLabel;
    JLabel dataLabel;
    JLabel totalLabel;
    JLabel qtdLabel;
    GridBagConstraints gbc = new GridBagConstraints();
    Font infoFont = new Font("Arial", Font.BOLD, 25);
    Compra compra;
    Cliente cliente = null;

    public OldCompraPanel(Compra compra){
        this.compra = compra;
        panel = new JPanel();
        numPanel = new JPanel();
        compraIdPanel = new JPanel();
        dataPanel = new JPanel();
        overallPanel = new JPanel();
        numLabel = new JLabel(String.format("%d", MinhasComprasPanel.numCompra));
        compraIdLabel = new JLabel(String.format("Compra #%06d", compra.getId()));
        dataLabel = new JLabel("Data: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(compra.getData()));
        totalLabel = new JLabel(String.format("Total: R$%.2f", compra.getTotal()));
        qtdLabel = new JLabel("Quantidade de produtos: " + compra.getQtdProdutos());
        MinhasComprasPanel.numCompra--;

        panel.setPreferredSize(new Dimension(856, 140));
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.setLayout(new GridBagLayout());
        panel.addMouseListener(this);
        numPanel.setPreferredSize(new Dimension(150, 140));
        numPanel.setLayout(new FlowLayout());
        compraIdPanel.setPreferredSize(new Dimension(706, 30));
        compraIdPanel.setLayout(new FlowLayout());
        dataPanel.setPreferredSize(new Dimension(706, 40));
        dataPanel.setLayout(new FlowLayout());
        overallPanel.setPreferredSize(new Dimension(706, 70));
        overallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        numLabel.setFont(new Font("Arial", Font.BOLD, 55));
        compraIdLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dataLabel.setFont(infoFont);
        totalLabel.setFont(infoFont);
        qtdLabel.setFont(infoFont);

        numPanel.add(numLabel);
        compraIdPanel.add(compraIdLabel);
        dataPanel.add(dataLabel);
        overallPanel.add(totalLabel);
        overallPanel.add(qtdLabel);

        //Panel numCompra
        alterarGbc(0, 0);
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(30, -15, 0, 0);
        panel.add(numPanel, gbc);

        //Panel idCompra
        alterarGbc(1, 0);
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 90, 5, 0);
        panel.add(compraIdPanel, gbc);

        //Panel data
        alterarGbc(1, 1);
        panel.add(dataPanel, gbc);

        //Panel overall
        alterarGbc(1, 2);
        panel.add(overallPanel, gbc);
    }

    public OldCompraPanel(Cliente cliente, Compra compra){
        Font infoFont = new Font("SansSerif", Font.BOLD, 12);
        this.cliente = cliente;
        this.compra = compra;
        panel = new JPanel();
        numPanel = new JPanel();
        compraIdPanel = new JPanel();
        dataPanel = new JPanel();
        overallPanel = new JPanel();
        numLabel = new JLabel(String.format("%d", MinhasComprasPanel.numCompra));
        compraIdLabel = new JLabel(String.format("Compra #%06d", compra.getId()));
        dataLabel = new JLabel("Data: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(compra.getData()));
        totalLabel = new JLabel(String.format("Total: R$%.2f", compra.getTotal()));
        qtdLabel = new JLabel("Quantidade de produtos: " + compra.getQtdProdutos());
        MinhasComprasPanel.numCompra--;

        panel.setPreferredSize(new Dimension(570, 80));
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.setLayout(new GridBagLayout());
        panel.addMouseListener(this);
        numPanel.setPreferredSize(new Dimension(70, 30));
        numPanel.setLayout(new FlowLayout());
        compraIdPanel.setPreferredSize(new Dimension(470, 14));
        compraIdPanel.setLayout(new FlowLayout());
        dataPanel.setPreferredSize(new Dimension(470, 18));
        dataPanel.setLayout(new FlowLayout());
        overallPanel.setPreferredSize(new Dimension(470, 33));
        overallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 2));
        numLabel.setFont(new Font("Arial", Font.BOLD, 23));
        compraIdLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dataLabel.setFont(infoFont);
        totalLabel.setFont(infoFont);
        qtdLabel.setFont(infoFont);

        numPanel.add(numLabel);
        compraIdPanel.add(compraIdLabel);
        dataPanel.add(dataLabel);
        overallPanel.add(totalLabel);
        overallPanel.add(qtdLabel);

        //Panel numCompra
        alterarGbc(0, 0);
        gbc.gridwidth = 1;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, -7, 0, 0);
        panel.add(numPanel, gbc);

        //Panel idCompra
        alterarGbc(1, 0);
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 45, 2, 0);
        panel.add(compraIdPanel, gbc);

        //Panel data
        alterarGbc(1, 1);
        panel.add(dataPanel, gbc);

        //Panel overall
        alterarGbc(1, 2);
        panel.add(overallPanel, gbc);
    }

    public void alterarGbc(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if(cliente==null) {
                MenuPrincipal.alterarTela(new DetalhesOldCompraPanel(compra).getPanel());
            }else{
                AdminClientePanel.alterarClientePanel(new DetalhesOldCompraPanel(cliente, compra).getPanel());
            }
        } catch (ContaExceptions.NomeInvalidoException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
