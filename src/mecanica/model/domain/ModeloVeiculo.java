package mecanica.model.domain;

import java.io.Serializable;

public class ModeloVeiculo implements Serializable {

    private Integer codigo;
    private boolean moto;
    private String nome;
    private String descricao;

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public boolean getMoto() {
        return this.moto;
    }

    public void setMoto(boolean moto) {
        this.moto = moto;
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

    @Override
    public String toString() {
        return String.format("(Código: %d, Moto: %b, Nome: %s, Descrição: %s)",
                this.codigo, this.moto, this.nome, this.descricao);
    }
}
