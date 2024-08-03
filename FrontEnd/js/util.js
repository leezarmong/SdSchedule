export function numberWithCommas(x) {
    // 1000000 > 1,000,000
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};
export function halfMinuteConverter(x){
    // 13.3 > 13.5
    return x.includes('.')?Number(x.split('.')[0])+Math.round(Number(x.split('.')[1])/6*100)/100:Number(x)
};
export function numToHourMinuteConverter(x){
    // 13.5 > 13:30
    x =  String(x);
    return x.includes('.')?`${Number(x.split('.')[0])}:${Math.round(6*Number(x.split('.')[1]))}`:`${Number(x.split('.')[0])}:00`
};


export function getDateFromExcel(numb) {
    var date = new Date(Date.UTC(1900, 0, 1));
    var date2 = new Date();
    const weekend = ["일", "월", "화", "수", "목", "금", "토"];
    
    date2.setTime(date.getTime() + ((numb - 2) * 24 * 60 * 60 * 1000));
    var dayOfWeek = weekend[date2.getDay()];
    
    
    // J.W 요일 추가.
    return date2.getFullYear() + "년 " + 
           ("0" + (date2.getMonth() + 1)).slice(-2) + "월 " + 
           ("0" + date2.getDate()).slice(-2) + "일 " +
           dayOfWeek + "요일";
};

export function isNumber(value) {
    return typeof value === 'number';
};
// export function getDateBy (numb){
//     var date = new Date();
//     var date2 = new Date();
//     date2.setTime(date.getTime() + (numb * 24 * 60 * 60 * 1000));
//     return date2.getFullYear() + ("0" + (date2.getMonth()+1)).slice(-2) + ("0" + date2.getDate()).slice(-2);
// }