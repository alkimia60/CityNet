package com.openfactorybeans.citynet.desktop.forms;

import com.openfactorybeans.citynet.desktop.management.IncidentsManagement;
import com.openfactorybeans.citynet.desktop.model.Container;
import com.openfactorybeans.citynet.desktop.model.Incident;
import com.openfactorybeans.citynet.desktop.utils.ContainerMap;
import com.openfactorybeans.citynet.desktop.utils.JsonUtils;
import com.openfactorybeans.citynet.desktop.utils.TMContainer;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Jose
 */
public class IncidentAdd extends javax.swing.JInternalFrame {

    //Variables de la taula
    private Container container;
    private TMContainer modelTable;
    private int selectedRow;
    
    //Variables per la connexió amb el servidor
    private String serverResponse;
    private String serverMessageOK;
    private String serverMessageError;
    
    //Variables per la incidència
    private IncidentsManagement incidentManagement;
    private Incident incident;
    
    //Variables per definir el mapa
    private String zoom, mapType;
    
    /**
     * Creates new form IncidentAdd
     */
    public IncidentAdd(Container container, TMContainer modelTable, int selectedRow) {
        initComponents();
        
        this.container = container;
        this.modelTable = modelTable;
        this.selectedRow = selectedRow;
        
        //Passem el id del contenidor a la seva etiqueta
        lblContainerId.setText(container.getId());
        
        //Amaguem el panel dels modificadors del mapa. Només es veuran quan es cliqui el mapa
        jPanelMapButtons.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jPanelContainerID = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        lblContainerId = new javax.swing.JLabel();
        jPanelIndidentType = new javax.swing.JPanel();
        lblIncidentType = new javax.swing.JLabel();
        cbxIncidentType = new javax.swing.JComboBox<>();
        lblMessagesError = new javax.swing.JLabel();
        jPanelButtons = new javax.swing.JPanel();
        btnAddIncident = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnMap = new javax.swing.JButton();
        jPanelMap = new javax.swing.JPanel();
        lblMap = new javax.swing.JLabel();
        jPanelMapButtons = new javax.swing.JPanel();
        chboxSatelite = new javax.swing.JCheckBox();
        btnZoomMore = new javax.swing.JButton();
        btbZoomLess = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Notificar incidència");

        lblId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblId.setText("Identificador:");

        lblContainerId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblContainerId.setForeground(new java.awt.Color(102, 0, 0));
        lblContainerId.setPreferredSize(new java.awt.Dimension(50, 19));

        javax.swing.GroupLayout jPanelContainerIDLayout = new javax.swing.GroupLayout(jPanelContainerID);
        jPanelContainerID.setLayout(jPanelContainerIDLayout);
        jPanelContainerIDLayout.setHorizontalGroup(
            jPanelContainerIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerIDLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(lblId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblContainerId, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelContainerIDLayout.setVerticalGroup(
            jPanelContainerIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContainerIDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContainerIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(lblContainerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblIncidentType.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblIncidentType.setText("Tipus de incidència:");

        cbxIncidentType.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbxIncidentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona tipus", "Ple", "Trencat" }));
        cbxIncidentType.setPreferredSize(new java.awt.Dimension(150, 29));

        javax.swing.GroupLayout jPanelIndidentTypeLayout = new javax.swing.GroupLayout(jPanelIndidentType);
        jPanelIndidentType.setLayout(jPanelIndidentTypeLayout);
        jPanelIndidentTypeLayout.setHorizontalGroup(
            jPanelIndidentTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIndidentTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIncidentType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxIncidentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelIndidentTypeLayout.setVerticalGroup(
            jPanelIndidentTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelIndidentTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelIndidentTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIncidentType)
                    .addComponent(cbxIncidentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblMessagesError.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblMessagesError.setForeground(new java.awt.Color(102, 0, 0));
        lblMessagesError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMessagesError.setPreferredSize(new java.awt.Dimension(0, 27));

        btnAddIncident.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddIncident.setText("Notificar");
        btnAddIncident.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddIncidentActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCancel.setText("Cancel·lar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnMap.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnMap.setText("Mapa");
        btnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelButtonsLayout = new javax.swing.GroupLayout(jPanelButtons);
        jPanelButtons.setLayout(jPanelButtonsLayout);
        jPanelButtonsLayout.setHorizontalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddIncident, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMap)
                .addContainerGap())
        );
        jPanelButtonsLayout.setVerticalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddIncident)
                    .addComponent(btnCancel)
                    .addComponent(btnMap))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelMapLayout = new javax.swing.GroupLayout(jPanelMap);
        jPanelMap.setLayout(jPanelMapLayout);
        jPanelMapLayout.setHorizontalGroup(
            jPanelMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMap, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMapLayout.setVerticalGroup(
            jPanelMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMap, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chboxSatelite.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        chboxSatelite.setText("Satèl·lit");
        chboxSatelite.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chboxSateliteStateChanged(evt);
            }
        });

        btnZoomMore.setText("+");
        btnZoomMore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomMoreActionPerformed(evt);
            }
        });

        btbZoomLess.setText("-");
        btbZoomLess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btbZoomLessActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMapButtonsLayout = new javax.swing.GroupLayout(jPanelMapButtons);
        jPanelMapButtons.setLayout(jPanelMapButtonsLayout);
        jPanelMapButtonsLayout.setHorizontalGroup(
            jPanelMapButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMapButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chboxSatelite)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnZoomMore)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btbZoomLess)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMapButtonsLayout.setVerticalGroup(
            jPanelMapButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMapButtonsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelMapButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chboxSatelite)
                    .addComponent(btnZoomMore)
                    .addComponent(btbZoomLess)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanelIndidentType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelContainerID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblMessagesError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelMapButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanelMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 25, Short.MAX_VALUE))
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelContainerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelIndidentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanelMapButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMessagesError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
        //Tanquem el formulari
        this.dispose();
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddIncidentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddIncidentActionPerformed
        
        //Netejem l'etiqueta de missatges
        lblMessagesError.setText("");
        lblIncidentType.setForeground(Color.black);
        
        //Instanciem una nova incidència
        incident = new Incident();
        
        //Passem al objecte incident el id del contenidor
        incident.setContainer(container.getId());
        
        //Verifiquem que s'ha selecionat algun tipus de incidència
        if (cbxIncidentType.getSelectedIndex()== 0) {
            
            //Res seleccionat
            lblIncidentType.setForeground(Color.red);
            lblMessagesError.setText("Cap tipus de incidència seleccionat");
            
        } else {
            
            //S'ha seleccionat un tipus i el passem al servidor
            if (cbxIncidentType.getSelectedIndex() == 1) {
                
                //La incidência és que està ple
                incident.setType(Incident.FULL);
                
            } else {
                
                //La incidència és que està trencat
                incident.setType(Incident.BROKEN);
                
            }
            
            //Posem les variables a null per una nova comunicació amb el server
            serverMessageError = null;
            serverMessageOK = null;
            
            System.out.println("Incidència per passar: " + incident.getContainer() + " " + incident.getType());
            
            ////////////////////////////////////////////////////////////////////////////
            //Conectem amb el servidor per obtenidr les dades de la incidència
            ////////////////////////////////////////////////////////////////////////////
            incidentManagement = new IncidentsManagement();
            serverResponse = incidentManagement.incidentNotification(Login.PUBLIC_URL_INCIDENT, Login.token, incident);
            
            //Mirem el tipus de missatge que retorna el servidor
            serverMessageOK = JsonUtils.findJsonValue(serverResponse, "OK");
            serverMessageError = JsonUtils.findJsonValue(serverResponse, "error");
            
            //Hi ah missatge d'error?
            if (serverMessageError != null) {

                //Mostrem un missatge
                JOptionPane.showMessageDialog(null, serverMessageError, "CityNet - Notificar incidència", JOptionPane.ERROR_MESSAGE);
                
                //Si l'error és de sessió finalitzada...
                if (serverMessageError.equals("Not a valid token")) {
                    
                    //Tanquem l'aplicació
                    System.exit(0);
                    
                }
                
            }
            
            //Si s'ha notificat
            if (serverMessageOK != null) {
                
                //Mostrem un missatge
                JOptionPane.showMessageDialog(null, serverMessageOK, "CityNet - Notificar incidència", JOptionPane.INFORMATION_MESSAGE);
                
                //Actialitzem la taula
                modelTable.reportIncidence(selectedRow);
                
                //Tanquem el formulari
                this.dispose();
                
            }
            
        }
        
    }//GEN-LAST:event_btnAddIncidentActionPerformed

    private void btnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapActionPerformed
        
        //Iniciem els valors per defecte del mapa
        zoom = "18";
        mapType = "roadmap";
        chboxSatelite.setSelected(false);
        
        //Instanciem el mapa
        ContainerMap containerMap = new ContainerMap(container, zoom, mapType);
        
        //Obtenim la imatge
        Image imageMap = containerMap.downloadMap();
        
        //Passem la imatge a l'etiqueta
        lblMap.setIcon(new ImageIcon(imageMap));
        
        //Mostrem el panel dels butons del mapa
        jPanelMapButtons.setVisible(true);
        
    }//GEN-LAST:event_btnMapActionPerformed

    private void chboxSateliteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chboxSateliteStateChanged

        //Obtenim el valor del checkbox
        boolean chbox = chboxSatelite.isSelected();

        //Verifiquem el valor
        if (chbox) {
            mapType = "satellite";
        } else {
            mapType = "roadmap";
        }

        //Instanciem el mapa
        ContainerMap containerMap = new ContainerMap(container, zoom, mapType);

        //Obtenim la imatge
        Image imageMap = containerMap.downloadMap();

        //Passem la imatge a l'etiqueta
        lblMap.setIcon(new ImageIcon(imageMap));
    }//GEN-LAST:event_chboxSateliteStateChanged

    private void btnZoomMoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomMoreActionPerformed

        //Obtenim el zoom actual
        int zoomTemp = Integer.parseInt(zoom);

        //Incrementem en 1 el zoom i controlem que no passi del límit
        zoomTemp ++;
        if (zoomTemp >= 21) zoomTemp = 21;

        //Passem el resultat a un String
        zoom = String.valueOf(zoomTemp);

        //Instanciem el mapa
        ContainerMap containerMap = new ContainerMap(container, zoom, mapType);

        //Obtenim la imatge
        Image imageMap = containerMap.downloadMap();

        //Passem la imatge a l'etiqueta
        lblMap.setIcon(new ImageIcon(imageMap));
    }//GEN-LAST:event_btnZoomMoreActionPerformed

    private void btbZoomLessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btbZoomLessActionPerformed

        //Obtenim el zoom actual
        int zoomTemp = Integer.parseInt(zoom);

        //Decrementem en 1 el zoom i controlem que no passi del límit
        zoomTemp --;
        if (zoomTemp <= 16) zoomTemp = 16;

        //Passem el resultat a un String
        zoom = String.valueOf(zoomTemp);

        //Instanciem el mapa
        ContainerMap containerMap = new ContainerMap(container, zoom, mapType);

        //Obtenim la imatge
        Image imageMap = containerMap.downloadMap();

        //Passem la imatge a l'etiqueta
        lblMap.setIcon(new ImageIcon(imageMap));
    }//GEN-LAST:event_btbZoomLessActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btbZoomLess;
    private javax.swing.JButton btnAddIncident;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnMap;
    private javax.swing.JButton btnZoomMore;
    private javax.swing.JComboBox<String> cbxIncidentType;
    private javax.swing.JCheckBox chboxSatelite;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelContainerID;
    private javax.swing.JPanel jPanelIndidentType;
    private javax.swing.JPanel jPanelMap;
    private javax.swing.JPanel jPanelMapButtons;
    private javax.swing.JLabel lblContainerId;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblIncidentType;
    private javax.swing.JLabel lblMap;
    private javax.swing.JLabel lblMessagesError;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}
