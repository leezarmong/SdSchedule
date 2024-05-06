 		



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

            var confirmDelete = confirm("정말로 삭제하시겠습니까?");

            if (confirmDelete) {
                // 클릭된 버튼이 속한 행에서 회원 번호를 가져옴
                var memberNo = parseInt($(button).closest('tr').find('.member_no').val());

                $.ajax({
                    type: "POST",
                    url: "delete",
                    data: {
                        "member_no": memberNo
                    },
                    success: function (data) {
                        alert("삭제완료..!");
                        window.location.href = "memberpage";
                    },
                    error: function (xhr, status, error) {
                        alert("삭제 실패: " + error);
                    }
                });
            } else {
                console.log("삭제가 취소되었습니다.");
            }
        }