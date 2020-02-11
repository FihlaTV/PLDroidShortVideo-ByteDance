package com.qiniu.pili.droid.shortvideo.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiniu.bytedanceplugin.model.StickerItem;
import com.qiniu.pili.droid.shortvideo.demo.R;
import com.qiniu.pili.droid.shortvideo.demo.adapter.StickerRVAdapter;
import com.qiniu.pili.droid.shortvideo.demo.fragment.contract.StickerContract;
import com.qiniu.pili.droid.shortvideo.demo.fragment.contract.presenter.StickerPresenter;
import com.qiniu.pili.droid.shortvideo.demo.utils.ToastUtils;
import com.qiniu.pili.droid.shortvideo.demo.view.RecordView;

public class StickerFragment extends BaseFeatureFragment<StickerContract.Presenter, StickerFragment.IStickerCallback>
        implements StickerRVAdapter.OnItemClickListener, RecordView.OnCloseListener, StickerContract.View {
    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(R.layout.fragment_sticker, container, false);
        return rv;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPresenter(new StickerPresenter());

        StickerRVAdapter adapter = new StickerRVAdapter(mPresenter.getStickersItems(), this);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(StickerItem item) {
        if (item.getTip()!=null) {
            ToastUtils.showShortMessage(item.getTip());
        }
        if (getCallback() == null) {
            return;
        }
        getCallback().onStickerSelected(item.getFileName());
    }

    @Override
    public void onClose() {
        getCallback().onStickerSelected(null);
        ((StickerRVAdapter)rv.getAdapter()).setSelect(0);
    }

    public interface IStickerCallback {
        void onStickerSelected(String path);
    }
}
