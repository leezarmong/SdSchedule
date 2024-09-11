		
		
	/*
	   function insert() {
    var member_name = $("#member_name").val();
    var member_grade = $("#member_grade").val();
    var user_id = $("#user_id").val();  

    console.log(member_name);

    if (!member_name || !member_grade || member_grade === "null") {
        alert("직원 이름 또는 직급을 선택 해 주세요.");
    } else {
        $.ajax({
            type: "POST",
            url: "check",
            data: {
                "member_name": member_name,
                "user_id": user_id
            },
            success: function (data) {
                if (data != 0) {
                    alert("해당 직원은 이미 존재합니다.");
                } else {
                    $.ajax({
                        type: "POST",
                        url: "insert",
                        data: {
                            "member_name": member_name,
                            "member_grade": member_grade,
                            "user_id": user_id 
                        },
                        success: function (data) {
                            alert("입력 완료..!");
                            window.location.href = "memberpage";
                        }
                    });
                }
            }
        });
    }
}
*/


function insert() {
    // Disable the button to prevent multiple clicks
    $(".btn-primary").prop('disabled', true);

    // member table variables
    var member_name = $("#member_name").val();
    var member_grade = $("#member_grade").val();
    var user_id = $("#user_id").val();
    
    // station table variables
    var frei = $("#frei").is(":checked") ? $("#frei").val() : null;
    var grill = $("#grill").is(":checked") ? $("#grill").val() : null;
    var make = $("#make").is(":checked") ? $("#make").val() : null;
    var expo = $("#expo").is(":checked") ? $("#expo").val() : null;
    var dish = $("#dish").is(":checked") ? $("#dish").val() : null;
    
    console.log("Member Name: " + member_name);
    
    // Log values to ensure they're captured
    console.log("frei: " + frei);
    console.log("grill: " + grill);
    console.log("make: " + make);
    console.log("expo: " + expo);
    console.log("dish: " + dish);

    if (!member_name || !member_grade || member_grade === "null") {
        alert("직원 이름 또는 직급을 선택 해 주세요.");
        $(".btn-primary").prop('disabled', false);  // Re-enable button if validation fails
        return; // Prevent further execution if validation fails
    } 

    // Combine all data into a single AJAX request
    $.ajax({
        type: "POST",
        url: "insert",  // Single URL for both member and station insert
        data: {
            member_name: member_name,
            member_grade: member_grade,
            user_id: user_id,
            frei: frei,
            grill: grill,
            make: make,
            expo: expo,
            dish: dish
        },
        success: function (data) {
            alert("입력 완료..!");
            window.location.href = "memberpage";
        },
        error: function(xhr, status, error) {
            console.error("Error inserting data:", error);
            $(".btn-primary").prop('disabled', false);  // Re-enable button on error
        }
    });
}



        /* 멤버 삭제 */
        function deleteMember(button) {

            var confirmDelete = confirm("정말로 삭제하시겠습니까?");

            if (confirmDelete) {
                // 클릭된 버튼이 속한 행에서 회원 번호를 가져옴
                /*var member_name = parseInt($(button).closest('tr').find('.member_name').val());*/
                 var member_name = $(button).closest('tr').find('.member_name').val();

                $.ajax({
                    type: "POST",
                    url: "delete",
                    data: {
                        "member_name":member_name
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

        			
		
	/*
	   function insert() {
    var member_name = $("#member_name").val();
    var member_grade = $("#member_grade").val();
    var user_id = $("#user_id").val();  

    console.log(member_name);

    if (!member_name || !member_grade || member_grade === "null") {
        alert("직원 이름 또는 직급을 선택 해 주세요.");
    } else {
        $.ajax({
            type: "POST",
            url: "check",
            data: {
                "member_name": member_name,
                "user_id": user_id
            },
            success: function (data) {
                if (data != 0) {
                    alert("해당 직원은 이미 존재합니다.");
                } else {
                    $.ajax({
                        type: "POST",
                        url: "insert",
                        data: {
                            "member_name": member_name,
                            "member_grade": member_grade,
                            "user_id": user_id 
                        },
                        success: function (data) {
                            alert("입력 완료..!");
                            window.location.href = "memberpage";
                        }
                    });
                }
            }
        });
    }
}
*/


function insert() {
    // Disable the button to prevent multiple clicks
    $(".btn-primary").prop('disabled', true);

    // member table variables
    var member_name = $("#member_name").val();
    var member_grade = $("#member_grade").val();
    var user_id = $("#user_id").val();
    
    // station table variables
    var frei = $("#frei").is(":checked") ? $("#frei").val() : null;
    var grill = $("#grill").is(":checked") ? $("#grill").val() : null;
    var make = $("#make").is(":checked") ? $("#make").val() : null;
    var expo = $("#expo").is(":checked") ? $("#expo").val() : null;
    var dish = $("#dish").is(":checked") ? $("#dish").val() : null;
    
    console.log("Member Name: " + member_name);
    
    // Log values to ensure they're captured
    console.log("frei: " + frei);
    console.log("grill: " + grill);
    console.log("make: " + make);
    console.log("expo: " + expo);
    console.log("dish: " + dish);

    if (!member_name || !member_grade || member_grade === "null") {
        alert("직원 이름 또는 직급을 선택 해 주세요.");
        $(".btn-primary").prop('disabled', false);  // Re-enable button if validation fails
        return; // Prevent further execution if validation fails
    } 

    // Combine all data into a single AJAX request
    $.ajax({
        type: "POST",
        url: "insert",  // Single URL for both member and station insert
        data: {
            member_name: member_name,
            member_grade: member_grade,
            user_id: user_id,
            frei: frei,
            grill: grill,
            make: make,
            expo: expo,
            dish: dish
        },
        success: function (data) {
            alert("입력 완료..!");
            window.location.href = "memberpage";
        },
        error: function(xhr, status, error) {
            console.error("Error inserting data:", error);
            $(".btn-primary").prop('disabled', false);  // Re-enable button on error
        }
    });
}



        /* 멤버 삭제 */
        function deleteMember(button) {

            var confirmDelete = confirm("정말로 삭제하시겠습니까?");

            if (confirmDelete) {
                // 클릭된 버튼이 속한 행에서 회원 번호를 가져옴
                /*var member_name = parseInt($(button).closest('tr').find('.member_name').val());*/
                 var member_name = $(button).closest('tr').find('.member_name').val();

                $.ajax({
                    type: "POST",
                    url: "delete",
                    data: {
                        "member_name":member_name
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

        	/* 멤버 업데이트 */
			function update(button) {
			    var member_name = $(button).closest('tr').find('.member_name').val();
			    var member_grade = $(button).closest('tr').find('.member_grade').val();
			      // station table variables
			    var frei = $("#frei").is(":checked") ? $("#frei").val() : null;
			    var grill = $("#grill").is(":checked") ? $("#grill").val() : null;
			    var make = $("#make").is(":checked") ? $("#make").val() : null;
			    var expo = $("#expo").is(":checked") ? $("#expo").val() : null;
			    var dish = $("#dish").is(":checked") ? $("#dish").val() : null;
			
			    if (member_grade === "null") {
			        alert("등급을 다시 선택 해 주세요.");
			    } else {
			        var confirmUpdate = confirm("정말로 수정하시겠습니까?");
			        if (confirmUpdate) {
			            // 클릭된 버튼이 속한 행에서 회원 번호를 가져옴
			            var memberNo = parseInt($(button).closest('tr').find('.member_no').val());
			console.log(memberNo);
			            $.ajax({
			                type: "GET",
			                url: "updateMember",
			                data: {
			                    "member_name": member_name,
			                    "member_grade": member_grade,
			                    "member_no": memberNo,
			                     frei: frei,
					            grill: grill,
					            make: make,
					            expo: expo,
					            dish: dish
			                },
			                success: function (data) {
			                    alert("업데이트 완료.. !");
			                    window.location.href = "memberpage";
			                }
			            });
			        }
			    }
			}
			
			