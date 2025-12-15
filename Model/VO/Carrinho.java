package Model.VO;

import java.util.ArrayList;
import java.util.Objects;

public class Carrinho {
    private ArrayList<Produto> carrinhoProd = new ArrayList<>();
    private ArrayList<Double> carrinhoQtds = new ArrayList<>();

    public ArrayList<Produto> getCarrinhoProd() {
        return carrinhoProd;
    }

    public void setCarrinhoProd(ArrayList<Produto> carrinhoProd) {
        this.carrinhoProd = carrinhoProd;
    }

    public ArrayList<Double> getCarrinhoQtds() {
        return carrinhoQtds;
    }

    public void setCarrinhoQtds(ArrayList<Double> carrinhoQtds) {
        this.carrinhoQtds = carrinhoQtds;
    }

    public void addProdutoCarrinho(Produto p, double qtd){
        carrinhoProd.add(p);
        carrinhoQtds.add(qtd);
    }

    public boolean verificarCarrinho(String descricao){
        for(Produto p : carrinhoProd){
            if(Objects.equals(p.getDescricao(), descricao)){
                return true;
            }
        }
        return false;
    }

    public void limparCarrinho(){
        carrinhoProd.clear();
        carrinhoQtds.clear();
    }
}
