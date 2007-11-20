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
    private final List<String> exculdes = new ArrayList<String>();

    @Override
    public void importFile(final String fileName) {
        String sheetName = null;
        int totalRowCount = 0;
        int curRowCount = 0;
        short forstCellNum = 0;
        HSSFRow row = null;
        String unitName = null;

        try {
            WordImportor.log.debug("Start of function of read()");
            final HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    fileName));

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                final HSSFSheet sheet = workbook.getSheetAt(i);
                sheetName = workbook.getSheetName(i).trim().toUpperCase();
                if ((this.exculdes != null)
                        && this.exculdes.contains(sheetName.toLowerCase())) {
                    continue;
                }

                totalRowCount = sheet.getLastRowNum();
                curRowCount = 0;
                row = sheet.getRow(curRowCount++);
                forstCellNum = 0;
                forstCellNum++; // Id
                unitName = this.getCellStringValue(row, forstCellNum++);
                WordImportor.log.info("\n=================>: " + unitName);
                final WordUnit wordUnit = this.createWordUnit(unitName);
                wordUnit.setLevel(this.getCellLongValue(row, forstCellNum++));
                wordUnit.setWordCount(totalRowCount - 2);
                this.saveWordUnit(wordUnit);

                // Skip the title row
                curRowCount++;
                for (; curRowCount <= totalRowCount; curRowCount++) {
                    row = sheet.getRow(curRowCount);
                    forstCellNum = 0;
                    forstCellNum++; // Id

                    final String word = this.getCellStringValue(row,
                            forstCellNum++);
                    final String pronounce = this.getCellStringValue(row,
                            forstCellNum++);
                    final String pronounceType = this.getCellStringValue(row,
                            forstCellNum++);
                    final String wordType = this.getCellStringValue(row,
                            forstCellNum++);
                    final String meaning = this.getCellStringValue(row,
                            forstCellNum++);
                    final String sentence = this.getCellStringValue(row,
                            forstCellNum++);
                    final String comments = this.getCellStringValue(row,
                            forstCellNum++);

                    final WordEntry wordEntry = this.createWordEntry(word,
                            pronounce, pronounceType, wordType, meaning,
                            sentence, comments);
                    this.saveWordEntry(wordUnit, wordEntry);
                }
                this.flush();
            }
        } catch (final Exception e) {
            WordImportor.log.info("*****>" + fileName + " Sheet(" + sheetName
                    + ":" + curRowCount + ")");
            e.printStackTrace();
        }
        return;
    }

    /**
     * @param row
     * @param forstCellNum
     * @return
     */
    private String getCellStringValue(final HSSFRow row, final short cellNum) {
        final HSSFCell cell = row.getCell(cellNum);
        if (cell == null) {
            return null;
        }

        String result = "**";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            final HSSFRichTextString richStringCellValue = cell
                    .getRichStringCellValue();
            if (richStringCellValue == null) {
                result = null;
            }
            result = richStringCellValue.getString();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            result = "" + cell.getNumericCellValue();
            break;
        }

        if (result != null) {
            result = result.trim();
        }
        return result;
    }

    private long getCellLongValue(final HSSFRow row, final short cellNum) {
        final String cellStringValue = this.getCellStringValue(row, cellNum);
        return Long.parseLong(cellStringValue);
    }

    public static final void main(final String[] argv) {
        final XlsWordImportor importor = new XlsWordImportor();

        XlsWordImportor.importFile("metadata\\data\\newwords\\", importor, 4);

        System.exit(0);
    }

    private static void importFile(final String filePath,
            final XlsWordImportor importor, final int wordLevel) {
        importor.wordLelvel = wordLevel;
        importor.exculdes.add("index");

        final File dir = new File(filePath);
        final String[] filenames = dir.list();
        for (final String element : filenames) {
            importor.importFile(filePath + element);
        }
    }
}
