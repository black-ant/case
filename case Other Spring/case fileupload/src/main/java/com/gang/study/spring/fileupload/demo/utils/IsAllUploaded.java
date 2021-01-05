package com.gang.study.spring.fileupload.demo.utils;


import com.gang.study.spring.fileupload.demo.model.File;
import com.gang.study.spring.fileupload.demo.model.UploadInfo;
import com.gang.study.spring.fileupload.demo.service.FileService;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gang.study.spring.fileupload.demo.utils.MergeFile.mergeFile;

public class IsAllUploaded {

    private final static List<UploadInfo> uploadInfoList = new ArrayList<>();

    /**
     * @param md5
     * @param chunks
     * @return
     */
    public static boolean isAllUploaded(final String md5,
                                        final String chunks) {
        int size = uploadInfoList.stream()
                .filter(item -> item.getMd5().equals(md5))
                .distinct()
                .collect(Collectors.toList())
                .size();
        boolean bool = (size == Integer.parseInt(chunks));
        if (bool) {
            synchronized (uploadInfoList) {
                uploadInfoList.removeIf(item -> Objects.equals(item.getMd5(), md5));
            }
        }
        return bool;
    }

    /**
     * @param md5         MD5
     * @param guid        随机生成的文件名
     * @param chunk       文件分块序号
     * @param chunks      文件分块数
     * @param fileName    文件名
     * @param ext         文件后缀名
     * @param fileService fileService
     */
    public static void Uploaded(final String md5,
                                final String guid,
                                final String chunk,
                                final String chunks,
                                final String uploadFolderPath,
                                final String fileName,
                                final String ext,
                                final FileService fileService)
            throws Exception {
        synchronized (uploadInfoList) {
            uploadInfoList.add(new UploadInfo(md5, chunks, chunk, uploadFolderPath, fileName, ext));
        }
        boolean allUploaded = isAllUploaded(md5, chunks);
        int chunksNumber = Integer.parseInt(chunks);

        if (allUploaded) {
            mergeFile(chunksNumber, ext, guid, uploadFolderPath);
            fileService.save(new File(guid + ext, md5, new Date()));
        }
    }
}



