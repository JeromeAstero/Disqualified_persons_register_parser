package ru.zhivov;

import ru.zhivov.doc_tool.JsonBuilder;
import ru.zhivov.files_tool.CryptoTool;
import ru.zhivov.mail_tool.MailSender;

public class Worker {
    public static void main(String[] args) {

        //получаем путь json
        String jsonPath = new JsonBuilder().buildAndSaveStatsJson();

        //отправляем по почте
        MailSender mailSender = new MailSender(new CryptoTool());
/*
        MailSender mailSender = new MailSender(
                "",//пароль
                "",//отправитель(логин)
                "",//получатель
                null//если нужна скрытая копия, то необходимо указать адрес
        );
*/

        mailSender.sendJson(jsonPath);

    }
}