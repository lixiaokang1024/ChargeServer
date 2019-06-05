package com.charge.export.common;

import com.charge.pojo.common.PageResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractExportCSVRandomHandler<T, P> implements ExportCSVService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractExportCSVRandomHandler.class);

    private static final int DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 2000;

    protected P searchParam;
    private Integer currentPage = DEFAULT_CURRENT_PAGE;
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    public File exportCSVFile(String templateFilePath) throws IOException {
        final long timestamp = System.currentTimeMillis();

        final StringBuilder builder = new StringBuilder();
        builder.append(templateFilePath).append(File.separator).append("temp").append(File.separator);
        File directory = new File(builder.toString());
        if(!directory.exists()) {
            if(logger.isDebugEnabled()) {
                logger.debug("create temp directory ["+directory.getAbsolutePath()+"]");
            }
            directory.mkdirs();
        }
        File targetFile = new File(builder.append(timestamp).append(".csv").toString());
        if(!targetFile.exists()) {
            if(logger.isDebugEnabled()) {
                logger.debug("create temp file ["+targetFile.getAbsolutePath()+"]");
            }
            targetFile.createNewFile();
        }
        writeRandomAccessFile(Collections.singletonList(initColumnHeader()), targetFile);

        while (true) {
            PageResultDTO<List<T>> pageResultDTO = queryPageData(searchParam, getCurrentPage(), getPageSize());
            if (null == pageResultDTO.getData() || pageResultDTO.getData().isEmpty()) {
                break;
            }
            List<String> data = initData(pageResultDTO.getData());
            writeRandomAccessFile(data, targetFile);
            currentPage ++;
        }

        return targetFile;
    }

    private void writeRandomAccessFile(List<String> data, File targetFile) throws IOException {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(targetFile, true);
            FileChannel fileChannel = outputStream.getChannel();

            for (Iterator<String> iterator = data.iterator(); iterator.hasNext();) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                String value = iterator.next();
                byteBuffer.put(value.getBytes("GBK"));
                byteBuffer.flip();

                fileChannel.write(byteBuffer);
            }
            fileChannel.close();
            outputStream.flush();
        } catch (Exception e){
            logger.error(e.getMessage(),e);
        } finally {
            if (null != outputStream) {
                outputStream.close();
            }
        }
    }

    public Integer getCurrentPage() {
        return currentPage == null ? DEFAULT_CURRENT_PAGE : currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage == null ? DEFAULT_CURRENT_PAGE : currentPage;
    }

    public Integer getPageSize() {
        return pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
    }

    protected abstract PageResultDTO<List<T>> queryPageData(P parameter, int currentPage, int pageSize);

    protected abstract List<String> initData(List<T> data);

    protected abstract String initColumnHeader();

}
