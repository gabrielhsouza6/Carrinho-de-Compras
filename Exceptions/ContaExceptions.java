package Exceptions;

public class ContaExceptions extends RuntimeException {
    public ContaExceptions(String message) {
        super(message);
    }

    public static class UsuarioInexistenteException extends Exception{
        public UsuarioInexistenteException(String mensagem){super(mensagem);}
    }

    public static class NomeInvalidoException extends Exception{
        public NomeInvalidoException(String mensagem){super(mensagem);}
    }

    public static class SenhaInvalidaException extends Exception{
        public SenhaInvalidaException(String mensagem){super(mensagem);}
    }

    public static class CPFInvalidoException extends Exception{
        public CPFInvalidoException(String mensagem){super(mensagem);}
    }

    public static class SexoInvalidoException extends Exception{
        public SexoInvalidoException(String mensagem){super(mensagem);}
    }

    public static class DtNascInvalidaException extends Exception{
        public DtNascInvalidaException(String mensagem){super(mensagem);}
    }
}
