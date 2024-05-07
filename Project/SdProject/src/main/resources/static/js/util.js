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