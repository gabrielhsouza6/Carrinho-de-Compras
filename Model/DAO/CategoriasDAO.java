package Model.DAO;


import java.sql.*;
import java.util.ArrayList;

public class CategoriasDAO {
    static Connection conexao = ConnectClass.getInstance();

    public static ArrayList<String> getCategorias() throws SQLException {
        String sql = "select categoria from categorias";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<String> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getString("categoria"));
        }
        return temp;
    }

    public static void addCategoriaDAO(String novaCategoria) throws SQLException {
        String sql = "insert into categorias(categoria) values(?)";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, novaCategoria);
        preparedStatement.execute();
    }

    public static void deleteCategoriaDAO(String categoria) throws SQLException {
        String sql = "delete from categorias where categoria = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, categoria);
        preparedStatement.execute();
    }

    public static String searchCategoria(int idCategoria) throws SQLException {
        String sql = "select * from categorias where id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCategoria);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getString("categoria");
        }
        return null;
    }

    public static Integer getIdCategoria(String nomeCategoria) throws SQLException {
        String sql = "select * from categorias where categoria = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, nomeCategoria);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt("id");
        }
        return null;
    }

    public static int getNumVendasCategoria(int idCategoria) throws SQLException {
        String sql = "select id_compra, categoria from produtoscompras " +
                        "inner join produtos on produtos.id = id_produto where categoria = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCategoria);
        ResultSet resultSet = preparedStatement.executeQuery();
        int temp = 0;
        while(resultSet.next()){
            temp++;
        }
        return temp;
    }

    public static int getNumProdutosCategoria(int idCategoria) throws SQLException {
        String sql = "select categoria from produtos where categoria = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCategoria);
        ResultSet resultSet = preparedStatement.executeQuery();
        int temp = 0;
        while(resultSet.next()){
            temp++;
        }
        return temp;
    }
}
