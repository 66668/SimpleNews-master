package com.lauren.simplenews.presenter;

import com.lauren.simplenews.beans.ImageBean;
import com.lauren.simplenews.model.imodel.IImageModel;
import com.lauren.simplenews.model.ImageModelImpl;
import com.lauren.simplenews.ui.iview.ImageView;
import com.lauren.simplenews.presenter.ipersenter.IImagePresenter;

import java.util.List;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/22
 */
public class ImagePresenterImpl implements IImagePresenter, ImageModelImpl.OnLoadImageListListener {

    private IImageModel mIImageModel;
    private ImageView mImageView;

    public ImagePresenterImpl(ImageView imageView) {
        this.mIImageModel = new ImageModelImpl();
        this.mImageView = imageView;
    }

    @Override
    public void loadImageList() {
        mImageView.showProgress();
        mIImageModel.loadImageList(this);
    }

    @Override
    public void onSuccess(List<ImageBean> list) {
        mImageView.addImages(list);
        mImageView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mImageView.hideProgress();
        mImageView.showLoadFailMsg();
    }
}
