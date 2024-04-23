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

<!-- nav 스타일 -->
document.addEventListener('DOMContentLoaded', function() {
    // 현재 URL 가져오기
    var currentURL = window.location.href;
    var navButtons = document.querySelectorAll('.nav_btn');

    // 특정 URL에 대한 특별한 스타일 적용
    if (currentURL === 'http://localhost:8080/') {
        // 버튼 중에서 Home 버튼에만 특별한 클래스 추가
        navButtons[0].classList.add('special_nav_btn');
    }
    else if (currentURL === 'http://localhost:8080/post') {
        // 버튼 중에서 Post 버튼에만 특별한 클래스 추가
        navButtons[1].classList.add('special_nav_btn');
        navButtons[0].classList.add('nav_btn');
    }
    else if (currentURL === 'http://localhost:8080/#') {
        // 버튼 중에서 Portfolio 버튼에만 특별한 클래스 추가
        navButtons[2].classList.add('special_nav_btn');
    }
});
