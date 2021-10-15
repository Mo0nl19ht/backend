package ganggang3.gang.Repository_en;
import ganggang3.gang.domain.Course;
import ganggang3.gang.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository_en extends JpaRepository<Course,Long> {

    List<Course> findAllByMember(Member member);
    Optional<Course> findByNameAndMember(String name, Member member);
    Optional<Course> findById(Long id);

}
