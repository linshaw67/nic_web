function createCat(cat){
    var i;
    $newCat = $("<div class='category'>\
                    <div class='category-name'></div>\
                    <div class='products'></div>\
                </div>");
    $newCat.find(".category-name").text(cat['catName']);
    $line = $("<ul></ul>");
    line_count = 0;
    for (i=0;i<cat['products'].length;i++){
        $("<li><img" + " src=" + cat['products'][i] "<div></div></li>")
    };
};
$(document).ready(function(){
    $(".nav .rightli a").click(function(){
        $.smoothScroll({
        scrollTarget: "#pcontact"
        })
    });
    $(".arrow").click(function(){
        //$(this).animate({"left":  $("#pcontent .panel .categories").width() + "px"}, "300");
        $(this).hide();
        $("#pcontent .panel .pselection").css("display", "none");
        $("#pcontent .panel .categories").css({
            "display": "inline-block",
            "opacity": 0
        });
        $("#pcontent .panel .categories").fadeTo(800, 1);
    });
    $(".category a").click(function(){
        var $r = $(this);
        $.ajax({
            method: "get",
            url: "./product/get"+$(this).parent().data("cat"),
            dataType: "json",
            success: function(data){
                $("#pcontent .panel .pselectio").show().text($r.text());
                $("#pcontent .panel .categories").fadeOut();
                for (i=0;i<data.length;i++){
                    createCat(data[i]);
                }
            }
        });
    });
});