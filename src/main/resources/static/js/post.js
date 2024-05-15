
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
        let hiddenCheckBox = document.getElementById("hiddenStat");
        let isHiddenChecked = hiddenCheckBox.checked;
        let portFolioCheckBox = document.getElementById("portFolioStat");
        let isPortFolioChecked = portFolioCheckBox.checked;
        let data = {
            uuid: $("#uuid").val(),
            title: $("#title").val(),
            content: $("#content").val(),
            tagString: $("#tagString").val(),
            hidden: isHiddenChecked,
            portFolio: isPortFolioChecked
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
            location.href="/post";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },

    postEdit : function() {
        let hiddenCheckBox = document.getElementById("hiddenStat");
        let isHiddenChecked = hiddenCheckBox.checked;
        let portFolioCheckBox = document.getElementById("portFolioStat");
        let isPortFolioChecked = portFolioCheckBox.checked;

        let data = {
            id:  $("#id").val(),
            title: $("#title").val(),
            content: $("#content").val(),
            tagString: $("#tagString").val(),
            hidden:isHiddenChecked,
            portFolio: isPortFolioChecked
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
                location.href="/post";
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
                location.href="/post";
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



$('.summernote').summernote({
    tabsize: 2,
    height: 300,
    focus: true,
    lang: "ko-KR",
    callbacks : {
        onImageUpload : function(files, editor, welEditable) {
            for (let i = 0; i < files.length; i++) {
                sendFile(files[i], this);
            }
        },
        onPaste: function (e) {
            let clipboardData = e.originalEvent.clipboardData;
            if (clipboardData && clipboardData.items && clipboardData.items.length) {
                let item = clipboardData.items[0];
                if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                    e.preventDefault();
                }
            }
        }
    }
});

function sendFile(file, el) {
    let form_data = new FormData();
    form_data.append('file', file);
    let uuid = $("#uuid").val()
    form_data.append('uuid',uuid);

    $.ajax({
        data : form_data,
        type : "POST",
        url : '/image',
        cache : false,
        contentType : false,
        enctype : 'multipart/form-data',
        processData : false,
        success : function(url) {
            $(el).summernote('insertImage', url, function($image) {
                $image.css('width', "50%");
            });
        }
    });
}