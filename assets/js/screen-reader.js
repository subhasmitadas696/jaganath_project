setInterval(function () {
    $(".screen-reader").each(function (e) {
        $(this).attr("tabindex", e);
    });
}, 1e3)





