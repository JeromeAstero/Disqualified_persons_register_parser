package ru.zhivov.docCsv_tool;

import ru.zhivov.beans.Row;

import java.util.*;
import java.util.stream.Collectors;

public class StatsBuilder {

    //1.	общее количество записей
    private Long total;
    //2.	уникальное количество ФЛ
    private Long uniqFl;
    //3.	уникальное количество ЮЛ
    private Long uniqUl;
    //4.	топ 10 статей КоАП, по количеству нарушений
    private LinkedHashMap<String, Integer> topTenAdministrativeCodes;

    //объектное представление csv
    private List<Row> csvRows;

    public StatsBuilder() {
        this.csvRows = new DocumentLoader().getRowsFromCsv();
        this.topTenAdministrativeCodes = new LinkedHashMap<>(10);

        getTotal();
        getUniqFl();
        getUniqUl();
        getTopTenAdministrativeCodes();

        printStats();
    }

    //общее колво строк(без шапки)
    private void getTotal() {
        total = (long) csvRows.size();
    }

    //уникальное количество ФЛ
    private void getUniqFl() {
        Set<String> uniqData = new HashSet<>();
        for (Row row :
                csvRows) {
            uniqData.add(row.getFullName());
        }
        uniqFl = (long) uniqData.size();
    }

    //уникальное количество ЮЛ
    private void getUniqUl() {
        Set<String> uniqData = new HashSet<>();
        for (Row row :
                csvRows) {
            uniqData.add(row.getOrganizationName());
        }
        uniqUl = (long) uniqData.size();
    }

    //топ 10 статей КоАП, по количеству нарушений
    private void getTopTenAdministrativeCodes() {
        HashMap<String, Integer> administrativeCodesCounter = new HashMap<>();
        //проходим по объектам строк csv
        for (Row row
                : csvRows
        ) {
            String administrativeCode = row.getAdministrativeCode();
            Integer value = 1;
            //если данная статья коап есть в мэпе, то
            //берем значнение по ключу и увеличиваем его
            if (administrativeCodesCounter.containsKey(administrativeCode)) {
                administrativeCodesCounter.put(administrativeCode, administrativeCodesCounter.get(administrativeCode) + value);
                //если данной статьи коап нет в мэпе, то
                //добавляем ключ(статья коап) со значением 1
            } else {
                administrativeCodesCounter.put(administrativeCode, value);
            }


        }

        //сортируем мэп по значению
        //устанавливаем лимит строк 10
        //коллектим данные в связную мэпу
        topTenAdministrativeCodes = administrativeCodesCounter
                .entrySet()
                .stream()
                .sorted(
                        Map.Entry.<String, Integer>comparingByValue()
                                .reversed()
                )
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (c1, c2) -> c1, LinkedHashMap::new));
    }

    //выводит статистику в консоль
    private void printStats() {
        System.out.println("<<<Статистика>>>");

        System.out.println("Общее количество строк(без шапки) - " + total);
        System.out.println("Уникальное количество ФЛ - " + uniqFl);
        System.out.println("Уникальное количество ЮЛ - " + uniqUl);

        System.out.println("<<<Топ 10 статей КоАП>>>");
        for (Map.Entry<String, Integer> entry : topTenAdministrativeCodes.entrySet()) {
            System.out.println(
                    "Статья " + entry.getKey() + "; кол-во вхождений " + entry.getValue()
            );
        }
    }

}
