package mecanica.model.domain;

import java.io.Serializable;

public class Manutencao implements Serializable {
    private Integer cdManutencao;
    private String descricao;
    private Veiculo veiculo;

    public Integer getCdManutencao() {
        return cdManutencao;
    }

    public void setCdManutencao(Integer cdManutencao) {
        this.cdManutencao = cdManutencao;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {

        return String.format("(Código de Manutenção: %d, Placa do Veículo: %s, Descrição: %s)",
                cdManutencao, veiculo.getPlaca(), descricao);
    }
}
