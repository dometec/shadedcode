package org.example.demo.controller;

import java.io.FileInputStream;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.jersey.spi.resource.PerRequest;

@PerRequest
@Path("services/JugMilano/People/DomenicoBriganti")
public class Presentation {

	private UserDetails userdet;

	public Presentation() {
		userdet = new UserDetails();
		userdet.setCompany("Eidon srl");
		userdet.setEmail("dometec@gmail.com");
		userdet.setLinkedin("http://www.linkedin.com/in/dometec");
		userdet.setBlog("http://tipsaboutmywork.blogspot.com/");
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUserDetails() {
		return Response.ok(userdet).build();
	}

	@GET
	@Produces("application/pdf")
	public Response getUserDetailsPdf() throws IOException {
		FileInputStream stream = new FileInputStream("src/main/resources/present.pdf");
		byte[] data = new byte[8196];
		stream.read(data);
		stream.close();
		return Response.ok(data).header("Content-Disposition", "attachment; filename=Presentazione.pdf").build();
	}

	@XmlRootElement(name = "user")
	static class UserDetails {

		private String role;
		private String company;
		private String email;
		private String blog;
		private String linkedin;

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

	}

}