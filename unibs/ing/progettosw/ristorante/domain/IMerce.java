package unibs.ing.progettosw.ristorante.domain;

public interface IMerce {

    /*
    * Interfaccia che contiene metodi per gestire opportunamente un tipo di IMerce.
    * */

    // metodo che restituisce true o false sulla base del nome specificato(parametro di ingresso al metodo).
    // in particolare serve per effettuare un check sull'esistenza/sulla presenza dell'ingrediente(tipo di IMerce) specificato
    // attraverso il nome.
    // pre : il parametro in ingresso nome deve essere di tipo String
    // post : valore boolean --> true se l'ingrediente specificato esiste/è presente , "è stato trovato"
    //                       --> false se l'ingrediente specificato non esiste/non è presente , "non è stato trovato"
    public boolean getIngredienteFromNome(String nome);

    // metodo che permette di aggiungere/incrementare la quantità della IMerce.
    // pre :  quantità(intero) > 0
    // post : la quantità della merce deve essere incrementata di quantità.
    void aggiungiQuantita(int quantita);

    // metodo che permette di rimuovere/decrementare la quantità della IMerce.
    // pre :  quantità(intero) > 0
    // post : la quantità della merce deve essere decrementata di quantità.
    void rimuoviQuantita(int quantita);

    // metodo che restituisce il nome della IMerce - "merce"
    String getNome();

    // metodo che restituisce la quantità di IMerce - "merce" a disposizione.
    // pre : -
    // post : un valore intero(int) >= 0
    public int getQuantita();

    // metodo che restituisce il valore del consumoProCapite  della IMerce - "merce".
    // pre : -
    // post : un valore intero(int) >= 0
    public int getConsumoProCapite();
}
