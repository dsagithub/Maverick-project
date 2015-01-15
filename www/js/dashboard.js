var API_BASE_URL = "http://localhost:8080/Maverick-api";
var username = getCookie("username");
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
$("#searchartist").click(function(e) {
	e.preventDefault();
  var elementobusqueda = $("#artisttosearch").val();
  $.cookie('elementobusqueda', elementobusqueda);
    $.cookie('elementobusqueda');
  
 
	window.location.replace("search.html");
});




function followsongs(username) {
var url = API_BASE_URL + '/songs/' + username;

//console.log(url);
  
  $("#searchtab").text('');

  $.ajax({
    url : url,
    type : 'GET',
    crossDomain : true,
    dataType : 'json',
  }).done(function(data, status, jqxhr) {

  var listausers = data;
      
        
        $.each(listausers.songs, function(i,v){
          var song = v;
          console.log(song);
            
          
          
        
          $('<strong> Song: </strong>' + song.song_name + '<br>').appendTo($('#myResults'));
            $('<strong> Artist: </strong>' + song.username + '<br>').appendTo($('#myResults'));
          
          
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