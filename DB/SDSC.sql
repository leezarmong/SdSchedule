select * from member
order by member_no;


SELECT *
FROM (
    SELECT ROWNUM AS RN, A.* 
    FROM (
        SELECT * 
        FROM MEMBER 
        ORDER BY MEMBER_NO
    ) A
)
ORDER BY RN;



select * from ( select rownum as RN, A.*
from(select * from member order by member_no)a )
order by RN;

select * from USERC natural join member where user_id = 'a';


desc member;

create table USERC(
USER_ID varchar2(20),
USER_PASS varchar2(20)
);

insert into userc values ('a','a');
insert into userc values ('sd_gang','sd001');
insert into userc values ('sd_hong','sd002');
insert into userc values ('sd_coex','sd003');

select * from userc;

select count(*) from userc where
user_id='a' and user_pass='a';

select member_name , member_grade from member natural join userc where user_id='b';

select * from member natural join userc where user_id = 'b';

select count(*) from member where user_id='a';

select count(*) from member;

select * from member where user_id ='a';


select * from member;

select * from userc;

insert into userc values ('test','001');


-- station 테이블에 member_name  컬럼 추가하기 
select * from station;


alter table station add MEMBER_NAME varchar2(20) NOT NULL;
SELECT member_name , FREI , GRILL , MAKE , EXPO , DISH 
FROM station 
NATURAL JOIN member 
WHERE member_name IS NOT NULL;

update userc set user_pass='1234' 
where user_pass='0425';


select member_name , frei , grill , make , expo , dish from station natural join member where;

 SELECT
        member_name, 
        FREI, 
        GRILL, 
        MAKE, 
        EXPO, 
        DISH
    FROM 
        STATION 
        NATURAL JOIN MEMBER
    WHERE 
        user_id = 'sd-coex' and member_name='이재원';
        
        select * from station;

alter table station add USER_ID varchar2(20);


select frei , grill , make ,expo , dish , member_name from station natural join member where user_id='sd-coex';
select member_name, user_id, frei, grill, make, expo, dish from member natural join station where member_name='전예준';



select * from station
where user_id = 'sd-coex';




SELECT frei, grill, make, expo, dish 
FROM member 
NATURAL JOIN station 
WHERE member_name IN ('김은경');

select member_name from member;



select frei ,grill , make ,expo ,dish from station where member_name IN ('','','','','','','','','','','',);



select frei ,grill , make ,expo ,dish from member natural join station where member_name IN ('','','',',');



SELECT
    m.member_name AS member_name,
    s.member_name AS station_name
FROM
    station s
LEFT JOIN
    member m
    
ON
    m.member_name = s.member_name;
    
    
    
    
    
    
    SELECT
    m.member_name AS member_name,
    s.member_name AS station_name
FROM
    member m
LEFT JOIN
    station s
    
ON
    m.member_name = s.member_name;
    
    


select member_name from member
where user_id='test';


delete from member
where member_name in ('멤버1','멤버2');

select * from station where user_id='test';
select * from member where user_id='test';

select * from member;

-- 내용 한번에 지우기 
TRUNCATE TABLE member;

TRUNCATE TABLE station;


-- 서버에 배포할시 station 테이블은 익스포트 ( 내용이 없는상테에서 ) 하고
-- ec2 oracle 에서 truncate table member; 를 사용하여 최신화 하자. 


-- sdsc 유저에 생성 되어있는 테이블 보기 .
SELECT table_name FROM all_tables WHERE owner = 'SDSC';

desc member;



-- Test Update 

update station set member_name='이재원짱'
where member_name='이재원';


update station set member_name='이재원'
where member_name='이재원짱';


SELECT MEMBER_SEQ.NEXTVAL FROM DUAL;


--1
TRUNCATE TABLE member;

TRUNCATE TABLE station;

--2
alter table station add test number(20) not null;

alter table station add MEMBER_NO number(20) not null
