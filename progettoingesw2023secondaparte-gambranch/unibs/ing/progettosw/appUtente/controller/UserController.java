package unibs.ing.progettosw.appUtente.controller;

import unibs.ing.progettosw.appUtente.domain.PrenotazioneUtente;
import unibs.ing.progettosw.ristorante.domain.Gestore;
import unibs.ing.progettosw.ristorante.domain.Menu;
import unibs.ing.progettosw.ristorante.domain.Piatto;
import unibs.ing.progettosw.ristorante.domain.Prenotazione;
import unibs.ing.progettosw.utilities.DateUtility;
import unibs.ing.progettosw.utilities.InputDati;
import unibs.ing.progettosw.utilities.StringToDateConverter;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    private final StringToDateConverter std = new StringToDateConverter();
    private final DateUtility du = new DateUtility();
    /*
     * Controller utilizzato per elaborare le informazioni essenziali per creare una corretta prenotazione.
     * */
    private PrenotazioneUtente nuovaPrenotazione = new PrenotazioneUtente();
    private Gestore gestore;

    public UserController() {
    }

    public void start() {
        // metodo principale che inizializza i piatti e menu tematici validi che è possibile prenotare
        creaListaPiattiIngredienti();
    }

    private void creaListaPiattiIngredienti() {
        gestore = new Gestore();
        gestore.initAll();
    }

    // dati il numero di menu ordinati creo una mappa che contiene quali menu sono stati scelti e il loro quantitativo ordinato
    // pre : numeroMenu > 0
    // post : menuTematiciPrenotati.size() > 0 and menuTematiciPrenotati.size() == numeroMenu
    public Map<String, Integer> ordinaMenuTematici(int numeroMenu, int dayPassed) {
        Map<String, Integer> menuTematiciPrenotati = creaMenuPrenotati(new HashMap<>(), numeroMenu, dayPassed);
        return menuTematiciPrenotati;
    }

    private Map<String, Integer> creaMenuPrenotati(Map<String, Integer> menuTematiciPrenotati, int numeroMenu, int dayPassed) {
        int menuScelto;
        for (int i = 0; i < numeroMenu; i++) {
            menuScelto = InputDati.leggiIntero(0, gestore.getMenuTematiciValidi(dayPassed).size() - 1);

            // se un particolare menu non è ancora stato ordinato lo inseriamo per la prima volta nella mappa
            // altrimenti se è già stato inserito o la mappa non è vuota, cerchiamo il nome del menu e aumentiamo
            // di una unità il numero di quel menu prenotato
            if (menuTematiciPrenotati.isEmpty()) {
                menuTematiciPrenotati.put(gestore.getMenuTematiciValidi(dayPassed).get(menuScelto).getNome(), 1);
            } else if (menuTematiciPrenotati.containsKey(gestore.getMenuTematiciValidi(dayPassed).get(menuScelto).getNome())) {
                menuTematiciPrenotati.put(gestore.getMenuTematiciValidi(dayPassed).get(menuScelto).getNome(), menuTematiciPrenotati.get(gestore.getMenuTematiciValidi(dayPassed).get(menuScelto).getNome()) + 1);
            } else {
                menuTematiciPrenotati.put(gestore.getMenuTematiciValidi(dayPassed).get(menuScelto).getNome(), 1);
            }
        }

        return menuTematiciPrenotati;
    }

    // metodo che costruisce la stringa che contiene la lista dei vari menu tematici validi
    // post : sb.toString() != null
    public String listaMenuDaStampare(int dayPassed) {
        StringBuilder sb = new StringBuilder();
        int counter = 0;

        for (Menu aMenuT : gestore.getMenuTematiciValidi(dayPassed)) {
            sb.append(counter).append(" - ").append(aMenuT.getNome()).append("\n");
            counter++;
        }

        return sb.toString();
    }

    // dati il numero di piatti ordinati creo una mappa che contiene quali piatti sono stati scelti e il loro quantitativo ordinato
    // pre : numeroPiatti > 0
    // post : piattiPrenotati.size() > 0 and piattiPrenotati.size() == numeroPiatti
    public Map<String, Integer> ordinaPiatti(int numeroPiatti, int dayPassed) {
        Map<String, Integer> piattiPrenotati = creaPiattiOrdinati(new HashMap<>(), numeroPiatti, dayPassed);
        return piattiPrenotati;
    }

    private Map<String, Integer> creaPiattiOrdinati(Map<String, Integer> piattiPrenotati, int numeroPiatti, int dayPassed) {
        int piattoScelto;
        for (int j = 0; j < numeroPiatti; j++) {
            piattoScelto = InputDati.leggiIntero(0, gestore.getMenuAllaCarta().getPiatti().size() - 1);
            // se un particolare piatto non è ancora stato ordinato lo inseriamo per la prima volta nella mappa
            // altrimenti se è già stato inserito o la mappa non è vuota, cerchiamo il nome del piatto e aumentiamo
            // di una unità il numero di quel piatto prenotato
            if (piattiPrenotati.isEmpty()) {
                piattiPrenotati.put(gestore.getListaPiattiValidi(dayPassed).get(piattoScelto).getNome(), 1);
            } else if (piattiPrenotati.containsKey(gestore.getListaPiattiValidi(dayPassed).get(piattoScelto).getNome())) {
                piattiPrenotati.put(gestore.getListaPiattiValidi(dayPassed).get(piattoScelto).getNome(), piattiPrenotati.get(gestore.getListaPiattiValidi(dayPassed).get(piattoScelto).getNome()) + 1);
            } else {
                piattiPrenotati.put(gestore.getListaPiattiValidi(dayPassed).get(piattoScelto).getNome(), 1);
            }
        }

        return piattiPrenotati;
    }

    // metodo che costruisce la stringa che contiene la lista dei vari piatti validi
    // post : sb.toString() != null
    public String listaPiattiDaStampare(int dayPassed) {
        StringBuilder sb = new StringBuilder();
        int counter = 0;

        for (Piatto aPiatto : gestore.getListaPiattiValidi(dayPassed)) {
            sb.append(counter).append(" - ").append(aPiatto.getNome()).append("\n");
            counter++;
        }

        return sb.toString();
    }

    // crea la prenotazione che verrà scritta su file
    // pre : data nel formato dd-MM-YYYY
    //       numeroCoperti >= 2 and numeroCoperti <= 10
    //       menuTematiciPrenotati not null
    //       piattiPrenotati not null
    // post : prenotazione salvata su file
    public void creaPrenotazione(String data, int numeroCoperti, Map<String, Integer> menuTematiciPrenotati, Map<String, Integer> piattiPrenotati) {
        Prenotazione p = new Prenotazione(std.creaDataDaStringa(data), du.todaysDate(),
                numeroCoperti, menuTematiciPrenotati, piattiPrenotati);
        nuovaPrenotazione.creaPrenotazione(p);
    }

    // pre : giorno > 0 and (giorno <= 31 or giorno <= 30 or giorno <= 28)
    //       1 <= mese <= 12
    //       anno >= 2023
    public String creaData(int giorno, int mese, int anno) {
        return giorno + "/" + mese + "/" + anno;
    }
}
