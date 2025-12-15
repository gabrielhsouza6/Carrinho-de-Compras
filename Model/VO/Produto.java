package Model.VO;

public class Produto {
    private int id;
    private String descricao;
    private Double preco;
    private Double desconto;
    private String categoria;
    private String medida;
    private String foto;

    public Produto(int id, String descricao, Double preco, Double desconto, String categoria, String medida, String foto){
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.desconto = desconto;
        this.categoria = categoria;
        this.medida = medida;
        this.foto = foto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public Double getDesconto() {
        return desconto;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMedida() {
        return medida;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
