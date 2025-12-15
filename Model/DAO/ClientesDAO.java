package Model.DAO;

import Model.VO.Cliente;
import View.MenuPrincipal;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class ClientesDAO {
    static Connection conexao = ConnectClass.getInstance();

    public static void addClienteDAO(Cliente cliente) throws SQLException {
        String sql = "insert into clientes(nome, CPF, dataDeNascimento, sexo, senha) values(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, cliente.getNome());
        preparedStatement.setString(2, cliente.getCPF());
        preparedStatement.setDate(3, cliente.getDataDeNascimento());
        preparedStatement.setString(4, cliente.getSexo());
        preparedStatement.setString(5, cliente.getSenha());
        preparedStatement.execute();
    }

    public static void deleteClienteDAO(Cliente cliente) throws SQLException {
        String sql = "delete from clientes where id=?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, cliente.getId());
        preparedStatement.execute();
    }

    public static void updateClienteDAO(Cliente clienteAlterado) throws SQLException {
        String sql = "update clientes set nome=?, CPF=?, dataDeNascimento=?, sexo=?, senha=? where id=?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, clienteAlterado.getNome());
        preparedStatement.setString(2, clienteAlterado.getCPF());
        preparedStatement.setDate(3, clienteAlterado.getDataDeNascimento());
        preparedStatement.setString(4, clienteAlterado.getSexo());
        preparedStatement.setString(5, clienteAlterado.getSenha());
        preparedStatement.setInt(6, clienteAlterado.getId());
        preparedStatement.execute();
    }

    public static Cliente searchClienteDAO(int id) throws SQLException {
        String sql = "select * from clientes where id=?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int idBD = resultSet.getInt("id");
            String nomeBD = resultSet.getString("nome");
            String senhaBD = resultSet.getString("senha");
            String CpfBD = resultSet.getString("CPF");
            Date dateBD = resultSet.getDate("dataDeNascimento");
            String sexoBD = resultSet.getString("sexo");
            return new Cliente(idBD, nomeBD, CpfBD, dateBD, sexoBD, senhaBD);
        }
        return null;
    }

    public static Cliente searchClienteDAO(String nome) throws SQLException {
        String sql = "select * from clientes where nome=?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, nome);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int idBD = resultSet.getInt("id");
            String nomeBD = resultSet.getString("nome");
            String senhaBD = resultSet.getString("senha");
            String CpfBD = resultSet.getString("CPF");
            Date dateBD = resultSet.getDate("dataDeNascimento");
            String sexoBD = resultSet.getString("sexo");
            return new Cliente(idBD, nomeBD, CpfBD, dateBD, sexoBD, senhaBD);
        }
        return null;
    }

    public static int logarClienteDAO(String nome, String senha) throws SQLException {
        String sql = "select * from clientes";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            int idBD = resultSet.getInt("id");
            String nomeBD = resultSet.getString("nome");
            String senhaBD = resultSet.getString("senha");
            if(Objects.equals(nomeBD, nome)){
                if(Objects.equals(senhaBD, senha)){
                    if(Objects.equals(nomeBD, "Admin") && Objects.equals(senhaBD, "nnawym23")){
                        MenuPrincipal.getCurrentFrame().getJMenuBar().add(MenuPrincipal.getAdminMenu());
                        MenuPrincipal.getCurrentFrame().repaint();
                    }
                    return idBD;
                }else{
                    System.out.println("Senha Incorreta");
                    return -1;
                }
            }
        }
        return -2;
    }

    public static ArrayList<String> getNomes() throws SQLException {
        String sql = "select nome from clientes order by nome";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<String> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getString("nome"));
        }
        return temp;
    }

    public static ArrayList<String> getSexos() throws SQLException {
        String sql = "select * from clientes";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<String> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getString("sexo"));
        }
        return temp;
    }

    public static ArrayList<Date> getDtsDeNasc() throws SQLException {
        String sql = "select * from clientes";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Date> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getDate("dataDeNascimento"));
        }
        return temp;
    }

    public static ArrayList<Integer> getIds() throws SQLException {
        String sql = "select * from clientes";
        Statement statement = conexao.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Integer> temp = new ArrayList<>();
        while(resultSet.next()){
            temp.add(resultSet.getInt("id"));
        }
        return temp;
    }

}
