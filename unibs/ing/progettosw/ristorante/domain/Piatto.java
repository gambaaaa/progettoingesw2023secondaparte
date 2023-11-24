package unibs.ing.progettosw.ristorante.domain;

import java.util.Date;


public class Piatto {

    /*
     * Classe che intende descrivere un piatto presente nei vari menu con relativa disponibilità nell'arco dell'anno.
     * E' strettamente correlata alla classe Ricetta del package unibs.ing.progettosw.ristorante.domain ,in quanto
     * il Piatto corrisponde ad una ricetta, quella usata per cucinarlo.
     */
    private String nome;
    private Ricetta ricetta;
    private double caricoLavoro;
    private Date inizioDisponibilita;
    private Date fineDisponibilita;


    //costruttore della classe
    //creazione di un Piatto in base a sei parametri da specificare: nome,ricetta correlata , numeroPersone ,
    //caricoLavoro,date di inizioDisponibilità e fineDisponibilità.
    // pre : ricetta != NULL
    // post : creato un Piatto correttamente
    public Piatto(String nome, Ricetta ricetta, double caricoLavoro, Date inizioDisponibilita, Date fineDisponibilita) {
        this.nome = nome;
        this.ricetta = ricetta;
        this.caricoLavoro = caricoLavoro;
        this.inizioDisponibilita = inizioDisponibilita;
        this.fineDisponibilita = fineDisponibilita;
    }

    public String getNome() {
        return nome;
    }

    // metodo che restituisce la ricetta associata-relativa usata per cucinare il Piatto
    public Ricetta getRicetta() {
        return ricetta;
    }

    public double getCaricoLavoro() {
        return caricoLavoro;
    }


    // metodo che restituisce la data di inizio disponibilità del Piatto
    public Date getInizioDisponibilita() {
        return inizioDisponibilita;
    }

    // metodo che restituisce la data di fine disponibilità del Piatto
    public Date getFineDisponibilita() {
        return fineDisponibilita;
    }

    // metodo che restituisce :
    //                          true se il nome specificato corrisponde al nome del Piatto
    //                          false se il nome specificato non corrisponde al nome del Piatto
    public boolean getPiattoFromNome(String nome) {
        return nome.equals(this.nome);
    }

    public String denominazioneValiditaPiatto() {
        return "Nome: " + this.nome + ", inizio validità: " + inizioDisponibilita + ", fine validità: " + fineDisponibilita;
    }
}
