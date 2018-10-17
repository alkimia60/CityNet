/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfactorybeans.citynet.desktop.errors;

/**
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
