# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "Facility" ("ID" SERIAL NOT NULL PRIMARY KEY,"IMAGE" VARCHAR(254) DEFAULT '' NOT NULL,"NAME" VARCHAR(254) DEFAULT '' NOT NULL);

# --- !Downs

drop table "Facility";

