
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
	var hidden = $("#"+id).prop('hidden');
	var setter = !hidden;
	$("#"+id).prop('hidden', setter);
	
}

function switchHiddenParticipation(ceremId, partId, fait = false){
	if( !fait ){
		switchHidden('qte'+partId+ceremId);
		switchHidden('inqte'+partId+ceremId);
		switchHidden('prix'+partId+ceremId);
		switchHidden('divinprix'+partId+ceremId);
	}
}
function switchHiddenParticipationPen(ceremId, partId){
	switchHidden('divpen'+partId+ceremId);
	switchHidden('indivpen'+partId+ceremId);
}

function setVisible( id ){
	var element = document.getElementById(id);
    element.hidden = false; 
}

$(document).click(
	function() {
		if( $("#grandmereJeudi").is(":checked") ||
			$("#grandmereVendredi").is(":checked") || 
			$("#princesse").is(":checked") ||
			$("#ninos").is(":checked") ){
			
			$("#questionnaire").hidden = false;
			$("#questNav").hidden = false;
		}else{
			$("#questionnaire").hidden = true;
			$("#questNav").hidden = true;
		}
});

	function questionnaireVisibility() {
		if( $("#grandmereJeudi").is(":checked") ||
			$("#grandmereVendredi").is(":checked") || 
			$("#princesse").is(":checked") ||
			$("#ninos").is(":checked") ){
			
			$("#questionnaire").prop('hidden', false);
			$("#questNav").prop('hidden', false);
			
//			$("#intention").prop('disabled', true);
//			$("#sante").prop('disabled', true);
//			$("#experience").prop('disabled', true);
//			$("#defi").prop('disabled', true);
//			$("#prenoms").prop('disabled', true);
//			$("#naissance").prop('disabled', true);
						
		}else{
			$("#questionnaire").prop('hidden', true);
			$("#questNav").prop('hidden', true);
			
//			$("#intention").prop('disabled', false);
//			$("#sante").prop('disabled', false);
//			$("#experience").prop('disabled', false);
//			$("#defi").prop('disabled', false);
//			$("#prenoms").prop('disabled', false);
//			$("#naissance").prop('disabled', false);
		}
	}

		
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
		
function saveParticipation( partId, ceremId){
	var element = document.getElementById("inqte"+partId+ceremId);
	var qte = element.value;
	element = document.getElementById("inprix"+partId+ceremId);
	var prix = element.value;
	
	sendSaveParticipation(partId, ceremId, qte, prix);
	
	switchHiddenParticipation(ceremId, partId);
}

function saveParticipationPen( partId, ceremId ){
	var idQte = "#inqte"+partId+ceremId;
	var idPrix = "#inprix"+partId+ceremId;
	
	var qte = $(idQte).val();
	var prix = $(idPrix).val();
	
	sendSaveParticipation(partId, ceremId, qte, prix);
	
	switchHidden('indivpen'+partId+ceremId);
	switchHidden('textpen'+partId+ceremId);
	
}

function sendSaveParticipation( partId, ceremId, qte, prix ){
	$.ajax({
		type : "GET",
		url : "api/saveParticipation",
		data : {
			"ceremId" : ceremId,
			"partId" : partId,
			"qte" : qte,
			"prix" : prix
		},
		success : function(result) {
			if (result == true) {
				$("#txtqte"+partId+ceremId).text(qte);
				$("#txtprix"+partId+ceremId).text(prix);
			} 
		},
		error : function(e) {
			$("#getResultDiv").html("<strong>Error</strong>");
			console.log("ERROR: ", e);
		}
	});
}


function validateParticipation( partId, ceremId ){
	$.ajax({
		type : "GET",
		url : "api/validateParticipation",
		data : {
			"ceremId" : ceremId,
			"partId" : partId
		},
		success : function(result) {
			if (result == true) {
				var classList = $("#qte"+partId+ceremId).attr("class");
				if( classList == null ){
					$("#qte"+partId+ceremId).addClass("bg-success");
				
				}else{
			        var classArr = classList.split(/\s+/);
					if( classArr.includes("bg-success") ){
						$("#qte"+partId+ceremId).removeClass("bg-success");
						$("#prix"+partId+ceremId).removeClass("bg-success");
					}else{
						$("#qte"+partId+ceremId).addClass("bg-success");
						$("#prix"+partId+ceremId).addClass("bg-success");
					}
				}
			} 
		},
		error : function(e) {
			$("#getResultDiv").html("<strong>Error</strong>");
			console.log("ERROR: ", e);
		}
	});
}
