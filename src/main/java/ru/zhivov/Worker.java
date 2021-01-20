package ru.zhivov;

import ru.zhivov.file_tool.FileManager;

public class Worker {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        fileManager.saveDocCsv();
    }
}
