package mecanica.model.domain;

import java.io.Serializable;

public class Manutencao implements Serializable {
    private Integer codigo;
    private Veiculo veiculo;

    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public Veiculo getVeiculo() {
        return this.veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    @Override
    public String toString() {
        return String.format("Id: %d", this.codigo);
    }
}
