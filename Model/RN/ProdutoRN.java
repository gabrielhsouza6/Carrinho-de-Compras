package Model.RN;

import Model.DAO.CategoriasDAO;
import Model.DAO.ProdutosDAO;
import Exceptions.ContaExceptions;
import Model.VO.Produto;

import java.sql.SQLException;
import java.util.Objects;

public class ProdutoRN {

    public static Produto searchProdutoRN(String descricao) throws SQLException, ContaExceptions.NomeInvalidoException, NullPointerException {
        if(descricao == null){
            throw new NullPointerException();
        }

        return ProdutosDAO.searchProdutoDAO(descricao);
    }

    public static Produto searchProdutoRN(int id) throws SQLException, IllegalArgumentException {
        if(id < 0){
            throw new IllegalArgumentException();
        }

        return ProdutosDAO.searchProdutoDAO(id);
    }

    public static void addProdutoRN(Produto produto) throws NullPointerException, SQLException, IllegalArgumentException {
        if(produto == null){
            throw new NullPointerException();
        }
        else if (produto.getDescricao().trim().isEmpty() || produto.getDescricao() == null){
            throw new IllegalArgumentException("Descrição Inválida");
        }
        else if (produto.getPreco() <= 0){
            throw new IllegalArgumentException("Preço Inválido");
        }
        else if (produto.getCategoria().trim().isEmpty() || produto.getCategoria() == null
                    || !CategoriasDAO.getCategorias().contains(produto.getCategoria())){
            throw new IllegalArgumentException("Categoria Inválida");
        }
        else if (produto.getMedida().trim().isEmpty() || produto.getMedida() == null
                    || !Objects.equals(produto.getMedida(), "kg") && !Objects.equals(produto.getMedida(), "un")){
            throw new IllegalArgumentException("Medida Inválida");
        }
        else if (produto.getDesconto() == null){
            throw new IllegalArgumentException("Desconto Inválido");
        }
        else if (produto.getFoto().trim().isEmpty() || produto.getFoto() == null){
            throw new IllegalArgumentException("Foto Inválida");
        }

        ProdutosDAO.addProdutoDAO(produto);
    }

    public static void updateProdutoRN(Produto produtoAlterado) throws SQLException, NullPointerException, IllegalArgumentException {
        if(produtoAlterado == null){
            throw new NullPointerException();
        }
        else if (produtoAlterado.getDescricao().trim().isEmpty() || produtoAlterado.getDescricao() == null){
            throw new IllegalArgumentException("Descrição Inválida");
        }
        else if (produtoAlterado.getPreco() <= 0){
            throw new IllegalArgumentException("Preço Inválido");
        }
        else if (produtoAlterado.getCategoria().trim().isEmpty() || produtoAlterado.getCategoria() == null
                || !CategoriasDAO.getCategorias().contains(produtoAlterado.getCategoria())){
            throw new IllegalArgumentException("Categoria Inválida");
        }
        else if (produtoAlterado.getMedida().trim().isEmpty() || produtoAlterado.getMedida() == null
                || !Objects.equals(produtoAlterado.getMedida(), "kg") && !Objects.equals(produtoAlterado.getMedida(), "un")){
            throw new IllegalArgumentException("Medida Inválida");
        }
        else if (produtoAlterado.getDesconto() == null){
            throw new IllegalArgumentException("Desconto Inválido");
        }
//        else if (produtoAlterado.getFoto().trim().isEmpty() || produtoAlterado.getFoto() == null){
//            throw new IllegalArgumentException("Foto Inválida");
//        }

        ProdutosDAO.updateProdutoDAO(produtoAlterado);
    }

    public static void deleteProdutoRN(Produto produto) throws SQLException, NullPointerException {
        if (produto != null) {
            ProdutosDAO.deleteProdutoDAO(produto);
        } else {
            throw new NullPointerException();
        }
    }

}
