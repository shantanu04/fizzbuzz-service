package com.myapp.fizzbuzzservice.persistence;

import com.myapp.fizzbuzzservice.dto.ParameterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author shantanuk
 * <p>
 * JPA Repository for persisting data
 */
@Repository
public interface ParameterRepository extends JpaRepository<ParameterDto, String> {

}
