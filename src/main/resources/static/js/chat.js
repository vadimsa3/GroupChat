// $ переменная, содержащая библиотеку jquery

// код запустится когда загрузится страница полностью
$(function(){
    let getMessageElement = function(message)
    {
    // создаем свойства каждого сообщения
        let item = $('<div class="message-item"></div>');
        let header = $('<div class="message-header"></div>');
        // добавляем элементы и сообщение со свойством (отформатированная строка)
        header.append($('<div class="datetime">' + message.datetime + '</div>'));
        // добавляем элементы и сообщение со свойством (отформатированная строка)
        header.append($('<div class="username">' + message.username + '</div>'));
        let textElement = $('<div class="message-text"></div>');
        textElement.text(message.text);
        item.append(header, textElement); // добавляем элементы
        return item;
    };
    let updateMessages = function() // запуск обновления сообщений
    {
        $('.messages-list').html('<i>Сообщений нет</i>'); //  очищаем полностью messages-list при вызове updateMessages
        $.get('/message', {}, function(response) { // отправляем get запрос к драйверу и получаем ответ
            if(response.length == 0) { return ;}
            $('.messages-list').html(''); //  очищаем полностью messages-list

            for(i in response) { // перебор пришедших message
                let element = getMessageElement(response[i]);
                $('.messages-list').append(element); // добавляем в очищенный выше messages-list созданный item
            }

        })
    };


    let initApplication = function() // инициализация событий
    {
        $('.messages-and-users').css({display: 'flex'}); // включение контейнера
        $('.controls').css({display: 'flex'}); // включение контейнера
        $('.send-message').on('click', function() { // включение кнопки отправки сообщений
        let message = $('.new-message').val(); // получение сообщения
            $.post('/message', {message: message}, // событие на кнопке click и по событию выполняется функция
            function(response) { // отправляем сообщение (объект с параметром message) методом post на экшен '/message'
                                // контроллера и получаем ответ response
                    if(response.result) { // если ответ true
                           $('.new-message').val(''); // очистим поле сообщений добавив пустую строчку
                    } else { // если ответ false
                           alert('Ошибка. Повторите попытку позже'); // сообщение пользователю
                    }
            });
        })

        // метод вызывает функцию updateMessages каждую секунду (обновление списка сообщений)
        setInterval(updateMessages, 1000);
    };

    let registerUser = function(name) { // функция регистрации пользователя
    $.post('/auth', {name: name}, function(response) { // запрос по адресу auth отправляет name, проверяет по полю name
                                                       // в итоге получаем response
       if(response.result) { // проверяем, если пришел ответ true, то инициализируем наше приложение
          initApplication();
    }
    });
    };

                                            // !!! НАЧАЛО ВЫПОЛНЕНИЯ !!!
                                    // приложение запрашивает данные с сервера
        // при входе в приложении сразу идет Get запрос init и проверка авторизации
        // метод обращается к серверу - у нас к init (передаем путь) и возвращает ответ - response в виде json
        $.get('/init', {}, function(response){ // запрос к init по полю name, в итоге получаем response в виде json
            if(!response.result) // проверим авторизацию (на вход идет ответ boolean из метода init)
            // если пришел false, то требуется авторизация, если true, то запускается приложение
            {
                let name = prompt("Введите Ваше имя:"); // всплывающее окно если пользователь не авторизован и false
                registerUser(name); // регистрация (выполняется функция registerUser)
                return; // активация приложения
            } // иначе приложение начинает работать
                initApplication();
        });
    });

