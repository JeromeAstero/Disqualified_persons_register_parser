# Disqualified persons register parser

Компиляция проекта осуществляется инструментами Maven не ниже версии 3.0

Для компиляции необходимо выполнить скрипт - scripts\build_with_dependencies.bat

Для запуска программы необходимо выполнить скрипт - scripts\run.bat

Если после компиляции необходим перенос исполняемого файла - target\\*.jar, то папку target\data необходимо разместить вместе с исполняемым файлом, они зависимы.

Программа записывает дату последнего изменения в файл data\last_update_register.txt, что бы повторно выполнить программу в файле нужно указать дату предшествующую записанной, т.е 22.01.2021 -> 20.01.2021

Почтовый сервер("из коробки") - Gmail

Для отправки почты требуется настройка учетных данных smtp-клиента, адресов отправителя, получателя и адреса скрытой копии.
Если скрытая копия не требуется необходимо закомментировать/удалить строки №83-№85 в классе ru\zhivov\mail_tool\MailSender.java

"Из коробки" программе необходимы зашифрованные AES данные, а именно: пароль для доступа на сервер smtp, адрес отправителя(логин), адрес получателя, адрес скрытой копии.
Так же требуется наличие ключа шифрования. Зашифрованные имена файлов, как и сам ключ шифрования должны иметь вид(имена и пути) описанный в классе ru\zhivov\files_tool\CryptoTool.java
Зашифрованные файлы вместе с ключом должны находится в каталоге data/secret/
Для работы "из коробки" возможно шифрование Ваших данных с помощью метода encrypt(String key, File inputFile, File outputFile) класса ru\zhivov\files_tool\CryptoTool.java, где 
String key - ключ расшифровки (необходимо создать файл key.seq в директории data/secret, в данный файл необходимо записать 16 знаков, которые будут являться ключом шифрования/расшифровки. После создания данного файла программа будет автоматически подхватывать его и обрабатывать)
File inputFile - файл с исходными данным для шифрования (Ваш файл подается в метод с помощью new File(*filename*))
File outputFile - выходной файл с зашифрованными данными (Ваш файл подается в метод с помощью new File(*filename*))

Так же возможно использование программы без зашифрованных данных.
Для этого необходимо в классе ru\zhivov\Worker.java закомментировать строку №14 и раскомментировать строки №16-№21 для использования другого конструктора, в котором необходимо указать Ваши данные.