package com.dubrovnyi.bohdan.metric.handlers.implementation;

import com.dubrovnyi.bohdan.db.models.SLOCModel;
import com.dubrovnyi.bohdan.metric.handlers.SLOCHandler;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bohdan on 28.05.2017.
 */
public class SLOCHandlerImplementation implements SLOCHandler {
    //private File currentFile;
    private InputStream inputStream;
    private int arraySize;
    private String[] arrayOfLines;
    private boolean beginPointInitialed;

    private Matcher matcher;

    private static final String EMPTY_LINE = "";
    private static final String WHITE_SPACE = " ";
    private static final String COMMENT_FIRST_POSITION = " --";
    private static final String COMMENT_SIGN = "--";
    private static final String SLASH_SIGN = "/";


    private static final String COMMENT_LINE_REG_EXP = "-- ";
    private static final String INPUT_PROGRAM_POINT_REG_EXP =
            "CREATE OR REPLACE";

    private int numberOfLines;
    private int numberOfComments;
    private int numberOfEmptyLines;


    public SLOCHandlerImplementation(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void showCodeInformation() {
        System.out.println("-----------------------------");
        System.out.println("Number of code lines:    " + numberOfLines);
        System.out.println("Number of comment lines: " + numberOfComments);
        System.out.println("Number of empty lines:   " + numberOfEmptyLines);
        System.out.println("-----------------------------");
    }

    @Override
    public SLOCModel getResults() throws IOException {

        analyzeCode();

        SLOCModel model = new SLOCModel();

        model.setLoc(numberOfLines);

        double com = (numberOfLines == 0) ? 0
                : numberOfComments / numberOfLines;
        model.setCom(com);

        return model;
    }

    private void analyzeCode() throws IOException {
        initStartPoint();

        for (int position = 0; position < arraySize; position++) {
            isBeginLine(position);

            if (beginPointInitialed) {
                countCodeLines(position);
                countCommentLines(position);
                countEmptyLines(position);
            }
        }
    }

    private void countCodeLines(int position) {
        if (isCodeLine(position)) {
            numberOfLines++;
        }
    }

    private void countCommentLines(int position) {
        if (isStartsFromCommentLine(position)
                || isTrailCommentLine(position)) {
            numberOfComments++;
        }
    }

    private void countEmptyLines(int position) {
        if (isEmptyLine(position)) {
            numberOfEmptyLines++;
        }
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    private void initArray(List<String> inputList) {
        arraySize = inputList.size();
        arrayOfLines = new String[arraySize];
        System.arraycopy(inputList.toArray(), 0,
                arrayOfLines, 0, arraySize);
    }

    private void initRegExp(String regExp, String line) {
        Pattern pattern = Pattern.compile(regExp);
        matcher = pattern.matcher(line);
    }

    // initials start point op program
    private void initStartPoint() throws IOException {
        List<String> listOfLines = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            listOfLines.add(line);
        }

        initArray(listOfLines);
    }

    private boolean isCodeLine(int position) {
        return !isStartsFromCommentLine(position) && !isEmptyLine(position)
                && !arrayOfLines[position].startsWith(SLASH_SIGN);
    }

    private void isBeginLine(int position) {
        initRegExp(INPUT_PROGRAM_POINT_REG_EXP, arrayOfLines[position]);

        if (matcher.find()) {
            beginPointInitialed = true;
        }

    }

    private boolean isStartsFromCommentLine(int position) {
        String[] tempString = arrayOfLines[position].split(WHITE_SPACE);
        for (String currentString : tempString) {
            if (EMPTY_LINE.equals(currentString)
                    || WHITE_SPACE.equals(currentString)) {
                continue;
            }
            return COMMENT_FIRST_POSITION.equals(currentString)
                    || COMMENT_SIGN.equals(currentString);
        }
        return false;
    }

    private boolean isCommentLine(String line) {
        initRegExp(COMMENT_LINE_REG_EXP, line);

        return matcher.find();
    }

    /**
     * additional comment line
     *
     * @param position current line position for verification is it trail comment line
     * @return result of operation
     */
    private boolean isTrailCommentLine(int position) {
        return !isStartsFromCommentLine(position)
                && isCommentLine(arrayOfLines[position]);
    }

    private boolean isEmptyLine(int position) {
        return EMPTY_LINE.equals(arrayOfLines[position]);
    }
}