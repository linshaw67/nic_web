function createCat(cat){
    var i, $newCat, $line, line_count, $newProduct;
    $newCat = $("<div class='category'>\
                    <div class='category-name'><div></div></div>\
                    <div class='products'></div>\
                </div>");
    $newCat.find(".category-name div").text(cat['catName']);
    $line = $("<ul></ul>");
    line_count = 0;
    for (i=0;i<cat['products'].length;i++){
        if (line_count == 6) {
            $line.append("<div class='clear'></div>");
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
    $line.append("<div class='clear'></div>");
    $newCat.find(".products").append($line);
    $("#pcontent").append($newCat);
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
        url: "./product/get?catId="+defaultCatId,
        dataType: "json",
        success:function(data){
                    if (defaultCatId == 0){
                        pselectionText = "ALL PRODUCTS";
                    }
                    else{
                        pselectionText = data["data"][0]['catName'];
                    }
                    $("#pcontent .panel .pselection").text(pselectionText);
                    $("#pcontent .panel").show();
                    for (i = 0; i < data["data"].length; i++) {
                        createCat(data["data"][i]);
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
    $("#pcontent .panel .categories a").click(function(){
        var $r = $(this);
        $.ajax({
            method: "get",
            url: "./product/get?catId="+$(this).parent().data("catid"),
            dataType: "json",
            success: function(data){
                        $("#pcontent .panel .pselection").fadeIn().text($r.text());
                        $(".arrow").fadeIn();
                        $("#pcontent .panel .categories").hide();
                        $("#pcontent .category").remove();
                        for (i = 0; i < data["data"].length; i++) {
                            createCat(data["data"][i]);
                        }
                     }
        });
    });
});