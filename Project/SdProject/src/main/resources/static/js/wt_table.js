import { numberWithCommas } from "./util.js";

const wt_table_body = document.querySelector("tbody");

let wt_table_html = "";
for(let w = 0; w < 20; w++){
    wt_table_html += `
        <tr>
            <td>알바</td>
            <td>전예준</td>
            <td>Jack</td>
            <td>20 h</td>
            <td>0 h</td>
            <td>O</td>
            <td>${numberWithCommas(111111111)} ₩</td>
        </tr>
    `;
};

wt_table_html += `
    <tr>
        <td>합산</td>
        <td></td>
        <td></td>
        <td>200 h</td>
        <td>30 h</td>
        <td></td>
        <td>${numberWithCommas(111111111)} ₩</td>
    </tr>
`;

wt_table_body.innerHTML = wt_table_html;