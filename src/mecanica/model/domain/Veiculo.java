package mecanica.model.domain;


import java.io.Serializable;

/**
 *
 * @author Jones
 */
public class Veiculo implements Serializable{
    private String placa;
    private String nome;
    private String marca;
    private int cdModelo;
    private String cdCliente;

    public String getPlaca(){
        return placa;
    }

    public void setPlaca(String placa){
        this.placa = placa;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getMarca(){
        return marca;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }

    public int getcdModelo(){
        return cdModelo;
    }

    public void setcdModelo(int cdModelo){
        this.cdModelo = cdModelo;
    }

    public String getcdCliente(){
        return cdCliente;
    }

    public void setcdCliente(String cdCliente){
        this.cdCliente = cdCliente;
    }

    @Override
    public String toString(){
        return String.format("(Placa: %s, Nome: %s, Marca: %s, Cod_Modelo: %d, Cod_Cliente: %s)", 
                placa, nome, marca, cdModelo, cdCliente);
    }
}