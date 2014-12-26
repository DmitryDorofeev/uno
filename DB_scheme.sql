--
-- База данных: `uno_db`
--

--
-- Подготовительный этап
-- CREATE DATABASE uno_db CHARACTER SET utf8 COLLATE utf8_general_ci;
-- GRANT ALL PRIVILEGES ON uno_db.* TO uno_admin@127.0.0.1 IDENTIFIED BY 'password';
--

-- --------------------------------------------------------

--
-- Структура таблицы `game`
--

-- В этой таблице подразумеваются записи вида:
-- (1, 2, 40, 1),
-- (2, 4, 20, 1),
-- (3, 5, 10, 1),
-- (4, 7, 0, 1), 
-- то есть после матча для каждого игока (playerId) создаётся запись в таблице с указанием количества набранных очков (score) и id матча (gameId).
-- В примере выше показаны записи, которые внеслись бы в пустую таблицу для игры с gameId = 1, в которой было 4 игрока: с id = 2, 4, 5 и 7.

CREATE TABLE IF NOT EXISTS `game` (
`id` bigint(20) NOT NULL,
  `playerId` bigint(20) NOT NULL,
  `score` bigint(20) NOT NULL,
  `gameId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

--
-- В этой таблице подразумеваются записи вида
-- (1, 'admin@admin.admin', 'admin', 'admin'),
-- (2, 'test@test.test', 'test', 'test'),
-- т.е. в примере выше создано 2 пользователя
-- 

CREATE TABLE IF NOT EXISTS `user` (
`id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Индексы таблицы `game`
--
ALTER TABLE `game`
 ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для таблицы `game`
--
ALTER TABLE `game`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;