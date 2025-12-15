package Model.RN;

import Model.DAO.ClientesDAO;
import Exceptions.ContaExceptions;
import Model.VO.Cliente;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ClienteRN {

    public static Cliente searchClienteRN(int id) throws SQLException, ContaExceptions.UsuarioInexistenteException {
        Cliente temp;
        if((temp = ClientesDAO.searchClienteDAO(id)) != null){
            return temp;
        }
        throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário vinculado a esse id");
    }

    public static Cliente searchClienteRN(String nome) throws SQLException, ContaExceptions.UsuarioInexistenteException {
        Cliente temp;
        if((temp = ClientesDAO.searchClienteDAO(nome)) != null){
            return temp;
        }
        throw new ContaExceptions.UsuarioInexistenteException("Nenhum usuário vinculado a esse nome");
    }

    public static void addClienteRN(Cliente cliente) throws ContaExceptions.NomeInvalidoException, ContaExceptions.CPFInvalidoException, ContaExceptions.SexoInvalidoException, ContaExceptions.DtNascInvalidaException, ContaExceptions.SenhaInvalidaException, SQLException {
        if (cliente.getNome().trim().isEmpty()) {
            throw new ContaExceptions.NomeInvalidoException("Nome Invalido");
        }else if(ClientesDAO.getNomes().contains(cliente.getNome())){
            throw new ContaExceptions.NomeInvalidoException("Nome Inválido");
        }else if(cliente.getCPF().length()!=14){
            throw new ContaExceptions.CPFInvalidoException("CPF Invalido");
        }else if(cliente.getSexo() == null){
            throw new ContaExceptions.SexoInvalidoException("Sexo Invalido");
        }else if(cliente.getDataDeNascimento() == null){
            throw new ContaExceptions.DtNascInvalidaException("Idade Invalida");
        }else if(LocalDate.now().getYear() - LocalDate.parse(new SimpleDateFormat("dd/MM/yyyy").format(cliente.getDataDeNascimento()), DateTimeFormatter.ofPattern("dd/MM/yyyy")).getYear()< 10){
            throw new ContaExceptions.DtNascInvalidaException("Idade Invalida");
        }else if(cliente.getSenha().contains(" ")
                || cliente.getSenha().length()<5
                || cliente.getSenha().isEmpty()){
            throw new ContaExceptions.SenhaInvalidaException("Senha Invalida");
        }
        ClientesDAO.addClienteDAO(cliente);
    }

    public static void updateClienteRN(Cliente clienteAlterado) throws ContaExceptions.NomeInvalidoException, ContaExceptions.SexoInvalidoException, ContaExceptions.CPFInvalidoException, ContaExceptions.DtNascInvalidaException, ContaExceptions.SenhaInvalidaException, SQLException {
        if (clienteAlterado.getNome().trim().isEmpty()) {
            throw new ContaExceptions.NomeInvalidoException("Nome invalido");
        }else if(clienteAlterado.getCPF().length() != 14){
            throw new ContaExceptions.CPFInvalidoException("CPF Invalido");
        }else if(clienteAlterado.getSexo() == null){
            throw new ContaExceptions.SexoInvalidoException("Sexo invalido");
        }else if(clienteAlterado.getDataDeNascimento() == null){
            throw new ContaExceptions.DtNascInvalidaException("Idade Invalida");
        }else if(LocalDate.now().getYear() - LocalDate.parse(new SimpleDateFormat("dd/MM/yyyy").format(clienteAlterado.getDataDeNascimento()), DateTimeFormatter.ofPattern("dd/MM/yyyy")).getYear()< 10){
            throw new ContaExceptions.DtNascInvalidaException("Idade Invalida");
        }else if(clienteAlterado.getSenha().contains(" ")
                || clienteAlterado.getSenha().length()<5
                || clienteAlterado.getSenha().isEmpty()){
            throw new ContaExceptions.SenhaInvalidaException("Senha Invalida");
        }
        ClientesDAO.updateClienteDAO(clienteAlterado);
    }

    public static void deleteClienteRN(Cliente cliente) throws SQLException, NullPointerException {
        if (cliente != null) {
            ClientesDAO.deleteClienteDAO(cliente);
        } else {
            throw new NullPointerException();
        }
    }

    public static Cliente logarClienteRN(String nome, String senha) throws SQLException, ContaExceptions.SenhaInvalidaException, ContaExceptions.NomeInvalidoException, ContaExceptions.UsuarioInexistenteException {
        if(nome == null || senha == null){
            throw new NullPointerException();
        }

        int login = ClientesDAO.logarClienteDAO(nome, senha);
        if(login == -1){
            throw new ContaExceptions.SenhaInvalidaException("Senha Incorreta");
        }else if(login == -2){
            throw new ContaExceptions.NomeInvalidoException("Nenhum usuário vinculado a esse nome");
        }else{
            return searchClienteRN(login);
        }
    }

    public static int getSexoCount(String sexo) throws SQLException, NullPointerException {
        if (sexo == null) {
            throw new NullPointerException();
        } else if (!sexo.equals("Feminino") && !sexo.equals("Masculino")){
            throw new IllegalArgumentException("Sexo Inválido");
        }

        int sum = 0;
        for(String s : ClientesDAO.getSexos()){
            if(Objects.equals(s, sexo)){
                sum++;
            }
        }
        return sum;
    }

    public static int getFaixaEtCount(String faixaEtaria) throws SQLException {
        if (faixaEtaria == null) {
            throw new NullPointerException();
        } else if (!faixaEtaria.equals("Criança") && !faixaEtaria.equals("Adolescente")
                    && !faixaEtaria.equals("Jovem-adulto") && !faixaEtaria.equals("Adulto")
                    && !faixaEtaria.equals("Idoso")){
            throw new IllegalArgumentException("Sexo Inválido");
        }

        int temp = 0;
        for(Date d : ClientesDAO.getDtsDeNasc()){
            int idade = Period.between(d.toLocalDate(), LocalDate.now()).getYears();
            if(getFaixaEtaria(idade).equals(faixaEtaria)){
                temp++;
            }
        }
        return temp;
    }

    public static String getFaixaEtaria(int idade){
        if(idade<14){
            return "Criança";
        }else if(idade<18){
            return "Adolescente";
        }else if(idade<25){
            return "Jovem-adulto";
        }else if(idade<60){
            return "Adulto";
        }else{
            return "Idoso";
        }
    }
}
