package unibs.ing.progettosw.ristorante.domain;

import unibs.ing.progettosw.utilities.DateUtility;
import unibs.ing.progettosw.utilities.FileService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Gestore implements Dipendente {
    /*
     * Classe che implementa l'interfaccia dipendente e simula le azioni svolte da un gestore di ristoranti.
     * */
    private FileService fs = new FileService();
    private DateUtility du = new DateUtility();
    private Ristorante ristorante;
    private List<IMerce> bevande;
    private List<IMerce> generiExtra;
    private List<Ricetta> ricette;
    private List<IMerce> ingredienti;
    private List<Piatto> piatti;
    private Menu menuAllaCarta;
    private List<Menu> menuT;

    public Gestore() {
    }

    // metodo che contiene i vari metodi per inizializzare le informazioni principali del ristorante, ingredienti e ricette
    public void initAll() throws ParseException {
        initRistorante();
        initBevande();
        initGenereExtra();
        initIngredienti();
        initRicette();
        initPiatti();
        initMenu();
    }

    // metodo che stampa su console le varie informazioni del ristorante
    // post : informazioni del ristorante
    public String stampaInfoRistorante() {
        StringBuilder sb = new StringBuilder(ristorante.getInfoRistorante());
        sb.append("\nBevande e consumo pro-capite:\n");
        sb.append(getMerceEConsumoProcapite(bevande));

        sb.append("\nGeneri Extra e consumo pro-capite:\n");
        sb.append(getMerceEConsumoProcapite(generiExtra));

        sb.append("\nCorrispondenze piatto-ricetta:\n");
        sb.append(getCorrispRicettaPiatto());

        sb.append("\nDenominazione e periodo di validità di ciascun piatto:\n");
        sb.append(getNomeValiditaPiatti());

        sb.append("\nVisualizzazione ricette:\n");
        sb.append(getNomeRicette());

        sb.append("\nVisualizzazione menu tematici:\n");
        sb.append(getNomeMenuTematico());

        return sb.toString();
    }


    // inizializza le informazioni del ristorante (nome ristorante, posti a sedere, carico di lavoro) leggendole da un file esterno
    private void initRistorante() {
        fs.setupRistorante("/initFiles/initRistorante.json");
        ristorante = Ristorante.getInstance();
    }

    // inizializza le bevande del ristorante leggendole da un file esterno
    private void initBevande() {
        bevande = fs.setupBevande("/initFiles/initBevande.json", "bevande");
    }

    // inizializza i generiExtra del ristorante leggendoli da un file esterno
    private void initGenereExtra() {
        generiExtra = fs.setupGeneriExtra("/initFiles/initGeneri.json", "generiExtra");
    }

    // inizializza le ricette del ristorante leggendole da un file esterno
    private void initRicette() {
        ricette = fs.setupRicetta("/initFiles/initRicette.json");
    }

    // inizializza gli ingredienti del ristorante leggendoli da un file esterno
    private void initIngredienti() throws ParseException {
        ingredienti = fs.setupIngredienti("/initFiles/initIngredienti.json", "ingredienti");
    }

    // inizializza i piatti del ristorante leggendoli da un file esterno
    private void initPiatti() throws ParseException {
        piatti = fs.setupPiatti("/initFiles/initPiatti.json", "piatti", ricette);
    }

    // inizializza i menu alla carta e tematici del ristorante leggendoli da un file esterno
    private void initMenu() throws ParseException {
        initMenuCarta();
        initMenuTematico();
    }

    // inizializza i menu tematici validi del ristorante leggendoli da un file esterno
    private void initMenuCarta() {
        double caricoLavoroPiatti = sommaCaricoLavoroPiatti(piatti);
        List<Piatto> piattiDisponibili = creaListaPiattiDisponibili(piatti);
        this.menuAllaCarta = new Menu("Menù alla carta", piattiDisponibili, caricoLavoroPiatti);
    }

    // inizializza i menu alla carta validi del ristorante leggendoli da un file esterno
    private void initMenuTematico() throws ParseException {
        this.menuT = fs.setupMenuTematico("/initFiles/initMenu.json", "menuTematici", ricette);
    }

    // crea la lista dei piatti validi
    // post : lista dei piatti (può essere vuota, anche se è un caso poco sensato)
    private List<Piatto> creaListaPiattiDisponibili(List<Piatto> piatti) {
        List<Piatto> piattiDisponibili = new ArrayList<>();
        Date dataOdierna = new Date();
        for (Piatto aPiatto : piatti) {
            if (!dataOdierna.before(aPiatto.getInizioDisponibilita()) && !dataOdierna.after(aPiatto.getFineDisponibilita())) {
                piattiDisponibili.add(aPiatto);
            }
        }

        return piattiDisponibili;
    }

    // calcolo della somma del carico di lavoro dei piatti
    // pre : piatti not null, piatti.size() > 0
    // post : sum > 0, getCaricoLavoro di ogni piatto = sum
    private double sommaCaricoLavoroPiatti(List<Piatto> piatti) {
        double sum = 0;
        for (Piatto aPiatto : piatti) {
            sum += aPiatto.getCaricoLavoro();
        }

        return sum;
    }

    // crea la stringa formattata per stampare su console il nome della merce e il suo consumo procapite.
    // pre : merce not null, merce.size > 0
    // post : stringa opportunamente formattata
    private String getMerceEConsumoProcapite(List<IMerce> merce) {
        StringBuilder sb = new StringBuilder();

        for (IMerce aMerce : merce) {
            sb.append(aMerce.getNome()).append(": ").append(aMerce.getConsumoProCapite()).append("\n");
        }

        return sb.toString();
    }

    // crea la stringa formattata per stampare su console il nome del piatto e la sua ricetta associata.
    // post : stringa opportunamente formattata
    private String getCorrispRicettaPiatto() {
        StringBuilder sb = new StringBuilder();

        for (Piatto aPiatto : piatti) {
            sb.append("Piatto: ").append(aPiatto.getNome()).append(" <--> Ricetta usata: ").append(aPiatto.getRicetta().getNome()).append("\n");
        }

        return sb.toString();
    }

    // metodo che restituisce la stringa formattata contenente il nome del piatto e la sua validità nel tempo
    // post : stringa opportunamente formattata
    private String getNomeValiditaPiatti() {
        StringBuilder sb = new StringBuilder();

        for (Piatto aPiatto : piatti) {
            sb.append(aPiatto.denominazioneValiditaPiatto()).append("\n");
        }

        return sb.toString();
    }

    // metodo che restituisce la stringa formattata contenente il nome delle ricette
    // post : stringa opportunamente formattata
    private String getNomeRicette() {
        StringBuilder sb = new StringBuilder();

        for (Ricetta aRicetta : ricette) {
            sb.append(aRicetta.getNome()).append("\n");
        }

        return sb.toString();
    }

    // metodo che restituisce la stringa formattata contenente il nome del menu tematico e i piatti contenuti
    // (indipendentemente dal fatto che il menu sia valido o meno)
    // post : stringa opportunamente formattata
    private String getNomeMenuTematico() {
        StringBuilder sb = new StringBuilder();

        for (Menu aMenu : menuT) {
            sb.append("Menu Tematico: ").append(aMenu.getNome()).append(", Piatti usati: ").append(getNomePiatti(aMenu.getPiatti())).append("\n");
        }

        return sb.toString();
    }

    // metodo che restituisce la stringa formattata contenente il nome dei piatti contenuti in un menu tematico
    // pre : piatti not null, piatti.size > 0
    // post : stringa opportunamente formattata
    private String getNomePiatti(List<Piatto> piatti) {
        StringBuilder strbldr = new StringBuilder();

        for (Piatto aPiatto : piatti) {
            strbldr.append(aPiatto.getNome()).append(". ");
        }
        String nomePiatti = strbldr.toString();
        return nomePiatti;
    }

    public Ristorante getRistorante() {
        return ristorante;
    }

    public List<IMerce> getBevande() {
        return bevande;
    }

    public List<IMerce> getGeneriExtra() {
        return generiExtra;
    }

    public List<Ricetta> getRicette() {
        return ricette;
    }

    public List<IMerce> getIngredienti() {
        return ingredienti;
    }

    public List<Piatto> getPiatti() {
        return piatti;
    }

    public Menu getMenuAllaCarta() {
        return menuAllaCarta;
    }

    public List<Menu> getMenuT() {
        return menuT;
    }

    // Metodo che restituisce la lista dei menu tematici validi, per semplicità un menu contiene 2 piatti, perciò
    // se un menu contiene due piatti validi allora è a sua volta un menu valido
    // post : menuValidi.size() > 0
    public List<Menu> getMenuTematiciValidi(int dayPassed) {
        List<Menu> menuValidi = new ArrayList<>();

        for (Menu aMenu : menuT) {
            if (getNumeroPiattiValidi(aMenu, dayPassed) == 2)
                menuValidi.add(aMenu);
        }

        return menuValidi;
    }

    // Metodo che restituisce il numero di piatti validi un menu tematico, un piatto è valido se la sua data di inizio e
    // è precedente a oggi (o al tempo che "scorre") e se la data di fine disponibilità è successiva a oggi (o al tempo che scorre)
    // pre : aMenu not null
    // post : 0 <= countValidi <= 2
    private int getNumeroPiattiValidi(Menu aMenu, int dayPassed) {
        int countValidi = 0;
        for (Piatto aPiatto : aMenu.getPiatti()) {
            if (du.getDatePassedSinceToday(dayPassed).after(aPiatto.getInizioDisponibilita()) && du.getDatePassedSinceToday(dayPassed).before(aPiatto.getFineDisponibilita())) {
                countValidi++;
            }
        }

        return countValidi;
    }

    public List<Piatto> getListaPiattiValidi(int dayPassed) {
        List<Piatto> piattiValidi = new ArrayList<>();
        for (Piatto aPiatto : piatti) {
            if (du.getDatePassedSinceToday(dayPassed).after(aPiatto.getInizioDisponibilita()) && du.getDatePassedSinceToday(dayPassed).before(aPiatto.getFineDisponibilita())) {
                piattiValidi.add(aPiatto);
            }
        }

        return piattiValidi;
    }
}
