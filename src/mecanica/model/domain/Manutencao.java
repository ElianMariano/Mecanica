package mecanica.model.domain;

import java.io.Serializable;
import java.util.Date;

public class Manutencao implements Serializable {
    private int codigo;
    private Veiculo veiculo;
    private Date dia;
    private String inicio;
    private String fim;
    private String descricao;

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public Veiculo getVeiculo() {
        return this.veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    public Date getDia(){
        return this.dia;
    }
    
    public void setDia(Date dia){
        this.dia = dia;
    }
    
    public String getInicio(){
        return this.inicio;
    }
    
    public void setInicio(String inicio){
        this.inicio = inicio;
    }
    
    public String getFim(){
        return this.fim;
    }
    
    public void setFim(String fim){
        this.fim = fim;
    }
    
    public String getDescricao(){
        return this.descricao;
    }
    
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    
    @Override
    public String toString() {
        return String.format("Id: %d", this.codigo);
    }
}
