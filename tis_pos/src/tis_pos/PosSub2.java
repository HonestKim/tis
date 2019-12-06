
package tis_pos;


public class PosSub2 extends javax.swing.JFrame {
	
	PosSub3 sub3;
	
	public PosSub2() { }
    public PosSub2(PosSub3 sub3_) {
    	 sub3= sub3_;						//PosSub3의 객체를 가져와서 담음
        initComponents();
        
        tf1.setText("수정할 값 입력 ");
        tf1.setEditable(false);
        
        tf2.requestFocus();

    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void btModifyActionPerformed(java.awt.event.ActionEvent evt) {
        
    	String input = tf2.getText();		//사용자가 입력한 수정할 값을 저장
    	String sql ="";
    	String column[] = {"상품코드", "상품명", "입고가", "출고가", "재고", "입고일"};
         
         switch(sub3.gui.col) {		//선택한 행 정보를 POS_GUI로부터 가져옴
			case 0: sql = "update 상품 set "+column[0]+ "= '"+input+"' where 상품코드 = '"+sub3.gui.key+"'"; break;
			case 1: sql = "update 상품 set "+column[1]+ "= '"+input+"' where 상품코드 = '"+sub3.gui.key+"'"; break;
			case 2: sql = "update 상품 set "+column[2]+ "= '"+input+"' where 상품코드 = '"+sub3.gui.key+"'"; break;
			case 3: sql = "update 상품 set "+column[3]+ "= '"+input+"' where 상품코드 = '"+sub3.gui.key+"'"; break;
			case 4: sql = "update 상품 set "+column[4]+ "= '"+input+"' where 상품코드 = '"+sub3.gui.key+"'"; break;
			case 5: sql = "update 상품 set "+column[5]+ "= '"+input+"' where 상품코드 = '"+sub3.gui.key+"'"; break;
			//key값은 상품코드 POS_GUI로부터 가져옴
			default : break;
			}
       
         int result =  sub3.gui.dao.delete_update(sql);			//sql문을 실행하는 함수 호출
         String str = (result > 0) ? "수정성공":"수정실패";		//성공여부 
         sub3.gui.showMessage(str);								//메시지 출력
         sub3.gui.sql_to_table(sub3.gui.func());				
         //수정 후 사용자가 조회한 정보를 다시 테이블에 띄우기 func()함수에서 반환된 sql문을 가지고 sql_to_table 실행
         this.dispose();
         this.sub3.dispose();
         //PosSub2, PosSub3 두개의 창 모두 끄기
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {
    	
    	String str = "수정요청 취소";
    	sub3.gui.showMessage(str);
        this.dispose();
        //PosSub2 창만 끄기
        
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBackground = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        btModify = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        lbLogo = new javax.swing.JLabel();
        tf1 = new javax.swing.JTextField();
        tf2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jBackground.setBackground(new java.awt.Color(81, 13, 117));

        lbTitle.setFont(new java.awt.Font("맑은 고딕", 1, 12)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(180, 255, 229));
        lbTitle.setText("| 상품정보수정");

        btModify.setBackground(new java.awt.Color(180, 255, 229));
        btModify.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btModify.setForeground(new java.awt.Color(81, 13, 117));
        btModify.setText("수정하기");
        btModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btModifyActionPerformed(evt);
            }
        });

        btCancel.setBackground(new java.awt.Color(255, 153, 153));
        btCancel.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btCancel.setForeground(new java.awt.Color(81, 13, 117));
        btCancel.setText("취소");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        lbLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_down3.png"))); // NOI18N

        tf1.setBackground(new java.awt.Color(255, 255, 255));
        tf1.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        tf1.setForeground(new java.awt.Color(81, 13, 117));
        tf1.setText("수정할 값 입력");

        tf2.setBackground(new java.awt.Color(255, 255, 255));
        tf2.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        tf2.setForeground(new java.awt.Color(81, 13, 117));

        javax.swing.GroupLayout jBackgroundLayout = new javax.swing.GroupLayout(jBackground);
        jBackground.setLayout(jBackgroundLayout);
        jBackgroundLayout.setHorizontalGroup(
            jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBackgroundLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBackgroundLayout.createSequentialGroup()
                        .addComponent(tf1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tf2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jBackgroundLayout.createSequentialGroup()
                        .addComponent(lbLogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbTitle))
                    .addGroup(jBackgroundLayout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(btModify)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jBackgroundLayout.setVerticalGroup(
            jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBackgroundLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTitle))
                .addGap(18, 18, 18)
                .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btModify, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


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
            java.util.logging.Logger.getLogger(PosSub2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PosSub2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PosSub2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PosSub2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PosSub2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btModify;
    private javax.swing.JPanel jBackground;
    private javax.swing.JLabel lbLogo;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTextField tf1;
    private javax.swing.JTextField tf2;
    // End of variables declaration//GEN-END:variables
}
