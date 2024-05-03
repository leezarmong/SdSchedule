 /* 엔터키 이벤트 */
        $("#login").keypress(function (e) {
            if (e.keyCode === 13) {
                insert();
            }
        });



        /* 멤버 등록 */
        function insert() {
            var member_name = $("#member_name").val();
            var member_grade = $("#member_grade").val();

            console.log(member_name);

            if (!member_name || !member_grade || member_grade === "null") {
                alert("직원 이름 또는 직급을 선택 해 주세요.");
            } else {
                $.ajax({
                    type: "POST",
                    url: "insert",
                    data: {
                        "member_name": member_name,
                        "member_grade": member_grade
                    },
                    success: function (data) {
                        alert("입력 완료..!");
                        window.location.href = "memberpage";
                    }
                });
            }
        }

        /* 멤버 삭제 */
        function deleteMember(button) {
            var memberNo = parseInt($(button).closest('tr').find('.member_no').val());
            /* 해당 번호만 정확하게 지울수 있도록. <tr> */

            $.ajax({
                type: "POST",
                url: "delete",
                data: {
                    "member_no": memberNo
                },
                success: function (data) {
                    alert("삭제완료..!");
                    window.location.href = "memberpage";
                }
            });
        }