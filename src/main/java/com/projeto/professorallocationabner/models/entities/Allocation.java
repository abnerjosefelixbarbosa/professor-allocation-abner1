package com.projeto.professorallocationabner.models.entities;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "allocation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Allocation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	@Enumerated(EnumType.STRING)
	@Column(name = "day", nullable = false)
	private DayOfWeek day;	
	@Temporal(TemporalType.TIME)
	@Column(name = "start", nullable = false)
	private Date startHour;	
	@Temporal(TemporalType.TIME)
	@Column(name = "end", nullable = false)
	private Date endHour;	
	@Column(name = "course_id", nullable = false)
	private Long courseId;	
	@Column(name = "professor_id", nullable = false)
	private Long professorId;	
	@ManyToOne(optional = false)
	@JoinColumn(name = "professor_id", nullable = false, insertable = false, updatable = false)
	private Professor professor;	
	@ManyToOne(optional = false)
	@JoinColumn(name = "course_id", nullable = false, insertable = false, updatable = false)
	private Course course;
}
