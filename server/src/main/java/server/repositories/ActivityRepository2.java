package server.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entities.Actions.Action;
//import commons.Action;

//@Repository
public interface ActivityRepository2 extends JpaRepository<Action, String> {

}
