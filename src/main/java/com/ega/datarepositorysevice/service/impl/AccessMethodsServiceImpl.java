package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AccessMethodsServiceImpl implements AccessMethodsService {

    private final AccessMethodsRepository accessMethodsRepository;
    private final ObjectRepository objectRepository;

    @Autowired
    public AccessMethodsServiceImpl(AccessMethodsRepository accessMethodsRepository, ObjectRepository objectRepository) {
        this.accessMethodsRepository = accessMethodsRepository;
        this.objectRepository = objectRepository;
    }

    @Override
    public Mono<AccessMethods> getAccessMethodsById(Long objectId,Long id) {
        try {
            checkIsObjectExist(objectId);
        }catch (IllegalArgumentException e){
            return Mono.error(e);
        }
        Optional<AccessMethods> accessMethodsOpt = accessMethodsRepository.findById(id);
        return accessMethodsOpt.map(Mono::just).orElse(Mono.error(new IllegalAccessException("access id not found")));
    }


    @Override
    public Mono<AccessMethods> saveAccessMethod(Long objectId, Mono<AccessMethods> accessMethods) {
        return accessMethods.map(methods -> {
            checkIsObjectExist(objectId);
            Optional<Object> objectOptional = objectRepository.findById(objectId);
            Object object = objectOptional.get();
            methods.setObject(object);
            return accessMethodsRepository.save(methods);
        });

    }

    @Override
    public Mono<Boolean> deleteById(Long objectId, Long id)  {
        try {
            checkIsObjectExist(objectId);
            accessMethodsRepository.deleteById(id);
            return Mono.just(true);
        }catch (IllegalArgumentException e){
            return Mono.error(e);
        }
    }

    @Override
    public Mono<AccessMethods> updateAccessMethod(Long objectId,Mono<AccessMethods> accessMethodsMono)  {
        return accessMethodsMono.map(accessMethods -> {
            checkIsObjectExist(objectId);
            if(accessMethodsRepository.existsById(accessMethods.getAccessId())){
                return accessMethodsRepository.save(accessMethods);
            }else {
                throw new IllegalArgumentException("id of access method is not found");
            }
        });

    }

    private void checkIsObjectExist(Long objectId) {
        if(!objectRepository.existsById(objectId)) {
            throw new IllegalArgumentException("Object by given id not found");
        }
    }


}
