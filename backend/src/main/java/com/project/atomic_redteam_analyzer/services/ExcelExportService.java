package com.project.atomic_redteam_analyzer.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.project.atomic_redteam_analyzer.models.Technique;

@Service
public class ExcelExportService {
    @Value("${excel.output.path}")
    private String excelOutputPath;

    public void exportToExcel(List<Technique> technique) throws Exception {
        Workbook book = new Workbook();
        Worksheet sheet = book.getWorksheets().get(0);

        sheet.getCells().importCustomObjects((Collection<Technique>) technique,
                new String[] { "attack_technique", "display_name" }, true, 0, 0,
                technique.size(), true, null, false);

        book.save(excelOutputPath);
        System.out.println("Exporting data to Excel at: " + ""
                + excelOutputPath);
    }
}
