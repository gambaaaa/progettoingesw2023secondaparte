package unibs.ing.progettosw.ristorante.controller;

import unibs.ing.progettosw.ristorante.domain.*;
import unibs.ing.progettosw.ristorante.view.ViewRistorante;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Controller {
    /*
     * Classe che rappresenta il Controller del package unibs.ing.progettosw.ristorante conforme con il pattern MVC.
     * */

    private ViewRistorante view; // View package unibs.ing.progettosw.ristorante
    private Gestore gestore;
    private AddettoPrenotazioni addetto;
    private Magazziniere magazziniere;
    private Magazzino magazzino;
    private Cucina cucina;

    public Controller() throws ParseException {
        gestore = new Gestore();
    }

    // legge i file .json e carica le informazioni del ristorante (ristorante, bevande, ingrediente, etc...)
    public void caricaGestore() throws ParseException {
        this.gestore = new Gestore();
        this.gestore.initAll();
    }

    // stampa le informazioni relative al ristorante (posti a sedere e carico di lavoro)
    public String stampaInformazioniRistorante() {
        return this.gestore.stampaInfoRistorante();
    }

    // scrive le prenotazioni accettate su file, simulando lo scorrere del tempo
    // pre : giorniPassati > 0
    // post : prenotazione scritta su file, se valida
    public void caricaAddetto(int giorniPassati) throws IOException, ParseException {
        addetto = new AddettoPrenotazioni(giorniPassati);
    }

    // carica le informazioni relative al magazziniere
    public void caricaMagazziniere() throws ParseException {
        magazziniere = new Magazziniere(gestore);
    }

    // carica le informazioni relative al magazzino creando la lista della spesa
    public void simulaMagazzino() {
        magazzino = new Magazzino(magazziniere.creaListaDellaSpesa(gestore));
    }

    // simula il funzionamento della cucina
    public void simulaCucina() throws ParseException, InterruptedException {
        cucina = new Cucina(magazziniere.creaListaCucina(gestore), gestore, magazzino);
        cucina.eseguiComande();
    }
}
