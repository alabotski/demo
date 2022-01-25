package by.demo.repository;

import java.util.UUID;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.demo.domain.LimitEntity;


@Repository
@JaversSpringDataAuditable
public interface LimitRepository extends JpaRepository<LimitEntity, UUID> {

}
