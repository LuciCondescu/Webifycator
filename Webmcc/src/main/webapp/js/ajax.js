
function showHistory() {
    $.post( "commandHistory.jsp", function( data ) {
        $( "#mainContent" ).html( data );
    });
}
function showOverview() {
    $.get( "overview.jsp", function( data ) {
        $( "#mainContent" ).html( data );
    });
}

$(function () {
    $('a[data-toggle="collapse"]').on('click',function(){

        var objectID=$(this).attr('href');

        if($(objectID).hasClass('in'))
        {
            $(objectID).collapse('hide');
        }

        else{
            $(objectID).collapse('show');
        }
    });

});

function cancelExecution(id,token) {
    bootbox.dialog({
        message: "Are you sure you want to cancel the command ?",
        title: "Confirm",
        buttons: {
            ok : {
                label: "OK",
                className: "btn-primary",
                callback: function () {
                    $.post("cancel", {hash : id,CSRFtoken : token},function ( data ) {

                    });
                }
            },
            cancel : {
                label: "Cancel",
                className : "btn-default"
            }
        }
    });

}

function getRunningCommands() {
    $.get( "runningCommands.jsp", function( data ) {
        $("#liveCommands").html(data);
    });

    setTimeout(getRunningCommands,5000);
}
