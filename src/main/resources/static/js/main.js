//// -----------------USER OPERATIONS---------------------------------------------
//// LOG IN BUTTON
//$(function() {
//  $('#logInBtn').click(function() {
//    var data = {
//      userEmail: $('#emailLoginForm').val(),
//      userPassword: $('#passwordLogInForm').val(),
//    };
//    $.ajax({
//      url: '/userPage',
//      method: 'post',
//      data: JSON.stringify(data),
//      contentType: 'application/json',
//      dataType: "json",
//      cache: false
//    }).done(function(data, status, jqxhr) {
//    console.log(data);
//	    alert("Added a new user successfully!!!");
//	    $("#emailLoginForm").val("");
//	    $("#passwordLogInForm").val("");
//    }).fail(function(data, status, jqxhr) {
//     console.log(data);
//        alert("Failed to log in!!!");
//        $.each(data.responseJSON.errors,(index, value) => {
//        if(value.field == "userEmail") {
//            $('#errorEmailLoginForm').text(value.defaultMessage).css({"display":"block", "color":"red"});
//        }else{
//            $("#errorEmailLoginForm").text("");
//        }
//        if(value.field == "userPassword") {
//            $('#errorPasswordLogInForm').text(value.defaultMessage).css({"display":"block", "color":"red"});
//        }else{
//            $("#errorPasswordLogInForm").text("");
//        }
//    });
//   });
// });
//});

$(function() {
  $('#addUserBtn').click(function() {
    let passwordConfirmed = null;
	if($('#passwordReg').val() === $('#confirmingPasswordReg').val()){
			passwordConfirmed =  $('#passwordReg').val();
	}else{
		 $('#errorPasswordConfirmRe').text("The password you entered not match!!! ");
	}
    var data = {
      userEmail: $('#emailReg').val(),
      userFullName: $('#fullNameReg').val(),
      userPassword: passwordConfirmed,
      role: $('#roleReg').val()
    };
    $.ajax({
      url: '/save',
      method: 'post',
      data: JSON.stringify(data),
      contentType: 'application/json',
      dataType: "json",
      cache: false
    }).done(function(data, status, jqxhr) {
    console.log(data);
	    alert("Added a new user successfully!!!");
	    $('#exampleModal').modal('hide');
	    $('#emailReg').val("");
	    $('#fullNameReg').val("");
	    $('#passwordReg').val("");
	    $('#roleReg').val("");
    }).fail(function(data, status, jqxhr) {
    console.log(data);
        alert("Failed to add a new user!!!");
        $.each(data.responseJSON.errors,(index, value) => {
        if(value.field == "userEmail") {
            $('#errorEmailReg').text(value.defaultMessage).css({"display":"block", "color":"red"});
        }else{
            $('#errorEmailReg').text("");
        }
        if(value.field == "userFullName") {
            $('#errorFullNameReg').text(value.defaultMessage).css({"display":"block", "color":"red"});
        }else{
            $('#errorFullNameReg').text("");
        }
        if(value.field == "userPassword") {
	        $('#errorPasswordReg').text(value.defaultMessage).css({"display":"block", "color":"red"});
	    }else{
	        $('#errorPasswordReg').text("");
	    }
		if(value.field == "userConfirmingPassword") {
             $('#errorPasswordConfirmReg').text(value.defaultMessage).css({"display":"block", "color":"red"});
        }else{
            $('#errorPasswordConfirmReg').text("");
        }
        });
    });
   });
 });