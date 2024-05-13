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