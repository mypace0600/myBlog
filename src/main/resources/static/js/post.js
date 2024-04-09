let index = {
    init : function (){
        $("#btn-save").on("click",()=>{
            this.save();
        });

    }

    save : function (){
        let checkBox = document.getElementById("hiddenStat");
        let isHiddenChecked = checkBox.checked;

        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
            hidden: isHiddenChecked
        };

        $.ajax({
            type:"POST",
            url:"/post/write",
            data:JSON.stringify(data), // javascript object인 data를 json 형식으로 변환해서 java가 인식할 수 있도록 준비함
            contentType:"application/json; charset=utf-8", // http body 데이터가 어떤 타입인지(MIME)
            dataType:"json" // 요청에 대한 응답이 왔을 때 기본적으로 문자열(생긴게 json이라면)=> javascript object로 변경해줌
        }).done(function (resp){
            if(resp.status===500){
                alert("글쓰기 실패");
            } else {
                alert("글쓰기 완료");
            }
            location.href="/";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
}