package View;

import Model.DAO.CategoriasDAO;
import Model.DAO.ComprasDAO;
import Model.DAO.ProdutosComprasDAO;
import Model.RN.ClienteRN;
import Model.RN.ProdutosCompraRN;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AdminEstatisticasPanel extends JPanel {
    JPanel panel;
    JPanel scrollPanePanel;
    JScrollPane scrollPane;
    ///Dados
    //Venda por Categoria
    ArrayList<String> categoriaNomes = CategoriasDAO.getCategorias();
    ArrayList<Integer> categoriaVendas = getCategoriaIntData();
    //Sexo dos Clientes
    ArrayList<String> sexoNomes = new ArrayList<>(Arrays.asList("Masculino", "Feminino"));
    ArrayList<Integer> sexoNums = new ArrayList<>(Arrays.asList(ClienteRN.getSexoCount("Masculino"), ClienteRN.getSexoCount("Feminino")));
    //Faixa Etária dos Clientes
    ArrayList<String> faixaEtNomes = new ArrayList<>(Arrays.asList("Criança", "Adolescente", "Jovem-adulto", "Adulto", "Idoso"));
    ArrayList<Integer> faixaEtNums = getFaixaEtariaIntData();
    //Vendas por Faixa Etária
    /// String Data - faixaEtNomes
    ArrayList<Integer> faixaEtVendas = getFaixaEtariaVendData();

    public AdminEstatisticasPanel() throws SQLException {
        panel = new JPanel();
        scrollPanePanel= new JPanel();
        scrollPane = new JScrollPane(scrollPanePanel);

        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setPreferredSize(new Dimension(900, 540));

        scrollPanePanel.add(new AdminEstatisticasInfoPanel("Venda por Categoria", categoriaVendas, categoriaNomes).getPanel());
        scrollPanePanel.add(new AdminEstatisticasInfoPanel("Sexo dos Clientes", sexoNums, sexoNomes).getPanel());
        scrollPanePanel.add(new AdminEstatisticasInfoPanel("Faixa Etária dos Clientes", faixaEtNums, faixaEtNomes).getPanel());
        scrollPanePanel.add(new AdminEstatisticasInfoPanel("Vendas por Faixa Etária", faixaEtVendas, faixaEtNomes).getPanel());

        scrollPanePanel.setPreferredSize(new Dimension(900, scrollPanePanel.getComponentCount()*340));
        panel.setLayout(new BorderLayout());

        panel.add(scrollPane);
    }

    public JPanel getPanel() {
        return panel;
    }

    public ArrayList<Integer> getCategoriaIntData() throws SQLException {
        ArrayList<Integer> temp = new ArrayList<>();
        for(String s : categoriaNomes){
            temp.add(CategoriasDAO.getNumVendasCategoria(CategoriasDAO.getIdCategoria(s)));
        }
        return temp;
    }

    public ArrayList<Integer> getFaixaEtariaIntData() throws SQLException {
        ArrayList<Integer> temp = new ArrayList<>();
        for(String s : faixaEtNomes){
            temp.add(ClienteRN.getFaixaEtCount(s));
        }
        return temp;
    }

    public ArrayList<Integer> getFaixaEtariaVendData() throws SQLException {
        ArrayList<Integer> temp = new ArrayList<>();
        for(String s : faixaEtNomes){
            temp.add(ComprasDAO.getComprasNumFaixaEt(s));
        }
        return temp;
    }
}
