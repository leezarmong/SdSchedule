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