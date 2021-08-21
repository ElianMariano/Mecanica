package mecanica.controller.sockets;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Integrantes {

    public static List<String> obterIntegrantes() throws IOException, ClassNotFoundException {
       //1 - Abrir conexão
        Socket socket = new Socket("34.125.46.96", 12345);
        
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.writeInt(9);
        
        // 2 - Define stream de saida de dados do cliente
        ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
        List<String> integrantes = (List<String>) entrada.readObject();

        //4 - Fechar streams de entrada e saída de dados
        entrada.close();
        saida.close();

        //5 - Fechar o socket
        socket.close();
        
        return integrantes;
    }
}