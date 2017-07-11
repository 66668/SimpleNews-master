package com.lauren.simplenews.model;

import com.lauren.simplenews.beans.NewsBean;
import com.lauren.simplenews.beans.NewsDetailBean;
import com.lauren.simplenews.model.imodel.INewsModel;
import com.lauren.simplenews.utils.URLUtils;
import com.lauren.simplenews.utils.NewsJsonUtils;
import com.lauren.simplenews.ui.NewsFragment;
import com.lauren.simplenews.utils.OkHttpUtils;

import java.util.List;

/**
 * Description : 新闻业务处理类
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/19
 */
public class NewsModelImpl implements INewsModel {

    /**
     * 加载新闻列表
     * @param url
     * @param listener
     */
    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener listener) {

        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {

            @Override
            public void onSuccess(String response) {
                List<NewsBean> newsBeanList = NewsJsonUtils.readJsonNewsBeans(response, getID(type));
                listener.onSuccess(newsBeanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news list failure.", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }

    /**
     * 加载新闻详情
     * @param docid
     * @param listener
     */
    @Override
    public void loadNewsDetail(final String docid, final OnLoadNewsDetailListener listener) {
        String url = getDetailUrl(docid);
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response, docid);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news detail info failure.", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }

    /**
     * 获取ID
     * @param type
     * @return
     */
    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = URLUtils.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = URLUtils.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = URLUtils.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = URLUtils.JOKE_ID;
                break;
            default:
                id = URLUtils.TOP_ID;
                break;
        }
        return id;
    }

    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(URLUtils.NEW_DETAIL);
        sb.append(docId).append(URLUtils.END_DETAIL_URL);
        return sb.toString();
    }

}
