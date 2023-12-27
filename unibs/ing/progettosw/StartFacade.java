package unibs.ing.progettosw;

import unibs.ing.progettosw.appUtente.AppUtente;
import unibs.ing.progettosw.exceptions.ErrorDialog;
import unibs.ing.progettosw.ristorante.Initializer;
import unibs.ing.progettosw.utilities.*;

import java.io.IOException;
import java.text.ParseException;

public class StartFacade {
    private Initializer init = new Initializer();
    private ErrorDialog ed = new ErrorDialog();
    private AppUtente appUtente = new AppUtente();
    private DateUtility du = new DateUtility();
    private JSONFileWriter jfw = new JSONFileWriter();
    private TXTFileWriter tfw = new TXTFileWriter();
    private JSONFileReader jfr = new JSONFileReader();

    public void displayStartMenu() throws IOException, ParseException, InterruptedException {
        int giorniPassati = jfr.leggiGiornoDaFile(); // variabile giorniPassati necessaria per simulare l'avanzamento della data - dei giorni
        displayMenu(giorniPassati);
    }

    private void displayMenu(int giorniPassati) throws IOException, ParseException, InterruptedException {
        int scelta;
        do {
            System.out.println("Oggi è " + du.nameOfDaySinceToday(giorniPassati));
            // per come funziona la stampa su file .json è necessario che ogni volta che si caricano le prenotazioni
            // si deve chiudere il programma e ricominciare daccapo
            System.out.println("Scegli quale programma eseguire o se consultare un breve manuale d'uso:\n" +
                    "1 - Carica Prenotazioni\n" +
                    "2 - Simula Ristorante\n" +
                    "3 - Prenotazioni Utente\n" +
                    "4 - Help\n" +
                    "5 - Esci dal programma");
            scelta = InputDati.leggiIntero(1, 5);
            System.out.println();
            giorniPassati = eseguiScelta(scelta, giorniPassati);
        } while (scelta != 5 && scelta != 1 && scelta != 3);
        // continuo fino a che non scelgo di "uscire" dal programma oppure di caricare le prenotazioni.
        System.out.println("Uscendo dal programma...");
    }

    private int eseguiScelta(int scelta, int giorniPassati) throws IOException, ParseException, InterruptedException {
        switch (scelta) {
            case 1:
                // se è domenica non raccolgo le prenotazioni
                if (du.isSunday(giorniPassati)) {
                    ed.logError("Ristorante Chiuso!\n");
                } else {
                    init.initPrenotazioni(giorniPassati);
                }
                tfw.scriviGiornoSuFile(giorniPassati);
                break;
            case 2:
                // se è domenica il ristorante è chiuso, si passa automaticamente al giorno successivo
                if (du.isSunday(giorniPassati)) {
                    ed.logError("Ristorante Chiuso!\n");
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
                tfw.scriviStringaSuFile(String.valueOf(0), "initFiles\\giorniPassati.txt", false);
                jfw.creaPrenotazioniVuote("/initFiles/prenotazioniAccettate.json");
        }

        return giorniPassati;
    }
}
