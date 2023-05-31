package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.entities.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository <Client, Long> {

    List<Client> findAllClientNameByUserId(Long userId);
}
