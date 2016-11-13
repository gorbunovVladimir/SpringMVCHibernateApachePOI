package com.gorbunov.spring.service;

import com.gorbunov.spring.model.HumanHospitalization;

import java.util.List;
import java.util.Map;

/**
 * Created by Vl on 12.11.2016.
 */
public interface HospitalizationService {
    public Map<String,List<HumanHospitalization>> mapHospitalization (Integer countBed);
    public void removeAll();
}
