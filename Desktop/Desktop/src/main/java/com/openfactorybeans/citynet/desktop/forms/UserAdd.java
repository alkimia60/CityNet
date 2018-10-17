/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfactorybeans.citynet.desktop.forms;

import com.openfactorybeans.citynet.desktop.errors.ErrorForm;
import com.openfactorybeans.citynet.desktop.users.Add;
import com.openfactorybeans.citynet.desktop.users.User;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author Jose
 */
public class UserAdd extends javax.swing.JInternalFrame {
    
    //URL
    private static final String PUBLIC_URL = "http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080/citynet/UserManager";
    
    //Inicialització missatges d'error
    final String REQUIRED = "Els camps són obligatoris o el format no és correcte";
    final String NOTSAME = "Les contrasenyes no són iguals";
    
    //Declaració dels errors
    ErrorForm errorForm = new ErrorForm();
    
    //Declaració de variables dels camps del formulari
    String name, surname, address, postCode, city, email, password1, password2;
    char[] pass1, pass2;
    

    /**
     * Creates new form UserAdd
     */
    public UserAdd() {
        initComponents();
        
        //Posem l'estat inicial del formulari
        initialStateForm();
    }
    
    /**
     * Estat inicial del formulari amb els camps sense dades i
     * el panel de missatges ocult
     */
    public void initialStateForm() {
        
        txtName.setText("");
        txtSurname.setText("");
        txtAddress.setText("");
        txtPostCode.setText("");
        txtCity.setText("");
        txtEmail.setText("");
        pswPass1.setText("");
        pswPass2.setText("");
        lblMessagesError.setText("");
        
    }
    
    /**
     * Verifica que els camps són correctes per afegir l'usuari.
     * En cas contrari, mostrarà un missatge d'error.
     * @return errors Serà true si no hi ha errors i false si hi ha errors
     */
    
    public ErrorForm checkForm() {
        
        //Posem els errors a false
        errorForm.setRequired(false);
        errorForm.setNotSame(false);
        
        //Verifiquem el camp txtName
        name = txtName.getText();
        
        if (name.equals("")) {
            errorForm.setRequired(true);
            
            //Mostrem l'etiqueta del camp en un altre color
            lblName.setForeground(Color.RED);
            
        } else {
            
            //Restaurem el color de l'etiqueta
            lblName.setForeground(Color.BLACK);
            
        }
        
        //Verifiquem el camp txtSurname
        surname = txtSurname.getText();
        
        if (surname.equals("")) {
            errorForm.setRequired(true);
            
            //Mostrem l'etiqueta del camp en un altre color
            lblSurname.setForeground(Color.RED);
            
        } else {
            
            //Restaurem el color de l'etiqueta
            lblSurname.setForeground(Color.BLACK);
            
        }
        
        //Verifiquem el camp txtAddress
        address = txtAddress.getText();
        
        if (address.equals("")) {
            errorForm.setRequired(true);
            
            //Mostrem l'etiqueta del camp en un altre color
            lblAddress.setForeground(Color.RED);
            
        } else {
            
            //Restaurem el color de l'etiqueta
            lblAddress.setForeground(Color.BLACK);
            
        }
        
        //Verifiquem el camp txtPostCode
        postCode = txtPostCode.getText();
        
        if (postCode.equals("")) {
            errorForm.setRequired(true);
            
            //Mostrem l'etiqueta del camp en un altre color
            lblPostCode.setForeground(Color.RED);
            
        } else {
            
            //Restaurem el color de l'etiqueta
            lblPostCode.setForeground(Color.BLACK);
            
        }
        
        //Verifiquem el camp txtCity
        city = txtCity.getText();
        
        if (city.equals("")) {
            errorForm.setRequired(true);
            
            //Mostrem l'etiqueta del camp en un altre color
            lblCity.setForeground(Color.RED);
            
        } else {
            
            //Restaurem el color de l'etiqueta
            lblCity.setForeground(Color.BLACK);
            
        }
        
        //Verifiquem el camp txtEmail
        email = txtEmail.getText();
        
        if (email.equals("")) {
            errorForm.setRequired(true);
            
            //Mostrem l'etiqueta del camp en un altre color
            lblEmail.setForeground(Color.RED);
            
        } else {
            
            //Restaurem el color de l'etiqueta
            lblEmail.setForeground(Color.BLACK);
            
        }
        
        //Verifiquem el camp pswPass1
        pass1 = pswPass1.getPassword();
        password1 = new String(pass1); //Passem el password a String
        
        if (password1.equals("")) {
            errorForm.setRequired(true);
            
            //Mostrem l'etiqueta del camp en un altre color
            lblPass1.setForeground(Color.RED);
            
        } else {
            
            //Restaurem el color de l'etiqueta
            lblPass1.setForeground(Color.BLACK);
            
        }
        
        //Verifiquem el camp pswPass1
        pass2 = pswPass2.getPassword();
        password2 = new String(pass2); //Passem el password a String
        
        if (password2.equals("")) {
            errorForm.setRequired(true);
            
            //Mostrem l'etiqueta del camp en un altre color
            lblPass2.setForeground(Color.RED);
            
        } else {
            
            //Restaurem el color de l'etiqueta
            lblPass1.setForeground(Color.BLACK);
            
        }
            
        //Verifiquem que s'ha posat la mateixa contrasenya en els dos camps
        if (password1.equals(password2)) {

            //Són iguals. 
            errorForm.setNotSame(false);

            //Restaurem el color de l'etiqueta
            lblPass1.setForeground(Color.BLACK);
            lblPass2.setForeground(Color.BLACK);

        } else {

            //No són iguals.
            errorForm.setNotSame(true);

            //Mostrem l'etiqueta del camp en un altre color
            lblPass1.setForeground(Color.RED);
            lblPass2.setForeground(Color.RED);

        }
                
        return errorForm;
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanelPerosnalInfo = new javax.swing.JPanel();
        lblTitlePersonalInfo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblSurname = new javax.swing.JLabel();
        txtSurname = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        lblPostCode = new javax.swing.JLabel();
        txtPostCode = new javax.swing.JTextField();
        lblCity = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        jPanelLogin = new javax.swing.JPanel();
        lblTitleLogin = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPass1 = new javax.swing.JLabel();
        pswPass1 = new javax.swing.JPasswordField();
        lblPass2 = new javax.swing.JLabel();
        pswPass2 = new javax.swing.JPasswordField();
        jPanelMessages = new javax.swing.JPanel();
        lblMessagesError = new javax.swing.JLabel();
        jPanelButtons = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnClean = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setClosable(true);
        setTitle("Afegir usuari");

        lblTitlePersonalInfo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTitlePersonalInfo.setText("Dades personals");

        lblName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblName.setText("Nom:");

        txtName.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtName.setText("Name");
        txtName.setPreferredSize(new java.awt.Dimension(85, 27));

        lblSurname.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSurname.setText("Cognoms:");

        txtSurname.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtSurname.setText("Surnames");

        lblAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAddress.setText("Adreça:");

        txtAddress.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtAddress.setText("Address");

        lblPostCode.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblPostCode.setText("CP:");

        txtPostCode.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPostCode.setText("Postal C");

        lblCity.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCity.setText("Població:");

        txtCity.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtCity.setText("City");

        javax.swing.GroupLayout jPanelPerosnalInfoLayout = new javax.swing.GroupLayout(jPanelPerosnalInfo);
        jPanelPerosnalInfo.setLayout(jPanelPerosnalInfoLayout);
        jPanelPerosnalInfoLayout.setHorizontalGroup(
            jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPerosnalInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPerosnalInfoLayout.createSequentialGroup()
                        .addComponent(lblTitlePersonalInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1))
                    .addGroup(jPanelPerosnalInfoLayout.createSequentialGroup()
                        .addGroup(jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSurname)
                            .addComponent(lblName)
                            .addComponent(lblAddress)
                            .addComponent(lblPostCode))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSurname)
                            .addComponent(txtAddress)
                            .addGroup(jPanelPerosnalInfoLayout.createSequentialGroup()
                                .addComponent(txtPostCode, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblCity)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCity))
                            .addGroup(jPanelPerosnalInfoLayout.createSequentialGroup()
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanelPerosnalInfoLayout.setVerticalGroup(
            jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPerosnalInfoLayout.createSequentialGroup()
                .addGroup(jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitlePersonalInfo))
                .addGap(18, 18, 18)
                .addGroup(jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSurname)
                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddress)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelPerosnalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPostCode)
                    .addComponent(txtPostCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCity)
                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTitleLogin.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTitleLogin.setText("Dades d'identificació");

        lblEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEmail.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtEmail.setText("Email");

        lblPass1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblPass1.setText("Contrasenya:");

        pswPass1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        pswPass1.setText("pass");

        lblPass2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblPass2.setText("Repetir contrasenya:");

        pswPass2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        pswPass2.setText("pass");

        javax.swing.GroupLayout jPanelLoginLayout = new javax.swing.GroupLayout(jPanelLogin);
        jPanelLogin.setLayout(jPanelLoginLayout);
        jPanelLoginLayout.setHorizontalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addComponent(lblTitleLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelLoginLayout.createSequentialGroup()
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPass2)
                            .addComponent(lblPass1)
                            .addComponent(lblEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pswPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelLoginLayout.createSequentialGroup()
                                .addComponent(pswPass1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)))))
                .addContainerGap())
        );
        jPanelLoginLayout.setVerticalGroup(
            jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginLayout.createSequentialGroup()
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitleLogin))
                .addGap(18, 18, 18)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPass1)
                    .addComponent(pswPass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPass2)
                    .addComponent(pswPass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        lblMessagesError.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblMessagesError.setForeground(new java.awt.Color(102, 0, 0));
        lblMessagesError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelMessagesLayout = new javax.swing.GroupLayout(jPanelMessages);
        jPanelMessages.setLayout(jPanelMessagesLayout);
        jPanelMessagesLayout.setHorizontalGroup(
            jPanelMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMessagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessagesError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelMessagesLayout.setVerticalGroup(
            jPanelMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMessagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMessagesError, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAdd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAdd.setText("Afegir");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnClean.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnClean.setText("Netejar");
        btnClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCancel.setText("Cancel·lar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelButtonsLayout = new javax.swing.GroupLayout(jPanelButtons);
        jPanelButtons.setLayout(jPanelButtonsLayout);
        jPanelButtonsLayout.setHorizontalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelButtonsLayout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClean, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelButtonsLayout.setVerticalGroup(
            jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnClean)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPerosnalInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelMessages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addComponent(jPanelPerosnalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMessages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        
        //Tanquem el formulari
        this.dispose();
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanActionPerformed
        
        //Posem l'estat inicial del formulari
        initialStateForm();
        
    }//GEN-LAST:event_btnCleanActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        
        //Verifiquem el contingut dels camps del formulari
        errorForm = checkForm();
        
        if (!errorForm.isRequired() && !errorForm.isNotSame()) {
            
            //No hi ha errors
            
            //Netejem l'etiqueta dels missatges
            lblMessagesError.setText("");
            
            JOptionPane.showMessageDialog(null, "No hi ha errors", "Errors", JOptionPane.INFORMATION_MESSAGE);
            
            ////////////////////////////////////////////////////////////////////////////
            //Conectem amb el servidor per afegir un usuari
            ////////////////////////////////////////////////////////////////////////////
            Add addUser = new Add();
            User user = new User(email, password1, name, surname, address, postCode, city);
            addUser.userRegister(user, PUBLIC_URL);
            
        } else if (errorForm.isRequired()) {
            
            //Falta algun cap obligatori
            
            //Mostrem el missatge
            lblMessagesError.setText(REQUIRED);
            
        } else if (errorForm.isNotSame()) {
            
            //Les contrasenyes no són iguals
            lblMessagesError.setText(NOTSAME);
            
        }
 
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClean;
    private javax.swing.JPanel jPanelButtons;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelMessages;
    private javax.swing.JPanel jPanelPerosnalInfo;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblMessagesError;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPass1;
    private javax.swing.JLabel lblPass2;
    private javax.swing.JLabel lblPostCode;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JLabel lblTitleLogin;
    private javax.swing.JLabel lblTitlePersonalInfo;
    private javax.swing.JPasswordField pswPass1;
    private javax.swing.JPasswordField pswPass2;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPostCode;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables
}
