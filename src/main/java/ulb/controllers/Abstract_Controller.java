package ulb.controllers;

import ulb.models.Modele;
// ALL controller heritate from this class
public abstract class Abstract_Controller {
    Modele modele;

    public void Set_modele(Modele modele){
        this.modele = modele;
    }
}
