var API_BASE_URL = "http://localhost:8080/Maverick-api/";
var verusername = getCookie("verusername");
//console.log(username);	
var search = getCookie("search");
var username = getCookie("username");
console.log(username);
console.log(verusername);

$(document).ready(function() {
	
	
	$('<strong> </strong>' + verusername + '<br>').appendTo($('#usernameprofile'));
Userdata(verusername);
Userfollow();
});
$("#searchartist").click(function(e) {
	e.preventDefault();
  var elementobusqueda = $("#artisttosearch").val();
  $.cookie('elementobusqueda', elementobusqueda);
    $.cookie('elementobusqueda');
  
 
	window.location.replace("file:///C:/Users/david/Desktop/search.html");
});


$("#follow").click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
		  
var nombres;
		  nombres= {
			"username" : username
			
			 
		
		}
        getfollow(nombres);
	
});

$("#unfollow").click(function(e){
	
        e.preventDefault();
	$("#result").text(' ');
		  

		
        getunfollow();
	
});


$("#followingtab").click(function(e){
	
	e.preventDefault();
	$("#result").text(' ');
		  
	getFollowing(username);
        
	
});

function getFollowing(username) {
var url = API_BASE_URL + 'users/'+ username + '/following';

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
						
					
					
					
					$('<strong> Username: </strong>' + user.username + '<br>').appendTo($('#myResults'));
				    
					
					
					$('</p>').appendTo($('#myResults'));
				
				});
				
				
				

				
				
			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> No se pueden cargar los datos del usuario </div>').appendTo($("#myResults"));
	});

}

function Userfollow(){
	var url= API_BASE_URL+'users/'+ username+ '/following/' + verusername;
	
	console.log(url);
	
	$.ajax({
		url:url,
		type:'GET',
		crossDomain: true,
		dataType:'json',
		
	}).done(function(data, status, jqxhr) {
var listausers = data;
console.log(listausers);

//if (listausers[0] == undefined){
	
		$('#follow').show();
									$('#unfollow').hide();

//}
//else{
$.each(listausers.users, function(i,v){
//console.log(listausers.users);
//console.log(v);
console.log("hola");
					var user = v;
					console.log( user.username);
						console.log(user);
									if (user.username == verusername){
									$('#follow').hide();
									$('#unfollow').show();
								}
								else {
										$('#follow').show();
									$('#unfollow').hide();
								}
							 
					
					
				
				});

				
//}

		 

	}).fail(function() {
		 alert("No se puede seguir");  
	});
}



function getfollow(nombres){
	var url= API_BASE_URL+'users/'+ verusername+ '/following';
	console.log(nombres);
	var data = JSON.stringify(nombres);
	
	console.log(url);
	console.log(data);
	$.ajax({
		url:url,
		type:'POST',
		crossDomain: true,
		dataType:'json',
		contentType: 'application/vnd.maverick.api.user.collection+json',
		data: data,
	}).done(function(data, status, jqxhr) {
				console.log(data);
$('#follow').hide();
	 $('#unfollow').show();

		 

	}).fail(function() {
		 alert("No se puede seguir");  
	});
}


function getunfollow(){
	var url= API_BASE_URL+'users/'+ verusername+ '/following/' + username;
	
	
	console.log(url);
	
	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
		console.log("hola");
				
$('#unfollow').hide();
	 $('#follow').show();

		 

	}).fail(function() {
		 alert("No se puede dejar de seguir");  
	});
}



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



function getCookie(name){
  var pattern = RegExp(name + "=.[^;]*");
  matched = document.cookie.match(pattern);
  if(matched){
      var cookie = matched[0].split('=');
      return cookie[1];
  }
  return false;
}