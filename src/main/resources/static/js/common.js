let container = document.getElementById('line_container_div');
let Lines = 9;
let l = 10;
let n = 2;
for (let i = 1; i < Lines; i++) {
    const line = document.createElement('div');
    line.style.height = `calc(86vh - ${i * 10}vh`; // height
    line.style.marginTop = `calc(10px + ${4.9 * 10 * i}px)`; // margin-top
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




//
// let currentURL = window.location.href;
//
// // 특정 URL에 따라 CSS 클래스를 추가하여 스타일 변경
// if (currentURL.includes("")) {
//     document.getElementById("nav_btn").classList.add("page1-style");
// } else if (currentURL.includes("http://localhost:8080/post")) {
//     document.getElementById("nav_btn").classList.add("page2-style");
// }
// var currentURL = window.location.href;
//
// if (currentURL.includes("/")) {
//     var contentElement = document.getElementById("content");
//     if (contentElement) {
//         contentElement.classList.add("page1-style");
//     }
// } else if (currentURL.includes("/post")) {
//     var contentElement = document.getElementById("content");
//     if (contentElement) {
//         contentElement.classList.add("page2-style");
//     }
// }


// var currentURL = window.location.href;
//
// if (currentURL.includes("/")) {
//     var contentElement = document.getElementById("nav_btn");
//     if (contentElement) {
//         contentElement.classList.add("page1-style");
//     }
// } else if (currentURL.includes("/post")) {
//     var contentElement = document.getElementById("nav_btn");
//     if (contentElement) {
//         contentElement.classList.add("page2-style");
//     }
// }

// target.style.
// $.ajax({
//     type: "get",
//     url: "/",
//     data:JSON.stringify(data), // javascript object인 data를 json 형식으로 변환해서 java가 인식할 수 있도록 준비함
//     dataType: "json",
//     success:{
//         // 정상 요청, 응답 시 처리
//     },
//     error: function(xhr,status,error) {
//         // 오류 발생 시 처리
//     },
//     complete:function(data,textStatus) {
//         // 작업 완료 후 처리
//     }
// });