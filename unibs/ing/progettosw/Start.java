package unibs.ing.progettosw;

import unibs.ing.progettosw.appUtente.AppUtente;
import unibs.ing.progettosw.ristorante.Initializer;
import unibs.ing.progettosw.utilities.DateUtility;
import unibs.ing.progettosw.utilities.FileService;
import unibs.ing.progettosw.utilities.InputDati;
import unibs.ing.progettosw.utilities.Help;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;

/*
 * Classe che rappresenta la classe "principale" contenente il metodo main da cui evolve l'applicazione.
 * */
public class Start {

    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        StartFacade sf = new StartFacade();
        sf.displayStartMenu();
    }
}
