package com.jasonsparc.pletoon.app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.jasonsparc.pletoon.R;
import com.jasonsparc.pletoon.app.setup.LayoutComicSetup;
import com.jasonsparc.pletoon.client.Pletoon;
import com.jasonsparc.pletoon.databinding.LayoutComicBinding;
import com.jasonsparc.pletoon.model.Comic;
import com.jasonsparc.pletoon.model.ComicWrapper;
import com.jasonsparc.pletoon.rx.Op;
import com.jasonsparc.pletoon.rx.RxBus;
import com.jasonsparc.pletoon.rx.Sched;
import com.jasonsparc.pletoon.rxlifecycle.RxNaviAppCompatActivity;
import com.jasonsparc.pletoon.util.NaviIcepick;
import com.jasonsparc.pletoon.view.RefreshRequestHelper;
import com.trello.rxlifecycle.ActivityEvent;

import icepick.State;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class ComicActivity extends RxNaviAppCompatActivity {

	public static final String EXTRA_COMIC = "com.jasonsparc.pletoon.app.COMIC";

	@State Comic comic;

	LayoutComicBinding binding;
	RefreshRequestHelper refreshRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!fetchExtras()) return;
		NaviIcepick.installOnCreate(this, savedInstanceState);

		binding = DataBindingUtil.setContentView(this, R.layout.layout_comic);
		binding.setSetup(new LayoutComicSetup());
		binding.setComic(comic);

		setUpActionBar();
		setUpRefreshLayout();
		setUpComicLoading();
	}

	private boolean fetchExtras() {
		comic = getIntent().getParcelableExtra(EXTRA_COMIC);

		if (comic == null) {
			finish();
			return false;
		}
		return true;
	}

	private void setUpActionBar() {
		setSupportActionBar(binding.toolbar);
		setTitle(comic.title());

		ActionBar actionBar = getSupportActionBar();
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onSupportNavigateUp() {
		if (super.onSupportNavigateUp()) {
			return true;
		}
		onBackPressed();
		return false;
	}

	private void setUpRefreshLayout() {
		refreshRequest = new RefreshRequestHelper(binding.refreshLayout);
		binding.refreshLayout.setOnRefreshListener(ComicActivity::refreshComic);
	}

	// ======================================
	// Data handling logic
	// ======================================

	static final Object refreshSignal = new Object();
	static final RxBus<Object> refreshBus = new RxBus<>();

	private void setUpComicLoading() {
		refreshBus.observe()
				.onBackpressureDrop() // Guard against uncontrollable frequency of scheduler executions.
				.startWith(refreshSignal) // Initial load request
				.concatMap(r -> Pletoon.api().getComic(comic.id())
						.map(ComicWrapper::comic)
						.subscribeOn(Sched.io())
						.doOnSubscribe(refreshRequest::requestRefreshing)
						.doOnTerminate(refreshRequest::requestEndRefreshing)
						.compose(Op.ignoreError()))
				.compose(bindUntilEvent(ActivityEvent.DESTROY))
				.observeOn(Sched.main())
				.onBackpressureLatest() // Guard against uncontrollable frequency of scheduler executions.
				.subscribe(this::setComic);
	}

	final void setComic(Comic comic) {
		this.comic = comic;
		binding.setComic(comic);
	}

	static void refreshComic() {
		refreshBus.post(refreshSignal);
	}
}
