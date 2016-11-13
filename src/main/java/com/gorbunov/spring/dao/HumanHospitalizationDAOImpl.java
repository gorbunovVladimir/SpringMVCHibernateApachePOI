package com.gorbunov.spring.dao;

import com.gorbunov.spring.model.HumanHospitalization;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Vl on 12.11.2016.
 */
public class HumanHospitalizationDAOImpl implements HumanHospitalizationDAO {

    private static final Logger logger = LoggerFactory.getLogger(HumanHospitalizationDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    @Transactional
    public void addHumanHospitalization(HumanHospitalization h) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(h);
        logger.info(String.format(" %s saved successfully ", h));
    }

    @Override
    @Transactional
    public List<HumanHospitalization> listHumanHospitalization(){
        Session session = this.sessionFactory.getCurrentSession();
        List<HumanHospitalization> humanHospitalizationList;
        humanHospitalizationList =session.createQuery("from HumanHospitalization").list();
        for(HumanHospitalization h : humanHospitalizationList)
            logger.info("Human List::"+h);
        return humanHospitalizationList;
    }
    @Override
    @Transactional
    public void removeAll() {
        Session session = this.sessionFactory.getCurrentSession();
        session.createQuery("DELETE HumanHospitalization").executeUpdate();
        logger.info("All records deleted successfully");
    }
}
