package unibs.ing.progettosw.utilities;

public class Help {

    /**
     * Classe con la finalità di fornire informazioni circa l'utilizzo dell'applicazione.
     * Rappresenta una sorta di piccolo-breve manuale d'uso da consultare prima di interagire effettivamente
     * con l'applicazione stessa : l'help in linea di comando.
     */


    final private static String INTESTAZIONE_CONCLUSIONE_MANUALE = "--------------------------------BREVE MANUALE D'USO - GUIDA--------------------------------\n\n";
    final private static String INTRODUZIONE = " In questo breve manuale vengono chiariti le opzioni del menu presentato:\n\t 1 - Carica Prenotazioni\n\t 2 - Simula Ristorante \n\t 3 - Prenotazioni Utente\n\n";

    final private static String CARICA_PRENOTAZIONI="-------------1 - Carica Prenotazioni-------------\n\n Caricamento delle prenotazioni inerenti date successive a quella odierna.\n\n";
    final private static String SIMULA_RISTORANTE = "-------------2 - Simula Ristorante-------------\n\n Si tratta di una simulazione guidata in cui vengono caricati i dati del ristorante,successivamente.\n\n" +
            " verrà creata una lista della spesa per effettuare gli acquisti e mantenere costantemente aggiornato il registro delle merci in giacenza in magazzino. Infine verrà simulato il funzionamento della cucina.\n" +
            " Il tutto è caratterizzato con opportuni messaggi di feedback.\n\n";
    final private static String PRENOTAZIONI_UTENTE = "-------------3 - Prenotazioni Utente-------------\n\n L’utente ha la possibilità di effettuare una prenotazione guidata: dopo aver digitato l’opzione “2”, vengono richiesti una serie di dati da inserire\n" +
            "per formulare correttamente una prenotazione al ristorante.\n\n";
    final private static String CONSULTA_MANUALE_FORNITO = " ***Per ulteriori chiarimenti, consultare il Manuale d'installazione ed uso fornito.";

    public static String getHelp() {

        StringBuffer buffer = new StringBuffer(INTESTAZIONE_CONCLUSIONE_MANUALE);
        buffer.append(INTRODUZIONE);
        buffer.append(CARICA_PRENOTAZIONI);
        buffer.append(SIMULA_RISTORANTE);
        buffer.append(PRENOTAZIONI_UTENTE);
        buffer.append(CONSULTA_MANUALE_FORNITO + "\n\n");
        buffer.append(INTESTAZIONE_CONCLUSIONE_MANUALE);

        return buffer.toString();
    }

}
