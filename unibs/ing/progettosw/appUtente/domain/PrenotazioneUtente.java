package unibs.ing.progettosw.appUtente.domain;

import unibs.ing.progettosw.utilities.FileService;
import unibs.ing.progettosw.ristorante.domain.Prenotazione;

import java.text.ParseException;

public class PrenotazioneUtente {
    /*
     * Classe utilizzata per salvare su file una prenotazione.
     * */
    private FileService fs = new FileService();

    public PrenotazioneUtente() {
    }

    // salva la prenotazione nel file prenotazioni.json
    // pre : prenotazione not null
    // post : salva la prenotazione su file
    public void creaPrenotazione(Prenotazione p) {
        fs.scriviPrenotazioneSuFile(p, "/initFiles/prenotazioni.json", "prenotazioni", "prenotazioni");
    }
}
