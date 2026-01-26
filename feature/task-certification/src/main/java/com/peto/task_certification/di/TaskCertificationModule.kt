package com.peto.task_certification.di

import com.peto.task_certification.TaskCertificationViewModel
import com.peto.task_certification.camera.Camera
import com.peto.task_certification.camera.CaptureCamera
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val taskCertificationModule =
    module {
        viewModelOf(::TaskCertificationViewModel)
        factory<Camera> { CaptureCamera(get()) }
    }
