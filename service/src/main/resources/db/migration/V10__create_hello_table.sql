CREATE TABLE result (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(200) not null,
  scan_time datetime not null,
  created timestamp not null,
  PRIMARY KEY (id)
)
;

CREATE TABLE vulnerability_count (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  result_id bigint(20) not null,
  severity varchar(200) not null,
  count bigint(20) not null,
  PRIMARY KEY (id)
)
;
