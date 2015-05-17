function toSignUp(){
    $("#sign-in-entries").hide();
    $("#sign-in-inputs").hide();
    $("#sign-up-entries").fadeIn();
    $("#sign-up-inputs").fadeIn();
    //$("#sign-up").css({});
};
$(document).ready(function(){
    $("#sign-up").click(toSignUp);
});