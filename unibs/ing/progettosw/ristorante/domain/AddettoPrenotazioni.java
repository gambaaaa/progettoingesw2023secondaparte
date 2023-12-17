package unibs.ing.progettosw.ristorante.domain;

import unibs.ing.progettosw.exceptions.ErrorLogger;
import unibs.ing.progettosw.utilities.FileService;
import unibs.ing.progettosw.utilities.StringToClassGetter;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

public class AddettoPrenotazioni implements Dipendente {

    //Classe che rappresenta l'addetto alle prenotazioni adibito a "raccogliere" le prenotazioni

    private int giorniPassati = 0;
    private int caricoLavoro = 0;
    private Ristorante ristorante = Ristorante.getInstance(); // Singleton
    private FileService fs = new FileService();
    private List<Prenotazione> prenotazioni = new ArrayList();
    private List<Prenotazione> prenotazioniAccettate = new ArrayList<>();
    private Gestore gestore = new Gestore();
    private ErrorLogger el = new ErrorLogger();

    // costruttore
    // creazione di un AddettoPrenotazioni in base ad un parametro intero
    // pre : giorniPassati >= 0
    // post : creato correttamente un AddettoPrenotazioni con conseguente caricamento-raccolta delle prenotazioni accettate
    //        vedi metodi sottostanti.
    public AddettoPrenotazioni(int giorniPassati) throws IOException, ParseException {
        this.giorniPassati = giorniPassati;
        initPrenotazioni();
    }


    // metodo che permette di inizializzare --> raccogliere le prenotazioni
    // pre : -
    // post :
    private void initPrenotazioni() throws IOException, ParseException {
        raccogliPrenotazioni();
    }

    // metodo che permette di raccogliere le prenotazioni e "accettare"/"validare" tali prenotazioni
    // pre : -
    // post : eventuale aggiornamento di prenotazioniAccettate (attributo di tale classe) e del file prenotazioniAccettate.json
    private void raccogliPrenotazioni() throws IOException, ParseException {
        prenotazioni = fs.leggiPrenotazioni("/initFiles/prenotazioni.json", "prenotazioni");
        if (prenotazioni != null) {
            for (Prenotazione aPreno : prenotazioni) {
                inserisciNuovaPrenotazione(aPreno);
            }
            fs.scriviPrenotazioniAccettateSuFile(prenotazioniAccettate, "/initFiles/prenotazioniAccettate.json", "prenotazioniAccettate", "prenotazioniAccettate");
        } else {
            createLogError();
            prenotazioniAccettate = null;
        }
    }

    private void createLogError() throws IOException {
        el.logError(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) +
                ": nessuna prenotazione è stata caricata. Controllare se il comportamento è corretto.");
    }

    // metodo che permette di "accettare"/"validare" - considerare accettata/valida una data prenotazione
    // pre : p != NULL
    // post : eventuale aggiornamento dell'attributo prenotazioniAccettate (se la prenotazione è valida)
    private void inserisciNuovaPrenotazione(Prenotazione p) throws ParseException {
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

        return almenoIeri(p) < 0 &&
                dataPrenotazione.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY &&
                p.getData().equals(dateSinceDayPassed());
    }

    // metodo che permette di verificare che il carico di lavoro di una data Prenotazione p e di tutte le altre
    // prenotazioniAccettate non superi il carico di lavoro richiesto / carico di lavoro sostenibile dal ristorante.
    // pre : p != NULL
    // post : true se il carico di lavoro sostenibile dal ristorante è minore del carico di lavoro della totalità delle prenotazioni accettate
    //        false altrimenti
    // Vedi classe Ristorante
    private boolean isCaricoLavoroSuperato(Prenotazione p) throws ParseException {
        return ristorante.getCaricoLavoroSostenibile() < getCaricoLavoroAttuale(p);
    }

    // metodo che permette di calcolare il carico di lavoro attuale dato dalle prenotazioni precedentemente accettate
    // e dalla prenotazione che si vuole vedere se ha i requisiti per essere accettata
    // pre : p != NULL
    // post : valore_ritornato=somma del carico di lavoro di ciascuna prenotazione accettata + carico di lavoro di una data prenotazione p
    //        valore_ritornato >= 0
    public int getCaricoLavoroAttuale(Prenotazione p) throws ParseException {
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
    private int getCaricoLavoroFromPrenotazione(Prenotazione pTemp) throws ParseException {

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

    // metodo che restituisce la data del giorno contando i giorni trascorsi.
    // post : data di (oggi + giorni trascorsi)
    private Date dateSinceDayPassed() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate today = LocalDate.now();
        today = today.plusDays(giorniPassati);
        Date todayDate = Date.from(today.atStartOfDay(defaultZoneId).toInstant());
        return todayDate;
    }

    // metodo che permette di controllare se una specifica prenotazione in ingresso
    // è stata effettuata con almeno un giorno d'anticipo/ "almeno ieri" rispetto alla data odierna;
    // se la data di prenotazione di una specifica prenotazione precede quella odierna-oggi.
    // Vedi metodo soprastante isDataPrenotazioneValida
    // pre : p != NULL
    // post : valore_ritornato = -1 se prenotazione "è arrivata" almeno ieri ("data prenotazione < data odierna-oggi") - se la data di prenotazione precede quella odierna
    //        valore_ritornato = 0  se la data di prenotazione di p corrisponde/coincide alla/con la data odierna
    //        valore_ritornato = 1  se la data di prenotazione di p segue la data odierna
    private int almenoIeri(Prenotazione p) {
        //Controllo che d non sia uguale a today ma "minore" --> < 0
        //se ritorna -1 (<=) sicuramente la data di prenotazione "è arrivata" almeno ieri
        return p.getDataPrenotazione().compareTo(dateSinceDayPassed()); // se si usa DateUtility ==> return almenoIeri(p.getDataPrenotazione)
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
