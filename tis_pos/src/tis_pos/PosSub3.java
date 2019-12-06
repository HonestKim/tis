
package tis_pos;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PosSub3 extends javax.swing.JFrame {
  
	POS_GUI gui;	//POS_GUI의 정보를 담고있을 변수
	PosSub2 sub2;	//PosSub2객체를 호출할 때 사용할 변수
	String text;	//선택된 행에 대한 모든 정보를 담을 문자열 변수
	
	public PosSub3() { }
    public PosSub3(POS_GUI gui_) {
        initComponents();
        gui = gui_;

	
		text = gui.dao.sql_write(gui.key, 6);	 //선택된 행에 대한 모든 정보를 String형으로 반환해주는 함수
		tf1.setText(text);						//선택된 행에 대한 모든 정보를 Text Field에 출력
		tf1.setEditable(false);					//사용자 수정불가
		
		
		text = gui.dao.sql_write(gui.key, gui.col); //선택한 값을 String형으로 반환해주는 함수
		tf2.setText(text);							//선택한 값을 String형으로 반환해주는 함수
		tf2.setEditable(false);						//사용자 수정불가
		
        tf3.setText("에 어떤 작업을 하시겠습니까?");
        tf3.setEditable(false);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) { 		//삭제버튼 클릭시 
        
    	String str="";
    	int yn = JOptionPane.showConfirmDialog(this ,gui.key+" 상품을 삭제할까요 ?");
		
    	if(yn == JOptionPane.YES_OPTION) {		
			String sql = "delete from 상품 where 상품코드 = '"+gui.key+"'"; 	//해당 행 삭제를 위해 유일한 키값인 상품코드 사용
			int result = gui.dao.delete_update(sql);							//sql문을 가지고 테이블에서 삭제를 수행하는 메소드
			str = (result > 0) ? "삭제성공":"삭제실패";							//삭제 성공 여부를 문자열에 저장		
			gui.sql_to_table(gui.func());										//최초의 조회정보 출력시 사용했던 sql문을 반환해주는 func()함수로부터 받아서 테이블로 뿌리기				
		}
    	
    	else { str = "삭제요청취소"; }
    	
		gui.showMessage(str);									
		this.dispose();
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void btModifyActionPerformed(java.awt.event.ActionEvent evt) {
        
        sub2 = new PosSub2(PosSub3.this);	//수정버튼 클릭시 PosSub2객체 호출
		sub2.pack();
  	   	sub2.setLocation(500,300);
  	   	sub2.setVisible(true);
       
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBackground = new javax.swing.JPanel();
        btModify = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        tf2 = new javax.swing.JTextField();
        tf3 = new javax.swing.JTextField();
        tf1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jBackground.setBackground(new java.awt.Color(81, 13, 117));

        btModify.setBackground(new java.awt.Color(180, 255, 229));
        btModify.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btModify.setForeground(new java.awt.Color(81, 13, 117));
        btModify.setText("수정");
        btModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btModifyActionPerformed(evt);
            }
        });

        btCancel.setBackground(new java.awt.Color(255, 153, 153));
        btCancel.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btCancel.setForeground(new java.awt.Color(81, 13, 117));
        btCancel.setText("삭제");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        tf2.setBackground(new java.awt.Color(255, 255, 255));
        tf2.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        tf2.setForeground(new java.awt.Color(81, 13, 117));
        tf2.setText("20");
        tf2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf2ActionPerformed(evt);
            }
        });

        tf3.setBackground(new java.awt.Color(255, 255, 255));
        tf3.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        tf3.setForeground(new java.awt.Color(81, 13, 117));
        tf3.setText("에 어떤 작업을 하시겠습니까?");

        tf1.setBackground(new java.awt.Color(255, 255, 255));
        tf1.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        tf1.setForeground(new java.awt.Color(81, 13, 117));
        tf1.setText("L0005 / AA건전지 / 1000 / 1500 / 20 / 2019-11-27");

        javax.swing.GroupLayout jBackgroundLayout = new javax.swing.GroupLayout(jBackground);
        jBackground.setLayout(jBackgroundLayout);
        jBackgroundLayout.setHorizontalGroup(
            jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBackgroundLayout.createSequentialGroup()
                .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBackgroundLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(btModify, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jBackgroundLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jBackgroundLayout.createSequentialGroup()
                                .addComponent(tf2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tf3, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jBackgroundLayout.setVerticalGroup(
            jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBackgroundLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(tf1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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



    

    private void tf2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf2ActionPerformed

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
            java.util.logging.Logger.getLogger(PosSub3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PosSub3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PosSub3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PosSub3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PosSub3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btModify;
    private javax.swing.JPanel jBackground;
    private javax.swing.JTextField tf1;
    private javax.swing.JTextField tf2;
    private javax.swing.JTextField tf3;
    // End of variables declaration//GEN-END:variables
}
