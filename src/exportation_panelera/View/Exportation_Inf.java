/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package exportation_panelera.View;







public class Exportation_Inf extends javax.swing.JFrame {
    
    
   
  
   
    public Exportation_Inf() {
        
       
        
        initComponents();   
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        pmnDate = new java.awt.PopupMenu();
        lblEmplyeID = new javax.swing.JLabel();
        lblFullname = new javax.swing.JLabel();
        txtEmploye_ID = new javax.swing.JTextField();
        txtFullName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDestination = new javax.swing.JTextArea();
        lblDollar = new javax.swing.JLabel();
        lblEuro = new javax.swing.JLabel();
        cbEuro = new javax.swing.JCheckBox();
        cbDollar = new javax.swing.JCheckBox();
        lblProduct_type = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        lblDestination = new javax.swing.JLabel();
        lblPlane = new javax.swing.JLabel();
        lblShip = new javax.swing.JLabel();
        ScrProduct = new java.awt.Choice();
        ckbPlane = new java.awt.Checkbox();
        cbxShip = new java.awt.Checkbox();
        btnCreate = new javax.swing.JButton();
        lblDate = new javax.swing.JLabel();
        jtxtDate = new javax.swing.JFormattedTextField();
        lblImage_Exportation = new javax.swing.JLabel();

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        pmnDate.setLabel("Date");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(300, 300));
        setMinimumSize(new java.awt.Dimension(1000, 1000));
        setMixingCutoutShape(null);
        setModalExclusionType(null);
        getContentPane().setLayout(null);

        lblEmplyeID.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lblEmplyeID.setText("Employe ID");
        getContentPane().add(lblEmplyeID);
        lblEmplyeID.setBounds(99, 62, 77, 18);

        lblFullname.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lblFullname.setText("Full Name");
        getContentPane().add(lblFullname);
        lblFullname.setBounds(99, 121, 77, 18);
        getContentPane().add(txtEmploye_ID);
        txtEmploye_ID.setBounds(188, 60, 147, 23);
        getContentPane().add(txtFullName);
        txtFullName.setBounds(188, 119, 147, 23);

        txtDestination.setColumns(20);
        txtDestination.setRows(5);
        jScrollPane1.setViewportView(txtDestination);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(185, 280, 252, 91);

        lblDollar.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lblDollar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dollar-symbol.png"))); // NOI18N
        lblDollar.setText("Dollar");
        getContentPane().add(lblDollar);
        lblDollar.setBounds(839, 328, 68, 24);

        lblEuro.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lblEuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coin.png"))); // NOI18N
        lblEuro.setText("Euro");
        getContentPane().add(lblEuro);
        lblEuro.setBounds(711, 328, 59, 24);
        getContentPane().add(cbEuro);
        cbEuro.setBounds(776, 333, 19, 19);
        getContentPane().add(cbDollar);
        cbDollar.setBounds(913, 333, 19, 19);

        lblProduct_type.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lblProduct_type.setText("Product Type");
        getContentPane().add(lblProduct_type);
        lblProduct_type.setBounds(689, 58, 115, 18);

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel2.setText("Amount");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(838, 58, 69, 18);
        getContentPane().add(txtAmount);
        txtAmount.setBounds(830, 90, 86, 23);

        lblDestination.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lblDestination.setForeground(new java.awt.Color(255, 255, 255));
        lblDestination.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logistics.png"))); // NOI18N
        lblDestination.setText("Destination");
        lblDestination.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(lblDestination);
        lblDestination.setBounds(0, 280, 173, 91);

        lblPlane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/takeoff-the-plane.png"))); // NOI18N
        getContentPane().add(lblPlane);
        lblPlane.setBounds(35, 454, 24, 24);

        lblShip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/boat.png"))); // NOI18N
        getContentPane().add(lblShip);
        lblShip.setBounds(105, 454, 24, 24);

        ScrProduct.setFocusTraversalKeysEnabled(false);
        ScrProduct.setFocusable(false);
        ScrProduct.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        ScrProduct.setName("Panela\nWine\nSugar\nSuryp\nEthanol\n"); // NOI18N
        ScrProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ScrProductMouseClicked(evt);
            }
        });
        ScrProduct.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                ScrProductComponentHidden(evt);
            }
        });
        ScrProduct.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                ScrProductInputMethodTextChanged(evt);
            }
        });
        getContentPane().add(ScrProduct);
        ScrProduct.setBounds(672, 89, 132, 21);
        getContentPane().add(ckbPlane);
        ckbPlane.setBounds(69, 458, 26, 20);
        getContentPane().add(cbxShip);
        cbxShip.setBounds(131, 458, 26, 20);

        btnCreate.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });
        getContentPane().add(btnCreate);
        btnCreate.setBounds(500, 439, 90, 30);

        lblDate.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("Date");
        getContentPane().add(lblDate);
        lblDate.setBounds(180, 390, 42, 20);
        getContentPane().add(jtxtDate);
        jtxtDate.setBounds(230, 390, 190, 23);

        lblImage_Exportation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Exportation Image 4.png"))); // NOI18N
        getContentPane().add(lblImage_Exportation);
        lblImage_Exportation.setBounds(2, -3, 940, 500);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ScrProductInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_ScrProductInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ScrProductInputMethodTextChanged

    private void ScrProductComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_ScrProductComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_ScrProductComponentHidden

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        Delivery_Inf Delivery_inf = new Delivery_Inf();
      Delivery_inf.setVisible(true);
      this.dispose();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void ScrProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScrProductMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ScrProductMouseClicked

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Exportation_Inf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Exportation_Inf().setVisible(true);
            }
        });
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice ScrProduct;
    private javax.swing.JButton btnCreate;
    private javax.swing.JCheckBox cbDollar;
    private javax.swing.JCheckBox cbEuro;
    private java.awt.Checkbox cbxShip;
    private java.awt.Checkbox ckbPlane;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField jtxtDate;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDestination;
    private javax.swing.JLabel lblDollar;
    private javax.swing.JLabel lblEmplyeID;
    private javax.swing.JLabel lblEuro;
    private javax.swing.JLabel lblFullname;
    private javax.swing.JLabel lblImage_Exportation;
    private javax.swing.JLabel lblPlane;
    private javax.swing.JLabel lblProduct_type;
    private javax.swing.JLabel lblShip;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private java.awt.PopupMenu pmnDate;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextArea txtDestination;
    private javax.swing.JTextField txtEmploye_ID;
    private javax.swing.JTextField txtFullName;
    // End of variables declaration//GEN-END:variables

  
}


