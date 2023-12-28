package unibs.ing.progettosw.ristorante.domain;

import unibs.ing.progettosw.exceptions.ErrorDialog;
import unibs.ing.progettosw.exceptions.ErrorLogger;
import unibs.ing.progettosw.utilities.JSONFileReader;
import unibs.ing.progettosw.utilities.StringToClassGetter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Cucina {

    /*
     * Classe che rappresenta la Cucina del ristorante.
     * */

    //Attributi privati
    private Map<IMerce, Integer> registroCucina;//Integer è la quantità in cucina che deve essere <= di quella disponibile in maghazizino

    private Random rand = new Random();
    private Magazzino magazzino;
    private JSONFileReader jfr = new JSONFileReader();
    private Gestore gestore;
    private StringToClassGetter stc = new StringToClassGetter();
    private ErrorLogger el = new ErrorLogger();

    // invariante di classe
    public Cucina(Map<IMerce, Integer> registroCucina, Gestore gestore, Magazzino magazzino) {
        this.registroCucina = registroCucina;
        this.gestore = gestore;
        this.magazzino = magazzino;
    }

    // metodo utilizzato per "simulare" le comande di una cucina, a partire dalla lista delle prenotazioni accettate
    // pre : -
    // post :
    public void eseguiComande() {
        List<Prenotazione> listaPrenotazioniAccettate = recuperaPrenotazioniAccettate();

        if (listaPrenotazioniAccettate != null) {
            for (Prenotazione p : listaPrenotazioniAccettate) {
                if (p != null) {
                    cucinaPiatto(p);
                } else {
                    el.logError(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()) + ": Attenzione! Nessuna prenotazione trovata. " +
                            "Assicurarsi che sia tutto corretto.\n");
                }
            }
        } else {
            System.out.println("\nNon ci sono prenotazioni oggi, si torna a casa...\n");
        }
    }

    // metodo che permette di recuperare da un opportuno file .json le prenotazioni che sono state accettate
    // pre : -
    // post : prenotazioni deve essere una lista di solo prenotazioni accettate (anche vuota - empty)
    //        prenotazioni.size() >= 0
    private List<Prenotazione> recuperaPrenotazioniAccettate() {
        List<Prenotazione> prenotazioni = null;
        prenotazioni = jfr.leggiPrenotazioni("/initFiles/prenotazioniAccettate.json", "prenotazioniAccettate");
        return prenotazioni;
    }

    // metodo che permette di "cucinare" un piatto data una Prenotazione
    // pre : aPreno deve essere una prenotazione accettata && aPreno != NULL
    // post : piatti e menu tematici specificati dalla Prenotazione aPreno "cucinati" && quantità degli ingredienti relativi
    // decrementata opportunamente
    private void cucinaPiatto(Prenotazione aPreno) {
        Map<String, Integer> piattiPrenotati = aPreno.getPiattoPrenotato();
        estraiPiattiDaMappa(piattiPrenotati);
        Map<String, Integer> menuTPrenotati = aPreno.getMenuTematicoPrenotato();
        estraiMenuDaMappa(menuTPrenotati);
    }

    // metodo che permette di "cucinare" (simulare la "preparazione") i piatti di ciascun menuTematico prenotato
    // pre : menuTPrenotati != NULL
    // post : piatti costituenti ciascun menu tematico prenotato "cucinati" && quantità ingredienti relativi decrementata
    // opportunamente, ingredienti della cucina decrementati a seconda dell'esigenze del piatto
    private void estraiMenuDaMappa(Map<String, Integer> menuTPrenotati) {
        for (Map.Entry<String, Integer> entry : menuTPrenotati.entrySet()) {
            Menu menuT = stc.getMenuTematicofromNome(gestore.getMenuT(), entry.getKey());
            System.out.println("\nStiamo cucinando " + menuT.getNome() + ", portate pazienza...");
            List<Piatto> listaPiatti = estraiPiattiDaMenuT(menuT);
            for (Piatto aPiatto : listaPiatti) {
                System.out.println("Stiamo cucinando " + aPiatto.getNome());
                Ricetta r = aPiatto.getRicetta();
                estraiIngredientiDaRicetta(r);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    StringWriter sWriter = new StringWriter();
                    e.printStackTrace(new PrintWriter(sWriter));
                    ErrorDialog.getInstance().logError("Il programma si è interrotto in maniera improvvisa.\n");
                    ErrorLogger.getInstance().logError(sWriter.toString()+"\n");
                }
            }
        }
    }

    // metodo che restituisce una lista di piatti costituenti un dato menu tematico
    // pre : menuT != NULL
    // post : menuT.getPiatti().size() > 0
    private List<Piatto> estraiPiattiDaMenuT(Menu menuT) {
        return menuT.getPiatti();
    }

    // metodo che permette di "cucinare" ciascun piatto specificato da mappaPietanze.
    // ovvero di "cucinare" ciascuna ricetta rappresentante quel piatto
    // pre : mappaPietanze != NULL
    // post : ricetta costituente ciascun piatto "cucinata" && quantità ingredienti relativi decrementate opportunamente
    private void estraiPiattiDaMappa(Map<String, Integer> mappaPietanze) {
        for (Map.Entry<String, Integer> entry : mappaPietanze.entrySet()) {
            System.out.println("\nStiamo cucinando " + entry.getKey() + ", portate pazienza...");
            Ricetta r = estraiRicettaDaPiatti(entry);
            estraiIngredientiDaRicetta(r);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                StringWriter sWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(sWriter));
                ErrorDialog.getInstance().logError("Il programma si è interrotto in maniera improvvisa.\n");
                ErrorLogger.getInstance().logError(sWriter.toString()+"\n");
            }
        }
    }

    // metodo che permette di cucinare una data Ricetta r effettuando un controllo su eventuali ingredienti scadenti
    // vedere metodo isScandente()
    // pre : r != NULL
    // post : quantità ingredienti relativi alla Ricetta r decrementate oppotunamente
    private void estraiIngredientiDaRicetta(Ricetta r) {
        boolean isScadente;
        for (Map.Entry<String, Integer> entry : r.getIngredienti().entrySet()) {
            do {
                IMerce anIngre = stc.getIngredienteFromNome(gestore.getIngredienti(), entry.getKey());
                int decremento = entry.getValue();
                decrementaQuantitaIngredienteDaCucina(anIngre, decremento);
                isScadente = isProdottoScadente();
                if (isScadente) {
                    System.out.println(anIngre.getNome() + " è di cattiva qualità! Ritentiamo un'altra volta...");
                }
            } while (isScadente);
        }
    }

    // metodo che permette decrementare la quantità di un dato ingrediente secondo un dato decremento
    // pre : ingr è di tipo IMerce && ingr != NULL, decremento > 0
    // post: quantità dell'ingrediente decrementata secondo il valore specificato dalla variabile decremento.
    private void decrementaQuantitaIngredienteDaCucina(IMerce ingr, int decremento) {
        int quantitaPresente = this.registroCucina.get(ingr);

        if ((quantitaPresente - decremento) < 0) {
            System.out.println("Manca l'ingrediente! Un attimo che lo recuperiamo dal magazzino...");
            magazzino.decrementaQuantitaMerce(ingr, 100);
        } else {
            this.registroCucina.put(ingr, quantitaPresente - decremento);
        }
    }

    // metodo che restituisce una Ricetta associata ad un piatto specificato da entry di tipo <NomePiatto, Integer>
    // pre : entry != NULL
    // post : valore_ritornato è LA Ricetta associata al piatto specificato
    private Ricetta estraiRicettaDaPiatti(Map.Entry<String, Integer> entry) {
        return stc.getPiattofromNome(gestore.getPiatti(), entry.getKey()).getRicetta();
    }

    // metodo che permette di verificare se un prodotto è scadente (casualmente, estraggo un numero da 0 a 100 minore di 5 lo scarto)
    // pre : -
    // post : true o false : true : a < 5
    //                       false : a >= 5
    private boolean isProdottoScadente() {
        // probabilità del 5% di scartare il prodotto
        int a = rand.nextInt(101);
        return a < 5;
    }
}
