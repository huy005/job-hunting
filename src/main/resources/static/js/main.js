//// -----------------USER OPERATIONS---------------------------------------------
// ADD USER BUTTON
$(function () {
    $('#addUserBtn').click(function () {
        let confirmationPassword = null;
        if ($('#passwordReg').val() === $('#confirmationPasswordReg').val()) {
            confirmationPassword = $('#passwordReg').val();
        } else {
            $('#errorConfirmationPasswordReg').text("The password you entered not match!!! ");
            return e.file.preventDefault();
        }
        const token = $('input[name="_csrf"]').val();
        var data = {
            email: $('#emailReg').val(),
            username: $('#fullNameReg').val(),
            password: confirmationPassword,
            role: $('#roleReg').val(),
            token: token
        };

        $('#addUserBtn').addClass('hiddenAll');
        $('#registerSubmitBtn-close').addClass('hiddenAll');
        $('#registerSubmitBtn-spinIcon').removeClass('hiddenAll');
        setTimeout(function () {
            // the followings performed after 2 seconds
            // $('#registerSubmitBtn-spinIcon').fadeOut(3000, function () {
            $('#registerSubmitBtn-spinIcon').addClass('hiddenAll');
            $('#addUserBtn').removeClass('hiddenAll');
            $('#registerSubmitBtn-close').removeClass('hiddenAll');
            // });
        }, 3000);

        $.ajax({
            url: '/user-registration',
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
            $('#registerModal').modal('hide');
            $('#emailReg').val("");
            $('#fullNameReg').val("");
            $('#passwordReg').val("");
            $('#confirmationPasswordReg').val("");
            $('#errorConfirmationPasswordReg').text("");
            $('#activationUser').removeClass('hiddenAll');
            alert("A new user added successfully!!! Check your email for the activation!!!");
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to add a new user!!!");
            // $.each(data.responseJSON.errors, (index, value) => {
            //     if (value.field == "email") {
            //         $('#errorEmailReg').text(value.defaultMessage).css({"display": "block", "color": "red"});
            //     } else {
            //         $('#errorEmailReg').text("");
            //     }
            //     if (value.field == "username") {
            //         $('#errorFullNameReg').text(value.defaultMessage).css({"display": "block", "color": "red"});
            //     } else {
            //         $('#errorFullNameReg').text("");
            //     }
            //     if (value.field == "password") {
            //         $('#errorPasswordReg').text(value.defaultMessage).css({"display": "block", "color": "red"});
            //     } else {
            //         $('#errorPasswordReg').text("");
            //     }
            //     if (value.field == "confirmationPasswordReg") {
            //         $('#errorConfirmationPasswordReg').text(value.defaultMessage).css({
            //             "display": "block",
            //             "color": "red"
            //         });
            //     } else {
            //         $('#errorConfirmationPasswordReg').text("");
            //     }
            // });
        });
    });
});

// UPDATE RECRUITER's INFO
// $(function () {
//     $('#updateInfoBtn').click(function () {
//
//         if ($('#userFileReInfoForm-info').val() != 0) {
//             alert("Upload the user's image before submitting.")
//             $('#erroruserFileReInfoForm').text('Upload the user\'s image before submitting.')
//             e.file.preventDefault();
//             // uploadUserImage();
//         }
//
//         var data = {
//             userAddress: $('#addressReInfo').val(),
//             userDescription: $('#descriptionReInfo').val(),
//             userImage: $('#userImageReInfo').val(),
//             username: $('#fullNameReInfo').val(),
//             userPhoneNumber: $('#phoneNumberReInfo').val()
//         };
//         $.ajax({
//             url: '/recruiters/recruiter-info',
//             method: 'post',
//             data: JSON.stringify(data),
//             contentType: 'application/json',
//             dataType: "json",
//             headers: {
//                 'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
//             },
//             processData: false,
//             cache: false
//         }).done(function (data, status, jqxhr) {
//             console.log(data);
//             alert("The recruiter's info updated successfully!!!");
//             $('#recruiterInfoModal').modal('hide');
//             $('#userImageReInfo').val("");
//             $('#fullNameReInfo').val("");
//             $('#addressReInfo').val("");
//             $('#phoneNumberReInfo').val("");
//             $('#descriptionReInfo').val("");
//             $('#successfuluserFileReInfoForm').text('')
//             // location.reload();
//         }).fail(function (data, status, jqxhr) {
//             console.log(data);
//             console.log(status);
//             console.log(jqxhr);
//             alert("Failed to update recruiter's info!!!");
//             $.each(data.responseJSON.errors, (index, value) => {
//                 if (value.field == "userFullName") {
//                     $('#errorFullNameReInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
//                 } else {
//                     $('#errorFullNameReInfo').text("");
//                 }
//                 if (value.field == "userAddress") {
//                     $('#errorAddressReInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
//                 } else {
//                     $('#errorAddressReInfo').text("");
//                 }
//                 if (value.field == "userPhoneNumber") {
//                     $('#errorPhoneNumberReInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
//                 } else {
//                     $('#errorPhoneNumberReInfo').text("");
//                 }
//             });
//         });
//     });
// });

// UPDATE RECRUITER's INFO
$(function () {
    $('.updateInfoBtn').click(function () {

        if ($('.userFileReInfoForm-info').val() != 0 && $('#userCvReInfoForm-info-id').val() == 0) {
            alert("Upload the user's image before submitting.")
            $('.errorUserFileReInfoForm').text('Upload the user\'s image before submitting.')
            return e.file.preventDefault();
        }

        if ($('#userCvReInfoForm-info-id').val() != 0 && $('.userFileReInfoForm-info').val() == 0) {
            alert("Upload the CV before submitting.")
            $('#errorUserCvReInfoForm').text('Upload the CV before submitting.')
            return e.file.preventDefault();
        }

        var data = {
            userAddress: $('.addressReInfo').val(),
            userDescription: $('.descriptionReInfo').val(),
            userImageFileName: $('.userImageReInfo').val(),
            username: $('.fullNameReInfo').val(),
            userPhoneNumber: $('.phoneNumberReInfo').val(),
            cvFileName: $('.userCvReInfo').val()
        };
        $.ajax({
            url: '/new-user-info',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            headers: {
                'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
            },
            processData: false,
            cache: false
        }).done(function (data, status, jqxhr) {
            console.log(data);
            alert("The recruiter's info updated successfully!!!");
            $('.recruiterInfoModal').modal('hide');
            // $('.userImageReInfo').val("");
            // $('#userCvReInfo').val("");
            // $('.fullNameReInfo').val("");
            // $('.addressReInfo').val("");
            // $('.phoneNumberReInfo').val("");
            // $('.descriptionReInfo').val("");
            $('#successfulUserFileReInfoForm').text('');
            $('#successfulUserCvReInfoForm').text('');
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update recruiter's info!!!");
            $.each(data.responseJSON.errors, (index, value) => {
                if (value.field == "userFullName") {
                    $('.errorFullNameReInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('.errorFullNameReInfo').text("");
                }
                if (value.field == "userAddress") {
                    $('.errorAddressReInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('.errorAddressReInfo').text("");
                }
                if (value.field == "userPhoneNumber") {
                    $('.errorPhoneNumberReInfo').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('.errorPhoneNumberReInfo').text("");
                }
            });
        });
    });
});

// UPLOAD USER's FILE
$(function () {
    $('.userFileReInfoForm').submit(function (event) {
        if ($('.userFileReInfoForm-info').val() != 0
            || $('#userCvReInfoForm-info-id').val() != 0
            || $('#userCvReInfoForm2-info-id').val() != 0) {
            var formData = new FormData(this);
        }
        $.ajax({
            url: '/file-upload',
            method: 'post',
            data: formData,
            enctype: 'multipart/form-data',
            headers: {
                'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
            },
            contentType: false,
            processData: false,
            cache: false
        }).done(function (formData, status, jqxhr) {
            console.log(formData);
            console.log(status);
            console.log(jqxhr);
            $('.userImageReInfo').val(formData.fileName);
            $('.userCvReInfo').val(formData.fileName);
            if ($('.userFileReInfoForm-info').val() != 0) {
                $('.userFileReInfoForm-info').val('');
                $('.errorUserFileReInfoForm').text('');
                $('.successfulUserFileReInfoForm').text('The user\'s image updated successfully!!!');
            } else if ($('#userCvReInfoForm-info-id').val() != 0) {
                $('#userCvReInfoForm-info-id').val('');
                $('#errorUserCvReInfoForm').text('');
                $('#successfulUserCvReInfoForm').text('The CV updated successfully!!!');

            } else if ($('#userCvReInfoForm2-info-id').val() != 0) {
                $('#userCvReInfoForm2-info-id').val('');
                $('#errorUserCvReInfoForm2').text('');
                $('#successfulUserCvReInfoForm2').text('The CV updated successfully!!!');
            }
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update the file!!!");
        });
        event.preventDefault();
    });
});

// USER's IMAGE PREVIEW
$(function () {
    $('.userFileReInfoForm-info').change(function () {
        let file = this.files[0];
        if (file) {
            let reader = new FileReader();
            reader.onload = function (e) {
                $('.user-image-preview').html('<img width="100%" alt="user-image.png" src="' + e.target.result + '">');
            }
            reader.readAsDataURL(file);
        }
    })
});

// UPDATE COMPANY's INFO
$(function () {
    $('#updateInfo2Btn').click(function () {

        if ($('#companyLogoCoForm-info').val() != 0) {
            alert("Upload the company logo before submitting.")
            $('#errorCompanyLogoCoForm').text('Upload the company\'s logo before submitting.')
            return e.file.preventDefault();
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
            headers: {
                'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
            },
            dataType: "json",
            cache: false
        }).done(function (data) {
            console.log(data);
            alert("The company's info updated successfully!!!");
            $('#companyInfoModal').modal('hide');
            // $('#emailCoInfo').val("");
            // $('#companyLogoCoInfo').val("");
            // $('#nameCoInfo').val("");
            // $('#addressCoInfo').val("");
            // $('#phoneNumberCoInfo').val("");
            // $('#descriptionCoInfo').val("");
            $('#successfulCompanyLogoCoForm').text('');
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
            var formData = new FormData(this);
        }
        $.ajax({
            url: '/file-upload',
            method: 'post',
            data: formData,
            enctype: 'multipart/form-data',
            headers: {
                'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
            },
            contentType: false,
            processData: false,
            cache: false
        }).done(function (formData, status, jqxhr) {
            console.log(formData);
            console.log(status);
            console.log(jqxhr);
            $('#companyLogoCoInfo').val(formData.fileName);
            $('#companyLogoCoForm-info').val('');
            $('#errorCompanyLogoCoForm').text('');
            $('#successfulCompanyLogoCoForm').text('The company\'s logo updated successfully!!!');
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update the company's logo!!!");
        });
        return event.preventDefault();
    });
});

// COMPANY's LOGO PREVIEW
$(function () {
    $('#companyLogoCoForm-info').change(function () {
        let file = this.files[0];
        if (file) {
            let reader = new FileReader();
            reader.onload = function (e) {
                $('#company-logo-img').html('<img width="100%" alt="user-image.png" src="' + e.target.result + '">');
            }
            reader.readAsDataURL(file);
        }
    })
});

// ADD JOB DESCRIPTION BUTTON
$(function () {
    $('#addJobDescriptionBtn').click(function () {
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
                'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
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
                    $('#error-jd-quantity').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-quantity').text("");
                }
                if (value.field == "experience") {
                    $('#error-jd-experience').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-experience').text("");
                }
                if (value.field == "jobDescriptionAddress") {
                    $('#error-jd-address').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#error-jd-address').text("");
                }
                if (value.field == "deadline") {
                    $('#error-jd-deadline').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
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
                // if (value.field == "category") {
                //     $('#error-jd-category').text(value.defaultMessage).css({"display": "block", "color": "red"});
                // } else {
                //     $('#jd-category').text("");
                // }
                if (value.field == "description") {
                    $('#error-jd-description').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-description').text("");
                }
            });
        });
    });
});

// UPDATE JOB DESCRIPTION's INFO
$(function () {
    $('#updateJdInfoBtn').click(function () {
        var data = {
            jobDescriptionId: $('#jobDescriptionId').val(),
            title: $('#jd-ud-title').val(),
            quantity: $('#jd-ud-quantity').val(),
            experience: $('#jd-ud-experience').val(),
            jobDescriptionAddress: $('#jd-ud-address').val(),
            deadline: $('#jd-ud-deadline').val(),
            salary: $('#jd-ud-salary').val(),
            jobDescriptionType: $('#jd-ud-type').val(),
            categoryId: $('#jd-ud-category').val(),
            description: $('#jd-ud-description').val()
        };
        $.ajax({
            url: '/recruiters/job-description-info',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            headers: {
                'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
            },
            cache: false
        }).done(function (data) {
            console.log(data);
            alert("The job description's info updated successfully!!!");
            $('#jdInfoModal').modal('hide');
            // $('#emailCoInfo').val("");
            // $('#companyLogoCoInfo').val("");
            // $('#nameCoInfo').val("");
            // $('#addressCoInfo').val("");
            // $('#phoneNumberCoInfo').val("");
            // $('#descriptionCoInfo').val("");
            // location.reload();
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update job description's info!!!");
            $.each(data.responseJSON.errors, (index, value) => {
                if (value.field == "title") {
                    $('#error-jd-ud-title').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-ud-title').text("");
                }
                if (value.field == "quantity") {
                    $('#error-jd-ud-quantity').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-ud-quantity').text("");
                }
                if (value.field == "experience") {
                    $('#error-jd-ud-experience').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-ud-experience').text("");
                }
                if (value.field == "jobDescriptionAddress") {
                    $('#error-jd-ud-address').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-ud-address').text("");
                }
                if (value.field == "deadline") {
                    $('#error-jd-ud-deadline').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-ud-deadline').text("");
                }
                if (value.field == "salary") {
                    $('#error-jd-ud-salary').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-ud-salary').text("");
                }
                if (value.field == "jobDescriptionType") {
                    $('#error-jd-ud-type').text(value.defaultMessage).css({"display": "block", "color": "red"});
                } else {
                    $('#error-jd-ud-type').text("");
                }
                // if (value.field == "category") {
                //     $('#error-jd-category').text(value.defaultMessage).css({"display": "block", "color": "red"});
                // } else {
                //     $('#jd-category').text("");
                // }
                if (value.field == "description") {
                    $('#error-jd-ud-description').text(value.defaultMessage).css({
                        "display": "block",
                        "color": "red"
                    });
                } else {
                    $('#error-jd-ud-description').text("");
                }
            });
        });
    });
});

// DELETE JOB DESCRIPTION's INFO
$(function () {
    $('#deleteJdBtn').click(function () {
        var data = {
            jobDescriptionId: $('#deleteJdBtn').val(),
        };
        $.ajax({
            url: '/recruiters/job-description-delete',
            method: 'post',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: "json",
            headers: {
                'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
            },
            cache: false
        }).done(function (data) {
            console.log(data);
            alert("The job description's info updated successfully!!!");
            $('#deleteJdBtnModal').modal('hide');
            location.reload();
        }).fail(function (data, status, jqxhr) {
            console.log(data);
            console.log(status);
            console.log(jqxhr);
            alert("Failed to update job description's info!!!");
        });
    });
});

// CLEAR UPLOAD BUTTON
$(function () {
    $('.clearUploadBtn').click(function (event) {
        if ($('.userFileReInfoForm-info').val() != 0) {
            alert("The uploaded image canceled!!!")
            $('#company-logo-img').html('<img th:src="*{companyLogo}" width="100%" alt="company-logo.png">');
            $('.user-image-preview').html('<img th:src="*{userImage}" width="100%" alt="user-image.png">');
            $('#errorCompanyLogoCoForm').text('');
            $('.errorUserFileReInfoForm').text('');
            $('.userFileReInfoForm-info').val("");
        } else if ($('#userCvReInfoForm-info-id').val() != 0) {
            alert("The uploaded CV canceled!!!")
            $('.errorCompanyLogoCoForm').text('');
            $('#userCvReInfoForm-info-id').val("");
        } else {
            alert("There is not any file to be canceled!!!")
        }
    });
});

// SHOW UPLOAD CV BUTTON
function showUploadCvButton(btn) {
    if (btn.value == "newCv") {
        $('#showUploadCvId').removeClass('hiddenAll');
    } else {
        $('#showUploadCvId').addClass('hiddenAll');
    }
}

// APPLY FOR THE JOB BUTTON
function applyJob(jobDescriptionId) {
    var data = {
        jobDescriptionId: jobDescriptionId,
        cvName: $('.userCvReInfo').val(),
        additionalInfo: $('#additionalInfoArea').val(),
    };
    $.ajax({
        url: '/candidates/job-application',
        method: 'post',
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: "json",
        headers: {
            'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
        },
        cache: false
    }).done(function (data, status, jqxhr) {
        console.log(data);
        alert("Successfully applied for the job!!!");
        $('#applyForJobBtnModal').modal('hide');
        $('.userCvReInfo').html("");
        $('#additionalInfo').html("");
    }).fail(function (data, status, jqxhr) {
        console.log(data);
        console.log(status);
        console.log(jqxhr);
        alert("Failed to apply for the job!!!");
        $.each(data.responseJSON.errors, (index, value) => {
            if (value.field == "additionalInfo") {
                $('#errorDescriptionApplyBtn').text(value.defaultMessage).css({"display": "block", "color": "red"});
            } else {
                $('#errorDescriptionApplyBtn').text("");
            }
        });
    });
}

// FAVOURITE BUTTON
function changeFavoriteBtnStatus(id, jobOrCom) {
    var data = null;
    let favoriteBtn = null;
    let favoriteBtnIcon = null;
    let favoriteJobStatus = 0;
    if (jobOrCom === 0){
        favoriteBtn = $('#favoriteJobBtn-' + id);

        favoriteBtn.toggleClass("btn-warning btn-light")

        favoriteBtnIcon = $('#favoriteJobBtnIcon-' + id);

        favoriteBtnIcon.toggleClass("fa-solid fa-heart fa-regular fa-heart")

        if (favoriteBtn.hasClass("btn-warning") && favoriteBtnIcon.hasClass("fa-solid fa-heart")) {
            favoriteJobStatus = 1;
        }
        data ={
            jobDescriptionId: id,
            favoriteJobStatus: favoriteJobStatus
        };
    }else if (jobOrCom === 1){
        favoriteBtn = $('#favoriteComBtn-' + id);

        favoriteBtn.toggleClass("btn-warning btn-light")

        favoriteBtnIcon = $('#favoriteComBtnIcon-' + id);

        favoriteBtnIcon.toggleClass("fa-solid fa-heart fa-regular fa-heart")

        favoriteJobStatus = 2;
        if (favoriteBtn.hasClass("btn-warning") && favoriteBtnIcon.hasClass("fa-solid fa-heart")) {
            favoriteJobStatus = 3;
        }
        data ={
            companyId: id,
            favoriteJobStatus: favoriteJobStatus
        };
    }
    $.ajax({
        url: '/candidates/favorite-job',
        method: 'post',
        data: JSON.stringify(data),
        contentType: 'application/json',
        headers: {
            'X-XSRF-TOKEN': $('input[name="_csrf"]').val()
        },
        dataType: "json",
        cache: false
    }).done(function (data) {
        console.log(data);
        if (favoriteJobStatus === 1 && favoriteBtn.hasClass("btn-warning") && favoriteBtnIcon.hasClass("fa-solid fa-heart")){
            alert("The favorite job description added to the list successfully!!!");
        }
        if (favoriteJobStatus === 0 && favoriteBtn.hasClass("btn-light") && favoriteBtnIcon.hasClass("fa-regular fa-heart")){
            alert("The favorite job description deleted from the list successfully!!!");
        }
        if (favoriteJobStatus === 3 && favoriteBtn.hasClass("btn-warning") && favoriteBtnIcon.hasClass("fa-solid fa-heart")){
            alert("The favorite company added to the list successfully!!!");
        }
        if (favoriteJobStatus === 2 && favoriteBtn.hasClass("btn-light") && favoriteBtnIcon.hasClass("fa-regular fa-heart")){
            alert("The favorite company deleted from the list successfully!!!");
        }
    }).fail(function (data, status, jqxhr) {
        console.log(data);
        console.log(status);
        console.log(jqxhr);
        alert("Failed to add favorite job description!!!");
    });
}