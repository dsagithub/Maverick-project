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

$('#button_consultar').click(function(e){
	
	e.preventDefault();
	getComent($('#nombrecomentarios').val());
	
});

function getComent(nombrecomentarios) {
var url = API_BASE_URL + 'songs/' + nombrecomentarios + '/comments';
$("#myResults").text('');
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var songs = data;
				console.log(data);
				console.log(url);
				$('<h4> Comentarios canción </h4>').appendTo($('#myResults'));
			    $.each(songs.songs, function(i, v) {
				var song = v;
					
					$('<strong> Nombre canción: </strong>' + song.song_name + '<br>').appendTo($('#myResults'));
				    $('<strong> Autor: </strong>' + song.username + '<br>').appendTo($('#myResults'));
				    $('<strong> Comentario : </strong>' + song.text + '<br>').appendTo($('#myResults'));
					$('<strong> Fecha : </strong>' + song.fechacomment + '<br>').appendTo($('#myResults'));
					$('</p>').appendTo($('#myResults'));
			
				    $("#myResults").append('</div>');
					});
				
			}).fail(function() {
			
			$('<div class="alert alert-danger"> <strong>Oh!</strong> No hay canciones con ese nombre </div>').appendTo($("#myResults"));
	});
	

}


$("#info_detail").click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
		  var ver = getCookie('verusername');
	getProfile(ver);
       
	
});

$('#logout').click(function(e){
	
	e.preventDefault();      
	
    alert("Gracias por utilizar Maverick " + username + ", hasta pronto!");	
		window.location.replace("signin.html");
	

});
       
	

function searchpendiente(search) {
var url = API_BASE_URL + 'users/search?username=' + search;

//console.log(url);
	
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
				 	
					window.location.replace('seeprofile.html');
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
					window.location.replace('seeprofile.html');
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

/*
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
					$('<h4> Datos canción: </h4> ').appendTo($('#myResults'));
					$('<p>').appendTo($('#myResults'));
					$('<strong> Datos Canción Nombre: </strong>' + song.song_name + '<br>').appendTo($('#myResults'));
				    $('<strong> Username: </strong>' + song.username + '<br>').appendTo($('#myResults'));
					$('<strong> Style: </strong>' + song.style + '<br>').appendTo($('#myResults'));
					$('<strong> Fecha: </strong>' + song.last_modified + '<br>').appendTo($('#myResults'));
					$('<h4> Reproducir </h4>' ).appendTo($('#myResults'));
					$('<div class="col-sd-4"><audio id ="song' + song.songid +'" src="'+ song.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button onclick="document.getElementById(\'song ' + song.songid +'\').play()">Reproducir</button> <button onclick="document.getElementById(\'song ' + song.songid +'\').pause()">Pausar</button><button onclick="document.getElementById(\'song ' + song.songid +'\').volume+=0.1">Sube Volumen</button><button onclick="document.getElementById(\'song ' + song.songid +'\').volume-=0.1">Baja Volumen</button> </div></audio></div>').appendTo($('#myResults'));
                    $('<strong> Likes: </strong>' + song.likes + '<br>').appendTo($('#myResults'));
					//$('<strong> URL reproductor: </strong>' + song.songURL + '<br>').appendTo($('#myResults'));
				    // $('<h4> -------------- </h4>' ).appendTo($('#myResults'));
					console.log(data);
					$('</p>').appendTo($('#myResults'));
				
					});
     
				
			}).fail(function() {
			
			$('<div class="alert alert-danger"> <strong>Oh!</strong> No hay canciones con ese nombre </div>').appendTo($("#myResults"));
	});

}
*/

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
				//console.log(songs);
				var html='';
			  // si la consulta ajax devuelve datos
			 
				//	if(data.length > 0){
					//songsResult = songs;
					$.each(songs.songs, function(i, v) {
					
					 html += '<tr>'
					//var song = v;
					//$('<h4> Datos canción: </h4> ').appendTo($('#myResults'));
					//$('<p>').appendTo($('#myResults'));
					var button = $("tableDeposits").append('<button type="button" align="center" class="btn btn-success" href="#coments"  id="button">Coments</button>');
					 html += '<td>'+v.song_name+'</td>'
					 html += '<td>'+ v.username+'</td>'
				     html += '<td>'+v.style+'</td>'
					 html += '<td>'+v.last_modified+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
					 html +='<td>'+ button + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + v.songid +'" src="'+ v.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + v.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 
					
					button.click(function(e){
	
					e.preventDefault();
					alert("dentro");
					//getComent($('v.song_name').val());
					});
	
	
					
					console.log(data);
					//$('</p>').appendTo($('#myResults'));
				 html += '</tr>';
				    });
              //} 
			  // si no hay datos mostramos mensaje de no encontraron registros
                if(html == '') html = '<tr><td colspan="6">No se encontraron registros, revisa los datos a consultar...</td></tr>'
                // añadimos  a nuestra tabla todos los datos encontrados mediante la funcion html
                $("#tableDeposits tbody").html(html);  
                  
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

