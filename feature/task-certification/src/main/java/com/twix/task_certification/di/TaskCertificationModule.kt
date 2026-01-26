package com.twix.task_certification.di

import com.twix.task_certification.TaskCertificationViewModel
import com.twix.task_certification.camera.Camera
import com.twix.task_certification.camera.CaptureCamera
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val taskCertificationModule =
    module {
        viewModelOf(::TaskCertificationViewModel)
        factory<Camera> { CaptureCamera(get()) }
    }
