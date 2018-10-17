/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfactorybeans.citynet.desktop.forms;

import com.openfactorybeans.citynet.desktop.users.ListAllUsers;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;

/**
 *
 * @author Jose
 */
public class DesktopMain extends javax.swing.JFrame {

    /**
     * Creates new form DesktopMain
     */
    public DesktopMain() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        //setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktop = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuUsers = new javax.swing.JMenu();
        jMenuItemAddUser = new javax.swing.JMenuItem();
        jMenuItemModifyUser = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemListUsers = new javax.swing.JMenuItem();
        jMenuContainers = new javax.swing.JMenu();
        jMenuMyMenu = new javax.swing.JMenu();
        jMenuItemDataUser = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemLogout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jDesktopLayout = new javax.swing.GroupLayout(jDesktop);
        jDesktop.setLayout(jDesktopLayout);
        jDesktopLayout.setHorizontalGroup(
            jDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDesktopLayout.setVerticalGroup(
            jDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        jMenuUsers.setText("Usuaris");

        jMenuItemAddUser.setText("Afegir");
        jMenuItemAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddUserActionPerformed(evt);
            }
        });
        jMenuUsers.add(jMenuItemAddUser);

        jMenuItemModifyUser.setText("Modificar");
        jMenuUsers.add(jMenuItemModifyUser);
        jMenuUsers.add(jSeparator1);

        jMenuItemListUsers.setText("Llistar");
        jMenuItemListUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemListUsersActionPerformed(evt);
            }
        });
        jMenuUsers.add(jMenuItemListUsers);

        jMenuBar1.add(jMenuUsers);

        jMenuContainers.setText("Contenidors");
        jMenuBar1.add(jMenuContainers);

        jMenuMyMenu.setText("El meu menú");

        jMenuItemDataUser.setText("Modificar dades");
        jMenuMyMenu.add(jMenuItemDataUser);
        jMenuMyMenu.add(jSeparator2);

        jMenuItemLogout.setText("Tancar sessió");
        jMenuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogoutActionPerformed(evt);
            }
        });
        jMenuMyMenu.add(jMenuItemLogout);

        jMenuBar1.add(jMenuMyMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktop)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktop)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemListUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemListUsersActionPerformed
        
        //Netejem qualsevol finestra oberta anteriorment en l'escrptori
        jDesktop.removeAll();
        jDesktop.repaint();
        
        //Instanciem el JFrame Llistat
        UsersList userList = new UsersList();        
        
        //Afegim el formulari de llistat a l'escrptori
        jDesktop.add(userList);
        
        //Maximitzem el JFrameInternal
        try {
            userList.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(DesktopMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //El fem visible
        userList.setVisible(true);
        
    }//GEN-LAST:event_jMenuItemListUsersActionPerformed

    private void jMenuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLogoutActionPerformed
        
        //Amaguem l'escriptori i mostrem el login
        Login login = new Login();
        setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_jMenuItemLogoutActionPerformed

    private void jMenuItemAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddUserActionPerformed
        
        //Netejem qualsevol finestra oberta anteriorment en l'escrptori
        jDesktop.removeAll();
        jDesktop.repaint();
        
        //Instanciem el JFrame Afegir
        UserAdd userAdd = new UserAdd();
        
        //Afegim el formulari d'afegir a l'escriptori
        jDesktop.add(userAdd);
        
        //Centrem la finestra
        centerWindow(userAdd);
        
        //El fem visible
        userAdd.setVisible(true);
        
        
    }//GEN-LAST:event_jMenuItemAddUserActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DesktopMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DesktopMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DesktopMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DesktopMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DesktopMain().setVisible(true);
            }
        });
    }
    
    /**
     * Mètode per centrar els jInternalFrame dintre d'un jDesktopPane
     * @param internalFrame que es vol centrar
     */
    public void centerWindow(JInternalFrame internalFrame) {
        
        //Obtenim els valors x, y amb la mida de l'escriptori dividit per 2 menys la mida del jInternalFrame dividit per 2
        int x = (jDesktop.getWidth() / 2) - (internalFrame.getWidth() / 2);
        int y = (jDesktop.getHeight() / 2) - (internalFrame.getHeight() / 2);
        
        //La posicionem al mig
        internalFrame.setLocation(x, y);
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktop;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuContainers;
    private javax.swing.JMenuItem jMenuItemAddUser;
    private javax.swing.JMenuItem jMenuItemDataUser;
    private javax.swing.JMenuItem jMenuItemListUsers;
    private javax.swing.JMenuItem jMenuItemLogout;
    private javax.swing.JMenuItem jMenuItemModifyUser;
    private javax.swing.JMenu jMenuMyMenu;
    private javax.swing.JMenu jMenuUsers;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
