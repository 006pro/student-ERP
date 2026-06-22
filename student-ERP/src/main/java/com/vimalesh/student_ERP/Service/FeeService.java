package com.vimalesh.student_ERP.Service;

import com.vimalesh.student_ERP.DTO.Request.Fee.FeeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Fee.FeeResponseDTO;
import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;
import com.vimalesh.student_ERP.Entity.Fee;
import com.vimalesh.student_ERP.Entity.Student;
import com.vimalesh.student_ERP.Mapper.FeeMapper;
import com.vimalesh.student_ERP.Repo.FeeRepo;
import com.vimalesh.student_ERP.Repo.StudentRepo;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class FeeService {


    @Autowired
    StudentRepo studentRepo;
    @Autowired
    FeeRepo feeRepo;
    @Autowired
    FeeMapper feeMapper;
    public FeeResponseDTO createFeeRecord(@Valid FeeRequestDTO dto) {
        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Fee fee = feeMapper.toEntity(dto);
        fee.setStudent(student);
        feeRepo.save(fee);
        return feeMapper.toDTO(fee);
    }

    public FeeResponseDTO markAsPaid(int id) {
        Fee fee = feeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Fee record not found with id: " + id));
       if(fee.getStatus() == FeeStatus.PAID) {
           throw new RuntimeException("Fee record with id: " + id + " is already marked as PAID");
       }
       fee.setStatus(FeeStatus.PAID);
       fee.setDueDate(LocalDate.now());
       feeRepo.save(fee);
        return feeMapper.toDTO(fee);
    }

    public List<FeeResponseDTO> getFeesByStudent(int studentId) {

     return feeRepo.findByStudentId(studentId)
             .stream()
             .map(feeMapper::toDTO)
             .collect(Collectors.toList());

    }

    public List<FeeResponseDTO> getPendingFees() {
        return feeRepo.findByStatus(FeeStatus.PENDING)
                .stream()
                .map(feeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FeeResponseDTO> getOverdueFees() {
        return feeRepo.findByStatus(FeeStatus.OVERDUE)
                .stream()
                .map(feeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
