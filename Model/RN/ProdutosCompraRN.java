package Model.RN;

import Model.DAO.CategoriasDAO;
import Model.DAO.ComprasDAO;
import Model.DAO.ProdutosComprasDAO;
import Model.VO.Compra;
import Model.VO.Produto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ProdutosCompraRN {

    public static void addProdutosCompraRN(int idCompra, ArrayList<Produto> produtos, ArrayList<Double> qtdsPorProduto) throws SQLException, IllegalArgumentException {
        if(idCompra<=0){
            throw new IllegalArgumentException("Compra Inválida");
        }
        else if (produtos == null || produtos.contains(null)
                    || qtdsPorProduto == null || qtdsPorProduto.contains(null)){
            throw new IllegalArgumentException("Dados Inconsistentes");
        }

        ProdutosComprasDAO.addProdutosCompraDAO(idCompra, produtos, qtdsPorProduto);
    }

}
