package com.twix.task_certification.di

import com.twix.navigation.NavRoutes
import com.twix.navigation.base.NavGraphContributor
import com.twix.task_certification.TaskCertificationViewModel
import com.twix.task_certification.camera.Camera
import com.twix.task_certification.camera.CaptureCamera
import com.twix.task_certification.navigation.TaskCertificationGraph
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val taskCertificationModule =
    module {
        viewModelOf(::TaskCertificationViewModel)
        factory<Camera> { CaptureCamera(get()) }
        single<NavGraphContributor>(named(NavRoutes.TaskCertificationRoute.route)) { TaskCertificationGraph }
    }
