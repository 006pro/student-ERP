package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Grade.GradeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Grade.GradeResponseDTO;
import com.vimalesh.student_ERP.Entity.Grade;
import com.vimalesh.student_ERP.Entity.Student;
import com.vimalesh.student_ERP.Mapper.GradeMapper;
import com.vimalesh.student_ERP.Repo.ClassRoomRepo;
import com.vimalesh.student_ERP.Repo.GradeRepo;
import com.vimalesh.student_ERP.Repo.StudentRepo;
import com.vimalesh.student_ERP.Util.GradeCalculator;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    @Autowired
    GradeRepo gradeRepository;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    GradeCalculator gradeCalculator;

    public List<GradeResponseDTO> getGradesByStudent(int studentId) {
        return gradeRepository.findAllByStudentId(studentId).stream()
                .map(g -> {
                    GradeResponseDTO dto = new GradeResponseDTO();
                    dto.setGrade(gradeCalculator.calculateGrade(dto.getMarks(), dto.getMaxMarks()));
                    return dto;
                })
                .collect(Collectors.toList());

    }

    public GradeResponseDTO saveGrade(@Valid GradeRequestDTO dto) {
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("student not found"));


        Grade grade = gradeMapper.toEntity(dto);
        grade.setStudent(student);

      Grade saved =   gradeRepository.save(grade);
      GradeResponseDTO response = gradeMapper.toDTO(saved);
      response.setGrade(gradeCalculator.calculateGrade(grade.getMarks(), grade.getMaxMarks()));
      return response;
    }

    public void saveBulkGrades(@Valid List<GradeRequestDTO> dtos) {
         dtos.forEach(this::saveGrade);

    }

    public List<GradeResponseDTO> getGradesByStudentAndTerm(int studentId, String term) {
        return gradeRepository.findAllByStudentIdAndTerm(studentId , term).stream()
                .map(g -> {
                    GradeResponseDTO dto = new GradeResponseDTO();
                    dto.setGrade(gradeCalculator.calculateGrade(dto.getMarks(), dto.getMaxMarks()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Double calculateAverage(int studentId, String term) {
       List<Grade> grades = gradeRepository.findAllByStudentIdAndTerm(studentId ,term);
        if (grades.isEmpty()) return 0.0;

        double totalpercent = grades.stream()
                .mapToDouble(g -> gradeCalculator.calculatePercentage(g.getMarks(), g.getMaxMarks()))
                .sum();

             return Math.round(totalpercent / grades.size() * 100) / 100.0;

    }
}
