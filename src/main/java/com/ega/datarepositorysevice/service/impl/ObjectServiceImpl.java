package com.ega.datarepositorysevice.service.impl;

import com.ega.datarepositorysevice.model.Object;
import com.ega.datarepositorysevice.repository.ObjectRepository;
import com.ega.datarepositorysevice.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ObjectServiceImpl implements ObjectService {

    private final ObjectRepository objectRepository;

    @Autowired
    public ObjectServiceImpl(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @Override
    public Mono<Object> getObjectById(Long id) {
        Optional<Object> objectOpt = objectRepository.findById(id);
        return objectOpt.map(Mono::just).orElse(Mono.error(new EmptyResultDataAccessException("id of object is not found" ,1)));
    }

    @Override
    public Mono<Void> deleteObjectById(Long id) {
        try{
            objectRepository.deleteById(id);
            return Mono.empty();
        }catch (EmptyResultDataAccessException e){
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Object> saveObject(Mono<Object> objectMono)
    {
        return objectMono.map(objectRepository::save);
    }

    @Override
    public Mono<Object> updateObject(Mono<Object> objectMono) {
        return objectMono.map(object -> {
            if(objectRepository.existsById(object.getId())){
                return objectRepository.save(object);
            }else {
                throw new EmptyResultDataAccessException("id of object is not found" ,1);
            }
        });    }
}
