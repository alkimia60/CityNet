package com.openfactorybeans.citynet.desktop.errors;

/**
 * Classe de suport pels errors del formulari de dades
 * 
 * @author Jose
 */
public class ErrorForm {
    
    //Declaració de variables
    private boolean required, notSame;

    public ErrorForm() {
        
        required = false;
        notSame = false;
        
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isNotSame() {
        return notSame;
    }

    public void setNotSame(boolean notSame) {
        this.notSame = notSame;
    }

}
