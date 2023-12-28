package unibs.ing.progettosw.appUtente.view;

public class UserView {
    /*
     * View utilizzata per stampare su console le informazioni essenziali per creare una corretta prenotazione.
     * */
    public void interazioneInizialeUI(int dayPassed) {
        System.out.println("Buongiorno, benvenuto alla simulazione:");
        UserViewFacade uwf = new UserViewFacade();
        uwf.startViewFacade(dayPassed);
    }
}
