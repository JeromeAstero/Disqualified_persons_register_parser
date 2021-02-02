package ru.zhivov.mail_tool;

import ru.zhivov.files_tool.CryptoTool;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailSender {

    //найстроки почтового клиента
    private final String mailTo;
    private final String mailFrom;
    private final String password;
    private final String smtpHost = "smtp.gmail.com";
    private final String smtpPort = "465";

    //настройки для сессии smtp клиента
    private Properties properties;
    private Session session;
    //найстройка аутентификации на smtp сервере
    private Authenticator authenticator;

    //экземпляр утилиты шифрования
    private CryptoTool cryptoTool;

    //конструктор работающий с зашифрованными данными:
    //пароль
    //получатель
    //отправитель(логин)
    public MailSender(CryptoTool cryptoTool) {
        this.cryptoTool = cryptoTool;
        this.mailTo = cryptoTool.getRecipient();
        this.mailFrom = cryptoTool.getSender();
        this.password = cryptoTool.getPss();
        setUpSessionAndAuthentication();
    }

    //конструктор получающий параметры открыто
    public MailSender(String password, String emailFrom, String emailTo) {
        this.mailTo = emailTo;
        this.mailFrom = emailFrom;
        this.password = password;
        setUpSessionAndAuthentication();
    }


    public void sendJson(String jsonFilePath) {

        //имя файла при отправлении(без директории)
        String[] split = jsonFilePath.split("/");
        String jsonFileName = split[split.length - 1];

        //проверка расширения json
        String[] exts = jsonFileName.split("\\.");
        String fileExtension = exts[exts.length - 1];
        if (!fileExtension.equals("json")) {
            System.out.println("Неверный файл для отправки");
            System.out.println("Необходим .json");
            printDebug(jsonFilePath, jsonFileName);
            doExit();
        }
        try {
            //создаем письмо
            Message message = new MimeMessage(session);

            //указываем отправителя
            message.setFrom(new InternetAddress(mailFrom));
            //указываем получателя
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));

            //!!!
            //указываем скрытую копию
            message.setRecipient(Message.RecipientType.BCC, new InternetAddress(cryptoTool.getBCC()));
            //!!!

            //указываем тему письма
            message.setSubject("Statistics");

            //создаем обработчик разметки
            Multipart multipart = new MimeMultipart();

            //Создаем 1ую часть разметки - сообщения с текстом
            BodyPart messageBodyPart = new MimeBodyPart();
            //вносим текст
            messageBodyPart.setText("Сбор статистических данных завершен.\nРезультат в файле " + jsonFileName);
            //добавляем часть разметки к обработчику
            multipart.addBodyPart(messageBodyPart);

            //Создаем 2ую часть разметки - сообщения с вложением
            messageBodyPart = new MimeBodyPart();
            //создаем источник данных на основе переданого в метод пути и имени файла
            DataSource source = new FileDataSource(jsonFilePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            //именуем вложение
            messageBodyPart.setFileName(jsonFileName);
            //добавляем часть разметки к обработчику
            multipart.addBodyPart(messageBodyPart);

            //Создаем 3ую часть разметки - сообщения с подписью
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("\nСообщение сгенерировано автоматически.\n\nBest regards\nAnton Zhivov");
            //добавляем часть разметки к обработчику
            multipart.addBodyPart(messageBodyPart);

            //внедряем настроенный контент(части раземтки) в сообщение
            message.setContent(multipart);

            //отправляем сообщение
            Transport.send(message);

            System.out.println("Сообщение для " + mailTo + " успешно отправлено");

        } catch (MessagingException e) {
            System.out.println("Не удалось отправить сообщение");
            printDebug(jsonFilePath, jsonFileName);
            doExit();
        }
    }

    //обобщенный метод настройки smtp клиента
    private void setUpSessionAndAuthentication() {

        //настройки для сессии smtp клиента
        properties = new Properties();
        properties.setProperty("mail.smtp.host", smtpHost);
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //найстройка аутентификации на smtp сервере
        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailFrom, password);
            }
        };

        //создаем сессию c найстройками клиента и серверной аутентификации
        session = Session.getDefaultInstance(properties, authenticator);
    }

    //метод вывода отладочной информации
    private void printDebug(String jsonFilePath, String jsonFileName) {
        System.out.println("<<<DEBUG INFO START>>>");

        System.out.println("  mailTo: " + mailTo);
        System.out.println("mailFrom: " + mailFrom);
        System.out.println("password: " + "*hidden*");
        System.out.println("smtpHost: " + smtpHost);
        System.out.println("smtpPort: " + smtpPort);
        System.out.println("filePath: " + jsonFilePath);
        System.out.println("fileName: " + jsonFileName);

        System.out.println("<<<DEBUG INFO END>>>");
    }

    private void doExit() {
        System.exit(1);
    }
}
