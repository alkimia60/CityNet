package com.openfactorybeans.citynet.desktop.forms;

import com.openfactorybeans.citynet.desktop.model.Container;
import com.openfactorybeans.citynet.desktop.management.ContainersManagement;
import com.openfactorybeans.citynet.desktop.utils.FormsUtils;
import com.openfactorybeans.citynet.desktop.utils.TMContainer;
import com.openfactorybeans.citynet.desktop.utils.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;

/**
 *
 * @author Jose
 */
public class ContainersList extends javax.swing.JInternalFrame {

    //Variables finals dels noms de les columnes de la taula
    private final int ID = 0;
    private final int TYPE = 1;
    private final int LATITUDE = 2;
    private final int LONGITUDE = 3;
    private final int OPERATIVE = 4;
    
    //Variables finals del tipus de filtre per passar al servidor
    private final String ALL = "all";
    private final String TYPE_FIELD = "type";
    private final String OPERATIVE_FIELD = "operative";
    private String type = "ALL"; //Per defecte tots
    private int operative = -1; //Per defecte tots
    
    //Declaració de variables pel llistat
    private ContainersManagement listContainers;
    private List<Container> containers;
    private TMContainer modelTable;
    private String serverResponse;
    private String serverMessageOK;
    private String serverMessageError;
    
    //Declaració dels components que obren finestres
    private IncidentDetail incidentDetail;
    private ContainerDelete containerDelete;
    private IncidentAdd incidentAdd;
    private ContainerLocModify containerLocModify;
    
    //Variable que informa de la pàgina solicitada al servidor
    private int screen = 0;
    
    //Variable de finalització del llistat
    private boolean endList;
    
    //Variable pel filtre. Per defecte tots
    private String filterField = ALL;
    private String filterValue = ALL;
    
    /**
     * Creates new form ContainersList
     */
    public ContainersList() {
        initComponents();
        
        //Amaguem el botons que només s'activen si es selecciona un contenidor de la taula
        btnModify.setEnabled(false);
        btnDelete.setEnabled(false);
        btnIncidentAdd.setEnabled(false);
        btnIncidenceDetail.setEnabled(false);
        
        // Cridem al servidor
        callToServer();
        
    }
    
    /**
     * Mètode per fer les crides al servidor
     */
    public void callToServer() {
        
        //Posem les variables de null per una nova comunicació amb el server
        serverMessageError = null;
        serverMessageOK = null;
        
        ////////////////////////////////////////////////////////////////////////////
        //Conectem amb el servidor per obtenir la pàgina dels contenidors.
        ////////////////////////////////////////////////////////////////////////////
        listContainers = new ContainersManagement();
        //serverResponse = listContainers.listAllContainers(Login.PUBLIC_URL_CONTAINER, Login.token, screen, filterField, filterValue);
        serverResponse = listContainers.listFilteredContainers(Login.PUBLIC_URL_CONTAINER, Login.token, screen, type, operative);
        
        //Mirem el tipus de missatge que retorna el servidor
        serverMessageOK = JsonUtils.findJsonValue(serverResponse, "users");
        serverMessageError = JsonUtils.findJsonValue(serverResponse, "error");
        
        //Mirem el tipus de missatge que retorna el servidor
        serverMessageOK = JsonUtils.findJsonValue(serverResponse, "containers");
        serverMessageError = JsonUtils.findJsonValue(serverResponse, "error");
        
        System.out.println("Missatge OK: " + serverMessageOK);
        System.out.println("Missatge ERROR: " + serverMessageError);
        System.out.println("Camp de filtrat: " + filterField);
        System.out.println("Valor de filtrat: " + filterValue);
        System.out.println("Valor de type: " + type);
        System.out.println("Valor de operative: " + operative);
        System.out.println("");
        
        //Hi ha messatge OK?
        if (serverMessageOK != null) {
            
            //Passem el json a ArrayList
            containers = new ArrayList<>();
            containers = JsonUtils.parseJsonContainers(serverResponse);
            
            //Comprovem les dades control
            endList = JsonUtils.parseJsonControl(serverResponse);
            
            //Cridem al mètode per canviar l'estat dels botons
            buttonsStates();
            
            //Omplim la taula amb l'Array
            fillTable();
            
        }
        
        if (serverMessageError != null) {
            
            //No estem identificats
            JOptionPane.showMessageDialog(null, serverMessageError, "CityNet - Gestió de contenidors", JOptionPane.ERROR_MESSAGE);
            
            //Tanquem formulari
            //this.dispose();
            
            //Login login = new Login();
            //this.setVisible(false);
            //this.dispose();
            //login.setVisible(true);
            
        }
        
    }
    
    /**
     * Mètode per omplir la taula amb els contenidors a partir d'un Array
     */
    public void fillTable() {

        modelTable = new TMContainer(containers);
        jTableContainers.setModel(modelTable);
        
        //jTableContainers.getColumnModel().getColumn(0).setPreferredWidth(20);
        //jTableContainers.getColumnModel().getColumn(4).setPreferredWidth(10);
        
        
    }
    
    /**
     * Mètode per canviar l'estat dels botons d'avançar i enrederir
     * segons les dades rebudes pel servidor
     */
    public void buttonsStates() {
        
        //Habilitem o deshabiitem el botó per enrederir
        if (screen == 0) {
            
            btnRePag.setEnabled(false);
            
        } else {
            
            btnRePag.setEnabled(true);
            
        }
        
        //Habilitem o deshabiitem el botó per avanç
        if (endList) {
            
            btnAvPag.setEnabled(false);
            
        } else {
            
            btnAvPag.setEnabled(true);
            
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableContainers = new javax.swing.JTable();
        jPanelOptions = new javax.swing.JPanel();
        btnRePag = new javax.swing.JButton();
        btnAvPag = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnDelete = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        lblType = new javax.swing.JLabel();
        cbxContainerType = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cbxContainerOperative = new javax.swing.JComboBox<>();
        jSeparator4 = new javax.swing.JSeparator();
        btnIncidentAdd = new javax.swing.JButton();
        btnIncidenceDetail = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        jPanelDetail = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();

        setTitle("Llistat de contenidors");

        jTableContainers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Identificador", "Tipus", "Latitud", "Longitud", "Operatiu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableContainers.setPreferredSize(new java.awt.Dimension(525, 160));
        jTableContainers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableContainersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableContainers);
        if (jTableContainers.getColumnModel().getColumnCount() > 0) {
            jTableContainers.getColumnModel().getColumn(0).setResizable(false);
            jTableContainers.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTableContainers.getColumnModel().getColumn(4).setResizable(false);
            jTableContainers.getColumnModel().getColumn(4).setPreferredWidth(4);
        }

        btnRePag.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRePag.setText("<<");
        btnRePag.setPreferredSize(new java.awt.Dimension(90, 35));
        btnRePag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRePagActionPerformed(evt);
            }
        });

        btnAvPag.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAvPag.setText(">>");
        btnAvPag.setPreferredSize(new java.awt.Dimension(90, 35));
        btnAvPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvPagActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDelete.setText("Eliminar");
        btnDelete.setPreferredSize(new java.awt.Dimension(96, 35));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblType.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblType.setText("Tipus:");

        cbxContainerType.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbxContainerType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tots", "Paper i cartró", "Vidre", "Envasos lleugers", "Matèria orgànica", "Rebuig" }));
        cbxContainerType.setPreferredSize(new java.awt.Dimension(155, 29));
        cbxContainerType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxContainerTypeActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Operatiu:");

        cbxContainerOperative.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbxContainerOperative.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tots", "No", "Si" }));
        cbxContainerOperative.setPreferredSize(new java.awt.Dimension(70, 29));
        cbxContainerOperative.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxContainerOperativeActionPerformed(evt);
            }
        });

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnIncidentAdd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnIncidentAdd.setText("Nova incidència");
        btnIncidentAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncidentAddActionPerformed(evt);
            }
        });

        btnIncidenceDetail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnIncidenceDetail.setText("Veure incidència");
        btnIncidenceDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncidenceDetailActionPerformed(evt);
            }
        });

        btnModify.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnModify.setText("Modificar");
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelOptionsLayout);
        jPanelOptionsLayout.setHorizontalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRePag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAvPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModify)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxContainerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxContainerOperative, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIncidentAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIncidenceDetail)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelOptionsLayout.setVerticalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbxContainerType, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIncidenceDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIncidentAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnRePag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAvPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createSequentialGroup()
                                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbxContainerOperative, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))
                    .addComponent(btnModify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanelDetailLayout = new javax.swing.GroupLayout(jPanelDetail);
        jPanelDetail.setLayout(jPanelDetailLayout);
        jPanelDetailLayout.setHorizontalGroup(
            jPanelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelDetailLayout.setVerticalGroup(
            jPanelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jSeparator1)
            .addComponent(jPanelDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRePagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRePagActionPerformed
        
        //Disminuim el número de pàgina per solicitar al servidor
        screen--;
        
        //Cridem al servidor
        callToServer();
        
        //Desactivem els botons
        btnModify.setEnabled(false);
        btnDelete.setEnabled(false);
        btnIncidentAdd.setEnabled(false);
        btnIncidenceDetail.setEnabled(false);
        
    }//GEN-LAST:event_btnRePagActionPerformed

    private void btnAvPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvPagActionPerformed
        
        //Aumentem el número de pàgina per solicitar al servidor
        screen++;
        
        //Cridem al servidor
        callToServer();
        
        //Desactivem els botons
        btnModify.setEnabled(false);
        btnDelete.setEnabled(false);
        btnIncidentAdd.setEnabled(false);
        btnIncidenceDetail.setEnabled(false);
        
    }//GEN-LAST:event_btnAvPagActionPerformed

    private void cbxContainerTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxContainerTypeActionPerformed
        
        //Posem la primera pàgina
        screen = 0;
        
        //El tipus a passar al servidor és type
        //filterField = TYPE_FIELD;
        
        //Obtenim el valor de l'element seleccionat
        int filterId = cbxContainerType.getSelectedIndex();
        
        //Convertim a String el filtre seleccionat per passar al servidor
        switch (filterId) {
            
            case 0:
                type = ALL;
                break;
                
            case 1:
                type = Container.TYPES_SERVER[filterId - 1]; //paper
                break;
                
            case 2:
                type = Container.TYPES_SERVER[filterId - 1]; //glass
                break;
                
            case 3:
                type = Container.TYPES_SERVER[filterId - 1]; //packaging
                break;
                
            case 4:
                type = Container.TYPES_SERVER[filterId - 1]; //organic
                break;
                
            case 5:
                type = Container.TYPES_SERVER[filterId - 1]; //trash
                break;

        }
        
        callToServer();
        
    }//GEN-LAST:event_cbxContainerTypeActionPerformed

    private void cbxContainerOperativeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxContainerOperativeActionPerformed
        
        //Posem la primera pàgina
        screen = 0;
        
        //El tipus a passar al servidor és operative
        //filterField = OPERATIVE_FIELD;
        
        //Obtenim el valor de l'element seleccionat
        int filterId = cbxContainerOperative.getSelectedIndex();
        
        //Convertim a String el filtre seleccionat per passar al servidor
        switch (filterId) {
            
            case 0:
                //filterField = ALL;
                //filterValue = ALL;
                operative = -1;
                break;
                
            case 1:
                //filterValue = Container.OPERATIVE_SERVER[filterId - 1]; //no o false
                operative = 0;
                break;
                
            case 2:
                //filterValue = Container.OPERATIVE_SERVER[filterId - 1]; //si o true
                operative = 1;
                break;

        }
        
        callToServer();
    }//GEN-LAST:event_cbxContainerOperativeActionPerformed

    private void jTableContainersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableContainersMouseClicked
        
        //Obtenim la fila seleccionada
        int selectedRow = jTableContainers.getSelectedRow();
        
        if (selectedRow != -1) {
            
            //Netejem qualsevol finestra oberta anteriorment en l'escriptori
            jPanelDetail.removeAll();
            jPanelDetail.repaint();
            
            //Obtenim el valor del camp operatiu
            String operative = modelTable.getValueAt(selectedRow, OPERATIVE).toString();
            
            //Activem o desactivem el botons segons l'estat operatiu
            if (operative.equals(Container.OPERATIVE_TABLE[0])) { //No està operatiu
                //Si no està operatiu --> Té incidència
                //Modificar SI
                //Eliminar NO
                //Afegir NO
                //Veure SI
                btnModify.setEnabled(true);
                btnDelete.setEnabled(false);
                btnIncidentAdd.setEnabled(false);
                btnIncidenceDetail.setEnabled(true);
            } else {
                //Si està operatiu --> No té incidència
                //Modificar SI
                //Eliminar SI
                //Afegir SI
                //Veure NO
                btnModify.setEnabled(true);
                btnDelete.setEnabled(true);
                btnIncidentAdd.setEnabled(true);
                btnIncidenceDetail.setEnabled(false);
            }
            
        }
        
    }//GEN-LAST:event_jTableContainersMouseClicked

    private void btnIncidenceDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncidenceDetailActionPerformed
        
        //Obtenim la fila seleccionada
        int selectedRow = jTableContainers.getSelectedRow();
        
        if (selectedRow != -1) {
            
            //Netejem qualsevol finestra oberta anteriorment en l'escriptori
            jPanelDetail.removeAll();
            jPanelDetail.repaint();
            
            //Obtenim el valors per un contenidor
            String containerID = modelTable.getValueAt(selectedRow, ID).toString();
            String containerType = modelTable.getValueAt(selectedRow, TYPE).toString();
            String latitude = modelTable.getValueAt(selectedRow, LATITUDE).toString();
            String longitude = modelTable.getValueAt(selectedRow, LONGITUDE).toString();
            
            //Instanciem el contenidor
            Container container = new Container();
            container.setId(containerID);
            container.setType(containerType);
            container.setLatitude(Double.parseDouble(latitude));
            container.setLongitude(Double.parseDouble(longitude));
            
            //Instanciem el InternalFrame per mostrar incidències
            incidentDetail = new IncidentDetail(container, modelTable, selectedRow);
            
            //Afegim el formulari al panel de detall de la incidència
            jPanelDetail.add(incidentDetail);
            
            //Centrem la finestra
            FormsUtils.centerJInternalFrame(jPanelDetail, incidentDetail);
            
            //El fem visible
            incidentDetail.setVisible(true);
            
        }
    }//GEN-LAST:event_btnIncidenceDetailActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
        //Obtenir la fila del contenidor
        int selectedRow = jTableContainers.getSelectedRow();
        
        if (selectedRow != -1) {
            
            //Netejem qualsevol finestra oberta anteriorment en l'escriptori
            jPanelDetail.removeAll();
            jPanelDetail.repaint();
            
            //Obtenim el valor dels camps de la fila
            String id = modelTable.getValueAt(selectedRow, ID).toString();
            String type = modelTable.getValueAt(selectedRow, TYPE).toString();
            String latitude = modelTable.getValueAt(selectedRow, LATITUDE).toString();
            String longitude = modelTable.getValueAt(selectedRow, LONGITUDE).toString();
            
            //Instanciem el contenidor
            Container container = new Container();
            container.setId(id);
            container.setType(type);
            container.setLatitude(Double.parseDouble(latitude));
            container.setLongitude(Double.parseDouble(longitude));
            
            //Instanciem el InternalFrame
            containerDelete = new ContainerDelete(container, modelTable, selectedRow);
            
            //Afegim el formulari al panel d'eliminar
            jPanelDetail.add(containerDelete);
            
            //Centrem la finestra
            FormsUtils.centerJInternalFrame(jPanelDetail, containerDelete);
            
            //El fem visible
            containerDelete.setVisible(true);

        } else {
            
            //No hi ha cap fila seleccionada
            JOptionPane.showMessageDialog(null, "Container not selected", "CityNet - Eliminar contenidor", JOptionPane.ERROR_MESSAGE);
            
        }
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnIncidentAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncidentAddActionPerformed
        
        //Obtenim la fila seleccionada
        int selectedRow = jTableContainers.getSelectedRow();
        
        if (selectedRow != -1) {
            
            //Netejem qualsevol finestra oberta anteriorment en l'escriptori
            jPanelDetail.removeAll();
            jPanelDetail.repaint();
            
            //Obtenim el valor dels camps de la fila
            String id = modelTable.getValueAt(selectedRow, ID).toString();
            String type = modelTable.getValueAt(selectedRow, TYPE).toString();
            String latitude = modelTable.getValueAt(selectedRow, LATITUDE).toString();
            String longitude = modelTable.getValueAt(selectedRow, LONGITUDE).toString();
            
            //Instanciem el contenidor
            Container container = new Container();
            container.setId(id);
            container.setType(type);
            container.setLatitude(Double.parseDouble(latitude));
            container.setLongitude(Double.parseDouble(longitude));
            
            //Instanciem el InternaFrame per notificar una incidència
            incidentAdd = new IncidentAdd(container, modelTable, selectedRow);
            
            //Afegim el formulari al panel de detall de la incidència
            jPanelDetail.add(incidentAdd);
            
            //Centrem la finestra
            FormsUtils.centerJInternalFrame(jPanelDetail, incidentAdd);
            
            //El fem visible
            incidentAdd.setVisible(true);
            
        } else {
            
            //No hi ha cap fila seleccionada
            JOptionPane.showMessageDialog(null, "Container not selected", "CityNet - Notificar incidència", JOptionPane.ERROR_MESSAGE);
            
        }
        
        
    }//GEN-LAST:event_btnIncidentAddActionPerformed

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        
        //Obtenir la fila del contenidor
        int selectedRow = jTableContainers.getSelectedRow();
        
        if (selectedRow != -1) {
            
            //Netejem qualsevol finestra oberta anteriorment en l'escriptori
            jPanelDetail.removeAll();
            jPanelDetail.repaint();
            
            //Obtenim el valor dels camps de la fila
            String id = modelTable.getValueAt(selectedRow, ID).toString();
            String type = modelTable.getValueAt(selectedRow, TYPE).toString();
            String latitude = modelTable.getValueAt(selectedRow, LATITUDE).toString();
            String longitude = modelTable.getValueAt(selectedRow, LONGITUDE).toString();
            
            //Instanciem el contenidor
            Container container = new Container();
            container.setId(id);
            container.setType(type);
            container.setLatitude(Double.parseDouble(latitude));
            container.setLongitude(Double.parseDouble(longitude));
            
            //Instanciem el InternalFrame
            containerLocModify = new ContainerLocModify(container, modelTable, selectedRow);
            
            //Afegim el formulari al panel de modificar
            jPanelDetail.add(containerLocModify);
            
            //Centrem la finestra
            FormsUtils.centerJInternalFrame(jPanelDetail, containerLocModify);
            
            //El fem visible
            containerLocModify.setVisible(true);
            
        } else {
            
            //No hi ha cao fila seleccionada
            JOptionPane.showMessageDialog(null, "Container not selected", "CityNet - Modificar localització", JOptionPane.ERROR_MESSAGE);
            
        }
        
    }//GEN-LAST:event_btnModifyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvPag;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnIncidenceDetail;
    private javax.swing.JButton btnIncidentAdd;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnRePag;
    private javax.swing.JComboBox<String> cbxContainerOperative;
    private javax.swing.JComboBox<String> cbxContainerType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelDetail;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTableContainers;
    private javax.swing.JLabel lblType;
    // End of variables declaration//GEN-END:variables
}
