package tis_pos;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import tis_pos.POS_GUI.calchoose;
import tis_pos.POS_GUI.calchoose2;

public class pos_graph {
	static Connection con;
	static PreparedStatement ps;
	static ResultSet rs;
	static Statement stmt;
	static int sumToday,sumToday2,sumToday3;
	static int sumThree,sumThree2,sumThree3;
	static int sumSeven,sumSeven2,sumSeven3;
	static int sumOneMt,sumOneMt2,sumOneMt3;
	static int sumThreeMt,sumThreeMt2,sumThreeMt3;
	static int sumAll,sumAll2,sumAll3;
	static int sumCalendar,sumCalendar2,sumCalendar3;


	JFreeChart chart;
	SimpleDateFormat date= new SimpleDateFormat("yy/MM/dd");
		  Calendar cal = Calendar.getInstance();
		  Calendar cal2 = Calendar.getInstance();
		  Calendar cal3 = Calendar.getInstance();
		  Calendar cal4 = Calendar.getInstance();
		  Calendar cal5 = Calendar.getInstance();
		  Calendar cal6 = Calendar.getInstance();
		  Calendar cal7 = Calendar.getInstance();

	//static class p_graph{
		//getDataSetToday p = new getDataSetToday();
	//}
	// getChart() 메서드. Chart 를 만들어서 리턴함

	// getDataSet() 메서드. dataset 을 만들어서 리턴함 - getChart() 내에서 사용
	public void chart() {
		chart.setBackgroundPaint(java.awt.Color.white);
		chart.setTitle("매출 그래프");
		chart.getTitle().setFont(new Font("돋움", Font.BOLD, 15));
		// 범례
		chart.getLegend().setItemFont(new Font("돋움", Font.PLAIN, 10));
		CategoryPlot plot = chart.getCategoryPlot();
		Font font = plot.getDomainAxis().getLabelFont();
		// X축 라벨
		plot.getDomainAxis().setLabelFont(new Font("돋움", font.getStyle(), font.getSize()));
		// X축 도메인
		plot.getDomainAxis().setTickLabelFont(new Font("돋움", font.getStyle(), 10));

		font = plot.getRangeAxis().getLabelFont();
		// Y축 라벨
		plot.getRangeAxis().setLabelFont(new Font("돋움", font.getStyle(), font.getSize()));
		// Y축 범위
		plot.getRangeAxis().setTickLabelFont(new Font("돋움", font.getStyle(), 10));

	}

	public JFreeChart getChartToday() {
		
		chart = ChartFactory.createBarChart(getClass().getName(), // title
				"", // categoryAxisLabel
				"", // valueAxisLabel
				getDataSetToday(), // dataset
				PlotOrientation.VERTICAL, // orientation
				true, // legend
				false, // tooltips
				false); // url
		chart();
		return chart;
	}

	private DefaultCategoryDataset getDataSetToday() {
		
		cal.add(Calendar.DATE, 0);
		String yd = date.format(cal.getTime());
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String category1 = "매출 금액";
		String category2 = "매출 원가";
		String category3 = "매출 이익";
		String test1 = yd;

		try {
			con = DBUtil.getCon();
			String sql = "select sum(출고가*팔린갯수),sum(입고가*팔린갯수),(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))"
					+ " from 매출 s join 상품 p on s.상품코드 = p.상품코드 where to_char(날짜, 'yy/mm/dd')= to_char(sysdate, 'yy/mm/dd')";         
	          

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
			    sumToday = rs.getInt("sum(출고가*팔린갯수)");
				sumToday2 = rs.getInt("sum(입고가*팔린갯수)");
				sumToday3 = rs.getInt("(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))");
				System.out.println(sumToday);
				dataSet.addValue(sumToday, category1, test1);
				dataSet.addValue(sumToday2, category2, test1);
				dataSet.addValue(sumToday3, category3, test1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return dataSet;
	}

	public JFreeChart getChartThree() {
		chart = ChartFactory.createBarChart(getClass().getName(), // title
				"", // categoryAxisLabel
				"", // valueAxisLabel
				getDataSetThree(), // dataset
				PlotOrientation.VERTICAL, // orientation
				true, // legend
				false, // tooltips
				false); // url
		chart();
		return chart;
	}

	private DefaultCategoryDataset getDataSetThree() {

	    cal.add(Calendar.DATE, 0);
		cal2.add(Calendar.DATE, -1);
		cal3.add(Calendar.DATE, -2);
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		 String yd = date.format(cal.getTime());
		 String yd2 = date.format(cal2.getTime());
		 String yd3 = date.format(cal3.getTime());
	
		String category1 = "매출 금액";
		String category2 = "매출 원가";
		String category3 = "매출 이익";
		String test1 = yd;
		String test2 = yd2;
		String test3 = yd3;

		try {

			Vector<Integer> v = new Vector<>(5, 5);
			Vector<Integer> v2 = new Vector<>(5, 5);
			Vector<Integer> v3 = new Vector<>(5, 5);

			for (int s = 0; s < 3; s++) {
				con = DBUtil.getCon(); // sum(in_price)
				String sql = "select sum(출고가*팔린갯수),sum(입고가*팔린갯수),(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))"
						+ " from 매출 s join 상품 p on s.상품코드 = p.상품코드 "
						+ " where to_char(날짜, 'yy/mm/dd')=to_char(sysdate-" + s + ", 'yy/mm/dd')";        

				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();

				System.out.println(v);
				while (rs.next()) {
					sumThree = rs.getInt("sum(출고가*팔린갯수)");
					sumThree2 = rs.getInt("sum(입고가*팔린갯수)");
					sumThree3 = rs.getInt("(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))");
					System.out.println("매출금액" + sumThree);
					System.out.println("매출원가" + sumThree2);
					System.out.println("매출이익" + sumThree3);
					System.out.println("============");

					v.add(sumThree);
					v2.add(sumThree2);
					v3.add(sumThree3);

					System.out.println(v);
					Integer val = (Integer) v.get(0);
					System.out.println(val);
					System.out.println("v.size()=" + v.size());
				}
				POS_GUI.pps.add(v);
				POS_GUI.pps.add(v2);
				POS_GUI.pps.add(v3);
			}

			Vector<String> v4 = new Vector<>(5, 5);
			v4.add(test1);
			v4.add(test2);
			v4.add(test3);
			for (int i = 2; i >= 0; i--) {
				dataSet.addValue(v.get(i), category1, v4.get(i));
				dataSet.addValue(v2.get(i), category2, v4.get(i));
				dataSet.addValue(v3.get(i), category3, v4.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return dataSet;
	}

	public JFreeChart getChartSeven() {
		chart = ChartFactory.createBarChart(getClass().getName(), // title
				"", // categoryAxisLabel
				"", // valueAxisLabel
				getDataSetSeven(), // dataset
				PlotOrientation.VERTICAL, // orientation
				true, // legend
				false, // tooltips
				false); // url
		chart();
		return chart;
	}

	private DefaultCategoryDataset getDataSetSeven() {
		
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

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String category1 = "매출 금액";
		String category2 = "매출 원가";
		String category3 = "매출 이익";
		String test1 = yd;
		String test2 = yd2;
		String test3 = yd3;
		String test4 = yd4;
		String test5 = yd5;
		String test6 = yd6;
		String test7 = yd7;
		try {

			Vector<Integer> v = new Vector<>(5, 5);
			Vector<Integer> v2 = new Vector<>(5, 5);
			Vector<Integer> v3 = new Vector<>(5, 5);

			for (int s = 0; s < 7; s++) {
				con = DBUtil.getCon(); // sum(in_price)
				String sql = "select sum(출고가*팔린갯수),sum(입고가*팔린갯수),(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))"
						+ " from 매출 s join 상품 p on s.상품코드 = p.상품코드 "
						+ " where to_char(날짜, 'yy/mm/dd')=to_char(sysdate-" + s + ", 'yy/mm/dd')"; 

				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();

				System.out.println(v);
				while (rs.next()) {
					sumSeven = rs.getInt("sum(출고가*팔린갯수)");
					sumSeven2 = rs.getInt("sum(입고가*팔린갯수)");
					sumSeven3 = rs.getInt("(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))");
					System.out.println("매출금액" + sumSeven);
					System.out.println("매출원가" + sumSeven2);
					System.out.println("매출이익" + sumSeven3);
					System.out.println("============");

					v.add(sumSeven);
					v2.add(sumSeven2);
					v3.add(sumSeven3);

//					System.out.println(v);
//					Integer val = (Integer) v.get(0);
//					System.out.println(val);
//					System.out.println("v.size()=" + v.size());
					
				}
				POS_GUI.pps2.add(v);
				POS_GUI.pps2.add(v2);
				POS_GUI.pps2.add(v3);
	
		
			}

			Vector<String> v4 = new Vector<>(5, 5);
			v4.add(test1);
			v4.add(test2);
			v4.add(test3);
			v4.add(test4);
			v4.add(test5);
			v4.add(test6);
			v4.add(test7);
			for (int i = 6; i >= 0; i--) {
				dataSet.addValue(v.get(i), category1, v4.get(i));
				dataSet.addValue(v2.get(i), category2, v4.get(i));
				dataSet.addValue(v3.get(i), category3, v4.get(i));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return dataSet;

	}

	public JFreeChart getChartOneMt() {
		
		chart = ChartFactory.createBarChart(getClass().getName(), // title
				"", // categoryAxisLabel
				"", // valueAxisLabel
				getDataSetOneMt(), // dataset
				PlotOrientation.VERTICAL, // orientation
				true, // legend
				false, // tooltips
				false); // url
		chart();
		return chart;
	}

	private DefaultCategoryDataset getDataSetOneMt() {
		
		cal.add(Calendar.MONTH, -1);
		String yd = date.format(cal.getTime());

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String category1 = "매출 금액";
		String category2 = "매출 원가";
		String category3 = "매출 이익";
		String test1 = yd;

		try {
			con = DBUtil.getCon();
			String sql = "select sum(출고가*팔린갯수),sum(입고가*팔린갯수),(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))"
					+ " from 매출 s join 상품 p on s.상품코드=p.상품코드 "
					+ " where 날짜 between add_months(sysdate,-1) and to_char(sysdate, 'yy/mm/dd')";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				sumOneMt = rs.getInt("sum(출고가*팔린갯수)");
				sumOneMt2 = rs.getInt("sum(입고가*팔린갯수)");
				sumOneMt3 = rs.getInt("(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))");
				System.out.println(sumOneMt);
				dataSet.addValue(sumOneMt, category1, test1);
				dataSet.addValue(sumOneMt2, category2, test1);
				dataSet.addValue(sumOneMt3, category3, test1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return dataSet;
	}

	public JFreeChart getChartThreeMt() {
		chart = ChartFactory.createBarChart(getClass().getName(), // title
				"", // categoryAxisLabel
				"", // valueAxisLabel
				getDataSetThreeMt(), // dataset
				PlotOrientation.VERTICAL, // orientation
				true, // legend
				false, // tooltips
				false); // url
		chart();
		return chart;
	}

	private DefaultCategoryDataset getDataSetThreeMt() {
		
		cal.add(Calendar.MONTH, 0);
		cal2.add(Calendar.MONTH, -1);
		cal3.add(Calendar.MONTH, -2);
		String yd = date.format(cal.getTime());
		String yd2 = date.format(cal2.getTime());
		String yd3 = date.format(cal3.getTime());

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String category1 = "매출 금액";
		String category2 = "매출 원가";
		String category3 = "매출 이익";
		String test1 = yd;
		String test2 = yd2;
		String test3 = yd3;

		try {

			Vector<Integer> v = new Vector<>(5, 5);
			Vector<Integer> v2 = new Vector<>(5, 5);
			Vector<Integer> v3 = new Vector<>(5, 5);
			
		
//			String [] tmp = new String [3];
//			tmp[0]="add_months(sysdate ,-2)";
//			tmp[1]="add_months(sysdate ,-1)";
//			tmp[2]="sysdate";

			for (int s = -3; s <0; s++) {
				con = DBUtil.getCon(); // sum(in_price)
				String sql = "select sum(출고가*팔린갯수),sum(입고가*팔린갯수),(sum(출고가*팔린갯수)-sum(입고가*팔린갯수)) "
						+ "from 매출 s join 상품 p on s.상품코드=p.상품코드 "
						+ "WHERE 날짜 between add_months(sysdate ,"+s+") and add_months(sysdate ,"+(s+1)+")";

			
				ps = con.prepareStatement(sql);
//				if(s==-3) {
//					ps.setString(1, "add_months(sysdate ,-2)");
//				}
//				else if(s==-2) {
//					ps.setString(1, "19/11/05");
//				}
//				else if(s==-1) {
//					ps.setString(1, "19/12/05");
//				}

				rs = ps.executeQuery();
				
				System.out.println(v);
				while (rs.next()) {
					sumThreeMt = rs.getInt("sum(출고가*팔린갯수)");
					sumThreeMt2 = rs.getInt("sum(입고가*팔린갯수)");
					sumThreeMt3 = rs.getInt("(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))");
					System.out.println("매출금액" + sumThreeMt);
					System.out.println("매출원가" + sumThreeMt2);
					System.out.println("매출이익" + sumThreeMt3);
					System.out.println("============");

					v.add(sumThreeMt);
					v2.add(sumThreeMt2);
					v3.add(sumThreeMt3);

					System.out.println(v);
					Integer val = (Integer) v.get(0);
					System.out.println(val);
					System.out.println("v.size()=" + v.size());
				}

				POS_GUI.pps3.add(v);
				POS_GUI.pps3.add(v2);
				POS_GUI.pps3.add(v3);
			}
			Vector<String> v4 = new Vector<>(5, 5);
			v4.add(test1);
			v4.add(test2);
			v4.add(test3);
			for (int i = 2; i >= 0; i--) {
				dataSet.addValue(v.get(i), category1, v4.get(i));
				dataSet.addValue(v2.get(i), category2, v4.get(i));
				dataSet.addValue(v3.get(i), category3, v4.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}

		return dataSet;
	}

	public JFreeChart getChartAll() {
		chart = ChartFactory.createBarChart(getClass().getName(), // title
				"", // categoryAxisLabel
				"", // valueAxisLabel
				getDataSetAll(), // dataset
				PlotOrientation.VERTICAL, // orientation
				true, // legend
				false, // tooltips
				false); // url
		chart();
		return chart;
	}

	private DefaultCategoryDataset getDataSetAll() {

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String category1 = "매출 금액";
		String category2 = "매출 원가";
		String category3 = "매출 이익";
		String test1 = "누 적";

		try {
			con = DBUtil.getCon();
			String sql =" select sum(출고가*팔린갯수),sum(입고가*팔린갯수),(sum(출고가*팔린갯수)-sum(입고가*팔린갯수)) "
					+ " from 매출 s join 상품 p on s.상품코드=p.상품코드 "
					+ " where to_char(날짜, 'yy/mm/dd') <= to_char(sysdate, 'yy/mm/dd')";  


			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				sumAll = rs.getInt("sum(출고가*팔린갯수)");
				sumAll2 = rs.getInt("sum(입고가*팔린갯수)");
				sumAll3 = rs.getInt("(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))");
				System.out.println(sumAll);
				dataSet.addValue(sumAll, category1, test1);
				dataSet.addValue(sumAll2, category2, test1);
				dataSet.addValue(sumAll3, category3, test1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
		return dataSet;
	}

	public JFreeChart getChartCalendar() {
		chart = ChartFactory.createBarChart(getClass().getName(), // title
				"", // categoryAxisLabel
				"", // valueAxisLabel
				getDataSetCalendar(), // dataset
				PlotOrientation.VERTICAL, // orientation
				true, // legend
				false, // tooltips
				false); // url
		chart();
		return chart;
	}

	private DefaultCategoryDataset getDataSetCalendar() {
		calchoose p = new calchoose();
		calchoose2 p2 = new calchoose2();
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String category1 = "매출 금액";
		String category2 = "매출 원가";
		String category3 = "매출 이익";
		String test1 = p.date+"~"+p2.date2;

		try {
			con = DBUtil.getCon();
			String sql = "select sum(출고가*팔린갯수),sum(입고가*팔린갯수),(sum(출고가*팔린갯수)-sum(입고가*팔린갯수)) "
					+ " from 매출 s join 상품 p on s.상품코드=p.상품코드"
					+ "  where 날짜 between ? and ?";
		
			ps = con.prepareStatement(sql);
			ps.setString(1, p.date);
			ps.setString(2, p2.date2);
			rs = ps.executeQuery();

			while (rs.next()) {
				sumCalendar = rs.getInt("sum(출고가*팔린갯수)");
				sumCalendar2 = rs.getInt("sum(입고가*팔린갯수)");
				sumCalendar3 = rs.getInt("(sum(출고가*팔린갯수)-sum(입고가*팔린갯수))");
				System.out.println(sumCalendar);

				dataSet.addValue(sumCalendar, category1, test1);
				dataSet.addValue(sumCalendar2, category2, test1);
				dataSet.addValue(sumCalendar3, category3, test1);

			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			dbClose();

		}
		return dataSet;

	}

	public static void dbClose() {

		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
		} catch (Exception e) {

		}
	}

	// 메인 메서드. 챠트 프레임을 보여줌
}
