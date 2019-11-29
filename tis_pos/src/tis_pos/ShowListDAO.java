package tis_pos;
import java.sql.*;
import java.util.*;

public class ShowListDAO {

	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	public ArrayList<ShowListVO> insertViewList(String word)
	{
		
		try {
			
			String sql고객증가="insert into 고객 values(판상_SEQ.nextval)";
			String sql유통기한="select decode(substr(상품코드,1,1),'L',입고일+1024,'F',입고일+7,'P',입고일+180)"
					+ " \"만기일\" from 상품 where 상품코드 = ?";
				
			String sql결제화면="insert into 결제화면 (상품번호,상품명,가격) " + 
					"(select 상품코드,상품명,출고가 from 상품 where 상품코드 = ?)";
			
			String sql라스트추가="update 결제화면 set 수량=1 ,유통기한 = ? ,고객번호= (select max(고객번호) from 고객) where 상품번호=?";
			String sql배열 = "select * from 결제화면";
			String sql중복수량증가= "select";
			
			con=DBUtil.getCon();
			ps=con.prepareStatement(sql유통기한);
			ps.setString(1, word);
			rs=ps.executeQuery();
			java.sql.Date wdate_Tmp=null;
			while(rs.next())
			{
				wdate_Tmp=rs.getDate(1);
			}
			
			 System.out.println(wdate_Tmp);
			// -------------------------------------------------------------- 유통기한 계산
			 if(PosMain.ck==1) {
				 ps=con.prepareStatement(sql고객증가);
				 ps.execute(); //고객증가 쿼리 실행
				 System.out.println("고객증가");
				 PosMain.ck=0;
			 }
			 ps=con.prepareStatement(sql결제화면);
			 ps.setString(1, word);
			 ps.execute();
			 System.out.println("결제화면");
			 
			 ps=con.prepareStatement(sql라스트추가);/////////업데이트
			 ps.setDate(1, wdate_Tmp);
			 ps.setString(2, word);
			 ps.execute();
			 
			 System.out.println("리스트추가");
			 ps=con.prepareStatement(sql배열);
			 rs=ps.executeQuery();
			 ArrayList<ShowListVO> mp= makeList(rs);
			 System.out.println("입력");
			 ///////////한방에가냐?
			return mp;
			
			/*
			 * insert into 고객 values(판상_SEQ.nextval);
				insert into 결제화면 (상품번호,상품명,가격,유통기한)
				(select 상품코드,상품명,출고가,?? from 상품 where 상품코드 =???);
				update 결제화면 set 수량=1 ,고객번호=(select max(고객번호) from 고객) where 상품번호=???;
			 */
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
			
		}
		finally
		{
			close();
		}
	}
	public ArrayList<ShowListVO> makeList(ResultSet rs)
	throws SQLException
	{
		ArrayList<ShowListVO> pp = new ArrayList<>();
		while(rs.next())
		{
			String pronum=rs.getString(1);
			String name =rs.getString(2);
			int price=rs.getInt(3);
			int count=rs.getInt(4);
			java.sql.Date wdate=rs.getDate(5);
			int customnum=rs.getInt(6);
			
			ShowListVO tm=new ShowListVO(pronum,name,price,count,wdate,customnum);
			pp.add(tm);
		}
		return pp;
	}
	
	private void close() {
		try
		{
			if(rs!=null)rs.close();
			if(ps!=null)ps.close();
			if(con!=null)con.close();
		}
		catch(Exception e) {}	
	}//--------------
}
