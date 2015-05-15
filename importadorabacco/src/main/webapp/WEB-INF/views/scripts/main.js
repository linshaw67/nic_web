function scrollTo($toS){
    $currentS.css({
        "background-color": "#d5c295",
        "opacity": "0.7"
    });
    $toS.css({
        "background-color": "#d1a867",
        "opacity": "1"
    });
    $currentS = $toS;
}
function positionJudge(){
    if ($(window).scrollTop() + 5< $("#home1").height()){
        return $("#s1");
    } else if ($(window).scrollTop() + 5< $("#home1").height() + $("#home2").height()) {
        return $("#s2");
    }
    else if ($(window).scrollTop() < $("#home1").height() + $("#home2").height() + 200) {
        return $("#s3");
    }
    else return $("#s4");
}
$(document).ready(function(){
    $currentS = positionJudge();
    scrollTo($currentS);
    $("#s1").click(function(){
        $.smoothScroll({
        scrollTarget: "#home1"
        })
    });
    $("#s2").click(function(){
        $.smoothScroll({
        scrollTarget: "#home2"
        })
    });
    $("#s3").click(function(){
        $.smoothScroll({
        scrollTarget: "#home3"
        })
    });
    $("#s4").click(function(){
        $.smoothScroll({
        scrollTarget: "#home4"
        })
    });
    $(".nav .rightli a").click(function(){
        $.smoothScroll({
        scrollTarget: "#home4"
        })
    });
    $("#home1").height(Math.max(740,$(window).height()));
    $(window).scroll(function() {
                $toS = positionJudge()
                if ($toS != $currentS){
                    scrollTo($toS);
                }
                if ($(window).scrollTop() < (1670 - $("#home1").height()) / 2.3) {
                    $("#home1").css("background-position", "center -" + 2.3 * $(window).scrollTop() + "px");

                    /*$("#home1 .navwrap").css("position","fixed");
                    $("#home1 .introwrap").css("position","fixed");
                }
                else {
                    $("#home1 .navwrap").css("position","static");
                    $("#home1 .introwrap").css("position","static");*/
                }
                else{
                    $("#home1").css("background-position", "center -" + 1670 - $("#home1").height() + "px");
                }
            })

    $("#home2 .img").height($("#home2 .leftwrap").height() - 40);
    $("#home2 .img").width(600 * $("#home2 .img").height() / 447);
    $(".categories a").click(function(){
            location = "./product?catId=" + $(this).parent().data("catId");
    });
})