// import { numberWithCommas } from "./util.js";

const navbar = document.getElementById("navbar");

let navbar_html = `
    <i id="openNav" class="fa-solid fa-bars fa-lg"></i>
    <div id="hiddenMenu">
        <div id="navHeader">
            <i id="closeNav" class="fa-solid fa-bars fa-lg"></i>
            <img src="../img/logo.png" />
        </div>
        <a href="/">메인</a>
        <a href="/memberpage">멤버</a>
    </div>
`;

navbar.innerHTML = navbar_html;