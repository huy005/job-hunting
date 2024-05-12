//// -----------------USER OPERATIONS---------------------------------------------
// ADD USER BUTTON
$(function () {
    $('#addUserBtn').click(function () {
        let passwordConfirmed = null;
        if ($('#passwordReg').val() === $('#confirmingPasswordReg').val()) {
            passwordConfirmed = $('#passwordReg').val();
        } else {
            $('#errorPasswordConfirmRe').text("The password you entered not match!!! ");
        }
        var data = {
            email: $('#emailReg').val(),
            username: $('#fullNameReg').val(),
            password: passwordConfirmed,
            role: $('#roleReg').val()
        };
        $.ajax({
            url: '/user-registration',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            cache: false
        }).done(function (data, status, jqxhr) {
            console.log(data);
            alert("A new user added successfully!!!");
            $('#exampleModal').modal('hide');
            $('#emailReg').val("");
            $('#fullNameReg').val("");
            $('#passwordReg').val("");
            $('#roleReg').val("");
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            alert("Failed to add a new user!!!");
            $.each(data.responseJSON.errors, (index, value) => {
                if (value.field == "userEmail") {
                    $('#errorEmailReg').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorEmailReg').text("");
                }
                if (value.field == "userFullName") {
                    $('#errorFullNameReg').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorFullNameReg').text("");
                }
                if (value.field == "userPassword") {
                    $('#errorPasswordReg').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorPasswordReg').text("");
                }
                if (value.field == "userConfirmingPassword") {
                    $('#errorPasswordConfirmReg').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorPasswordConfirmReg').text("");
                }
            });
        });
    });
});

// UPDATE RECRUITER's INFO
$(function () {
    $('#updateInfoBtn').click(function () {

        if ($('#userImageReInfoForm-info').val() != 0) {
            alert("Upload the user's image before submitting.")
        }

        var token =  $('input[name="_csrf"]').val();
        var data = {
            userAddress: $('#addressReInfo').val(),
            userDescription: $('#descriptionReInfo').val(),
            userImage: $('#userImageReInfo').val(),
            username: $('#fullNameReInfo').val(),
            userPhoneNumber: $('#phoneNumberReInfo').val()
        };
        $.ajax({
            url: '/recruiters/recruiter-info',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            headers: {
                'X-XSRF-TOKEN': token
            },
            processData: false,
            cache: false
        }).done(function (data, status, jqxhr) {
            console.log(data);
            alert("The recruiter's info updated successfully!!!");
            $('#recruiterInfoModal').modal('hide');
            $('#userImageReInfo').val("");
            $('#fullNameReInfo').val("");
            $('#addressReInfo').val("");
            $('#phoneNumberReInfo').val("");
            $('#descriptionReInfo').val("");
            location.reload();
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update recruiter's info!!!");
            $.each(data.responseJSON.errors, (index, value) => {
                if (value.field == "userFullName") {
                    $('#errorFullNameReInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorFullNameReInfo').text("");
                }
                if (value.field == "userAddress") {
                    $('#errorAddressReInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorAddressReInfo').text("");
                }
                if (value.field == "userPhoneNumber") {
                    $('#errorPhoneNumberReInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorPhoneNumberReInfo').text("");
                }
            });
        });
    });
});

// UPDATE COMPANY's INFO
$(function () {
    $('#updateInfo2Btn').click(function () {

        if ($('#companyLogoCoForm-info').val() != 0) {
            alert("Upload the company logo before submitting.")
        }

        var data = {
            companyAddress: $('#addressCoInfo').val(),
            companyDescription: $('#descriptionCoInfo').val(),
            companyEmail: $('#emailCoInfo').val(),
            companyName: $('#nameCoInfo').val(),
            companyLogo: $('#companyLogoCoInfo').val(),
            companyPhoneNumber: $('#phoneNumberCoInfo').val()
        };
        $.ajax({
            url: '/recruiters/company-info',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            cache: false
        }).done(function (data) {
            console.log(data);
            alert("The company's info updated successfully!!!");
            $('#companyInfoModal').modal('hide');
            $('#emailCoInfo').val("");
            $('#companyLogoCoInfo').val("");
            $('#nameCoInfo').val("");
            $('#addressCoInfo').val("");
            $('#phoneNumberCoInfo').val("");
            $('#descriptionCoInfo').val("");
            location.reload();
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update company's info!!!");
            $.each(data.responseJSON.errors, (index, value) => {
                if (value.field == "companyEmail") {
                    $('#errorEmailCoInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorEmailCoInfo').text("");
                }
                if (value.field == "companyName") {
                    $('#errorNameCoInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorNameCoInfo').text("");
                }
                if (value.field == "companyAddress") {
                    $('#errorAddressCoInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorAddressCoInfo').text("");
                }
                if (value.field == "companyPhoneNumber") {
                    $('#errorPhoneNumberCoInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#errorPhoneNumberCoInfo').text("");
                }
            });
        });
    });
});

// UPDATE COMPANY's LOGO
$(function () {
    $('#companyLogoCoInfoForm').submit(function (event) {

        if ($('#companyLogoCoForm-info').val() != 0) {
            var formElement = this;
            var formData = new FormData(formElement);
        }

        $.ajax({
            url: '/file-upload',
            method: 'post',
            data: formData,
            enctype: 'multipart/form-data',
            contentType: false,
            processData: false,
            cache: false
        }).done(function (formData, status, jqxhr) {
            console.log(formData);
            console.log(status);
            console.log(jqxhr);
            $('#companyLogoCoInfo').val(formData.fileName);
            $('#companyLogoCoForm-info').val('');
            alert("The company's logo updated successfully!!!");
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update the company's logo!!!");
        });
        event.preventDefault();
    });
});

// UPDATE USER's IMAGE
$(function () {
    $('#userImageReInfoForm').submit(function (event) {

        if ($('#userImageReInfoForm-info').val() != 0) {
            var formElement = this;
            var formData = new FormData(formElement);
        }

        $.ajax({
            url: '/file-upload',
            method: 'post',
            data: formData,
            enctype: 'multipart/form-data',
            contentType: false,
            processData: false,
            cache: false
        }).done(function (formData, status, jqxhr) {
            console.log(formData);
            console.log(status);
            console.log(jqxhr);
            $('#userImageReInfo').val(formData.fileName);
            $('#userImageReInfoForm-info').val('');
            alert("The user's image updated successfully!!!");
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update The user's image!!!");
        });
        event.preventDefault();
    });
});

// ADD JOB DESCRIPTION BUTTON
$(function () {
    $('#addJobDescriptionBtn').click(function () {
        var token =  $('input[name="_csrf"]').val();
        var data = {
            title: $('#jd-title').val(),
            quantity: $('#jd-quantity').val(),
            experience: $('#jd-experience').val(),
            jobDescriptionAddress: $('#jd-address').val(),
            deadline: $('#jd-deadline').val(),
            salary: $('#jd-salary').val(),
            jobDescriptionType: $('#jd-type').val(),
            categoryId: $('#jd-category').val(),
            description: $('#jd-description').val()
        };
        $.ajax({
            url: '/recruiters/job-description-registration',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            headers: {
                'X-XSRF-TOKEN': token
            },
            cache: false
        }).done(function (data, status, jqxhr) {
            console.log(data);
            alert("A new job description added successfully!!!");
            $('#jdReModal').modal('hide');
            $('#jd-title').val("");
            $('#jd-quantity').val("");
            $('#jd-experience').val("");
            $('#jd-address').val("");
            $('#jd-deadline').val("");
            $('#jd-salary').val("");
            $('#jd-type').val("");
            $('#jd-category').val("");
            $('#jd-description').val("");
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            alert("Failed to add a new job description!!!");
            $.each(data.responseJSON.errors, (index, value) => {
                if (value.field == "title") {
                    $('#error-jd-title').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#error-jd-title').text("");
                }
                if (value.field == "quantity") {
                    $('#error-jd-quantity').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#error-jd-quantity').text("");
                }
                if (value.field == "experience") {
                    $('#error-jd-experience').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#error-jd-experience').text("");
                }
                if (value.field == "jobDescriptionAddress") {
                    $('#error-jd-address').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#error-jd-address').text("");
                }
                if (value.field == "deadline") {
                    $('#error-jd-deadline').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#error-jd-deadline').text("");
                }
                if (value.field == "salary") {
                    $('#error-jd-salary').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#error-jd-salary').text("");
                }
                if (value.field == "jobDescriptionType") {
                    $('#error-jd-type').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#error-jd-type').text("");
                }
                if (value.field == "category") {
                    $('#jd-category').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#jd-category').text("");
                }
                if (value.field == "description") {
                    $('#jd-description').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#jd-description').text("");
                }
            });
        });
    });
});