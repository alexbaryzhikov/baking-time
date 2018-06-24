package com.alexbaryzhikov.bakingtime.di.modules;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager;

import com.alexbaryzhikov.bakingtime.di.scopes.DetailFragmentScope;
import com.alexbaryzhikov.bakingtime.ui.DetailFragment;
import com.alexbaryzhikov.bakingtime.ui.MainActivity;
import com.alexbaryzhikov.bakingtime.ui.DetailAdapter.StepClickCallback;
import com.alexbaryzhikov.bakingtime.viewmodel.RecipeViewModel;
import com.alexbaryzhikov.bakingtime.viewmodel.RecipeViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailFragmentModule {

  @Provides
  LayoutManager provideLayoutManager(Context context) {
    return new LinearLayoutManager(context);
  }

  @Provides
  @DetailFragmentScope
  RecipeViewModel provideRecipeViewModel(MainActivity mainActivity, RecipeViewModelFactory factory) {
    return ViewModelProviders.of(mainActivity, factory).get(RecipeViewModel.class);
  }

  @Provides
  @DetailFragmentScope
  StepClickCallback provideStepClickCallback(MainActivity mainActivity, DetailFragment fragment) {
    return (recipePosition, stepPosition) -> {
      if (fragment.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
        mainActivity.showStep(recipePosition, stepPosition);
      }
    };
  }

  @Provides
  Context provideContext(DetailFragment fragment) {
    return fragment.getContext();
  }

  @Provides
  MainActivity provideMainActivity(DetailFragment fragment) {
    return (MainActivity) fragment.getActivity();
  }
}
