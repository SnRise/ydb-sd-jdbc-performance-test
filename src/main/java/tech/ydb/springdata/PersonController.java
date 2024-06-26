package tech.ydb.springdata;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Madiyar Nurgazin
 */
@RestController("personControllerSpringDataJDBC")
@RequestMapping("/sd/jdbc/person")
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/between")
    public List<Person> getPersonsByIdBetween(@RequestParam int from, @RequestParam int to) {
        return personRepository.findByIdBetween(from, to);
    }

    @GetMapping
    private List<Person> getAllPersonsWithLimit() {
        return personRepository.findAllWithLimit();
    }

    @PostMapping
    public Person save(@RequestBody Person person) {
        return personRepository.insert(person);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        personRepository.deleteById(id);
    }
}
