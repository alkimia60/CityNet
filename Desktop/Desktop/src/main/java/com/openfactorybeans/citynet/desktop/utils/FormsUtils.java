/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openfactorybeans.citynet.desktop.utils;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jose
 */
public class FormsUtils {
    
    public static void centerDesktop(JDesktopPane jDesktop, JInternalFrame internalFrame) {
        
        //Obtenim els valors x, y amb la mida de l'escriptori dividit per 2 menys la mida del jInternalFrame dividit per 2
        int x = (jDesktop.getWidth() / 2) - (internalFrame.getWidth() / 2);
        int y = (jDesktop.getHeight() / 2) - (internalFrame.getHeight() / 2);
        
        //La posicionem al mig
        internalFrame.setLocation(x, y);
        
    }
    
    public static void centerJInternalFrame(JPanel jPanel, JInternalFrame jInternal) {
        
        //Obtenim els valors x, y amb la mida de l'escriptori dividit per 2 menys la mida del jInternalFrame dividit per 2
        int x = (jPanel.getWidth() / 2) - (jInternal.getWidth() / 2);
        int y = (jPanel.getHeight() / 2) - (jInternal.getHeight() / 2);
        
        //La posicionem al mig
        jInternal.setLocation(x, y);
        
    }
    
}
