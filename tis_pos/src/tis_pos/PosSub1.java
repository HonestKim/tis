
package tis_pos;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PosSub1 extends javax.swing.JFrame {

	ArrayList<VO> list=new ArrayList<VO>();
	POS_GUI gui;		//POS_GUI의 정보를 담을 변수	
	String[][]data;		//JTable Object의 값을 String값으로 바꾸어 사용할 2차원String배열
	DAO dao=new DAO();
   
	public PosSub1() { }
	public PosSub1(POS_GUI gui_) {
        gui=gui_;
    	initComponents();
    	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
	private void btInsertActionPerformed(java.awt.event.ActionEvent evt) {		//상품등록버튼 선택시
		
		 int regist=JOptionPane.showConfirmDialog(this,"등록 하시겠습니까?");
		 
	     switch (regist) {
	     	case JOptionPane.NO_OPTION : show_Msg("신규 상품 등록이 취소되었습니다."); break;
			
	     	case JOptionPane.YES_OPTION :
	     			restart:
					for(int i=0;i<data.length;i++) {
						int have_null = 0;							
						for(int j=0; j<6; j++) { if(data[i][j] == null) { have_null++;  } }
						//have_null 변수는 각 행에 null값이 하나라도 있는 체크하는 변수
						
						if(have_null == 0) {	
							// null값이 하나도 없는 테이블에 한해서 상품등록을 실행함
							int duplication = dao.check_code(data[i][0]);
							//check_code는 테이블내에 동일한 상품코드 값이 있는지 검사 한후 1 또는 0 값을 반환 data[i][0]은 해당행의 상품코드값

							if(duplication == 1) { dupli_Msg(data[i][0]); }	// 리턴값이 1 : 상품코드값 중복되었을 경우, 0 : 중복없음
							else if(duplication < 0) { show_Msg("데이터베이스 접속에 실패했습니다."); } //리턴값이 음수 : 실패
							else {   //상품코드값이 중복되지 않았을 경우
								try {
									int in_price = Integer.parseInt(data[i][2]);
									int out_price = Integer.parseInt(data[i][3]);
									int stock = Integer.parseInt(data[i][4]);
									java.sql.Date wdate = java.sql.Date.valueOf(data[i][5]);
									VO mv=new VO(data[i][0],data[i][1], in_price, out_price , stock , wdate );
									list.add(mv);	//베열에 저장
								}
								catch(NumberFormatException e) { error_Msg(i); show_Msg("해당 정수 값이 올바르지 않습니다.");  continue restart; }	
								// 정수입력란에 문자열 입력시 오류
								catch(IllegalArgumentException e) { error_Msg(i); show_Msg("날짜형식은 2019-12-02 로 입력해주세요");  continue restart; }
								//날짜 포맷을 정확히 입력하지 않았을 경우 오류
								Success_Msg(i);	//해당 행 등록 성공에 대한 메시지 출력
								}	
						}//////////////////////if(have_null==0)///////////////
						
						else { 								
							if(have_null == 6) { continue; }	//전부 null값일 경우 그냥 지나침
							else { error_Msg(i); }				//하나의 칸이라도 정보의 입력이 있을경우 에러메시지 출력
						}/////////////////////////////////////////////////////
					}///////////////////////////////for문////////////////////////////////////////////////
	     		
					int cnt = dao.insert(list);		//배열에 저장된 값을 insert sql문으로 실행하는 함수
					showMessage(cnt);				//몇 개의 정보가 등록성공 했는지 출력
					list.clear();					//배열을 비워줌.
					break;
			}
	        
	}//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void btClearActionPerformed(java.awt.event.ActionEvent evt) {		//초기화버튼 선택시
    	int reset=JOptionPane.showConfirmDialog(this,"모두 지우시겠습니까?");

    	switch (reset) {
			case JOptionPane.NO_OPTION:
				break;
			case JOptionPane.YES_OPTION:
				DefaultTableModel model = (DefaultTableModel)jTable.getModel();
				model.setNumRows(0);  //초기화 할때 각 표에 null값 채우기?
				model.setNumRows(8);
				break;
		}
    }//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void showMessage(int cnt) {
   	  JOptionPane.showMessageDialog(this, cnt+"개의 정보 등록에 성공했습니다.");
   }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   public void Success_Msg(int row) {
   	  JOptionPane.showMessageDialog(this, (row+1)+"행 정보 등록 성공!");
   }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     
   public void error_Msg(int row) {
 	  JOptionPane.showMessageDialog(this, (row+1)+"행의 정보가 올바르지 않습니다. 값을 올바르게 입력해주세요");
   }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   public void show_Msg(String str) {
   	  JOptionPane.showMessageDialog(this, str);
   }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   public void dupli_Msg(String key) {
 	  JOptionPane.showMessageDialog(this, "상품코드 "+key+" 값이 중복되었습니다. 등록실패!");
   }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBackground = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        btInsert = new javax.swing.JButton();
        btClear = new javax.swing.JButton();
        lbLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jBackground.setBackground(new java.awt.Color(81, 13, 117));

        lbTitle.setFont(new java.awt.Font("맑은 고딕", 1, 12)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(180, 255, 229));
        lbTitle.setText("| 상품등록");

        jTable.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        jTable.setForeground(new java.awt.Color(81, 13, 117));
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            data = new String [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "상품코드", "상품명", "입고가", "출고가", "재고", "입고일"
            }
            ) {
                
                @Override
                public void setValueAt(Object o, int row,int col) {
          		  super.setValueAt(o, row, col);
          		  
          		  String str=o.toString();
          		  data[row][col]=str;
          		  
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        jTable.setSelectionBackground(new java.awt.Color(180, 255, 229));
        jTable.setSelectionForeground(new java.awt.Color(81, 13, 117));
        jScrollPane.setViewportView(jTable);

        btInsert.setBackground(new java.awt.Color(180, 255, 229));
        btInsert.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btInsert.setForeground(new java.awt.Color(81, 13, 117));
        btInsert.setText("상품등록");
        btInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInsertActionPerformed(evt);
            }
        });

        btClear.setBackground(new java.awt.Color(255, 153, 153));
        btClear.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btClear.setForeground(new java.awt.Color(81, 13, 117));
        btClear.setText("초기화");
        btClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btClearActionPerformed(evt);
            }
        });

        lbLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_down3.png"))); // NOI18N

        javax.swing.GroupLayout jBackgroundLayout = new javax.swing.GroupLayout(jBackground);
        jBackground.setLayout(jBackgroundLayout);
        jBackgroundLayout.setHorizontalGroup(
            jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBackgroundLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jBackgroundLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lbLogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btClear)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jBackgroundLayout.setVerticalGroup(
            jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBackgroundLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btClear, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTitle))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(PosSub1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PosSub1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PosSub1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PosSub1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PosSub1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClear;
    private javax.swing.JButton btInsert;
    private javax.swing.JPanel jBackground;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable jTable;
    private javax.swing.JLabel lbLogo;
    private javax.swing.JLabel lbTitle;
    // End of variables declaration//GEN-END:variables
}
