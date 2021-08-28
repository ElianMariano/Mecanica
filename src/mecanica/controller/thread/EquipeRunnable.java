package mecanica.controller.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class EquipeRunnable implements Runnable{
    List<String> equipe = new ArrayList<>();
    Label label;
    int i;
    
    public EquipeRunnable(Label label){
        this.label = label;
    }
    
    public EquipeRunnable(Label label, List<String> equipe){
        this.label = label;
        this.equipe = equipe;
    }

    @Override
    public void run() {
        while (true){
            Platform.runLater(() -> label.setText(equipe.get(i)));
            
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EquipeRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (i < (equipe.size()-1)) i++; else i = 0;
        }
    }
}
