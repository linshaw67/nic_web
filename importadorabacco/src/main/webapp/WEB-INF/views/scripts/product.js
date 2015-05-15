function createCat(cat){
    var i, $newCat, $line, line_count, $newProduct;
    $newCat = $("<div class='category'>\
                    <div class='category-name'></div>\
                    <div class='products'></div>\
                </div>");
    $newCat.find(".category-name").text(cat['catName']);
    $line = $("<ul></ul>");
    line_count = 0;
    for (i=0;i<cat['products'].length;i++){
        if (line_count == 6) {
            $newCat.find(".products").append($line);
            $line = $("<ul></ul>");
            line_count = 0;
        }
        $newProduct = $("<li><img" + " src=" + cat['products'][i]['imageUrl'] + "/><div>" + cat['products'][i]['productName'] + "</div></li>");
        $newProduct.data("productId",cat["products"][i]["productId"]);
        $newProduct.data("catId",cat["products"][i]['catId']);
        $line.append($newProduct);
        line_count++;
    };
    $newCat.find(".products").append($line);
};
$(document).ready(function(){
    var defaultCatId = location.href.slice(location.href.indexOf('=')+1, location.href.length);
    $(".nav .rightli a").click(function(){
        $.smoothScroll({
        scrollTarget: "#pcontact"
        })
    });
    $.ajax({
        method: "get",
        url: "./product/get?cat="+defaultCatId,
        dataType: "json",
        statusCode: {
            0: function(data){
                if (defaultCatId == 0){
                    pselectionText = "ALL PRODUCTS";
                }
                else{
                    pselectionText = data[0]['catName'];
                }
                $("#pcontent .panel .pselection").text(pselectionText);
                $("#pcontent .panel").show();
                for (i = 0; i < data.length; i++) {
                    createCat(data[i]);
                }
            }
        }
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
            url: "./product/get?cat="+$(this).parent().data("cat"),
            dataType: "json",
            statusCode: {
                0: function(data){
                    $("#pcontent .panel .pselection").show().text($r.text());
                    $(".arrow").show();
                    $("#pcontent .panel .categories").fadeOut();
                    for (i = 0; i < data.length; i++) {
                        createCat(data[i]);
                    }
                }
            }
        });
    });
});