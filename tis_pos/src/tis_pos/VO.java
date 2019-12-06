package tis_pos;

import java.sql.Date;

public class VO {
	private String code;
	private String name;
	private int in_price;
	private int out_price;
	private int stock;
	private java.sql.Date wdate;
	
	public VO() {
		this(null, null, 0, 0, 0, null);
	}
	public VO(String code, String name, int in_price, int out_price, int stock, Date wdate) {
		super();
		this.code = code;
		this.name = name;
		this.in_price = in_price;
		this.out_price = out_price;
		this.stock = stock;
		this.wdate = wdate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIn_price() {
		return in_price;
	}
	public void setIn_price(int in_price) {
		this.in_price = in_price;
	}
	public int getOut_price() {
		return out_price;
	}
	public void setOut_price(int out_price) {
		this.out_price = out_price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public java.sql.Date getWdate() {
		return wdate;
	}
	public void setWdate(java.sql.Date wdate) {
		this.wdate = wdate;
	}

}
