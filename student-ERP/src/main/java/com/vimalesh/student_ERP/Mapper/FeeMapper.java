package com.vimalesh.student_ERP.Mapper;

import com.vimalesh.student_ERP.DTO.Request.Fee.FeeRequestDTO;
import com.vimalesh.student_ERP.DTO.Response.Fee.FeeResponseDTO;
import com.vimalesh.student_ERP.Entity.Enum.FeeStatus;
import com.vimalesh.student_ERP.Entity.Fee;
import org.springframework.stereotype.Component;

@Component
public class FeeMapper {

    public FeeResponseDTO toDTO(Fee fee) {
        FeeResponseDTO dto = new FeeResponseDTO();
        dto.setId(fee.getId());
        dto.setAmount(fee.getAmount());
        dto.setDueDate(fee.getDueDate());
        dto.setPaidOn(fee.getPaidOn());
        dto.setStatus(fee.getStatus());

        if (fee.getStudent() != null) {
            dto.setStudentName(fee.getStudent().getName());
        }
        return dto;
    }

    public Fee toEntity(FeeRequestDTO dto) {
        Fee fee = new Fee();
        fee.setAmount(dto.getAmount());
        fee.setDueDate(dto.getDueDate());
        fee.setStatus(FeeStatus.PENDING);
        return fee;
    }
}