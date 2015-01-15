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
  
 

	window.location.replace("/search.html");
});


$("#followingtab").click(function(e){
	console.log("pulsamos el boton");
	e.preventDefault();
	$("#result").text(' ');	  
	getArtist2(username);
        console.log("adios");
	
});



function getArtist2(username){

	var url = API_BASE_URL +'users/'+ username  +'/following';
	console.log(url);

	
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var songs = data;
				//console.log(songs);
				var html='';
			  // si la consulta ajax devuelve datos
			 
				//	if(data.length > 0){
					songsResult = songs;
					$.each(songs.users, function(i, v) {
					var todo = v;
					 html += '<tr>'
					
					 html += '<td>'+todo.username+'</td>'
					 html += '<td>'+todo.description+'</td>'
					 html += '<td>'+todo.name+'</td>'
				   

					console.log(data);
					//$('</p>').appendTo($('#myResults'));
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



$("#followertab").click(function(e){
	console.log("pulsamos el boton");
	e.preventDefault();
	$("#result").text(' ');
		  
	getArtist3(username);
        console.log("adios");
	
});


function getArtist3(username){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'users/'+ username +'/follower';

	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var songs = data;
				//console.log(songs);
				var html='';
			  // si la consulta ajax devuelve datos
			 
				//	if(data.length > 0){
					songsResult = songs;
					$.each(songs.users, function(i, v) {
					var todo = v;
					 html += '<tr>'
					
					 html += '<td>'+todo.username+'</td>'
					 html += '<td>'+todo.description+'</td>'
					 html += '<td>'+todo.name+'</td>'
				   

					console.log(data);
					//$('</p>').appendTo($('#myResults'));
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

$('#megusta').click(function(e){
	 e.preventDefault();
	 //$('#megusta').text('');
	 $('#megusta').hide();
	 var nombre_cancion;
	 var me_gusta;
	 nombre_cancion= {
	 "song_name": "maverick"}
	//me_gusta ={"likes": "1"}
	 Megusta(nombre_cancion);
	console.log(nombre_cancion);
});

function Megusta(nombre_cancion) {
	var url = API_BASE_URL + 'songs/likes/maverick';
	var data= JSON.stringify(nombre_cancion);
	console.log(url);
	$.ajax({
		url : url,
		type : 'PUT',
		crossDomain : true,
		dataType : 'json',
		contentType : 'application/vnd.maverick.api.song+json',
		data:data,
	}).done(function(data, status, jqxhr) {
		//getArtist();
		//$.each(songs.songs, function(i,v){
		alert("Te gusta esto!");
		//alert("likes" + me_gusta)
		//});
	}).fail(function() {
		$("#myResults").text("NO RESULT");
	});
}

$("#songs").click(function(e){
	console.log("pulsamos el boton");
	e.preventDefault();
	$("#result").text(' ');
		  
	getArtist4(username);
        console.log("adios");
	
});

function getArtist4(){
	var url = API_BASE_URL +'songs/search2?username=' + username;

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
		var songs = data;
				//console.log(songs);
				var html='';
			  // si la consulta ajax devuelve datos
			 
				//	if(data.length > 0){
					songsResult = songs;
					 
					$.each(songs.songs, function(i, v) {
					var todo = v;
					 html += '<tr>'
					
					 html += '<td>'+todo.song_name+'</td>'
					 html += '<td>'+todo.last_modified+'</td>'
					 html += '<td>'+todo.style+'</td>'
				     html += '<td>'+ todo.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
				     html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComment('+ todo.song_name +').dialog("open")>Ver' +  '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + todo.songid +'" src="'+ todo.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + todo.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 
			         

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

/*
$('#uploadButton').click(function(e){
	
	e.preventDefault();
	var newSong = new Object();
	newSong.song_name = $("#inputSong").val()
	newSong.username = $("#inputUsername").val()
	newSong.album_name = $("#album").val()
	newSong.description = $("#description").val()
	newSong.style = $("#inputStyke").val()
	newSong.songfile = $("#inputFile").val()
	newSong.likes = $("#inputLikes").val()
	createSong(newSong);
	console.log(newSong);
	
        
	
});
*/
/*
	function createSong(newSong) {
	var url = API_BASE_URL + 'songs/';
	alert("dins");
	//var formData = new FormData($('form')[0]);
	var formData = new FormData(newSong);
	$.ajax({
		url: url,
		type: 'POST',
		xhr: function() {  
	    	var myXhr = $.ajaxSettings.xhr();
	        if(myXhr.upload){ 
	        myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // For handling the progress of the upload
	        }
	        return myXhr;
        },
		crossDomain : true,
		data: formData,
		cache: false,
		contentType:false,
        processData: false
	}).done(function(data, status, jqxhr) {
		console.log(data);
		var response = $.parseJSON(jqxhr.responseText);
        $('#uploadedAudio').attr('src', response.songURL);	
		$('<div class="alert alert-success"> <strong>Ok!</strong> Repository Created</div>').appendTo($("#create_result"));	
			
  	}).fail(function (jqXHR, textStatus) {
    	alert("KO");
		console.log(formData);
	});
}
	*/


$('#imageForm').submit(function(e){
	alert("hjhkjh");
	e.preventDefault();
	$('progress').toggle();
	var url = API_BASE_URL + "songs/";
	var formData = new FormData($('#imageForm')[0]);
	
	console.log(url);
	$.ajax({
					url: url,
					type: 'POST',
					xhr: function() {  
						var myXhr = $.ajaxSettings.xhr();
						if(myXhr.upload){ 
							alert("jkhkjhk");
							myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // For handling the progress of the upload
						}
						return myXhr;
					},
						crossDomain : true,
						data: formData,
						cache: false,
						contentType: false,
						processData: false
	})
	.done(function (data, status, jqxhr)
	{
		var response = $.parseJSON(jqxhr.responseText);
		lastFilename = response.filename;
		$('#uploadedImage').attr('src', response.songURL);
		$('progress').toggle();
		$('#imageForm')[0].reset();
		alert("Upload correcto");
	}).fail(function (jqXHR, textStatus) {
    	alert("KO");
		console.log(textStatus);
	});
});

function progressHandlingFunction(e){
if (e.lengthComputable) {
		$('progress').attr({value:e.loaded,max:e.total});

	}
}

/*
function createSong(newSong) {
	var url = API_BASE_URL + 'songs/';
	var data = form-data.(newSong);

	$("#create_result").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'form-data',
		contentType : 'multipart/form-data;',
		data : data,
	}).done(function(data, status, jqxhr) {
		console.log(data);
		$('<div class="alert alert-success"> <strong>Ok!</strong> Repository Created</div>').appendTo($("#create_result"));				
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Error </div>').appendTo($("#create_result"));
		console.log(newSong);
	});

}
*/
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
	
	$('#searchtab').text('');

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
	 window.location.replace("signin.html");				
			}).fail(function() {
				 alert('No se ha podido borrar');
	});

}

function editUser(useredit) {
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
