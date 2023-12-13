package unibs.ing.progettosw.utilities;

import unibs.ing.progettosw.ristorante.domain.IMerce;
import unibs.ing.progettosw.ristorante.domain.Menu;
import unibs.ing.progettosw.ristorante.domain.Piatto;
import unibs.ing.progettosw.ristorante.domain.Ricetta;

import java.util.List;

public class StringToClassGetter {
    /*
     * Classe di utilità. Contiene metodi utili per ottenere oggetti a partire dal nome di quest'ultimo.
     * */

    // dato in input il nome di una ricetta cicla sulla lista di ricette e trova la ricetta corrispondente
    // pre : ricette not null
    //       nomeRicetta == ad almeno una ricetta esistente
    // post : aRicetta.getNome() == nomeRicetta, aRicetta not null
    public Ricetta getRicettafromNome(List<Ricetta> ricette, String nomeRicetta) {
        for (Ricetta aRicetta : ricette) {
            if (aRicetta.getRicettaFromNome(nomeRicetta))
                return aRicetta;
        }
        // caso eccezionale, non dovrebbe accadere
        return null;
    }

    // dato in input il nome di un piatto cicla sulla lista di piatti e trova il piatto corrispondente
    // pre : piatti not null
    //       nomePiatti == ad almeno un piatto esistente
    // post : aPiatto.getNome() == nomePiatti, aPiatto not null
    public Piatto getPiattofromNome(List<Piatto> piatti, String nomePiatti) {
        for (Piatto aPiatto : piatti) {
            if (aPiatto.getPiattoFromNome(nomePiatti))
                return aPiatto;
        }
        return null;
    }

    // dato in input il nome di un menu tematici cicla sulla lista di menu tematici e trova il menu corrispondente
    // pre : menuTematici not null
    //       nomeMenuTematico == ad almeno un menu esistente
    // post : aMenuT.getNome() == nomeMenuTematico, aMenuT not null
    public Menu getMenuTematicofromNome(List<Menu> menuTematici, String nomeMenuTematico) {
        for (Menu aMenuT : menuTematici) {
            if (aMenuT.getMenuFromNome(nomeMenuTematico))
                return aMenuT;
        }
        // caso eccezionale, non dovrebbe accadere
        return null;
    }

    // dato in input il nome di un ingrediente cicla sulla lista di ingredienti e trova l'ingrediente corrispondente
    // pre : ingredienti not null
    //       nomeIngrediente == ad almeno un ingrediente
    // post : aIngr.getNome() == nomeIngrediente, aIngr not null
    public IMerce getIngredienteFromNome(List<IMerce> ingredienti, String nomeIngrediente) {
        for (IMerce aIngr : ingredienti) {
            if (aIngr.getIngredienteFromNome(nomeIngrediente))
                return aIngr;
        }
        // caso eccezionale, non dovrebbe accadere
        return null;
    }
}
