package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AccessMethodsServiceImpl implements AccessMethodsService {

    private final AccessMethodsRepository accessMethodsRepository;

    @Autowired
    public AccessMethodsServiceImpl(AccessMethodsRepository accessMethodsRepository) {
        this.accessMethodsRepository = accessMethodsRepository;
    }

    @Override
    public Mono<AccessMethods> getAccessMethodsById(Long id) {
        Optional<AccessMethods> accessMethodsOpt = accessMethodsRepository.findById(id);
        return accessMethodsOpt.map(Mono::just).orElseGet(Mono::empty);
    }

    @Override
    public Mono<AccessMethods> saveAccessMethod(Mono<AccessMethods> accessMethods) {
        return accessMethods.map(accessMethodsRepository::save);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            accessMethodsRepository.deleteById(id);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public Mono<AccessMethods> updateAccessMethod(Mono<AccessMethods> accessMethodsMono) {
        return accessMethodsMono.map(accessMethods -> {
            if(accessMethodsRepository.existsById(accessMethods.getAccessId())){
                return accessMethodsRepository.save(accessMethods);
            }else {
                throw new IllegalArgumentException("id of access method is not found");
            }
        });
    }


}
