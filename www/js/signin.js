var API_BASE_URL = "http://localhost:8080/Maverick-api";
var username;
$("#button_signin").click(function(e) {
	e.preventDefault();
	
	var login = new Object();
	login.username=$("#username").val();
	login.userpass=$("#password").val();
  
	
	Login(login);
});

$("#button_register").click(function(e) {
	e.preventDefault();
	window.location.replace("register.html");
});

function Login(login){
	var url= API_BASE_URL+'/users/login';
	var data = JSON.stringify(login);
	console.log(data);
	
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		dataType:'json',
		contentType: 'application/vnd.maverick.api.user+json',
		data: data,
	}).done(function(data, status, jqxhr) {
				var info= data;
				console.log(info);
				console.log(info.loginSuccessful);
				if (info.loginSuccessful == true){
					$.cookie('username', info.username);
					$.cookie('username');
							console.log(info.loginSuccessful);
	
				window.location.replace("dashboard.html");

				}
				else {		alert("contraseña incorrecta"); 
					console.log(info.loginSuccessful);
						
		      
					
					
					
					
					
				
			}
		 

	}).fail(function() {
		 alert("Username o contraseña incorrectos");  
	});


}
