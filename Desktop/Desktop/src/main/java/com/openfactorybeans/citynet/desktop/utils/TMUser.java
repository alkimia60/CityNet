package com.openfactorybeans.citynet.desktop.utils;

import com.openfactorybeans.citynet.desktop.model.User;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Codificació per definir la taula d'usuaris (Table Model)
 * 
 * @author Jose
 */
public class TMUser implements TableModel {
    
    private List<User> users;
    
    //Subscriptors
    private LinkedList subscribers  = new LinkedList();
    
    public TMUser(List<User> users) {
        
        this.users = users;
        
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public String getColumnName(int columnIndex) {
        
        String columName = null;
        
        switch(columnIndex) {
            case 0:
                columName = "Email";
                break;
                
            case 1:
                columName = "Nom";
                break;
                
            case 2:
                columName = "Cognoms";
                break;
                
            case 3:
                columName = "Adreça";
                break;
                
            case 4:
                columName = "Codi Postal";
                break;
                
            case 5:
                columName = "Població";
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
        //Localitzem la fila del objecte que volem obtenir el valor
        User u = users.get(rowIndex);
        
        //A través d'un switch, localitzem la columna
        String columNameValue = null;
        
        switch(columnIndex) {
            case 0:
                columNameValue = u.getEmail();
                break;
                
            case 1:
                columNameValue = u.getName();
                break;
                
            case 2:
                columNameValue = u.getSurname();
                break;
                
            case 3:
                columNameValue = u.getAddress();
                break;
                
            case 4:
                columNameValue = u.getPostcode();
                break;
                
            case 5:
                columNameValue = u.getCity();
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
                u.setEmail(aValue.toString());
                break;
                
            case 1:
                u.setName(aValue.toString());
                break;
                
            case 2:
                u.setSurname(aValue.toString());
                break;
                
            case 3:
                u.setAddress(aValue.toString());
                break;
                
            case 4:
                u.setPostcode(aValue.toString());
                break;
                
            case 5:
                u.setCity(aValue.toString());
                break;
                
            case 6:
                u.setUserLevel(aValue.toString());
                break;
        }
        
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
        subscribers.add(l);
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
        subscribers.add(l);
        subscribers.remove(1);
        
    }
    
    public void removeRow(int rowIndex) {
        
        //Eliminem la fila
        users.remove(rowIndex);
        
        //Creem un TableModelEvent
        TableModelEvent TMEvent = new TableModelEvent(this, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        
        //I passem l'event als subscriptors
        for (int i = 0; i < subscribers.size(); i++) {
            ((TableModelListener) subscribers.get(i)).tableChanged(TMEvent);
        }
        
    }
    
}
