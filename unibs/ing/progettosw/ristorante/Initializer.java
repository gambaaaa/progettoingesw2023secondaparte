package unibs.ing.progettosw.ristorante;

import unibs.ing.progettosw.ristorante.view.ViewRistorante;

public class Initializer {
    /*
     * Classe che istanzia la view.
     * */
    private ViewRistorante view = new ViewRistorante();

    public Initializer() {
    }

    // metodo che stampa su console le informazioni principali del ristorante
    public void startRistorante() {
        view.interazioneInizialeUI();
    }

    // metodo che salva su file le prenotazioni accettate dagli addetti
    public void initPrenotazioni(int giorniPassati) {
        view.caricaPrenotazioni(giorniPassati);
    }
}
