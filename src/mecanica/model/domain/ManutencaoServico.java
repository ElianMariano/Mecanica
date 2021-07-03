package mecanica.model.domain;

import java.io.Serializable;
import java.util.Date;

public class ManutencaoServico implements Serializable{
    private int codigo;
    private Manutencao manutencao;
    private Servicos servico;
    
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
    
    @Override
    public String toString(){
        return String.valueOf(this.codigo);
    }
}
