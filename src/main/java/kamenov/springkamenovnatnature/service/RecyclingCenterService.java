package kamenov.springkamenovnatnature.service;

import kamenov.springkamenovnatnature.entity.RecyclingCenter;

import java.util.List;

public interface RecyclingCenterService {
    List<RecyclingCenter> getCentersByCity(String city);
}
