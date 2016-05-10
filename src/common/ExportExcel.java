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
		e.doExportNoTitle("20141101", "20141130", "ZYSHG", new String[] { "����","O/N", "1W", "2W", "1M", "3M", "6M" }, data);
	}

	/**
	 * ������excel,û��title�����������ޱ�ͷ�������ڣ���"����","O/N","1W","2W","1M","3M","6M"��
	 * 
	 * @param StrstartDate  ���ݿ�ʼ����
	 * @param StrendDate   ���ݽ�������
	 * @param fileName  �ļ���
	 * @param titles  ��ͷ����
	 * @param data  ��������
	 */
	public void doExportNoTitle(String StrstartDate, String StrendDate,
			String fileName, String[] titles, double[][] data) {
		// titles={"����","O/N","1W","2W","1M","3M","6M"};
		// ��һ��������һ��webbook����Ӧһ��Excel�ļ�
		HSSFWorkbook wb = new HSSFWorkbook();
		// �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet
		HSSFSheet sheet = wb.createSheet("data");
		// ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short
		HSSFRow row = sheet.createRow((int) 0);
		// ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ
		// --
		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
		}
		/*
		 * HSSFCell cell = row.createCell(0); cell.setCellValue("����");
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

		// ���岽��д��ʵ������ ʵ��Ӧ������Щ���ݴ����ݿ�õ���
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
		// �����������ļ��浽ָ��λ��
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
				System.out.print("����ʧ��");
			}
			FileOutputStream fout = new FileOutputStream(filepath + file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * ������excel,
	 * @param fileName ������
	 * @param sheetName sheet ����
	 * @param titiles   ��ͷ
	 * @param data  ��������
	 */
	public void doExportNoTitle(String fileName,String sheetName, String[] titles,String[][] data) {
		// ��һ��������һ��webbook����Ӧһ��Excel�ļ�
		HSSFWorkbook wb = new HSSFWorkbook();
		// �ڶ�������webbook�����һ��sheet,��ӦExcel�ļ��е�sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short
		HSSFRow row = sheet.createRow((int) 0);
		// ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ
		// --
		for (int i = 0; i < titles.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
		}

		// ���岽��д��ʵ������ ʵ��Ӧ������Щ���ݴ����ݿ�õ���
		int sheetrow = 0;
		for (int i = 0; i < data.length; i++) {
				row = sheet.createRow(sheetrow + 1);
				for (int j = 0; j < data[0].length; j++) {
					row.createCell(j).setCellValue(data[i][j]);
				}
				sheetrow++;
		}
		// �����������ļ��浽ָ��λ��
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
				System.out.print("����ʧ��");
			}
			FileOutputStream fout = new FileOutputStream(filepath + file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
