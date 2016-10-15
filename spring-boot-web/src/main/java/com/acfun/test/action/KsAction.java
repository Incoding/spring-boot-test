/**
 * 文件名：@KsAction.java <br/>
 * 包名：tv.acfun.ks.action <br/>
 * 项目名：acfun-ks <br/>
 * @author xtwin <br/>
 */
package com.acfun.test.action;

import com.ksyun.ks3.dto.Ks3ObjectSummary;
import com.ksyun.ks3.dto.ObjectListing;
import com.ksyun.ks3.service.Ks3;
import com.ksyun.ks3.service.request.ListObjectsRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名：KsAction  <br />
 *
 * 功能：
 *
 * @author xtwin <br />
 * 创建时间：2016年6月17日 下午12:09:04  <br />
 * @version 2016年6月17日
 */
@Controller
public class KsAction {

    // 日志记录器
    private static final Log logger = LogFactory.getLog(KsAction.class);

    // 待上传的文件路径
    @Value("${for.upload.path}")
    private File filePath;

    // 文件上传的bucket
    @Value("${ks.upload.bucket}")
    private String uploadBucket;

    // 转码规则
    @Resource(name = "encodeMap")
    private Map<String, String> encodeMap;

    // ks3客户端
    @Autowired
    private Ks3 client;

    /**
     * 功能：文件上传输入页面 <br/>
     *
     * @author xtwin <br/>
     * @version 2016年6月17日 下午5:00:27 <br/>
     */
    @RequestMapping("/nihao")
    public String uploadInput2(ModelMap map) {
        map.put("nihao", "nihaokk22");
        return "nihao";
    }



    @RequestMapping("/upload-input")
    public String uploadInput(ModelMap map) {
        map.put("nihao", "nihaokk");
        return "ks/upload";
    }

    /**
     * 功能： <br/>
     *
     * @author xtwin <br/>
     * @version 2016年6月20日 上午10:55:09 <br/>
     */
    @RequestMapping("/callback")
    @ResponseBody
    public Map<String, Object> callback(HttpServletRequest request) {

        // 打印金山云的接口信息
        logger.info("print header info [start]...");
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();

            logger.info("header : [" + name + " = " + request.getHeader(name) + "]");
        }
        logger.info("print header info [end]...");

        logger.info("print param info [start]...");
        names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();

            logger.info("param : [" + name + " = " + Arrays.toString(request.getParameterValues(name)) + "]");
        }
        logger.info("print param info [end]...");

        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("realPath 1", request.getRealPath("/"));
        result.put("realPath 2", request.getRealPath(""));
        result.put("context path", request.getContextPath());

        return result;
    }

    /**
     * 功能：文件上传 <br/>
     *
     * @author xtwin <br/>
     * @version 2016年6月17日 下午1:56:02 <br/>
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file) {
        String message = "上传成功，请等待后台任务处理。";
        boolean success = true;
        if (file.isEmpty()) {
            message = "文件为空";
        } else {
            File dest = new File(filePath, String.valueOf(System.currentTimeMillis()) + String.valueOf(Math.random()).replace(".", "") + ".tmp");
            try {
                // 保存文件
                FileUtils.copyToFile(file.getInputStream(), dest);

                // 文件改名
                dest.renameTo(new File(filePath, file.getOriginalFilename()));

                success = true;
            } catch (Exception e) {

                logger.error("upload fail with error", e);

                // 文件保存失败，删除临时文件
                dest.delete();

                message = "上传失败：" + String.valueOf(e);
            }
        }

        Map<String, Object> result = new HashMap<>();

        result.put("message", message);
        result.put("success", success);

        return result;
    }

    /**
     * 功能：播放页面 <br/>
     *
     * @author xtwin <br/>
     * @version 2016年6月17日 下午4:34:23 <br/>
     */
    @RequestMapping("/play")
    public String play(ModelMap model, String key, String bucket) {

        model.put("key", key);
        model.put("bucket", bucket);

        return "ks/player";
    }

    /**
     * 功能：对象列表查询 <br/>
     *
     * @author xtwin <br/>
     * @version 2016年6月17日 下午2:18:04 <br/>
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/list")
    public String list(ModelMap model) {

        // 数据容器
        //List<Ks3ObjectSummary> data = new ArrayList<>();

        // <原始文件名, [原始文件, [360P, 720P, 1080P, ...]]>
        Map<String, Object[]> data = new HashMap<>();

        String message = "操作成功";
        boolean success = false;

        try {
            // 查询请求
            ListObjectsRequest request = new ListObjectsRequest(uploadBucket);
            while (true) {
                // 查询列表
                ObjectListing listing = client.listObjects(request);

                // 提取数据
                //data.addAll(listing.getObjectSummaries());
                for (Ks3ObjectSummary kos : listing.getObjectSummaries()) {
                    String key = kos.getKey();

                    int index = key.lastIndexOf('-');

                    String _p = null;
                    if (index != -1) {
                        // 当前为转码产生的文件
                        _p = key.substring(index + 1, key.lastIndexOf('.'));

                        // 截取原始文件名
                        key = key.substring(0, index);
                    }

                    Object[] vs = data.get(key);
                    if (null == vs) {
                        data.put(key, vs = new Object[] { null, new HashMap<String, Ks3ObjectSummary>()});
                    }

                    if (null != _p) {
                        ((Map<String, Ks3ObjectSummary>) vs[1]).put(_p, kos);
                    } else {
                        vs[0] = kos;
                    }
                }

                if (! listing.isTruncated()) {
                    break;
                }

                request.setMarker(listing.getNextMarker());
            }

            success = true;
        } catch (Exception e) {
            logger.error("query fail with error", e);

            message = "Error: " + String.valueOf(e);
        }

        model.put("message", message);
        model.put("success", success);
        model.put("data", data);

        return "ks/list";
    }
}