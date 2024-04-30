const Excel = document.getElementById("excel-upload");
const TextArea = document.getElementById("json_data");

Excel.onchange = function () {
    const memberList = [
        "김장현",
        "김해수",
        "최인화",
        "유건희",
        "이희정",
        "강민지",
        "권태영",
        "김경민",
        "김무준",
        "김세희",
        "김영록",
        "김은경",
        "김지환",
        "박대용",
        "박현선",
        "복금현",
        "서준영",
        "안지연",
        "원동하",
        "유영현",
        "윤승관",
        "윤승관",
        "이상건",
        "이영현",
        "이재원",
        "전예준",
        "조경서",
        "조관우",
        "홍지오"
    ];
    readXlsxFile(Excel.files[0]).then(data=>{
        console.log(data)
        const Json = [];
        for(let i = 0; i < data.length; i++){
            // data[i][0], data[i][11] = 이름
            if(
                memberList.includes(String(data[i][0]).split('\n')[0])
            ){
                const temp = {};
                const arrid = String(data[i][0]).split('\n')[0];
                temp[arrid]=[];
                for(let j = 2; j < 9; j++){
                    if(data[i][j]!=null){
                        if(data[i][j].includes("~")){
                            let time_start = data[i][j].split('~')[0];
                            let time_finish = data[i][j].split('~')[1];
                            temp[arrid].push([time_start, time_finish])
                        }else{
                            temp[arrid].push(null)
                        }
                    }else{
                        temp[arrid].push(null)
                    }
                }
                Json.push(temp);
            };
            if(
                memberList.includes(String(data[i][11]).split('\n')[0])
            ){
                const temp = {};
                const arrid = String(data[i][11]).split('\n')[0];
                temp[arrid]=[];
                for(let j = 13; j < 20; j++){
                    if(data[i][j]!=null){
                        if(data[i][j].includes("~")){
                            let time_start = data[i][j].split('~')[0];
                            let time_finish = data[i][j].split('~')[1];
                            temp[arrid].push([time_start, time_finish])
                        }else{
                            temp[arrid].push(null)
                        }
                    }else{
                        temp[arrid].push(null)
                    }
                }
                Json.push(temp);
            };
        };
        console.log(Json)
    });
};