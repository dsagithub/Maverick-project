var API_BASE_URL = "http://147.83.7.156:8080/grupo3.dsa.eetac/Maverick-api";
var username = getCookie('username');
console.log(username);	

$(document).ready(function() {
  
followsongs(username);
});



$("#profile").click(function(e) {
	e.preventDefault();
	window.location.replace("userprofile.html");
});
$("#ranking").click(function(e) {
  e.preventDefault();
  window.location.replace("ranking.html");
});
/*
$("#searchsong").click(function(e) {
	e.preventDefault();
  var elementobusqueda2 = $("#songtosearch").val();
  $.cookie('elementobusqueda2', elementobusqueda2);
    $.cookie('elementobusqueda2');
	window.location.replace("search.html");
});
*/
$("#searchartist").click(function(e) {
	e.preventDefault();
  var elementobusqueda = $("#artisttosearch").val();
  $.cookie('elementobusqueda', elementobusqueda);
    $.cookie('elementobusqueda');
	window.location.replace("search.html");
});

function followsongs(username) {
var url = 'http://localhost:8080/Maverick-api/songs/' + username + '/devuelve';

//console.log(url);
  
  $('#searchtab').text('');

	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				//console.log(songs);
				var html='';
			  // si la consulta ajax devuelve datos
			 
				//	if(data.length > 0){
				var listausers = data;
      
		$.each(listausers.songs, function(i,v){
          var song = v;
          console.log(song);
					 html += '<tr>'
					//var song = v;
					//$('<h4> Datos canción: </h4> ').appendTo($('#myResults'));
					//$('<p>').appendTo($('#myResults'));
				//	var button = $("tableDeposits").append('<button type="button" align="center" class="btn btn-success" href="#coments"  id="button">Coments</button>');
					 html += '<td>'+ song.song_name+'</td>'
					 html += '<td>'+ song.username+'</td>'
				     html += '<td>'+ song.style+'</td>'
					 html += '<td>'+ song.description+'</td>'
				     html += '<td>'+ song.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
					// html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComent('+ v.song_name +')>Ver' + '<button type="button" class="btn btn-danger" onclick=crearComent('+ v.song_name +')>Crear' + '<button type="button" class="btn btn-danger" onclick=DeleteComent('+ v.song_name +')>Borrar' + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' +  song.songid +'" src="'+ song.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + song.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 

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
	
function getCookie(name){
  var pattern = RegExp(name + "=.[^;]*");
  matched = document.cookie.match(pattern);
  if(matched){
      var cookie = matched[0].split('=');
      return cookie[1];
  }
  return false;
}



