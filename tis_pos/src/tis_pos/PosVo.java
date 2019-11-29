package tis_pos;

import java.sql.Date;

public class PosVo {
	private String product_idx;//상품코드
	private String product_name;//상품명
	private int input_price;//입고가
	private int output_price;//출고가
	private int product_count;//재고
	private java.sql.Date product_inputDate;//입고일
	public PosVo()
	{
		this("vo",null,0,0,0,null);
	}
	public PosVo(String idx,String name,int input_price,int output_price,int count, Date inputDate)
	{
		super();
		this.product_idx=idx;
		this.product_name=name;
		this.input_price=input_price;
		this.output_price=output_price;
		this.product_count=count;
		this.product_inputDate=inputDate;
		
	}
	
	
	public String getProduct_idx() {
		return product_idx;
	}
	public void setProduct_idx(String product_idx) {
		this.product_idx = product_idx;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getInput_price() {
		return input_price;
	}
	public void setInput_price(int input_price) {
		this.input_price = input_price;
	}
	public int getOutput_price() {
		return output_price;
	}
	public void setOutput_price(int output_price) {
		this.output_price = output_price;
	}
	public int getProduct_count() {
		return product_count;
	}
	public void setProduct_count(int product_count) {
		this.product_count = product_count;
	}
	public java.sql.Date getProduct_inputDate() {
		return product_inputDate;
	}
	public void setProduct_inputDate(java.sql.Date product_inputDate) {
		this.product_inputDate = product_inputDate;
	}
	
	
}
