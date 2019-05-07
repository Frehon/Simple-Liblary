package pl.vm.library.to;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Transport Object of the Book class.
 */
public class BookTo implements Serializable {

	private static final long serialVersionUID = -3939153951563803292L;

	private Long id;

	@NotNull
	@Size(max = 255)
	private String author;

	@NotNull
	@Size(max = 255)
	private String title;

	@NotNull
	@Size(min = 13, max = 13)
	private String isbn;

	private Date releaseDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
}
