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
	$("#myResults").text('');
	console.log(url);
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var songs = data;
				//console.log(todos)
				$('<h4> Lista Rock </h4>').appendTo($('#myResults'));
				$.each(songs.songs, function(i,v){
					var todo = v;
					console.log(data);
					//console.log(todo.id);
					
					$('<p>').appendTo($('#myResults'));
					$('<strong> songid: </strong>' + todo.songid + '<br>').appendTo($('#myResults'));
					$('<strong> username: </strong>' + todo.username + '<br>').appendTo($('#myResults'));
					$('<strong> song_name: </strong>' + todo.song_name + '<br>').appendTo($('#myResults'));
					$('<strong> album_name: </strong>' + todo.album_name + '<br>').appendTo($('#myResults'));
					$('<strong> description: </strong>' + todo.description + '<br>').appendTo($('#myResults'));
					$('<strong> style: </strong>' + todo.style + '<br>').appendTo($('#myResults'));
					$('</p>').appendTo($('#myResults'));
					
				
	}).fail(function(){
		$("#myResults").text('No hay Todos');
	});
});
}
$("#songstab2").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos2();
	
});

function getTodos2(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'edm';
	$("#myResults").text('');
	console.log(url);
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var songs = data;
				//console.log(todos)
				$('<h4> Lista EDM </h4>').appendTo($('#myResults'));
				$.each(songs.songs, function(i,v){
					var todo = v;
					console.log(data);
					//console.log(todo.id);
					
					$('<p>').appendTo($('#myResults'));
					$('<strong> songid: </strong>' + todo.songid + '<br>').appendTo($('#myResults'));
					$('<strong> username: </strong>' + todo.username + '<br>').appendTo($('#myResults'));
					$('<strong> song_name: </strong>' + todo.song_name + '<br>').appendTo($('#myResults'));
					$('<strong> album_name: </strong>' + todo.album_name + '<br>').appendTo($('#myResults'));
					$('<strong> description: </strong>' + todo.description + '<br>').appendTo($('#myResults'));
					$('<strong> style: </strong>' + todo.style + '<br>').appendTo($('#myResults'));
					$('</p>').appendTo($('#myResults'));
					
				
	}).fail(function(){
		$("#myResults").text('No hay Todos');
	});
});
}
$("#songstab3").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos3();
	
});

function getTodos3(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'Indie';
	$("#myResults").text('');
	console.log(url);
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var songs = data;
				//console.log(todos)
				$.each(songs.songs, function(i,v){
					var todo = v;
					console.log(data);
					//console.log(todo.id);
					$('<h4> Datos Todo </h4>').appendTo($('#myResults'));
					$('<p>').appendTo($('#myResults'));
					$('<strong> songid: </strong>' + todo.songid + '<br>').appendTo($('#myResults'));
					$('<strong> username: </strong>' + todo.username + '<br>').appendTo($('#myResults'));
					$('<strong> song_name: </strong>' + todo.song_name + '<br>').appendTo($('#myResults'));
					$('<strong> album_name: </strong>' + todo.album_name + '<br>').appendTo($('#myResults'));
					$('<strong> description: </strong>' + todo.description + '<br>').appendTo($('#myResults'));
					$('<strong> style: </strong>' + todo.style + '<br>').appendTo($('#myResults'));
					$('</p>').appendTo($('#myResults'));
					
				
	}).fail(function(){
		$("#myResults").text('No hay Todos');
	});
});
}
$("#songstab4").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos4();
	
});

function getTodos4(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'Pop';
	$("#myResults").text('');
	console.log(url);
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var songs = data;
				//console.log(todos)
				$.each(songs.songs, function(i,v){
					var todo = v;
					console.log(data);
					//console.log(todo.id);
					$('<h4> Datos Todo </h4>').appendTo($('#myResults'));
					$('<p>').appendTo($('#myResults'));
					$('<strong> songid: </strong>' + todo.songid + '<br>').appendTo($('#myResults'));
					$('<strong> username: </strong>' + todo.username + '<br>').appendTo($('#myResults'));
					$('<strong> song_name: </strong>' + todo.song_name + '<br>').appendTo($('#myResults'));
					$('<strong> album_name: </strong>' + todo.album_name + '<br>').appendTo($('#myResults'));
					$('<strong> description: </strong>' + todo.description + '<br>').appendTo($('#myResults'));
					$('<strong> style: </strong>' + todo.style + '<br>').appendTo($('#myResults'));
					$('</p>').appendTo($('#myResults'));
					
				
	}).fail(function(){
		$("#myResults").text('No hay Todos');
	});
});
}
$("#songstab5").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos5();
	
});

function getTodos5(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'Pachangeo';
	$("#myResults").text('');
	console.log(url);
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var songs = data;
				//console.log(todos)
				$.each(songs.songs, function(i,v){
					var todo = v;
					console.log(data);
					//console.log(todo.id);
					$('<h4> Datos Todo </h4>').appendTo($('#myResults'));
					$('<p>').appendTo($('#myResults'));
					$('<strong> songid: </strong>' + todo.songid + '<br>').appendTo($('#myResults'));
					$('<strong> username: </strong>' + todo.username + '<br>').appendTo($('#myResults'));
					$('<strong> song_name: </strong>' + todo.song_name + '<br>').appendTo($('#myResults'));
					$('<strong> album_name: </strong>' + todo.album_name + '<br>').appendTo($('#myResults'));
					$('<strong> description: </strong>' + todo.description + '<br>').appendTo($('#myResults'));
					$('<strong> style: </strong>' + todo.style + '<br>').appendTo($('#myResults'));
					$('</p>').appendTo($('#myResults'));
					
				
	}).fail(function(){
		$("#myResults").text('No hay Todos');
	});
});
}

$("#songstab6").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos6();
	
});

function getTodos6(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/style?style=' + 'Otros';
	$("#myResults").text('');
	console.log(url);
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var songs = data;
				//console.log(todos)
				$.each(songs.songs, function(i,v){
					var todo = v;
					console.log(data);
					//console.log(todo.id);
					$('<h4> Datos Todo </h4>').appendTo($('#myResults'));
					$('<p>').appendTo($('#myResults'));
					$('<strong> songid: </strong>' + todo.songid + '<br>').appendTo($('#myResults'));
					$('<strong> username: </strong>' + todo.username + '<br>').appendTo($('#myResults'));
					$('<strong> song_name: </strong>' + todo.song_name + '<br>').appendTo($('#myResults'));
					$('<strong> album_name: </strong>' + todo.album_name + '<br>').appendTo($('#myResults'));
					$('<strong> description: </strong>' + todo.description + '<br>').appendTo($('#myResults'));
					$('<strong> style: </strong>' + todo.style + '<br>').appendTo($('#myResults'));
					$('</p>').appendTo($('#myResults'));
					
				
	}).fail(function(){
		$("#myResults").text('No hay Todos');
	});
});
}

$("#songstab7").click(function(e){
	e.preventDefault();
		//getTodos($('#style_name').val());
		getTodos7();
	
});

function getTodos7(){
	//var url = API_BASE_URL +'songs/style?style=' + style_name;
	var url = API_BASE_URL +'songs/likes'
	$("#myResults").text('');
	console.log(url);
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr){
				var songs = data;
				//console.log(todos)
				$('<h4> Top Likes</h4>').appendTo($('#myResults'));
				$.each(songs.songs, function(i,v){
					var todo = v;
					console.log(data);
					//console.log(todo.id);
					
					$('<p>').appendTo($('#myResults'));
					//$('<strong> songid: </strong>' + todo.songid + '<br>').appendTo($('#myResults'));
					$('<strong> likes: </strong>' + todo.likes + '<br>').appendTo($('#myResults'));
					$('<strong> username: </strong>' + todo.username + '<br>').appendTo($('#myResults'));
					$('<strong> song_name: </strong>' + todo.song_name + '<br>').appendTo($('#myResults'));
					//$('<strong> album_name: </strong>' + todo.album_name + '<br>').appendTo($('#myResults'));
					$('<strong> description: </strong>' + todo.description + '<br>').appendTo($('#myResults'));
					$('<strong> style: </strong>' + todo.style + '<br>').appendTo($('#myResults'));
					$('</p>').appendTo($('#myResults'));
					
				
	}).fail(function(){
		$("#myResults").text('No hay Todos');
	});
});
}











