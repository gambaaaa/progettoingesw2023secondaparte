package unibs.ing.progettosw.ristorante.domain;

import java.util.List;
import java.util.Map;

public class Ristorante {
    private static Ristorante ristorante;
    /*
     * Classe che implementa il pattern Singleton per rappresentare il fatto che esiste una sola instanza di Ristorante
     * */
    private String nome;
    private int postiSedere;
    private int caricoLavoroPersona;
    private Map<String, Ricetta> ricettario;
    private List<MenuTematico> menuTematici;
    private List<Piatto> menuCarta;
    private List<Prenotazione> prenotazioni;

    private Ristorante(String nome, int postiSedere, int caricoLavoroPersona) {
        this.nome = nome;
        this.postiSedere = postiSedere;
        this.caricoLavoroPersona = caricoLavoroPersona;
    }

    // metodo pubblico per creare un oggetto statico di tipo Ristorante
    // pre : nome not empty
    //       postiSedere > 0
    //       caricoLavoroPersona > 0
    // post : unica istanza di Ristorante (singleton)
    public static Ristorante getInstance(String nome, int postiSedere, int caricoLavoroPersona) {
        if (ristorante == null) {
            ristorante = new Ristorante(nome, postiSedere, caricoLavoroPersona);
        }
        return ristorante;
    }

    public static Ristorante getInstance() {
        return ristorante;
    }

    public String getNome() {
        return nome;
    }

    public int getPostiSedere() {
        return postiSedere;
    }

    public int getCaricoLavoroPersona() {
        return caricoLavoroPersona;
    }

    // Ammonta al prodotto del carico di lavoro per persona per il numero complessivo di posti a sedere del ristorante
    // accresciuto del 20%
    // post : caricoLavoroSostenibile > 0, caricoLavoroSostenibile = postiSedere * 1.2 * caricoLavoroPersona
    public int getCaricoLavoroSostenibile() {
        return (int) Math.round((postiSedere * 1.2) * caricoLavoroPersona);
    }

    public String getInfoRistorante() {
        return "Carico di lavoro per persona: " + getCaricoLavoroPersona() + "\nNumero di posti a sedere disponibili: " +
                getPostiSedere() + "\n";
    }
}
