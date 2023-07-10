let index = {
    init: function(){
        // on(event, do)
        $("#btn-save").on("click", ()=>{
            this.save();
        });
    },
    save: function(){
//        alert('user의 save함수 호출됨');
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };
        $.ajax({
            type: "POST",
            url: "/blog/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8", // 요청 body 데이터 타입(MIME 방식)
            dataType: "json" // 서버에서 응답하는 문자열의 형태가 json이면 javascript object로 변경해 줌.
        }).done(function(res){
            alert("회원가입이 완료되었습니다.");
            console.log(res);
            location.href="/blog";
        }).fail(function(error){
            alert("JSON.stringify(error)")
        });
    }
}

index.init();