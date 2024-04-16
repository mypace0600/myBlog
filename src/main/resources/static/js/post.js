let post = {
    init : function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
        $("#btn-edit").on("click",()=>{
            this.edit();
        });
        $("#btn-delete").on("click",()=>{
            this.delete();
        })
    },

    save : function (){
        let checkBox = document.getElementById("hiddenStat");
        let isHiddenChecked = checkBox.checked;
        console.log("isHiddenChecked : "+isHiddenChecked);
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
            tagString: $("#tagString").val(),
            hidden: isHiddenChecked
        };
        console.log("data : "+JSON.stringify(data));
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
    },

    edit : function(){
        let checkBox = $("#hiddenStat").val();
        let hidden = false;
        if(checkBox==="on") {
            hidden = true;
        }
        let data = {
            id:  $("#id").val(),
            title: $("#title").val(),
            content: $("#content").val(),
            tagString: $("#tagString").val(),
            hidden:hidden
        };
        console.log(data.hidden);

        $.ajax({
            type:"post",
            url:"/post/edit",
            data:JSON.stringify(data), // javascript object인 data를 json 형식으로 변환해서 java가 인식할 수 있도록 준비함
            contentType:"application/json; charset=utf-8", // http body 데이터가 어떤 타입인지(MIME)
            dataType:"json" // 요청에 대한 응답이 왔을 때 기본적으로 문자열(생긴게 json이라면)=> javascript object로 변경해줌
        }).done(function (resp){
            if(resp.status===200){
                alert("글 수정 완료");
                location.href="/";
            } else {
                alert("글 수정 실패!!!!!!");
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },

    delete : function (){
        let checkBox = $("#hiddenStat").val();
        let hidden = false;
        if(checkBox==="on") {
            hidden = true;
        }
        let data = {
            id:  $("#id").val(),
            title: $("#title").val(),
            content: $("#content").val(),
            tagString: $("#tagString").val(),
            hidden:hidden
        };

        console.log(data.hidden);
        $.ajax({
            type:"post",
            url:"/post/delete",
            data:JSON.stringify(data), // javascript object인 data를 json 형식으로 변환해서 java가 인식할 수 있도록 준비함
            contentType:"application/json; charset=utf-8", // http body 데이터가 어떤 타입인지(MIME)
            dataType:"json" // 요청에 대한 응답이 왔을 때 기본적으로 문자열(생긴게 json이라면)=> javascript object로 변경해줌
        }).done(function (resp){
            if(resp.status===200){
                alert("글 삭제 완료");
                location.href="/";
            } else {
                alert("글 삭제 실패!!!!!!");
            }
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }

}

post.init();