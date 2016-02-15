$(function () {

    $('#login-form-link').click(function (e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $('#register-form-link').click(function (e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

    $("#register-form").on('submit', function (e) {

        var responseMsg = $("#registration-response");

        var onSuccess = function (msg) {
            $.alert({title:"Registration", text:msg.responseText});
            $("#register-form")[0].reset();
        };

        var onError = function (msg) {
            $.alert({title:"Registration", text:msg.responseText});
            $("#register-form")[0].reset();
        }

        var user = {
            firstName: $("#regUserName").val(),
            lastName: $("#userLastName").val(),
            password: $("#regpassword").val(),
            email: $("#email").val()
        }

        e.preventDefault();

        sendPost("/auth/registration",user , onSuccess, onError);
    })


    function sendPost(url, data, onSuccess, onError) {
        $.ajax({
            url: url,
            type: 'POST',
            dataType: "json",
            contentType: 'application/json',
            mimeType: 'application/json',
            async: true,
            data: JSON.stringify(data),
            success: onSuccess,
            error: onError
        });
    }
});
