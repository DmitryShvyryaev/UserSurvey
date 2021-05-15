package ru.usersSurvey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.usersSurvey.model.User;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
}
