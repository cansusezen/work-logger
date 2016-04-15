/**
 * Created by c.sezen on 15.4.2016.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = scanner.nextLine();
        Path path = Paths.get(Constants.FILE_NAME);
        File f = new File(Constants.FILE_NAME);
        Date lastDate;

        try {
            if (f.exists()) {
                lastDate = readLastDate(Constants.FILE_NAME);
            }
            else {
                Files.createFile(path);
                lastDate = DateHelper.parseToDate(Constants.DATE_FORMAT, Constants.DATE_IN_PAST);
            }
            workLogger(text, lastDate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    private static void workLogger(String text, Date lastDate) {
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(Constants.FILE_NAME, true);
            PrintWriter pWriter = new PrintWriter(fWriter);
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
            SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT);

            String nowString = dateFormatter.format(new Date());
            Date now = dateFormatter.parse(nowString);

            if (now.after(lastDate)) {
                pWriter.append(Constants.HEADER_PREFIX + dateFormatter.format(new Date()));
                pWriter.append(System.lineSeparator());

            }
            pWriter.append(dateTimeFormatter.format(new Date()) + Constants.TAB_KEY + text);
            pWriter.append(System.lineSeparator());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fWriter != null) {
                    fWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Date readLastDate(String filePath) throws ParseException {
        FileReader fReader = null;
        String lastLine = Constants.BLANK_STRING;
        Date lastDate = DateHelper.parseToDate(Constants.DATE_FORMAT, Constants.DATE_IN_PAST);
        try {
            fReader = new FileReader(filePath);
            BufferedReader bReader = new BufferedReader(fReader);
            String line;
            while ((line = bReader.readLine()) != null) {
                lastLine = line;
            }

            String date = lastLine.substring(0, lastLine.indexOf(Constants.TAB_KEY));
            lastDate = DateHelper.parseToDate(Constants.DATE_FORMAT, date);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fReader != null) {
                    fReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lastDate;
    }
}
