package mecanica.model.domain;

import java.io.Serializable;

public class Servicos implements Serializable {

    private Integer cdServico;
    private String nome;
    private String descricao;
    private Double preco;

    public int getcdServico() {
        return cdServico;
    }

    public void setcdServico(int cdServico) {
        this.cdServico = cdServico;
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
    
    public Double getPreco(){
        return this.preco;
    }
    
    public void setPreco(Double preco){
        this.preco = preco;
    }

    @Override
    public String toString() {

        return String.format("(Codigo: %d, Nome: %s, Descrição: %s, Preço: %.2f)",
                cdServico, nome, descricao, preco);
    }
}
