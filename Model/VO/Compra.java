package Model.VO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Compra {
    private int id;
    private Cliente cliente;
    private ArrayList<Produto> produtos;
    private float total;
    private Timestamp data;
    private int qtdProdutos;
    private ArrayList<Double> qtdsPorProduto;
    private String formaPagamento;
    private float valorPago;
    private float troco;

    public Compra(int id, Cliente cliente, Timestamp data, ArrayList<Produto> produtos, ArrayList<Double> qtdsPorProduto, float total, int qtdProdutos, String formaPagamento, float valorPago, float troco){
        this.id = id;
        this.cliente = cliente;
        this.data = data;
        this.produtos = produtos;
        this.qtdsPorProduto = qtdsPorProduto;
        this.total = total;
        this.qtdProdutos = qtdProdutos;
        this.formaPagamento = formaPagamento;
        this.valorPago = valorPago;
        this.troco = troco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getQtdProdutos() {
        return qtdProdutos;
    }

    public void setQtdProdutos(int qtdProdutos) {
        this.qtdProdutos = qtdProdutos;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public ArrayList<Double> getQtdsPorProduto() {
        return qtdsPorProduto;
    }

    public void setQtdsPorProduto(ArrayList<Double> qtdsPorProduto) {
        this.qtdsPorProduto = qtdsPorProduto;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public float getValorPago() {
        return valorPago;
    }

    public void setValorPago(float valorPago) {
        this.valorPago = valorPago;
    }

    public float getTroco() {
        return troco;
    }

    public void setTroco(float troco) {
        this.troco = troco;
    }
}
