package com.openfactorybeans.citynet.desktop.utils;

import com.openfactorybeans.citynet.desktop.model.Container;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Codificació per definir la taula de contenidors (Table Model)
 *
 * @author Jose
 */
public class TMContainer implements TableModel{
    
    private List<Container> containers;
    /*private Class[] typesClass = new Class[] {
      java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
    };*/
    
    //Subscriptors
    private LinkedList subscribers  = new LinkedList();
    
    public TMContainer(List<Container> containers) {
        
        this.containers = containers;
        
    }

    @Override
    public int getRowCount() {
        return containers.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        
        String columName = null;
        
        switch (columnIndex) {
            case 0:
                columName = "Identificador";
                break;
                
            case 1:
                columName = "Tipus";
                break;
                
            case 2:
                columName = "Latitud";
                break;
                
            case 3:
                columName = "Longitud";
                break;
                
            case 4:
                columName ="Operatiu";
                break;
        }
        
        return columName;
        
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        
        //return typesClass[columnIndex];
        return String.class;
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        
        return false; //Cap columna editable
        
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //Localitzem la fila del objecte que volem obtenir el valor
        Container c = containers.get(rowIndex);
        
        //A través d'un switch, localitzem la columna
        String columNameValue = null;
        
        switch (columnIndex) {
            
            case 0:
                columNameValue = c.getId();
                break;
                
            case 1:
                if (c.getType().equals(Container.TYPES_SERVER[0])) {
                    columNameValue = Container.TYPES_TABLE[0];
                }
                if (c.getType().equals(Container.TYPES_SERVER[1])) {
                    columNameValue = Container.TYPES_TABLE[1];
                }
                if (c.getType().equals(Container.TYPES_SERVER[2])) {
                    columNameValue = Container.TYPES_TABLE[2];
                }
                if (c.getType().equals(Container.TYPES_SERVER[3])) {
                    columNameValue = Container.TYPES_TABLE[3];
                }
                if (c.getType().equals(Container.TYPES_SERVER[4])) {
                    columNameValue = Container.TYPES_TABLE[4];
                }
                break;
                
            case 2:
                columNameValue = c.getLatitude().toString();
                break;
                
            case 3:
                columNameValue = c.getLongitude().toString();
                break;
                
            case 4:
                if (String.valueOf(c.getOperative()).equals("false")) {
                    columNameValue = Container.OPERATIVE_TABLE[0];
                } else {
                    columNameValue = Container.OPERATIVE_TABLE[1];
                }
                
                
        }
        
        return columNameValue;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //Localitzem la fila del objecte que es vol canviar el valor
        Container c = new Container();
        
        //A travès d'un switch, localitzem la columna
        
        switch (columnIndex) {
            case 0:
                c.setId(aValue.toString());
                break;
                
            case 1:
                c.setType(aValue.toString());
                break;
                
            case 2:
                c.setLatitude(Double.parseDouble(aValue.toString()));
                break;
                
            case 3:
                c.setLongitude(Double.parseDouble(aValue.toString()));
                
            case 4:
                c.setOperative(Boolean.parseBoolean(aValue.toString()));
                
        }
        
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
        subscribers.add(l);
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
        subscribers.remove(l);
        
    }
    
    public void removeRow(int rowIndex) {
        
        //Eliminem la fila
        containers.remove(rowIndex);
        
        //Creem un TableModelEvent
        TableModelEvent TMEvent = new TableModelEvent(this, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        
        //I passem l'event als subscriptors
        for (int i = 0; i < subscribers.size(); i++) {
            ((TableModelListener) subscribers.get(i)).tableChanged(TMEvent);
        }
        
    }

}
