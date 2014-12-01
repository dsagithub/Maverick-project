var API_BASE_URL = "http://localhost:8080/Maverick-api/users";
$('input[type="checkbox"]').click(function(){
            if($(this).prop("checked") == true){
                
				
				role= {
				"rolename" : "artist"}
				

            }
            else if($(this).prop("checked") == false){
           
				
				role={
				"rolename" : "fan"}
					
				
            }
			
			crearUser(role);
        });
$("#signin").click(function(e){
	e.preventDefault();
	$("#result").text(' ');
		  
	var nuevoUser ;
	nuevoUser= {
			"username" : $("#username").val(),
			"userpass" : $("#password").val(),
			"name" : $("#name").val(),
			"email" : $("#email").val(),
			"description" : $("#description").val(),
			
			 
		
		}
		crearUser(nuevoUser);
		console.log(nuevoUser);

	
});

function crearUser(nuevoUser) {
	var url = API_BASE_URL;
	var data = JSON.stringify(nuevoUser);
	
console.log(data);
	$("#result").text('');

	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		contentType: 'application/vnd.maverick.api.user+json',
		dataType : 'json',
		data : data,
	}).done(function(data, status, jqxhr) {
		var info= data;
		
			window.location.replace("C:/Users/david/Desktop/dashboard.html");
  	}).fail(function() {
	
		 alert("Se ha producido un error"); 
	});

}
