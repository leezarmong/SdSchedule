export function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};
export function halfMinuteConverter(x){
    return x.includes('.')?Number(x.split('.')[0])+Math.round(Number(x.split('.')[1])/6*100)/100:Number(x)
};