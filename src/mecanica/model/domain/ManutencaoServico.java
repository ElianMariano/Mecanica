package mecanica.model.domain;

import java.io.Serializable;
import java.util.Date;

public class ManutencaoServico implements Serializable{
    private int codigo;
    private Manutencao manutencao;
    private Servicos servico;
    private Date dia;
    private String inicio;
    private String fim;
    
    public int getCodigo(){
        return this.codigo;
    }
    
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    
    public Manutencao getManutencao(){
        return this.manutencao;
    }
    
    public void setManutencao(Manutencao manutencao){
        this.manutencao = manutencao;
    }
    
    public Servicos getServico(){
        return this.servico;
    }
    
    public void setServico(Servicos servico){
        this.servico = servico;
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
    
    @Override
    public String toString(){
        return String.valueOf(this.codigo);
    }
}
