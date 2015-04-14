# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "Company" ("ID" SERIAL NOT NULL PRIMARY KEY,"NAME" VARCHAR(254) DEFAULT '' NOT NULL);
create table "Facility" ("ID" SERIAL NOT NULL PRIMARY KEY,"IMAGE" VARCHAR(254) DEFAULT '' NOT NULL,"NAME" VARCHAR(254) DEFAULT '' NOT NULL,"COMPANYID" INTEGER NOT NULL);

# --- !Downs

drop table "Facility";
drop table "Company";

