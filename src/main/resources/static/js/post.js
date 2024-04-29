let post = {
    init : function () {
        $("#btn-save").on("click", () => {
            this.postSave();
        });
        $("#btn-edit").on("click",()=>{
            this.postEdit();
        });
        $("#btn-delete").on("click",()=>{
            this.postDelete();
        });
        $("#btn-save-comment").on("click",()=>{
            this.commentSave();
        });
        $(".btn-edit-comment").on("click",(e)=>{
            this.aboutAllCommentBtn(e);
        });
        $(".btn-edit-save-comment").on("click",(e)=>{
            this.aboutAllCommentBtn(e);
        });
        $(".btn-edit-cancel-comment").on("click",(e)=>{
            this.aboutAllCommentBtn(e);
        });
        $(".btn-delete-comment").on("click",(e)=>{
            this.aboutAllCommentBtn(e);
        });
    },

    postSave : function() {
        let checkBox = document.getElementById("hiddenStat");
        let isHiddenChecked = checkBox.checked;
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
            tagString: $("#tagString").val(),
            uuid: $("#uuid").val(),
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
    },

    postEdit : function() {
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

    postDelete : function() {
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
    },

    commentSave : function() {
        let data = {
            postId:  $("#id").val(),
            commentContent: $("#comment-content").val()
        }

        $.ajax({
            type:"post",
            url:"/comment",
            data:JSON.stringify(data), // javascript object인 data를 json 형식으로 변환해서 java가 인식할 수 있도록 준비함
            contentType:"application/json; charset=utf-8", // http body 데이터가 어떤 타입인지(MIME)
            dataType:"json" // 요청에 대한 응답이 왔을 때 기본적으로 문자열(생긴게 json이라면)=> javascript object로 변경해줌
        }).done(function (resp){
            if(resp.status===200){
                alert("댓글 작성 완료");
                location.href = "/post/" + data.postId;
            } else {
                alert("댓글 작성 실패");
            }
        }).fail(function (error){
            alert(error);
        });
    },

    aboutAllCommentBtn : function(e){
        let parentRow = $(e.target).closest(".comment-row");
        let commentId = parentRow.children("input[type='hidden']").val();
        let originBox = document.getElementById("originBox"+commentId);
        let editBox = document.getElementById("editBox"+commentId);

        let className = e.target.classList[0];
        if(className === "btn-edit-comment"){
            originBox.classList.add("non-active");
            editBox.classList.remove("non-active");
        } else if(className === "btn-edit-save-comment"){
            this.commentEdit(commentId);
            originBox.classList.remove("non-active");
            editBox.classList.add("non-active");
        } else if(className === "btn-edit-cancel-comment"){
            originBox.classList.remove("non-active");
            editBox.classList.add("non-active");
        } else if(className === "btn-delete-comment"){
            this.commentDelete(commentId);
        }

    },

    commentEdit : function(commentId){
        let data = {
            postId:  $("#id").val(),
            commentContent: document.getElementById("saved-comment"+commentId).value,
            commentId: commentId
        }

        $.ajax({
            type:"put",
            url:"/comment",
            data:JSON.stringify(data), // javascript object인 data를 json 형식으로 변환해서 java가 인식할 수 있도록 준비함
            contentType:"application/json; charset=utf-8", // http body 데이터가 어떤 타입인지(MIME)
            dataType:"json" // 요청에 대한 응답이 왔을 때 기본적으로 문자열(생긴게 json이라면)=> javascript object로 변경해줌
        }).done(function (resp){
            if(resp.status===200){
                alert("댓글 수정 완료");
                location.href = "/post/" + data.postId;
            } else {
                alert("댓글 수정 실패");
            }
        }).fail(function (error){
            alert(error);
        });
    },

    commentDelete : function(commentId){
        let data = {
            postId:  $("#id").val(),
            commentId: commentId
        }

        $.ajax({
            type:"delete",
            url:"/comment",
            data:JSON.stringify(data), // javascript object인 data를 json 형식으로 변환해서 java가 인식할 수 있도록 준비함
            contentType:"application/json; charset=utf-8", // http body 데이터가 어떤 타입인지(MIME)
            dataType:"json" // 요청에 대한 응답이 왔을 때 기본적으로 문자열(생긴게 json이라면)=> javascript object로 변경해줌
        }).done(function (resp){
            if(resp.status===200){
                alert("댓글 삭제 완료");
                location.href = "/post/" + data.postId;
            } else {
                alert("댓글 삭제 실패");
            }
        }).fail(function (error){
            alert(error);
        });
    }
}

post.init();


// 배열을 문자열로 변환하여 UUID를 생성하는 함수
function arrayToUUID(array) {
    return (
        array.slice(0, 4).map(byte => byte.toString(16).padStart(2, '0')).join('') + '-' +
        array.slice(4, 6).map(byte => byte.toString(16).padStart(2, '0')).join('') + '-' +
        array.slice(6, 8).map(byte => byte.toString(16).padStart(2, '0')).join('') + '-' +
        array.slice(8, 10).map(byte => byte.toString(16).padStart(2, '0')).join('') + '-' +
        array.slice(10, 16).map(byte => byte.toString(16).padStart(2, '0')).join('')
    );
}

let uuidStr = (function() {
    let array = new Uint8Array(16);
    crypto.getRandomValues(array);
    return arrayToUUID(array);
})();

$('.summernote').summernote({
    tabsize: 2,
    height: 300,
    focus: true,
    lang: "ko-KR",
    placeholder: '최대 2048자까지 쓸 수 있습니다',
    callbacks: {	//여기 부분이 이미지를 첨부하는 부분
        onImageUpload : function(files) {
            uploadSummernoteImageFile(files[0],this);
        },
        onPaste: function (e) {
            var clipboardData = e.originalEvent.clipboardData;
            if (clipboardData && clipboardData.items && clipboardData.items.length) {
                var item = clipboardData.items[0];
                if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                    e.preventDefault();
                }
            }
        }
    }
});

function uploadSummernoteImageFile(file, editor) {
    data = new FormData();
    data.append("file", file);
    data.append("uuid",uuidStr);
    $.ajax({
        data : data,
        type : "POST",
        url : "/img",
        contentType : false,
        processData : false,
        success : function(data) {
            //항상 업로드된 파일의 url이 있어야 한다.
            $(editor).summernote('insertImage', data.url);
        }
    });
}