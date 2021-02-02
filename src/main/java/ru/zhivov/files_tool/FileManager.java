package ru.zhivov.files_tool;

import ru.zhivov.web_tool.DataChecker;
import ru.zhivov.web_tool.HtmlTool;
import ru.zhivov.web_tool.WebToolConst;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;

public class FileManager implements WebToolConst {

    private HashMap<String, String> data;
    private String docLink;
    private String editDate;
    private String actualDate;
    private String dateFromFile;
    private DateTool dateTool;

    //константа основной директории
    private final String mainDir = "data/";
    //константа с имененм документа csv
    private final String csvFileName = mainDir + "document.csv";
    //константа с именем документа json
    private final String jsonFileName = mainDir + "statistic.json";


    //указываем путь и имя файла в котором хранится запись последнего обновления реестра
    private final String dateFileName = mainDir + "last_update_register.txt";

    //в конструкторе присвоим данные для всех рабочих объектов
    public FileManager() {
        this.data = new HtmlTool().getParsedWebData();
        this.docLink = data.get(DOC_LINK);
        this.editDate = data.get(EDIT_DATE);
        this.actualDate = data.get(ACTUAL_DATE);
        this.dateFromFile = getDateFromFile();
        this.dateTool = new DateTool(editDate, actualDate, dateFromFile);
    }

    //for save json
    public FileManager(int i) {

    }

    //получаем дату последнего изменения из файла
    private String getDateFromFile() {
        File file = new File(dateFileName);
        String strFromFile = "";
        //читаем файл и складываем прочитанное в стрингу
        try (FileReader reader = new FileReader(file)) {
            char[] buffer = new char[10];
            //реализовано для исключения варнинга
            int read = reader.read(buffer);
            strFromFile = String.valueOf(buffer).trim();
        } catch (IOException e) {
            System.out.println("Файл не найден " + dateFileName);
            doExit();
        }
        //проверяем формат считанной даты
        if (DataChecker.doMatch(strFromFile, ACTUAL_DATE)) {
            return strFromFile;
        } else {
            System.out.println("Неверный формат даты в файле " + dateFileName);
            System.out.println("Нужнный формат - дд.мм.гггг");
            doExit();
        }
        return "";
    }

    //записываем дату последнего изменения в файл
    private void setDateToFile() {
        File file = new File(dateFileName);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write(editDate);
        } catch (IOException e) {
            System.out.println("Запись невозможна");
            System.out.println("Файл не найден " + dateFileName);
        }
    }


    //метод сохранения реестра csv
    public String saveDocCsv() {
        if (dateTool.doMacth()) {
            try {
                //создали коннект
                //открыли поток
                //передали поток в рбч
                URL url = new URL(docLink);
                InputStream inputStream = url.openStream();
                ReadableByteChannel channel = Channels.newChannel(inputStream);

                //читаем и записываем файл
                FileOutputStream outputStream = new FileOutputStream(csvFileName);
                outputStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);

                System.out.println("Дата предыдущего изменения реестра " + dateFromFile);
                System.out.println("Дата текущего изменения реестра " + editDate);
                System.out.println("Загружен csv, файл - " + csvFileName);

                //записываем дату текущего изменения в файл
                setDateToFile();
            } catch (IOException e) {
                System.out.println("Невозможно записать документ");
                System.out.println("Проверьте наличие директории " + mainDir);
            }
        } else {
            System.out.println("Документ не был изменен");
            System.out.println("Загрузка не требуется");
            doExit();
        }
        //возвращаем путь и имя файла
        return csvFileName;
    }

    public String saveJson(String json) {
        File file = new File(jsonFileName);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write(json);
            System.out.println("Файл " + jsonFileName + " записан успешно");
        } catch (IOException e) {
            System.out.println("Запись невозможна");
            System.out.println("Файл не найден " + jsonFileName);
        }
        return jsonFileName;
    }

    private void doExit() {
        System.exit(1);
    }
}
