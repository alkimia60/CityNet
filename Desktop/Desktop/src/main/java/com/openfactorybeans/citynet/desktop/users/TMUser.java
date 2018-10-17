/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfactorybeans.citynet.desktop.users;

import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Jose
 */
public class TMUser implements TableModel {
    
    private List<User> users;
    
    public TMUser(List<User> list) {
        
        users = list;
        
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public String getColumnName(int columnIndex) {
        
        String columName = null;
        
        switch(columnIndex) {
            case 0:
                columName = "Nom";
                break;
                
            case 1:
                columName = "Cognoms";
                break;
                
            case 2:
                columName = "Adreça";
                break;
                
            case 3:
                columName = "CP";
                break;
                
            case 4:
                columName = "Població";
                break;
                
            case 5:
                columName = "Email";
                break;
                
            case 6:
                columName = "Rol";
                break;
        }
        
        return columName;
        
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //En aquest cas totes les columnes són String
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //Retornem que sigui editable totes les columnes menya la primera
        //return columnIndex != 0;
        
        return false; //Cap columna editable
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //Localitzem la fila del objecte que es vol el valor
        User u = users.get(rowIndex);
        
        //A través d'un switch, localitzem la columna
        String columNameValue = null;
        
        switch(columnIndex) {
            case 0:
                columNameValue = u.getName();
                break;
                
            case 1:
                columNameValue = u.getSurname();
                break;
                
            case 2:
                columNameValue = u.getAddress();
                break;
                
            case 3:
                columNameValue = u.getPostcode();
                break;
                
            case 4:
                columNameValue = u.getCity();
                break;
                
            case 5:
                columNameValue = u.getEmail();
                break;
                
            case 6:
                columNameValue = u.getUserLevel();
                break;
        }
        
        return columNameValue;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Localitzem la fila del objecte que es vol canviar el valor
        User u = users.get(rowIndex);
        
        //A través d'un switch, localitzem la columna
        
        switch(columnIndex) {
            case 0:
                u.setName(aValue.toString());
                break;
                
            case 1:
                u.setSurname(aValue.toString());
                break;
                
            case 2:
                u.setAddress(aValue.toString());
                break;
                
            case 3:
                u.setPostcode(aValue.toString());
                break;
                
            case 4:
                u.setCity(aValue.toString());
                break;
                
            case 5:
                u.setEmail(aValue.toString());
                break;
                
            case 6:
                u.setUserLevel(aValue.toString());
                break;
        }
        
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }
    
}
