# Disqualified persons register parser

Компиляция проекта осуществляется инструментами Maven не ниже версии 3.0

Для компиляции необходимо выполнить скрипт - scripts\build_with_dependencies.bat

Для запуска программы необходимо выполнить скрипт - scripts\run.bat

Если после компиляции необходим перенос исполняемого файла - target\\*.jar, то папку target\data необходимо разместить вместе с исполняемым файлом, они зависимы.

Прграмма записывает дату последнего изменения в файл data\last_update_register.txt, что бы повторно выполнить программу в файле нужно указать дату предшествующую записанной, т.е 22.01.2021 -> 20.01.2021