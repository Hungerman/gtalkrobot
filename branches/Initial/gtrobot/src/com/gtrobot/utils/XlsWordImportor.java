package com.gtrobot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;

/**
 * 实现数据的导入处理。
 * 
 * @author Joey
 * 
 */
public class XlsWordImportor extends WordImportor {
	private List<String> exculdes = new ArrayList<String>();

	public void importFile(String fileName) {
		String sheetName = null;
		int totalRowCount = 0;
		int curRowCount = 0;
		short forstCellNum = 0;
		HSSFRow row = null;
		String unitName = null;

		try {
			log.debug("Start of function of read()");
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					fileName));

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				sheetName = workbook.getSheetName(i).trim().toUpperCase();
				if (exculdes != null
						&& exculdes.contains(sheetName.toLowerCase())) {
					continue;
				}

				totalRowCount = sheet.getLastRowNum();
				curRowCount = 0;
				row = sheet.getRow(curRowCount++);
				forstCellNum = 0;
				forstCellNum++; // Id
				unitName = getCellStringValue(row, forstCellNum++);
				log.info("\n=================>: " + unitName);
				WordUnit wordUnit = createWordUnit(unitName);
				wordUnit.setLevel(getCellLongValue(row, forstCellNum++));
				wordUnit.setWordCount(totalRowCount - 2);
				saveWordUnit(wordUnit);

				// Skip the title row
				curRowCount++;
				for (; curRowCount <= totalRowCount; curRowCount++) {
					row = sheet.getRow(curRowCount);
					forstCellNum = 0;
					forstCellNum++; // Id

					String word = getCellStringValue(row, forstCellNum++);
					String pronounce = getCellStringValue(row, forstCellNum++);
					String pronounceType = getCellStringValue(row,
							forstCellNum++);
					String wordType = getCellStringValue(row, forstCellNum++);
					String meaning = getCellStringValue(row, forstCellNum++);
					String sentence = getCellStringValue(row, forstCellNum++);
					String comments = getCellStringValue(row, forstCellNum++);

					WordEntry wordEntry = createWordEntry(word, pronounce,
							pronounceType, wordType, meaning, sentence,
							comments);
					saveWordEntry(wordUnit, wordEntry);
				}
				flush();
			}
		} catch (Exception e) {
			log.info("*****>" + fileName + " Sheet(" + sheetName + ":"
					+ curRowCount + ")");
			e.printStackTrace();
		}
		return;
	}

	/**
	 * @param row
	 * @param forstCellNum
	 * @return
	 */
	private String getCellStringValue(HSSFRow row, short cellNum) {
		HSSFCell cell = row.getCell(cellNum);
		if (cell == null)
			return null;

		String result = "**";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			HSSFRichTextString richStringCellValue = cell
					.getRichStringCellValue();
			if (richStringCellValue == null)
				result = null;
			result = richStringCellValue.getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			result = "" + cell.getNumericCellValue();
			break;
		}

		if (result != null)
			result = result.trim();
		return result;
	}

	private long getCellLongValue(HSSFRow row, short cellNum) {
		String cellStringValue = getCellStringValue(row, cellNum);
		return Long.parseLong(cellStringValue);
	}

	public static final void main(String[] argv) {
		XlsWordImportor importor = new XlsWordImportor();

		importFile("metadata\\data\\newwords\\", importor, 4);

		System.exit(0);
	}

	private static void importFile(String filePath, XlsWordImportor importor,
			int wordLevel) {
		importor.wordLelvel = wordLevel;
		importor.exculdes.add("index");

		File dir = new File(filePath);
		String[] filenames = dir.list();
		for (int i = 0; i < filenames.length; i++) {
			importor.importFile(filePath + filenames[i]);
		}
	}
}
