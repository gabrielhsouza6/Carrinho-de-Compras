package Model.RN;

import Model.DAO.EnderecoDAO;
import Model.DAO.EnderecosClientesDAO;
import Model.VO.Cliente;
import Model.VO.Endereco;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnderecoRN {

    public static void addEnderecoRN(Cliente cliente, Endereco endereco) throws SQLException, NumberFormatException {
        if(endereco.getRua() == null || endereco.getRua().trim().isEmpty()){
            throw new IllegalArgumentException("Rua Inválida");
        }
        else if(endereco.getBairro() == null || endereco.getBairro().trim().isEmpty()){
            throw new IllegalArgumentException("Bairro Inválido");
        }
        try {
            int q = Integer.parseInt(endereco.getNumero());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Número Inválido");
        }
        if(endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()){
            throw new IllegalArgumentException("Cidade Inválida");
        }
        else if(endereco.getEstado() == null || endereco.getEstado().trim().isEmpty()){
            throw new IllegalArgumentException("Estado Inválido");
        }

        Endereco enderecoIgual = EnderecoDAO.searchEndereco(endereco.getRua(), endereco.getBairro(), endereco.getNumero(), endereco.getCidade(), endereco.getEstado());
        if(enderecoIgual == null) {
            EnderecoDAO.addEnderecoDAO(cliente, endereco);
        }else if(!EnderecosClienteRN.getEnderecosIdClienteRN(cliente).contains(enderecoIgual.getId())){
            EnderecosClienteRN.addEnderecoClienteRN(cliente.getId(), enderecoIgual.getId());
        }
    }

    public static void updateEnderecoRN(Endereco enderecoAlterado, Cliente cliente) throws SQLException, NumberFormatException {
        if(enderecoAlterado.getRua() == null || enderecoAlterado.getRua().trim().isEmpty()){
            throw new IllegalArgumentException("Rua Inválida");
        }
        try {
            int q = Integer.parseInt(enderecoAlterado.getNumero());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Número Inválido");
        }
        if(enderecoAlterado.getBairro() == null || enderecoAlterado.getBairro().trim().isEmpty()){
            throw new IllegalArgumentException("Bairro Inválido");
        }
        else if(enderecoAlterado.getCidade() == null || enderecoAlterado.getCidade().trim().isEmpty()){
            throw new IllegalArgumentException("Cidade Inválida");
        }
        else if(enderecoAlterado.getEstado() == null || enderecoAlterado.getEstado().trim().isEmpty()){
            throw new IllegalArgumentException("Estado Inválido");
        }

        Endereco enderecoIgual = EnderecoDAO.searchEndereco(enderecoAlterado.getRua(), enderecoAlterado.getBairro(), enderecoAlterado.getNumero(), enderecoAlterado.getCidade(), enderecoAlterado.getEstado());
        int clientesNoEnderecoAlterado = EnderecosClientesDAO.searchClientesEnderecoDAO(enderecoAlterado.getId()).size();

        if(enderecoIgual == null){
            if (clientesNoEnderecoAlterado == 1) {
                EnderecoDAO.updateEnderecoDAO(enderecoAlterado);
            }
            else if(clientesNoEnderecoAlterado > 1){
                EnderecosClientesDAO.deleteEnderecoClienteDAO(cliente, enderecoAlterado);
                EnderecoRN.addEnderecoRN(cliente,
                        new Endereco(
                                0,
                                enderecoAlterado.getRua(),
                                enderecoAlterado.getBairro(),
                                enderecoAlterado.getNumero(),
                                enderecoAlterado.getCidade(),
                                enderecoAlterado.getEstado()
                        ));
            }
        }
        else{
            if(clientesNoEnderecoAlterado == 1){
                if(enderecoAlterado.getId()!=enderecoIgual.getId()){
                    deleteEnderecoRN(enderecoAlterado, cliente);
                }
                if(!EnderecosClienteRN.getEnderecosIdClienteRN(cliente).contains(enderecoIgual.getId())){
                    EnderecosClientesDAO.addEnderecoClienteDAO(cliente.getId(), enderecoIgual.getId());
                }
            }
            else if(clientesNoEnderecoAlterado > 1){
                EnderecosClientesDAO.deleteEnderecoClienteDAO(cliente, enderecoAlterado);
                EnderecosClientesDAO.addEnderecoClienteDAO(cliente.getId(), enderecoIgual.getId());
            }
        }
    }

    public static void deleteEnderecoRN(Endereco endereco, Cliente cliente) throws SQLException {
        if(endereco == null || cliente == null){
            throw new NullPointerException();
        }
        EnderecosClientesDAO.deleteEnderecoClienteDAO(cliente, endereco);
        if(EnderecosClientesDAO.searchClientesEnderecoDAO(endereco.getId()).isEmpty()){
            EnderecoDAO.deleteEnderecoDAO(endereco);
        }
    }

}
