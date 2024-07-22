package com.projeto.professorallocationabner.models.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.professorallocationabner.models.dtos.ProfessorDTO;
import com.projeto.professorallocationabner.models.dtos.ProfessorView;
import com.projeto.professorallocationabner.models.entities.Department;
import com.projeto.professorallocationabner.models.entities.Professor;
import com.projeto.professorallocationabner.models.mappers.ProfessorMapper;
import com.projeto.professorallocationabner.models.repositories.ProfessorRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorService {
	private final ProfessorRepository professorRepository;
	private final DepartmentService departmentService;
	private final ProfessorMapper professorMapper;

	public Page<ProfessorView> findAll(Pageable pageable) {
		return professorRepository
				.findAll(pageable)
				.map(professorMapper::toProfessorView);
	}

	public ProfessorView findById(Long id) {
		return professorRepository
				.findById(id)
				.map(professorMapper::toProfessorView)
				.orElseThrow(() -> new EntityNotFoundException("professor not found"));
	}
	
	public Professor findByProfessorId(Long professorId) {
		return professorRepository
				.findById(professorId)
				.orElseThrow(() -> new EntityNotFoundException("professor not found"));
	}
	
	public Page<ProfessorView> findByDepartment(Long departmentId, Pageable pageable) {
		return professorRepository
				.findByDepartmentId(departmentId, pageable)
				.map(professorMapper::toProfessorView);
	}

	public ProfessorView save(ProfessorDTO dto) {
		Professor professor = professorMapper.toProfessor(dto);
		professor = saveInternal(professor);
		return professorMapper.toProfessorView(professor);
	}

	public ProfessorView update(ProfessorDTO dto) {
		Long id = dto.id();
		
		return professorRepository
				.findById(id)
				.map((val) -> {
					Professor professor = professorMapper.toProfessor(dto);
					professor = saveInternal(professor);
					return professorMapper.toProfessorView(professor);
				})
				.orElseThrow(() -> new EntityNotFoundException("professor not found"));
	}
	
	public void deleteById(Long id) {
		Professor professor = professorRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("professor not found"));
		professorRepository.delete(professor);
    }
    
    public void deleteAll() {
    	professorRepository.deleteAllInBatch();
    }
	
	private Professor saveInternal(Professor professor) {
		professor = professorRepository.save(professor);
		
		Department department = departmentService.findDepartmentById(professor.getDepartmentId());
		professor.setDepartment(department);
		
		return professor;
	}
}
