package Model.RN;

import Model.DAO.CartoesClientesDAO;
import Model.VO.Cartao;
import Model.VO.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public class CartoesClienteRN {

    public static void addCartaoClienteRN(Cliente cliente, Cartao cartao) throws SQLException {
        if(cliente == null || cartao == null){
            throw new NullPointerException();
        }
        if(cartao.getId()<=0){
            throw new SQLException();
        }

        CartoesClientesDAO.addCartaoClienteDAO(cliente, cartao);
    }

    public static void deleteCartaoClienteRN(Cliente cliente, Cartao cartao) throws SQLException {
        if(cliente == null || cartao == null){
            throw new NullPointerException();
        }

        CartoesClientesDAO.deleteCartaoClienteDAO(cliente, cartao);
    }

    public static ArrayList<Integer> searchCartoesIdClienteRN(Cliente cliente) throws SQLException {
        if(cliente == null){
            throw new NullPointerException();
        }
        ArrayList<Integer> temp = new ArrayList<>();
        for(Cartao c : CartoesClientesDAO.getCartoesCliente(cliente)){
            temp.add(c.getId());
        }
        return temp;
    }
}
