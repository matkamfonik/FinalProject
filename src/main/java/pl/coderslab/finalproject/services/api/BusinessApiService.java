package pl.coderslab.finalproject.services.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.repository.BusinessRepository;
import pl.coderslab.finalproject.services.interfaces.BusinessService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class BusinessApiService implements BusinessService {
    private final BusinessRepository businessRepository;

    @Override
    public List<Business> findAllBusinesses(CurrentUser currentUser) {
        Long userId = currentUser.getUser().getId();
        return businessRepository.findAllBusinessNameByUserId(userId);
    }

    @Override
    public Optional<Business> get(Long id){
        return businessRepository.findById(id);
    }

    @Override
    public void add(Business business) {
        businessRepository.save(business);
    }

    @Override
    public void delete(Long id){
        businessRepository.deleteById(id);
    }
}
