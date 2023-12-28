package unibs.ing.progettosw.ristorante.domain;

import unibs.ing.progettosw.exceptions.ErrorLogger;
import unibs.ing.progettosw.utilities.DateUtility;
import unibs.ing.progettosw.utilities.JSONFileReader;
import unibs.ing.progettosw.utilities.JSONFileWriter;
import unibs.ing.progettosw.utilities.StringToClassGetter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class AddettoPrenotazioni implements Dipendente {

    //Classe che rappresenta l'addetto alle prenotazioni adibito a "raccogliere" le prenotazioni

    private int giorniPassati = 0;
    private int caricoLavoro = 0;
    private Ristorante ristorante = Ristorante.getInstance(); // Singleton
    private JSONFileReader jfr = new JSONFileReader();
    private JSONFileWriter jfw = new JSONFileWriter();
    private List<Prenotazione> prenotazioni = new ArrayList();
    private List<Prenotazione> prenotazioniAccettate = new ArrayList<>();
    private Gestore gestore = new Gestore();
    private ErrorLogger el = new ErrorLogger();
    private DateUtility du = new DateUtility();

    // costruttore
    // creazione di un AddettoPrenotazioni in base ad un parametro intero
    // pre : giorniPassati >= 0
    // post : creato correttamente un AddettoPrenotazioni con conseguente caricamento-raccolta delle prenotazioni accettate
    //        vedi metodi sottostanti.
    public AddettoPrenotazioni(int giorniPassati) {
        this.giorniPassati = giorniPassati;
        initPrenotazioni();
    }


    // metodo che permette di inizializzare --> raccogliere le prenotazioni
    // pre : -
    // post :
    private void initPrenotazioni() {
        raccogliPrenotazioni();
    }

    // metodo che permette di raccogliere le prenotazioni e "accettare"/"validare" tali prenotazioni
    // pre : -
    // post : eventuale aggiornamento di prenotazioniAccettate (attributo di tale classe) e del file prenotazioniAccettate.json
    private void raccogliPrenotazioni() {
        prenotazioni = jfr.leggiPrenotazioni("/initFiles/prenotazioni.json", "prenotazioni");
        if (prenotazioni != null) {
            for (Prenotazione aPreno : prenotazioni) {
                inserisciNuovaPrenotazione(aPreno);
            }
            jfw.scriviPrenotazioniAccettateSuFile(prenotazioniAccettate, "/initFiles/prenotazioniAccettate.json", "prenotazioniAccettate");
        } else {
            el.logError(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) +
                    ": nessuna prenotazione è stata caricata. Controllare se il comportamento è corretto.");
            prenotazioniAccettate = null;
        }
    }

    // metodo che permette di "accettare"/"validare" - considerare accettata/valida una data prenotazione
    // pre : p != NULL
    // post : eventuale aggiornamento dell'attributo prenotazioniAccettate (se la prenotazione è valida)
    private void inserisciNuovaPrenotazione(Prenotazione p) {
        boolean isDataPrenotazioneValida = isDataPrenotazioneValida(p);
        boolean isCopertiSuperati = isNumeroCopertiSuperato(p);
        boolean isCaricoLavoroSuperato = false;

        if (!prenotazioniAccettate.isEmpty()) {
            isCaricoLavoroSuperato = isCaricoLavoroSuperato(p);
        }

        if (isDataPrenotazioneValida && !isCopertiSuperati && !isCaricoLavoroSuperato) {
            aggiungiPrenotazione(p);
        }
    }

    // metodo che permette di aggiungere una data prenotazione alla lista delle prenotazioni accettate
    // pre : p != NULL
    // post : aggiornamento dell'attributo-lista prenotazioniAccettate : aggiunta correttamente alla lista una data prenotazione
    private void aggiungiPrenotazione(Prenotazione p) {
        prenotazioniAccettate.add(p);
    }

    // metodo che permette di verificare che la data di una specifica prenotazione sia valida come da consegna
    // pre : p != NULL
    // post : true se la data della specifica Prenotazione p è valida
    //        false se la data della specifica Prenotazione p non è valida
    private boolean isDataPrenotazioneValida(Prenotazione p) {
        // stessa data (anno mese e giorno uguali) ==> data (per cui ho prenotato) la prenotaziona è valida (cioè uguale a quella del giorno corrente)
        Calendar dataPrenotazione = Calendar.getInstance();
        dataPrenotazione.setTime(p.getDataPrenotazione());

        // Check data corrente e giorno feriale (da lunedì a sabato) != 1 (domenica = 1)
        // Check data in cui ho prenotato - dataPrenotazione con un giorno d'anticipo ("non oggi ma almeno ieri")

        return du.atLeastYesterday(p, giorniPassati) < 0 &&
                dataPrenotazione.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY &&
                p.getData().equals(du.dateSinceDayPassed(giorniPassati));
    }

    // metodo che permette di verificare che il carico di lavoro di una data Prenotazione p e di tutte le altre
    // prenotazioniAccettate non superi il carico di lavoro richiesto / carico di lavoro sostenibile dal ristorante.
    // pre : p != NULL
    // post : true se il carico di lavoro sostenibile dal ristorante è minore del carico di lavoro della totalità delle prenotazioni accettate
    //        false altrimenti
    // Vedi classe Ristorante
    private boolean isCaricoLavoroSuperato(Prenotazione p) {
        return ristorante.getCaricoLavoroSostenibile() < getCaricoLavoroAttuale(p);
    }

    // metodo che permette di calcolare il carico di lavoro attuale dato dalle prenotazioni precedentemente accettate
    // e dalla prenotazione che si vuole vedere se ha i requisiti per essere accettata
    // pre : p != NULL
    // post : valore_ritornato=somma del carico di lavoro di ciascuna prenotazione accettata + carico di lavoro di una data prenotazione p
    //        valore_ritornato >= 0
    public int getCaricoLavoroAttuale(Prenotazione p) {
        int caricoLavoroAttuale = 0;
        for (Prenotazione pTemp : prenotazioniAccettate) {
            caricoLavoroAttuale += getCaricoLavoroFromPrenotazione(pTemp);
        }

        caricoLavoroAttuale += getCaricoLavoroFromPrenotazione(p);
        caricoLavoro = caricoLavoroAttuale;
        return this.caricoLavoro;
    }

    // metodo che calcola il carico di lavoro di una data prenotazione
    // pre : pTemp != NULL
    // post : caricoLavoro >= 0 && caricoLavoro = somma caricoLavoro di ciascun Menu tematico e piatto prenotato/i
    private int getCaricoLavoroFromPrenotazione(Prenotazione pTemp) {

        // carico lavoro di una prenotazione : valore carico di lavoro di ciascun menuTematico associato +
        //                                     valore carico di lavoro  di ciascun piatto associato alla prenotazione

        int caricoLavoro = 0;
        StringToClassGetter stc = new StringToClassGetter();
        gestore.initAll();

        // "recupero" il carico di lavoro di ciascun piatto prenotato
        for (Map.Entry<String, Integer> aPiattoEntry : pTemp.getPiattoPrenotato().entrySet()) {
            if (stc.getPiattofromNome(gestore.getPiatti(), aPiattoEntry.getKey()) != null) {
                Piatto piatto = stc.getPiattofromNome(gestore.getPiatti(), aPiattoEntry.getKey());
                caricoLavoro = (int) (caricoLavoro + (piatto.getRicetta().getCaricoLavoroPorzione() * aPiattoEntry.getValue()));
            }
        }

        // "recupero" il carico di lavoro di ciascun menu tematico prenotato
        for (Map.Entry<String, Integer> aMenuEntry : pTemp.getMenuTematicoPrenotato().entrySet()) {
            if (stc.getMenuTematicofromNome(gestore.getMenuT(), aMenuEntry.getKey()) != null) {
                Menu menu = stc.getMenuTematicofromNome(gestore.getMenuT(), aMenuEntry.getKey());
                caricoLavoro = (int) (caricoLavoro + (menu.getCaricoLavoro() * aMenuEntry.getValue()));
            }
        }

        return caricoLavoro;
    }

    // metodo che verifica se il numero di coperti di tutte le prenotazioni accettate + il numero di coperti di una data prenotazione
    // eccede il numero di coperti/posti a sedere complessivi disponibili del ristorante
    // pre : p != NULL
    // post : true se il numero complessivo di posti a sedere del ristorante è inferiore alla somma del numero di coperti estesa a tutte le prenotazioni raccolte per tale
    //        data(a tutte le prenotazioni accettate)
    //        false altrimenti
    private boolean isNumeroCopertiSuperato(Prenotazione p) {
        return Ristorante.getInstance().getPostiSedere() < getPostiAttualmenteOccupati(p);
    }

    // metodo che restituisce / calcola la somma del numero di coperti di tutte le prenotazioni accettate + il numero di coperti di una data prenotazione
    // pre : p != NULL
    // post : valore_ritornato >= 0 && valore_ritornato = somma numero coperti di ogni prenotazione accettate + numero coperti di una data prenotazione
    private int getPostiAttualmenteOccupati(Prenotazione p) {
        int postiAttualmenteOccupati = 0;
        for (Prenotazione pTemp : prenotazioniAccettate) {
            postiAttualmenteOccupati += pTemp.getNumeroCoperti();
        }
        return p.getNumeroCoperti() + postiAttualmenteOccupati;
    }
}
