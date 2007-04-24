package com.gtrobot.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;
import com.gtrobot.model.word.WordUnitEntry;
import com.gtrobot.service.word.WordEntryManager;
import com.gtrobot.service.word.WordUnitManager;

/**
 * 实现数据的导入处理。
 * 
 * @author Joey
 * 
 */
public class XlsWordExportor {
	protected static final Log log = LogFactory.getLog(XlsWordExportor.class);

	private WordEntryManager wordEntryManager = null;

	private WordUnitManager wordUnitManager = null;

	protected int wordLelvel = 0;

	public static final String INDEX_SHEET_NAME = "index";

	private HSSFCellStyle contentStyle;

	private HSSFCellStyle zhContentStyle;

	private HSSFCellStyle titleStyle;

	public XlsWordExportor() {
		GTRobotContextHelper.initApplicationContext();

		wordEntryManager = (WordEntryManager) GTRobotContextHelper
				.getBean("wordEntryManager");
		wordUnitManager = (WordUnitManager) GTRobotContextHelper
				.getBean("wordUnitManager");

		SessionFactory sessionFactory = (SessionFactory) GTRobotContextHelper
				.getBean("sessionFactory");
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.MANUAL);
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(session));
	}

	public void export(String filename) throws IOException {
		log.debug("Start of function of export to " + filename);
		export(new FileOutputStream(filename));
	}

	public void export(OutputStream out) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();

		titleStyle = createTitleStyle(workbook);
		contentStyle = createContentStyle(workbook);
		zhContentStyle = createZhContentStyle(workbook);

		List wordUnits = wordUnitManager.getWordUnits();
		generateIndexSheet(workbook, wordUnits);
		generateSheets(workbook, wordUnits);

		// write xls document
		workbook.write(out);
		out.flush();
		log.debug("End of function of write()");
	}

	private void generateIndexSheet(HSSFWorkbook workbook, List wordUnits) {
		// Create the index sheet
		HSSFSheet indexSheet = workbook.createSheet(INDEX_SHEET_NAME);

		int curRowCount = 0;
		// Write the title
		HSSFRow indexRow = indexSheet.createRow(curRowCount++);
		createStringCell(indexRow, 0, "Unit Id", titleStyle);
		createStringCell(indexRow, 1, "Unit Name", titleStyle);
		createStringCell(indexRow, 2, "Level", titleStyle);
		createStringCell(indexRow, 3, "Counts", titleStyle);
		// createStringCell(indexRow, 4, "Owner", titleStyle);

		// Write the contents of table list
		Iterator iterator = wordUnits.iterator();
		while (iterator.hasNext()) {
			WordUnit wordUnit = (WordUnit) iterator.next();

			indexRow = indexSheet.createRow(curRowCount++);

			createStringCell(indexRow, 0, wordUnit.getWordUnitId().toString(),
					contentStyle);
			HSSFCell cell = indexRow.createCell((short) 1);
			cell.setCellFormula("HYPERLINK( \"#" + wordUnit.getName()
					+ "!A1\", \"" + wordUnit.getName() + "\" )");
			cell.setCellStyle(contentStyle);

			createStringCell(indexRow, 2, "" + wordUnit.getLevel(),
					contentStyle);
			createStringCell(indexRow, 3, "" + wordUnit.getWordCount(),
					contentStyle);

			// if ( wordUnit.getOwner() == null) {
			// createStringCell(indexRow, 4, "", contentStyle);
			// } else {
			// createStringCell(indexRow, 4, wordUnit.getOwner(), contentStyle);
			// }
		}
	}

	private void generateSheets(HSSFWorkbook workbook, List wordUnits) {
		// Write the contents of table list
		Iterator iterator = wordUnits.iterator();
		while (iterator.hasNext()) {
			WordUnit wordUnit = (WordUnit) iterator.next();

			// Create the index sheet
			HSSFSheet sheet = workbook.createSheet(wordUnit.getName());
			generateSheet(sheet, wordUnit);
		}
	}

	private void generateSheet(HSSFSheet sheet, WordUnit wordUnit) {
		int curRowCount = 0;
		// Write the table title
		HSSFRow row = sheet.createRow(curRowCount++);
		createStringCell(row, 0, wordUnit.getWordUnitId().toString(),
				contentStyle);
		createStringCell(row, 1, "" + wordUnit.getName(), contentStyle);
		createStringCell(row, 2, "" + wordUnit.getLevel(), contentStyle);
		createStringCell(row, 3, "" + wordUnit.getWordCount(), contentStyle);
		HSSFCell cell = row.createCell((short) 5);
		cell.setCellFormula("HYPERLINK( \"#" + INDEX_SHEET_NAME
				+ "!A1\", \"-> Index\" )");
		cell.setCellStyle(contentStyle);

		// Write the title
		row = sheet.createRow(curRowCount++);
		createStringCell(row, 0, "Id", titleStyle);
		createStringCell(row, 1, "Word", titleStyle);
		createStringCell(row, 2, "Pronounce", titleStyle);
		createStringCell(row, 3, "PronounceType", titleStyle);
		createStringCell(row, 4, "WordType", titleStyle);
		createStringCell(row, 5, "Meaning", titleStyle);
		createStringCell(row, 6, "Sentence", titleStyle);
		createStringCell(row, 7, "Comments", titleStyle);

		List wordEntries = wordUnit.getWordEntries();
		Iterator iterator = wordEntries.iterator();
		while (iterator.hasNext()) {
			WordUnitEntry wordUnitEntry = (WordUnitEntry) iterator.next();
			WordEntry wordEntry = wordEntryManager.getWordEntry(wordUnitEntry
					.getPk().getWordEntryId());

			row = sheet.createRow(curRowCount++);

			createStringCell(row, 0, wordEntry.getWordEntryId().toString(),
					contentStyle);
			createStringCell(row, 1, wordEntry.getWord().toString(),
					contentStyle);
			createStringCell(row, 2, wordEntry.getPronounce(), contentStyle);
			createStringCell(row, 3, wordEntry.getPronounceType(), contentStyle);
			createStringCell(row, 4, wordEntry.getWordType(), zhContentStyle);
			createStringCell(row, 5, wordEntry.getMeaning(), zhContentStyle);
			createStringCell(row, 6, wordEntry.getSentence(), contentStyle);
			createStringCell(row, 7, wordEntry.getComments(), contentStyle);
		}
	}

	private HSSFCellStyle createTitleStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style;

		style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);// 字号
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
		font.setColor((short) HSSFColor.WHITE.index);
		font.setFontName("ＭＳ Ｐゴシック");
		style.setFont(font);

		return style;
	}

	private HSSFCellStyle createContentStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style;

		style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);// 字号
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
		font.setColor((short) HSSFColor.BLACK.index);
		font.setFontName("ＭＳ Ｐゴシック");
		style.setFont(font);

		return style;
	}

	private HSSFCellStyle createZhContentStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style;

		style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);// 字号
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
		font.setColor((short) HSSFColor.BLACK.index);
		font.setFontName("宋体");
		style.setFont(font);

		return style;
	}

	private HSSFCell createStringCell(HSSFRow row, int cellNumber,
			String value, HSSFCellStyle style) {
		return createStringCell(row, (short) cellNumber, value, style);
	}

	private HSSFCell createStringCell(HSSFRow row, short cellNumber,
			String value, HSSFCellStyle style) {
		if (value == null)
			value = "";
		HSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(new HSSFRichTextString(value));
		cell.setCellStyle(style);
		return cell;

	}

	public static final void main(String[] argv) throws IOException {
		XlsWordExportor importor = new XlsWordExportor();

		importor.export("metadata\\data\\AllWords.xls");

		System.exit(0);
	}
}
