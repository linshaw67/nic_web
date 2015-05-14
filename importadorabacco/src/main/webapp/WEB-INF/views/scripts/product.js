$(document).ready(function(){
    $(".nav .rightli a").click(function(){
        $.smoothScroll({
        scrollTarget: "#pcontact"
        })
    });
    $(".arrow").click(function(){
        $(this).animate({"left":  $("#pcontent .panel .categories").width() + "px"}, "300");
        $("#pcontent .panel .pselection").css("display", "none");
        $("#pcontent .panel .categories").css({
            "display": "inline-block",
            "opacity": 0
        });
        $("#pcontent .panel .categories").fadeTo(800, 1);
    })
})