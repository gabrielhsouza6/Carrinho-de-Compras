package Model.DAO;

import Model.RN.EnderecoRN;
import Model.VO.Cliente;
import Model.VO.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnderecosClientesDAO {
    static Connection conexao = ConnectClass.getInstance();

    public static void addEnderecoClienteDAO(int idCliente, int idEndereco) throws SQLException {
        String sql = "insert into enderecosclientes values(?, ?)";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idCliente);
        preparedStatement.setInt(2, idEndereco);
        preparedStatement.execute();
    }

    public static ArrayList<Endereco> getEnderecosCliente(Cliente cliente) throws SQLException {
        String sql = "select * from enderecosclientes where id_cliente = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, cliente.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Endereco> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(EnderecoDAO.searchEndereco(resultSet.getInt("id_endereco")));
        }
        return temp;
    }

    public static ArrayList<Cliente> searchClientesEnderecoDAO(int idEnderecoAlterado) throws SQLException {
        String sql = "select * from enderecosclientes where id_endereco = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, idEnderecoAlterado);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Cliente> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(ClientesDAO.searchClienteDAO(resultSet.getInt("id_cliente")));
        }
        return temp;
    }

    public static void deleteEnderecoClienteDAO(Cliente cliente, Endereco endereco) throws SQLException {
        String sql = "delete from enderecosclientes where id_endereco = ? and id_cliente = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, endereco.getId());
        preparedStatement.setInt(2, cliente.getId());
        preparedStatement.execute();
    }

}
