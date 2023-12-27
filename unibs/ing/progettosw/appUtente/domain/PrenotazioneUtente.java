package unibs.ing.progettosw.appUtente.domain;

import unibs.ing.progettosw.ristorante.domain.Prenotazione;
import unibs.ing.progettosw.utilities.JSONFileWriter;

public class PrenotazioneUtente {
    /*
     * Classe utilizzata per salvare su file una prenotazione.
     * */
    private JSONFileWriter jfw = new JSONFileWriter();

    public PrenotazioneUtente() {
    }

    // salva la prenotazione nel file prenotazioni.json
    // pre : prenotazione not null
    // post : salva la prenotazione su file
    public void creaPrenotazione(Prenotazione p) {
        jfw.scriviPrenotazioneSuFile(p, "/initFiles/prenotazioni.json", "prenotazioni", "prenotazioni");
    }
}
