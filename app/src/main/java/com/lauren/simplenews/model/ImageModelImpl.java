package com.lauren.simplenews.model;

import com.lauren.simplenews.beans.ImageBean;
import com.lauren.simplenews.model.imodel.IImageModel;
import com.lauren.simplenews.utils.URLUtils;
import com.lauren.simplenews.utils.ImageJsonUtils;
import com.lauren.simplenews.utils.OkHttpUtils;

import java.util.List;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/22
 */
public class ImageModelImpl implements IImageModel {

    /**
     * 获取图片列表
     * @param listener
     */
    @Override
    public void loadImageList(final OnLoadImageListListener listener) {
        String url = URLUtils.IMAGES_URL;

        //接口回调
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<ImageBean> iamgeBeanList = ImageJsonUtils.readJsonImageBeans(response);
                listener.onSuccess(iamgeBeanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load image list failure.", e);
            }
        };

        //调接口
        OkHttpUtils.get(url, loadNewsCallback);
    }

    public interface OnLoadImageListListener {
        void onSuccess(List<ImageBean> list);
        void onFailure(String msg, Exception e);
    }
}
