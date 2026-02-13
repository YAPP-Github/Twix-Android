package com.twix.task_certification.certification.model

enum class TorchStatus {
    On,
    Off,
    ;

    companion object {
        fun toggle(value: TorchStatus): TorchStatus =
            when (value) {
                On -> Off
                Off -> On
            }
    }
}
