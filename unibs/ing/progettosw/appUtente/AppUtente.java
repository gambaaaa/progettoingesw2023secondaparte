package unibs.ing.progettosw.appUtente;

import java.text.ParseException;

import unibs.ing.progettosw.appUtente.view.UserView;

public class AppUtente {
    /*
     * Classe utilizzata per creare le prenotazioni lato utente - da parte degli utenti
     * */
    public void startAppUtente(int dayPassed) throws ParseException {
        UserView view = new UserView();
        view.interazioneInizialeUI(dayPassed);
    }
}
