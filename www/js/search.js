var API_BASE_URL = "http://localhost:8080/Maverick-api/";
var username = getCookie("username");
//console.log(username);	
var search = getCookie("elementobusqueda");

$(document).ready(function() {
  
searchpendiente(search);
});


$("#searchartist").click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
		  
	getArtist($('#artisttosearch').val());
       
	
});
$("#info_detail").click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
		  var ver = getCookie("verusername");
	getProfile(ver);
       
	
});


function searchpendiente(search) {
var url = API_BASE_URL + 'users/search?username=' + search;

console.log(url);
	
	//$("#searchtab").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

	var listausers = data;
			console.log(listausers);
				
				$.each(listausers.users, function(i,v){
					var user =  v;
					$('<strong> Username: </strong>' + user.username + '<br>').appendTo($('#myResults'));
					$('<strong> Description: </strong>' + user.description + '<br>').appendTo($('#myResults'));
					$.cookie('verusername', user.username);
					$.cookie('verusername');
					var userlink =  $("#myResults").append('<tr><th><a id="user" href="#myResults">'+ 'Ver perfil' +'</a></th></tr>');
					console.log(user);

					$.removeCookie('elementobusqueda');
					userlink.click(function (e){
				e.preventDefault();
				// alert("Datos Modificados Correctamente");
				 	
					window.location.replace("file:///C:/Users/david/Desktop/seeprofile.html");
				//loadProfile(userjs.getLink('self').href);
				//$('#searchpg').hide();
				//$('#profilepg').show();
				
			});

					//$('<h4> Search: </h4>').appendTo($('#myResults'));
					//$('<p>').appendTo($('#myResults'));
					//$('<strong> Username: </strong>' + user.username + '<br>').appendTo($('#myResults'));
				    //$('<strong> Description: </strong>' + user.description + '<br>').appendTo($('#myResults'));
				    //$("#myResults").append('</div>');
				//$("#myResults").append('<button  type="button" class="btn btn-primary"  id=info_detail onClick="verPerfil('+user.username+')">Ver</button>');				
				//$("#myResults").append('</div>');
//	$.cookie('verusername', user.username);
//		$.cookie('verusername');

				   
					
					
					//$('</p>').appendTo($('#myResults'));
				
				});
				
				
				

				
				
			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> No se pueden cargar los datos del usuario </div>').appendTo($("#myResults"));
	});

}


function getArtist(artisttosearch) {
var url = API_BASE_URL + 'users/search?username=' + artisttosearch;

console.log(url);
	
	//$("#searchtab").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {

	var listausers = data;
			console.log(listausers);
				
				$.each(listausers.users, function(i,v){
					var user =  v;
					$('<strong> Username: </strong>' + user.username + '<br>').appendTo($('#myResults'));
					$('<strong> Description: </strong>' + user.description + '<br>').appendTo($('#myResults'));
					$.cookie('verusername', user.username);
					$.cookie('verusername');
					var userlink =  $("#myResults").append('<tr><th><a id="user" href="#myResults">'+ 'Ver perfil' +'</a></th></tr>');
					console.log(user);
					
					userlink.click(function (e){
				e.preventDefault();
				// alert("Datos Modificados Correctamente");
				 	
					window.location.replace("file:///C:/Users/david/Desktop/seeprofile.html");
				//loadProfile(userjs.getLink('self').href);
				//$('#searchpg').hide();
				//$('#profilepg').show();
				
			});

					//$('<h4> Search: </h4>').appendTo($('#myResults'));
					//$('<p>').appendTo($('#myResults'));
					//$('<strong> Username: </strong>' + user.username + '<br>').appendTo($('#myResults'));
				    //$('<strong> Description: </strong>' + user.description + '<br>').appendTo($('#myResults'));
				    //$("#myResults").append('</div>');
				//$("#myResults").append('<button  type="button" class="btn btn-primary"  id=info_detail onClick="verPerfil('+user.username+')">Ver</button>');				
				//$("#myResults").append('</div>');
//	$.cookie('verusername', user.username);
//		$.cookie('verusername');

				   
					
					
					//$('</p>').appendTo($('#myResults'));
				
				});
				
				
				

				
				
			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> No se pueden cargar los datos del usuario </div>').appendTo($("#myResults"));
	});

}

function getProfile(ver) {
var url = API_BASE_URL + 'users/' + ver;

console.log(url);
	
	//$("#searchtab").text('');

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
				    $("#myResults").append('</div>');
				$("#myResults").append('<button  type="button" class="btn btn-primary"  id=info_detail onClick="verPerfil('+user.username+')">Ver</button>');				
				$("#myResults").append('</div>');
	$.cookie('verusername', user.username);
		$.cookie('verusername');
				   
					
					
					$('</p>').appendTo($('#myResults'));
				
				});
				
				
				

				
				
			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> No se pueden cargar los datos del usuario </div>').appendTo($("#myResults"));
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