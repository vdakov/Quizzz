package server.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import commons.Actions.Action;

@Repository
public interface ActivityRepository extends JpaRepository<Action, String> {

}
