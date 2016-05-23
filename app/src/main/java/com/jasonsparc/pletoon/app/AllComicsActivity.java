package com.jasonsparc.pletoon.app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jasonsparc.pletoon.R;
import com.jasonsparc.pletoon.app.setup.ItemComicSetup;
import com.jasonsparc.pletoon.app.setup.LayoutAllComicsSetup;
import com.jasonsparc.pletoon.client.Pletoon;
import com.jasonsparc.pletoon.databinding.LayoutAllComicsBinding;
import com.jasonsparc.pletoon.model.Comic;
import com.jasonsparc.pletoon.model.Comics;
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
public class AllComicsActivity extends RxNaviAppCompatActivity implements ItemComicSetup.OnClickComicListener {

	@State Comics comics;

	LayoutAllComicsBinding binding;
	RefreshRequestHelper refreshRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NaviIcepick.installOnCreate(this, savedInstanceState);

		binding = DataBindingUtil.setContentView(this, R.layout.layout_all_comics);
		binding.setSetup(new LayoutAllComicsSetup());
		binding.setComics(comics);

		setUpActionBar();
		setUpRefreshLayout();
		setUpComicsLoading();
	}

	private void setUpActionBar() {
		setSupportActionBar(binding.toolbar);
	}

	private void setUpRefreshLayout() {
		refreshRequest = new RefreshRequestHelper(binding.refreshLayout);
		binding.refreshLayout.setOnRefreshListener(AllComicsActivity::refreshComics);
	}

	// ======================================
	// Data handling logic
	// ======================================

	static final Object refreshSignal = new Object();
	static final RxBus<Object> refreshBus = new RxBus<>();

	private void setUpComicsLoading() {
		refreshBus.observe()
				.onBackpressureDrop() // Guard against uncontrollable frequency of scheduler executions.
				.startWith(refreshSignal) // Initial load request
				.concatMap(r -> Pletoon.api().getComics()
						.subscribeOn(Sched.io())
						.doOnSubscribe(refreshRequest::requestRefreshing)
						.doOnTerminate(refreshRequest::requestEndRefreshing)
						.compose(Op.ignoreError()))
				.compose(bindUntilEvent(ActivityEvent.DESTROY))
				.observeOn(Sched.main())
				.onBackpressureLatest() // Guard against uncontrollable frequency of scheduler executions.
				.subscribe(this::setComics);
	}

	final void setComics(Comics comics) {
		this.comics = comics;
		binding.setComics(comics);
	}

	static void refreshComics() {
		refreshBus.post(refreshSignal);
	}

	// ======================================
	// Callbacks implementation
	// ======================================

	@Override
	public void onClickComic(Comic comic) {
		Intent intent = new Intent(this, ComicActivity.class);
		intent.putExtra(ComicActivity.EXTRA_COMIC, comic);
		startActivity(intent);
	}
}
