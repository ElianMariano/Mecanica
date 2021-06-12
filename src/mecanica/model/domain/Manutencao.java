package mecanica.model.domain;

import java.io.Serializable;

public class Manutencao implements Serializable {

    private Integer cdManutencao;
    private Integer cdServico;
    private String cdVeiculo;
    private String descricao;

    public Integer getCdManutencao() {
        return cdManutencao;
    }

    public void setCdManutencao(Integer cdManutencao) {
        this.cdManutencao = cdManutencao;
    }

    public Integer getCdServico() {
        return cdServico;
    }

    public void setCdServico(Integer cdServico) {
        this.cdServico = cdServico;
    }

    public String getCdVeiculo() {
        return cdVeiculo;
    }

    public void setCdVeiculo(String cdVeiculo) {
        this.cdVeiculo = cdVeiculo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {

        return String.format("(Código de Manutenção: %d, Código de Serviço: %d, Código de Veículo: %s, Descrição: %s)",
                cdManutencao, cdServico, cdVeiculo, descricao);
    }
}
