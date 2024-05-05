//// -----------------USER OPERATIONS---------------------------------------------

$(function() {
  $('#addUserBtn').click(function() {
    let passwordConfirmed = null;
	if($('#passwordReg').val() === $('#confirmingPasswordReg').val()){
			passwordConfirmed =  $('#passwordReg').val();
	}else{
		 $('#errorPasswordConfirmRe').text("The password you entered not match!!! ");
	}
    var data = {
      email: $('#emailReg').val(),
      username: $('#fullNameReg').val(),
      password: passwordConfirmed,
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
	    alert("A new user added successfully!!!");
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

 // UPDATE RECRUITER's INFO
$(function() {
    $('#updateInfoBtn').click(function() {
        var data = {
            userAddress: $('#addressReInfo').val(),
            userDescription: $('#descriptionReInfo').val(),
            email: $('#emailReInfo').val(),
            username: $('#fullNameReInfo').val(),
            userPhoneNumber: $('#phoneNumberReInfo').val()
        };
        $.ajax({
            url: '/recruiters/recruiter-info',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            cache: false
        }).done(function(data, status, jqxhr) {
            console.log(data);
            alert("The recruiter's info updated successfully!!!");
            $('#recruiterInfoModal').modal('hide');
            $('#emailReInfo').val("");
            $('#fullNameReInfo').val("");
            $('#addressReInfo').val("");
            $('#phoneNumberReInfo').val("");
            $('#descriptionReInfo').val("");
        }).fail(function(data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update recruiter's info!!!");
            $.each(data.responseJSON.errors,(index, value) => {
                if(value.field == "userEmail") {
                    $('#errorEmailReInfo').text(value.defaultMessage).css({"display":"block", "color":"red"});
                }else{
                    $('#errorEmailReInfo').text("");
                }
                if(value.field == "userFullName") {
                    $('#errorFullNameReInfo').text(value.defaultMessage).css({"display":"block", "color":"red"});
                }else{
                    $('#errorFullNameReInfo').text("");
                }
                if(value.field == "userAddress") {
                    $('#errorAddressReInfo').text(value.defaultMessage).css({"display":"block", "color":"red"});
                }else{
                    $('#errorAddressReInfo').text("");
                }
                if(value.field == "userPhoneNumber") {
                    $('#errorPhoneNumberReInfo').text(value.defaultMessage).css({"display":"block", "color":"red"});
                }else{
                    $('#errorPhoneNumberReInfo').text("");
                }
            });
        });
    });
});

// UPDATE COMPANY's INFO
$(function() {
    $('#updateInfo2Btn').click(function() {
        var data = {
            companyAddress: $('#addressCoInfo').val(),
            companyDescription: $('#descriptionCoInfo').val(),
            companyEmail: $('#emailCoInfo').val(),
            companyName: $('#nameCoInfo').val(),
            companyPhoneNumber: $('#phoneNumberCoInfo').val()
        };
        $.ajax({
            url: '/recruiters/company-info',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            cache: false
        }).done(function(data, status, jqxhr) {
            console.log(data);
            alert("The company's info updated successfully!!!");
            $('#companyInfoModal').modal('hide');
            $('#emailCoInfo').val("");
            $('#nameCoInfo').val("");
            $('#addressCoInfo').val("");
            $('#phoneNumberCoInfo').val("");
            $('#descriptionCoInfo').val("");
        }).fail(function(data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update company's info!!!");
            $.each(data.responseJSON.errors,(index, value) => {
                if(value.field == "companyEmail") {
                    $('#errorEmailCoInfo').text(value.defaultMessage).css({"display":"block", "color":"red"});
                }else{
                    $('#errorEmailCoInfo').text("");
                }
                if(value.field == "companyName") {
                    $('#errorNameCoInfo').text(value.defaultMessage).css({"display":"block", "color":"red"});
                }else{
                    $('#errorNameCoInfo').text("");
                }
                if(value.field == "companyAddress") {
                    $('#errorAddressCoInfo').text(value.defaultMessage).css({"display":"block", "color":"red"});
                }else{
                    $('#errorAddressCoInfo').text("");
                }
                if(value.field == "companyPhoneNumber") {
                    $('#errorPhoneNumberCoInfo').text(value.defaultMessage).css({"display":"block", "color":"red"});
                }else{
                    $('#errorPhoneNumberCoInfo').text("");
                }
            });
        });
    });
});
