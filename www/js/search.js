var API_BASE_URL = "http://localhost:8080/Maverick-api/";
var username = getCookie('username');
//console.log(username);	
var search = getCookie('elementobusqueda');

var songsResult;

$(document).ready(function() {
  
searchpendiente(search);
});


$('#searchartist').click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
	getArtist($('#artisttosearch').val());
       
	
});

$('#searchsong').click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
	if($('#songtosearch').val() == ""){
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Debes proporcionar un  nombre de canción </div>').appendTo($("#myResults"));
	}else{
	getSong($('#songtosearch').val());
	}
	
       
	
});


$("#info_detail").click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
		  var ver = getCookie('verusername');
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
				 	
					window.location.replace('C:/Users/DaviD/Desktop/wwwproyecto/seeprofile.html');
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
					$('<h4> Datos usuario </h4>' ).appendTo($('#myResults'));
					$('<strong> Username: </strong>' + user.username + '<br>').appendTo($('#myResults'));
					$('<strong> Description: </strong>' + user.description + '<br>').appendTo($('#myResults'));
					$.cookie('verusername', user.username);
					$.cookie('verusername');
					var userlink =  $("#myResults").append('<tr><th><a id="user" href="#myResults">'+ 'Ver perfil' +'</a></th></tr>');
					console.log(user);
					
					userlink.click(function (e){
				e.preventDefault();
				// alert("Datos Modificados Correctamente");
				 	
					window.location.replace('C:/Users/DaviD/Desktop/wwwproyecto/seeprofile.html');
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

function getSong(songtosearch) {
var url = API_BASE_URL + 'songs/search?song_name=' + songtosearch;
$("#myResults").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var songs = data;
				console.log(songs);
				
					songsResult = songs;
					$.each(songs.songs, function(i, v) {
					
					var song = v;
					$('<h4> Datos canción </h4>' ).appendTo($('#myResults'));
					$('<p>').appendTo($('#myResults'));
					$('<strong> Nombre: </strong>' + song.song_name + '<br>').appendTo($('#myResults'));
				    $('<strong> Username: </strong>' + song.username + '<br>').appendTo($('#myResults'));
					$('<strong> Id: </strong>' + song.songid + '<br>').appendTo($('#myResults'));
					$('<strong> Album: </strong>' + song.album + '<br>').appendTo($('#myResults')); 
				    $('<strong> Style: </strong>' + song.style + '<br>').appendTo($('#myResults'));
					$('<strong> Fecha: </strong>' + song.fecha + '<br>').appendTo($('#myResults'));
					$('<strong> Likes: </strong>' + song.likes + '<br>').appendTo($('#myResults'));
					console.log(data);
					$('</p>').appendTo($('#myResults'));
								
	
					});
     
				
			}).fail(function() {
			
			$('<div class="alert alert-danger"> <strong>Oh!</strong> No hay canciones con ese nombre </div>').appendTo($("#myResults"));
	});

}


function getProfile(ver) {
var url = API_BASE_URL + '/songs/search?song_name=' + ver;

console.log(url);
	

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
					$("#myResults").append('<button  type="button" class="btn btn-primary" id=info_detail onClick="verPerfil('+user.username+')">Ver</button>');				
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

