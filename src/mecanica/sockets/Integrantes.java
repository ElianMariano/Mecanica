package mecanica.sockets;

import java.io.*;
import java.net.Socket;

public class Integrantes {

    public static void main(String[] args) throws IOException {
        
       //1 - Abrir conexão
        Socket socket = new Socket("34.125.46.96", 12345);
        
        //2 - Definir stream de saída de dados do cliente
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.writeUTF("9"); //Enviar mensagem em minúsculo para o servidor

        //3 - Definir stream de entrada de dados no cliente
        DataInputStream entrada = new DataInputStream(socket.getInputStream());
        String novaMensagem = entrada.readUTF(); //Receber mensagem em maiúsculo do servidor
        System.out.println(novaMensagem); //Mostrar mensagem em maiúsculo no cliente

        //4 - Fechar streams de entrada e saída de dados
        entrada.close();
        saida.close();

        //5 - Fechar o socket
        socket.close();
    }
}