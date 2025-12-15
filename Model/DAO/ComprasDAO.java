package Model.DAO;

import Model.RN.ClienteRN;
import Model.RN.ProdutosCompraRN;
import Model.VO.Cliente;
import Model.VO.Compra;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class ComprasDAO {
    static Connection conexao = ConnectClass.getInstance();

    public static void addCompraDAO(Compra compra) throws SQLException, IllegalArgumentException {
        String sql1 = "insert into compras(id_cliente, data, total, qtdProdutos, formaPagamento, valorPago, troco) " +
                                "values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement1 = conexao.prepareStatement(sql1);
        preparedStatement1.setInt(1, compra.getCliente().getId());
        preparedStatement1.setObject(2, compra.getData());
        preparedStatement1.setFloat(3, compra.getTotal());
        preparedStatement1.setInt(4, compra.getQtdProdutos());
        preparedStatement1.setString(5, compra.getFormaPagamento());
        preparedStatement1.setFloat(6, compra.getValorPago());
        preparedStatement1.setFloat(7, compra.getTroco());
        preparedStatement1.execute();

        String sql2 = "select * from compras order by id desc";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql2);
        int idCompra = -1;
        if(resultSet.next()){
            idCompra = resultSet.getInt("id");
        }

        ProdutosCompraRN.addProdutosCompraRN(idCompra, compra.getProdutos(), compra.getQtdsPorProduto());
    }

    public static ArrayList<Compra> searchComprasClienteDAO(Cliente cliente) throws SQLException {
        String sql = "select * from compras where id_cliente = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, cliente.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Compra> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(new Compra(
                    resultSet.getInt("id"),
                    ClientesDAO.searchClienteDAO(resultSet.getInt("id_cliente")),
                    resultSet.getTimestamp("data"),
                    ProdutosComprasDAO.getProdutosCompraDAO(resultSet.getInt("id")),
                    ProdutosComprasDAO.getQtdsCompraDAO(resultSet.getInt("id")),
                    resultSet.getFloat("total"),
                    resultSet.getInt("qtdProdutos"),
                    resultSet.getString("formaPagamento"),
                    resultSet.getFloat("valorPago"),
                    resultSet.getFloat("troco")
            ));
        }
        return temp;
    }

    public static ArrayList<Compra> getListCompras() throws SQLException {
        String sql = "select * from compras";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Compra> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(new Compra(
                    resultSet.getInt("id"),
                    ClientesDAO.searchClienteDAO(resultSet.getInt("id_cliente")),
                    resultSet.getTimestamp("data"),
                    ProdutosComprasDAO.getProdutosCompraDAO(resultSet.getInt("id")),
                    ProdutosComprasDAO.getQtdsCompraDAO(resultSet.getInt("id")),
                    resultSet.getFloat("total"),
                    resultSet.getInt("qtdProdutos"),
                    resultSet.getString("formaPagamento"),
                    resultSet.getFloat("valorPago"),
                    resultSet.getFloat("troco")
            ));
        }
        return temp;
    }

    public static int getComprasNumFaixaEt(String faixaEtaria) throws SQLException {
        int temp = 0;
        for(Compra c : getListCompras()){
            int idade = Period.between(c.getCliente().getDataDeNascimento().toLocalDate(), LocalDate.now()).getYears();
            if(ClienteRN.getFaixaEtaria(idade).equals(faixaEtaria)){
                temp++;
            }
        }
        return temp;
    }
}
