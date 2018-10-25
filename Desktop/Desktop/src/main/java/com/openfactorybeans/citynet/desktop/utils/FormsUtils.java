package com.openfactorybeans.citynet.desktop.utils;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * Codificació d'utilitats dels formularis
 * @author Jose
 */
public class FormsUtils {
    
    /**
     * Mètode per centrar les finestres en un desktop
     * @param jDesktop L'escriptori on es vol centrar
     * @param internalFrame La finestra que es vol centrar
     */
    public static void centerDesktop(JDesktopPane jDesktop, JInternalFrame internalFrame) {
        
        //Obtenim els valors x, y amb la mida de l'escriptori dividit per 2 menys la mida del jInternalFrame dividit per 2
        int x = (jDesktop.getWidth() / 2) - (internalFrame.getWidth() / 2);
        int y = (jDesktop.getHeight() / 2) - (internalFrame.getHeight() / 2);
        
        //La posicionem al mig
        internalFrame.setLocation(x, y);
        
    }
    
    /**
     * Mètode per centrar les finestres en un panel
     * @param jPanel El panel on el vol centrar
     * @param jInternal La finestra que es vol centrar
     */
    public static void centerJInternalFrame(JPanel jPanel, JInternalFrame jInternal) {
        
        //Obtenim els valors x, y amb la mida de l'escriptori dividit per 2 menys la mida del jInternalFrame dividit per 2
        int x = (jPanel.getWidth() / 2) - (jInternal.getWidth() / 2);
        int y = (jPanel.getHeight() / 2) - (jInternal.getHeight() / 2);
        
        //La posicionem al mig
        jInternal.setLocation(x, y);
        
    }
    
}
