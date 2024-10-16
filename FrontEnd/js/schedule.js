import { 
    numberWithCommas,
    halfMinuteConverter,
    numToHourMinuteConverter,
    getDateFromExcel,
    getDayFromExcel,
    isNumber
} from "./util.js";

const Excel = document.getElementById("excel-upload");
const weekPayTable = document.getElementById("weekPayTable");
const payPerDayTable = document.getElementById("payPerDayTable");
const excel_upload_btn = document.querySelector("label");
const roster = document.getElementById("roster");
const tables = document.getElementById("tables");
const roster_print_btn = document.getElementById("rosterPrint");

const weekend = ["일","월","화","수","목","금","토"];

// get member list from: db > java > html > js

//   const memberList = [];
//   const mNameElements = document.querySelectorAll('.hidden .mname');
//   const mGradeElements = document.querySelectorAll('.hidden .mgrade');
//   const mNElements = document.querySelectorAll('.hidden .sname');
//   const mFElements = document.querySelectorAll('.hidden .F');
//   const mGElements = document.querySelectorAll('.hidden .G');
//   const mMElements = document.querySelectorAll('.hidden .M');
//   const mEElements = document.querySelectorAll('.hidden .E');
//   const mDElements = document.querySelectorAll('.hidden .D');

//   mNameElements.forEach((nameElement, i) => {
//       const memberName = nameElement.innerText;
//       const memberGrade = mGradeElements[i].innerText;
   
      
//      let tempArr = new Array;
//       for(let a = 0; a < mNElements.length; a++){
//         tempArr.push(mNElements[a].innerText);
//       }
//       console.log(tempArr)
//       let index = tempArr.indexOf(memberName);
     
     
//       const memberF = mFElements[index].innerText ? "F" : "";
//       const memberG = mGElements[index].innerText ? "G" : "";
//       const memberM = mMElements[index].innerText ? "M" : "";
//       const memberE = mEElements[index].innerText ? "E" : "";
//       const memberD = mDElements[index].innerText ? "D" : "";

//      memberList.push({
//          name: memberName,
//          grade: memberGrade,
//          position: [memberF, memberG, memberM, memberE, memberD]
//       });
//   });



// 서버리스용 예제 데이터
const memberList = [
    {name: '김장현', grade: 'VSM', position: ['F']},
    {name: '김해수', grade: 'VSM', position: ['F']},
    {name: '최인화', grade: 'MGR', position: ['F','G']},
    {name: '유건희', grade: 'CT', position: ['F','G','M']},
    {name: '이희정', grade: 'CT', position: ['F','G','M','E']},
    {name: '강민지', grade: 'EMP', position: ['F','G','M','E','D']},
    {name: '권태영', grade: 'PT', position: ['F']},
    {name: '김경민', grade: 'PT', position: ['F','G']},
    {name: '김무준', grade: 'PT', position: ['F','G','M']},
    {name: '김세희', grade: 'PT', position: ['F','G','M','E']},
    {name: '김영록', grade: 'PT', position: ['F','G','M','E','D']},
    {name: '김은경', grade: 'PT', position: ['F']},
    {name: '김지환', grade: 'PT', position: ['F','G']},
    {name: '박대용', grade: 'PT', position: ['F','G','M']},
    {name: '박현선', grade: 'PT', position: ['F','G','M','E']},
    {name: '복금현', grade: 'PT', position: ['F','G','M','E','D']},
    {name: '서준영', grade: 'PT', position: ['F']},
    {name: '안지연', grade: 'PT', position: ['F','G']},
    {name: '원동하', grade: 'PT', position: ['F','G','M']},
    {name: '유영현', grade: 'PT', position: ['F','G','M','E']},
    {name: '윤승관', grade: 'PT', position: ['F','G','M','E','D']},
    {name: '이상건', grade: 'PT', position: ['F']},
    {name: '이영현', grade: 'PT', position: ['F','G']},
    {name: '이재원', grade: 'PT', position: ['F','G','M']},
    {name: '전예준', grade: 'PT', position: ['F','G','M','E']},
    {name: '조경서', grade: 'PT', position: ['F','G','M','E','D']},
    {name: '조관우', grade: 'PT', position: ['F']},
    {name: '홍지오', grade: 'PT', position: ['F','G']}
];

// convert schedule excel > json
const excelToJson = async (callback) => {
    let returnData;
    await readXlsxFile(Excel.files[0]).then(data => {
        // code for work table body
        // data > Json convert as I want
        const Json = {};
        const Loss = []; // db상에 없지만 엑셀 상에 존재했던 이름들을 저장하는 배열
        let tableDate = [];
        // loop as count of data's row
        for (let i = 0; i < data.length; i++) {
            if (i == 50) { break }
            let tempName = '';  // 공백과 엔터를 기준으로 자른 이름이라 분류된 문자
            let arrid = ''; // DB에 존재하는 이름이면 해당 변수에 저장
            let Qid = ''; // DB에 존재하지 않는 이름이면 해당 변수에 저장
            // 시간표가 1번째 줄(a(=0)), 12번째 줄(l(=11)) 2곳에서 각기 시작하므로 2번 반복
            for (let f = 0; f < 2; f++) {
                // \n 또는 공백이 있을 경우 해당 문자를 기준으로 앞쪽에 있던 글자를 arrid 에 저장
                // \n 또는 공백이 없을 경우 arrid 를 빈문자로 선언
                const nameCell = (                                                      // 엑셀에서 이름이 들어갔을 칸에 있는 데이터
                    !isNumber(data[i][11 * f]) && data[i][11 * f] != null                   // 그 값이 숫자거나 null 이면 일단 배제
                    && data[i][11 * f] != "OP Shift" && data[i][11 * f] != "CL Shift"       // 그 값이 OP Shift, CL Shift 여도 배제
                ) ? String(data[i][11 * f]) : '';
                let spaceIndex = nameCell.indexOf(' ');
                let enterIndex = nameCell.indexOf('\n');
                if (spaceIndex > 0 && enterIndex > 0) {
                    tempName = (spaceIndex > enterIndex) ? nameCell.split(' ')[0] : nameCell.split('\n')[0];
                } else if (spaceIndex > 0 && enterIndex < 0) {
                    tempName = nameCell.split(' ')[0];
                } else if (spaceIndex < 0 && enterIndex > 0) {
                    tempName = nameCell.split('\n')[0];
                } else if (spaceIndex < 0 && enterIndex < 0) {
                    tempName = nameCell;
                };
                if (
                    memberList.map(el => el.name).includes(tempName)
                ) {
                    arrid = tempName;
                } else {
                    Qid = tempName;
                };
                // arrid 가 빈문자가 아닌경우
                if (
                    arrid != ''
                ) {
                    Json[arrid] = [];
                    const tempArr = [];
                    // 해당 row의 2~9번째(2번째 실행일경우 13~20번째) column을 읽음
                    for (let j = 11 * f + 2; j < 11 * f + 9; j++) {
                        // 해당 셀이 빈칸이 아니라면
                        if (data[i][j] != null) {
                            // 해당 셀에 ~ 또는 - 이 있을 경우 해당 문자를 기준으로 앞숫자를 시작시각, 뒷숫자를 종료시각에 저장
                            // 시작시각과 종료시각을 배열에 넣고 tempArr에 push
                            // 해당 셀에 ~ 또는 - 이 없을 경우 null 값을 tempArr에 push
                            if (data[i][j].includes("~")) {
                                let time_start = halfMinuteConverter(data[i][j].split('~')[0]);
                                let time_finish = halfMinuteConverter(data[i][j].split('~')[1]);
                                tempArr.push([time_start, time_finish])
                            } else if (data[i][j].includes("-")) {
                                let time_start = halfMinuteConverter(data[i][j].split('-')[0]);
                                let time_finish = halfMinuteConverter(data[i][j].split('-')[1]);
                                tempArr.push([time_start, time_finish])
                            } else {
                                tempArr.push(null)
                            }
                        }
                        // 해당 셀이 빈칸이라면 null 값을 tempArr에 push
                        else {
                            tempArr.push(null)
                        };
                    };
                    // Json 객체에 arrid as key, tempArr as value 로 저장
                    Json[arrid] = tempArr;
                    // arrid 값 초기화
                    arrid = '';
                } else if (
                    Qid != ''
                ) {
                    Loss.push(Qid);
                };
            };
            if (String(data[i][1]) === "주차" && tableDate.length < 7) {
                for (let d = 0; d < 7; d++) {
                    tableDate.push(String(data[i][d + 2]));
                }
            };
        };
        // sort converted Json as key by 가나다
        const sortedJson = {};
        const sortedKeys = Object.keys(Json).sort((a, b) => (a < b) ? -1 : (a == b) ? 0 : 1);
        sortedKeys.forEach(key => {
            sortedJson[key] = Json[key];
        });
        returnData = sortedJson;
        returnData.Loss = Loss;
        returnData.tableDate = tableDate;
    });
    callback(returnData)
};

// run. if detected excel upload
Excel.onchange = () => {
    // 주급 계산 테이블 작성
    excelToJson(data => {
        console.log(data)
        const Loss = data.Loss;
        if (Loss.length > 0) {
            let lossList = Loss.map(el => `"${el}"`).join(", ");
            alert(`
등록되지 않은 멤버가 있습니다.
MemberPage에서 신규 인원을 등록 해야
Roster 에 반영됩니다.

<등록되지 않은 멤버>
${lossList}
            `);
            // Open the popup window first
            const width = 1000;
            const height = 600;
            const left = (screen.width / 2) - (width / 2);
            const top = (screen.height / 2) - (height / 2);
            const popup = window.open('', 'addrenewal', `width=${width},height=${height},top=${top},left=${left}`);

            // Delay the form creation and submission to keep the popup context
            setTimeout(() => {
                // Create a form dynamically
                let form = document.createElement('form');
                form.method = 'POST';
                form.action = '/addrenewal';
                form.enctype = 'multipart/form-data';

                // Create a file input element
                let fileInput = document.createElement('input');
                fileInput.type = 'file';
                fileInput.name = 'file';

                // Append the form to the body first
                document.body.appendChild(form);

                // Set the file input to the actual file
                let files = document.getElementById('excel-upload').files;
                if (files.length > 0) {
                    const dataTransfer = new DataTransfer();
                    dataTransfer.items.add(files[0]);
                    fileInput.files = dataTransfer.files;

                    // Append the file input to the form
                    form.appendChild(fileInput);

                    // Submit the form in the opened popup
                    form.target = 'addrenewal';
                    form.submit();
                }
            }, 0);
        }
        console.log(Loss);
        delete data['Loss'];
        delete data['tableDate'];
        const sortedJson = data;
        console.log(sortedJson);
        const sortedJson_key = Object.keys(sortedJson);
        const sortedJson_value = Object.values(sortedJson);
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
        for (let w = 0; w < sortedJson_key.length; w++) {
            // get weekly work time, night work time
            let weekWorkTime = 0;
            let nightWorkTime = 0;
            for (let d = 0; d < 7; d++) {
                let workData = sortedJson[sortedJson_key[w]][d];
                if (workData != null) {
                    if (workData[1] - workData[0] >= 9) {
                        weekWorkTime += workData[1] - workData[0] - 1;
                    } else {
                        weekWorkTime += workData[1] - workData[0] - 0.5;
                    }
                    if (workData[1] === 22.5) {
                        nightWorkTime += 0.5;
                    }
                } else {
                    weekWorkTime += 0;
                }
            }
            // get isOver, weekPay
            let isOver = 'O';
            let classPay = 9875;
            let weekPay = 0;
            if (weekWorkTime >= 15) {
                isOver = 'O';
                weekPay = Math.round((weekWorkTime * 1.2 + nightWorkTime * 0.5) * classPay);
            } else {
                isOver = 'X';
                weekPay = Math.round((weekWorkTime + nightWorkTime * 0.5) * classPay);
            }
            weekPayTable_body_html += `
                <tr>
                    <td>${memberList.find(el => el.name === sortedJson_key[w]).grade}</td>
                    <td>${sortedJson_key[w]}</td>
                    <td>연동x</td>
                    <td>${Math.round(weekWorkTime * 10) / 10} h</td>
                    <td>${Math.round(nightWorkTime * 10) / 10} h</td>
                    <td>${isOver}</td>
                    <td>${numberWithCommas(weekPay)} ₩</td>
                </tr>
            `;
            total.weekWorkTime += weekWorkTime;
            total.nightWorkTime += nightWorkTime;
            total.weekPay += weekPay;
        }
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
        // code for payPerDayTable head
        let payPerDayTable_head_html = `
            <tr>
                <th scope="col">요일</th>
                <th scope="col">급여</th>
            </tr>
        `;
        let payPerDayTable_body_html = ``;
        for (let d = 0; d < 7; d++) {
            const dayJson = sortedJson_value.map(el => {
                if (el[d] != null) {
                    return (el[d][1] - el[d][0]);
                } else {
                    return null;
                }
            });
            const sumOfDay = numberWithCommas(Math.round(dayJson.reduce((partialSum, a) => partialSum + a, 0) * 14451));
            payPerDayTable_body_html += `
                <tr>
                    <td>${weekend[d]}</td>
                    <td>${sumOfDay} ₩</td>
                </tr>
            `;
        }
        payPerDayTable.innerHTML = payPerDayTable_head_html + payPerDayTable_body_html;
    });
    // when file uploaded, hide button and show result.
    excel_upload_btn.style.display = "none";
    weekPayTable.style.display = "block";
    payPerDayTable.style.display = "block";
    roster.style.display = "block";
    tables.style.display = "block";
    // 로스터 테이블 작성
    excelToJson(data => {
        const tableDate = data.tableDate;
        delete data['Loss'];
        delete data['tableDate'];
        const sortedJson_key = Object.keys(data);
        const sortedJson_value = Object.values(data);
        let rosterTable_head_html = `
            <thead>
                <tr>
                    <th rowspan="2" style="width: 50px">NO</th>
                    <th rowspan="2" style="width: 200px">성명</th>
                    <th colspan="5" style="width: 150px">포지션 / 라인</th>
                    <th colspan="3" style="width: 300px">스케줄</th>
                    <th colspan="3" style="width: 650px">실근무시간</th>
                    <th rowspan="2" style="width: 350px">비고</th>
                </tr>
                <tr>
                    <th rowspan="1" class="line">F</th>
                    <th rowspan="1" class="line">G</th>
                    <th rowspan="1" class="line">M</th>
                    <th rowspan="1" class="line">E</th>
                    <th rowspan="1" class="line">D</th>
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
        for (let date = 0; date <= tableDate.length - 1; date++) {
            const rosterbody = [];
            for (let memb = 0; memb < sortedJson_value.length; memb++) {
              
                if (sortedJson_value[memb][date] != null) {
                    let lineArr = memberList.find(el=>el.name === sortedJson_key[memb]).position;
                    
                    console.log(sortedJson_key[memb])
                    console.log(memberList.find(el=>el.name == sortedJson_key[memb]))
                    console.log(memberList.find(el=>el.name === sortedJson_key[memb]))
                    
                    
                    console.log(memberList);
                    console.log(lineArr);
                    
                    let tempArr = [
                        sortedJson_key[memb],
                        lineArr.indexOf("F") >= 0 ? "F" : "",
                        lineArr.indexOf("G") >= 0 ? "G" : "",
                        lineArr.indexOf("M") >= 0 ? "M" : "",
                        lineArr.indexOf("E") >= 0 ? "E" : "",
                        lineArr.indexOf("D") >= 0 ? "D" : ""
                    ];
                    
                    
                    let time_start = sortedJson_value[memb][date][0];
                    let time_finish = sortedJson_value[memb][date][1];
                    if (sortedJson_value[memb][date][1] - sortedJson_value[memb][date][0]){
                        let time_rest = Math.floor((time_finish - time_start) / 4.5)
                        tempArr.push(
                            time_start,
                            time_finish,
                            `0${numToHourMinuteConverter(Math.floor((time_finish - time_start) / 4.5) / 2)}`,
                            '',
                            '',
                            '',
                            ''
                        );
                    }
                    rosterbody.push(tempArr);
                }
            }
            let sortedRosterbody = rosterbody.sort((a, b) => a[6] - b[6]);
            sortedRosterbody.map((el, i) => el.unshift(i + 1))
            // create html table for excel sheet
            let rosterTable_body_html = ``;

            // counting members of each times
            let lunchTime = '';
            let dinnerTime = '';
            let countLunch = 0;
            let countDinner = 0;
            for (let b = 0; b < sortedRosterbody.length; b++) {
                let temp_html = ``;
                for (let t = 0; t < Object.keys(sortedRosterbody[0]).length; t++) {
                    if (t === 7 || t === 8) {
                        temp_html += `<td>${numToHourMinuteConverter(sortedRosterbody[b][t])}</td>`;
                    } else {
                        temp_html += `<td>${sortedRosterbody[b][t]}</td>`;
                    }
                }
                rosterTable_body_html += `<tr>${temp_html}</tr>`;
                if(
                    sortedRosterbody[b][1] !== '김은경' &&
                    sortedRosterbody[b][1] !== '김장현'
                ){

                switch (getDayFromExcel(tableDate[date])) {
                    case 0:
                    case 6:
                        // holiday & count dinner
                        lunchTime = "12:00 - 14:30";
                        dinnerTime = "18:00 - 20:00";
                        if(
                            sortedRosterbody[b][7] <= 12 &&
                            sortedRosterbody[b][8] >= 14.5
                        ){
                            countLunch += 1;
                        };
                        if(
                            sortedRosterbody[b][7] <= 18 &&
                            sortedRosterbody[b][8] >= 20
                        ){
                            countDinner += 1;
                        };
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        // not holiday & count lunch
                        lunchTime = "11:30 - 14:00";
                        dinnerTime = "18:00 - 20:00";
                        if(
                            sortedRosterbody[b][7] <= 11.5 &&
                            sortedRosterbody[b][8] >= 14
                        ){
                            countLunch += 1;
                        };
                        if(
                            sortedRosterbody[b][7] <= 18 &&
                            sortedRosterbody[b][8] >= 20
                        ){
                            countDinner += 1;
                        };
                        break;
                }
                }
            }
            rosterTable_body_html = `<tbody>${rosterTable_body_html}</tbody>`;
            roster.insertAdjacentHTML('beforeend', `
                <div class="rosterPage">
                    <div class="rosterHeader">FOH Roster</div>
                    <div class="rosterDate">근무일자 : ${getDateFromExcel(tableDate[date])}</div>
                    <table class="rosterTable">
                        ${rosterTable_head_html}${rosterTable_body_html}
                    </table>
                    <table class="countTable">
                        <tr>
                            <th>LUNCH<div class="countTime">| ${lunchTime}</div></th>
                            <td>${countLunch} 명</td>
                            <th>DINNER<div class="countTime">| ${dinnerTime}</div></th>
                            <td>${countDinner} 명</td>
                        </tr>
                    </table>
                    <div class="notes" style="height: calc(100% - 100px - 32px - (64px + ${32*sortedRosterbody.length}px + 4px) - 26px - 45px);">
                        Notes.
                    </div>
                </div>
            `);
        }
    });
};

// code for export roster sheet
roster_print_btn.addEventListener("click", () => {
    window.print();
});
