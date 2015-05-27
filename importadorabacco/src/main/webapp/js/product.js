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
        $newProduct = $("<li class='product'><img" + " src=" + cat['products'][i]['imageUrl'] + "/><div>" + cat['products'][i]['productName'] + "</div></li>");
        $newProduct.data("productId",cat["products"][i]["productId"]);
        $newProduct.data("catId",cat["products"][i]['catId']);
        $newProduct.bind("click", productClick);
        $line.append($newProduct);
        line_count++;
    };
    $line.append("<div class='clear'></div>");
    $line.appendTo($newCat.find(".products")).hide().fadeIn();
    $("#pcontent").append($newCat);
};

function productClick(){
    $("#add-response").hide();
    for (i=0;i<gdata["data"].length;i++){
        if (gdata["data"][i]["catId"] == $(this).data("catId")){
            var cat = gdata["data"][i];
            for (j=0;j<cat["products"].length;j++){
                if (cat["products"][j]["productId"] == $(this).data("productId")){
                    var tmpId = $(this).data("productId");
                    $("#popup-detail").data("productId",tmpId);
                    $("#product-big-image").attr("src", cat["products"][j]["imageUrl"].replace('mid.jpg','big.jpg'));
                    $("#product-name").text(cat["products"][j]["productName"]);
                    //$("#product-price span").text("$"+cat["products"][j]["price"]);
                    $("#qty-number").text("1");
                    $("#popup-detail")
                        .fadeIn()
                        .css({
                            "position": "absolute",
                            "top": $(window).scrollTop() + Math.max(($(window).height() - $("#popup-detail").height())/2,40) + "px",
                            "left": ($("body").width() - $("#popup-detail").width())/2 + "px",
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
                            $("#popup-detail").hide();
                        });
                }
            }
        }
    }
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
                    gdata = data;
                    if (defaultCatId == 0){
                        pselectionText = "All Products";
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
                        gdata = data;
                        //$("#pcontent .panel .pselection").fadeIn().text($r.text());
                        //$(".arrow").fadeIn();
                        //$("#pcontent .panel .categories").hide();
                        $("#pcontent .category").remove();
                        for (i = 0; i < data["data"].length; i++) {
                            createCat(data["data"][i]);
                        }
                     }
        });
    });
    $("#qty-control img").eq(0).click(function(){
        $("#qty-number").text(parseInt($("#qty-number").text())+1);
    });
    $("#qty-control img").eq(1).click(function(){
        $("#qty-number").text(Math.max(parseInt($("#qty-number").text())-1,1));
    });
    $("#add-to-cart").click(function(){
        $.ajax({
            method: "post",
            url: "./cart/add",
            data: {
                userId: userid,
                productId: $("#popup-detail").data("productId"),
                quantity: $("#qty-number").text()
            },
            success: function(response){
                if (response["status"] == 0){
                    $("#add-response").show().text("Succeed!");
                }
                else if (response["status"] == -1){
                    alert(response["msg"]);
                    }
                    else if (response["status"] == -2){
                        $("#add-response").show().text("Please log in!");
                    }
            }
        });
    });
    $("#popup-detail .closepic").click(function(){
        $(".screen-cover").remove();
        $("#popup-detail").hide();
    });
});