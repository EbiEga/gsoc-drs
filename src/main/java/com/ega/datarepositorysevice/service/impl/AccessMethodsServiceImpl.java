package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.repository.AccessMethodsRepository;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.service.AccessMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public Mono<AccessMethods> getAccessMethodsById(Long objectId, Long id) {
        return accessMethodsRepository
                .findById(id)
                .map(Mono::just)
                .orElse(Mono.error(new EmptyResultDataAccessException("Access id not found", 1)))
                .map(mono -> {
                    checkIsObjectExist(objectId);
                    return mono;
                });
    }


    @Override
    public Mono<AccessMethods> saveAccessMethod(Long objectId, Mono<AccessMethods> accessMethods) {
        return accessMethods.map(methods ->{
                objectRepository
                        .findById(objectId)
                        .map(object -> {
                            methods.setObject(object);
                            return object;
                        })
                        .orElseThrow(() -> new EmptyResultDataAccessException("Object not found",1));
                return accessMethodsRepository.save(methods);
        });

    }

    @Override
    public Mono<Void> deleteById(Long objectId, Long id)  {
        try{
            checkIsObjectExist(objectId);
            accessMethodsRepository.deleteById(id);
            return Mono.empty();
        }catch (EmptyResultDataAccessException e){
            return Mono.error(e);
        }
    }

    @Override
    public Mono<AccessMethods> updateAccessMethod(Long objectId,Mono<AccessMethods> accessMethodsMono)  {
        return accessMethodsMono.map(accessMethods -> {
            checkIsObjectExist(objectId);
            if(!accessMethodsRepository.existsById(accessMethods.getAccessId())){
                throw  new EmptyResultDataAccessException("Access id not found", 1);
            }
            return accessMethodsRepository.save(accessMethods);
        });

    }

    private void checkIsObjectExist(Long objectId) {
        if(!objectRepository.existsById(objectId)) {
            throw new EmptyResultDataAccessException("Object by given id not found",1);
        }
    }


}
