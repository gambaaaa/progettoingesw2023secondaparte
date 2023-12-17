package unibs.ing.progettosw.utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import unibs.ing.progettosw.ristorante.domain.Prenotazione;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fileWriter.write(prenotazioniObj.toString());
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
