package unibs.ing.progettosw.appUtente.view;

import java.text.ParseException;

public class UserView {
    /*
     * View utilizzata per stampare su console le informazioni essenziali per creare una corretta prenotazione.
     * */
    public void interazioneInizialeUI(int dayPassed) throws ParseException {
        System.out.println("Buongiorno, benvenuto alla simulazione:");
        UserViewFacade uwf = new UserViewFacade();
        uwf.startViewFacade(dayPassed);
    }
}
