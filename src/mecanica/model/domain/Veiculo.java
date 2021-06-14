package mecanica.model.domain;

import java.io.Serializable;

public class Veiculo implements Serializable {

    private String placa;
    private String nome;
    private String marca;
    private int cdmodelo;
    private String cdcliente;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public int getCdModelo(){
        return cdmodelo;
    }
    
    public void setCdModelo(int cdmodelo){
        this.cdmodelo = cdmodelo;
    }
    
    public String getCdCliente(){
        return cdcliente;
    }
    
    public void setCdCliente(String cdcliente){
        this.cdcliente = cdcliente;
    }

    @Override
    public String toString() {
        return String.format("(Placa: %s, Nome: %s, Marca: %s, Cod_Modelo: %d, Cod_Cliente: %s)",
                placa, nome, marca, cdmodelo, cdcliente);
    }
}
