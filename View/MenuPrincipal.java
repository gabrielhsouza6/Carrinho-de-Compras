package View;

import Model.DAO.CategoriasDAO;
import Exceptions.ContaExceptions;
import Model.VO.Carrinho;
import Model.VO.Cliente;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.SQLException;
import java.util.Objects;

public class MenuPrincipal extends JFrame implements ActionListener, MenuListener {
    JFrame frame;
    static JFrame currentFrame = null;
    private static Cliente usuario = null;
    private static Carrinho carrinho = new Carrinho();
    static JPanel currentPanel = null;
    static JScrollPane currentScrollPane = null;
    String previousCategorie;
    JMenuBar menuBar;
    JMenu inicioMenu;
    JMenu profileMenu;
    static JMenu productsMenu;
    JMenu myShoppingCarMenu;
    private static JMenu adminMenu;
    JMenuItem info;
    JMenuItem minhasCompras;
    JMenuItem cartoes;
    JMenuItem enderecos;
    JMenuItem logout;
    JMenuItem hortifruti;
    JMenuItem frios;
    JMenuItem consumiveisDiversos;
    JMenuItem limpeza;
    JMenuItem estatisticas;
    JMenuItem clientes;
    JMenuItem produtos;


    public MenuPrincipal() throws SQLException {
        //Inicialização
        frame = new JFrame();
        currentFrame = new JFrame();
        frame.setIconImage(new ImageIcon("src//Imagens//carrinho icone.png").getImage());
        frame.setTitle("Bem-vindo!");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(900, 550);

        menuBar = new JMenuBar();
        inicioMenu = new JMenu("Início");
        profileMenu = new JMenu("Perfil");
        productsMenu = new JMenu("Produtos");
        myShoppingCarMenu = new JMenu("Meu Carrinho");
        adminMenu = new JMenu("Admin");
        info = new JMenuItem("Info");
        minhasCompras = new JMenuItem("Minhas Compras");
        cartoes = new JMenuItem("Cartões");
        enderecos = new JMenuItem("Endereços");
        logout = new JMenuItem("Logout");
        hortifruti = new JMenuItem("Hortifruti");
        frios = new JMenuItem("Frios");
        consumiveisDiversos = new JMenuItem("Consumíveis Diversos");
        limpeza = new JMenuItem("Limpeza");
        estatisticas = new JMenuItem("Estatísticas");
        clientes = new JMenuItem("Clientes");
        produtos = new JMenuItem("Produtos");

        //Alterações
        info.addActionListener(this);
        minhasCompras.addActionListener(this);
        cartoes.addActionListener(this);
        enderecos.addActionListener(this);
        logout.addActionListener(this);
        hortifruti.addActionListener(this);
        frios.addActionListener(this);
        consumiveisDiversos.addActionListener(this);
        limpeza.addActionListener(this);
        inicioMenu.addMenuListener(this);
        myShoppingCarMenu.addMenuListener(this);
        clientes.addActionListener(this);
        produtos.addActionListener(this);
        estatisticas.addActionListener(this);
        profileMenu.add(info);
        profileMenu.add(minhasCompras);
        profileMenu.add(cartoes);
        profileMenu.add(enderecos);
        profileMenu.add(logout);
//        productsMenu.add(hortifruti);
//        productsMenu.add(frios);
//        productsMenu.add(consumiveisDiversos);
//        productsMenu.add(limpeza);
//        for(String s : CategoriasDAO.getCategorias()){
//            productsMenu.add(new JMenuItem(s));
//        }
//        for(int i=0; i<productsMenu.getItemCount(); i++){
//            productsMenu.getItem(i).addActionListener(this);
//        }
        atualizarProductsMenu();
        adminMenu.add(clientes);
        adminMenu.add(produtos);
        adminMenu.add(estatisticas);
        menuBar.add(inicioMenu);
        menuBar.add(profileMenu);
        menuBar.add(productsMenu);
        menuBar.add(myShoppingCarMenu);
        currentPanel = new InicioPanel().getPanel();

        frame.setJMenuBar(menuBar);
        frame.add(currentPanel);
        currentFrame = frame;
        currentFrame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(e.getSource()==currentFrame && currentScrollPane !=null){
                    try {
                        alterarTela(new ListaDeProdutosPanel(previousCategorie).getScrollPane());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        currentFrame.setLocationRelativeTo(null);
        currentFrame.setVisible(true);
    }

    public static Carrinho getCarrinho() {
        return carrinho;
    }

    public static void setCarrinho(Carrinho carrinho) {
        MenuPrincipal.carrinho = carrinho;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==info){
            JPanel infoPanel = null;
            try {
                alterarTela(new PerfilPanel().getPanel());
            } catch (ContaExceptions.UsuarioInexistenteException ex) {
                JOptionPane.showMessageDialog(currentFrame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==logout){
            if(MenuPrincipal.getUsuario()!=null) {
                usuario = null;
                carrinho.limparCarrinho();
                currentFrame.dispose();
                try {
                    new MenuPrincipal();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(currentFrame, "Nenhuma conta vinculada", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        for(int i=0; i<productsMenu.getItemCount(); i++){
            if(e.getSource()==productsMenu.getItem(i)){
                try {
                    alterarTela(new ListaDeProdutosPanel(productsMenu.getItem(i).getText()).getScrollPane());
                    previousCategorie = productsMenu.getItem(i).getText();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if(e.getSource()==cartoes){
            try {
                alterarTela(new CartoesGUI().getPanel());
            } catch (ContaExceptions.UsuarioInexistenteException ex) {
                JOptionPane.showMessageDialog(currentFrame, "Nenhuma conta vinculada", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==enderecos){
            try {
                alterarTela(new EnderecosGUI().getPanel());
            } catch (ContaExceptions.UsuarioInexistenteException ex) {
                JOptionPane.showMessageDialog(currentFrame, "Nenhuma conta vinculada", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==minhasCompras){
            try {
                alterarTela(new MinhasComprasPanel().getScrollPane());
            } catch (ContaExceptions.NomeInvalidoException ex) {
                JOptionPane.showMessageDialog(currentFrame, "Nenhuma conta vinculada", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ContaExceptions.UsuarioInexistenteException ex) {
                JOptionPane.showMessageDialog(currentFrame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex){
                throw new RuntimeException();
            }
        }
        if(e.getSource()==clientes){
            alterarTela(new AdminClientePanel().getPanel());
        }
        if(e.getSource()==produtos){
            alterarTela(new AdminProdutoPanel().getPanel());
        }
        if(e.getSource()==estatisticas){
            try {
                alterarTela(new AdminEstatisticasPanel().getPanel());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void setUsuario(Cliente usuario) {
        MenuPrincipal.usuario = usuario;
    }

    public static Cliente getUsuario(){
            return usuario;
    }

    public static JFrame getCurrentFrame(){
        return currentFrame;
    }

    public void disposeFrame() {
        currentFrame.dispose();
    }

    public static JMenu getAdminMenu() {
        return adminMenu;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        if(e.getSource()==inicioMenu){
            alterarTela(new InicioPanel().getPanel());
            System.out.println(currentFrame.getWidth());
        }
        if(e.getSource()==myShoppingCarMenu){
            try {
                alterarTela(new CarrinhoGUI().getPanel());
            } catch (ContaExceptions.UsuarioInexistenteException ex) {
                JOptionPane.showMessageDialog(currentFrame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }

    public static void alterarTela(JPanel panel){
        if (currentScrollPane == null) {
            currentFrame.remove(currentPanel);
            currentPanel = panel;
            currentFrame.add(currentPanel);
            currentFrame.revalidate();
            currentFrame.repaint();
        }else{
            currentFrame.remove(currentScrollPane);
            currentScrollPane = null;
            currentPanel = panel;
            currentFrame.add(currentPanel);
            currentFrame.revalidate();
            currentFrame.repaint();
        }
    }

    public static void alterarTela(JScrollPane pane){
        if (currentPanel == null) {
            currentFrame.remove(currentScrollPane);
            currentScrollPane = pane;
            currentFrame.add(currentScrollPane);
            currentFrame.revalidate();
            currentFrame.repaint();
        }else{
            currentFrame.remove(currentPanel);
            currentPanel = null;
            currentScrollPane = pane;
            currentFrame.add(currentScrollPane);
            currentFrame.revalidate();
            currentFrame.repaint();
        }
    }

    public static void atualizarProductsMenu() throws SQLException {
        productsMenu.removeAll();
        for(String s : CategoriasDAO.getCategorias()){
            productsMenu.add(new JMenuItem(s));
        }
        for(int i=0; i<productsMenu.getItemCount(); i++){
            int I = i;
            productsMenu.getItem(i).addActionListener(e -> {
                try {
                    alterarTela(new ListaDeProdutosPanel(productsMenu.getItem(I).getText()).getScrollPane());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        productsMenu.repaint();
    }
}
