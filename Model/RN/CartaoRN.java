package Model.RN;

import Exceptions.ContaExceptions;
import Model.DAO.CartaoDAO;
import Model.DAO.CartoesClientesDAO;
import Model.VO.Cartao;
import Model.VO.Cliente;

import java.sql.SQLException;
import java.util.Objects;

public class CartaoRN {

    public static void addCartaoRN(Cliente cliente, Cartao cartao) throws SQLException, IllegalArgumentException {
        Cartao cartaoIgual = searchCartaoRN(cartao.getNumCartao(), cartao.getNomeTitular(), cartao.getDtValidade(), cartao.getCVV(), cartao.getTipo());

        if(cartaoIgual == null) {
            if(cartao.getNumCartao() == null || cartao.getNumCartao().trim().isEmpty() || cartao.getNumCartao().length()!=19){
                throw new IllegalArgumentException("Numero de cartão inválido");
            }
            else if (cartao.getNomeTitular() == null || cartao.getNomeTitular().trim().isEmpty()){
                throw new IllegalArgumentException("Nome de titular inválido");
            }
            else if (cartao.getDtValidade() == null || cartao.getDtValidade().trim().isEmpty() || cartao.getDtValidade().length()!=5){
                throw new IllegalArgumentException("Data de validade inválida");
            }
            else if(cartao.getCVV() == null || cartao.getCVV().trim().isEmpty() || cartao.getCVV().length()!=3){
                throw new IllegalArgumentException("CVV Inválido");
            }
            else if (!Objects.equals(cartao.getTipo(), "Crédito") && !Objects.equals(cartao.getTipo(), "Débito")){
                throw new IllegalArgumentException("Tipo Inválido");
            }
            CartaoDAO.addCartaoDAO(cliente, cartao);
        }
        else if(!CartoesClienteRN.searchCartoesIdClienteRN(cliente).contains(cartaoIgual.getId())){
            CartoesClienteRN.addCartaoClienteRN(cliente, cartaoIgual);
        }
        else {
            throw new IllegalArgumentException("Cartão já associado ao login");
        }
    }

    public static Cartao searchCartaoRN(int idCartao) throws SQLException, IllegalArgumentException {
        if(!CartaoDAO.getCartoesIdDAO().contains(idCartao)){
            throw new IllegalArgumentException("Id Inválido");
        }
        return CartaoDAO.searchCartaoDAO(idCartao);
    }

    public static Cartao searchCartaoRN(String numCartao, String nomeTitular, String dtValidade, String CVV, String tipo) throws SQLException, IllegalArgumentException {
        if(numCartao == null || numCartao.trim().isEmpty() || numCartao.length()!=19){
            throw new IllegalArgumentException("Numero de cartão inválido");
        }
        else if (nomeTitular == null || nomeTitular.trim().isEmpty()){
            throw new IllegalArgumentException("Nome de titular inválido");
        }
        else if (dtValidade == null || dtValidade.trim().isEmpty() || dtValidade.length()!=5){
            throw new IllegalArgumentException("Data de validade inválida");
        }
        else if(CVV == null || CVV.trim().isEmpty() || CVV.length()!=3){
            throw new IllegalArgumentException("CVV Inválido");
        }
        else if (!Objects.equals(tipo, "Crédito") && !Objects.equals(tipo, "Débito")){
            throw new IllegalArgumentException("Tipo Inválido");
        }

        return CartaoDAO.searchCartaoDAO(numCartao, nomeTitular, dtValidade, CVV, tipo);
    }

    public static void updateCartaoRN(Cliente cliente, Cartao cartaoAlterado) throws SQLException, ContaExceptions.UsuarioInexistenteException {
        Cartao cartaoIgual = searchCartaoRN(cartaoAlterado.getNumCartao(), cartaoAlterado.getNomeTitular(), cartaoAlterado.getDtValidade(), cartaoAlterado.getCVV(), cartaoAlterado.getTipo());
        int clientesComCartaoAlterado = CartoesClientesDAO.getClientesCartaoDAO(cartaoAlterado).size();

        if(cliente == null){
            throw new NullPointerException();
        }

        if(cartaoIgual == null){
            if (clientesComCartaoAlterado == 1) {
                CartaoDAO.updateCartaoDAO(cartaoAlterado);
            }
            else if(clientesComCartaoAlterado > 1){
                CartoesClienteRN.deleteCartaoClienteRN(cliente, cartaoAlterado);
                addCartaoRN(cliente, cartaoAlterado);
            }
        }
        else {
            if (clientesComCartaoAlterado == 1) {
                if (cartaoAlterado.getId() != cartaoIgual.getId()) {
                    deleteCartaoRN(cliente, cartaoAlterado);
                }
                if (!CartoesClienteRN.searchCartoesIdClienteRN(cliente).contains(cartaoIgual.getId())) {
                    CartoesClienteRN.addCartaoClienteRN(cliente, cartaoIgual);
                }
            } else if (clientesComCartaoAlterado > 1) {
                CartoesClienteRN.deleteCartaoClienteRN(cliente, cartaoAlterado);
                CartoesClienteRN.addCartaoClienteRN(cliente, cartaoAlterado);
            }
        }
    }

    public static void deleteCartaoRN(Cliente cliente, Cartao cartao) throws SQLException, ContaExceptions.UsuarioInexistenteException {
        CartoesClientesDAO.deleteCartaoClienteDAO(cliente, cartao);
        if(CartoesClientesDAO.getClientesCartaoDAO(cartao).isEmpty()){
            CartaoDAO.deleteCartaoDAO(cartao);
        }
    }
}
