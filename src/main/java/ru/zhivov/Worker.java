package ru.zhivov;

import ru.zhivov.doc_tool.JsonBuilder;

public class Worker {
    public static void main(String[] args) {
        JsonBuilder builder = new JsonBuilder();
        builder.buildAndSaveStatsJson();
    }
}
