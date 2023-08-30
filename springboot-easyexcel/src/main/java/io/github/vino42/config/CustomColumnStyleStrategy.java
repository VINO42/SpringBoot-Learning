package io.github.vino42.config;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.List;

/**
 * 自定义表格格式
 */
public class CustomColumnStyleStrategy extends AbstractVerticalCellStyleStrategy {

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }


    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {


        if (cell.getRowIndex() != 0) {
            SXSSFSheet sheet = (SXSSFSheet) writeSheetHolder.getSheet();
            sheet.trackAllColumnsForAutoSizing();
            sheet.autoSizeColumn(cell.getColumnIndex());
        }
    }

    @Override
    protected WriteCellStyle headCellStyle(Head head) {
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return writeCellStyle;
    }

    @Override
    protected WriteCellStyle contentCellStyle(Head head) {
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        writeCellStyle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        return writeCellStyle;
    }
}
