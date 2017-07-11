package com.lauren.simplenews.model.imodel;

import com.lauren.simplenews.model.ImageModelImpl;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/22
 */
public interface IImageModel {
    void loadImageList(ImageModelImpl.OnLoadImageListListener listener);
}
