$(document).ready(function() {
    $('#form').ajaxForm(function(response) {
        var title,className;

        if(response.error == true) {
            title = "Oops!";
            className = "btn-danger";
        } else {
            title = "Success!";
            className = "btn-success";
        }

        bootbox.dialog({
            message: response.data,
            title: title,
            buttons: {
                ok : {
                    label: "OK",
                    className: className
                }
            }
        });
    });
});