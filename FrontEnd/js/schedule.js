import { 
    numberWithCommas,
    halfMinuteConverter,
    numToHourMinuteConverter
} from "./util.js";

const Excel = document.getElementById("excel-upload");
const weekPayTable = document.getElementById("weekPayTable");
const excel_upload_btn = document.querySelector("label");
const roster = document.getElementById("roster");
const roster_print_btn = document.getElementById("rosterPrint");

// get member list from: db > java > html > js

const memberList = [];
const mNameElements = document.querySelectorAll('.hidden .mname');
const mGradeElements = document.querySelectorAll('.hidden .mgrade');

mNameElements.forEach((nameElement, index) => {
    const memberName = nameElement.innerText;
    const memberGrade = mGradeElements[index].innerText;

    memberList.push({
        name: memberName,
        grade: memberGrade
    });
});

// const memberList = [
//     {name: '김장현', grade: 'SM'},
//     {name: '김해수', grade: 'VSM'},
//     {name: '최인화', grade: 'MGR'},
//     {name: '유건희', grade: 'CT'},
//     {name: '이희정', grade: 'CT'},
//     {name: '강민지', grade: 'EMP'},
//     {name: '권태영', grade: 'PT'},
//     {name: '김경민', grade: 'PT'},
//     {name: '김무준', grade: 'PT'},
//     {name: '김세희', grade: 'PT'},
//     {name: '김영록', grade: 'PT'},
//     {name: '김은경', grade: 'PT'},
//     {name: '김지환', grade: 'PT'},
//     {name: '박대용', grade: 'PT'},
//     {name: '박현선', grade: 'PT'},
//     {name: '복금현', grade: 'PT'},
//     {name: '서준영', grade: 'PT'},
//     {name: '안지연', grade: 'PT'},
//     {name: '원동하', grade: 'PT'},
//     {name: '유영현', grade: 'PT'},
//     {name: '윤승관', grade: 'PT'},
//     {name: '이상건', grade: 'PT'},
//     {name: '이영현', grade: 'PT'},
//     {name: '이재원', grade: 'PT'},
//     {name: '전예준', grade: 'PT'},
//     {name: '조경서', grade: 'PT'},
//     {name: '조관우', grade: 'PT'},
//     {name: '홍지오', grade: 'PT'}
// ];

// convert schedule excel > json
const excelToJson = async (callback) => {
    let returnData;
    await readXlsxFile(Excel.files[0]).then(data=>{
        // code for work table body
        // data > Json convert as I want
        const Json = [];
        let tableDate = [];
        for(let i = 0; i < data.length; i++){
            // data[i][0], data[i][11] = 이름
            if(
                memberList.map(el=>el.name).includes(String(data[i][0]).split('\n')[0])
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
                memberList.map(el=>el.name).includes(String(data[i][11]).split('\n')[0])
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
            if(String(data[i][1])==="주차" && tableDate.length < 7){
                for(let d = 0; d < 7; d++){
                    tableDate.push(String(data[i][d+2]));
                }
            };
        };
        // sort converted Json as key by 가나다
        const sortedJson = {};
        const sortedKeys = Object.keys(Json).sort((a,b)=>(a<b)?-1:(a==b)?0:1);
        sortedKeys.forEach(key=>{
            sortedJson[key] = Json[key];
        });
        returnData = sortedJson;
        returnData.tableDate = tableDate;
    });
    callback(returnData)
};

// run. if detected excel upload
Excel.onchange = () => {
    excelToJson( data => {
        delete data['tableDate'];
        const sortedJson = data;
        const sortedJson_key = Object.keys(sortedJson);
        // code for work table head
        let weekPayTable_head_html = `
            <thead>
                <tr>
                <th scope="col">직급</th>
                <th scope="col">이름</th>
                <th scope="col">닉네임</th>
                <th scope="col">총 근무 시간</th>
                <th scope="col">야간 근무 시간</th>
                <th scope="col">주휴 발생 여부</th>
                <th scope="col">주급</th>
                </tr>
            </thead>
        `;
        // Insert converted Json to table
        let weekPayTable_body_html = "";
        const total = {
            weekWorkTime: 0,
            nightWorkTime: 0,
            weekPay: 0
        };
        for(let w = 0; w < sortedJson_key.length; w++){
            // get weekly work time, night work time
            let weekWorkTime = 0;
            let nightWorkTime = 0;
            for(let d = 0; d < 7; d++){
                let workData = sortedJson[sortedJson_key[w]][d];
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
            weekPayTable_body_html += `
                <tr>
                    <td>${memberList.find(el=>el.name === sortedJson_key[w]).grade}</td>
                    <td>${sortedJson_key[w]}</td>
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
        weekPayTable_body_html = `
            <tbody>
                <tr class="bold">
                    <td>합계</td>
                    <td></td>
                    <td></td>
                    <td>${total.weekWorkTime} h</td>
                    <td>${total.nightWorkTime} h</td>
                    <td></td>
                    <td>${numberWithCommas(total.weekPay)} ₩</td>
                </tr>
                ${weekPayTable_body_html}
            </tbody>
        `;
        weekPayTable.innerHTML = weekPayTable_head_html + weekPayTable_body_html;
    });
    // when file uploaded, hide button and show result.
    excel_upload_btn.style.display = "none";
    weekPayTable.style.display = "block";
    roster.style.display = "block";
    excelToJson( data => {
        const tableDate =  data.tableDate;
        delete data['tableDate'];
        const sortedJson = data;
        const sortedJson_key = Object.keys(data);
        const sortedJson_value = Object.values(data);
        let rosterTable_head_html = `
            <thead>
                <tr>
                    <th rowspan="2">NO</th>
                    <th rowspan="2">성명</th>
                    <th rowspan="2">Nick Name</th>
                    <th rowspan="2">직무</th>
                    <th colspan="3">스케줄</th>
                    <th colspan="3">실근무시간</th>
                    <th rowspan="2">비고</th>
                </tr>
                <tr>
                    <th rowspan="1">출근</th>
                    <th rowspan="1">퇴근</th>
                    <th rowspan="1">휴게</th>
                    <th rowspan="1">출근</th>
                    <th rowspan="1">퇴근</th>
                    <th rowspan="1">휴게</th>
                </tr>
            </thead>
        `;
        // convert shape of json data
        for(let date = tableDate.length-1; date >= 0; date--){
            const rosterbody = [];
            for(let memb = 0; memb < sortedJson_value.length; memb++){
                if(sortedJson_value[memb][date]!=null){
                    let tempArr = [
                        sortedJson_key[memb],
                        '',
                        ''
                    ];
                    let time_start = sortedJson_value[memb][date][0];
                    let time_finish = sortedJson_value[memb][date][1];
                    if(sortedJson_value[memb][date][1]-sortedJson_value[memb][date][0])
                    tempArr.push(
                        time_start,
                        time_finish,
                        time_finish-time_start>=9?"01:00":"00:30",
                        '',
                        '',
                        '',
                        ''
                    );
                    rosterbody.push(tempArr);
                };
            };
            let sortedRosterbody = rosterbody.sort((a, b)=>a[3] - b[3]);
            sortedRosterbody.map((el,i)=>el.unshift(i+1))
            // create html table for excel sheet
            let rosterTable_body_html = ``;
            for(let b = 0; b < sortedRosterbody.length; b++){
                let temp_html = ``;
                for(let t = 0; t < Object.keys(sortedRosterbody[0]).length; t++){
                    if(t===4 || t===5){
                        temp_html += `<td>${numToHourMinuteConverter(Object.values(sortedRosterbody[b])[t])}</td>`;
                    }else{
                        temp_html += `<td>${Object.values(sortedRosterbody[b])[t]}</td>`;
                    }
                };
                rosterTable_body_html += `<tr>${temp_html}</tr>`;
            };
            rosterTable_body_html = `<tbody>${rosterTable_body_html}</tbody>`;
            roster.insertAdjacentHTML('beforeend',`
                <div class="rosterPage">
                    <div class="rosterHeader">FOH Roster</div>
                    <div class="rosterDate">근무일자 : 2024년 ${tableDate[date]}</div>
                    <table class="rosterTable">
                        ${rosterTable_head_html}${rosterTable_body_html}
                    </table>
                </div>
            `);
        };
    });
};

// code for export roster sheet
roster_print_btn.addEventListener("click", () => {
    window.print();
});