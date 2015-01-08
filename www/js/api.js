var API_BASE_URL = "http://localhost:8080/Maverick-api/";
var username = getCookie('username');
//console.log(username);	
var search = getCookie("search");

$(document).ready(function() {
	$('<strong> </strong>' + username + '<br>').appendTo($('#usernameprofile'));
Userdata(username);
});



$('#searchartist').click(function(e) {
	e.preventDefault();
  var elementobusqueda = $("#artisttosearch").val();
  $.cookie('elementobusqueda', elementobusqueda);
    $.cookie('elementobusqueda');
  
 
	window.location.replace("file:///C:/Users/DaviD/Desktop/wwwproyecto/search.html");
});

$('#followingtab').click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
		  
	getArtist($('#artisttosearch').val());
        
	
});

$('#deleteprofilefinal').click(function(e){
	var usernametodelete =getCookie('username');
	e.preventDefault();
	$("#result").text(' ');
		  
	deleteUser(username);
        
	
});


$("#updateButton").click(function(e){
	var useredit={
		"name" : $('#nametoedit').val(),
		"email" : $('#emailtoedit').val(),
	"description" : $('#descriptiontoedit').val()
	
	
}
console.log(useredit);
	e.preventDefault();
	$("#result").text(' ');
		  
	editUser(useredit);
        
	
});



function getArtist(artisttosearch) {
var url = API_BASE_URL + 'search?username=' + artisttosearch;

console.log(url);
	
	$("'#searchtab').text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

	var listausers = data;
			console.log(listausers);
				
				$.each(listausers.users, function(i,v){
					var user = v;
					console.log(user);
						
					
					
					$('<h4> Search: </h4>').appendTo($('#myResults'));
					$('<p>').appendTo($('#myResults'));
					$('<strong> Username: </strong>' + user.username + '<br>').appendTo($('#myResults'));
				    $('<strong> Description: </strong>' + user.description + '<br>').appendTo($('#myResults'));
					
					
					$('</p>').appendTo($('#myResults'));
				
				});
				
				
				

				
				
			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> No se pueden cargar los datos del usuario </div>').appendTo($("#myResults"));
	});

}

function Userdata(username) {
var url = API_BASE_URL + 'users/' + username;

console.log(url);
	
	$('#searchtab').text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

	var listausers = data;
			console.log(listausers);
				
				
					
					
					
					$('<a href="#"  style="color: #00FF00"><strong> Name: </strong></a>' + listausers.name + '<br>').appendTo($('#nameofuser'));
					$('<a href="#"  style="color: #00FF00"><strong> Email: </strong></a>' + listausers.email + '<br>').appendTo($('#emailofuser'));
					$('<a href="#"  style="color: #00FF00"><strong> Description: </strong></a>' + listausers.description + '<br>').appendTo($('#descriptionofuser'));
				   
					
				
				
				
				

				
				
			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> Todo not found </div>').appendTo($("#myResults"));
	});

}

function deleteUser(usernametodelete) {
var url = API_BASE_URL + 'users/' + username;

console.log(url);
	
	$('#searchtab').text('');

	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,

		dataType : 'json',
	}).done(function(data, status, jqxhr) {

	 alert('Usuario eliminado correctamente, Esperamos volver a verle algún día');
	 window.location.replace("file:///C:/Users/DaviD/Desktop/wwwproyecto/signin.html");
				
				
				
				

				
				
			}).fail(function() {
				 alert('No se ha podido borrar');
	});

}

function 	editUser(useredit) {
var url = API_BASE_URL + 'users/' + username;
console.log(useredit);
console.log(url);
	var todo = JSON.stringify(useredit);
	$('#searchtab').text('');

	$.ajax({
		url : url,
		type : 'PUT',
		crossDomain : true,
		contentType: 'application/vnd.maverick.api.user+json',
		dataType : 'json',
		data: todo,
	}).done(function(data, status, jqxhr) {
console.log(data);
	 alert('Datos Modificados Correctamente');
	 location.reload();
				
			}).fail(function() {
				 alert('No se ha podido editar');
	});

}


function getCookie(name){
  var pattern = RegExp(name + "=.[^;]*");
  matched = document.cookie.match(pattern);
  if(matched){
      var cookie = matched[0].split('=');
      return cookie[1];
  }
  return false;
}