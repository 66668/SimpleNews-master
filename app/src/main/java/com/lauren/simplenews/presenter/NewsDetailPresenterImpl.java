package com.lauren.simplenews.presenter;

import android.content.Context;

import com.lauren.simplenews.beans.NewsDetailBean;
import com.lauren.simplenews.model.imodel.INewsModel;
import com.lauren.simplenews.model.NewsModelImpl;
import com.lauren.simplenews.model.OnLoadNewsDetailListener;
import com.lauren.simplenews.ui.iview.INewsDetailView;
import com.lauren.simplenews.presenter.ipersenter.INewsDetailPresenter;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 2015/12/21
 */
public class NewsDetailPresenterImpl implements INewsDetailPresenter, OnLoadNewsDetailListener {

    private Context mContent;
    private INewsDetailView mINewsDetailView;
    private INewsModel mNewsModel;

    public NewsDetailPresenterImpl(Context mContent, INewsDetailView mINewsDetailView) {
        this.mContent = mContent;
        this.mINewsDetailView = mINewsDetailView;
        mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNewsDetail(final String docId) {
        mINewsDetailView.showProgress();
        mNewsModel.loadNewsDetail(docId, this);
    }


    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if (newsDetailBean != null) {
            mINewsDetailView.showNewsDetialContent(newsDetailBean.getBody());
        }
        mINewsDetailView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mINewsDetailView.hideProgress();
    }
}
