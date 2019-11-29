package tis_pos;
import java.sql.*;
public class ShowListVO {
	private String product_num;
	private String product_name;
	private int price;
	private int count;
	private java.sql.Date date_out;
	private int customer_num;
	
	public ShowListVO()
	{
		this(null,null,0,0,null,0);
	}
	public ShowListVO(String num,String name,int pri,int coun, Date datez,int conum)
	{
		this.product_num=num;
		this.product_name=name;
		this.price=pri;
		this.count=coun;
		this.date_out=datez;
		this.customer_num=conum;
	}
	public String getProduct_num() {
		return product_num;
	}
	public void setProduct_num(String product_num) {
		this.product_num = product_num;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public java.sql.Date getDate_out() {
		return date_out;
	}
	public void setDate_out(java.sql.Date date_out) {
		this.date_out = date_out;
	}
	public int getCustomer_num() {
		return customer_num;
	}
	public void setCustomer_num(int customer_num) {
		this.customer_num = customer_num;
	}
}
