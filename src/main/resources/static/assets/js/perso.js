
function includeHTML() {
  var z, i, elmnt, file, xhttp;
  /*loop through a collection of all HTML elements:*/
  z = document.getElementsByTagName("*");
  for (i = 0; i < z.length; i++) {
    elmnt = z[i];
    /*search for elements with a certain atrribute:*/
    file = elmnt.getAttribute("w3-include-html");
    if (file) {
      /*make an HTTP request using the attribute value as the file name:*/
      xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function() {
        if (this.readyState == 4) {
          if (this.status == 200) {elmnt.innerHTML = this.responseText;}
          if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
          /*remove the attribute, and call this function once more:*/
          elmnt.removeAttribute("w3-include-html");
          includeHTML();
        }
      }      
      xhttp.open("GET", file, true);
      xhttp.send();
      /*exit the function:*/
      return;
    }
  }
};


document.getElementById("addNote").addEventListener(
  "click",
  () => {
    var element = document.getElementById("addNoteForm");
    element.hidden = !element.hidden; 
  },
  false
);


function switchHidden( id ){
	var element = document.getElementById(id);
    element.hidden = !element.hidden; 
}

//$(document).ready(
//	function() {

//		// GET REQUEST
//		$("#switchSouhait").click(function(event) {
//			event.preventDefault();
//			ajaxGet();
//		});

		// DO GET
		function switchSouhait( ceremId, partId) {
			$.ajax({
				type : "GET",
				url : "api/switchSouhait",
				data : {
					"ceremId" : ceremId,
					"partId" : partId
				},
				success : function(result) {
					if (result == true) {
						// Get class list string
				        var classList = $("#check"+ceremId+partId).attr("class");
				        
				        // Creating class array by splitting class list string
				        var classArr = classList.split(/\s+/);
				        
				        if( classArr.includes("bi-square") ){
							$("#check"+ceremId+partId).removeClass("bi-square");
							$("#check"+ceremId+partId).addClass("bi-check-square-fill");
						}else{
							$("#check"+ceremId+partId).removeClass("bi-check-square-fill");
							$("#check"+ceremId+partId).addClass("bi-square");
						}
					} 
				},
				error : function(e) {
					$("#getResultDiv").html("<strong>Error</strong>");
					console.log("ERROR: ", e);
				}
			});
		}
	//})