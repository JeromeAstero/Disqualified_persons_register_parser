package ru.zhivov;

import ru.zhivov.web_tool.HtmlTool;

import java.util.HashMap;

public class Worker {
    public static void main(String[] args) {
        HtmlTool tool = new HtmlTool();
        HashMap<String, String> parsedWebData = tool.getParsedWebData();
        System.out.println(parsedWebData);

    }
}
