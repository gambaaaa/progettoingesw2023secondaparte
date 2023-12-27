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

    public void scriviGiornoSuFile(int giorniPassati) throws IOException {
        File giornoFile = new File("initFiles\\giorniPassati.txt");
        FileWriter fw = new FileWriter(giornoFile);
        fw.write(String.valueOf(giorniPassati));
        fw.flush();
        fw.close();
    }

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
        ;
        try {
            fileWriter = new FileWriter(filePath);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) + ": Errore durante il salvataggio della prenotazione. Controllare il percorso della cartella.");
            ErrorLogger.getInstance().logError(sWriter.toString());
        }
        try {
            fileWriter.write(prenotazioniObj.toString());
            fileWriter.flush();
        } catch (Exception e) {
            //e.printStackTrace();
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) + ": Errore durante il salvataggio della prenotazione. Controllare che la prenotazione sia valida.");
            ErrorLogger.getInstance().logError(sWriter.toString());
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace(new PrintWriter(sWriter));
            ErrorDialog.getInstance().logError(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) + ": Errore duurante la chiusura dello stream.");
            ErrorLogger.getInstance().logError(sWriter.toString());
        }
    }

    public void scriviPrenotazioniAccettateSuFile(List<Prenotazione> prenotazioniAccettate, String path, String typePrenotazione) throws IOException {
        File filePath = creaFilePath(path);
        creaPrenotazioniVuote(path.substring(1));
        JSONObject prenotazioniObj = new JSONObject();
        prenotazioniObj.put(typePrenotazione, prenotazioniAccettate);

        scriviSuFile(prenotazioniObj, filePath);
    }

    public void creaPrenotazioniVuote(String path) throws IOException {
        File filePath = new File(path.substring(1));
        filePath.delete();
        Path accettateOld = Paths.get(path.substring(1));
        Path vuotoNew = Paths.get("initFiles\\prenotazioniAccettateVuote.json");
        Files.copy(vuotoNew, accettateOld);
    }
}
