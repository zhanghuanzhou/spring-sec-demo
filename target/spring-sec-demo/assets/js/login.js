(function(){
	$(function(){
		/*if(canVerify){
			recode();
			$(".verify.hide").removeClass("hide");
		}*/

		$("#myForm [type=submit]").click(function(){
			signIn($("#myForm").serialize() , function(data, result){
				if (result == 'failure') {
					$(".form-error .message").html(data.error);
					$(".form-error").removeClass("hide");
				} else if (result == 'success') {
					$(".form-error .message").html("");
					$(".form-error").addClass("hide");
					// 跳转到首页
					window.location.href = "index.jsp";
				}

				/*if(status < 0){
					if(item.errorNum != null && item.errorNum >= 3){
						recode();
						$(".verify.hide").removeClass("hide");
					}
				}*/
			});
		});

		/*$("#changeVerifyImage").click(recode);*/
	});

	function recode(){
		$("#verifyImage").attr("src", localDomain + "/verifyImage?a="+Math.random());
	}

	function signIn(formData, callback) {
		$.ajax({
			url: "login/process",
			type: "post",
			data: formData,
            dataType: 'json',
			error: function (xhr, error, e) {
				alert(error);
			},
			success: function (data) {
				callback(data, data.result);
			}
		});
	}
})();
