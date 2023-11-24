package unibs.ing.progettosw.utilities;

import java.text.Format;
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
