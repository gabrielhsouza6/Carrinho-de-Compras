package Model.RN;

import Model.DAO.CategoriasDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoriaRN {

    public static void addCategoriaRN(String novaCategoria) throws SQLException {
        if(!CategoriasDAO.getCategorias().contains(novaCategoria)) {
            CategoriasDAO.addCategoriaDAO(novaCategoria);
        }
        else{
            throw new IllegalArgumentException("Categoria já existente");
        }
    }

    public static void deleteCategoriaRN(String categoria) throws SQLException {
        if(categoria == null || categoria.trim().isEmpty()){
            throw new IllegalArgumentException("Categoria Inválida");
        }
        CategoriasDAO.deleteCategoriaDAO(categoria);
    }
}
