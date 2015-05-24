function logoutState(){
    $("#sign").show();
    $("#userinfo").hide();
    userid = -1;
}

function loginState(username){
    $("#username").show().text(username);
    $("#userinfo").show();
    $("#sign").hide();
    var cookie = document.cookie.split("; ");
    for (i=0;i<cookie.length;i++){
        if (cookie[i].startsWith("i=")){
            userid = cookie[i].slice(cookie[i].indexOf("=") + 1, cookie[i].length)
            break;
        }
    }
}

function logout(){
    document.cookie = "u=;expires=Thu, 01 Jan 1970 00:00:01 GMT;";
    document.cookie = "i=;expires=Thu, 01 Jan 1970 00:00:01 GMT;";
    document.cookie = "t=;expires=Thu, 01 Jan 1970 00:00:01 GMT;";
    document.cookie = "l=;expires=Thu, 01 Jan 1970 00:00:01 GMT;";
    logoutState();
}

function cookieCheck(){
    if (document.cookie.indexOf("u=") > -1){
        var cookie = document.cookie.split("; ");
        for (i=0;i<cookie.length;i++){
            if (cookie[i].startsWith("u=")){
                username = cookie[i].slice(cookie[i].indexOf("=") + 2, cookie[i].indexOf("@"))
                loginState(username);
                return;
            }
        }
    }
    logoutState();
}

function toSignIn(){
    $("#sign-in-entries").fadeIn();
    $("#sign-in-inputs").fadeIn();
    $("#sign-up-entries").hide();
    $("#sign-up-inputs").hide();
    $("#sign-up-success").remove();
    $(".sign-alert").fadeOut();
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
    $(".sign-alert").fadeOut();
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
    $("#userinfo img").on("mouseenter", function(){$("#logout").show();});
    $("#userinfo").on("mouseleave", function(){$("#logout").hide();});
    $("#logout").click(logout);
    $("#sign-up").on("click",toSignUp);
    $("#sign-passwd-confirm, #sign-in-inputs input[type=password]").keypress(function(e){
        if (e.which == 13) {
            e.preventDefault();
            $("#sign-submit").trigger("click");
        }
    });
    $("#sign-submit").click(function(){
        if ($(this).text().indexOf("SIGN IN") >= 0){
            $.ajax({
                method: "post",
                url: "./user/login",
                data:{
                    "username": $("#sign-in-inputs input[type=text]").val(),
                    "pwd": $("#sign-in-inputs input[type=password]").val()
                },
                success: function(response){
                    if (response["status"] == -1){
                        $("#wrongaccount").fadeIn();
                    }
                    else if (response["status"] == 0){
                        $("#sign-box").hide();
                        $(".screen-cover").remove();
                        cookieCheck();
                    }
                }
            })
        }
        else if ($(this).text().indexOf("SIGN UP") >= 0){
            if ($("#sign-up-inputs input[type=password]").eq(0).val() == $("#sign-up-inputs input[type=password]").eq(1).val()){
                $.ajax({
                    method: "post",
                    url: "./user/register",
                    data: {
                        "username": $("#sign-up-inputs input[type=text]").val(),
                        "pwd": $("#sign-up-inputs input[type=password]").eq(0).val()
                    },
                    success: function(response){
                        if (response["status"] == -1){
                            $("#user-exists").text(response["msg"]).show();
                        }
                        else if (response["status"] == 0){
                            $("<div id='sign-up-success'></div>")
                                .text("Congratulations!  Sign up succeeded, please go to confirm your e-mail address")
                                .appendTo("#sign-form")
                                .css({
                                    "position": "absolute",
                                    "height": $("#sign-form").height()+"px",
                                    "line-height": $("#sign-form").height()+"px",
                                    "width": $("#sign-form").width()+"px",
                                    "background-color": "#f5f5f5",
                                    "top": "0px",
                                    "left": "0px",
                                    "text-align": "center",
                                    // #### to improve
                                });
                        }
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
        // #### to solve: last infomartion entered
        $("#sign-box input").val("");
        $("#sign-box")
            .fadeIn()
            .css({
                "top": $(window).scrollTop() + Math.max(($(window).height() - $("#sign-box").height())/2,40) + "px",
                "left": ($("body").width() - $("#sign-box").width())/2 + "px",
                "z-index": "11"
            });
        $("<div class='screen-cover'></div>")
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
                $("#sign-up-success").remove();
                $("#sign-box").hide();
            });
    });
    $("#cart").click(function(){
        if (userid != -1){
            $.ajax({
                method: "get",
                url: "./cart",
                data: {
                    userId: userid,
                },
                success: function(response){
                    if (response["status"] == 0){
                        $("<div id='cart-popup'>" +
                            "<img class='closepic' src='./image/close.jpg'>" +
                            "<div id='order-title'>My Order</div>" +
                            "<div id='cart-line'></div>" +
                            "<ul id='order-list'></ul>" +
                            "<div id='cart-line'></div>" +
                            "<div id='ship'>" +
                                "\
                                <ul id='ship-info'>\
                                    <li>Name:</li>\
                                    <li>Phone Number:</li>\
                                    <li>Address Line 1:</li>\
                                    <li>Address Line 2:</li>\
                                    <li>City:</li>\
                                    <li>Zip Code:</li>\
                                 </ul>\
                                 <div id='ship-inputs'>\
                                     <input id='ship-name' type='text'>\
                                     <input id='ship-phone' type='text'>\
                                     <input id='ship-addr1' type='text'>\
                                     <input id='ship-addr2' type='text'>\
                                     <input id='ship-city' type='text'>\
                                     <input id='ship-zcode' type='text'>\
                                 </div>\
                                 <div id='order-submit'>Submit</div>\
                                 <div id='order-response'>Submission Succeed!</div>\
                                 <div class='clear'></div>\
                                 " +
                            "</div>" +
                          "</div>")
                            .appendTo("body")
                            .css({
                                "position": "absolute",
                                "top": $(window).scrollTop() + Math.max(($(window).height() - $("#cart-popup").height())/2,40) + "px",
                                "left": ($("body").width() - $("#cart-popup").width())/2 + "px",
                                "z-index": "11"
                            });
                        for (i=0; i<response["data"].length;i++){
                            p = response["data"][i];
                            $("<li><span class='cartPName'>"+ p["productName"] +
                                "</span>" +
                                "<span class='cartQty'>Qty:</span><span class='cartPqty'>" +
                                p["quantity"] + "</span></li>").appendTo("#order-list");
                        }
                        $("#cart-popup .closepic").click(function(){
                            $(".screen-cover").remove();
                            $("#cart-popup").remove();
                        });
                        $("#order-submit").click(function(){
                            $.ajax({
                                method: "post",
                                url: "./cart/commit",
                                contentType: "application/json",
                                data:JSON.stringify({
                                    userId: userid,
                                    name: $("#ship-name").val(),
                                    mobile: $("#ship-phone").val(),
                                    address1: $("#ship-addr1").val(),
                                    address2: $("#ship-addr2").val(),
                                    city: $("#ship-city").val(),
                                    email: "1@test.com",
                                    zipCode: $("#ship-zcode").val()
                                }),
                                success: function(response){
                                    if (response["status"] == 0){
                                        $("#order-response").show().text("Submission Succeed!")
                                    }
                                }
                            });
                        })
                        $("<div class='screen-cover'></div>")
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
                                $("#cart-popup").remove();
                            });
                        $.ajax({
                            method: "get",
                            url: "/cart/lastOrder",
                            data: {
                                userId: userid
                            },
                            success: function(shipresponse){
                                $("#ship-name").val(shipresponse["data"]["name"]);
                                $("#ship-phone").val(shipresponse["data"]["mobile"]);
                                $("#ship-addr1").val(shipresponse["data"]["address1"]);
                                $("#ship-addr2").val(shipresponse["data"]["address2"]);
                                $("#ship-city").val(shipresponse["data"]["city"]);
                                $("#ship-zcode").val(shipresponse["data"]["zipCode"]);
                            }
                        });

                    }
                    else if (response["status"] == -2){
                        $("#sign").trigger("click");
                    }
                }
            });
        }
        else{
            $("#sign").trigger("click");
        }
    });
    $("#sign-box .closepic").click(function(){
        $(".screen-cover").remove();
        $("#sign-up-success").remove();
        $("#sign-box").hide();
    });
});