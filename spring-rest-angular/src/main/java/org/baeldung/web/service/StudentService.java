package org.baeldung.web.service;

import org.baeldung.web.model.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
	 Page<Student> findPaginated(final int page, final int size);
}
