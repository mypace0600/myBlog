let common = {
    init : function () {
        document.addEventListener("DOMContentLoaded", this.drawLines);
        document.addEventListener("DOMContentLoaded", this.urlCheck);
    },

    drawLines : function (){
        let container = document.getElementById('line_container_div');
        let Lines = 9;
        let l = 10;
        let n = 2;
        for (let i = 1; i < Lines; i++) {
            const line = document.createElement('div');
            line.style.height = `calc(86vh - ${i*10}vh`; // height
            line.style.marginTop = `calc(10px + ${4.9*10*i}px)`; // margin-top
            line.style.marginLeft = `calc(0px + ${1}vw)`; // fallfrom left
            line.style.width = '1px';
            container.appendChild(line);
        }
    },

    urlCheck: function (){
        let url = window.location.pathname;
        let id = "nav_"+url.replace("/","");
        console.log(id);
        let currentNavBtn = document.getElementById(id);
        currentNavBtn.classList.add("special_nav_btn");
    }
}


common.init();

