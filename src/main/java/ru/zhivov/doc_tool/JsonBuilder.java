package ru.zhivov.doc_tool;

import org.json.JSONObject;
import ru.zhivov.files_tool.FileManager;

import java.util.LinkedHashMap;

public class JsonBuilder {

    //1.	общее количество записей
    private Long total;
    //2.	уникальное количество ФЛ
    private Long uniqFl;
    //3.	уникальное количество ЮЛ
    private Long uniqUl;
    //4.	топ 10 статей КоАП, по количеству нарушений
    private LinkedHashMap<String, Integer> topTenAdministrativeCodes;

    private StatsBuilder statsBuilder;

    public JsonBuilder() {
        statsBuilder = new StatsBuilder();
        this.total = statsBuilder.getTotal();
        this.uniqFl = statsBuilder.getUniqFl();
        this.uniqUl = statsBuilder.getUniqUl();
        this.topTenAdministrativeCodes = statsBuilder.getTopTenAdministrativeCodes();
    }

    public String buildAndSaveStatsJson() {

        //Создаем объект json
        JSONObject jsonObject = new JSONObject();


        //записываем общее количество записей
        jsonObject.put("1. Total number of entries", total);

        //уникальное количество ФЛ
        jsonObject.put("2. Unique number of FL", uniqFl);

        //уникальное количество ЮЛ
        jsonObject.put("3. Unique number of UL", uniqUl);

        //топ 10 статей КоАП, по количеству нарушений
        jsonObject.accumulate("4. Top 10 administrative codes", null);
        for (String s :
                topTenAdministrativeCodes.keySet()) {
            Integer value = topTenAdministrativeCodes.get(s);
            jsonObject.append("4. Top 10 administrative codes", s + ": " + value + " matches");
        }

        System.out.println("Построение json завершено");

        //сохраним файл в фс
        return new FileManager(1).saveJson(jsonObject.toString(4));

    }
}
