package ru.zhivov.docCsv_tool;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.zhivov.beans.Row;
import ru.zhivov.files_tool.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DocumentLoader {

    private FileManager fileManager;
    private String documentFile;
    private List<Row> rowsFromFile;

    public DocumentLoader() {
        this.documentFile = new FileManager().saveDocCsv();
        this.rowsFromFile = new ArrayList<>();
    }

    //метод загрузки данных csv в память приложения
    public List<Row> getRowsFromCsv() {
        try {
            File file = new File(documentFile);
            CsvToBean<Row> build = new CsvToBeanBuilder<Row>(new FileReader(file))
                    //пропускаем первую строку
                    .withSkipLines(1)
                    //указываем на обозначение кавычек
                    .withQuoteChar('\'')
                    //используем разделитель ;
                    .withSeparator(';')
                    //указываем тип объекта(бина) для мэппинга
                    .withType(Row.class)
                    //собираем
                    .build();

            //записываем данные в лист
            rowsFromFile = build.parse();

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден " + documentFile);
        }

        return rowsFromFile;
    }
}
