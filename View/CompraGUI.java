package View;

import Model.DAO.EnderecoDAO;
import Exceptions.EntradaInvalidaException;
import Model.RN.CompraRN;
import Model.VO.Carrinho;
import Model.VO.Compra;
import Model.VO.Endereco;
import Model.VO.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

public class CompraGUI extends JFrame implements ActionListener {
    JFrame frame;
    JPanel panel;
    JLabel endLabel;
    JLabel pagLabel;
    JComboBox pagComboBox;
    JComboBox endComboBox;
    JPanel pagamentoPanel;
    JButton submitButton;
    ArrayList<Endereco> enderecos = EnderecoDAO.getEnderecosClienteDAO(MenuPrincipal.getUsuario());
    String[] enderecosStrings;
    String[] pagamentos = {"Dinheiro", "Cartão", "PIX"};
    float total = 0;
    static Carrinho carrinho = MenuPrincipal.getCarrinho();
    static ArrayList<Produto> produtosCarrinho = MenuPrincipal.getCarrinho().getCarrinhoProd();
    static ArrayList<Double> quantidadesCarrinho = MenuPrincipal.getCarrinho().getCarrinhoQtds();
    String formaDePagamento = "";
    float valorPago;
    float troco;

    public CompraGUI(float totalCompra) throws SQLException {
        panel = new JPanel();
        frame = new JFrame("Finalizar Compra");
        endLabel = new JLabel("Endereço: ");
        pagLabel = new JLabel("Forma de pagamento: ");
        enderecosStrings = new String[enderecos.size()];
        for(int i = 0; i< enderecosStrings.length; i++){
            enderecosStrings[i] = enderecos.get(i).toString();
        }
        endComboBox = new JComboBox(enderecosStrings);
        pagComboBox = new JComboBox(pagamentos);
        pagamentoPanel = new JPanel();
        submitButton = new JButton("Confirmar");
        total = totalCompra;

        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(500, 350));
        panel.setLayout(new FlowLayout());
        endLabel.setFont(new Font("Arial", Font.BOLD, 15));
        pagLabel.setFont(new Font("Arial", Font.BOLD, 15));
        endComboBox.setPreferredSize(new Dimension(300, 18));
        pagComboBox.setPreferredSize(new Dimension(235, 18));
        pagComboBox.addActionListener(this);
        pagComboBox.setSelectedIndex(0);
        pagamentoPanel.setPreferredSize(new Dimension(450, 200));
        submitButton.setPreferredSize(new Dimension(130, 40));
        submitButton.addActionListener(this);

        frame.add(panel);
        panel.add(endLabel);
        panel.add(endComboBox);
        panel.add(pagLabel);
        panel.add(pagComboBox);
        panel.add(pagamentoPanel);
        panel.add(submitButton);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==pagComboBox){
            if(Objects.equals(pagComboBox.getSelectedItem(), "Dinheiro")){
                alterarPanel(new DinheiroCompraPanel(total).getPanel());
                formaDePagamento = "Dinheiro";
            }
            if(Objects.equals(pagComboBox.getSelectedItem(), "Cartão")){
                try {
                    alterarPanel(new CartaoCompraPanel().getPanel());
                    formaDePagamento = "Cartão";
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(Objects.equals(pagComboBox.getSelectedItem(), "PIX")){
                alterarPanel(new PIXCompraPanel().getPanel());
                formaDePagamento = "PIX";
            }
        }
        if(e.getSource()==submitButton){
            try {
                if(Objects.equals(formaDePagamento, "Dinheiro") && DinheiroCompraPanel.dinheiro<total){
                    throw new EntradaInvalidaException("É preciso de um valor maior que o total da compra");
                }
                if(Objects.equals(formaDePagamento, "Cartão") || Objects.equals(formaDePagamento, "PIX")){
                    valorPago = total;
                    troco = 0;
                }else{
                    valorPago = DinheiroCompraPanel.dinheiro;
                    troco = DinheiroCompraPanel.troco;
                }
                if(endComboBox.getItemCount()==0){
                    throw new IllegalArgumentException("Endereço Inválido");
                }
                if(Objects.equals(formaDePagamento, "Cartão") && CartaoCompraPanel.cartaoComboBox.getItemCount()==0){
                    throw new IllegalArgumentException("Cartão Inválido");
                }
                CompraRN.addCompraRN(new Compra(
                        0,
                        MenuPrincipal.getUsuario(),
                        new Timestamp(Instant.now().toEpochMilli()),
                        produtosCarrinho,
                        quantidadesCarrinho,
                        total,
                        produtosCarrinho.size(),
                        formaDePagamento,
                        valorPago,
                        troco
                ));
//                System.out.println("Endereco: " + Objects.requireNonNull(endComboBox.getSelectedItem()).toString().trim());
//                System.out.println("Cartao: " + Objects.requireNonNull(CartaoCompraPanel.cartaoComboBox.getSelectedItem()).toString().trim());

                carrinho.limparCarrinho();
                CarrinhoGUI.atualizarLista();
                CarrinhoGUI.atualizarOverallPanel();
                JOptionPane.showMessageDialog(frame, "Compra realizada com sucesso!", "Obrigado pela preferência", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void alterarPanel(JPanel newPanel){
        panel.removeAll();
        pagamentoPanel = newPanel;
        panel.add(endLabel);
        panel.add(endComboBox);
        panel.add(pagLabel);
        panel.add(pagComboBox);
        panel.add(pagamentoPanel);
        panel.add(submitButton);
        pagamentoPanel.revalidate();
        pagamentoPanel.repaint();
        panel.revalidate();
        panel.repaint();
    }
}
