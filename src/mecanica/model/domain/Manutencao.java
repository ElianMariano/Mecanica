package mecanica.model.domain;

import java.io.Serializable;

public class Manutencao implements Serializable {
    private Integer cdManutencao;
    private String descricao;
    private String cdVeiculo;

    public Integer getCdManutencao() {
        return cdManutencao;
    }

    public void setCdManutencao(Integer cdManutencao) {
        this.cdManutencao = cdManutencao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCdVeiculo() {
        return cdVeiculo;
    }

    public void setCdVeiculo(String cdVeiculo) {
        this.cdVeiculo = cdVeiculo;
    }
    
    @Override
    public String toString() {

        return String.format("(Código de Manutenção: %d, Descrição: %s, Código do Veículo: %s)",
                cdManutencao, descricao, cdVeiculo);
    }
}
