package com.davidsantiagoiriarte.presentation.di

import com.davidsantiagoiriarte.domain.di.NAMED_INJECTOR_API_GIF_REPOSITORY
import com.davidsantiagoiriarte.domain.di.NAMED_INJECTOR_FAVORITE_GIF_REPOSITORY
import com.davidsantiagoiriarte.presentation.gifslist.GifsViewModel
import com.davidsantiagoiriarte.presentation.util.GIFS_SCREEN_INDEX
import com.davidsantiagoiriarte.presentation.util.ViewModelFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    viewModel { (screenIndex: Int) ->
        ViewModelFactory(
            if (screenIndex == GIFS_SCREEN_INDEX) {
                get(named(NAMED_INJECTOR_API_GIF_REPOSITORY))
            } else {
                get(named(NAMED_INJECTOR_FAVORITE_GIF_REPOSITORY))
            },
            get(),
            get()
        ).create(GifsViewModel::class.java)
    }

}
