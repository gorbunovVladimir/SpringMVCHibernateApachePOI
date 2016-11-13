package com.gorbunov.spring.service;

import com.gorbunov.spring.dao.HumanHospitalizationDAO;
import com.gorbunov.spring.model.FileUpload;
import com.gorbunov.spring.model.HumanHospitalization;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;


/**
 * Created by Vl on 06.11.2016.
 */
public class ImportServiceImpl implements ImportService {
    @Autowired
    private HumanHospitalizationDAO HumanHospitalizationDAO;

    private static final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);

    @Override
    public void importFile(FileUpload fileUpload) {

        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(fileUpload.getFile().getBytes());
        } catch (IOException e) {
            logger.error(e.toString());
            //e.printStackTrace();
        }
        Workbook workbook;
        Sheet sheet=null;
        try {
            if (fileUpload.getFile().getOriginalFilename().endsWith("xls")) {
                workbook = new HSSFWorkbook(bis);
                if (workbook.getSheetAt(0) instanceof HSSFSheet)
                    sheet = (HSSFSheet) workbook.getSheetAt(0);
            } else if (fileUpload.getFile().getOriginalFilename().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(bis);
                if (workbook.getSheetAt(0) instanceof XSSFSheet)
                    sheet = (XSSFSheet)workbook.getSheetAt(0);
            } else {
                logger.error(String.format("Received file %s does not have a standard excel extension ", fileUpload.getFile().getOriginalFilename()));
                //throw new IllegalArgumentException(String.format("Received file %s does not have a standard excel extension %s", fileUpload.getFile().getOriginalFilename()));
            }
            if (sheet!=null) {
                for (Row row : sheet) {
                    if (row!=null){
                        HumanHospitalization humanHospitalization = new HumanHospitalization();
                        if (row.getCell(0)!=null/*&&row.getCell(0).getCellTypeEnum().equals(Cell.CELL_TYPE_STRING)*/)
                            humanHospitalization.setName(row.getCell(0).getStringCellValue());
                        else logger.error(String.format("Column 0 on row %d has an invalid format on received file %s", row.getRowNum(),fileUpload.getFile().getOriginalFilename()));
                        if ((row.getCell(1)!=null/*&&row.getCell(1).getCellTypeEnum().equals(Cell.CELL_TYPE_NUMERIC)*/)&&
                            (((sheet instanceof HSSFSheet)&&HSSFDateUtil.isCellDateFormatted(row.getCell(1)))||
                             ((sheet instanceof XSSFSheet)&&DateUtil.isCellDateFormatted(row.getCell(1)))))
                                humanHospitalization.setBdate(row.getCell(1).getDateCellValue());
                        else logger.error(String.format("Column 1 on row %d has an invalid format on received file %s", row.getRowNum(),fileUpload.getFile().getOriginalFilename()));
                        if ((row.getCell(2)!=null/*&&row.getCell(2).getCellTypeEnum().equals(Cell.CELL_TYPE_NUMERIC)*/)&&
                             (((sheet instanceof HSSFSheet)&&HSSFDateUtil.isCellDateFormatted(row.getCell(2)))||
                              ((sheet instanceof XSSFSheet)&&DateUtil.isCellDateFormatted(row.getCell(2)))))
                                 humanHospitalization.setEdate(row.getCell(2).getDateCellValue());
                        else logger.error(String.format("Column 2 on row %d has an invalid format on received file %s", row.getRowNum(),fileUpload.getFile().getOriginalFilename()));
                        HumanHospitalizationDAO.addHumanHospitalization(humanHospitalization);
                    }
                    else logger.info(String.format("Row %d has an invalid format on received file %s",row.getRowNum(), fileUpload.getFile().getOriginalFilename()));
                }
            }
            else logger.error(String.format("Sheet %d has an invalid format on received file %s", sheet.getLastRowNum(), fileUpload.getFile().getOriginalFilename()));
        } catch (IOException e) {
            logger.error(e.toString());
            //e.printStackTrace();
        }
    }
}
