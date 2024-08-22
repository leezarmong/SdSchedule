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

desc station;
