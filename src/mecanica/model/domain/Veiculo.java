package mecanica.model.domain;

import java.io.Serializable;

public class Veiculo implements Serializable {

    private String placa;
    private String nome;
    private String marca;
    private String modelo;
    private String cliente;

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
    
    public String getModelo(){
        return modelo;
    }
    
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    
    public String getCliente(){
        return cliente;
    }
    
    public void setCliente(String cliente){
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return String.format("(Placa: %s, Nome: %s, Marca: %s, Modelo: %s, Cliente: %s)",
                placa, nome, marca, modelo, cliente);
    }
}
