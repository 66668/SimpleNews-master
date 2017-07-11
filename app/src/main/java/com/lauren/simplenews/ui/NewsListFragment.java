package com.lauren.simplenews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lauren.simplenews.R;
import com.lauren.simplenews.beans.NewsBean;
import com.lauren.simplenews.utils.URLUtils;
import com.lauren.simplenews.adapter.NewsAdapter;
import com.lauren.simplenews.presenter.ipersenter.INewsPresenter;
import com.lauren.simplenews.presenter.NewsPresenterImpl;
import com.lauren.simplenews.ui.iview.INewsView;
import com.lauren.simplenews.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : 新闻Fragment
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/13
 */
public class NewsListFragment extends Fragment implements INewsView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NewsListFragment";

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private List<NewsBean> listData;
    private INewsPresenter mNewsPresenter;

    private int mType = NewsFragment.NEWS_TYPE_TOP;
    private int pageIndex = 0;

    public static NewsListFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化 presenter
        mNewsPresenter = new NewsPresenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);

        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

        //设置加载progressbar的颜色
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary_text//primary
                , R.color.primary_dark
                , R.color.primary_light
                , R.color.accent);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new NewsAdapter(getActivity().getApplicationContext());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        onRefresh();
        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()
                    && mAdapter.isShowFooter()) {
                //加载更多
                LogUtils.d(TAG, "loading more data");
                mNewsPresenter.loadNews(mType, pageIndex + URLUtils.PAZE_SIZE);
            }
        }
    };

    private NewsAdapter.OnItemClickListener mOnItemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (listData.size() <= 0) {
                return;
            }
            NewsBean news = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("news", news);

            View transitionView = view.findViewById(R.id.ivNews);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_news_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };
    /**
     * iView回调
     */
    @Override
    public void showProgress() {
        mSwipeRefreshWidget.setRefreshing(true);
    }

    /**
     * iView回调,将获取数据listData返回
     */
    @Override
    public void addNews(List<NewsBean> newsList) {
        mAdapter.isShowFooter(true);
        if(listData == null) {
            listData = new ArrayList<NewsBean>();
        }
        listData.addAll(newsList);
        if(pageIndex == 0) {
            mAdapter.setmDate(listData);
        } else {
            //如果没有更多数据了,则隐藏footer布局
            if(newsList == null || newsList.size() == 0) {
                mAdapter.isShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex += URLUtils.PAZE_SIZE;
    }

    /**
     * iView回调
     */
    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }
    /**
     * iView回调
     */
    @Override
    public void showLoadFailMsg() {
        if(pageIndex == 0) {
            mAdapter.isShowFooter(false);
            mAdapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    /**
     * SwipeRefreshLayout刷新
     */
    @Override
    public void onRefresh() {
        pageIndex = 0;
        if(listData != null) {
            listData.clear();
        }
        mNewsPresenter.loadNews(mType, pageIndex);
    }

}
