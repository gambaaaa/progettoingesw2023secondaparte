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
    private static Initializer init;

    static {
        try {
            init = new Initializer();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static AppUtente appUtente = new AppUtente();
    private static DateUtility du = new DateUtility();
    private static FileService fs = new FileService();

    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        int scelta, giorniPassati = fs.leggiGiornoDaFile(); // variabile giorniPassati necessaria per simulare l'avanzamento della data - dei giorni
        do {
            System.out.println("Oggi è " + du.nameOfDaySinceToday(giorniPassati));
            // per come funziona la stampa su file .json è necessario che ogni volta che si caricano le prenotazioni
            // si deve chiudere il programma e ricominciare daccapo
            scelta = InputDati.leggiIntero("Scegli quale programma eseguire o se consultare un breve manuale d'uso:\n" +
                    "1 - Carica Prenotazioni\n" +
                    "2 - Simula Ristorante\n" +
                    "3 - Prenotazioni Utente\n" +
                    "4 - Help\n" +
                    "5 - Esci dal programma\n", 1, 5);
            System.out.println("\n\n");

            switch (scelta) {
                case 1:
                    // se è domenica non raccolgo le prenotazioni
                    if (du.dayPassed(giorniPassati) == Calendar.SUNDAY) {
                        System.out.println("Ristorante Chiuso!\n\n\n");
                    } else {
                        init.initPrenotazioni(giorniPassati);
                    }
                    fs.scriviGiornoSuFile(giorniPassati);
                    break;
                case 2:
                    // se è domenica il ristorante è chiuso, si passa automaticamente al giorno successivo
                    if (du.dayPassed(giorniPassati) == Calendar.SUNDAY) {
                        System.out.println("Ristorante Chiuso!\n\n\n");
                    } else {
                        init.startRistorante();
                    }
                    giorniPassati++;
                    break;
                case 3:
                    appUtente.startAppUtente(giorniPassati);
                    break;
                case 4:
                    System.out.println(Help.getHelp());
                    break;
                case 5:
                    // resetto i giorni trascorsi e le prenotazioniAccettate
                    fs.azzeraGiornoSuFile();
                    fs.creaPrenotazioniVuote("/initFiles/prenotazioniAccettate.json");
            }
        } while (scelta != 5 && scelta != 1 && scelta != 3);
        // continuo fino a che non scelgo di "uscire" dal programma oppure di caricare le prenotazioni.
        System.out.println("Uscendo dal programma...");
    }
}
