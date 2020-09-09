create table category (id bigint generated by default as identity, description varchar(255), primary key (id)) engine=InnoDB;
create table ingredient (id bigint generated by default as identity, amount decimal(19,2), description varchar(255), recipe_id bigint, unit_of_measure_id bigint, primary key (id)) engine=InnoDB;
create table notes (id bigint generated by default as identity, note clob, recipe_id bigint, primary key (id)) engine=InnoDB;
create table recipe (id bigint generated by default as identity, cook_time integer, description varchar(255), difficulty varchar(255), directions clob, image blob, prep_time integer, servings integer, source varchar(255), url varchar(255), notes_id bigint, primary key (id)) engine=InnoDB;
create table recipe_category (recipe_id bigint not null, category_id bigint not null, primary key (recipe_id, category_id)) engine=InnoDB;
create table unit_of_measure (id bigint generated by default as identity, description varchar(255), primary key (id)) engine=InnoDB;
alter table ingredient add constraint FKj0s4ywmqqqw4h5iommigh5yja foreign key (recipe_id) references recipe;
alter table ingredient add constraint FK15ttfoaomqy1bbpo251fuidxw foreign key (unit_of_measure_id) references unit_of_measure;
alter table notes add constraint FKdbfsiv21ocsbt63sd6fg0t3c8 foreign key (recipe_id) references recipe;
alter table recipe add constraint FK37al6kcbdasgfnut9xokktie9 foreign key (notes_id) references notes;
alter table recipe_category add constraint FKqsi87i8d4qqdehlv2eiwvpwb foreign key (category_id) references category;
alter table recipe_category add constraint FKcqlqnvfyarhieewfeayk3v25v foreign key (recipe_id) references recipe;