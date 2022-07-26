package qtx.web.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import qtx.dominio.Articulo;
import qtx.persistencia.GestorDatos;

@Path("articulo")
public class ServicioRest {
	private static GestorDatos gestorDatos = new GestorDatos();
	
	public ServicioRest() {
		super();
	}
	public static long getTotArticulos() {
		return gestorDatos.getNarticulos();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	public String getArticuloXID(
						@QueryParam("clave") 
						@DefaultValue("ninguno") 
						String clave) {
		Articulo artI = gestorDatos.leerArticulo(clave);
		if(artI != null)
			return artI.toString();
		else
			return "No existe";
	}

	// URL esperada: /articulo?clave=idArticulo
	@GET
	@Produces( { MediaType.APPLICATION_JSON + "; charset=ISO-8859-1",
		         MediaType.APPLICATION_XML + "; charset=ISO-8859-1" })
	public Articulo getArticuloXID_JsonXml(
			@QueryParam("clave") 
			@DefaultValue("ninguno") 
			String clave) {
		return gestorDatos.leerArticulo(clave);
	}
	
	// URL esperada: /articulo/numArticulos
	@Path("numArticulos")
	@GET
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	public long getNumArticulos() {
		return gestorDatos.getNarticulos();
	}
	
	// URL esperada: /articulo/todos
	@Path("todos")
	@GET
//	@Produces(MediaType.APPLICATION_JSON + "; charset=ISO-8859-1")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public List<Articulo> getArticulos(){
		return gestorDatos.getArticulos();
	}
	
	// URL esperada: /articulo/idArticulo
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML + "; charset=ISO-8859-1")
	public Articulo getArticuloXID_URL_XML(
			@PathParam("id") 
			@DefaultValue("ninguno") 
			String clave) {
		return gestorDatos.leerArticulo(clave);
	}

	// URL esperada: /articulo/mensaje
	@Path("mensaje")
	@POST
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	@Consumes(MediaType.TEXT_PLAIN)
	public String ecoMensaje(String mensaje) {
		return "Mensaje recibido:" + mensaje;
	}
	
	// URL esperada: /articulo
	@POST
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String insertarArticulo(
							@DefaultValue("nulo")
							@FormParam("clave") 
							String clave, 
							@DefaultValue("")
							@FormParam("nombre") 
							String nombre, 
							@DefaultValue("")
							@FormParam("descripcion") 
							String descripcion, 
							@DefaultValue("0.0")
							@FormParam("costo") 
							BigDecimal costo) {
		System.out.println("insertarArticulo");
		if(clave.equals("nulo") || clave.trim().isEmpty())
			return "Clave no especificada";
		if(costo.floatValue() == 0f)
			return "Costo no especificado o igual a Cero";
		
		Articulo artI = new Articulo(clave, nombre, descripcion, costo);
		if(ServicioRest.gestorDatos.insertarArticulo(artI) == true)
		   return artI.toString();
		else
		   return "Articulo duplicado";
	}
	
	// URL esperada: /articulo/categorias
	@Path("categorias")
	@POST
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String insertarCategorias(
							@DefaultValue("nulo")
							@FormParam("categoria") 
							List<String> categorias) {
		System.out.println("insertarCategorias");
		if(categorias.size() == 1 && categorias.get(0).equals("nulo"))
			return "Clave(s) no especificada";
		int nCategorias = ServicioRest.gestorDatos.agregarCategorias(categorias);
		return Integer.toString(nCategorias);
	}
	
	// URL esperada: /articulo/categorias
	@Path("categorias")
	@POST
	@Produces(MediaType.TEXT_PLAIN + "; charset=ISO-8859-1")
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertarCategorias_json(List<String> categorias) {
//		System.out.println("insertarCategorias_json");
		if(categorias.size() == 0 )
			return "Clave(s) no especificada";
		int nCategorias = ServicioRest.gestorDatos.agregarCategorias(categorias);
		return Integer.toString(nCategorias);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public Articulo eliminar(
			@QueryParam("clave") 
			@DefaultValue("ninguno") 
			String clave) {
		return ServicioRest.gestorDatos.eliminarArticulo(clave);
	}
	public static void main(String[] args) {
		
		ServicioRest sr = new ServicioRest();
		System.out.println(sr.getArticuloXID("C-200P"));
	}

	
}
