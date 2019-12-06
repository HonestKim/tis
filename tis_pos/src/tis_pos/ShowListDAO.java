package tis_pos;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;


public class ShowListDAO {

	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	public String showdate() {
		java.util.Date da = new java.util.Date();
		java.text.SimpleDateFormat date = new java.text.SimpleDateFormat("yyyy/MM/dd");
		return date.format(da);

	}
	
	public java.sql.Date calcul_day(String word)// ---------------- 유통기한 계산
		{
		try {
			String sql유통기한 = "select decode(substr(상품코드,1,1),'L',입고일+1024,'F',입고일+7,'P',입고일+180)"
					+ " \"만기일\" from 상품 where 상품코드 = ?";
			con=DBUtil.getCon();
			ps = con.prepareStatement(sql유통기한);
			ps.setString(1, word);
			rs = ps.executeQuery();
			
			java.sql.Date wdate_Tmp = null;
			while (rs.next()) {
				wdate_Tmp = rs.getDate(1);
			}
			System.out.println(wdate_Tmp);
			return wdate_Tmp;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		

	}
    public boolean checkDate(String word)
    {
    	java.util.Date aa=calcul_day(word);
    	if(aa==null) {return false;}
        java.util.Date today = new java.util.Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.format(today);
        long to=today.getTime();
        System.out.println(to);
        long ex=aa.getTime();
        System.out.println(ex);
        if(ex>to) {
           return false;
        }else{
           return true;
        }
     }
    

	public ArrayList<Dsc_VO> makeSoldInfo() {
		try {
			con = DBUtil.getCon();

			String sql = "select 날짜,c.고객번호,s.상품코드,p.상품명,출고가,팔린갯수 from 매출 s\r\n"
					+ "join 상품 p on s.상품코드=p.상품코드 join 고객 c on s.고객번호=c.고객번호\r\n"
					+ "where to_char(날짜,'yyyy-mm-dd') =to_char(sysdate,'yyyy-mm-dd')\r\n" + "order by 1 desc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ArrayList<Dsc_VO> arr = new ArrayList<Dsc_VO>();
			while (rs.next()) {
				String date = rs.getString("날짜");
				int customer_no = rs.getInt("고객번호");
				String product_no = rs.getString("상품코드");
				String product_name = rs.getString("상품명");
				int output_price = rs.getInt("출고가");
				int sales_vol = rs.getInt("팔린갯수");
				Dsc_VO dv = new Dsc_VO(date, customer_no, product_no, product_name, output_price, sales_vol);
				arr.add(dv);
			}
			System.out.println("너 실행 되냐");
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("너 실행 되냐 안되냐?");
			return null;
		} finally {
			close();
		}
	}// --------------------------------

	   public boolean cking_product_num(String word)
	    {
		   try
		   {
			   String sql="select 재고  from  상품 where 상품코드 =?";
		    	con=DBUtil.getCon();
		    	
		    	ps=con.prepareStatement(sql);
		    	ps.setString(1, word);
		    	rs=ps.executeQuery();
		    	int tmp=0;
		    	while(rs.next())
		    	{
		    		tmp=rs.getInt(1);
		    	}
		    	
		    	System.out.println("재고체크 "+tmp+"개 ");
		    	if(tmp>0)
		    	{
		    		return true;
		    	}
		    	else 
		    		return false;
		   }
		   catch(SQLException e)
		   {
			   e.printStackTrace();
			   return false;
		   }
		   finally
		   {
			   close();
		   }
	    }
	    
	
	public boolean ck_List(String word) {
		try {
			con = DBUtil.getCon();
			String sql존재하냐 = "select 상품코드 from 상품 where 상품코드 = ?";
			ps = con.prepareStatement(sql존재하냐);
			ps.setString(1, word);
			rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close();
		}
	}

	// ------선택 항목 삭제-------------------------
	public ArrayList<ShowListVO> deleteViewList(String pcode) {
		try {
			con = DBUtil.getCon();
			String sql선택삭제 = "delete from 결제화면 where 상품번호=?";
			String sql고객수체크="select count(*) from 결제화면 where 고객번호 =(select max(고객번호) from 고객)";
			String sql고객삭제 = "delete from 고객 where 고객번호 =(select max(고객번호) from 고객)";
		
			ps = con.prepareStatement(sql고객수체크);
			rs=ps.executeQuery();
			ArrayList<ShowListVO> arr=null;
			int tmp=-1;
			while(rs.next())
			{
				tmp=rs.getInt(1);
			}
			/////
			if(tmp>1)
			{System.out.println("선삭 낱개");
				ps = con.prepareStatement(sql선택삭제);
				ps.setString(1, pcode);
				ps.execute();
				arr= List_View();
			}
			else
			{
				System.out.println("선삭 마지막");
				ps = con.prepareStatement(sql선택삭제);
				ps.setString(1, pcode);
				ps.execute();
				arr= List_View();
				ps = con.prepareStatement(sql고객삭제);
				ps.execute();
				PosMain.ck = 1;
			}
			return arr;
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
	}// ----------------------------------------

	public void restore_product_num(String word,Connection con)
	throws SQLException
	{
	//	String sql=""
	}
	public void all_cancel() {
		try {
			con = DBUtil.getCon();
			String sql삭제 = "delete from 결제화면 where 고객번호 =(select max(고객번호) from 고객)";
			String sql고객삭제 = "delete from 고객 where 고객번호 =(select max(고객번호) from 고객)";
			ps = con.prepareStatement(sql삭제);
			ps.execute();
			ps = con.prepareStatement(sql고객삭제);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	

	public void gogag(Connection con) throws SQLException {

		String sql고객증가 = "insert into 고객 values(판상_SEQ.nextval)";
		ps = con.prepareStatement(sql고객증가);
		ps.execute(); // 고객증가 쿼리 실행
		System.out.println("고객증가");
		PosMain.ck = 0;

	}

	public int check_each(String word, Connection con) throws SQLException {
		System.out.println("중복체크 ");
		String sql중복체크 = "select count(*) from 결제화면 where 상품번호 = ? and 고객번호 =  (select max(고객번호) from 고객)";

		ps = con.prepareStatement(sql중복체크);
		ps.setString(1, word);
		rs = ps.executeQuery();
		int count_tmp = 1;
		while (rs.next()) {
			count_tmp = rs.getInt(1);
		}
		return count_tmp;

	}

	public ArrayList<String> basic_info(String word, Connection con) throws SQLException {
		System.out.println(" 작은기본정보");

		String sql기본정보 = "select 상품코드,상품명,출고가 from 상품 where 상품코드 = ?";
		ps = con.prepareStatement(sql기본정보);
		ps.setString(1, word);
		rs = ps.executeQuery();
		// 상품코드,상품명,출고가
		String tmp1 = null;
		String tmp2 = null;
		int tmp3 = 0;
		while (rs.next()) {
			tmp1 = rs.getString(1);
			tmp2 = rs.getString(2);
			tmp3 = rs.getInt(3);

		}
		ArrayList<String> ars = new ArrayList<>();
		ars.add(tmp1);
		ars.add(tmp2);
		ars.add(Integer.toString(tmp3));
		return ars;

	}

	public void show_sell_list(String word, Connection con) throws SQLException {

		Iterator<String> it = basic_info(word, con).iterator();
		String tmp1 = null;
		String tmp2 = null;
		int tmp3 = 0;
		while (it.hasNext()) {
			tmp1 = it.next();
			tmp2 = it.next();
			tmp3 = Integer.parseInt(it.next());
		}

		// 결제화면 (상품번호,상품명,가격,수량,유통기한,고객번호)
		String sql결제화면 = "insert into 결제화면 " + " values(?,?,?,1,?,(select max(고객번호) from 고객))";

		PreparedStatement ppp;

		ppp = con.prepareStatement(sql결제화면);
		ppp.setString(1, tmp1);
		ppp.setString(2, tmp2);
		ppp.setInt(3, tmp3);
		ppp.setDate(4, calcul_day(word)); // <-유통기한
		ppp.execute();
		
		int tm = ppp.getUpdateCount();
		System.out.println("기본정보 몆개 업뎃 되냐 : " + tm);

	}
		
	public int updateQuty(String product_num, int vol) {
		try {
			String sql현수량="select 수량 from 결제화면 where 상품번호 = ? and 고객번호 = (select max(고객번호) from 고객)";
			String sql수량변경 = "update 결제화면 set 수량=? where 상품번호=? and 고객번호 = (select max(고객번호) from 고객)";
			String sql감소or증가="update 상품 set 재고 = 재고 + ? where 상품코드 = ?";
			con = DBUtil.getCon();
			ps=con.prepareStatement(sql현수량);
			ps.setString(1, product_num);
			rs =ps.executeQuery();
			int tmp=0;
			while(rs.next())
			{
				tmp=rs.getInt(1);
			}
			//----------------- 현수량 tmp - vol
			ps = con.prepareStatement(sql수량변경);
			ps.setInt(1, vol);
			ps.setString(2, product_num);
			int n = ps.executeUpdate();
			System.out.println("수량 변경");	
			//////////////////////////////////
			int tmp2=tmp-vol;
			ps= con.prepareStatement(sql감소or증가);
			ps.setInt(1, tmp2);
			ps.setString(2, product_num);
			ps.execute();
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void up_default(String word, Connection con) throws SQLException {

		String sql수량추가 = "update 결제화면 set 수량=수량+1 where 상품번호=?and 고객번호 = (select max(고객번호) from 고객)";
		String sql재고감소 = "update 상품 set 재고 = 재고-1 where 상품코드=?";
		ps = con.prepareStatement(sql수량추가);///////// 업데이트
		System.out.println("수량추가");
		ps.setString(1, word);
		ps.execute();
		ps = con.prepareStatement(sql재고감소);///////// 업데이트
		System.out.println("재고감소 업뎃");
		ps.setString(1, word);
		ps.execute();
	}

	
	public ArrayList<ShowListVO> insertViewList(String word) {
		try {
			con = DBUtil.getCon();
			
			
			if (PosMain.ck == 1) {
				gogag(con); // <-고객증가;
			}
			if (check_each(word, con) == 0)// <-중복체크
			{
				show_sell_list(word, con);// 기본정보
			
			} else {
				up_default(word, con);// 업데이트
			}

			// List_View()
			ArrayList<ShowListVO> mp = List_View();
			return mp;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			close();
		}
	}

	public ArrayList<ShowListVO> List_View() throws SQLException {

		System.out.println("리스트뷰");
		String sql배열 = "select * from 결제화면 where 고객번호 = (select max(고객번호) from 고객)";
		ps = con.prepareStatement(sql배열);
		rs = ps.executeQuery();

		ArrayList<ShowListVO> mp = makeList(rs);

		return mp;

	}

	public String discount(String total) {
		System.out.println(total);
		int tmp = Integer.parseInt(total);
		tmp = (int) (tmp * 0.1);
		return Integer.toString(tmp);
	}

	public String total(String a, String b) {
		if (a.isEmpty()) {
			return b;
		} else {
			int tmp1 = Integer.parseInt(a);
			int tmp2 = Integer.parseInt(b);
			tmp1 = tmp2 - tmp1;
			String tmp3 = Integer.toString(tmp1);
			return tmp3;
		}

	}

	public String cal_all_sum() {
		try {
			con = DBUtil.getCon();
			String sql합 = "select  sum(가격합)" + "from (select (수량*가격) 가격합,고객번호 from 결제화면 where 고객번호 =?)";
			String sql고객번호 = "select max(고객번호) from 고객";
			ps = con.prepareStatement(sql고객번호);
			rs = ps.executeQuery();
			String tmp = "0";
			while (rs.next()) {
				tmp = rs.getString(1);
			}
			ps = con.prepareStatement(sql합);
			ps.setString(1, tmp);

			rs = ps.executeQuery();

			while (rs.next()) {
				tmp = rs.getString(1);
			}
			return tmp;

		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
		} finally {
			close();
		}
	}

	public ArrayList<ShowListVO> makeList(ResultSet rs) throws SQLException {
		System.out.println("makeList");

		ArrayList<ShowListVO> pp = new ArrayList<>();
		while (rs.next()) { // 상품번호,상품명,가격,수량,유통기한,고객번호)
			String pronum = rs.getString(1);
			String name = rs.getString(2);
			int price = rs.getInt(3);
			int count = rs.getInt(4);
			java.sql.Date wdate = rs.getDate(5);
			int customnum = rs.getInt(6);

			ShowListVO tm = new ShowListVO(pronum, name, price, count, wdate, customnum);
			pp.add(tm);
		}
		return pp;
	}

	public int insertAll() {
		try {
			con = DBUtil.getCon();
			String sql = "insert into 매출 (상품코드,팔린갯수,날짜,고객번호)"
					+ " (select 상품번호 ,수량 ,sysdate as \"날짜\", 고객번호 from 결제화면 where 고객번호 = (select max(고객번호) from 고객))";

			ps = con.prepareStatement(sql);
			// rs;
			int mm = ps.executeUpdate();
			return mm;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			close();
		}

	}

	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
		}
	}// --------------
}
