package tis_pos;


import static tis_pos.pos_graph.sumAll;
import static tis_pos.pos_graph.sumAll2;
import static tis_pos.pos_graph.sumAll3;
import static tis_pos.pos_graph.sumCalendar;
import static tis_pos.pos_graph.sumCalendar2;
import static tis_pos.pos_graph.sumCalendar3;
import static tis_pos.pos_graph.sumOneMt;
import static tis_pos.pos_graph.sumOneMt2;
import static tis_pos.pos_graph.sumOneMt3;
import static tis_pos.pos_graph.sumToday;
import static tis_pos.pos_graph.sumToday2;
import static tis_pos.pos_graph.sumToday3;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

//import tis_pos.pos_g.calchoose;
//import pos.pos_sales3.calchoose2;
import tis_pos.pos_graph;


public class POS_GUI extends javax.swing.JFrame {
	ShowListDAO Sld = new ShowListDAO();
	
	DAO dao = new DAO();	
	PosSub1 sub1;			//입력버튼 누를때 나올 객체
	PosSub3  sub3;			//조회버튼 누를때 나올 객체
	int row, col;			//테이블에서 마우스이벤트 발생할때 해당 칸의 행과 열 정보를 가지고 있을 변수
	String key;				//Text Field에 값을 입력되어 있을 경우 값을 저장할 변수
    int column_name = 0;	//ComboBox에서 선택된 메뉴정보를 저장할 변수 ( 상품코드, 상품명,...)
    String[] colHeader = {"상품코드","상품명","입고가", "출고가", "재고", "입고일" };	//조회기능이용시 테이블의 가장 상단에 뿌려줄 문자열배열
	SimpleDateFormat date= new SimpleDateFormat("yy/MM/dd");
    static class calchoose{
		java.sql.Date sqldate = new java.sql.Date(dcFrom.getDate().getTime());
		String date= new SimpleDateFormat("yy/MM/dd").format(sqldate);
			
		}
		
		static class calchoose2{
		
		java.sql.Date sqldate2 = new java.sql.Date(dcTo.getDate().getTime());
	    String date2= new SimpleDateFormat("yy/MM/dd").format(sqldate2);
		}
		
    public POS_GUI() {
        initComponents();
        tfSerch.requestFocus();
        
        jTable.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {		//테이블에서 마우스 클릭이벤트 발생시
        		
        		int check = e.getButton();					//반환값 마우스 왼쪽클릭:1, 오른쪽클릭:-1 왼쪽클릭 기능만 사용함
        		
        		if(check == 1) {
        			
        			row = jTable.getSelectedRow();			
    				col = jTable.getSelectedColumn();			//이벤트가 발생한 위치의 행과 열 정보 저장
    				
    				Object value_ = jTable.getValueAt(row, 0);	//이벤트가 발생한 위치의 행에서 0번열에 있는 상품코드값을 저장
					String value = (String)value_;				//문자열로 변환
    				key = value;								//상품코드를 전역변수 key값에 입력
    				
    				sub3=new PosSub3(POS_GUI.this);				//PosSub3객체 호출, 위치 
        			sub3.pack();
      	    	   	sub3.setLocation(500,100);
      	    	   	sub3.setVisible(true);
        		}
        	}
        });
    }//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void btInputActionPerformed(java.awt.event.ActionEvent evt) {		//입력 버튼 선택
        sub1 = new PosSub1(POS_GUI.this);
		sub1.pack();
  	   	sub1.setLocation(500,300);
  	   	sub1.setVisible(true);
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void btSerch1ActionPerformed(java.awt.event.ActionEvent evt) {		//조회 버튼 선택
        func();
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public String func(){
    	String keyword = tfSerch.getText();			//Text Field에서 입력되어 있던 값을 가져옴
    	column_name = cbList.getSelectedIndex();	//ComboBox에서 선택된 인덱스 정보 저장
    	String sql ="";								//sql문을 저장할 변수
    	
    	if(keyword == null || keyword.trim().contentEquals("")) { column_name = 0; }	
    	//Text Field가 공백일 경우에는 전체 정보를 조회
    	
    	if(column_name == 6 &&  keyword.length() != 10) { column_name = 0; showMessage("2019/11/27 의 형태로 입력해주세요"); }	
    	//ComboBox에서 입고날짜 인덱스를 선택했는데 XXXX/XX/XX 형태가 아닐경우 전체정보조회
    	
    	switch(column_name) {
    	case 0: 	//전체상품 조회
    		sql = "select * from 상품 order by 상품코드"; break;
    	case 1: 	//Text Field값을 가지고 상품코드별 조회
    		keyword = "'%"+keyword+"%'"; sql = "select * from 상품 where "+colHeader[0]+" like "+keyword+" order by 상품코드";  break;
    	case 2: 	//Text Field값을 가지고 상품명별 조회
    		keyword = "'%"+keyword+"%'"; sql = "select * from 상품 where "+colHeader[1]+" like "+keyword+" order by 상품명"; break;
    	case 3: 	//Text Field값을 가지고 입고가 조회
    		sql = "select * from 상품 where "+colHeader[2]+" >= "+keyword+" order by 입고가"; break;
    	case 4: 	//Text Field값을 가지고 출고가 조회
    		sql = "select * from 상품 where "+colHeader[3]+" >= "+keyword+" order by 출고가"; break;
    	case 5: 	//Text Field값을 가지고 재고 조회
    		keyword = "'"+keyword+"'"; sql = "select * from 상품 where "+colHeader[4]+" >= "+keyword+" order by 재고"; break;
    	case 6: 	//Text Field값을 가지고 입고날짜 조회
    		sql = "select * from 상품 where "+colHeader[5]+" >= '"+keyword+"' order by 입고일"; break;
    	default : break;
    	}
    	sql_to_table(sql);		//sql문을 실행해줄 함수 호출
    	return sql;							//이 sql문이 필요한 경우
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
    public void sql_to_table(String sql) { 
    	ArrayList<VO> info = new ArrayList<>();
    	info = dao.show(sql);		//sql문 실행 후 결과를 배열의 형태로 저장하여 반환하는 show함수
    	if(info == null) { showMessage("키워드가 적절하지 않습니다."); }
		show_table(info);			//sql문 실행 결과를 가지고 있는 info배열을 테이블에 보여주기 위한 show_table함수 호출
    }//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void show_table(ArrayList<VO> info){
    	Object[][] data = new Object[info.size()][6];	//2차원배열 [배열의 길이][칼럼개수]
    	if(info.size() == 0) { showMessage("해당 정보가 없습니다."); }
    	for(int i=0; i<data.length; i++) {
    		VO temp = info.get(i);						//넘어온 배열의 값을 temp에 넣고 
    		data[i][0] = temp.getCode();
    		data[i][1] = temp.getName();
    		data[i][2] = temp.getIn_price();
    		data[i][3] = temp.getOut_price();
    		data[i][4] = temp.getStock();
    		data[i][5] = temp.getWdate(); 				//하나하나 꺼내서 2차원 배열 data에 값 저장
    	}
    	DefaultTableModel model = new DefaultTableModel(data, colHeader);	
    	//완성된 data와 칼럼정보 6개를 가지고 있는 colHeader를 생성자의 형태로 model변수생성
    	jTable.setModel(model);		
    	//테이블 세팅
    }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void showMessage(String str) {
        JOptionPane.showMessageDialog(this, str);	//보내준 문자열값을 창으로 띄어주는 함수
    }///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // date유형 19/11/30 형태로 밖에 조회불가

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        
        jBackground1 = new javax.swing.JPanel();
        jTab = new javax.swing.JTabbedPane();
        jBackground2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        lbTitle1 = new javax.swing.JLabel();
        cbList = new javax.swing.JComboBox<>();
        tfSerch = new javax.swing.JTextField();
        btSerch1 = new javax.swing.JButton();
        btInput = new javax.swing.JButton();
        jBackground3 = new javax.swing.JPanel();
        lbTitle2 = new javax.swing.JLabel();
        lbWavy = new javax.swing.JLabel();
        btToday = new javax.swing.JButton();
        lbPeriod = new javax.swing.JLabel();
        bt3Day = new javax.swing.JButton();
        bt7Day = new javax.swing.JButton();
        bt1Month = new javax.swing.JButton();
        bt3Month = new javax.swing.JButton();
        btTotal = new javax.swing.JButton();
        btSerch2 = new javax.swing.JButton();
        jGpanel = new javax.swing.JPanel();
        dcFrom = new com.toedter.calendar.JDateChooser();
        dcTo = new com.toedter.calendar.JDateChooser();
        lbLogoS = new javax.swing.JLabel();
        lbDate2 = new javax.swing.JLabel();
        lbSubTitle = new javax.swing.JLabel();
        texta1 = new javax.swing.JTextArea();
        texta2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jBackground1.setBackground(new java.awt.Color(81, 13, 117));

        jBackground2.setBackground(new java.awt.Color(180, 255, 229));

        jTable.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        jTable.setForeground(new java.awt.Color(81, 13, 117));
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
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
        ));
        jTable.setSelectionBackground(new java.awt.Color(180, 255, 229));
        jTable.setSelectionForeground(new java.awt.Color(81, 13, 117));
        jScrollPane2.setViewportView(jTable);

        lbTitle1.setFont(new java.awt.Font("맑은 고딕", 0, 24)); // NOI18N
        lbTitle1.setForeground(new java.awt.Color(81, 13, 117));
        lbTitle1.setText("| 재고관리");

        cbList.setBackground(new java.awt.Color(255, 255, 255));
        cbList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "전체", "상품코드", "상품명", "입고가", "출고가", "재고", "입고일" }));

        tfSerch.setBackground(new java.awt.Color(255, 255, 255));
        tfSerch.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        tfSerch.setForeground(new java.awt.Color(81, 13, 117));
        tfSerch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSerchActionPerformed(evt);
            }
        });

        btSerch1.setBackground(new java.awt.Color(81, 13, 117));
        btSerch1.setFont(new java.awt.Font("맑은 고딕", 1, 17)); // NOI18N
        btSerch1.setForeground(new java.awt.Color(255, 255, 255));
        btSerch1.setText("조회");
        btSerch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSerch1ActionPerformed(evt);
            }
        });

        btInput.setBackground(new java.awt.Color(255, 153, 153));
        btInput.setFont(new java.awt.Font("맑은 고딕", 1, 17)); // NOI18N
        btInput.setForeground(new java.awt.Color(81, 13, 117));
        btInput.setText("입력");
        btInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jBackground2Layout = new javax.swing.GroupLayout(jBackground2);
        jBackground2.setLayout(jBackground2Layout);
        jBackground2Layout.setHorizontalGroup(
            jBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jBackground2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfSerch, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btSerch1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btInput, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jBackground2Layout.setVerticalGroup(
            jBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBackground2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(lbTitle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBackground2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSerch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSerch1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btInput, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTab.addTab("재고관리", jBackground2);

        jBackground3.setBackground(new java.awt.Color(180, 255, 229));

        lbTitle2.setFont(new java.awt.Font("맑은 고딕", 0, 24)); // NOI18N
        lbTitle2.setForeground(new java.awt.Color(81, 13, 117));
        lbTitle2.setText("| 매출현황");

        lbWavy.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lbWavy.setForeground(new java.awt.Color(81, 13, 117));
        lbWavy.setText("~");

        btToday.setBackground(new java.awt.Color(255, 255, 255));
        btToday.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btToday.setForeground(new java.awt.Color(81, 13, 117));
        btToday.setText("오늘");
        btToday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTodayActionPerformed(evt);
            }
        });

        lbPeriod.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        lbPeriod.setForeground(new java.awt.Color(81, 13, 117));
        lbPeriod.setText("기간 |");

        bt3Day.setBackground(new java.awt.Color(255, 255, 255));
        bt3Day.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        bt3Day.setForeground(new java.awt.Color(81, 13, 117));
        bt3Day.setText("3일");
        bt3Day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt3DayActionPerformed(evt);
            }
        });

        bt7Day.setBackground(new java.awt.Color(255, 255, 255));
        bt7Day.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        bt7Day.setForeground(new java.awt.Color(81, 13, 117));
        bt7Day.setText("7일");
        bt7Day.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt7DayActionPerformed(evt);
            }
        });

        bt1Month.setBackground(new java.awt.Color(255, 255, 255));
        bt1Month.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        bt1Month.setForeground(new java.awt.Color(81, 13, 117));
        bt1Month.setText("1개월");
        bt1Month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt1MonthActionPerformed(evt);
            }
        });

        bt3Month.setBackground(new java.awt.Color(255, 255, 255));
        bt3Month.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        bt3Month.setForeground(new java.awt.Color(81, 13, 117));
        bt3Month.setText("3개월");
        bt3Month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt3MonthActionPerformed(evt);
            }
        });

        btTotal.setBackground(new java.awt.Color(255, 255, 255));
        btTotal.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btTotal.setForeground(new java.awt.Color(81, 13, 117));
        btTotal.setText("누적");
        btTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTotalActionPerformed(evt);
            }
        });

        btSerch2.setBackground(new java.awt.Color(81, 13, 117));
        btSerch2.setFont(new java.awt.Font("맑은 고딕", 1, 17)); // NOI18N
        btSerch2.setForeground(new java.awt.Color(255, 255, 255));
        btSerch2.setText("검색");
        btSerch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSerch2ActionPerformed(evt);
            }
        });

        jGpanel.setBackground(new java.awt.Color(255, 255, 255));
        jGpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(81, 13, 117)));
        
        texta1.setEditable(false);
        texta1.setColumns(20);
        texta1.setRows(5);
        jScrollPane1.setViewportView(texta1);

        texta2.setEditable(false);
        texta2.setColumns(20);
        texta2.setRows(5);
        jScrollPane3.setViewportView(texta2);

        javax.swing.GroupLayout jGpanelLayout = new javax.swing.GroupLayout(jGpanel);
        jGpanel.setLayout(jGpanelLayout);
        jGpanelLayout.setHorizontalGroup(
            jGpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jGpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jGpanelLayout.setVerticalGroup(
            jGpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jGpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jGpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                    .addGroup(jGpanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        dcFrom.setBackground(new java.awt.Color(255, 255, 255));
        dcFrom.setForeground(new java.awt.Color(81, 13, 117));

        dcTo.setBackground(new java.awt.Color(255, 255, 255));
        dcTo.setForeground(new java.awt.Color(81, 13, 117));

        javax.swing.GroupLayout jBackground3Layout = new javax.swing.GroupLayout(jBackground3);
        jBackground3.setLayout(jBackground3Layout);
        jBackground3Layout.setHorizontalGroup(
            jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBackground3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBackground3Layout.createSequentialGroup()
                        .addComponent(lbTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(701, 701, 701))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBackground3Layout.createSequentialGroup()
                        .addGroup(jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jGpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jBackground3Layout.createSequentialGroup()
                                .addComponent(lbPeriod)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btToday, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt3Day, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt7Day, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt1Month, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt3Month)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dcFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbWavy)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcTo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btSerch2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26))))
        );
        jBackground3Layout.setVerticalGroup(
            jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBackground3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbTitle2)
                .addGap(18, 18, 18)
                .addComponent(jGpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBackground3Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBackground3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(dcFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(bt3Month, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(bt1Month, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(bt7Day, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btToday, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbPeriod))
                                    .addComponent(bt3Day, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jBackground3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jBackground3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBackground3Layout.createSequentialGroup()
                                .addComponent(lbWavy)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(dcTo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jBackground3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSerch2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(453, 453, 453))
        );

        jTab.addTab("매출현황", jBackground3);

        lbLogoS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_down.png"))); // NOI18N

        lbDate2.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        lbDate2.setForeground(new java.awt.Color(180, 255, 229));
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        lbDate2.setText(Sld.showdate());
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        lbSubTitle.setFont(new java.awt.Font("맑은 고딕", 1, 12)); // NOI18N
        lbSubTitle.setForeground(new java.awt.Color(180, 255, 229));
        lbSubTitle.setText("재고관리 | 매출현황");

        javax.swing.GroupLayout jBackground1Layout = new javax.swing.GroupLayout(jBackground1);
        jBackground1.setLayout(jBackground1Layout);
        jBackground1Layout.setHorizontalGroup(
            jBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBackground1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTab)
                .addContainerGap())
            .addGroup(jBackground1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lbLogoS, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(lbSubTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbDate2)
                .addGap(29, 29, 29))
        );
        jBackground1Layout.setVerticalGroup(
            jBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBackground1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbDate2)
                        .addComponent(lbSubTitle))
                    .addComponent(lbLogoS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTab, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  

    private void btTodayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTodayActionPerformed
    	JFreeChart chart = new pos_graph().getChartToday();
    	texta1.setLayout(new java.awt.BorderLayout());
    	
    	ChartPanel CP = new ChartPanel(chart);
    	
    	texta1.add(CP,BorderLayout.CENTER);
    	texta1.validate();
    	
    	
  
    	texta2.selectAll();
    	texta2.replaceSelection("");
    	texta2.setText("매출금액:"+"   "+Integer.toString(sumToday)+"\n"); 
    	texta2.append("매출원가:"+"   "+Integer.toString(sumToday2)+"\n");
    	texta2.append("매출이익:"+"   "+Integer.toString(sumToday3));
    }//GEN-LAST:event_btTodayActionPerformed

    private void bt3DayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt3DayActionPerformed
		JFreeChart chart = new pos_graph().getChartThree();

		texta1.setLayout(new java.awt.BorderLayout());

		ChartPanel CP = new ChartPanel(chart);

		texta1.add(CP, BorderLayout.CENTER);
		texta1.validate();

		settexta();
    }//GEN-LAST:event_bt3DayActionPerformed
static ArrayList<Vector>pps = new ArrayList<>();
    
    
    public void settexta()
    {	
 	   Vector<Integer> a=new Vector<>();
 	   Vector<Integer> b=new Vector<>();
 	   Vector<Integer> c=new Vector<>();
 	   System.out.println("*-------------사이즈출력2   "+pps.size());
 	   a=pps.get(0);
 	   b=pps.get(1);
 	   c=pps.get(2);
 	  Calendar calt = Calendar.getInstance();
	  Calendar cal2t = Calendar.getInstance();
	  Calendar cal3t = Calendar.getInstance();
	
 	  calt.add(Calendar.DATE, 0);
 	  cal2t.add(Calendar.DATE, -1);
 	  cal3t.add(Calendar.DATE, -2);
  	 String yd1 = date.format(calt.getTime());
  	 String yd2 = date.format(cal2t.getTime());
  	 String yd3 = date.format(cal3t.getTime());
  	 System.out.println(yd1+yd2+yd3);
  	texta2.selectAll();
	texta2.replaceSelection("");
 	   int i;
 	  texta2.append("날짜          "+yd3+"  "+yd2+"     "+yd1+ "\n");
 	  texta2.append("매출금액:");
 	  for(i=2;i>=0;i--) {
  		 String tmp1=Integer.toString(a.get(i));
 		   texta2.append("       "+tmp1);
  	  }
 	  texta2.append("\n");
 	  texta2.append("매출원가:");
 	  for(i=2;i>=0;i--) {
 		 String tmp2=Integer.toString(b.get(i));
		   texta2.append("       "+tmp2);
 	  }
 	 texta2.append("\n");
	  texta2.append("매출이익:");
	  for(i=2;i>=0;i--) {
		 String tmp3=Integer.toString(c.get(i));
		   texta2.append("       "+tmp3);
	  }
 	  
    }
    private void bt7DayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt7DayActionPerformed
    	JFreeChart chart = new pos_graph().getChartSeven();
    	texta1.setLayout(new java.awt.BorderLayout());
    	
    	ChartPanel CP = new ChartPanel(chart);
    	
    	texta1.add(CP,BorderLayout.CENTER);
    	texta1.validate();
    	
    	settexta2();
    }//GEN-LAST:event_bt7DayActionPerformed
 static ArrayList<Vector> pps2 = new ArrayList<>();
    
    public void settexta2()
    {	
 	   Vector<Integer> a=new Vector<>();
 	   Vector<Integer> b=new Vector<>();
 	   Vector<Integer> c=new Vector<>();
 	   System.out.println("*-------------사이즈출력2   "+pps.size());
 	   a=pps2.get(0); 
 	   b=pps2.get(1);
 	   c=pps2.get(2);
 	  Calendar cal = Calendar.getInstance();
	  Calendar cal2 = Calendar.getInstance();
	  Calendar cal3 = Calendar.getInstance();
	  Calendar cal4 = Calendar.getInstance();
	  Calendar cal5 = Calendar.getInstance();
	  Calendar cal6 = Calendar.getInstance();
	  Calendar cal7 = Calendar.getInstance();
 	  cal.add(Calendar.DATE, 0);
 	  cal2.add(Calendar.DATE, -1);
 	  cal3.add(Calendar.DATE, -2);
 	  cal4.add(Calendar.DATE, -3);
 	  cal5.add(Calendar.DATE, -4);
 	  cal6.add(Calendar.DATE, -5);
 	  cal7.add(Calendar.DATE, -6);
  	 String yd = date.format(cal.getTime());
  	 String yd2 = date.format(cal2.getTime());
  	 String yd3 = date.format(cal3.getTime());
  	 String yd4 = date.format(cal4.getTime());
  	 String yd5 = date.format(cal5.getTime());
  	 String yd6 = date.format(cal6.getTime());
  	 String yd7 = date.format(cal7.getTime());
  	texta2.selectAll();
	texta2.replaceSelection("");
  	int i;
  	  texta2.append("날짜          "+yd7+"  "+yd6+"  "+yd5+"  "+yd4+"  "+yd3+"  "+yd2+"  "+yd+ "\n");
      texta2.append("매출금액:");
	  for(i=6;i>=0;i--) {
		 String tmp1=Integer.toString(a.get(i));
		   texta2.append("      "+tmp1);
	  }
	  texta2.append("\n");
	  texta2.append("매출원가:");
	  for(i=6;i>=0;i--) {
		 String tmp2=Integer.toString(b.get(i));
		   texta2.append("      "+tmp2);
	  }
	 texta2.append("\n");
	 texta2.append("매출이익:");
	  for(i=6;i>=0;i--) {
		 String tmp3=Integer.toString(c.get(i));
		   texta2.append("       "+tmp3);
	  }
    }
    private void bt1MonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt1MonthActionPerformed
    	JFreeChart chart = new pos_graph().getChartOneMt();
    	texta1.setLayout(new java.awt.BorderLayout());
    	
    	ChartPanel CP = new ChartPanel(chart);
    	
    	texta1.add(CP,BorderLayout.CENTER);
    	texta1.validate();
    	
    	
    	texta2.selectAll();
    	texta2.replaceSelection("");
    	texta2.setText("매출금액:"+"   "+Integer.toString(sumOneMt)+"\n"); 
    	texta2.append("매출원가:"+"   "+Integer.toString(sumOneMt2)+"\n");
    	texta2.append("매출이익:"+"   "+Integer.toString(sumOneMt3));
    	
    }//GEN-LAST:event_bt1MonthActionPerformed

    private void bt3MonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt3MonthActionPerformed
    	JFreeChart chart = new pos_graph().getChartThreeMt();
    	texta1.setLayout(new java.awt.BorderLayout());
    	
    	ChartPanel CP = new ChartPanel(chart);
    	
    	texta1.add(CP,BorderLayout.CENTER);
    	texta1.validate();
    	settexta3();
    }//GEN-LAST:event_bt3MonthActionPerformed
static ArrayList<Vector> pps3 = new ArrayList<>();
    
    public void settexta3()
    {	
 	   Vector<Integer> a=new Vector<>();
 	   Vector<Integer> b=new Vector<>();
 	   Vector<Integer> c=new Vector<>();
 	   System.out.println("*-------------사이즈출력2   "+pps.size());
 	   a=pps3.get(0); 
 	   b=pps3.get(1);
 	   c=pps3.get(2);
 	  Calendar cal = Calendar.getInstance();
	  Calendar cal2 = Calendar.getInstance();
	  Calendar cal3 = Calendar.getInstance();
	 
 	  cal.add(Calendar.MONTH, 0);
 	  cal2.add(Calendar.MONTH, -1);
 	  cal3.add(Calendar.MONTH, -2);
  	 String yd = date.format(cal.getTime());
  	 String yd2 = date.format(cal2.getTime());
  	 String yd3 = date.format(cal3.getTime());
  	 
  	texta2.selectAll();
	texta2.replaceSelection("");
 	   int i;
 	  texta2.append("날짜            "+yd3+"       "+yd2+"       "+yd+ "\n");
 	  texta2.append("매출금액:");
 	  for(i=2;i>=0;i--) {
  		 String tmp1=Integer.toString(a.get(i));
 		   texta2.append("       "+tmp1);
  	  }
 	  texta2.append("\n");
 	  texta2.append("매출원가:");
 	  for(i=2;i>=0;i--) {
 		 String tmp2=Integer.toString(b.get(i));
		   texta2.append("       "+tmp2);
 	  }
 	 texta2.append("\n");
	  texta2.append("매출이익:");
	  for(i=2;i>=0;i--) {
		 String tmp3=Integer.toString(c.get(i));
		   texta2.append("       "+tmp3);
	  }
 	   
    }
    private void btTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTotalActionPerformed
    	JFreeChart chart = new pos_graph().getChartAll();
    	texta1.setLayout(new java.awt.BorderLayout());
    	
    	ChartPanel CP = new ChartPanel(chart);
    	
    	texta1.add(CP,BorderLayout.CENTER);
    	texta1.validate();
    	
    	texta2.selectAll();
    	texta2.replaceSelection("");
    	texta2.setText("매출금액:"+"   "+Integer.toString(sumAll)+"\n"); 
    	texta2.append("매출원가:"+"   "+Integer.toString(sumAll2)+"\n");
    	texta2.append("매출이익:"+"   "+Integer.toString(sumAll3));
    }//GEN-LAST:event_btTotalActionPerformed

    private void btSerch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSerch2ActionPerformed
    	JFreeChart chart = new pos_graph().getChartCalendar();
    	texta1.setLayout(new java.awt.BorderLayout());
    	
    	ChartPanel CP = new ChartPanel(chart);
    	
    	texta1.add(CP,BorderLayout.CENTER);
    	texta1.validate();
    	calchoose p=new calchoose();
		calchoose2 p2=new calchoose2();
    	System.out.println(p.date);
    	System.out.println(p2.date2);
    	//setTitle(p.date+"~"+p2.date2);
    	
    	texta2.selectAll();
    	texta2.replaceSelection("");
    	texta2.setText("매출금액:"+"   "+Integer.toString(sumCalendar)+"\n"); 
    	texta2.append("매출원가:"+"   "+Integer.toString(sumCalendar2)+"\n");
    	texta2.append("매출이익:"+"   "+Integer.toString(sumCalendar3));
    }//GEN-LAST:event_btSerch2ActionPerformed

    private void tfSerchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSerchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSerchActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(POS_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(POS_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(POS_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(POS_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new POS_GUI().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt1Month;
    private javax.swing.JButton bt3Day;
    private javax.swing.JButton bt3Month;
    private javax.swing.JButton bt7Day;
    private javax.swing.JButton btInput;
    private javax.swing.JButton btSerch1;
    private javax.swing.JButton btSerch2;
    private javax.swing.JButton btToday;
    private javax.swing.JButton btTotal;
    private javax.swing.JComboBox<String> cbList;
    private static com.toedter.calendar.JDateChooser dcFrom;
    private static com.toedter.calendar.JDateChooser dcTo;
    private javax.swing.JPanel jBackground1;
    private javax.swing.JPanel jBackground2;
    private javax.swing.JPanel jBackground3;
    private javax.swing.JPanel jGpanel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTab;
    private javax.swing.JTable jTable;
    private javax.swing.JLabel lbDate2;
    private javax.swing.JLabel lbLogoS;
    private javax.swing.JLabel lbPeriod;
    private javax.swing.JLabel lbSubTitle;
    private javax.swing.JLabel lbTitle1;
    private javax.swing.JLabel lbTitle2;
    private javax.swing.JLabel lbWavy;
    private javax.swing.JTextField tfSerch;
    private javax.swing.JTextArea texta1;
    private javax.swing.JTextArea texta2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
