package com.example.hits2025_java_missed_classes.service;

import com.example.hits2025_java_missed_classes.model.FacultyEntity;
import com.example.hits2025_java_missed_classes.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    // TODO: получения списка всех имен факультетов - просто список названий? или id тоже? с чем в целом хочет работать фронт id или название?
    // пока выдается и id и name
    public List<FacultyEntity> getAllFaculties(){
        return facultyRepository.findAll();
    }

    // TODO: добавление нового факультета по имени с проверкой, что такого имени факультета до этого не было

    // TODO: удаление факультета по имени, возможно перед нужна проверка на то что такой факультет есть

}
