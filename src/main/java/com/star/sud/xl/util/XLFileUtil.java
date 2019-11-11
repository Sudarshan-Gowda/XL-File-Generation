/**
 * 
 */
package com.star.sud.xl.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.star.sud.xl.dto.UserDetails;

/**
 * @author Sudarshan
 *
 */
public class XLFileUtil {

	public static String writeExcelFile(String[] columns, List<UserDetails> userDetails) throws Exception {

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook();
		// new HSSFWorkbook() for generating `.xls` file
		CreationHelper createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Employee");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;
		for (UserDetails userDetail : userDetails) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(String.valueOf(System.nanoTime()));

			row.createCell(1).setCellValue(userDetail.getUserFirstName());

			row.createCell(2).setCellValue(userDetail.getUserLastName());

			Cell dateOfBirthCell = row.createCell(3);
			dateOfBirthCell.setCellValue(Calendar.getInstance().getTime());
			dateOfBirthCell.setCellStyle(dateCellStyle);

			row.createCell(4).setCellValue(userDetail.getUserContact());

			row.createCell(5).setCellValue(userDetail.getUserAge());

			row.createCell(6).setCellValue(userDetail.getUserAddress());

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		File temp = File.createTempFile("XL_File_Creation_User_Details", ".xlsx");

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(temp);
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
		return temp.getAbsolutePath();

	}

}
