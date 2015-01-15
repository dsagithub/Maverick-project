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
	getSong($('#songtosearch').val());
	getComent($('#songtosearch').val())
	
	
       
	
});

$('#button_consultar').click(function(e){
	
	e.preventDefault();
	getComent($('#nombrecomentarios').val());
	
});

$('#button_delete').click(function(e){
	
	e.preventDefault();
	deleteComment($("#nombrecomentarios").val(),$("#idcomentarios").val())
});

function deleteComment(nombrecomentarios, idcomentarios) {
	var url = 'http://localhost:8080/Maverick-api/songs/' + nombrecomentarios + '/comment/'+idcomentarios;
	console.log(url);
	alert("dins");
	alert(url);
	alert(nombrecomentarios);
	alert(idcomentarios);
	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {			
		alert("comment eliminado");
	}).fail(function() {
		alert("ERROR");
	});
}

$('#button_create').click(function(e){
	
	e.preventDefault();
	 var newComment = new Object();
	newComment.song_name = $("#nombrecomentarios").val();
	newComment.username = $("#nombreusername").val();
	newComment.text = $("#comentario").val();
	createComment(newComment);
});

function createComment(newComment) {
	var url = API_BASE_URL + 'songs/' + newComment.song_name + '/comment';
	alert(newComment.song_name);
	console.log(url);
	var data = JSON.stringify(newComment);

	$("#myResults").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		contentType : 'application/vnd.maverick.api.comment+json',
		data : data,
	}).done(function(data, status, jqxhr) {
		$('<div class="alert alert-danger"> <strong>Ok!</strong> Error</div>').appendTo($("#myResults2"));				
  	}).fail(function() {
		$('<div class="alert alert-success"> <strong>Oh!</strong> Created </div>').appendTo($("#myResults2"));
	});

}


$("#info_detail").click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
		  var ver = getCookie('verusername');
	getProfile(ver);
       
	
});
$("#logout").click(function(e) {
	e.preventDefault();
  document.cookie = 'username=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
  
 console.log("hola");
	window.location.replace("index.html");
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
					document.cookie = 'elementobusqueda=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
					userlink.click(function (e){
					e.preventDefault();
				// alert("Datos Modificados Correctamente");
				 	
					window.location.replace("seeprofile.html");

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
function searchpendiente2(search2) {
var url = API_BASE_URL + 'songs/search?song_name='+ search2;

//console.log(url);
	
	//$("#searchtab").text('');

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
					songsResult = songs;
					 
					$.each(songs.songs, function(i, v) {
					var song = v.song_name;
					 html += '<tr>'
					//var song = v;
					//$('<h4> Datos canción: </h4> ').appendTo($('#myResults'));
					//$('<p>').appendTo($('#myResults'));
				//	var button = $("tableDeposits").append('<button type="button" align="center" class="btn btn-success" href="#coments"  id="button">Coments</button>');
					 html += '<td>'+v.song_name+'</td>'
					 html += '<td>'+ v.username+'</td>'
				     html += '<td>'+v.style+'</td>'
					 html += '<td>'+v.last_modified+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
				     html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComment('+ v.song_name +').dialog("open")>Ver' +  '</td>'
					// html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComent('+ v.song_name +')>Ver' + '<button type="button" class="btn btn-danger" onclick=crearComent('+ v.song_name +')>Crear' + '<button type="button" class="btn btn-danger" onclick=DeleteComent('+ v.song_name +')>Borrar' + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + v.songid +'" src="'+ v.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + v.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 
			         
					
					
					//alert(v.song_name);
						//console.log(v.song_name);
					//var nom = (songsResult.songs.song_name);
				//	button.onclick(function(e){
				//var busqueda= document.getElementById(tableDeposits[0]);
	
					//e.preventDefault();
					//alert("dentro");
					//getComent($('v.song_name').val());
					//});

					
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
	
	//var song = songsResult.songs.song_name;
	
	
}
*/
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
				 	
					window.location.replace("file:///C:/Users/Felipe/git/Maverick-project/www/seeprofile.html");
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
					songsResult = songs;
					 
					$.each(songs.songs, function(i, v) {
					var song = v.song_name;
					 html += '<tr>'
					//var song = v;
					//$('<h4> Datos canción: </h4> ').appendTo($('#myResults'));
					//$('<p>').appendTo($('#myResults'));
				//	var button = $("tableDeposits").append('<button type="button" align="center" class="btn btn-success" href="#coments"  id="button">Coments</button>');
					 html += '<td>'+v.song_name+'</td>'
					 html += '<td>'+ v.username+'</td>'
				     html += '<td>'+v.style+'</td>'
					 html += '<td>'+v.last_modified+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + v.songid +'" src="'+ v.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + v.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 
			         

					
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
	
	//var song = songsResult.songs.song_name;
	
	
}

function getComent(songtosearch) {
var url = API_BASE_URL + 'songs/' + songtosearch+ '/comments';
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
					songsResult = songs;
					 
					$.each(songs.songs, function(i, v) {
					var song = v;
					 html += '<tr>'
					//var song = v;
					//$('<h4> Datos canción: </h4> ').appendTo($('#myResults'));
					//$('<p>').appendTo($('#myResults'));
				//	var button = $("tableDeposits").append('<button type="button" align="center" class="btn btn-success" href="#coments"  id="button">Coments</button>');
					 html += '<td>'+v.commentid+'</td>'
					 html += '<td>'+ v.username+'</td>'
				     html += '<td>'+v.text+'</td>'
					 html += '<td>'+v.last_modified+'</td>'
					 html += '<td>'+v.song_name+'</td>'
		
					
					console.log(data);
				
				 html += '</tr>';
				    });
	
              //} 
			  // si no hay datos mostramos mensaje de no encontraron registros
                if(html == '') html = '<tr><td colspan="6">No se encontraron registros, revisa los datos a consultar...</td></tr>'
                // añadimos  a nuestra tabla todos los datos encontrados mediante la funcion html
                $("#tableDeposits2 tbody").html(html);  
                  
			}).fail(function() {
			
			$('<div class="alert alert-danger"> <strong>Oh!</strong> No hay canciones con ese nombre </div>').appendTo($("#myResults"));
	});
	
	//var song = songsResult.songs.song_name;
	
	
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