package Model.DAO;

import Model.VO.Produto;

import java.sql.*;
import java.util.ArrayList;

public class ProdutosComprasDAO {
    static Connection conexao = ConnectClass.getInstance();

    public static ArrayList<Produto> getProdutosCompraDAO(int idCompra) throws SQLException {
        String sql = "select * from produtoscompras where id_compra = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCompra);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Produto> temp= new ArrayList<>();
        while(resultSet.next()){
            Produto tempProd = ProdutosDAO.searchProdutoDAO(resultSet.getInt("id_produto"));
            if(tempProd!=null){
                temp.add(tempProd);
            }else{
                temp.add(new Produto(
                        0,
                        resultSet.getString("descricao"),
                        resultSet.getDouble("preco"),
                        resultSet.getDouble("desconto"),
                        "Deletado",
                        resultSet.getString("medida"),
                        "src/Imagens/foto generica.png"
                ));
            }
        }
        return temp;
    }

    public static ArrayList<Double> getQtdsCompraDAO(int idCompra) throws SQLException {
        String sql = "select * from produtoscompras where id_compra = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCompra);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Double> temp= new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getDouble("quantidade"));
        }
        return temp;
    }

    public static ArrayList<String> getMedsCompraDAO(int idCompra) throws SQLException {
        String sql = "select * from produtoscompras where id_compra = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCompra);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<String> temp= new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getString("medida"));
        }
        return temp;
    }

    public static ArrayList<String> getDescsCompraDAO(int idCompra) throws SQLException {
        String sql = "select * from produtoscompras where id_compra = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCompra);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<String> temp= new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getString("descricao"));
        }
        return temp;
    }

    public static ArrayList<Double> getPrecosCompraDAO(int idCompra) throws SQLException {
        String sql = "select * from produtoscompras where id_compra = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCompra);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Double> temp= new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getDouble("preco"));
        }
        return temp;
    }

    public static ArrayList<Double> getDsctsCompraDAO(int idCompra) throws SQLException {
        String sql = "select * from produtoscompras where id_compra = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCompra);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Double> temp= new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getDouble("desconto"));
        }
        return temp;
    }

    public static void addProdutosCompraDAO(int idCompra, ArrayList<Produto> produtos, ArrayList<Double> qtdsPorProduto) throws SQLException {
        String sql = "insert into produtoscompras values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCompra);
        for(int i=0; i<produtos.size(); i++){
            preparedStatement.setInt(2, produtos.get(i).getId());
            preparedStatement.setDouble(3, produtos.get(i).getPreco());
            preparedStatement.setString(4, produtos.get(i).getDescricao());
            preparedStatement.setString(5, produtos.get(i).getMedida());
            preparedStatement.setDouble(6, qtdsPorProduto.get(i));
            preparedStatement.setDouble(7, produtos.get(i).getDesconto());
            preparedStatement.execute();
        }
    }

//    public static ArrayList<Double> getPrecosAllComprasDAO() throws SQLException {
//        String sql = "select * from produtoscompras";
//        Statement statement = conexao.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//        ArrayList<Double> temp= new ArrayList<>();
//        while(resultSet.next()){
//            temp.add(resultSet.getDouble("preco"));
//        }
//        return temp;
//    }
}
