package Model.DAO;

import Exceptions.ContaExceptions;
import Model.RN.CartaoRN;
import Model.RN.ClienteRN;
import Model.VO.Cartao;
import Model.VO.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartoesClientesDAO {
    static Connection conexao = ConnectClass.getInstance();

    public static void addCartaoClienteDAO(Cliente cliente, Cartao cartao) throws SQLException {
        if(cartao.getId()<=0){
            throw new SQLException();
        }
        String sql = "insert into cartoesclientes values(?, ?)";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, cliente.getId());
        preparedStatement.setInt(2, cartao.getId());
        preparedStatement.execute();
    }

    public static ArrayList<Cartao> getCartoesCliente(Cliente cliente) throws SQLException {
        String sql = "select * from cartoesclientes where id_cliente = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, cliente.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Cartao> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(CartaoRN.searchCartaoRN(resultSet.getInt("id_cartao")));
        }
        return temp;
    }

    public static ArrayList<Cliente> getClientesCartaoDAO(Cartao cartao) throws SQLException, ContaExceptions.UsuarioInexistenteException {
        String sql = "select * from cartoesclientes where id_cartao = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, cartao.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Cliente> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(ClienteRN.searchClienteRN(resultSet.getInt("id_cliente")));
        }
        return temp;
    }

    public static void deleteCartaoClienteDAO(Cliente cliente, Cartao cartao) throws SQLException {
        String sql = "delete from cartoesclientes where id_cliente = ? and id_cartao = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, cliente.getId());
        preparedStatement.setInt(2, cartao.getId());
        preparedStatement.execute();
    }

}

