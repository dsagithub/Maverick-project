var API_BASE_URL = "http://147.83.7.156:8080/grupo3.dsa.eetac/Maverick-api/users";

$('#fan').click(function (e){
	role = $('#fan').val();
	console.log(role);
});
var role;
$('#artist').click(function (e){
	role = $('#artist').val();
	console.log(role);
});

$('#signin').click(function(e){
	e.preventDefault();
	$("#result").text(' ');
	
		  
	var nuevoUser ;
	nuevoUser= {
			'username' : $('#username').val(),
			'userpass' : $('#password').val(),
			"name" : $("#name").val(),
			"email" : $("#email").val(),
			"description" : $("#description").val(),
			'rolename' : role
			
			 
		
		}
		crearUser(nuevoUser);
		console.log(nuevoUser);

	
});

function crearUser(nuevoUser) {
	var url = API_BASE_URL;
	var data = JSON.stringify(nuevoUser);
	
console.log(data);
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		contentType: 'application/vnd.maverick.api.user+json',
		dataType : 'json',
		data : data,
	}).done(function(data, status, jqxhr) {
		var info= data;
		$.cookie('username', info.username);
		$.cookie('username');
		$.cookie('role', info.role);
		$.cookie('role');

		
			window.location.replace("dashboard.html");
			
  	}).fail(function() {
	if (status == "409"){ alert('Ya existe un usuario con este username'); 
	}else if (stauts =="500"){
	
		 alert('Se ha producido un error'); }
		 else if (stauts =="400"){
	
		 alert('No se ha selecionado ningún rol de usuario'); }
	});

}
