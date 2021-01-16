package ru.zhivov.web_tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class HtmlTool implements WebToolConst {

    //объекты для работы
    private Document document;
    private Elements elements;
    //константа размера мэпы для данных
    private final int dataCapacity = 3;
    private HashMap<String, String> data;

    //настройки коннектора Jsop
    private final String url = "https://www.nalog.ru/opendata/7707329152-registerdisqualified/";
    private final String userAgent = "Chrome/4.0.249.0 Safari/532.5";
    private final String referrer = "http://www.google.ru";

    //параметр поиска нужной таблицы с элементами для обработки
    private final String searchingTableHtmlClass = "table[class=border_table]";

    public HtmlTool() {
        data = new HashMap<>(dataCapacity);
    }

    //метод получения нужных элеметнов для дальнейшей работы
    //метод приватный, т.к. используется исключительно в скопе этого класса
    private Elements connectAndGetElementsTable() {
        try {
            document = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .referrer(referrer)
                    .get();
            System.out.println("Подключение к " + url + " установлено");
        } catch (
                IOException e) {
            System.out.println("Неудалось получить документ HTML от " + url);
            System.out.println("Проверьте подключение к интернет");
            System.exit(1);
        }
        return document.select(searchingTableHtmlClass);
    }

    //метод парсит полученный HTML документ
    //возвращает мэпу с данными для дальнейшей работы:
    //  ссылку на csv
    //  дату изменения
    //  дату актуальности
    public HashMap<String, String> getParsedWebData() {
        try {
            elements = connectAndGetElementsTable();
            //проверка полученного документа HTML на null
            if (elements != null) {

                //получаем из начального документа теги td
                elements = elements.select("td");


                //получаем данные элементов по индексу


                // 8 - Гиперссылка (URL) на набор

                // 8 - порядковый номер строки в таблице на сайте
                //элемент 21 - шапка
                //элемент 23 - значение
                int tableRow = 8;
                int top = 21;
                int value = 23;
                //проверяем значение в теге <td>(шапке)
                if (elements.get(top).text().equals(String.valueOf(tableRow))) {
                    //получем значение строки таблицы
                    String rowValue = elements.get(value).text().trim();
                    //проверяем формат данных для элемента
                    if (DataChecker.doMatch(rowValue, DOC_LINK)) {
                        //если все ОК складываем данные в мэпу и выводим отладку
                        data.put(DOC_LINK, rowValue);
                        System.out.println("Значение \"Гиперссылка (URL) на набор\" = " + rowValue + " - соотсветсвет формату URL");
                    } else {
                        System.out.println("Данные не соответствуют формату(не валидны)");
                        System.out.println("Дальнейшая работа невозможна");
                        doExit();
                    }
                } else {
                    System.out.println("Элемент таблицы \"Гиперссылка (URL) на набор\" - не был распознан");
                    System.out.println("Дальнейшая работа невозможна");
                    doExit();
                }

                // 12 - Дата последнего внесения изменений

                // 12 - порядковый номер строки в таблице на сайте
                //элемент 33 - шапка
                //элемент 35 - значение
                tableRow = 12;
                top = 33;
                value = 35;
                //проверяем значение в теге <td>
                if (elements.get(top).text().equals(String.valueOf(tableRow))) {
                    //получем значение строки таблицы
                    String rowValue = elements.get(value).text().trim();
                    if (DataChecker.doMatch(rowValue, EDIT_DATE)) {
                        //если все ОК складываем данные в мэпу и выводим отладку
                        data.put(EDIT_DATE, rowValue);
                        System.out.println("Значение \"Дата последнего внесения изменений\" = " + rowValue + " - соотсветсвет формату \"ДД.ММ.ГГГГ\"");
                    } else {
                        System.out.println("Данные не соответствуют формату(не валидны)");
                        System.out.println("Дальнейшая работа невозможна");
                        doExit();
                    }
                } else {
                    System.out.println("Элемент таблицы \"Дата последнего внесения изменений\" - не был распознан");
                    System.out.println("Дальнейшая работа невозможна");
                    doExit();
                }

                // 14 - Дата актуальности

                // 14 - порядковый номер строки в таблице на сайте
                //элемент 39 - шапка
                //элемент 41 - значение
                tableRow = 14;
                top = 39;
                value = 41;
                //проверяем значение в теге <td>
                if (elements.get(top).text().equals(String.valueOf(tableRow))) {
                    //получем значение строки таблицы
                    String rowValue = elements.get(value).text().trim();
                    if (DataChecker.doMatch(rowValue, ACTUAL_DATE)) {
                        //если все ОК складываем данные в мэпу и выводим отладку
                        data.put(ACTUAL_DATE, rowValue);
                        System.out.println("Значение \"Дата актуальности\" = " + rowValue + " - соотсветсвет формату \"ДД.ММ.ГГГГ\"");
                    } else {
                        System.out.println("Данные не соответствуют формату(не валидны)");
                        System.out.println("Дальнейшая работа невозможна");
                        doExit();
                    }
                } else {
                    System.out.println("Элемент таблицы \"Дата актуальности\" - не был распознан");
                    System.out.println("Дальнейшая работа невозможна");
                    doExit();
                }
            } else {
                throw new NullPointerException();
            }

        } catch (NullPointerException npe) {
            System.out.println("Полученный документ - пустой");
            System.out.println("Проверьте корректность указанного URL " + url);
            System.out.println("Проверьте корректность аргументов поиска таблицы внутри HTML документа " + "'" + searchingTableHtmlClass + "'");
            doExit();
        }
        return data;
    }

    private void doExit() {
        System.exit(1);
    }

}
