package unibs.ing.progettosw.ristorante.domain;

import java.util.Date;
import java.util.Map;

/*
 * La classe  intende rappresentare un’entità software che descrive quanto cumulativamente prenotato da un singolo cliente, per

 */
public class Prenotazione {

    // Classe che rappresenta una Prenotazione effettuata da un cliente per conto di un insieme, di cardinalità nota,
    // di persone che egli rappresenta(numero coperti) in una certa data.

    //Attributi privati
    private Date data;//data prenotazione : data "prenotata" - data per cui si effettua la prenotazione

    private Date dataPrenotazione; // data in cui è stata effettuata la prenotazione
    private int numeroCoperti;
    private Map<String, Integer> menuTematicoPrenotato;
    private Map<String, Integer> piattoPrenotato;

    /*
     * costruttore
     * creazione di una Prenotazione in base a quattro parametri da specicare: data di prenotazione,numeroCoperti , menuTematicoPrenotato ,
     * piattoPrenotato.
     *
     * pre : data != NULL && dataPrenotazione != NULL && menuTematicoPrenotato != NULL && piattoPrenotato != NULL
     * post : creata correttamente una Prenotazione
     */
    public Prenotazione(Date data, Date dataPrenotazione, int numeroCoperti, Map<String, Integer> menuTematicoPrenotato, Map<String, Integer> piattoPrenotato) {
        this.data = data;
        this.dataPrenotazione = dataPrenotazione;
        this.numeroCoperti = numeroCoperti;
        this.menuTematicoPrenotato = menuTematicoPrenotato;
        this.piattoPrenotato = piattoPrenotato;//non è Map<Piatto,Integer>?
    }

    public Date getData() {
        return data;
    }

    public Date getDataPrenotazione() {
        return dataPrenotazione;
    }

    public int getNumeroCoperti() {
        return numeroCoperti;
    }

    public Map<String, Integer> getMenuTematicoPrenotato() {
        return menuTematicoPrenotato;
    }

    public Map<String, Integer> getPiattoPrenotato() {
        return piattoPrenotato;
    }
}
