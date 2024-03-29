package io.github.vino42.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import io.github.vino42.config.AutoHeadColumnWidthStyleStrategy;
import io.github.vino42.config.CustomColumnStyleStrategy;
import io.github.vino42.config.FreezeAndFilterHandler;
import io.github.vino42.domain.DynamicHeader;
import io.github.vino42.domain.ExcelExportModel;
import io.github.vino42.domain.UploadData;
import io.github.vino42.util.EasyExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * =====================================================================================
 *
 * @Created :   2022/3/15 20:55
 * @Compiler :  jdk 17
 * @Author :    VINO
 * @Email :
 * @Copyright : VINO
 * @Decription :
 * =====================================================================================
 */
@RestController
@RequestMapping("/")
public class ExcelController {

    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        String sheetName = DateUtil.today();

        EasyExcelUtil.netExport(response, sheetName, "我的测试Excel下载_", Lists.newArrayList(),
                null, ExcelExportModel.class, null,
                new AutoHeadColumnWidthStyleStrategy(), new FreezeAndFilterHandler(), new CustomColumnStyleStrategy()
        );
    }

    @RequestMapping("/template")
    public void template(HttpServletResponse response) throws IOException {
        String sheetName = DateUtil.today();

        EasyExcelUtil.netExport(response, sheetName, "我的测试Excel下载_", Lists.newArrayList(),
                null, ExcelExportModel.class, null,
                new AutoHeadColumnWidthStyleStrategy(), new FreezeAndFilterHandler(), new CustomColumnStyleStrategy(), null
        );
    }

    @RequestMapping("/a")
    public void exportExcelFro(HttpServletResponse response) throws IOException {
        String sheetName = DateUtil.today();

        String fileName = URLEncoder.encode("我的测试Excel冻结表头自动列宽下载_" + DateUtil.today(), "UTF-8");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        // 这里需要设置不关闭流
        EasyExcel.write(response.getOutputStream(), ExcelExportModel.class)
                .head(ExcelExportModel.class)
                .registerWriteHandler(new AutoHeadColumnWidthStyleStrategy())
                .registerWriteHandler(new FreezeAndFilterHandler())
                .registerWriteHandler(new CustomColumnStyleStrategy())
                .autoCloseStream(Boolean.TRUE).sheet("测试")
                .doWrite(data());
    }

    @GetMapping("exportWithJson")
    public void downloadFailedUsingJson(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            String sheetName = DateUtil.today() + "我是";

            String fileName = URLEncoder.encode("我的测试2Excel下载_" + DateUtil.today(), "UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), ExcelExportModel.class).autoCloseStream(Boolean.FALSE).sheet("测试")
                    .doWrite(data());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSONUtil.toJsonStr(map));
        }
    }

    private List<ExcelExportModel> data() {
        List<ExcelExportModel> datas = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            datas.add(ExcelExportModel.getData());
        }

        return datas;

    }

    @RequestMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ExcelExportModel.class, new UploadDataListener()).sheet().doRead();
        return "success";
    }

    @RequestMapping("/testDynamicExport")
    public void testDynamicExport(HttpServletResponse response) throws IOException {
        //模拟数据
        //一般动态数据使用的是List，然后内部使用Map进行数据的接受
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<>();
            for (int j = 0; j < 5; j++) {
                map.put("title" + j, i + " " + j);
            }
            //这个用于测试值如果为null时，能否进行默认值填充
            map.put("title5", null);
            list.add(map);
        }

        //使用LinkedHashMap进行表头字段映射
        LinkedHashMap<String, DynamicHeader> nameMap = new LinkedHashMap<>();
        nameMap.put("title1", new DynamicHeader("年龄", "0"));
        nameMap.put("title0", new DynamicHeader("姓名", "0"));
        nameMap.put("title2", new DynamicHeader("职业", "0"));
        nameMap.put("title3", new DynamicHeader("爱好", "0"));
        nameMap.put("title4", new DynamicHeader("小名", "0"));
        nameMap.put("title5", new DynamicHeader("空白字段", "0"));
        String sheetName = DateUtil.today();
        String fileNmae = "测试";

        EasyExcelUtil.dynamicExport(response, fileNmae, sheetName, nameMap, list, null, null,
                new AutoHeadColumnWidthStyleStrategy(), new FreezeAndFilterHandler(), new CustomColumnStyleStrategy()
        );

    }
}
