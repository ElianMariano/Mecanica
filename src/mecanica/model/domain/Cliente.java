/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mecanica.model.domain;

import java.io.Serializable;

/**
 *
 * @author elian
 */
public class Cliente implements Serializable{
    private int cdCliente;
    private String cpf;
    private String nome;
    private String nascimento;
    private String cidade;
    private String uf;
    
    public int getcdCliente(){
        return cdCliente;
    }
    
    public void setcdCliente(int cdCliente){
        this.cdCliente = cdCliente;
    }
    
    public String getCpf(){
        return cpf;
    }
    
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getNascimento(){
        return nascimento;
    }
    
    public void setNascimento(String nascimento){
        this.nascimento = nascimento;
    }
    
    public String getCidade(){
        return cidade;
    }
    
    public void setCidade(String cidade){
        this.cidade = cidade;
    }
    
    public String getUf(){
        return uf;
    }
    
    public void setUf(String uf){
        this.uf = uf;
    }
    
    @Override
    public String toString(){
        return String.format("(Id: %d, CPF: %s, Nome: %s, Nascimento: %s, Cidade: %s, UF: %s)", 
                cdCliente, cpf, nome, nascimento, cidade, uf);
    }
}
