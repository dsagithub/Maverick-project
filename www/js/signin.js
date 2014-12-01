var API_BASE_URL = "http://localhost:8080/Maverick-api";

$("#button_signin").click(function(e) {
	e.preventDefault();
	
	var login = new Object();
	login.username=$("#username").val();
	login.userpass=$("#password").val();
	document.cookie = "username=" + login.username;
	document.cookie = "userpass=" + login.userpass;
	console.log(document.cookie);
	Login(login);
});

$("#button_register").click(function(e) {
	e.preventDefault();
	window.location.replace("C:/Users/david/Desktop/register.html");
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
				window.location.replace("C:/Users/david/Desktop/dashboard.html");
		 

	}).fail(function() {
		 alert("Username o contraseña incorrectos");  
	});


}
function getCookie(name) {
	var pattern = RegExp(name + "=.[^;]*");
	matched = document.cookie.match(pattern);
	if (matched) {
		var cookie = matched[0].split('=');
		return cookie[1];
	}
	return false;
}