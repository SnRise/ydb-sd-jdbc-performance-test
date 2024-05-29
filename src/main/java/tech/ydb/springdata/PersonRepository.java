package tech.ydb.springdata;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Madiyar Nurgazin
 */
@Repository("personRepositorySpringDataJdbc")
public interface PersonRepository extends ListCrudRepository<Person, Integer> {
    List<Person> findByIdBetween(int from, int to);
}

    @Query("select * from persons limit 100")
    List<Person> findAllWithLimit();
}
