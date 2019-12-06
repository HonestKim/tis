package tis_pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;

public class DAO {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	private void close() {
		try {
			if(rs != null) { rs.close(); }
			if(ps != null) { ps.close(); }
			if(con != null) { con.close(); }
		} 
		catch (Exception e) { e.getStackTrace(); }
	}////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ArrayList<VO> make_sql(ResultSet rs) throws SQLException{
		ArrayList<VO> table = new ArrayList<>();
		while(rs.next()) {
			String code = rs.getString(1);
			String name = rs.getString(2);
			int in_price = rs.getInt(3);
			int out_price = rs.getInt(4);
			int stock = rs.getInt(5);
			java.sql.Date wdate = rs.getDate(6);
			VO temp = new VO(code, name, in_price, out_price, stock, wdate);
			table.add(temp);
		}
		return table;
	}///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<VO> show(String sql){
		try {
			ArrayList<VO> table = new ArrayList<>();
			con = DBUtil.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			table = make_sql(rs);
			return table;
		}
		catch(SQLSyntaxErrorException e) { return null; }
		catch(Exception e) { e.getStackTrace(); return null; }
		finally { close(); }
	}/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int delete_update(String sql) {
		try {
			con=DBUtil.getCon();
			ps = con.prepareStatement(sql);
			int n = ps.executeUpdate();
			return n;
		}
		catch(Exception e) { e.getStackTrace(); return 0; }
		finally { close(); }
		
	}//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String sql_write(String key, int col) {
		try {
			
			String sql = "select * from 상품 where 상품코드 = '"+key+"'";
			String str ="";
			con=DBUtil.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String code = rs.getString(1);
				String name = rs.getString(2);
				String in_price = rs.getString(3);
				String out_price = rs.getString(4);
				String stock = rs.getString(5);
				java.sql.Date wdate = rs.getDate(6);
				if( col == 0 ) { str = code; }
				else if( col == 1 ) { str = name; }
				else if( col == 2 ) { str = in_price; }
				else if( col == 3 ) { str = out_price; }
				else if( col == 4 ) { str = stock; }
				else if( col == 5 ) { str = wdate+""; }
				sql = code+" / "+name+" / "+in_price+" / "+out_price+" / "+stock+" / "+wdate;
			}
			if(col==0 || col<=5) { return str; }
			else { return sql; }
			
		}
		catch(Exception e) { e.getStackTrace(); return "정보 가져오기 실패"; }
		finally { close(); }
		
	}/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int insert(ArrayList<VO> input_table) {
		
		try {
			con=DBUtil.getCon();
			int cnt=0;
			
			for(VO value:input_table) {
		    
				String sql= "insert into 상품 values( ?, ?, ?, ?, ?, ?)";
			
				ps=con.prepareStatement(sql);
				
				ps.setString(1, value.getCode());
				ps.setString(2, value.getName());
				ps.setInt(3, value.getIn_price());
				ps.setInt(4,  value.getOut_price());
				ps.setInt(5, value.getStock());
				ps.setDate(6, value.getWdate());
				
				int n=ps.executeUpdate();
				cnt++;
			}
			return  cnt;
		}
		catch(Exception e) { e.printStackTrace(); return -1; }
		finally { close(); }
	}/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int check_code(String key) {

		try {
			int value=0;
			con = DBUtil.getCon();
			ps = con.prepareStatement("select 상품코드 from 상품");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String code = rs.getString(1);
				if(key.equals(code)) { value = 1; }
			}
			return value;
		}
		catch(Exception e) { e.printStackTrace(); return -1; }
		finally { close(); }
		
	}//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
