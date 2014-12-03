var API_BASE_URL = "http://localhost:8080/Maverick-api";
var username = getCookie("username");
var userpass = getCookie("userpass");	
console.log(username);
console.log(userpass);

$("#profile").click(function(e) {
	e.preventDefault();
	window.location.replace("C:/Users/david/Desktop/userprofile.html");
});
function getCookie(name){
  var pattern = RegExp(name + "=.[^;]*");
  matched = document.cookie.match(pattern);
  if(matched){
      var cookie = matched[0].split('=');
      return cookie[1];
  }
  return false;
}