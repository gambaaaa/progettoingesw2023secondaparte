package unibs.ing.progettosw.utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import unibs.ing.progettosw.exceptions.ErrorDialog;
import unibs.ing.progettosw.exceptions.ErrorLogger;
import unibs.ing.progettosw.ristorante.domain.Prenotazione;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

public class JSONFileWriter extends JSONFile {
    public void scriviPrenotazioneSuFile(Prenotazione p, String path, String key, String typePrenotazione) {
        JSONObject object = readFromJSON(path);
        JSONArray prenotazioni = object.getJSONArray(key);

        JSONObject obj = new JSONObject(p);
        prenotazioni.put(obj);

        JSONObject prenotazioniObj = new JSONObject();
        prenotazioniObj.put(typePrenotazione, prenotazioni);

        File filePath = creaFilePath(path);
        scriviSuFile(prenotazioniObj, filePath);
    }

    private File creaFilePath(String path) {
        path = path.replace('/', '\\');
        return new File(path.substring(1));
    }

    private void scriviSuFile(JSONObject prenotazioniObj, File filePath) {
        FileWriter fileWriter = null;
        StringWriter sWriter = new StringWriter();

        try {
            fileWriter = new FileWriter(filePath);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()) + ": Errore durante il salvataggio della prenotazione. Controllare il percorso della cartella.\n");
            ErrorLogger.getInstance().logError(sWriter.toString() + "\n");
        }
        try {
            fileWriter.write(prenotazioniObj.toString());
            fileWriter.flush();
        } catch (Exception e) {
            //e.printStackTrace();
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()) + ": Errore durante il salvataggio della prenotazione. Controllare che la prenotazione sia valida.\n");
            ErrorLogger.getInstance().logError(sWriter.toString() + "\n");
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()) + ": Errore durante la chiusura dello stream.\n");
            ErrorLogger.getInstance().logError(sWriter.toString() + "\n");
        }
    }

    public void scriviPrenotazioniAccettateSuFile(List<Prenotazione> prenotazioniAccettate, String path, String typePrenotazione) {
        File filePath = creaFilePath(path);
        creaPrenotazioniVuote(path);
        JSONObject prenotazioniObj = new JSONObject();
        prenotazioniObj.put(typePrenotazione, prenotazioniAccettate);

        scriviSuFile(prenotazioniObj, filePath);
    }

    public void creaPrenotazioniVuote(String path) {
        File filePath = new File(path.substring(1));
        filePath.delete();
        Path accettateOld = Paths.get(path.substring(1));
        Path vuotoNew = Paths.get("initFiles\\prenotazioniAccettateVuote.json");
        try {
            Files.copy(vuotoNew, accettateOld);
        } catch (IOException e) {
            StringWriter sWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError("Errore nel copiare i file. Controllare che esistano e siano corretti.\n");
            ErrorLogger.getInstance().logError(sWriter.toString() + "\n");
        }
    }
}