package pl.coderslab.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.entities.TaxationForm;
import pl.coderslab.finalproject.entities.User;

import java.util.List;

public interface BusinessRepository extends JpaRepository <Business, Long> {

    List<Business> findAllBusinessNameByUserId(Long userId);
}
