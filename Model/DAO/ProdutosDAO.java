package Model.DAO;

import Exceptions.ContaExceptions;
import Model.VO.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class ProdutosDAO {
    static Connection conexao = ConnectClass.getInstance();

    public static ArrayList<Produto> getListaDeProdutosDAO() throws SQLException {
        String sql = "select * from produtos order by descricao";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Produto> tempLista = new ArrayList<>();
        while(resultSet.next()){
            tempLista.add(new Produto(
                    resultSet.getInt("id"),
                    resultSet.getString("descricao"),
                    resultSet.getDouble("preco"),
                    resultSet.getDouble("desconto"),
                    CategoriasDAO.searchCategoria(resultSet.getInt("categoria")),
                    resultSet.getString("medida"),
                    resultSet.getString("foto")
            ));
        }
        return tempLista;
    }

    public static void addProdutoDAO(Produto produto) throws SQLException {
        String sql = "insert into produtos(descricao, preco, desconto, categoria, medida, foto) values(?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, produto.getDescricao());
        preparedStatement.setDouble(2, produto.getPreco());
        preparedStatement.setDouble(3, produto.getDesconto());
        preparedStatement.setInt(4, CategoriasDAO.getIdCategoria(produto.getCategoria()));
        preparedStatement.setString(5, produto.getMedida());
        preparedStatement.setString(6, produto.getFoto());
        preparedStatement.execute();

    }

    public static Produto searchProdutoDAO(String descricao) throws SQLException, ContaExceptions.NomeInvalidoException {
        String sql = "select * from produtos";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            int idBD = resultSet.getInt("id");
            String descricaoBD = resultSet.getString("descricao");
            Double precoBD = resultSet.getDouble("preco");
            Double descontoBD = resultSet.getDouble("desconto");
            String categoriaBD = CategoriasDAO.searchCategoria(resultSet.getInt("categoria"));
            String medidaBD = resultSet.getString("medida");
            String fotoBD = resultSet.getString("foto");
            if(Objects.equals(descricaoBD, descricao)){
                return new Produto(idBD, descricao, precoBD, descontoBD, categoriaBD, medidaBD, fotoBD);
            }
        }
        throw new ContaExceptions.NomeInvalidoException("Não há nenhum produto vinculado a esse nome");
    }

    public static Produto searchProdutoDAO(int id) throws SQLException {
        String sql = "select * from produtos";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            Integer idBD = resultSet.getInt("id");
            String descricaoBD = resultSet.getString("descricao");
            Double precoBD = resultSet.getDouble("preco");
            Double descontoBD = resultSet.getDouble("desconto");
            String categoriaBD = CategoriasDAO.searchCategoria(resultSet.getInt("categoria"));
            String medidaBD = resultSet.getString("medida");
            String fotoBD = resultSet.getString("foto");
            if(idBD == id){
                return new Produto(idBD, descricaoBD, precoBD, descontoBD, categoriaBD, medidaBD, fotoBD);
            }
        }
        return null;
    }

    public static ArrayList<String> getNomes() throws SQLException {
        String sql = "select descricao from produtos order by descricao";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<String> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getString("descricao"));
        }
        return temp;
    }

    public static void updateProdutoDAO(Produto produtoAlterado) throws SQLException {
        String sql = "update produtos set descricao = ?, preco = ?, desconto = ?, categoria = ?, medida = ?, foto = ? where id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, produtoAlterado.getDescricao());
        preparedStatement.setDouble(2, produtoAlterado.getPreco());
        preparedStatement.setDouble(3, produtoAlterado.getDesconto());
        preparedStatement.setInt(4, CategoriasDAO.getIdCategoria(produtoAlterado.getCategoria()));
        preparedStatement.setString(5, produtoAlterado.getMedida());
        preparedStatement.setString(6, produtoAlterado.getFoto());
        preparedStatement.setInt(7, produtoAlterado.getId());
        preparedStatement.execute();
    }

    public static void deleteProdutoDAO(Produto produto) throws SQLException {
        String sql = "delete from produtos where id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, produto.getId());
        preparedStatement.execute();
    }

}
