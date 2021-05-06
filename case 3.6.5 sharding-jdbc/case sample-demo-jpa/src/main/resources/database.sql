CREATE TABLE `t_test_0` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `author` varchar(40) NOT NULL,
  `date` datetime DEFAULT NULL,
  `title_id` varchar(32) DEFAULT NULL,
  `column_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_test_1` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `author` varchar(40) NOT NULL,
  `date` datetime DEFAULT NULL,
  `title_id` varchar(32) DEFAULT NULL,
  `column_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
