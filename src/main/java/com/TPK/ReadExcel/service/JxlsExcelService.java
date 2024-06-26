package com.TPK.ReadExcel.service;

import com.TPK.ReadExcel.modal.ColumnExcel;
import com.TPK.ReadExcel.utils.ExcelUtils;
import jxl.Sheet;
import jxl.Workbook;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JxlsExcelService {

    private final ExcelUtils excelUtils;

    @SneakyThrows
    public List<ColumnExcel> readExcel(String path, List<Integer> column, Integer rowStart, Integer sheetStart) {
        List<ColumnExcel> resultExcel = new ArrayList<>();
        Workbook workbook = Workbook.getWorkbook(new File(path));
        Sheet sheet = workbook.getSheet(sheetStart);
        int rows = sheet.getRows();
        int columns = sheet.getColumns();
        List<String> rowDataArr = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            if (i >= rowStart) {
                column.forEach(e -> {
                    for (int j = 0; j < columns; j++) {
                        if (j == e) {
                            rowDataArr.add(sheet.getCell(j,e).getContents());
                        }
                    }
                });
                ColumnExcel excelFile = excelUtils.addListDataToColumnExcel(rowDataArr);
                resultExcel.add(excelFile);
                rowDataArr.clear();
            }
        }
        return resultExcel;
    }

}
