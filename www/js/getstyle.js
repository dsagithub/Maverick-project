var API_BASE_URL = "http://localhost:8080/Maverick-api/";
var username;


//boton GET TODOS
//$("#listsong").click(function(e){
	//e.preventDefault();
		//getTodos($('#style_name').val());
	
//});
$("#songstab").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos();
	
});

function getTodos(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'rock';
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
					 
					$.each(songs.songs, function(i, v) {
					 var todo = v;
					 html += '<tr>'
					 html += '<td>'+todo.song_name+'</td>'
					 html += '<td>'+todo.username+'</td>'
				     html += '<td>'+todo.album_name+'</td>'
					 html += '<td>'+todo.last_modified+'</td>'
					 html += '<td>'+todo.style+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
				     html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComment('+ todo.song_name +').dialog("open")>Ver</button>' +  '</td>'
					// html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComent('+ v.song_name +')>Ver' + '<button type="button" class="btn btn-danger" onclick=crearComent('+ v.song_name +')>Crear' + '<button type="button" class="btn btn-danger" onclick=DeleteComent('+ v.song_name +')>Borrar' + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + todo.songid +'" src="'+ todo.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + todo.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 

					console.log(data);
	
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
$("#songstab2").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos2();
	
});

function getTodos2(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'edm';
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
					 
					$.each(songs.songs, function(i, v) {
					 var todo = v;
					 html += '<tr>'
					 html += '<td>'+todo.song_name+'</td>'
					 html += '<td>'+todo.username+'</td>'
				     html += '<td>'+todo.album_name+'</td>'
					 html += '<td>'+todo.last_modified+'</td>'
					 html += '<td>'+todo.style+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
				     html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComment('+ todo.song_name +').dialog("open")>Ver</button>' +  '</td>'
					// html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComent('+ v.song_name +')>Ver' + '<button type="button" class="btn btn-danger" onclick=crearComent('+ v.song_name +')>Crear' + '<button type="button" class="btn btn-danger" onclick=DeleteComent('+ v.song_name +')>Borrar' + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + todo.songid +'" src="'+ todo.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + todo.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 

					console.log(data);
	
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
$("#songstab3").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos3();
	
});

function getTodos3(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'Indie';
	
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
					 
					$.each(songs.songs, function(i, v) {
					 var todo = v;
					 html += '<tr>'
					 html += '<td>'+todo.song_name+'</td>'
					 html += '<td>'+todo.username+'</td>'
				     html += '<td>'+todo.album_name+'</td>'
					 html += '<td>'+todo.last_modified+'</td>'
					 html += '<td>'+todo.style+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
				     html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComment('+ todo.song_name +').dialog("open")>Ver</button>' +  '</td>'
					// html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComent('+ v.song_name +')>Ver' + '<button type="button" class="btn btn-danger" onclick=crearComent('+ v.song_name +')>Crear' + '<button type="button" class="btn btn-danger" onclick=DeleteComent('+ v.song_name +')>Borrar' + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + todo.songid +'" src="'+ todo.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + todo.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 

					console.log(data);
	
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
$("#songstab4").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos4();
	
});

function getTodos4(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'Pop';
	
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
					 
					$.each(songs.songs, function(i, v) {
					 var todo = v;
					 html += '<tr>'
					 html += '<td>'+todo.song_name+'</td>'
					 html += '<td>'+todo.username+'</td>'
				     html += '<td>'+todo.album_name+'</td>'
					 html += '<td>'+todo.last_modified+'</td>'
					 html += '<td>'+todo.style+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
				     html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComment('+ todo.song_name +').dialog("open")>Ver</button>' +  '</td>'
					// html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComent('+ v.song_name +')>Ver' + '<button type="button" class="btn btn-danger" onclick=crearComent('+ v.song_name +')>Crear' + '<button type="button" class="btn btn-danger" onclick=DeleteComent('+ v.song_name +')>Borrar' + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + todo.songid +'" src="'+ todo.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + todo.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 

					console.log(data);
	
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
$("#songstab5").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos5();
	
});

function getTodos5(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'Pachangeo';
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
					 
					$.each(songs.songs, function(i, v) {
					 var todo = v;
					 html += '<tr>'
					 html += '<td>'+todo.song_name+'</td>'
					 html += '<td>'+todo.username+'</td>'
				     html += '<td>'+todo.album_name+'</td>'
					 html += '<td>'+todo.last_modified+'</td>'
					 html += '<td>'+todo.style+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
				     html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComment('+ todo.song_name +').dialog("open")>Ver</button>' +  '</td>'
					// html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComent('+ v.song_name +')>Ver' + '<button type="button" class="btn btn-danger" onclick=crearComent('+ v.song_name +')>Crear' + '<button type="button" class="btn btn-danger" onclick=DeleteComent('+ v.song_name +')>Borrar' + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + todo.songid +'" src="'+ todo.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + todo.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 

					console.log(data);
	
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
$("#songstab6").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos6();
	
});

function getTodos6(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'Otros';
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
					 
					$.each(songs.songs, function(i, v) {
					 var todo = v;
					 html += '<tr>'
					 html += '<td>'+todo.song_name+'</td>'
					 html += '<td>'+todo.username+'</td>'
				     html += '<td>'+todo.album_name+'</td>'
					 html += '<td>'+todo.last_modified+'</td>'
					 html += '<td>'+todo.style+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
				     html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComment('+ todo.song_name +').dialog("open")>Ver</button>' +  '</td>'
					// html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComent('+ v.song_name +')>Ver' + '<button type="button" class="btn btn-danger" onclick=crearComent('+ v.song_name +')>Crear' + '<button type="button" class="btn btn-danger" onclick=DeleteComent('+ v.song_name +')>Borrar' + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + todo.songid +'" src="'+ todo.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + todo.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 

					console.log(data);
	
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
$("#songstab7").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos7();
	
});

function getTodos7(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/likes'
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
					 
					$.each(songs.songs, function(i, v) {
					 var todo = v;
					 html += '<tr>'
					 html += '<td>'+todo.song_name+'</td>'
					 html += '<td>'+todo.username+'</td>'
				     html += '<td>'+todo.album_name+'</td>'
					 html += '<td>'+todo.last_modified+'</td>'
					 html += '<td>'+todo.style+'</td>'
				     html += '<td>'+ v.likes + '<button type="button" id=like class="btn btn-success">Like</button>'+'</td>'
				     html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComment('+ todo.song_name +').dialog("open")>Ver</button>' +  '</td>'
					// html +='<td>'+ '<button type="button" class="btn btn-danger" onclick=getComent('+ v.song_name +')>Ver' + '<button type="button" class="btn btn-danger" onclick=crearComent('+ v.song_name +')>Crear' + '<button type="button" class="btn btn-danger" onclick=DeleteComent('+ v.song_name +')>Borrar' + '</td>'
					 html += '<td>'+ '<div class="col-sd-4"><audio id ="song' + todo.songid +'" src="'+ todo.songURL+'" type="audio/mp3" style=background-color:#CEF6EC" controls><div><button  onclick="document.getElementById(\'song ' + todo.songid +'\').play()">Reproducir</button> style=background-color:#CEF6EC </div></audio></div>'+'</td>' 

					console.log(data);
	
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









