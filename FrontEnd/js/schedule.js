import { 
    numberWithCommas,
    halfMinuteConverter
} from "./util.js";

const Excel = document.getElementById("excel-upload");
const weekPayTable = document.getElementById("weekPayTable");
const excel_upload_btn = document.querySelector("label");
const buttons = document.getElementById("buttons");
const excel_download_btn = document.getElementById("excelDownload");
const rosterTable = document.getElementById("rosterTable");

// get member list from: db > java > html > js

/*
const memberList = [];


liElements.forEach( li => {
    const memberName = li.innerText;
    const memberGrade = "";

    memberList.push({
        name: memberName,
        grade: memberGrade
    });
});*/

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

console.log(memberList);


// convert schedule excel > json
const excelToJson = async (callback) => {
    let returnData;
    await readXlsxFile(Excel.files[0]).then(data=>{
        // code for work table body
        // data > Json convert as I want
        const Json = [];
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
        };
        // sort converted Json as key by 가나다
        const sortedJson = {};
        const sortedKeys = Object.keys(Json).sort((a,b)=>(a<b)?-1:(a==b)?0:1);
        sortedKeys.forEach(key=>{
            sortedJson[key] = Json[key];
        });
        returnData = sortedJson;
    });
    callback(returnData)
};

// run. if detected excel upload
Excel.onchange = () => {
    excelToJson( data => {
        const sortedJson = data;
        const sortedJson_key = Object.keys(data);
        console.log(sortedJson)
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
    buttons.style.display = "block";
};

// code for export roster sheet
excel_download_btn.addEventListener("click", () => {
    // window.print();
    // excelToJson( data => {
    //     const sortedJson = data;
    //     const sortedJson_key = Object.keys(data);
    //     const sortedJson_value = Object.values(data);
    //     let rosterTable_head_html = `
    //         <thead>
    //             <tr>
    //                 <th rowspan="2">NO</th>
    //                 <th rowspan="2">성명</th>
    //                 <th rowspan="2">Nick Name</th>
    //                 <th rowspan="2">직무</th>
    //                 <th colspan="3">스케줄</th>
    //                 <th colspan="3">실근무시간</th>
    //                 <th rowspan="2">비고</th>
    //             </tr>
    //             <tr>
    //                 <th rowspan="1">출근</th>
    //                 <th rowspan="1">퇴근</th>
    //                 <th rowspan="1">휴게</th>
    //                 <th rowspan="1">출근</th>
    //                 <th rowspan="1">퇴근</th>
    //                 <th rowspan="1">휴게</th>
    //             </tr>
    //         </thead>
    //     `;

    //     // convert shape of json data
    //     const rosterbody = [];
    //     let i = 0;
    //     for(let memb = 0; memb < sortedJson_value.length; memb++){
            
    //         if(sortedJson_value[memb][0]!=null){
    //             i++;
    //             let tempArr = [
    //                 i,
    //                 sortedJson_key[memb],
    //                 '',
    //                 ''
    //             ];
    //             let time_start = sortedJson_value[memb][0][0];
    //             let time_finish = sortedJson_value[memb][0][1];
    //             if(sortedJson_value[memb][0][1]-sortedJson_value[memb][0][0])
    //             tempArr.push(
    //                 time_start,
    //                 time_finish,
    //                 time_finish-time_start>=9?"01:00":"00:30",
    //                 '',
    //                 '',
    //                 '',
    //                 ''
    //             );
    //             rosterbody.push(tempArr);
    //         };
    //     };

    //     // create html table for excel sheet
    //     let rosterTable_body_html = ``;
    //     for(let b = 0; b < rosterbody.length; b++){
    //         let temp_html = ``;
    //         for(let t = 0; t < Object.keys(rosterbody[0]).length; t++){
    //             temp_html += `<td>${Object.values(rosterbody[b])[t]}</td>`
    //         };
    //         rosterTable_body_html += `<tr>${temp_html}</tr>`;
    //     };
    //     rosterTable_body_html = `<tbody>${rosterTable_body_html}</tbody>`
    //     rosterTable.innerHTML = rosterTable_head_html + rosterTable_body_html;

    //     // change html table to excel sheet        
    //     const workbookbody = XLSX.utils.table_to_book(rosterTable);

    //     const localExcel = XLSX.readFile("../../Excel/sdschedule.xlsx");

    //     XLSX.utils.sheet_add_aoa(worksheet, [excelHeader], { origin: "A7" });
    //     XLSX.utils.book_append_sheet(localExcel, workbookbody, "test");
        
    //     XLSX.writeFile(localExcel, "test.xlsx", { compression: true });
    // });
});