package mecanica.controller;
/*
Classe com métodos genéricos utilizados em toda a aplicação
*/
public class Utils {
    // Função que quebra a linha de acordo com determinada quantiade de
    // palavras
    public static String quebrarLinha(String texto, int quantidade){
        String[] palavras = texto.split(" ");
        String resultado = "";
        
        for (int i = 0;i < palavras.length;i++){
            resultado += String.format("%s ", palavras[i]);
            
            if (i % 4 == 0 && i != 0) resultado += "\n";
        }
        
        return resultado;
    }
    
    // Função que retorna verdadeiro se String for número
    public static boolean eNumero(String numero){
        try{
            Integer.parseInt(numero);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
