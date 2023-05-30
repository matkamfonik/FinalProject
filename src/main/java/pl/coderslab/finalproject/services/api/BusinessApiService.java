package pl.coderslab.finalproject.services.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.CurrentUser;
import pl.coderslab.finalproject.dtos.BusinessDTO;
import pl.coderslab.finalproject.entities.Business;
import pl.coderslab.finalproject.mappers.BusinessMapper;
import pl.coderslab.finalproject.repository.BusinessRepository;
import pl.coderslab.finalproject.services.interfaces.BusinessService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class BusinessApiService implements BusinessService {
    private final BusinessRepository businessRepository;

    private final BusinessMapper businessMapper;

    private final TaxationFormApiService taxationFormService;

    @Override
    public List<BusinessDTO> findAllBusinesses(CurrentUser currentUser) {
        Long userId = currentUser.getUser().getId();
        return businessRepository.findAllBusinessNameByUserId(userId).stream().map(businessMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BusinessDTO get(Long id) {
        return businessMapper.toDto(businessRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void add(BusinessDTO businessDTO, CurrentUser currentUser) {
        Business business = businessMapper.toEntity(businessDTO,
                currentUser.getUser(),
                taxationFormService.get(businessDTO.getTaxationFormId()).get());
        businessRepository.save(business);
    }
}
