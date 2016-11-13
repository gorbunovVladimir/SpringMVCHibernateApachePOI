package com.gorbunov.spring.service;

import com.gorbunov.spring.dao.HumanHospitalizationDAO;
import com.gorbunov.spring.model.HumanHospitalization;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


/**
 * Created by Vl on 12.11.2016.
 */
public class HospitalizationServiceImpl implements HospitalizationService {
    @Autowired
    private HumanHospitalizationDAO humanHospitalizationDAO;

    public void setHumanHospitalizationDAO(HumanHospitalizationDAO humanHospitalizationDAO) {
        this.humanHospitalizationDAO = humanHospitalizationDAO;
    }
    @Override
    public Map<String, List<HumanHospitalization>> mapHospitalization(Integer countBed) {
        if (countBed==0||countBed==null) return null;

        //пациенты предполагаемые к госпитализации
        List<HumanHospitalization> humansHospitalization;
        humansHospitalization=humanHospitalizationDAO.listHumanHospitalization();
        if (humansHospitalization.size()<=0)
            return null;

        Map<String, List<HumanHospitalization>> mapHospitalization=new HashMap<>();

        //Map, где ключ - номер койки, значение - список лиц, которые на ней будут размещаться
        Map<Integer, List<HumanHospitalization>> mapHospitalizationTemp=new TreeMap<>();

        //список лиц, которым требуется скорректировать даты госпитализации
        List<HumanHospitalization> humansForNotHospitalization=new ArrayList<>();
        //признак, показывающий легитимные ли даты у пациенты
        boolean isHumanHospitalisation;

        for (int i=0;i<humansHospitalization.size();i++){
            //текущий пациент
            HumanHospitalization currentHuman=humansHospitalization.get(i);
            //заполняем вначале первые countBed пациентов для каждой койки (вследсвие этого это на текущий момент не оптимальный с точки зрения "вместимости пациентов на койку" алгоритм)
            if (mapHospitalizationTemp.size()<countBed){
                //список лиц, которые будут располагаться на данной койки
                List<HumanHospitalization> humansForHospitalization=new ArrayList<>();
                currentHuman.setBedNumber(i+1);
                humansForHospitalization.add(currentHuman);
                mapHospitalizationTemp.put(i+1,humansForHospitalization);
                continue;
            }
            isHumanHospitalisation=false;
            //для последующих пациентов проверяем могут ли занять места на койках с учетом уже присутствующих пациентов
            for (Map.Entry<Integer,List<HumanHospitalization>> entry: mapHospitalizationTemp.entrySet()) {
                if (isHumanHospitalisation) break; // чтобы не помещать уже "вмещенного" пациента на другие койки
                Integer bedNumder = entry.getKey(); //номер койки
                List<HumanHospitalization> humansForHospitalization = entry.getValue();//список пациентов на данной койки
                int countHuman=humansForHospitalization.size();//т.к. в последующем мы можем изменить размер списка пациентов (на 1), то нужно заранее посчитать их текущее количество
                for (int j=0;j<countHuman;j++){
                    HumanHospitalization currentHumanForHospitalization=humansForHospitalization.get(j);
                    //проверяем может ли текущий пациент поместить на данной койки с уже существующими (проверяем даты),isHumanHospitalisation-признак того, что даты предыдущих пациентов позволяют сделать это
                    if ((isHumanHospitalisation||j==0)
                         &&
                         ((currentHuman.getEdate().before(currentHumanForHospitalization.getBdate()) && currentHuman.getEdate().before(currentHumanForHospitalization.getEdate()))
                          ||
                          (currentHuman.getBdate().after(currentHumanForHospitalization.getBdate()) && currentHuman.getBdate().after(currentHumanForHospitalization.getEdate())))){
                            isHumanHospitalisation=true;
                    }
                    else
                            isHumanHospitalisation=false;//пациент не может находиться на текущей койки
                    //помещаем пациента на текущую койку
                    if ((j==humansForHospitalization.size()-1) && isHumanHospitalisation){
                            currentHuman.setBedNumber(bedNumder);
                            humansForHospitalization.add(currentHuman);
                            mapHospitalizationTemp.put(bedNumder,humansForHospitalization);
                        }
                    }
            }
            if (!isHumanHospitalisation)
                //помещаем пациента в список лиц для которых нужно скорректировать даты
                humansForNotHospitalization.add(currentHuman);
        }
        List<HumanHospitalization> humansForHospitalization=new ArrayList<>();
        for (List<HumanHospitalization> list: mapHospitalizationTemp.values())
            humansForHospitalization.addAll(list);
        //Для более наглядного отображения на странице
        Collections.sort(humansForHospitalization, (HumanHospitalization h1, HumanHospitalization h2)
                -> {
                Integer compareBedNumber = Integer.compare(h1.getBedNumber(),h2.getBedNumber());
                if (compareBedNumber!=0) return compareBedNumber;
                return h1.getBdate().compareTo(h2.getBdate());
            }
        );
        Collections.sort(humansForNotHospitalization, (HumanHospitalization h1, HumanHospitalization h2)
                -> {
                Integer compareBdate = h1.getBdate().compareTo(h2.getBdate());
                if (compareBdate!=0) return compareBdate;
                return h1.getName().compareTo(h2.getName());
            }
        );
        /*
         Collections.sort(humansForHospitalization, new Comparator<HumanHospitalization>() {
         public int compare(HumanHospitalization h1, HumanHospitalization h2) {
         Integer compareBedNumber = Integer.compare(h1.getBedNumber(),h2.getBedNumber());
         if (compareBedNumber!=0) return compareBedNumber;
         return h1.getBdate().compareTo(h2.getBdate());
         }
         });
         Collections.sort(humansForNotHospitalization, new Comparator<HumanHospitalization>() {
            public int compare(HumanHospitalization h1, HumanHospitalization h2) {
                Integer compareBdate = h1.getBdate().compareTo(h2.getBdate());
                if (compareBdate!=0) return compareBdate;
                return h1.getName().compareTo(h2.getName());
            }
        });
         */
        mapHospitalization.put("People for hospitalization",humansForHospitalization);
        mapHospitalization.put("People for not hospitalization", humansForNotHospitalization);
        return mapHospitalization;
    }

    @Override
    public void removeAll(){
        this.humanHospitalizationDAO.removeAll();
    }
}
