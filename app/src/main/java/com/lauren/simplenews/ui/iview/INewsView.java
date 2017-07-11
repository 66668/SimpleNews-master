package com.lauren.simplenews.ui.iview;

import com.lauren.simplenews.beans.NewsBean;

import java.util.List;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/18
 */
public interface INewsView {

    void showProgress();

    void addNews(List<NewsBean> newsList);

    void hideProgress();

    void showLoadFailMsg();
}
