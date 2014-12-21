/*
$(document).bind('pagebeforeshow', function() {
   alert("pagebeforeshow");
   alert($(document).html());
});
*/

        function handlePageShown()
        {
            //alert("Page is shown!");
            //alert(jQuery("<h1>Hello World</h1>").html());
            console.log(jQuery("html").html());
          console.log("Page is shown!");
        }
        document.addEventListener("showpagecomplete", handlePageShown, false);