package org.baeldung.web.service;

import org.baeldung.web.model.Student;
import org.baeldung.web.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Page<Student> findPaginated(int page, int size) {
    	Page<Student> studentPage = studentRepository.findAll(new PageRequest(page, size));
        return studentPage;
    }
}
