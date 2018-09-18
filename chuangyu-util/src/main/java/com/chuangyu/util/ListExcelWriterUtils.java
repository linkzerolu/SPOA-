package com.chuangyu.util;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel模版导出工具类
 *
 * @author : zengRui
 * @date : 2018/7/2
 **/

public class ListExcelWriterUtils<T> {

    private final static Logger log = LoggerFactory.getLogger(ListExcelWriterUtils.class);

    private List<Integer> columnWidthList = new ArrayList<>();

    private List<CellStyle> titleStyle = new ArrayList<>();

    private List<CellStyle> rowStyle = new ArrayList<>();

    private List<RichTextString> titleText = new ArrayList<>();

    private List<String> dataTemplate = new ArrayList<>();

    int defaultColumnWidth;

    short defaultRowHeight;

    private static Pattern pattern = Pattern.compile("\\{\\=(.+?)\\}");

    private Resource resource;

    public static String SUCCESS = "success";

    public ListExcelWriterUtils(Resource resource) {
        this.resource = resource;
    }

    /**
     * 读取Excel模板
     */
    public void install() {

        // 声明一个工作薄
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(resource.getInputStream());
        } catch (IOException e) {
            log.error("resource.getInputStream() exception", e);
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheetAt(0);
        Row titleRow = sheet.getRow(0);
        defaultColumnWidth = sheet.getDefaultColumnWidth();
        defaultRowHeight = sheet.getDefaultRowHeight();

        Iterator<Cell> cellIterator = titleRow.cellIterator();
        for (int i = 0; cellIterator.hasNext(); i++) {
            Cell titleCell = cellIterator.next();
            titleStyle.add(titleCell.getCellStyle());
            columnWidthList.add(sheet.getColumnWidth(i));
            titleText.add(titleCell.getRichStringCellValue());
        }

        Row dataRow = sheet.getRow(1);
        cellIterator = dataRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            rowStyle.add(cell.getCellStyle());
            dataTemplate.add(cell.getStringCellValue());
        }

    }

    public boolean isExcel2003(String filePath) {

        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public boolean isExcel2007(String filePath) {

        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 生成Excel文件
     *
     * @param dataSet  :
     * @param response :
     * @throws IOException :
     */
    @SuppressWarnings("resource")
    public String fillToFile(List<T> dataSet, HttpServletResponse response,String filePrefix) throws IOException {

        String fileName = generateExportFileName(filePrefix);

        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));

        // 声明一个工作薄
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        // 生成一个表格
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(defaultColumnWidth);
        sheet.setDefaultRowHeight(defaultRowHeight);

        for (int i = 0; i < columnWidthList.size(); i++) {
            sheet.setColumnWidth(i, (int) (columnWidthList.get(i) * 1.15));
        }
        Row titleRow = sheet.createRow(0);
        // 产生表格标题行
        List<CellStyle> rowStyles = new ArrayList<>();

        for (int i = 0; i < titleStyle.size(); i++) {
            Cell cell = titleRow.createCell(i);
            CellStyle style = workbook.createCellStyle();
            style.cloneStyleFrom(titleStyle.get(i));
            cell.setCellStyle(style);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(titleText.get(i));

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.cloneStyleFrom(rowStyle.get(i));
            rowStyles.add(cellStyle);
        }

        for (int j = 0; j < dataSet.size(); j++) {
            fillRows(sheet, dataSet.get(j), rowStyles, j);
            dataSet.set(j, null);
        }
        ServletOutputStream fileOut = response.getOutputStream();
        try {
            workbook.write(fileOut);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "生成excel文件出错";
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return "写入excel文件出错";
                }
            }
        }
        return SUCCESS;

    }

    /**
     * 填写行数据
     *
     * @param sheet
     * @param rowData
     * @param rowStyles
     * @param rowNum
     */
    private void fillRows(Sheet sheet, Object rowData, List<CellStyle> rowStyles, int rowNum) {

        JXPathContext objectContext = JXPathContext.newContext(rowData);
        Row row = sheet.createRow(1 + rowNum);
        for (int i = 0; i < rowStyle.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(rowStyles.get(i));
            String templateCellValue = dataTemplate.get(i);
            Matcher matcher = pattern.matcher(templateCellValue);
            Map<String, Object> replacerMap = new HashMap<>();
            while (matcher.find()) {
                String xpath = matcher.group(1);
                Object valueObj = null;
                try {
                    valueObj = objectContext.getValue(xpath);
                } catch (JXPathNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
                replacerMap.put(matcher.group(0), valueObj == null ? "" : valueObj);
            }

            for (Entry<String, Object> entry : replacerMap.entrySet()) {
                templateCellValue = templateCellValue.replace(entry.getKey(), entry.getValue().toString());
            }
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(templateCellValue);
        }
        rowData = null;

    }


    /**
     * 生成导出文件名称
     * @param filePrefix : 文件前缀
     * @return : 导出文件名称
     */
    private static String generateExportFileName(String filePrefix) {
        String dateFormat = DateUtil.formatDateByFormat(new Date(), DateUtil.DATETIME_FORMAT_STAMP);
        return filePrefix + "_" + dateFormat + ".xlsx";
    }
}
