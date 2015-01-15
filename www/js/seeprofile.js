var API_BASE_URL = "http://localhost:8080/Maverick-api/";
var verusername = getCookie("verusername");
//console.log(username);	
var search = getCookie("search");

$(document).ready(function() {
	$('<strong> </strong>' + verusername + '<br>').appendTo($('#usernameprofile'));
Userdata(verusername);
});
$("#searchartist").click(function(e) {
	e.preventDefault();
  var elementobusqueda = $("#artisttosearch").val();
  $.cookie('elementobusqueda', elementobusqueda);
    $.cookie('elementobusqueda');
  
 
	window.location.replace("search.html");
});




$("#followingtab").click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
		  
	getArtist($('#artisttosearch').val());
        
	
});


function getArtist(artisttosearch) {
var url = API_BASE_URL + 'search?username=' + artisttosearch;

console.log(url);
	
	$("#searchtab").text('');

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



function Userdata(verusername) {
var url = API_BASE_URL + 'users/' + verusername;

console.log(url);
	
	$("#searchtab").text('');

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
$('#Indie').click(function (e){
	style = $('#Indie').val();
});
$('#Rock').click(function (e){
	style = $('#Rock').val();
});
$('#Pop').click(function (e){
	style = $('#Pop').val();
});
$('#Pachangeo').click(function (e){
	style = $('#Pachangeo').val();
});
$('#EDM').click(function (e){
	style = $('#EDM').val();
});
$('#Otros').click(function (e){
	style = $('#Otros').val();
});
$('#Top List').click(function (e){
	style = $('#Top List').val();
});

function updateSong(){
	//console.log(username, userprofilepgjs.username);
	if(username != userprofilepgjs.username){
	$('#songstab').hide();
	}
	else{
		$('#songstab').show();
	}
	
	 
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