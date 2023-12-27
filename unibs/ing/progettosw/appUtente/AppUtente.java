package unibs.ing.progettosw.appUtente;

import unibs.ing.progettosw.appUtente.view.UserView;

import java.text.ParseException;

public class AppUtente {
    /*
     * Classe utilizzata per creare le prenotazioni lato utente - da parte degli utenti
     * */
    public void startAppUtente(int dayPassed) throws ParseException {
        UserView view = new UserView();
        view.interazioneInizialeUI(dayPassed);
    }
}
