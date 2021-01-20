package ru.zhivov.files_tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {

    private String editDate;
    private String actualDate;
    private String dateFromFile;
    private String dateToday;

    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public DateTool(String editDate, String actualDate, String dateFromFile) {
        this.editDate = editDate;
        this.actualDate = actualDate;
        this.dateFromFile = dateFromFile;
        this.dateToday = getTodayDateString();
    }

    //получаем сегодняшнюю дату в строковом представлении
    private String getTodayDateString() {
        Date dateNow = new Date();
        return dateFormat.format(dateNow);
    }

    //сравниваем дату изменения в URL и дату в файле
    public boolean doMacth() {
        try {
            Date dateFromFileObj = dateFormat.parse(dateFromFile);
            Date dateTodayObj = dateFormat.parse(dateToday);
            Date actualDateObj = dateFormat.parse(actualDate);
            Date editDateObj = dateFormat.parse(editDate);

            //проверим актуальность документа
            int i = dateTodayObj.compareTo(actualDateObj);
            if (i < 0) {
                System.out.println("Документ csv актуален");

            } else {
                System.out.println("Документ csv неактуален");
            }
            System.out.println("Дата актуальности - " + actualDate);

            //выполняем бизнес-логику только когда дата изменения, полученная
            //из URL больше той, которая записана в файле
            //что бы избежать постоянной загрузки документа csv в день изменения
            return editDateObj.after(dateFromFileObj);

        } catch (ParseException e) {
            System.out.println("Полученные даты не были распознаны");
            doExit();
        }
        return false;
    }

    private void doExit() {
        System.exit(1);
    }
}
