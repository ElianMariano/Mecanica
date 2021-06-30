package mecanica.model.domain;

import java.io.Serializable;

public class Servicos implements Serializable {

    private int codigo;
    private String nome;
    private String descricao;
    private double preco;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int cdServico) {
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

        return String.format("(Codigo: %d, Nome: %s, Descrição: %s, Preço: %.2f)",
                codigo, nome, descricao, preco);
    }
}
