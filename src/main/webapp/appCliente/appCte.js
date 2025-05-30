/**
 * 
 */
	var spanNarticulos;
	var spanStatusAltaArt;
	var formArticulo;
	var tbodyArticulos;
	var inputCveArt;
	var inputCveArtDelete;
	var inputArtJson;
	var inputArtXml;
	
	function actualizaNarticulos(txtNarticulos){
		spanNarticulos.innerHTML = txtNarticulos;
	}
	
	function actualizaListaArticulos(articulosJSON){
		var arrHtmlArticulos = articulosJSON.map( function(x){
			    return "<tr>"
						+"<td>"	+ x.clave + "</td>"
						+"<td>"	+ x.nombre + "</td>"
						+"<td>"	+ x.descripcion + "</td>"
						+"<td>"	+ x.costo + "</td>"
						+"</tr>";} );
		var textoHtmlTbody = "";
		for(trI of arrHtmlArticulos){
			textoHtmlTbody += trI;
		}
		tbodyArticulos.innerHTML = textoHtmlTbody;
	}

	function actualizaStatusAltaArticulo(txtRespAlta){
		spanStatusAltaArt.innerHTML = txtRespAlta;
	}
	
	function getNArticulos(){
		  var xhttp=new XMLHttpRequest();
		  xhttp.open("GET","../webapi/articulo/numArticulos");
		  xhttp.send();
		  xhttp.onreadystatechange=function(){
		        if(xhttp.readyState == 4 && xhttp.status == 200){
		           actualizaNarticulos(xhttp.responseText);
		        }
		  }			
	}
	
	function getArticulos(){
		  var xhttp=new XMLHttpRequest();
		  xhttp.open("GET","../webapi/articulo/todos");
		  xhttp.responseType = "json";
		  xhttp.send();
		  xhttp.onreadystatechange=function(){
		        if(xhttp.readyState == 4 && xhttp.status == 200){
		        	actualizaListaArticulos(xhttp.response);
		        }
		  }			
	}
	
	function getArticuloI(){
		  var xhttp=new XMLHttpRequest();
		  xhttp.open("GET","../webapi/articulo?clave=" + inputCveArt.value);
		  xhttp.setRequestHeader("Accept","application/json");
		  xhttp.send();
		  xhttp.onreadystatechange=function(){
		        if(xhttp.readyState == 4) // Terminada
		        	if( xhttp.status == 200) // Status OK
		        		inputArtJson.value = xhttp.responseText;
		        	else
		        	if( xhttp.status == 204) // No content
		        		inputArtJson.value = "Respuesta sin contenido"
		  }			
	}
	
	function sendArticulo(){
		  var formDatos = new URLSearchParams();
		  formDatos.set("clave", formArticulo["clave"].value);
		  formDatos.set("nombre", formArticulo["nombre"].value);
		  formDatos.set("descripcion", formArticulo["descripcion"].value);
		  formDatos.set("costo", formArticulo["costo"].value);
		  
		  var xhttp=new XMLHttpRequest();
		  xhttp.open("POST", "../webapi/articulo");
		  xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		  xhttp.send(formDatos);
		  xhttp.onreadystatechange=function(){
		        if(xhttp.readyState == 4 && xhttp.status == 200){
		           actualizaStatusAltaArticulo(xhttp.responseText);
		           getNArticulos();
		           getArticulos();
		        }
		  }			
	}
	
	function eliminarArticuloI(){
		  var xhttp=new XMLHttpRequest();
		  xhttp.open("DELETE","../webapi/articulo?clave=" + inputCveArtDelete.value);
		  xhttp.setRequestHeader("Accept","application/xml");
		  xhttp.send();
		  xhttp.onreadystatechange=function(){
		        if(xhttp.readyState == 4)
		        	if(xhttp.status == 200){
		        	   inputArtXml.value = xhttp.responseText;
			           getNArticulos();
			           getArticulos();
		           }
		        	else
		        	if(xhttp.status == 204){ // Sin respuestas
		        	   inputArtXml.value = "Respuesta sin contenido";
		        	}
		  }			
	}

	window.onload = function() {
		spanNarticulos = document.getElementById("nArticulos");
		spanStatusAltaArt = document.getElementById("statusAltaArticulo");
		formArticulo = document.getElementById("idFormArticulo");
		tbodyArticulos = document.getElementById("idListaArticulos");
		inputCveArt = document.getElementById("idCveArticulo");
		inputArtJson = document.getElementById("idArtJson");
		inputCveArtDelete = document.getElementById("idCveArtDelete");
		inputArtXml = document.getElementById("idArtXml");
		
		getNArticulos();
		getArticulos();
	}
