package Model.RN;

import Model.DAO.ComprasDAO;
import Model.VO.Cliente;
import Model.VO.Compra;
import Model.VO.Produto;

import java.sql.*;
import java.util.ArrayList;

public class CompraRN {

    public static void addCompraRN(Compra compra) throws SQLException {
        Cliente cliente = compra.getCliente();
        ArrayList<Produto> produtos = compra.getProdutos();
        ArrayList<Double> qtdsPorProduto = compra.getQtdsPorProduto();
        float total = compra.getTotal();
        int qtdProdutos = compra.getQtdProdutos();
        String formaPagamento = compra.getFormaPagamento();
        float valorPago = compra.getValorPago();
        float troco = compra.getTroco();

        if (cliente == null){
            throw new NullPointerException("Cliente Inválido");
        }
        else if (produtos == null || produtos.contains(null) || qtdsPorProduto == null || qtdsPorProduto.contains(null)){
            throw new IllegalArgumentException("Dados Inconsistentes");
        }
        else if (total <= 0){
            throw new IllegalArgumentException("Valor de total inválido");
        }
        else if (qtdProdutos <= 0){
            throw new IllegalArgumentException("Quantidade de produtos inválida");
        }
        else if (formaPagamento == null || !formaPagamento.equals("Dinheiro") && !formaPagamento.equals("Cartão") && !formaPagamento.equals("PIX")){
            throw new IllegalArgumentException("Forma de pagamento inválida");
        }
        else if (valorPago <= 0 || valorPago < total || valorPago != total+troco){
            throw new IllegalArgumentException("Valor pago inválido");
        }
        else if (troco >= total){
            throw new IllegalArgumentException("Troco Inválido");
        }

        ComprasDAO.addCompraDAO(compra);
    }

    public static ArrayList<Compra> searchComprasClienteRN(Cliente cliente) throws SQLException, IllegalArgumentException {
        if (cliente == null){
            throw new IllegalArgumentException("Cliente Inválido");
        }

        return ComprasDAO.searchComprasClienteDAO(cliente);
    }
}
