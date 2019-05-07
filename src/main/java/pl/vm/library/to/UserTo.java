package pl.vm.library.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Transport Object for the User class.
 * 
 * Contains also the Reservation list of the given User.
 */
public class UserTo implements Serializable {

	private static final long serialVersionUID = 5327766680748073213L;

	private Long id;

	@NotNull
	@Size(min = 6, max = 255)
	private String login;

	@NotNull
	@Size(max = 255)
	private String name;

	@NotNull
	@Size(max = 255)
	private String lastName;

	@NotNull
	@Email
	@Size(max = 255)
	private String email;

	private List<ReservationTo> reservations = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ReservationTo> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationTo> reservations) {
		this.reservations = reservations;
	}

}
