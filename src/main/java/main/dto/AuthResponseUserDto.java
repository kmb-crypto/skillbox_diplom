package main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseUserDto {


}

//{
//        "result": true,
//        "user": {
//        "id": 576,
//        "name": "Дмитрий Петров",
//        "photo": "/avatars/ab/cd/ef/52461.jpg",
//        "email": "petrov@petroff.ru",
//        "moderation": true,
//        "moderationCount": 56,
//        "settings": true
//        }
//}
//        Значение moderationCount содержит количество постов необходимых для проверки модераторами.
//        Считаются посты имеющие статус NEW и не проверерны модератором.
//        Если пользователь не модератор возращать 0 в moderationCount и false в moderation