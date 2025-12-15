package Model.DAO;

import Model.RN.EnderecosClienteRN;
import Model.VO.Cliente;
import Model.VO.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class EnderecoDAO {
    static Connection conexao = ConnectClass.getInstance();

    public static void addEnderecoDAO(Cliente cliente, Endereco endereco) throws SQLException {
        String sql = "insert into enderecos(rua, bairro, numero, cidade, estado) values(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, endereco.getRua());
        preparedStatement.setString(2, endereco.getBairro());
        preparedStatement.setString(3, endereco.getNumero());
        preparedStatement.setString(4, endereco.getCidade());
        preparedStatement.setString(5, endereco.getEstado());
        preparedStatement.execute();

        String sql2 = "select * from enderecos order by id desc";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql2);
        int idEndereco = -1;
        if (resultSet.next()) {
            idEndereco = resultSet.getInt("id");
        }
        EnderecosClientesDAO.addEnderecoClienteDAO(cliente.getId(), idEndereco);
    }

    public static Endereco searchEndereco(int idEndereco) throws SQLException {
        String sql = "select * from enderecos where id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idEndereco);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Endereco(
                    resultSet.getInt("id"),
                    resultSet.getString("rua"),
                    resultSet.getString("bairro"),
                    resultSet.getString("numero"),
                    resultSet.getString("cidade"),
                    resultSet.getString("estado")
            );
        }
        return null;
    }

    public static Endereco searchEndereco(String rua, String bairro, String numero, String cidade, String estado) throws SQLException {
        String sql = "select * from enderecos";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            Endereco temp = new Endereco(
                    resultSet.getInt("id"),
                    resultSet.getString("rua"),
                    resultSet.getString("bairro"),
                    resultSet.getString("numero"),
                    resultSet.getString("cidade"),
                    resultSet.getString("estado")
            );
            if(Objects.equals(temp.getRua(), rua) && Objects.equals(temp.getBairro(), bairro) &&
                    Objects.equals(temp.getNumero(), numero) && Objects.equals(temp.getCidade(), cidade)
                          && Objects.equals(temp.getEstado(), estado)){
                return temp;
            }
        }
        return null;
    }

    public static ArrayList<Endereco> getEnderecosClienteDAO(Cliente cliente) throws SQLException {
        ArrayList<Endereco> temp = new ArrayList<>();
        ArrayList<Integer> idEnderecos = EnderecosClienteRN.getEnderecosIdClienteRN(cliente);
        for(Integer i : idEnderecos){
            temp.add(searchEndereco(i));
        }
        return temp;
    }

    public static void updateEnderecoDAO(Endereco enderecoAlterado) throws SQLException {
        String sql = "update enderecos set rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ? where id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, enderecoAlterado.getRua());
        preparedStatement.setString(2, enderecoAlterado.getNumero());
        preparedStatement.setString(3, enderecoAlterado.getBairro());
        preparedStatement.setString(4, enderecoAlterado.getCidade());
        preparedStatement.setString(5, enderecoAlterado.getEstado());
        preparedStatement.setInt(6, enderecoAlterado.getId());
        preparedStatement.execute();
    }

    public static void deleteEnderecoDAO(Endereco endereco) throws SQLException {
        String sql = "delete from enderecos where id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, endereco.getId());
        preparedStatement.execute();
    }

    public static ArrayList<Integer> getIds() throws SQLException {
        String sql = "select * from enderecos";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Integer> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getInt("id"));
        }
        return temp;
    }
}
