package unibs.ing.progettosw;

import java.io.IOException;
import java.text.ParseException;

/*
 * Classe che rappresenta la classe "principale" contenente il metodo main da cui evolve l'applicazione.
 * */
public class Start {

    public static void main(String[] args) {
        StartFacade sf = new StartFacade();
        sf.displayStartMenu();
    }
}
