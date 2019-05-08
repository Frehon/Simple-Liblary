package pl.vm.library.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.vm.library.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private BookServiceImpl bookService;

	@Test
	public void trueIsTrue() {
		assertThat(true).isTrue();
	}

	// TODO Tests for delete
}
