package unibs.ing.progettosw.utilities;

import unibs.ing.progettosw.ristorante.domain.Prenotazione;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtility {
    /*
     * Classe di utilità. Contiene metodi utili per trattare correttamente le date all'interno dell'applicazione.
     * */

    // metodo che ritorna la data odierna (senza contare lo scorrimento del tempo
    // post : data odierna
    public Date todaysDate() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate today = LocalDate.now();
        Date todayDate = Date.from(today.atStartOfDay(defaultZoneId).toInstant());
        return todayDate;
    }


    // metodo che restituisce la data del giorno contando i giorni trascorsi.
    // post : data di (oggi + giorni trascorsi)
    public Date dateSinceDayPassed(int giorniPassati) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate today = LocalDate.now();
        today = today.plusDays(giorniPassati);
        Date todayDate = Date.from(today.atStartOfDay(defaultZoneId).toInstant());
        return todayDate;
    }

    public boolean isSunday(int giorniPassati) {
        return dayPassed(giorniPassati) == Calendar.SUNDAY;
    }

    // metodo che permette di controllare se una specifica prenotazione in ingresso
    // è stata effettuata con almeno un giorno d'anticipo/ "almeno ieri" rispetto alla data odierna;
    // se la data di prenotazione di una specifica prenotazione precede quella odierna-oggi.
    // Vedi metodo soprastante isDataPrenotazioneValida
    // pre : p != NULL
    // post : valore_ritornato = -1 se prenotazione "è arrivata" almeno ieri ("data prenotazione < data odierna-oggi") - se la data di prenotazione precede quella odierna
    //        valore_ritornato = 0  se la data di prenotazione di p corrisponde/coincide alla/con la data odierna
    //        valore_ritornato = 1  se la data di prenotazione di p segue la data odierna
    public int atLeastYesterday(Prenotazione p, int giorniPassati) {
        //Controllo che d non sia uguale a today ma "minore" --> < 0
        //se ritorna -1 (<=) sicuramente la data di prenotazione "è arrivata" almeno ieri
        return p.getDataPrenotazione().compareTo(dateSinceDayPassed(giorniPassati)); // se si usa DateUtility ==> return almenoIeri(p.getDataPrenotazione)
    }

    // Metodo che restituisce il giorno a seconda di quanto tempo è passato nella simulazione
    // pre : dayPassed >= 0
    // post : data != null, data >= data odierna
    public Date getDatePassedSinceToday(int dayPassed) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(todaysDate());
        cal.add(Calendar.DATE, dayPassed);
        return cal.getTime();
    }

    // Metodo che restituisce il nome del giorno (in italiano) secondo la formula giorno odierno + giorni trascorsi, il metodo è usato
    // per simulare il passaggio del tempo
    // pre : dayPassed > 0
    // post : nome di uno dei giorni della settimana
    public String nameOfDaySinceToday(int dayPassed) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(todaysDate());
        cal.add(Calendar.DATE, dayPassed);
        return new SimpleDateFormat("EEEE", Locale.ITALIAN).format(cal.getTime());
    }

    // Metodo che restituisce il numero del giorno della settimana rispetto alla data attuale (data attuale + dayPassed).
    // Pre : dayPassed >= 0
    // Post : 1 <= cal.get(Calendar.DAY_OF_WEEK) <= 7
    public int dayPassed(int dayPassed) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(todaysDate());
        cal.add(Calendar.DATE, dayPassed);
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    // Metodo che restituisce l'ultimo giorno del mese.
    // Pre : month >= 0 && month <= 12
    // Post : day = 28 or day = 30 or day = 31 (a seconda del mese)
    public int getLastDayOfMonth(int month) {
        YearMonth ym = YearMonth.of(2023, month);
        return ym.atEndOfMonth().getDayOfMonth();
    }
}
