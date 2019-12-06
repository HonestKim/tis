package tis_pos;

public class Dsc_VO {
	
	private String salesdate;
	private int customer_no;
	private String product_no;
	private String product_name;
	private int output_price;
	private int sales_vol;
	
	public Dsc_VO()
	{
		this(null,0,null,null,0,0);
	}
	
	public Dsc_VO(String salesdate, int customer_no, String product_no,
			String product_name, int output_price, int sales_vol) {
		super();
		this.salesdate = salesdate;
		this.customer_no = customer_no;
		this.product_no = product_no;
		this.product_name = product_name;
		this.output_price = output_price;
		this.sales_vol = sales_vol;
	}

	public String getSalesdate() {
		return salesdate;
	}

	public void setSalesdate(String salesdate) {
		this.salesdate = salesdate;
	}

	public int getcustomer_no() {
		return customer_no;
	}

	public void setcustomer_no(int customer_no) {
		this.customer_no = customer_no;
	}

	public String getproduct_no() {
		return product_no;
	}

	public void setproduct_no(String product_no) {
		this.product_no = product_no;
	}

	public String getproduct_name() {
		return product_name;
	}

	public void setproduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getoutput_price() {
		return output_price;
	}

	public void setoutput_price(int output_price) {
		this.output_price = output_price;
	}

	public int getsales_vol() {
		return sales_vol;
	}

	public void setsales_vol(int sales_vol) {
		this.sales_vol = sales_vol;
	}

}
