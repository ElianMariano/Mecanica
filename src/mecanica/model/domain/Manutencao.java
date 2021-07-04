package mecanica.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Manutencao implements Serializable {
    private int codigo;
    private Veiculo veiculo;
    private LocalDate dia;
    private String inicio;
    private String fim;
    private String descricao;
    // Dados de manutencao_servico
    List<ManutencaoServico> ms = new ArrayList<>();
    
    public List<ManutencaoServico> getManutencaoServico(){
        return this.ms;
    }
    
    public void setManutencaoServico(List<ManutencaoServico> ms){
        this.ms = ms;
    }

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
    
    public LocalDate getDia(){
        return this.dia;
    }
    
    public void setDia(LocalDate dia){
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
