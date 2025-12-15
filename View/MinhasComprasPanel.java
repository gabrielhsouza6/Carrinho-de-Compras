package View;

import Model.DAO.ComprasDAO;
import Exceptions.ContaExceptions;
import Model.RN.CompraRN;
import Model.VO.Cliente;
import Model.VO.Compra;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MinhasComprasPanel extends JScrollPane {
    JPanel panel;
    JScrollPane scrollPane;
    JPanel listaPanel;
    ArrayList<Compra> comprasCliente;
    static int numCompra;

    public MinhasComprasPanel() throws ContaExceptions.NomeInvalidoException, SQLException, ContaExceptions.UsuarioInexistenteException {
        if(MenuPrincipal.getUsuario()==null){
            throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário vinculado");
        }
        comprasCliente = CompraRN.searchComprasClienteRN(MenuPrincipal.getUsuario());
        listaPanel = new JPanel();
        scrollPane = new JScrollPane(listaPanel);
        numCompra = comprasCliente.size();

        listaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        for(int i=comprasCliente.size()-1; i>=0; i--){
            listaPanel.add(new OldCompraPanel(comprasCliente.get(i)).getPanel());
        }

        listaPanel.setPreferredSize(new Dimension(550, listaPanel.getComponentCount()*145));
    }

    public MinhasComprasPanel(Cliente cliente) throws ContaExceptions.NomeInvalidoException, SQLException, NullPointerException {
        if(cliente == null){
            throw new NullPointerException();
        }
        comprasCliente = CompraRN.searchComprasClienteRN(cliente);
        panel = new JPanel();
        listaPanel = new JPanel();
        scrollPane = new JScrollPane(listaPanel);
        numCompra = comprasCliente.size();

        listaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setPreferredSize(new Dimension(600, 295));

        for(int i=comprasCliente.size()-1; i>=0; i--){
            listaPanel.add(new OldCompraPanel(cliente, comprasCliente.get(i)).getPanel());
        }

        listaPanel.setPreferredSize(new Dimension(365, listaPanel.getComponentCount()*85));

        panel.setPreferredSize(new Dimension(600, 300));
        panel.add(scrollPane);

    }

    public JPanel getPanel() {
        return panel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
