import { 
    numberWithCommas,
    halfMinuteConverter
} from "./util.js";

const Excel = document.getElementById("excel-upload");
const wt_table = document.getElementById("weekPayTable");
const wt_table_head = document.querySelector("thead");
const wt_table_body = document.querySelector("tbody");
const excel_upload_btn = document.querySelector("label");
const tdElements = document.querySelectorAll('li');
const XLSX = import("https://cdn.sheetjs.com/xlsx-0.19.2/package/xlsx.mjs");

// get member list from: db > java > html > js
const memberList = [];
tdElements.forEach(td=>{
    const memberName = td.innerText;
    memberList.push(memberName);
});

// run. if detected excel upload
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
        // code for work table head
        let wt_table_head_html = `
            <tr>
                <th scope="col">직급</th>
                <th scope="col">이름</th>
                <th scope="col">닉네임</th>
                <th scope="col">총 근무 시간</th>
                <th scope="col">야간 근무 시간</th>
                <th scope="col">주휴 발생 여부</th>
                <th scope="col">주급</th>
            </tr>
        `;
        wt_table_head.innerHTML = wt_table_head_html;
        // code for work table body
        // data > Json convert as I want
        const Json = [];
        for(let i = 0; i < data.length; i++){
            // data[i][0], data[i][11] = 이름
            if(
                memberList.includes(String(data[i][0]).split('\n')[0])
            ){
                const arrid = String(data[i][0]).split('\n')[0];
                Json[arrid] = [];
                const tempArr = [];
                for(let j = 2; j < 9; j++){
                    if(data[i][j]!=null){
                        if(data[i][j].includes("~")){
                            let time_start = halfMinuteConverter(data[i][j].split('~')[0]);
                            let time_finish = halfMinuteConverter(data[i][j].split('~')[1]);
                            tempArr.push([time_start, time_finish])
                        }else{
                            tempArr.push(null)
                        }
                    }else{
                        tempArr.push(null)
                    }
                }
                Json[arrid]=tempArr;
            };
            if(
                memberList.includes(String(data[i][11]).split('\n')[0])
            ){
                const arrid = String(data[i][11]).split('\n')[0];
                Json[arrid]=[];
                const tempArr = [];
                for(let j = 13; j < 20; j++){
                    if(data[i][j]!=null){
                        if(data[i][j].includes("~")){
                            let time_start = halfMinuteConverter(data[i][j].split('~')[0]);
                            let time_finish = halfMinuteConverter(data[i][j].split('~')[1]);
                            tempArr.push([time_start, time_finish])
                        }else{
                            tempArr.push(null)
                        }
                    }else{
                        tempArr.push(null)
                    }
                }
                Json[arrid]=tempArr;
            };
        };
        // sort converted Json as key by 가나다
        const sortedJson = {};
        const sortedKeys = Object.keys(Json).sort((a,b)=>(a<b)?-1:(a==b)?0:1);
        sortedKeys.forEach(key=>{
            sortedJson[key] = Json[key];
        });
        // Insert converted Json to table
        let wt_table_body_html = "";
        const total = {
            weekWorkTime: 0,
            nightWorkTime: 0,
            weekPay: 0
        };
        for(let w = 0; w < sortedKeys.length; w++){
            // get weekly work time, night work time
            let weekWorkTime = 0;
            let nightWorkTime = 0;
            for(let d = 0; d < 7; d++){
                let workData = sortedJson[sortedKeys[w]][d];
                if(workData!=null){
                    if(workData[1]-workData[0]>=9){
                        weekWorkTime+=workData[1]-workData[0]-1
                    }else{
                        weekWorkTime+=workData[1]-workData[0]-0.5
                    };
                    if(workData[1]===22.5){
                        nightWorkTime+=0.5
                    }
                }else{
                    weekWorkTime+=0
                };
            };
            // get isOver, weekPay
            let isOver = 'O';
            let classPay = 9875;
            let weekPay = 0;
            if(weekWorkTime>=15){
                isOver = 'O';
                weekPay = Math.round((weekWorkTime * 1.2 + nightWorkTime * 0.5) * classPay);
            }else{
                isOver = 'X';
                weekPay = Math.round((weekWorkTime + nightWorkTime * 0.5) * classPay);
            };
            wt_table_body_html += `
                <tr>
                    <td>연동x</td>
                    <td>${sortedKeys[w]}</td>
                    <td>연동x</td>
                    <td>${weekWorkTime} h</td>
                    <td>${nightWorkTime} h</td>
                    <td>${isOver}</td>
                    <td>${numberWithCommas(weekPay)} ₩</td>
                </tr>
            `;
            total.weekWorkTime += weekWorkTime;
            total.nightWorkTime += nightWorkTime;
            total.weekPay += weekPay;
        };
        wt_table_body_html = `
            <tr class="bold">
                <td>합계</td>
                <td></td>
                <td></td>
                <td>${total.weekWorkTime} h</td>
                <td>${total.nightWorkTime} h</td>
                <td></td>
                <td>${numberWithCommas(total.weekPay)} ₩</td>
            </tr>
        ` + wt_table_body_html;
        wt_table_body.innerHTML = wt_table_body_html;
        // console.log(sortedJson);
        // const worksheet = XLSX.utils.json_to_sheet(sortedJson);
        // const workbook = XLSX.utils.book_new();
        // XLSX.utils.book_append_sheet(workbook, worksheet, "ExcelExport");
    });

    // when file uploaded, hide button and show result.
    excel_upload_btn.style.display = "none";
    wt_table.style.display = "block";
};