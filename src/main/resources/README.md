1 . Выполните команду Maven для сборки проекта и создания JAR файла:
    mvn clean package

2. Запуск JAR файла через командную строку
    1. После успешной сборки проекта найдите сгенерированный JAR файл в директории target/.
    2. Откройте командную строку и перейдите в директорию с JAR файлом.
    3. Для запуска JAR файла используйте команду:

    java -jar ваш_файл.jar путь к файлу tickets.json, город отправления, город назанчения
    Например
    java -jar target/tickets-1.0-SNAPSHOT.jar ../tickets.json Владивосток Тель-Авив 



Результат вывода отображаеться следующим образом
For the airline {Имя Авиокомпании} Minimum flight time {Минимальное время в рамках текущей компангии}
Diff between avg and median = {Разница между медианой и средней ценной текущей компании}

For the airline SU Minimum flight time 06:00 Diff between avg and median = 483.33333333333394
For the airline S7 Minimum flight time 06:30 Diff between avg and median = 6550.0
For the airline TK Minimum flight time 05:50 Diff between avg and median = 6387.5
For the airline BA Minimum flight time 08:05 Diff between avg and median = 0.0
