package Model.RN;

import Model.DAO.ClientesDAO;
import Model.DAO.EnderecoDAO;
import Model.DAO.EnderecosClientesDAO;
import Model.VO.Cliente;
import Model.VO.Endereco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnderecosClienteRN {

    public static void addEnderecoClienteRN(int idCliente, int idEndereco) throws SQLException {
        if(idEndereco<=0){
            throw new SQLException();
        }
        if(!ClientesDAO.getIds().contains(idCliente) || !EnderecoDAO.getIds().contains(idEndereco)){
            throw new IllegalArgumentException("Cliente ou Endereço Inválido");
        }

        EnderecosClientesDAO.addEnderecoClienteDAO(idCliente, idEndereco);
    }

    public static void excluirEnderecoClienteRN(Cliente cliente, Endereco endereco) throws SQLException {
        if(cliente == null || endereco == null){
            throw new NullPointerException();
        }

        EnderecosClientesDAO.deleteEnderecoClienteDAO(cliente, endereco);
    }

    public static ArrayList<Integer> getEnderecosIdClienteRN(Cliente cliente) throws SQLException {
        if(cliente == null){
            throw new NullPointerException();
        }

        ArrayList<Integer> temp = new ArrayList<>();
        for(Endereco end : EnderecosClientesDAO.getEnderecosCliente(cliente)){
            temp.add(end.getId());
        }
        return temp;
    }

}
