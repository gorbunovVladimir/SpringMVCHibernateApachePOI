package com.gorbunov.spring.dao;

import com.gorbunov.spring.model.HumanHospitalization;

import java.util.List;

/**
 * Created by Vl on 12.11.2016.
 */
public interface HumanHospitalizationDAO {
    public void addHumanHospitalization(HumanHospitalization h);
    public List<HumanHospitalization> listHumanHospitalization();
    public void removeAll();
}
