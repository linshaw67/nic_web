function toSignIn(){
    $("#sign-in-entries").fadeIn();
    $("#sign-in-inputs").fadeIn();
    $("#sign-up-entries").hide();
    $("#sign-up-inputs").hide();
    $("#sign-in")
        .css({
            "color": "#000",
            "font-size": "22px",
            "font-family": "Trajan Pro Bold",
            "cursor": "default"
        })
        .off("click", toSignIn);
    $("#sign-up")
        .css({
            "color": "#d1a867",
            "font-size": "20px",
            "font-family": "Trajan Pro Regular",
            "cursor": "pointer"
        })
        .on("click",toSignUp);
    $("#sign-submit").text("SIGN IN");
}
function toSignUp(){
    $("#sign-in-entries").hide();
    $("#sign-in-inputs").hide();
    $("#sign-up-entries").fadeIn();
    $("#sign-up-inputs").fadeIn();
    $("#sign-up")
        .css({
            "color": "#000",
            "font-size": "22px",
            "font-family": "Trajan Pro Bold",
            "cursor": "default"
        })
        .off("click", toSignUp);
    $("#sign-in")
        .css({
            "color": "#d1a867",
            "font-size": "20px",
            "font-family": "Trajan Pro Regular",
            "cursor": "pointer"
        })
        .on("click",toSignIn);
    $("#sign-submit").text("SIGN UP");
};
$(document).ready(function(){
    $("#sign-up").on("click",toSignUp);
    $("#sign-submit").click(function(){
        if ($(this).text() == "SIGN IN"){
            $.ajax({
                method: "post",
                url: "./login",
                data:{
                    "username": $("#sign-in-entries input[type=text]").val(),
                    "pwd": $("#sign-in-entries input[type=password]").val()
                },
                success: function(response){
                    if (response["status"] == -1){

                    }
                }
            })
        }
        else if ($(this).text() == "SIGN UP"){
            if ($("#sign-up-inputs input[type=password]").eq(0).val() == $("#sign-up-inputs input[type=password]").eq(1).val()){
                $.ajax({
                    method: "post",
                    url: "./register",
                    data: {
                        "username": $("#sign-up-entries input[type=text]").val(),
                        "pwd": $("#sign-up-entries input[type=password]").eq(0).val()
                    },
                    success: function(response){

                    }
                });
            }
            else {
                $("#pwdnotmatch").fadeIn();
            }

        }
    });
    $("#sign-info input").keydown(function(){
        $(".sign-alert").fadeOut();
    });
    $("#sign").click(function(){
        $("#sign-box")
            .fadeIn()
            .css({
                "top": $(window).scrollTop() + Math.max(($(window).height() - $("#sign-box").height())/2,40) + "px",
                "left": ($("body").width() - $("#sign-box").width())/2 + "px",
                "z-index": "11"
            });
        $("<div></div>")
            .appendTo("body")
            .css({
                "width": $("body").width() + "px",
                "height": $("body").height() + "px",
                "position": "absolute",
                "top": "0px",
                "left": "0px",
                "background-color": "#000",
                "opacity": "0.25",
                "z-index": "10"
            })
            .click(function(){
                $(this).remove();
                $("#sign-box").hide();
            });
    });
});