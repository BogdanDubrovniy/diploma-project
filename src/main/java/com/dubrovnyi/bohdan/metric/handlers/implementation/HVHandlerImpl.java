package com.dubrovnyi.bohdan.metric.handlers.implementation;

import com.dubrovnyi.bohdan.constants.Operators;
import com.dubrovnyi.bohdan.db.models.HVModel;
import com.dubrovnyi.bohdan.exceptions.WrongNumberValueException;
import com.dubrovnyi.bohdan.metric.handlers.CommonMethodCollection;
import com.dubrovnyi.bohdan.metric.handlers.HVHandler;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bohdan on 26.11.2017.
 */
public class HVHandlerImpl implements HVHandler {

    private static final String BEGIN_VALUE = "BEGIN";

    private static final String EMPTY_LINE = "";
    private static final String WHITE_SPACE = " ";
    private static final String COMMENT_FIRST_POSITION = " --";
    private static final String COMMENT_SIGN = "--";
    private static final String SLASH_SIGN = "/";

    private static final String COMMENT_LINE_REG_EXP = "-- ";

    private int n;
    private int N;

    private double hvValue;

//    private File currentFile;
    private int arraySize;
    private String[] arrayOfLines;
    private boolean beginPointInitialed;

    private Matcher matcher;

    private Set<String> distinctOperators;
    private Set<String> distinctOperands;


    private List<String> allOperatorsList;
    private List<String> allOperandsList;

    private InputStream inputStream;

    public HVHandlerImpl(InputStream inputStream) {
        this.inputStream = inputStream;

        initVariables();
    }

    @PostConstruct
    public void initVariables() {
        n = 0;

        N = 0;

        distinctOperators = new LinkedHashSet<>();
        distinctOperands = new LinkedHashSet<>();

        allOperatorsList = new ArrayList<>();
        allOperandsList = new ArrayList<>();
    }

    @Override
    public void showResult() throws WrongNumberValueException {
        System.out.println(System.lineSeparator()
                + "_____________________"
                + System.lineSeparator());

        System.out.println("Operators(" + allOperatorsList.size()
                + "):" + allOperatorsList);

        System.out.println("Operands(" + allOperandsList.size()
                + "):" + allOperandsList);

        System.out.println("Distinct Operators(" + distinctOperators.size()
                + "):" + distinctOperators);
        System.out.println("Distinct Operands(" + distinctOperands.size()
                + "):" + distinctOperands);

        System.out.println("Halstead Volume is = " + hvValue);
    }

    @Override
    public HVModel getResult() throws IOException {
        HVModel result = new HVModel();

        try {
            analyzeCode();
            result.setValue(hvValue);

        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return result;
    }

    //private methods:
    private void analyzeCode() throws IOException {
        initStartPoint();

        for (int position = 0; position < arraySize; position++) {
            isBeginLine(position);

            if (beginPointInitialed) {
                //do operations:
                analyzeIfCodeLine(position);
            }
        }

        try {
            calculateTotalResult();
        } catch (WrongNumberValueException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    private void calculateTotalResult() throws WrongNumberValueException {
        distinctOperands = new LinkedHashSet<>(allOperandsList);
        distinctOperators = new LinkedHashSet<>(allOperatorsList);

        n = distinctOperators.size() + distinctOperands.size();
        N = allOperandsList.size() + allOperatorsList.size();

        hvValue = getHVResult(N, n);
    }

    private void analyzeIfCodeLine(int position) {
        if (isCodeLine(position)) {

            //further analyses:
            if (isConsistsSemicolumn(position)) {
                allOperatorsList.add(";");
//                System.out.println("Consist ;");
            }

            if (isEndStatement(position)) {
//                System.out.println("Consist END statement!");

                return;
            }

            final List<String> parts = getPureStringParts(position);

            System.out.println("Parts are next: " + parts);
            analyzeParts(parts);
        }
    }

    private void countOperators(String operator) {
        allOperatorsList.add(operator);
    }

    private void countOperands(String operand) {

        if (operand.startsWith("'")) {
            allOperatorsList.add("'");
            allOperatorsList.add("'");
        }
        allOperandsList.add(operand);
    }

    private void analyzeParts(List<String> parts) {

        for (String input : parts) {


            //Parts for ignoring!!
            if (input.equalsIgnoreCase(BEGIN_VALUE)
                    || input.equalsIgnoreCase("IN")
                    || input.equalsIgnoreCase("..")
                    || input.equalsIgnoreCase("FOR")) {
                continue;
            }


            if (isOperator(input)) {
                if (!isFunction(input)) {
                    countOperators(input);
                }
            } else {
                //operand
                countOperands(input);
            }
        }

    }

    private boolean isFunction(String inputs) {
        return inputs.contains(Operators.PARENTHESIS_VALUE_OPEN);
    }

    private boolean isOperator(String inputs) {

        if (inputs.startsWith(Operators.INV_COMMA_VALUE)) {

            //it is string value
            return false;
        }

        if (isFunction(inputs)) {
            parseFunction(inputs);

            return true;
        }


        if (inputs.equalsIgnoreCase(Operators.IF_VALUE)) {

            //add to container!
            return true;
        }

        if (inputs.equalsIgnoreCase(Operators.ELSE_VALUE)) {

            //add to container!
            return true;
        }

        if (inputs.equalsIgnoreCase(Operators.THEN_VALUE)) {

            //add to container!
            return true;
        }

        if (inputs.equalsIgnoreCase(Operators.SEMIC_EQUAL_VALUE)) {

            //add to container!
            return true;
        }

        if (inputs.equalsIgnoreCase(Operators.EQUAL_VALUE)
                || inputs.equalsIgnoreCase(Operators.NOT_VALUE)
                || inputs.equalsIgnoreCase(Operators.GOTO_VALUE)) {

            //add to container!
            return true;
        }

        if (inputs.equalsIgnoreCase("LOOP")) {

            //add to container!
            return true;
        }

        if (inputs.equalsIgnoreCase(Operators.RETURN_VALUE)) {

            //add to container!
            return true;
        }

        return false;
    }

    private List<String> getStringParts(int position) {
        final String currentString = arrayOfLines[position].trim();

        System.out.println("current trim string=" + currentString);

        if (!currentString.contains("(")) {
            return Arrays.asList(currentString.split(" "));
        }

        return getStringPartsWhereFunction(currentString);
    }

    private List<String> getStringPartsWhereFunction(String currentString) {
        List<String> specialList = new ArrayList<>();

        boolean wasParenthis = false;

        StringBuilder sb = new StringBuilder();

        for (int index = 0; index < currentString.length(); index++) {

            char currentSeparator = currentString.charAt(index);

            switch (currentSeparator) {

                case '(':
                    wasParenthis = true;

                    sb.append(currentSeparator);
                    break;

                case ')':
                    wasParenthis = false;
                    sb.append(currentSeparator);
                    break;

                case ' ':
                    if (wasParenthis) {
                        sb.append(currentSeparator);
                    } else {

                        specialList.add(sb.toString());
                        sb = new StringBuilder();
                    }
                    break;

                default:
                    sb.append(currentSeparator);
            }

        }

        specialList.add(sb.toString());


        System.out.println("________________ LIST:" + specialList);

        return specialList;
    }

    private List<String> getPureStringParts(int position) {
        List<String> newList = new ArrayList<>();
        List<String> oldList = getStringParts(position);

        for (String s : oldList) {
            if (s.endsWith(";")) {
                String newValue = s.split(";")[0];

                newList.add(newValue);
            } else {
                newList.add(s);
            }
        }

        return newList;
    }


    private void parseFunction(String input) {
        System.out.println("Function: " + input);
        //do parse!

        parseFunctionInDetails(input);
    }

    private void parseFunctionInDetails(String input) {

        StringBuilder funcNameSB = new StringBuilder();
        StringBuilder operandsNameSB = null;


        for (int index = 0; index < input.length(); index++) {

            char currentSeparator = input.charAt(index);

            switch (currentSeparator) {
                case '(':
                    countOperators(funcNameSB.toString());

                    funcNameSB = null;
                    operandsNameSB = new StringBuilder();
                    break;

                case ')':
                    countOperands(operandsNameSB.toString());
                    operandsNameSB = null;
                    break;

                case ',':
                    countOperands(operandsNameSB.toString());

                    operandsNameSB = new StringBuilder();
                    countOperators(",");
                    break;

                case ' ':
                    break;

                default:
                    if (operandsNameSB == null) {
                        funcNameSB.append(currentSeparator);
                    } else {
                        operandsNameSB.append(currentSeparator);
                    }

            }

        }

    }

    private boolean isEndStatement(int position) {
        return arrayOfLines[position].contains(Operators.END_VALUE + " ") ||
                arrayOfLines[position].contains(Operators.END_VALUE + Operators.SEMICOLON_VALUE);
    }

    private boolean isConsistsSemicolumn(int position) {
        return arrayOfLines[position].contains(Operators.SEMICOLON_VALUE);
    }

    private double getHVResult(int totalLength, int uniqueLength)
            throws WrongNumberValueException {
        return totalLength * CommonMethodCollection.log2(uniqueLength);
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    private void initArray(List<String> inputList) {
        arraySize = inputList.size();
        arrayOfLines = new String[arraySize];
        System.arraycopy(inputList.toArray(), 0, arrayOfLines, 0, arraySize);
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

        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null) {
            listOfLines.add(currentLine);
        }

        System.out.println();
        initArray(listOfLines);
    }

    private boolean isCodeLine(int position) {
        return !isStartsFromCommentLine(position) && !isEmptyLine(position)
                && !arrayOfLines[position].startsWith(SLASH_SIGN);
    }

    private boolean isBeginLine(int position) {
        initRegExp(BEGIN_VALUE, arrayOfLines[position]);

        if (matcher.find()) {
            beginPointInitialed = true;
            return true;
        }

        return false;
    }

    private boolean isStartsFromCommentLine(int position) {
        String[] tempString = arrayOfLines[position].split(WHITE_SPACE);
        for (String currentString : tempString) {
            if (EMPTY_LINE.equals(currentString) || WHITE_SPACE.equals(currentString)) {
                continue;
            }
            return COMMENT_FIRST_POSITION.equals(currentString) || COMMENT_SIGN.equals(currentString);
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
        return !isStartsFromCommentLine(position) && isCommentLine(arrayOfLines[position]);
    }

    private boolean isEmptyLine(int position) {
        return EMPTY_LINE.equals(arrayOfLines[position]);
    }
}
