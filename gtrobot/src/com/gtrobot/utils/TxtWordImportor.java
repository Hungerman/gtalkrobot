package com.gtrobot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.gtrobot.model.word.WordEntry;
import com.gtrobot.model.word.WordUnit;

/**
 * 实现数据的导入处理。
 * 
 * @author Joey
 * 
 */
public class TxtWordImportor extends WordImportor {

    @Override
    public void importFile(final String fileName) {
        long count = 0;
        String line = null;
        String unitName = null;
        try {
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), "GBK"));

            unitName = reader.readLine();
            WordImportor.log.info("\n\n\n================================>: "
                    + unitName);
            final WordUnit wordUnit = this.createWordUnit(unitName);

            line = reader.readLine();
            line = reader.readLine();
            while (!line.equals("/End")) {
                WordImportor.log.info("----->: " + line);
                final WordEntry wordEntry = this.processLine(line);

                this.saveWordEntry(wordUnit, wordEntry);

                line = reader.readLine();
                count++;
            }
            wordUnit.setWordCount(count);
            this.saveWordUnit(wordUnit);
            this.flush();
        } catch (final Exception e) {
            WordImportor.log.info("*****>" + unitName + "(" + count + "): "
                    + line);
            e.printStackTrace();
        }
    }

    private WordEntry processLine(final String line) throws SQLException,
            UnsupportedEncodingException {

        // できる//②/动2/会，能/0/
        final String[] split = line.split("/");
        final String pronounce = split[0];
        String word = split[1];
        if ((word == null) || (word.trim().length() == 0)) {
            word = pronounce;
        }
        final String pronounceType = split[2];
        final String wordType = split[3];
        final String meaning = split[4];

        final WordEntry wordEntry = this.createWordEntry(word, pronounce,
                pronounceType, wordType, meaning, null, null);
        return wordEntry;
    }

    public static final void main(final String[] argv) {
        final TxtWordImportor importor = new TxtWordImportor();

        TxtWordImportor.importFile("metadata\\data\\标日初级分课单词上\\", importor, 4);
        TxtWordImportor.importFile("metadata\\data\\标日初级分课单词下\\", importor, 3);
        TxtWordImportor.importFile("metadata\\data\\标日中级分课单词\\", importor, 2);

        System.exit(0);
    }

    private static void importFile(final String filePath,
            final WordImportor importor, final int wordLevel) {
        importor.wordLelvel = wordLevel;
        final File dir = new File(filePath);
        final String[] filenames = dir.list();
        for (final String element : filenames) {
            importor.importFile(filePath + element);
        }
    }
}
