package View;

import Exceptions.ContaExceptions;
import Model.VO.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CarrinhoGUI extends JPanel implements MouseListener {
    JPanel principalPanel;
    static JPanel listaProdPanel;
    static JScrollPane listaProdScroll;
    static JPanel overallPanel;
    static JLabel qtdProdLabel;
    static JLabel totalLabel;
    static ArrayList<Produto> produtosCarrinho = MenuPrincipal.getCarrinho().getCarrinhoProd();
    static ArrayList<Double> quantidadesCarrinho = MenuPrincipal.getCarrinho().getCarrinhoQtds();
    JButton buyButton;
    GridBagConstraints gbc = new GridBagConstraints();
    static float total = 0;

    public CarrinhoGUI() throws ContaExceptions.UsuarioInexistenteException {
        if(MenuPrincipal.getUsuario()==null){
            throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário vinculado");
        }
        ProdutoCarrinhoGUI.idIncremental = 0;
        principalPanel = new JPanel();
        listaProdPanel = new JPanel();
        listaProdScroll = new JScrollPane(listaProdPanel);
        overallPanel = new JPanel();
        qtdProdLabel = new JLabel("Quantidade de produtos: " + produtosCarrinho.size());
        totalLabel = new JLabel();
        buyButton = new JButton("Comprar");

//        principalPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
//        listaProdPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
//        listaProdScroll.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
//        overallPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));

        listaProdScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listaProdScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listaProdScroll.getVerticalScrollBar().setUnitIncrement(15);
        listaProdScroll.getVerticalScrollBar().setMinimumSize(new Dimension(25, 0));
        listaProdPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        listaProdScroll.setPreferredSize(new Dimension(900, 126 * produtosCarrinho.size()));
        listaProdPanel.setPreferredSize(new Dimension(900, 126 * produtosCarrinho.size()));
        addProdutosLista();
        calcularTotal();
        buyButton.setFont(new Font("Arial", Font.BOLD, 24));
        buyButton.setPreferredSize(new Dimension(170, 60));
        buyButton.setFocusable(false);
        buyButton.addMouseListener(this);

        qtdProdLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        overallPanel.setLayout(new GridBagLayout());
        overallPanel.setPreferredSize(new Dimension(900, 150));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 60);
        overallPanel.add(qtdProdLabel, gbc);

        totalLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 10, 5);
        totalLabel.setText(String.format("Total: R$%.2f", total));
        overallPanel.add(totalLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 100, 10, 0);
        overallPanel.add(buyButton, gbc);


//        principalPanel.setLayout(new GridBagLayout());
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridheight = 2;
//        gbc.fill = GridBagConstraints.VERTICAL;
        principalPanel.setLayout(new BorderLayout());
        principalPanel.add(overallPanel, BorderLayout.SOUTH);
        principalPanel.add(listaProdScroll, BorderLayout.CENTER);
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.gridheight = 1;
    }

    public JPanel getPanel() {
        return principalPanel;
    }

    public static void atualizarOverallPanel(){
        calcularTotal();
        totalLabel.setText(String.format("Total: R$%.2f", total));
        qtdProdLabel.setText("Quantidade de produtos: " + produtosCarrinho.size());
        overallPanel.revalidate();
        overallPanel.repaint();
    }

    public static void atualizarLista(){
        for(Component c : listaProdPanel.getComponents()){
            listaProdPanel.remove(c);
        }
        addProdutosLista();
        listaProdScroll.setPreferredSize(new Dimension(900, 126 * produtosCarrinho.size()));
        listaProdPanel.setPreferredSize(new Dimension(900, 126 * produtosCarrinho.size()));
        listaProdPanel.revalidate();
        listaProdPanel.repaint();
    }

    public static void calcularTotal(){
        total = 0;
        for(int i=0; i<produtosCarrinho.size(); i++){
            Produto p = produtosCarrinho.get(i);
            double qtd = quantidadesCarrinho.get(i);
            total += qtd * p.getPreco() * (1.0 - p.getDesconto()/100.0);
        }
    }

    public static void addProdutosLista(){
        for(int i=0; i<produtosCarrinho.size(); i++){
            Produto p = produtosCarrinho.get(i);
            double qtd = quantidadesCarrinho.get(i);
            JPanel temp = new ProdutoCarrinhoGUI(p, qtd).getPanel();
            listaProdPanel.add(temp);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==buyButton){
            try {
                if (MenuPrincipal.getUsuario() == null) {
                    throw new ContaExceptions.UsuarioInexistenteException("Necessita de cadastro para finalizar");
                } else if (MenuPrincipal.getCarrinho().getCarrinhoProd().isEmpty()){
                    throw new Exception("O carrinho está vazio");
                }
                else {
                    new CompraGUI(total);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(MenuPrincipal.currentFrame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
