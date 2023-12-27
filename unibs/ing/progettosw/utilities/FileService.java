package unibs.ing.progettosw.utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import unibs.ing.progettosw.ristorante.domain.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileService {

    private final StringToDateConverter std = new StringToDateConverter();
    private final StringToClassGetter stc = new StringToClassGetter();

    public FileService() {
    }

    private JSONObject readFromJSON(String path) {
        JSONObject object;
        InputStream is = FileService.class.getResourceAsStream(path);
        JSONTokener tokener = new JSONTokener(is);
        object = new JSONObject(tokener);

        return object;
    }

    public List<Prenotazione> leggiPrenotazioni(String path, String key) throws ParseException {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray prenotazioni = object.getJSONArray(key);

        if (prenotazioni.isEmpty()) {
            return null;
        }
        return creaPrenotazioni(prenotazioni);
    }

    private List<Prenotazione> creaPrenotazioni(JSONArray prenotazioni) throws ParseException {
        List<Prenotazione> prenotazioniList = new ArrayList();
        for (int i = 0; i < prenotazioni.length(); i++) {
            JSONObject aPreno = (JSONObject) prenotazioni.get(i);
            JSONObject menuTematiciJSON = aPreno.getJSONObject("menuTematicoPrenotato");
            Map<String, Integer> menuTematiciList = ObjectToMap(menuTematiciJSON);
            Date data = std.creaDataDaStringaCompleta(aPreno.getString("data"));
            Date dataPrenotazione = std.creaDataDaStringaCompleta(aPreno.getString("dataPrenotazione"));
            int numeroCoperti = aPreno.getInt("numeroCoperti");
            JSONObject piattiJSON = aPreno.getJSONObject("piattoPrenotato");
            Map<String, Integer> piattiList = ObjectToMap(piattiJSON);
            prenotazioniList.add(new Prenotazione(data, dataPrenotazione, numeroCoperti, menuTematiciList, piattiList));
        }

        return prenotazioniList;
    }

    private Map<String, Integer> ObjectToMap(JSONObject array) {
        Map<String, Integer> map = new HashMap<>();

        Iterator<?> keys = array.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            int value = array.getInt(key);
            map.put(key, value);
        }

        return map;
    }

    private Menu creaMenuTematico(JSONObject key, List<Ricetta> ricette) throws ParseException {
        String nome = key.getString("nome");
        JSONArray piatti = key.getJSONArray("piatti");
        List<Piatto> listaPiatti = creaListaPiatti(piatti, ricette);
        double caricoLavoro = key.getDouble("caricoLavoro");
        return new Menu(nome, listaPiatti, caricoLavoro);
    }

    private List<Menu> creaListaMenuTematico(JSONArray menu, List<Ricetta> ricette) throws ParseException {
        List<Menu> list = new ArrayList<>();

        for (int i = 0; i < menu.length(); i++) {
            list.add(creaMenuTematico((JSONObject) menu.get(i), ricette));
        }

        return list;
    }

    public List<Menu> setupMenuTematico(String path, String key, List<Ricetta> ricette) throws ParseException {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray menu = object.getJSONArray(key);
        return creaListaMenuTematico(menu, ricette);
    }

    private Piatto creaPiatto(JSONObject key, List<Ricetta> ricette) throws ParseException {
        String nomePiatto = key.getString("nome");
        String nomeRicetta = key.getString("ricetta");
        Ricetta ricetta = stc.getRicettafromNome(ricette, nomeRicetta);
        int caricoLavoro = key.getInt("caricoLavoro");
        Date inizioDisponibilita = std.creaDataDaStringa(key.getString("inizioDisponibilita"));
        Date fineDisponibilita = std.creaDataDaStringa(key.getString("fineDisponibilita"));

        return new Piatto(nomePiatto, ricetta, caricoLavoro, inizioDisponibilita, fineDisponibilita);
    }

    private Ricetta creaSingolaRicetta(JSONObject ricettaJSON) {
        String nome = ricettaJSON.getString("nome");
        int porzione = ricettaJSON.getInt("porzione");
        double caricoLavoroPorzione = ricettaJSON.getDouble("caricoLavoroPorzione");
        JSONArray ingredientiJSON = ricettaJSON.getJSONArray("ingredienti");
        Map<String, Integer> ingredientiList = JSONArrayToMap(ingredientiJSON);

        return new Ricetta(nome, ingredientiList, porzione, caricoLavoroPorzione);
    }

    private List<Piatto> creaListaPiatti(JSONArray piatti, List<Ricetta> ricette) throws ParseException {
        List<Piatto> list = new ArrayList<>();

        for (int i = 0; i < piatti.length(); i++) {
            list.add(creaPiatto((JSONObject) piatti.get(i), ricette));
        }

        return list;
    }

    public void setupRistorante(String path) {
        JSONObject object;
        object = readFromJSON(path);
        creaRistorante(object);
    }

    private void creaRistorante(JSONObject object) {
        int caricoLavoroPersona = object.getInt("caricoLavoroPersona");
        int postiSedere = object.getInt("postiSedere");
        String nome = object.getString("nome");
        Ristorante.getInstance(nome, postiSedere, caricoLavoroPersona);
    }

    public List<IMerce> setupBevande(String path, String key) {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray merce = object.getJSONArray(key);
        return creaBevande(merce);
    }

    private List<IMerce> creaBevande(JSONArray object) {
        List<IMerce> merceList = new ArrayList();
        for (int i = 0; i < object.length(); i++) {
            JSONObject aMerce = (JSONObject) object.get(i);
            String nome = aMerce.getString("nome");
            int quantita = aMerce.getInt("quantita");
            String udm = aMerce.getString("UdM");
            int consumoProCapite = aMerce.getInt("consumoProCapite");
            merceList.add(new Bevanda(nome, quantita, udm, consumoProCapite));
        }
        return merceList;
    }

    public List<IMerce> setupGeneriExtra(String path, String key) {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray generi = object.getJSONArray(key);
        return GeneriExtra(generi);
    }

    private List<IMerce> GeneriExtra(JSONArray object) {
        List<IMerce> merceList = new ArrayList();
        for (int i = 0; i < object.length(); i++) {
            JSONObject aMerce = (JSONObject) object.get(i);
            String nome = aMerce.getString("nome");
            int quantita = aMerce.getInt("quantita");
            String udm = aMerce.getString("UdM");
            int consumoProCapite = aMerce.getInt("consumoProCapite");
            merceList.add(new GenereExtra(nome, quantita, udm, consumoProCapite));
        }

        return merceList;
    }

    public List<IMerce> setupIngredienti(String path, String key) throws ParseException {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray merce = object.getJSONArray(key);
        return creaIngredienti(merce);
    }

    private List<IMerce> creaIngredienti(JSONArray object) throws ParseException {
        List<IMerce> merceList = new ArrayList();
        for (int i = 0; i < object.length(); i++) {
            JSONObject aMerce = (JSONObject) object.get(i);
            String nome = aMerce.getString("nome");
            int quantita = aMerce.getInt("quantita");
            String udm = aMerce.getString("UdM");
            String stringaScadenza = aMerce.getString("dataScadenza");
            Date dataScadenza = new SimpleDateFormat("dd/MM/yyyy").parse(stringaScadenza);
            merceList.add(new Ingrediente(nome, quantita, udm, dataScadenza));
        }

        return merceList;
    }

    public List<Ricetta> setupRicetta(String path) {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray ricette = object.getJSONArray("ricette");
        return creaRicette(ricette);
    }

    private List<Ricetta> creaRicette(JSONArray object) {
        List
                <Ricetta> ricetteList = new ArrayList();
        for (int i = 0; i < object.length(); i++) {
            JSONObject ricetta = (JSONObject) object.get(i);
            String nome = ricetta.getString("nome");
            int porzione = ricetta.getInt("porzione");
            double caricoLavoroPorzione = ricetta.getDouble("caricoLavoroPorzione");
            JSONArray ingredientiJSON = ricetta.getJSONArray("ingredienti");
            Map<String, Integer> ingredientiList = JSONArrayToMap(ingredientiJSON);
            ricetteList.add(new Ricetta(nome, ingredientiList, porzione, caricoLavoroPorzione));
        }

        return ricetteList;
    }

    private Map<String, Integer> JSONArrayToMap(JSONArray array) {
        Map<String, Integer> map = new HashMap<>();

        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                //Adding each element of JSON array into ArrayList
                JSONObject objectMap = (JSONObject) array.get(i);
                String key = objectMap.getString("key");
                int value = objectMap.getInt("value");
                map.put(key, value);
            }
        }

        return map;
    }

    public void scriviPrenotazioneSuFile(Prenotazione p, String path, String key, String typePrenotazione) {
        JSONObject object = readFromJSON(path);
        JSONArray prenotazioni = object.getJSONArray(key);

        JSONObject obj = new JSONObject(p);
        prenotazioni.put(obj);

        JSONObject prenotazioniObj = new JSONObject();
        prenotazioniObj.put(typePrenotazione, prenotazioni);

        path = path.replace('/', '\\');

        File filePath = new File(path.substring(1));

        //Momentaneamente lasciamo questo codice (per poter chiudere il fileWriter) .  Funzionante !!
        FileWriter fileWriter = null;
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

    public void scriviPrenotazioniAccettateSuFile(List<Prenotazione> prenotazioniAccettate, String path, String key, String typePrenotazione) throws IOException {
        JSONObject object = readFromJSON(path);
        JSONArray prenotazioni = object.getJSONArray(key);
        FileWriter fileWriter = null;

        path = path.replace('/', '\\');

        File filePath = new File(path.substring(1));
        creaPrenotazioniVuote(path.substring(1));

        JSONObject prenotazioniObj = new JSONObject();
        prenotazioniObj.put(typePrenotazione, prenotazioniAccettate);

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

    public void creaPrenotazioniVuote(String path) throws IOException {
        File filePath = new File(path.substring(1));
        filePath.delete();
        Path accettateOld = Paths.get(path.substring(1));
        Path vuotoNew = Paths.get("initFiles\\prenotazioniAccettateVuote.json");
        Files.copy(vuotoNew, accettateOld);
    }


    public List<Piatto> setupPiatti(String path, String key, List<Ricetta> ricette) throws ParseException {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray merce = object.getJSONArray(key);
        return creaPiatti(merce, ricette);
    }

    private List<Piatto> creaPiatti(JSONArray object, List<Ricetta> ricette) throws ParseException {
        List<Piatto> piattiList = new ArrayList();
        for (int i = 0; i < object.length(); i++) {
            JSONObject aPiatto = (JSONObject) object.get(i);
            String nome = aPiatto.getString("nome");
            String nomeRicetta = aPiatto.getString("ricetta");
            Ricetta ricetta = stc.getRicettafromNome(ricette, nomeRicetta);
            String inizioDisponibilita = aPiatto.getString("inizioDisponibilita");
            String fineDisponibilita = aPiatto.getString("fineDisponibilita");
            Date dataInizio = std.creaDataDaStringa(inizioDisponibilita);
            Date dataFine = std.creaDataDaStringa(fineDisponibilita);
            piattiList.add(new Piatto(nome, ricetta, ricetta.getCaricoLavoroPorzione(), dataInizio, dataFine));
        }

        return piattiList;
    }

    public int leggiGiornoDaFile() throws FileNotFoundException {
        File giornoFile = new File("initFiles\\giorniPassati.txt");
        Scanner scanner = new Scanner(giornoFile);
        int giorniPassati = 0;
        while (scanner.hasNextLong()) {
            giorniPassati = scanner.nextInt();
        }

        return giorniPassati;
    }

    public void scriviGiornoSuFile(int giorniPassati) throws IOException {
        File giornoFile = new File("initFiles\\giorniPassati.txt");
        FileWriter fw = new FileWriter(giornoFile);
        fw.write(String.valueOf(giorniPassati));
        fw.flush();
        fw.close();
    }

    public void azzeraGiornoSuFile() throws IOException {
        File giornoFile = new File("initFiles\\giorniPassati.txt");
        FileWriter fw = new FileWriter(giornoFile);
        fw.write(String.valueOf(0));
        fw.flush();
        fw.close();
    }
}
