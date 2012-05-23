package org.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/JugMilano/People/DomenicoBriganti")
public class Presentation {

	@Context
	Request request;

	private UserDetails userdet;

	public Presentation() {
		userdet = new UserDetails();
		userdet.setVersion(1);
		userdet.setCompany("Eidon srl");
		userdet.setEmail("dometec@gmail.com");
		userdet.setLinkedin("http://www.linkedin.com/in/dometec");
		userdet.setBlog("http://tipsaboutmywork.blogspot.com/");
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUserDetails() {

		EntityTag entityTag = new EntityTag(userdet.getVersion().toString());

		ResponseBuilder evaluatePreconditions = request.evaluatePreconditions(entityTag);
		if (evaluatePreconditions != null)
			return evaluatePreconditions.build();

		return Response.ok(userdet).tag(entityTag).build();
	}

	@GET
	@Produces("application/pdf")
	public Response getUserDetailsPdf() throws IOException {
		File pdf = new File("src/main/resources/present.pdf");

		Date lastModified = new Date(pdf.lastModified());

		ResponseBuilder evaluatePreconditions = request.evaluatePreconditions(lastModified);
		if (evaluatePreconditions != null)
			return evaluatePreconditions.build();

		return Response.ok(pdf).header("Content-Disposition", "attachment; filename=Presentazione.pdf").lastModified(lastModified).build();
	}

	@XmlRootElement(name = "user")
	static class UserDetails {

		private String role;
		private String company;
		private String email;
		private String blog;
		private String linkedin;
		private Integer version;

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getBlog() {
			return blog;
		}

		public void setBlog(String blog) {
			this.blog = blog;
		}

		public String getLinkedin() {
			return linkedin;
		}

		public void setLinkedin(String linkedin) {
			this.linkedin = linkedin;
		}

		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}

	}

}