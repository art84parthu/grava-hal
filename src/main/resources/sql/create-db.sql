drop table player_info if exists;
create table player_info (
    id integer primary key,
    name varchar(50) not null
);

drop table game_info if exists;
create table game_info (
    id integer primary key,
    name varchar(50),
    player1_id integer,
    player2_id integer,
    stonesInPits_p1 varchar(50),
    stonesInPits_p2 varchar(50),
    currently_playing integer,
    won_by integer
);
