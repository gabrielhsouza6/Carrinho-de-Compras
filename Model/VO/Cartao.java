package Model.VO;

public class Cartao {
    private int id;
    private String numCartao;
    private String nomeTitular;
    private String dtValidade;
    private String CVV;
    private String tipo;

    public Cartao(int id, String numCartao, String nomeTitular, String dtValidade, String CVV, String tipo){
        this.id = id;
        this.numCartao = numCartao;
        this.nomeTitular = nomeTitular;
        this.dtValidade = dtValidade;
        this.CVV = CVV;
        this.tipo = tipo;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getDtValidade() {
        return dtValidade;
    }

    public void setDtValidade(String dtValidade) {
        this.dtValidade = dtValidade;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString(){
        String bandeira;
        if(numCartao.charAt(0) == '4'){
            bandeira = "Visa";
        }else if(Integer.parseInt(numCartao.substring(0, 2))<=55 && Integer.parseInt(numCartao.substring(0, 2))>=51
                || Integer.parseInt(numCartao.substring(0, 4))<=2720 && Integer.parseInt(numCartao.substring(0, 4))>=2221){
            bandeira = "Mastercard";
        }else if(numCartao.substring(0, 2).equals("37") || numCartao.substring(0, 2).equals("34")){
            bandeira = "American Express";
        }else if(numCartao.substring(0, 4).equals("6011") || numCartao.substring(0, 2).equals("65")
                || Integer.parseInt(numCartao.substring(0, 3))<=649 && Integer.parseInt(numCartao.substring(0, 3))>=644){
            bandeira = "Discover";
        }else if(numCartao.substring(0, 4).equals("6062")){
            bandeira = "Hipercard";
        }else{
            bandeira = "Elo";
        }
        return tipo + " " + bandeira + " **** **** **** " + numCartao.substring(15) + " " + dtValidade;
    }
}
