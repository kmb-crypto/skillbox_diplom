package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

       SpringApplication.run(Main.class, args);

    }
}

//TODO
//Этап I
//
//1. Создать Git-репозиторий на github.com (чтобы случайно не потерять результаты своей работы при форс-мажорных ситуациях).
//
//        2. Создать новое Spring-приложение, аналогичное приложению BookLibrary, которое вы разрабатывали в рамках модуля 12:
//+        - Подключить с помощью Maven необходимые зависимости.
//+        - Создать класс и метод Main, в котором должно запускаться Spring-приложение:
//+        SpringApplication.run(Main.class, args);
//+        - Создать файл конфигурации application.yml
//        - Создать в пакете controller классы:
//        DefaultController - для обычных запросов не через API (главная страница - /, в частности)
//        ApiPostController - обрабатывает все запросы /api/post/*
//    ApiAuthController - обрабатывает все запросы /api/auth/*
//    ApiGeneralController - для прочих запросов к API.
//+   - Подключить к приложению frontend, разместив содержимое архива “frontend-dist_XXXX-XX-XX.zip” в директории “resources”: файлы из папки static в /resources/static/, а файл /resources/tempaltes/index.html - в директорию/resources/templates/.
//+  - Сделать так, чтобы при входе на главную страницу открывался шаблон index.html (аналогично тому, как это сделано в проекте “BookLibrary”).
//+   - Создать структуру базы данных с помощью сущностей Hibernate в пакете model, по примеру проекта “BookLibrary”. Структура базы данных описана в файле “db.pdf”.
//
//   - Прописать все необходимые связи (ManyToMany, OneToMany, ManyToOne) между @Entity классов.
//
//
//После завершения, необходимо слить изменения в ветку master и отправить на проверку.
//
//
//Этап II
//
//Откройте файл API.pdf и изучите запросы, которые требуется реализовать в результате работы над дипломом.
//При запуске проекта и перехода у вас будет открыта страница с ошибкой 404, так как запросы, которые отправляет фронтенд не реализованы в нашем приложении. Задача этого этапа реализовать запросы главной страницы чтобы открывалась главная страница без ошибок.
//
//
//Реализовать запросы API:
//
//    GET /api/init
//    GET /api/settings
//    GET /api/auth/check
//    GET /api/post
//    GET /api/tag
//
//Начните с ответов заглушек (ответы фиксированными значениями) и далее будете заменять заглушки на получение данных из базы данных.
//
//Вам может помочь видео, с вариантом реализации ответа на запросы.
//
//После завершения, необходимо слить изменения в ветку master и отправить на проверку.
//
//
//Этап III
//
//Реализация остальных публичных методов, в файле api.pdf до метода
//
//    Список постов на модерацию - GET /api/post/moderation
//
//Для ускорения разработки приложения вы можете тестировать API с помощью таких сервисов как Talend API Tester - Free Edition (расширение для браузера Google Chrome) или приложение Postman (Win / Mac OS / Linux).
//
//После завершения, необходимо слить изменения в ветку master и отправить на проверку.
//
//
//Этап IV
//
//
//Реализация авторизации, методы:
//
//    POST /api/auth/login
//    GET /api/auth/logout
//    GET /api/post/my
//
//В результате должна быть возможность зарегистрироваться, авторизоваться и посмотреть своего профиля.
//
//
//Вы можете реализовать через сохранении сессии в Map в одном из классе сервисе или использовать Spring Security, использую видео-гайд по внедрению Spring Security в проект диплома.
//
//
//После завершения, необходимо слить изменения в ветку master и отправить на проверку.
//
//
//Этап V
//
//
//Реализация методов:
//
//    добавления, редактирование поста
//    добавление комментариев
//    лайк, дизлайк поста
//    получение статистики
//    изменение профиля
//    запрос на смену пароля
//    модерация: вывод постов для модерации, утверждение поста, отклонение поста, изменение настроек блога
//
//После завершения, необходимо слить изменения в ветку master и отправить на проверку.
//
//
//Этап VI
//
//
//Размещение проекта для демонстрации на защите
//
//Необходимо разместить базу данных и само приложение на любом сервисе для java приложений, для его демонстрации.
//
//Вы можете выбрать любой доступный вам способ (heroku, AWS и прочее)
//
//Для heroku.com подготовлена инструкция с помощью которой вы можете разместить ваше приложение для демонстрации на защите.
//
//
//После завершения, пришлите ссылку преподавателю с рабочим проектом.
//
//
//Этап VII
//
//После того как преподаватель проверил деплой проекта, переходите во второй блок диплома и записывайтесь на защиту 👍
//
//
