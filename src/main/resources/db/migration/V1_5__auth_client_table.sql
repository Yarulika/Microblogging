  CREATE TABLE `client`
(
  id                integer                                           NOT NULL,
  client_id         varchar(50)                                        NOT NULL,
  internal_id       varchar(50),
  name              varchar(50)                                        NOT NULL,
  client_secret     varchar(100)                                       NOT NULL,
  granted_authority varchar(50)
);