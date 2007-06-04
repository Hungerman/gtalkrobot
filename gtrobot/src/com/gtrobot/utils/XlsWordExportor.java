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

        this.wordEntryManager = (WordEntryManager) GTRobotContextHelper
                .getBean("wordEntryManager");
        this.wordUnitManager = (WordUnitManager) GTRobotContextHelper
                .getBean("wordUnitManager");

        final SessionFactory sessionFactory = (SessionFactory) GTRobotContextHelper
                .getBean("sessionFactory");
        final Session session = SessionFactoryUtils.getSession(sessionFactory,
                true);
        session.setFlushMode(FlushMode.MANUAL);
        TransactionSynchronizationManager.bindResource(sessionFactory,
                new SessionHolder(session));
    }

    public void export(final String filename) throws IOException {
        XlsWordExportor.log.debug("Start of function of export to " + filename);
        this.export(new FileOutputStream(filename));
    }

    public void export(final OutputStream out) throws IOException {
        final HSSFWorkbook workbook = new HSSFWorkbook();

        this.titleStyle = this.createTitleStyle(workbook);
        this.contentStyle = this.createContentStyle(workbook);
        this.zhContentStyle = this.createZhContentStyle(workbook);

        final List wordUnits = this.wordUnitManager.getWordUnits();
        this.generateIndexSheet(workbook, wordUnits);
        this.generateSheets(workbook, wordUnits);

        // write xls document
        workbook.write(out);
        out.flush();
        XlsWordExportor.log.debug("End of function of write()");
    }

    private void generateIndexSheet(final HSSFWorkbook workbook,
            final List wordUnits) {
        // Create the index sheet
        final HSSFSheet indexSheet = workbook
                .createSheet(XlsWordExportor.INDEX_SHEET_NAME);

        int curRowCount = 0;
        // Write the title
        HSSFRow indexRow = indexSheet.createRow(curRowCount++);
        this.createStringCell(indexRow, 0, "Unit Id", this.titleStyle);
        this.createStringCell(indexRow, 1, "Unit Name", this.titleStyle);
        this.createStringCell(indexRow, 2, "Level", this.titleStyle);
        this.createStringCell(indexRow, 3, "Counts", this.titleStyle);
        // createStringCell(indexRow, 4, "Owner", titleStyle);

        // Write the contents of table list
        final Iterator iterator = wordUnits.iterator();
        while (iterator.hasNext()) {
            final WordUnit wordUnit = (WordUnit) iterator.next();

            indexRow = indexSheet.createRow(curRowCount++);

            this.createStringCell(indexRow, 0, wordUnit.getWordUnitId()
                    .toString(), this.contentStyle);
            final HSSFCell cell = indexRow.createCell((short) 1);
            cell.setCellFormula("HYPERLINK( \"#" + wordUnit.getName()
                    + "!A1\", \"" + wordUnit.getName() + "\" )");
            cell.setCellStyle(this.contentStyle);

            this.createStringCell(indexRow, 2, "" + wordUnit.getLevel(),
                    this.contentStyle);
            this.createStringCell(indexRow, 3, "" + wordUnit.getWordCount(),
                    this.contentStyle);

            // if ( wordUnit.getOwner() == null) {
            // createStringCell(indexRow, 4, "", contentStyle);
            // } else {
            // createStringCell(indexRow, 4, wordUnit.getOwner(), contentStyle);
            // }
        }
    }

    private void generateSheets(final HSSFWorkbook workbook,
            final List wordUnits) {
        // Write the contents of table list
        final Iterator iterator = wordUnits.iterator();
        while (iterator.hasNext()) {
            final WordUnit wordUnit = (WordUnit) iterator.next();

            // Create the index sheet
            final HSSFSheet sheet = workbook.createSheet(wordUnit.getName());
            this.generateSheet(sheet, wordUnit);
        }
    }

    private void generateSheet(final HSSFSheet sheet, final WordUnit wordUnit) {
        int curRowCount = 0;
        // Write the table title
        HSSFRow row = sheet.createRow(curRowCount++);
        this.createStringCell(row, 0, wordUnit.getWordUnitId().toString(),
                this.contentStyle);
        this.createStringCell(row, 1, "" + wordUnit.getName(),
                this.contentStyle);
        this.createStringCell(row, 2, "" + wordUnit.getLevel(),
                this.contentStyle);
        this.createStringCell(row, 3, "" + wordUnit.getWordCount(),
                this.contentStyle);
        final HSSFCell cell = row.createCell((short) 5);
        cell.setCellFormula("HYPERLINK( \"#" + XlsWordExportor.INDEX_SHEET_NAME
                + "!A1\", \"-> Index\" )");
        cell.setCellStyle(this.contentStyle);

        // Write the title
        row = sheet.createRow(curRowCount++);
        this.createStringCell(row, 0, "Id", this.titleStyle);
        this.createStringCell(row, 1, "Word", this.titleStyle);
        this.createStringCell(row, 2, "Pronounce", this.titleStyle);
        this.createStringCell(row, 3, "PronounceType", this.titleStyle);
        this.createStringCell(row, 4, "WordType", this.titleStyle);
        this.createStringCell(row, 5, "Meaning", this.titleStyle);
        this.createStringCell(row, 6, "Sentence", this.titleStyle);
        this.createStringCell(row, 7, "Comments", this.titleStyle);

        final List wordEntries = wordUnit.getWordEntries();
        final Iterator iterator = wordEntries.iterator();
        while (iterator.hasNext()) {
            final WordUnitEntry wordUnitEntry = (WordUnitEntry) iterator.next();
            final WordEntry wordEntry = this.wordEntryManager
                    .getWordEntry(wordUnitEntry.getPk().getWordEntryId());

            row = sheet.createRow(curRowCount++);

            this.createStringCell(row, 0,
                    wordEntry.getWordEntryId().toString(), this.contentStyle);
            this.createStringCell(row, 1, wordEntry.getWord().toString(),
                    this.contentStyle);
            this.createStringCell(row, 2, wordEntry.getPronounce(),
                    this.contentStyle);
            this.createStringCell(row, 3, wordEntry.getPronounceType(),
                    this.contentStyle);
            this.createStringCell(row, 4, wordEntry.getWordType(),
                    this.zhContentStyle);
            this.createStringCell(row, 5, wordEntry.getMeaning(),
                    this.zhContentStyle);
            this.createStringCell(row, 6, wordEntry.getSentence(),
                    this.contentStyle);
            this.createStringCell(row, 7, wordEntry.getComments(),
                    this.contentStyle);
        }
    }

    private HSSFCellStyle createTitleStyle(final HSSFWorkbook workbook) {
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

        final HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);// 字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName("ＭＳ Ｐゴシック");
        style.setFont(font);

        return style;
    }

    private HSSFCellStyle createContentStyle(final HSSFWorkbook workbook) {
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

        final HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);// 字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName("ＭＳ Ｐゴシック");
        style.setFont(font);

        return style;
    }

    private HSSFCellStyle createZhContentStyle(final HSSFWorkbook workbook) {
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

        final HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);// 字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗
        font.setColor(HSSFColor.BLACK.index);
        font.setFontName("宋体");
        style.setFont(font);

        return style;
    }

    private HSSFCell createStringCell(final HSSFRow row, final int cellNumber,
            final String value, final HSSFCellStyle style) {
        return this.createStringCell(row, (short) cellNumber, value, style);
    }

    private HSSFCell createStringCell(final HSSFRow row,
            final short cellNumber, String value, final HSSFCellStyle style) {
        if (value == null) {
            value = "";
        }
        final HSSFCell cell = row.createCell(cellNumber);
        cell.setCellValue(new HSSFRichTextString(value));
        cell.setCellStyle(style);
        return cell;

    }

    public static final void main(final String[] argv) throws IOException {
        final XlsWordExportor importor = new XlsWordExportor();

        importor.export("metadata\\data\\AllWords.xls");

        System.exit(0);
    }
}
