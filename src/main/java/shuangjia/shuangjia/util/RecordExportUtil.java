package shuangjia.shuangjia.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import shuangjia.shuangjia.entities.AlarmData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordExportUtil {
    private static final String[] COLUMNSTRING = {"序号", "数据类型", "设备名称", "处理情况",
            "状态", "报警类型", "报警时间", "处理时间", "当前值", "处理人", "备注"};
    Integer columnNum = COLUMNSTRING.length;

    public XSSFWorkbook recordExport(List<AlarmData> alarmDataList) {
        //POI对2007版本以及更高版本的支持
        XSSFWorkbook wb = new XSSFWorkbook();

        //创建Excel打开之后的当前页
        Sheet sheet = wb.createSheet("0");


        for (int i = 0; i < columnNum; i++) {
            sheet.setColumnWidth(i, 4300);
        }

        /**
         * 单元格 样式
         */

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中

        /**
         * 标题样式 样式
         */
        XSSFFont titleFont = wb.createFont();
        titleFont.setFontHeight(24);
        titleFont.setBold(true);
        CellStyle titleCellStyle = wb.createCellStyle();
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
//        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
//        titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中
        titleCellStyle.setFont(titleFont);

        /**
         * 主 标题 在这里插入主标题
         */
        Row titleRow;
        Cell titleCell;
        sheet.addMergedRegion(new CellRangeAddress((short) 0, (short) 2, (short) 0, (short) 8));
        for (int i = 0; i <= 2; i++) {
            titleRow = sheet.createRow(i);
            for (int j = 0; j < 9; j++) {
                titleCell = titleRow.createCell(j);
                titleCell.setCellType(CellType.STRING);
                titleCell.setCellStyle(titleCellStyle);
                titleCell.setCellValue("报警记录导出数据");
            }
        }

        /**
         * 列 标题 在这里插入标题
         */
        Row rowLabel;
        Cell cellLabel;
        for (int i = 3; i < 4; i++) {
            rowLabel = sheet.createRow(i);
            for (int j = 0; j < columnNum; j++) {
                cellLabel = rowLabel.createCell(j);
                cellLabel.setCellType(CellType.STRING);
                cellLabel.setCellStyle(cellStyle);
                cellLabel.setCellValue(COLUMNSTRING[j]);
            }
        }

        /**
         * 列 数据 在这里插入数据
         */
        Row rowCheck;
        Cell cellCheck;
        for (int i = 3; i < alarmDataList.size() + 3; i++) {
            rowCheck = sheet.createRow((i + 1));
            for (int j = 0; j < columnNum; j++) {
                cellCheck = rowCheck.createCell(j);
                cellCheck.setCellType(CellType.STRING);
                cellCheck.setCellStyle(cellStyle);
                AlarmData alarmData = alarmDataList.get(i - 3);
                Object[] objects = {alarmData.getAlarmDataId(), alarmData.getDataTypeName(),
                        alarmData.getDeviceName(), alarmData.getDealContent(),
                        alarmData.getDealState(), alarmData.getAlarmType(),
                        alarmData.getAlarmTime(), alarmData.getDealTime(),
                        alarmData.getMonitorValue(), alarmData.getDealPerson(),
                        alarmData.getStage()};
                cellCheck.setCellValue(String.valueOf(objects[j]));
            }
        }
        return wb;
    }

}
//        File file =null;
//        try {
//            file = new File("D:\\报警记录.xls");
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            wb.write(fileOutputStream);
//            fileOutputStream.close();
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        }
//        FileInputStream in = null;
//        try {
//            in = new FileInputStream(file);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return in;
//
//    }
//
//
//}


/**
/*
@Controller
@RequestMapping(value = "/manage")
public class ManageAction {
    /**
     * 通过流把文件传到前台下载
     *
     * @param request
     * @param response
     * @param id       第几个文件  （因为有多个文件  用;号隔开的）
     * @param tzggid   对应的通知公告id
     *//*
    @RequestMapping(value = "/findfile")
    @ResponseBody
    public void findfile(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id, @RequestParam("tzggid") String tzggid) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;
        List<Map<String, Object>> list = null;  //此处为业务需要
        list = jdbcTemplate.queryForList("select fjaddress,fjname from tb_tzgg where id = ?", tzggid);  //此处为业务需要
        if (list.size() > 0) {
            try {
                String url = String.valueOf(list.get(0).get("fjaddress")).split(";")[Integer.valueOf(id)];  //此处为业务需要  如果是测试可以指定路径
                //获取文件存放的路径
                File file = new File(url);
                String fileName = file.getName();
                //获取到文字 数据库里对应的附件名字加上老的文件名字：filename 截取到后面的文件类型 例：txt  组成一个新的文件名字：newFileName
                String newFileName = String.valueOf(list.get(0).get("fjname")).split(";")[Integer.parseInt(id)] + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
                if (!file.exists()) {
                    //如果文件不存在就跳出
                    return;
                }
                ips = new FileInputStream(file);
                response.setContentType("multipart/form-data");
                //为文件重新设置名字，采用数据库内存储的文件名称
                response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(newFileName.getBytes("UTF-8"), "ISO8859-1") + "\"");
                out = response.getOutputStream();
                //读取文件流
                int len = 0;
                byte[] buffer = new byte[1024 * 10];
                while ((len = ips.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    ips.close();
                } catch (IOException e) {
                    System.out.println("关闭流出现异常");
                    e.printStackTrace();
                }
            }
        }
        return;
    }
  */

