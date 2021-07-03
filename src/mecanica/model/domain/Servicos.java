package mecanica.model.domain;

import java.io.Serializable;

public class Servicos implements Serializable {

    private Integer codigo;
    private String nome;
    private String descricao;
    private double preco;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer cdServico) {
        this.codigo = cdServico;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getPreco(){
        return this.preco;
    }
    
    public void setPreco(Double preco){
        this.preco = preco;
    }

    @Override
    public String toString() {

        return String.format("Nome: %s, Preço: %.2f",
                nome, preco);
    }
}
