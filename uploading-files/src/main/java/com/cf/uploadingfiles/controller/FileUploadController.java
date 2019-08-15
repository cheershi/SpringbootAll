package com.cf.uploadingfiles.controller;

import com.cf.uploadingfiles.exception.StorageException;
import com.cf.uploadingfiles.exception.StorageFileNotFoundException;
import com.cf.uploadingfiles.service.StorageService;
import com.cf.uploadingfiles.until.CommUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：fengchen
 * @date ：Created in 2019/8/6 10:35
 * @description：文件上传 下载 导入 导出
 * @version: 1.0.0
 */
@Controller
public class FileUploadController {
    //上传路径
    private static final String UPLOAD_DIR ="static/upload/";


    private final StorageService storageService;
    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


    /**
     * 导出excel
     * @param request
     * @param resp
     * @throws Exception
     */
    @RequestMapping("exportExcel.hd")
    public void exportExcel(HttpServletRequest request, HttpServletResponse resp) throws Exception {
        request.setCharacterEncoding("UTF-8");
        //springboot 一般将静态文件 放在static中
        ClassPathResource cpr = new ClassPathResource("/static/upload/excel/"+"station_info.xls");
        //读取指定目录下面的excel导出模板
        //String excelDir = cpr.getFile();
        Workbook wb = null;
        try {
            File f = cpr.getFile();
            FileInputStream fi = new FileInputStream(f);
            wb = new HSSFWorkbook(fi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建字体，设置其为红色、粗体：
        Font font = wb.createFont();
        font.setColor(Font.COLOR_RED);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 创建格式
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        CellStyle cellStyleBorder = wb.createCellStyle();
        cellStyleBorder.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBorder.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBorder.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBorder.setBorderTop(CellStyle.BORDER_THIN);
        CellStyle cellStyleDataForma = wb.createCellStyle();
        cellStyleDataForma.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        Map<String, CellStyle> styleMap = new HashMap<String, CellStyle>();
        styleMap.put("cellStyleBorder", cellStyleBorder);
        styleMap.put("cellStyleDataForma", cellStyleDataForma);
        // 获得第一个工作区
//        Sheet sheet = wb.getSheetAt(0);
        HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0);

        /*List<StationDto> stationDtoList = this.findStationDtoList(request);
        StationInfo stationInfo;
        Term term;
        TermSim termSim;
        TermStatus termStatus;
        TermLocation termLocation;
        StationRsvr stationRsvr;
        StationRiver stationRiver;*/
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        if (!CommUtils.isNull("测试")) {
            HSSFRow row;
            int index = 0;
            //循环遍历查询出来的结果对象，写入到excel表格里面去
            for (Integer i : list) {
                /*stationInfo = stationDto.getStationInfo();
                term = stationDto.getRtuDto().getTerm();
                termSim = stationDto.getRtuDto().getTermSim();
                termStatus = stationDto.getRtuDto().getTermStatus();
                termLocation = stationDto.getRtuDto().getTermLocation();
                stationRsvr = stationDto.getStationRsvr();
                stationRiver = stationDto.getStationRiver();*/

                //从第三行开始写入
                row = sheet.createRow(index + 2);
                //往表格里面填充数据
                //国家测站号
                this.fillStringCellValue(row, 0, index+"");
                //测站名称
                this.fillStringCellValue(row, 1, index+"");
                //测站类型
                this.fillStringCellValue(row, 2, index+"");
                //设备地址
                this.fillStringCellValue(row, 3, index+"");
                //设备类型
                this.fillStringCellValue(row, 4, index+"");
                //设备型号
                this.fillStringCellValue(row, 5, index+"");
                //所属行政区划
                this.fillStringCellValue(row, 6, index+"");
                //负责人
                this.fillStringCellValue(row, 7, index+"");
                //联系电话
                this.fillStringCellValue(row, 8, index+"");
                //SIM卡号
                this.fillStringCellValue(row, 9, index+"");
                //建成日期
                this.fillStringCellValue(row, 10, index+"");
                //管理单位
                this.fillStringCellValue(row, 11, index+"");
                //所属河流
                if (null != index+"") {
                    this.fillStringCellValue(row, 12, index+"");
                }
                //死水位
                if (null != index+"") {
                    this.fillStringCellValue(row, 13, null == index+"" ? "0.00" : index+"");
                }
                //汛限水位
                if (null != index+"") {
                    this.fillStringCellValue(row, 14, null == index+"" ? "0.00" : index+"");
                }
                //安装位置
                this.fillStringCellValue(row, 15, index+"");
                //描述信息
                this.fillStringCellValue(row, 16, index+"");
                //经度
                this.fillStringCellValue(row, 17, index+"");
                //纬度
                this.fillStringCellValue(row, 18, index+"");
                //基值
                this.fillStringCellValue(row, 19, null == index+"" ? "0" : index+"");
                index++;
            }
        }
        // 生成导出文件
        String time = CommUtils.convertDateToString(new Date(), "yyyy_MM_dd_HH_mm_ss");
        File stationExcelFile = new File("station_" + time + ".xls");
        FileOutputStream fos = new FileOutputStream(stationExcelFile);
        wb.write(fos);
        if (!CommUtils.isNull(fos)) {
            fos.close();
        }
        // 读到流中
        InputStream inStream = new FileInputStream(stationExcelFile);
        // 设置输出的格式
        resp.reset();
        resp.setContentType("application/x-download; charset=UTF-8");
        resp.addHeader("Content-Disposition", "attachment; filename=\"" + stationExcelFile + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[1024];
        try {
            OutputStream out = resp.getOutputStream();
            while (inStream.read(b) > 0) {
                out.write(b);
            }
            inStream.close();
            out.close();
            stationExcelFile.delete();
        } catch (IOException ex) {
            throw ex;
        }
    }

    /*
     * 设置表格单元格文本内容
     * @param row HSSFRow
     * @param index 单元格下标，从0开始
     * @param content 填充内容
     */
    private void fillStringCellValue(HSSFRow row, int index, String content) throws IOException {
        HSSFCell cell = row.createCell(index);
        //设置单元格为文本
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        if (!CommUtils.isNull(content)) {
            cell.setCellValue(content);
        }
    }

    /**
     * 测试 导出路径
     * @param request
     * @param resp
     * @return
     * @throws IOException
     */
    @RequestMapping("getExcelDir.hd")
    @ResponseBody
    public String getExcelDir(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        ClassPathResource cpr = new ClassPathResource("/static/upload/excel/"+"station_info.xls");
        return cpr.getFile().getPath();
    }

    /**
     * 导入文件
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("importExcel.hd")
    @ResponseBody
    public String  importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        //实际中 写到service中
        //上传路径
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) {
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(),UPLOAD_DIR);
        if(!upload.exists()) {
            upload.mkdirs();
        }
        if (file.isEmpty()){
            throw new StorageException("文件为空： " + file.getOriginalFilename());
        }
        Files.copy(file.getInputStream(),new File(upload,file.getOriginalFilename()).toPath());
        return "上传成功";

    }

}
