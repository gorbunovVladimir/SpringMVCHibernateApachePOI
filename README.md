                                                 Задание:
Контроль корректности плана госпитализаций.
Веб-приложение из одной страницы, страница поделена на две части:
    1. форма запроса: поле для указания для загрузки файла с планом + текстовое поле "количество коек".
    2. результат обработки: общая корректность плана + список людей для которых нужно переносить госпитализацию.

Требования:
  - На вход подается файл MS Excel (.xls, 97-2003), из трех колонок (пример — в аттаче):
        A: ФИО пациента (тип поля — строка)
        B: Дата начала госпитализации (тип поля — дата)
        C: Дата окончания госпитализации, включительно (тип поля — дата)
      и количество коек на отделении.

    - Требуется определить, способно ли отделение вместить пациентов согласно плану, т.е. нет ли момента времени, в который, согласно плану, госпитализированных пациентов больше, чем коек. При проверке не учитываем время, т.е. если человек должен быть госпитализирован в день X, то он занимает его целиком.
    - Требуется определить, для каких пациентов надо скорректировать даты госпитализации, приоритет определяем по принципу "кто первый, тому и повезло", т.е. кто позже встречается в файле, тому и переносить госпитализацию; предлагать варианты для переноса не требуется.

                                                  Решение:
1. Импортируйте проект в IDE (Я использовал IntelliJ IDEA)
2. Я использовал Postgresql 9.6. В случае желания измените логин, пароль в SpringMVCHibernateApachePOI\src\main\webapp\WEB-INF\spring\appServlet\servlet-context.xml
3. В случае отсуствия такого желания создайте пользователя с логином - gorbunov, паролем -123456. (Можете воспользоваться положенным в корневой каталог скриптом sql") 
4. В корневом каталоге приложения с помощью maven выполните "mvn clean install"
5. Запустите проект на tomcat server (я использовал его)
6. Приложение доступно по адресу
- http://localhost:8080/SpringMVCHibernateApachePOI/humans
7. Для начала нужно выбрать файл, потом его загрузить, ввести количество кое и нажать расчитать при желании можете удалить все записи в БД, нажав соответствующую кнопку (внешний вид можно посмотреть на картинке =.jpg)
8. Для проверки использовал файлы sample-hospitalization-plan.xls и sample-hospitalization-plan.xlsx расположенные в корневом каталоге
