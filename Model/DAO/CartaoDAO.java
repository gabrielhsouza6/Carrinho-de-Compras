package Model.DAO;

import Model.RN.CartoesClienteRN;
import Model.VO.Cartao;
import Model.VO.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class CartaoDAO {
    static Connection conexao = ConnectClass.getInstance();

    public static void addCartaoDAO(Cliente cliente, Cartao cartao) throws SQLException {
        String sql = "insert into cartoes(numCartao, nomeTitular, dtValidade, CVV, tipo) values(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, cartao.getNumCartao());
        preparedStatement.setString(2, cartao.getNomeTitular());
        preparedStatement.setString(3, cartao.getDtValidade());
        preparedStatement.setString(4, cartao.getCVV());
        preparedStatement.setString(5, cartao.getTipo());
        preparedStatement.execute();

        String sql2 = "select * from cartoes order by id desc";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql2);
        int idCartao = -1;
        if (resultSet.next()) {
            idCartao = resultSet.getInt("id");
        }
        cartao.setId(idCartao);
        CartoesClienteRN.addCartaoClienteRN(cliente, cartao);
    }

    public static void updateCartaoDAO(Cartao cartaoAlterado) throws SQLException {
        String sql = "update cartoes set numCartao = ?, nomeTitular = ?, dtValidade = ?, CVV = ?, tipo = ? where id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, cartaoAlterado.getNumCartao());
        preparedStatement.setString(2, cartaoAlterado.getNomeTitular());
        preparedStatement.setString(3, cartaoAlterado.getDtValidade());
        preparedStatement.setString(4, cartaoAlterado.getCVV());
        preparedStatement.setString(5, cartaoAlterado.getTipo());
        preparedStatement.setInt(6, cartaoAlterado.getId());
        preparedStatement.execute();
    }

    public static Cartao searchCartaoDAO(int idCartao) throws SQLException {
        String sql = "select * from cartoes where id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCartao);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Cartao(
                    resultSet.getInt("id"),
                    resultSet.getString("numCartao"),
                    resultSet.getString("nomeTitular"),
                    resultSet.getString("dtValidade"),
                    resultSet.getString("CVV"),
                    resultSet.getString("tipo")
            );
        }
        return null;
    }

    public static Cartao searchCartaoDAO(String numCartao, String nomeTitular, String dtValidade, String CVV, String tipo) throws SQLException {
        String sql = "select * from cartoes";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            Cartao temp =  new Cartao(
                    resultSet.getInt("id"),
                    resultSet.getString("numCartao"),
                    resultSet.getString("nomeTitular"),
                    resultSet.getString("dtValidade"),
                    resultSet.getString("CVV"),
                    resultSet.getString("tipo")
            );
            if(Objects.equals(temp.getNumCartao(), numCartao) && Objects.equals(temp.getNomeTitular(), nomeTitular) &&
                    Objects.equals(temp.getDtValidade(), dtValidade) && Objects.equals(temp.getCVV(), CVV)
                    && Objects.equals(temp.getTipo(), tipo)){
                return temp;
            }
        }
        return null;
    }

    public static void deleteCartaoDAO(Cartao cartao) throws SQLException {
        String sql = "delete from cartoes where id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, cartao.getId());
        preparedStatement.execute();
    }

    public static ArrayList<Integer> getCartoesIdDAO() throws SQLException {
        String sql = "select * from cartoes";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<Integer> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getInt("id"));
        }
        return temp;
    }

    public static ArrayList<Cartao> getCartoesDAO() throws SQLException {
        String sql = "select * from cartoes";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<Cartao> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(new Cartao(
                    resultSet.getInt("id"),
                    resultSet.getString("numCartao"),
                    resultSet.getString("nomeTitular"),
                    resultSet.getString("dtValidade"),
                    resultSet.getString("CVV"),
                    resultSet.getString("tipo")
            ));
        }
        return temp;
    }
}
