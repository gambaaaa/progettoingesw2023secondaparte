package unibs.ing.progettosw.utilities;

import unibs.ing.progettosw.exceptions.ErrorDialog;
import unibs.ing.progettosw.exceptions.ErrorLogger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter {
    /*
     * Classe di utilità. Contiene metodi utili per convertire delle stringhe in oggetto di tipo Date.
     * */

    // data una stringa nel formato dd/MM/YYYY crea un oggetto di tipo Date
    // pre : data nel formato dd/MM/YYYY
    // post : data not null, data è un oggetto valido di tipo Date
    public Date creaDataDaStringa(String data) {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.parse(data);
        } catch (ParseException e) {
            StringWriter sWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError("Errore nella formattazione della data.\n");
            ErrorLogger.getInstance().logError(sWriter.toString() + "\n");
        }
        return null;
    }

    // data una stringa nel formato EEE MMM dd HH:mm:ss zzz yyyy crea un oggetto di tipo Date
    // pre : data nel formato EEE MMM dd HH:mm:ss zzz yyyy
    // post : data not null, data è un oggetto valido di tipo Date
    public Date creaDataDaStringaCompleta(String data) {
        try {
            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            return df.parse(data);
        } catch (ParseException e) {
            StringWriter sWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError("Errore nella formattazione della data.\n");
            ErrorLogger.getInstance().logError(sWriter.toString() + "\n");
        }
        return null;
    }
}
