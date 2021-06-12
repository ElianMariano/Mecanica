package mecanica.model.domain;

import java.io.Serializable;

public class ModeloVeiculo implements Serializable {

    private Integer cdVeiculo;
    private Boolean moto;
    private String nome;
    private String descricao;

    public int getcdVeiculo() {
        return cdVeiculo;
    }

    public void setcdVeiculo(int cdVeiculo) {
        this.cdVeiculo = cdVeiculo;
    }

    public Boolean getMoto() {
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

        return String.format("(Id: %d, Moto: %b, Nome: %s, Descrição: %s)",
                cdVeiculo, moto, nome, descricao);
    }
}
