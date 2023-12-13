package unibs.ing.progettosw.utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import unibs.ing.progettosw.ristorante.domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JSONFileReader extends JSONFile {
    private final StringToDateConverter std = new StringToDateConverter();
    private final StringToClassGetter stc = new StringToClassGetter();
    private final JSONUtilities ju = new JSONUtilities();

    public void setupRistorante(String path) {
        JSONObject object;
        object = readFromJSON(path);
        creaRistorante(object);
    }

    private void creaRistorante(JSONObject ristorante) {
        int caricoLavoroPersona = ristorante.getInt("caricoLavoroPersona");
        int postiSedere = ristorante.getInt("postiSedere");
        String nome = ristorante.getString("nome");
        Ristorante.getInstance(nome, postiSedere, caricoLavoroPersona);
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
            Map<String, Integer> menuTematiciList = ju.objectToStrIntMap(menuTematiciJSON);
            Date data = std.creaDataDaStringaCompleta(aPreno.getString("data"));
            Date dataPrenotazione = std.creaDataDaStringaCompleta(aPreno.getString("dataPrenotazione"));
            int numeroCoperti = aPreno.getInt("numeroCoperti");
            JSONObject piattiJSON = aPreno.getJSONObject("piattoPrenotato");
            Map<String, Integer> piattiList = ju.objectToStrIntMap(piattiJSON);
            prenotazioniList.add(new Prenotazione(data, dataPrenotazione, numeroCoperti, menuTematiciList, piattiList));
        }

        return prenotazioniList;
    }

    private List<Piatto> creaListaPiatti(JSONArray piatti, List<Ricetta> ricette) throws ParseException {
        List<Piatto> list = new ArrayList<>();

        for (int i = 0; i < piatti.length(); i++) {
            list.add(creaPiatto((JSONObject) piatti.get(i), ricette));
        }

        return list;
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

    private List<Menu> creaListaMenuTematico(JSONArray menu, List<Ricetta> ricette) throws ParseException {
        List<Menu> list = new ArrayList<>();

        for (int i = 0; i < menu.length(); i++) {
            list.add(creaMenuTematico((JSONObject) menu.get(i), ricette));
        }

        return list;
    }

    private Menu creaMenuTematico(JSONObject key, List<Ricetta> ricette) throws ParseException {
        String nome = key.getString("nome");
        JSONArray piatti = key.getJSONArray("piatti");
        List<Piatto> listaPiatti = creaListaPiatti(piatti, ricette);
        double caricoLavoro = key.getDouble("caricoLavoro");
        return new Menu(nome, listaPiatti, caricoLavoro);
    }

    public List<Menu> setupMenuTematico(String path, String key, List<Ricetta> ricette) throws ParseException {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray menu = object.getJSONArray(key);
        return creaListaMenuTematico(menu, ricette);
    }

    public ArrayList<IMerce> setupBevande(String path, String key) {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray merce = object.getJSONArray(key);
        return creaBevande(merce);
    }

    private ArrayList<IMerce> creaBevande(JSONArray object) {
        ArrayList<IMerce> merceList = new ArrayList();
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

    public ArrayList<IMerce> setupGeneriExtra(String path, String key) {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray generi = object.getJSONArray(key);
        return GeneriExtra(generi);
    }

    private ArrayList<IMerce> GeneriExtra(JSONArray object) {
        ArrayList<IMerce> merceList = new ArrayList();
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

    public ArrayList<IMerce> setupIngredienti(String path, String key) throws ParseException {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray merce = object.getJSONArray(key);
        return creaIngredienti(merce);
    }

    private ArrayList<IMerce> creaIngredienti(JSONArray object) throws ParseException {
        ArrayList<IMerce> merceList = new ArrayList();
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

    public ArrayList<Ricetta> setupRicetta(String path) {
        JSONObject object;
        object = readFromJSON(path);
        JSONArray ricette = object.getJSONArray("ricette");
        return creaRicette(ricette);
    }

    private ArrayList<Ricetta> creaRicette(JSONArray object) {
        ArrayList<Ricetta> ricetteList = new ArrayList();
        for (int i = 0; i < object.length(); i++) {
            JSONObject ricetta = (JSONObject) object.get(i);
            String nome = ricetta.getString("nome");
            int porzione = ricetta.getInt("porzione");
            double caricoLavoroPorzione = ricetta.getDouble("caricoLavoroPorzione");
            JSONArray ingredientiJSON = ricetta.getJSONArray("ingredienti");
            Map<String, Integer> ingredientiList = ju.JSONArrayToStrIntMap(ingredientiJSON);
            ricetteList.add(new Ricetta(nome, ingredientiList, porzione, caricoLavoroPorzione));
        }

        return ricetteList;
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
}