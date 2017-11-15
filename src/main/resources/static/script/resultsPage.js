$(document).ready(function () {
    $(".charity-desc-p").each(function () {
        if ($(this).html().length > 300) {
            var words = $(this).html().substring(0, 300).split(" ");
            if (words.length > 1) {
                words = words.slice(0, -1);
            }
            $(this).html(words.join(" ") + " ...");
        }
    });


    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/searchKeys",
        success: function (resultArray) {
            for (var i=0;i<resultArray.length;i++){
                console.log(resultArray[i]);
            }
        },
        error: function (e) {
            console.log("There was an error communicating with the server");
            console.log("ERROR : ", e);
        }
    });

});

function searchBarKeyDown(key) {
    if (key.keyCode === 13)
        $('#searchFormSubmit').click();
}

function searchSubmit(){
var xmlhttp;
if (window.XMLHttpRequest)
{
    //  IE7+ Firefox Chrome Opera Safari
    xmlhttp=new XMLHttpRequest();
}
else
{
    // IE6 IE5
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}
}