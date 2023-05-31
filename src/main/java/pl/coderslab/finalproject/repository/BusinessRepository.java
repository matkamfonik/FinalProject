package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.entities.Business;

import java.util.List;

public interface BusinessRepository extends JpaRepository <Business, Long> {

    List<Business> findAllBusinessNameByUserId(Long userId);
}
