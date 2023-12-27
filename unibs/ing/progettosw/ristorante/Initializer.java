package unibs.ing.progettosw.ristorante;

import unibs.ing.progettosw.ristorante.view.ViewRistorante;

import java.io.IOException;
import java.text.ParseException;

public class Initializer {
    /*
     * Classe che istanzia la view.
     * */
    private ViewRistorante view = new ViewRistorante();

    public Initializer() throws ParseException {
    }

    // metodo che stampa su console le informazioni principali del ristorante
    public void startRistorante() throws ParseException {
        view.interazioneInizialeUI();
    }

    // metodo che salva su file le prenotazioni accettate dagli addetti
    public void initPrenotazioni(int giorniPassati) throws IOException, ParseException, InterruptedException {
        view.caricaPrenotazioni(giorniPassati);
    }
}
