package kg.kazbekov.chatbot.service.internal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService <T, ID>{

    @Autowired
    private JpaRepository<T, ID> repository;

    @PostConstruct
    void postConstruct(){
        if(repository == null) {
            throw new RuntimeException("repository field of base service shouldn't be null");
        }
    }
    public List<T> findAll(){
        return repository.findAll();
    }

    public T findById(ID id){
        return repository.findById(id).orElse(null);
    }

    public T getById(ID id){
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found by id: " + id));
    }

    public T save(T model){
        return repository.save(model);
    }
}
