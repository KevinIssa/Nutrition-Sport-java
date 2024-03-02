package ulb.controllers;

import ulb.models.Modele;
// ALL controller heritate from this class
public abstract class AbstractController {
    Modele modele;

    public void setModele(Modele modele){
        this.modele = modele;
    }
    public void giveData(Object data){
        if (data != null){
            // do something with override in children class
        }
    }
}
