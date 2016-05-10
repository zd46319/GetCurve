package common;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import app.test;


public class ExportExcel {

	private String FilePath = null;

	public static void main(String[] args) {
		ExportExcel e = new ExportExcel();
		double[][] data = new double[30][6];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = 1;
			}
		}
		e.doExportNoTitle("20141101", "20141130", "ZYSHG", new String[] { "日期","O/N", "1W", "2W", "1M", "3M", "6M" }, data);
	}

	/**
	 * 导出到excel,没有title，首行是期限表头，类似于：（"日期","O/N","1W","2W","1M","3M","6M"）
	 * 
	 * @param StrstartDate  数据开始日期
	 * @param StrendDate   数据结束日期
	 * @param fileName  文件名
	 * @param titles  表头期限
	 * @param data  数据内容
	 */
	public void doExportNoTitle(String StrstartDate, String StrendDate,
			String fileName, String[] titles, double[][] data) {
		// titles={"日期","O/N","1W","2W","1M","3M","6M"};
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("data");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		// --
		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
		}
		/*
		 * HSSFCell cell = row.createCell(0); cell.setCellValue("日期");
		 * cell.setCellStyle(style); //-- cell = row.createCell(1);
		 * cell.setCellValue("O/N"); cell.setCellStyle(style); //-- cell =
		 * row.createCell(2); cell.setCellValue("1W"); cell.setCellStyle(style);
		 * //-- cell = row.createCell(3); cell.setCellValue("2W");
		 * cell.setCellStyle(style); //-- cell = row.createCell(4);
		 * cell.setCellValue("1M"); cell.setCellStyle(style); //-- cell =
		 * row.createCell(5); cell.setCellValue("3M"); cell.setCellStyle(style);
		 * //-- cell = row.createCell(6); cell.setCellValue("6M");
		 * cell.setCellStyle(style);
		 */

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		long startDate = Long.valueOf(StrstartDate);
		long endDate = Long.valueOf(StrendDate);
		int int_loop = CommonFunctions.daysSubtract(endDate, startDate);
		int sheetrow = 0;
		for (int i = 0; i < int_loop; i++) {
			if (data[i][0] != 0) {
				row = sheet.createRow(sheetrow + 1);
				// --
				row.createCell(0).setCellValue(
						CommonFunctions.pub_base_deadlineD(startDate, i));
				for (int j = 1; j < data[0].length + 1; j++) {
					row.createCell(j).setCellValue(data[i][j - 1]);
				}
				sheetrow++;
			}
		}
		// 第六步，将文件存到指定位置
		try {
			String file = fileName + "_" + startDate + "_" + endDate + ".xls";
			String filepath = test.class.getProtectionDomain().getCodeSource()
					.getLocation().getFile();
			if (filepath.substring(0, 1).equals("/")) {
				filepath = filepath.substring(1);
			}
			System.out.println(filepath + file);
			try {
				File path = new File(filepath);
				File dir = new File(path, file);
				if (!dir.exists()) {
					dir.createNewFile();
				}
			} catch (Exception e) {
				System.out.print("创建失败");
			}
			FileOutputStream fout = new FileOutputStream(filepath + file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 导出到excel,
	 * @param fileName 表名称
	 * @param sheetName sheet 名称
	 * @param titiles   表头
	 * @param data  数据内容
	 */
	public void doExportNoTitle(String fileName,String sheetName, String[] titles,String[][] data) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		// --
		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		int sheetrow = 0;
		for (int i = 0; i < data.length; i++) {
				row = sheet.createRow(sheetrow + 1);
				for (int j = 0; j < data[0].length; j++) {
					row.createCell(j).setCellValue(data[i][j]);
				}
				sheetrow++;
		}
		// 第六步，将文件存到指定位置
		try {
			String file = fileName + ".xls";
			String filepath = test.class.getProtectionDomain().getCodeSource()
					.getLocation().getFile();
			if (filepath.substring(0, 1).equals("/")) {
				filepath = filepath.substring(1);
			}
			System.out.println(filepath + file);
			try {
				File path = new File(filepath);
				File dir = new File(path, file);
				if (!dir.exists()) {
					dir.createNewFile();
				}
			} catch (Exception e) {
				System.out.print("创建失败");
			}
			FileOutputStream fout = new FileOutputStream(filepath + file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
