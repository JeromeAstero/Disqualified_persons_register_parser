package ru.zhivov;

import ru.zhivov.docCsv_tool.DocumentLoader;

public class Worker {
    public static void main(String[] args) {
        DocumentLoader loader = new DocumentLoader();
        loader.getRowsFromCsv();
    }
}
