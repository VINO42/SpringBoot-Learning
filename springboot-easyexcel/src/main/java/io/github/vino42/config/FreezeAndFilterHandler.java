package io.github.vino42.config;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 四个参数分别代表:
 * cellNum:表示要冻结的列数；
 * rowNum:表示要冻结的行数；
 * firstCellNum:表示被固定列右边第一列的列号；
 * firstRollNum :表示被固定行下边第一列的行号;
 *
 * 举例：
 * CreateFreezePane(0,1,0,1):冻结第一行,冻结行下侧第一行的左边框显示“2”
 * CreateFreezePane(1,0,1,0):冻结第一列，冻结列右侧的第一列为B列
 * CreateFreezePane(2,0,5,0):冻结左侧两列，冻结列右侧的第一列为F列
 */
public class FreezeAndFilterHandler implements SheetWriteHandler {

    public int colSplit = 0, rowSplit = 1, leftmostColumn = 0, topRow = 1;
    public String autoFilterRange = "1:1";

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {

        SheetWriteHandler.super.afterSheetCreate(context);
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        //默认列宽
        sheet.setDefaultColumnWidth(20);
        sheet.createFreezePane(colSplit, rowSplit, leftmostColumn, topRow);
        sheet.setAutoFilter(CellRangeAddress.valueOf(autoFilterRange));
    }
}
