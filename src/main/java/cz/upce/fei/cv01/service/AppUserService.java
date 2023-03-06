package cz.upce.fei.cv01.service;

import cz.upce.fei.cv01.domain.AppUser;
import cz.upce.fei.cv01.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;


@AllArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;


    public AppUser finById(final Long id) throws ResourceNotFoundException {
       return appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    public AppUser create(AppUser entity) {
        return appUserRepository.save(entity);

    }

    @Transactional
    public AppUser update( AppUser toEntity) {
        if (appUserRepository.existsByUsernameAndIdIsNot(toEntity.getUsername(), toEntity.getId())) {
            throw new IllegalArgumentException("The username already exists.");
        }
        AppUser user = appUserRepository.findById(toEntity.getId()).orElseThrow(() -> new NoSuchElementException("User not found!"));
        AppUser save = appUserRepository.save(toEntity);
        return save;

    }

    public void remove(Long userId) {
        AppUser appUser = appUserRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found!"));
        appUserRepository.deleteById(userId);
    }

}
