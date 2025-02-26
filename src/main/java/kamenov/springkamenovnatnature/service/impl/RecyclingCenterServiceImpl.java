package kamenov.springkamenovnatnature.service.impl;

import kamenov.springkamenovnatnature.entity.RecyclingCenter;
import kamenov.springkamenovnatnature.repositories.RecyclingCenterRepository;
import kamenov.springkamenovnatnature.service.RecyclingCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecyclingCenterServiceImpl implements RecyclingCenterService {
    @Autowired
    private final RecyclingCenterRepository repository;

    public RecyclingCenterServiceImpl(RecyclingCenterRepository repository) {
        this.repository = repository;
    }
@Override
    public List<RecyclingCenter> getCentersByCity(String city) {
        return repository.findByCity(city);
    }
}
